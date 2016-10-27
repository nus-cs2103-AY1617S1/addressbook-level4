package harmony.mastermind.logic.commands;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.commons.exceptions.InvalidEventDateException;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.tag.UniqueTagList;
import harmony.mastermind.model.task.*;
import harmony.mastermind.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Adds a task to the task manager.
 * 
 */
// @@author A0138862W
public class AddCommand extends Command implements Undoable, Redoable {

    public static final String COMMAND_KEYWORD_ADD = "add";
    public static final String COMMAND_KEYWORD_DO = "do";

    // The main idea of capturing parameters in any order is inspired by (author
    // velop):
    // http://stackoverflow.com/questions/1177081/mulitple-words-in-any-order-using-regex

    // As for capturing optional group AND in any order:
    // http://stackoverflow.com/questions/24472120/match-optional-components-in-any-order

    // We wrote the regular expression and tested at:
    // https://regex101.com/r/bFQrP6/1
    // @@author A0138862W
    //
    // the following regex is no longer in used, replaced by better one (NLP)
    /*
    public static final String COMMAND_ARGUMENTS_REGEX = "(?=(?:.*?r\\/'(?<recur>.+?)')?)" 
            + "(?=(?:.*?\\s\\'(?<name>.+?)'))"
            + "(?=(?:.*?sd\\/'(?<startDate>.+?)')?)"
            + "(?=(?:.*?ed\\/'(?<endDate>.+?)')?)"
            + "(?=(?:.*t\\/'(?<tags>\\w+(?:,\\w+)*)?')?)"
            + ".*";
     */
    
    // Better regex, support better NLP:
    // general form: add some task name from tomorrow 8pm to next friday 8pm daily #recurring,awesome
    // https://regex101.com/r/M2A3tB/8
    public static final String COMMAND_ARGUMENTS_REGEX = "(?=\\s(?<name>(?:.(?!by|from|#))+))"
                                                        + "(?:(?=.*(?:by|from)\\s(?<dates>(?:.(?!#))+)?))?"
                                                        + "(?:(?=.*(?<recur>daily|weekly|monthly|yearly)))?"
                                                        + "(?:(?=.*#(?<tags>.+)))?.*";

    public static final Pattern COMMAND_ARGUMENTS_PATTERN = Pattern.compile(COMMAND_ARGUMENTS_REGEX);

    public static final String COMMAND_FORMAT = "Floating Task: (add|do) <task_name> #[<comma_separated_tags>]\n"
                                                + "Deadline: (add|do) <task_name> by <end_date> [daily|weekly|monthly|yearly] #[comma_separated_tags]\n"
                                                + "Event: (add|do) <task_name> from <start_date> to <end_date> [daily|weekly|monthly|yearly] #[comma_separated_tags]";

    public static final String MESSAGE_EXAMPLE_EVENT = "add attend workshop from today 7pm to next monday 1pm #programming,java";
    public static final String MESSAGE_EXAMPLE_DEADLINE = "add submit homework by next sunday 11pm #math,physics";
    public static final String MESSAGE_EXAMPLE_FLOATING = "do chores #cleaning";
    public static final String MESSAGE_EXAMPLE_RECUR_DEADLINE = "add submit homework by next sunday 11pm weekly #math,physics";
    public static final String MESSAGE_EXAMPLE_RECUR_EVENT = "add attend workshop from today 7pm to next monday 1pm monthly #programming,java";
    
    public static final String MESSAGE_EXAMPLES = new StringBuilder()
                                                    .append("[Format]\n")
                                                    .append(COMMAND_FORMAT+ "\n\n")
                                                    .append("[Examples]:\n")
                                                    .append("Event: "+ MESSAGE_EXAMPLE_EVENT+"\n")
                                                    .append("Deadline: "+MESSAGE_EXAMPLE_DEADLINE+"\n")
                                                    .append("Floating: "+MESSAGE_EXAMPLE_FLOATING+"\n")
                                                    .append("Recurring Deadline: "+MESSAGE_EXAMPLE_RECUR_DEADLINE+"\n")
                                                    .append("Recurring Event: "+MESSAGE_EXAMPLE_RECUR_EVENT+"\n")
                                                    .toString();

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_UNDO_SUCCESS = "[Undo Add Command] Task deleted: %1$s";
    public static final String MESSAGE_REDO_SUCCESS = "[Redo Add Command] Task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in Mastermind";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.<br><br>
     *
     * Throws IllegalValueException if any of the raw values are invalid<br>
     * Throws InvalidEventDateException if event type has start date after end date
     */
    // event
    // @@author A0124797R
    public AddCommand(String name, String startDate, String endDate, Set<String> tags, String recurVal) throws IllegalValueException, InvalidEventDateException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        Date createdDate = new Date();
        Date startTime = prettyTimeParser.parse(startDate).get(0);
        Date endTime = prettyTimeParser.parse(endDate).get(0);
        
        if (startTime.after(endTime)) {
            throw new InvalidEventDateException();
        }

        this.toAdd = new Task(name, startTime, endTime, new UniqueTagList(tagSet), recurVal, createdDate);

    }

    // deadline
    // @@author A0138862W
    public AddCommand(String name, String endDateStr, Set<String> tags, String recur) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        // fix for #132
        List<Date> endDates = prettyTimeParser.parse(endDateStr);
        Date endDate = (endDates.isEmpty())? null: endDates.get(0);
        
        this.toAdd = new Task(name, endDate, new UniqueTagList(tagSet), recur);

    }

    // floating
    // @@author A0138862W
    public AddCommand(String name, Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        this.toAdd = new Task(name, new UniqueTagList(tagSet));
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            executeAdd();
            
            // push this command into undoHistory
            model.pushToUndoHistory(this);
            
            // this is a new command entered by user (not undo/redo)
            // need to clear the redoHistory Stack 
            model.clearRedoHistory();

            return new CommandResult(COMMAND_KEYWORD_ADD,String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(COMMAND_KEYWORD_ADD,MESSAGE_DUPLICATE_TASK);
        }

    }

    @Override
    /** action to perform when ModelManager requested to undo this command **/
    // @@author A0138862W
    public CommandResult undo() {
        try {
            // remove the task that's previously added.
            model.deleteTask(toAdd);
            
            model.pushToRedoHistory(this);

            return new CommandResult(COMMAND_KEYWORD_ADD,String.format(MESSAGE_UNDO_SUCCESS, toAdd));
        } catch (UniqueTaskList.TaskNotFoundException pne) {
            return new CommandResult(COMMAND_KEYWORD_ADD,Messages.MESSAGE_TASK_NOT_IN_MASTERMIND);
        }
    }

    @Override
    /** action to perform when ModelManager requested to redo this command**/
    // @@author A0138862W
    public CommandResult redo() {
        assert model != null;
        try {
            executeAdd();
            
            model.pushToUndoHistory(this);

            return new CommandResult(COMMAND_KEYWORD_ADD,String.format(MESSAGE_REDO_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(COMMAND_KEYWORD_ADD,MESSAGE_DUPLICATE_TASK);
        }        
    }
    
    /** extract method since it's reusable for execute() and redo()**/
    private void executeAdd() throws DuplicateTaskException {
        model.addTask(toAdd);
    }

}
