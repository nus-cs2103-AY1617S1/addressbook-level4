package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.MissingRecurringDateException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    //@@author A0146123R
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add an event with a starting and ending date or a task (with or without deadline) to the task manager.\n"
            + "Parameters: n/EVENT_NAME s/START_DATE e/END_DATE [p/PRIORITY_LEVEL] [t/TAG]... or n/TASK_NAME [d/DEADLINE] [p/PRIORITY_LEVEL] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " n/Lecture s/7.10.2016-14 e/7.10.2016-16 p/1 t/CS2103, add n/Project Deadline d/14.10.2016 p/3 t/CS2103";

    public static final String MESSAGE_EVENT_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_TASK_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "It's already exists in the task manager";

    private final Task toAdd;

    //@@author A0142325R
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, String deadline, Set<String> tags, String freq, int priorityLevel) throws Exception{
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        if (deadline != "" && freq != "") {
            this.toAdd = new Task(new Name(name), new Deadline(deadline), new UniqueTagList(tagSet),
                    new Recurring(freq), new Priority(priorityLevel));
        } else if (deadline != "") {
            this.toAdd = new Task(new Name(name), new Deadline(deadline), new UniqueTagList(tagSet), new Priority(priorityLevel));
        } else if(deadline==""&&freq==""){
            this.toAdd = new Task(new Name(name), new UniqueTagList(tagSet), new Priority(priorityLevel));
        }else{
            this.toAdd=null;
            MissingRecurringDateException();
        }
    }

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, String startDate, String endDate, Set<String> tags, String freq, int priorityLevel)
            throws Exception {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        if (!freq.equals("") && !startDate.equals("")) {
            this.toAdd = new Task(new Name(name), new EventDate(startDate, endDate), new UniqueTagList(tagSet),
                    new Recurring(freq), new Priority(priorityLevel));
        } else if (!startDate.equals("")&&freq.equals("")) {
            this.toAdd = new Task(new Name(name), new EventDate(startDate, endDate), new UniqueTagList(tagSet), new Priority(priorityLevel));
        } else{
            MissingRecurringDateException();
            this.toAdd=null;
        }
    }

    @Override
    public CommandResult execute() {
        assert model != null;
  
            model.addTask(toAdd);
            String message = String.format(getSuccessMessage(toAdd), toAdd);
            model.saveState(message);
            return new CommandResult(message);
        
        
    }

    public static String getSuccessMessage(Task toAdd) {
        if (toAdd.isEvent()) {
            return MESSAGE_EVENT_SUCCESS;
        } else {
            return MESSAGE_TASK_SUCCESS;
        }
    }

    private MissingRecurringDateException MissingRecurringDateException() throws MissingRecurringDateException{
            return new MissingRecurringDateException("Recurring task must have a deadline");
    }

}