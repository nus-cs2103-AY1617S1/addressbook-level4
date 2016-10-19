package seedu.malitio.logic.commands;


/**
 * Lists all tasks in Malitio to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    //    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists specified type of task to Malitio\n" +
    //            "Parameters: [events|deadlines|tasks]\n" +
    //            "Example: " + COMMAND_WORD + "deadlines" +
    //            "or " + COMMAND_WORD;

    public static final String ALL_MESSAGE_SUCCESS = "Listed all tasks";
    public static final String TASK_MESSAGE_SUCCESS = "Listed all floating tasks";
    public static final String DEADLINE_MESSAGE_SUCCESS = "Listed all deadlines";
    public static final String EVENT_MESSAGE_SUCCESS = "Listed all events";

    private String taskType = "";

    public ListCommand() {}

    public ListCommand(String taskType) {
        if (taskType.matches("(floating)? tasks?")) {
            this.taskType = "tasks";
        }
        else if (taskType.matches("deadlines?")) {
            this.taskType = "deadlines";
        }
        else if (taskType.matches("events?")) {
            this.taskType = "events";
        }
    }

    @Override
    public CommandResult execute() {
        if (taskType.equals("tasks")) {
            model.updateFilteredTaskListToShowAll();
            return new CommandResult(TASK_MESSAGE_SUCCESS);
        } else if (taskType.equals("deadlines")) {
            model.updateFilteredDeadlineListToShowAll();
            return new CommandResult(DEADLINE_MESSAGE_SUCCESS);
        } else if (taskType.equals("events")) {
            model.updateFilteredEventListToShowAll();
            return new CommandResult(EVENT_MESSAGE_SUCCESS);
        } else {
            model.updateFilteredTaskListToShowAll();
            model.updateFilteredDeadlineListToShowAll();
            model.updateFilteredEventListToShowAll();
            return new CommandResult(ALL_MESSAGE_SUCCESS);
        }
    }
}
