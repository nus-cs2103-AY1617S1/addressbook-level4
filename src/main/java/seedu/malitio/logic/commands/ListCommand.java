package seedu.malitio.logic.commands;

import seedu.malitio.commons.core.UnmodifiableObservableList;
import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.task.*;

/**
 * Lists all tasks in Malitio to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists specified type of task to Malitio\n" +
            "Parameters: [events|deadlines|tasks] [DATETIME]\n" +
            "Example: " + COMMAND_WORD + " deadlines " + "22102016 0000";

    public static final String ALL_MESSAGE_SUCCESS = "Listed all tasks";
    public static final String TASK_MESSAGE_SUCCESS = "Listed floating tasks";
    public static final String DEADLINE_MESSAGE_SUCCESS = "Listed deadlines";
    public static final String EVENT_MESSAGE_SUCCESS = "Listed events";

    private String taskType = "";
    private DateTime timeKeyWord = null;

    public ListCommand() {}

    public ListCommand(String args) throws IllegalValueException {
        if (args.matches("(floating)? tasks?")) {
            this.taskType = "tasks";
            return;
        }
        else if (args.matches("deadlines?.*")) {
            this.taskType = "deadlines";
            args = args.replaceAll("deadlines?\\s*", "");
        }
        else if (args.matches("events?.*")) {
            this.taskType = "events";
            args = args.replaceAll("events?\\s*", "");
        }
        if (!args.isEmpty()) {
            timeKeyWord = new DateTime(args);
        }
    }

    @Override
    public CommandResult execute() {
        if (taskType.equals("tasks")) {
            model.updateFilteredTaskListToShowAll();
            return new CommandResult(TASK_MESSAGE_SUCCESS);
        } else if (taskType.equals("deadlines")) {
            if (timeKeyWord != null) {
                model.updateFilteredDeadlineList(timeKeyWord);
            } else {
                model.updateFilteredDeadlineListToShowAll();
            }
            return new CommandResult(DEADLINE_MESSAGE_SUCCESS);
        } else if (taskType.equals("events")) {
            if (timeKeyWord != null) {
                model.updateFilteredEventList(timeKeyWord);
            } else { 
                model.updateFilteredEventListToShowAll();
            }
            return new CommandResult(EVENT_MESSAGE_SUCCESS);
        } else {
            if (timeKeyWord != null) {
                model.updateFilteredDeadlineList(timeKeyWord);
                model.updateFilteredEventList(timeKeyWord);
            } else {
                model.updateFilteredTaskListToShowAll();
                model.updateFilteredDeadlineListToShowAll();
                model.updateFilteredEventListToShowAll();
            }
            return new CommandResult(ALL_MESSAGE_SUCCESS);
        }
    }
}
