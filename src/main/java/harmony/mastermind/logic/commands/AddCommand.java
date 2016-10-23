package harmony.mastermind.logic.commands;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
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
    public static final String[] COMMAND_KEYWORDS_RECUR = {"daily", "weekly", "biweekly", "monthly", "yearly"};

    // The main idea of capturing parameters in any order is inspired by (author
    // velop):
    // http://stackoverflow.com/questions/1177081/mulitple-words-in-any-order-using-regex

    // As for capturing optional group AND in any order:
    // http://stackoverflow.com/questions/24472120/match-optional-components-in-any-order

    // We wrote the regular expression and tested at:
    // https://regex101.com/r/bFQrP6/1
    // @@author A0138862W
    public static final String COMMAND_ARGUMENTS_REGEX = "(?=(?:.*?r\\/'(?<recur>.+?)')?)" 
                                                         + "(?=(?:.*?\\s\\'(?<name>.+?)'))"
                                                         + "(?=(?:.*?sd\\/'(?<startDate>.+?)')?)"
                                                         + "(?=(?:.*?ed\\/'(?<endDate>.+?)')?)"
                                                         + "(?=(?:.*t\\/'(?<tags>\\w+(?:,\\w+)*)?')?)"
                                                         + ".*";

    public static final Pattern COMMAND_ARGUMENTS_PATTERN = Pattern.compile(COMMAND_ARGUMENTS_REGEX);

    public static final String COMMAND_FORMAT = "(add|do) [r/'<recur>'] '<name>' [sd/'<startDate>'] [ed/'<endDate>'] [t/'<tags>...']";

    public static final String MESSAGE_EXAMPLE_EVENT = "add 'attend workshop' sd/'today 7pm' ed/'next monday 1pm' t/'programming,java'";
    public static final String MESSAGE_EXAMPLE_DEADLINE = "add 'submit homework' ed/'next sunday 11pm' t/'math,physics'";
    public static final String MESSAGE_EXAMPLE_FLOATING = "do 'chores' t/'cleaning'";
    public static final String MESSAGE_EXAMPLE_RECUR_DEADLINE = "add r/'weekly' 'submit homework' ed/'next sunday 11pm' t/'math,physics'";
    public static final String MESSAGE_EXAMPLE_RECUR_EVENT = "add r/'daily 2' 'attend workshop' sd/'today 7pm' ed/'next monday 1pm' t/'programming,java'";
    
    public static final String MESSAGE_EXAMPLES = new StringBuilder()
                                                    .append("[Format]\n")
                                                    .append(COMMAND_FORMAT+ "\n\n")
                                                    .append("[Examples]:\n")
                                                    .append("Event: "+ MESSAGE_EXAMPLE_EVENT+"\n")
                                                    .append("Deadline: "+MESSAGE_EXAMPLE_DEADLINE+"\n")
                                                    .append("Floating: "+MESSAGE_EXAMPLE_FLOATING)
                                                    .append("Recurring Deadline: "+MESSAGE_EXAMPLE_RECUR_DEADLINE)
                                                    .append("Recur Event twice: "+MESSAGE_EXAMPLE_RECUR_EVENT)
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
        Date startTime = prettyTimeParser.parse(startDate).get(0);
        Date endTime = prettyTimeParser.parse(endDate).get(0);
        
        if (startTime.after(endTime)) {
            throw new InvalidEventDateException();
        }

        this.toAdd = new Task(name, startTime, endTime, new UniqueTagList(tagSet), recurVal);

    }

    // deadline
    // @@author A0138862W
    public AddCommand(String name, String endDate, Set<String> tags, String recur) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        this.toAdd = new Task(name, prettyTimeParser.parse(endDate).get(0), new UniqueTagList(tagSet), recur);

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
