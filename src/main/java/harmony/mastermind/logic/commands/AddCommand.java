package harmony.mastermind.logic.commands;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.commons.exceptions.InvalidEventDateException;
import harmony.mastermind.memory.GenericMemory;
import harmony.mastermind.memory.Memory;
import harmony.mastermind.logic.parser.Parser;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.tag.UniqueTagList;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.TaskBuilder;
import harmony.mastermind.model.task.UniqueTaskList;
import harmony.mastermind.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Adds a task to the task manager.
 * 
 */
// @@author A0138862W
public class AddCommand extends Command implements Undoable, Redoable {

    public static final String COMMAND_KEYWORD_ADD = "add";
    public static final String COMMAND_KEYWORD_DO = "do";
    
    // Better regex, support better NLP:
    // general form: add some task name from tomorrow 8pm to next friday 8pm daily #recurring,awesome
    // https://regex101.com/r/M2A3tB/8
    public static final String COMMAND_ARGUMENTS_REGEX = "(?='(?<nameEscaped>.+)'|(?<name>(?:(?:.(?!by|from|#)))+))"
                                                        + "(?:(?=.*(?:by|from)\\s(?<dates>(?:.(?!#|.*'))+)?))?"
                                                        + "(?:(?=.*(?<recur>daily|weekly|monthly|yearly)))?"
                                                        + "(?:(?=.*#(?<tags>.+)))?"
                                                        + ".*";

    public static final Pattern COMMAND_ARGUMENTS_PATTERN = Pattern.compile(COMMAND_ARGUMENTS_REGEX);
    
    public static final String COMMAND_DESCRIPTION = "Adding a task";

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
    

    private static final String TASK = "Task";
    private static final String DEADLINE = "Deadline";
    private static final String EVENT = "Event";
    
    static GenericMemory task; 
    static GenericMemory deadline; 
    static GenericMemory event;

    // @@author A0124797R-used
    /** adds an event */
    /**
     * @deprecated
     */
    /*
    public AddCommand(String name, String startDate, String endDate, Set<String> tags, String recurVal, Memory mem) throws IllegalValueException, InvalidEventDateException {
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
        
        //Converting Date start to Calendar start
        Calendar start = dateToCalendar(startTime);
        
        //Converting Date end to Calendar end
        Calendar end = dateToCalendar(endTime);
        
        event = new GenericMemory(tags.toString(), name, "", start, end, 0);
        mem.add(event);
    }
    */

    // deadline
    // @@author A0138862W-unused
    /**
     * 
     * The builder constructor has taken care of all the construction of event, floating and deadline
     * @see AddCommand(AddCommandBuilder)
     * @deprecated
     * 
     */
    /*
    public AddCommand(String name, String endDateStr, Set<String> tags, String recur, Memory mem) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        Date createdDate = new Date();
        Date endDate = prettyTimeParser.parse(endDateStr).get(0);
        
        
        this.toAdd = new Task(name, endDate, new UniqueTagList(tagSet), recur, createdDate);
        
        //Converting Date end to Calendar end
        Calendar end = dateToCalendar(endDate);

        deadline = new GenericMemory(tags.toString(), name, "", end);
        mem.add(deadline);

    }
    */

    // floating
    // @@author A0138862W-unused
    /**
     * The builder constructor has taken care of all the construction of event, floating and deadline
     * @see AddCommand(AddCommandBuilder)
     * @deprecated
     * 
     */
    /*
    public AddCommand(String name, Set<String> tags, Memory mem) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        
        Date createdDate = new Date();

        this.toAdd = new Task(name, new UniqueTagList(tagSet), createdDate);
        
        task = new GenericMemory(tags.toString(), name, "");
        mem.add(task);
    }
    */

    // @@author A0138862W
    /**
     * Build the AddCommand using addCommandBuilder. 
     * Depending on the builder attributes, the taskBuilder will return the appropriate event/floating/deadline task. 
     * 
     * @param addCommandBuilder to build the command safely
     * 
     */
    protected AddCommand(AddCommandBuilder addCommandBuilder) throws IllegalValueException, InvalidEventDateException{
        TaskBuilder taskBuilder = new TaskBuilder(addCommandBuilder.getName());
        taskBuilder.withTags(addCommandBuilder.getTags());
        
        if(addCommandBuilder.isDeadline()){
            taskBuilder.asDeadline(addCommandBuilder.getEndDate());
        }else if(addCommandBuilder.isEvent()){
            taskBuilder.asEvent(addCommandBuilder.getStartDate(), addCommandBuilder.getEndDate());
        }
        
        if(addCommandBuilder.isRecurring()){
            taskBuilder.asRecurring(addCommandBuilder.getRecur());
        }
        
        toAdd = taskBuilder.build();
        
        Parser.mem.add(new GenericMemory(toAdd.getTags().toString(), toAdd.getName(), ""));        
    }

    @Override
    //@@author A0138862W
    public CommandResult execute() {
        assert model != null;
        try {
            executeAdd();
            
            model.pushToUndoHistory(this);
            
            // this is a new command entered by user (not undo/redo)
            // need to clear the redoHistory Stack 
            model.clearRedoHistory();
            
            requestHighlightLastActionedRow(toAdd);

            return new CommandResult(COMMAND_KEYWORD_ADD,String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(COMMAND_KEYWORD_ADD,MESSAGE_DUPLICATE_TASK);
        }

    }

    // @@author A0138862W
    /** action to perform when ModelManager requested to undo this command **/
    @Override
    public CommandResult undo() {
        try {
            model.deleteTask(toAdd);
            
            model.pushToRedoHistory(this);

            return new CommandResult(COMMAND_KEYWORD_ADD,String.format(MESSAGE_UNDO_SUCCESS, toAdd));
        } catch (UniqueTaskList.TaskNotFoundException pne) {
            return new CommandResult(COMMAND_KEYWORD_ADD,Messages.MESSAGE_TASK_NOT_IN_MASTERMIND);
        }
    }
    
    // @@author A0138862W
    /** action to perform when ModelManager requested to redo this command**/
    @Override
    public CommandResult redo() {
        assert model != null;
        try {
            executeAdd();
            
            model.pushToUndoHistory(this);
            
            requestHighlightLastActionedRow(toAdd);

            return new CommandResult(COMMAND_KEYWORD_ADD,String.format(MESSAGE_REDO_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(COMMAND_KEYWORD_ADD,MESSAGE_DUPLICATE_TASK);
        }        
    }
    
    
    // @@author A0138862W
    /** extract method since it's reusable for execute() and redo()**/
    private void executeAdd() throws DuplicateTaskException {
        model.addTask(toAdd);
    }
    
    //@@author A0143378Y
    private Calendar dateToCalendar(Date date) { 
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }


}
