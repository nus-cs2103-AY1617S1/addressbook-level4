package seedu.agendum.logic.commands;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.agendum.commons.core.Messages;
import seedu.agendum.commons.core.UnmodifiableObservableList;
import seedu.agendum.logic.parser.DateTimeUtils;
import seedu.agendum.model.task.*;
import seedu.agendum.model.task.UniqueTaskList.TaskNotFoundException;


/**
 * Reschedules a task in the to do list.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";
    public static final String COMMAND_FORMAT = "schedule <id>\n"
                                              + "schedule <id> by <deadline>\n"
                                              + "schedule <id> from <start-time> to <end-time>";
    public static final String COMMAND_DESCRIPTION = "update the time of a task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " - "
            + COMMAND_DESCRIPTION + "\n"
            + COMMAND_FORMAT + "\n"
            + "(The id must be a positive number)\n"
            + "Example: " + COMMAND_WORD + " 2 from 7am to 9am";

    public static final String MESSAGE_SUCCESS = "Task rescheduled: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists!";

    public int targetIndex = -1;
    private Optional<LocalDateTime> newStartDateTime = Optional.empty();
    private Optional<LocalDateTime> newEndDateTime = Optional.empty();

    //@@author A0133367E
    public ScheduleCommand(int targetIndex, Optional<LocalDateTime> startTime,
            Optional<LocalDateTime> endTime) {
        Optional<LocalDateTime> balancedEndTime = endTime;
        if (startTime.isPresent() && endTime.isPresent()) {
            balancedEndTime = Optional.of(DateTimeUtils.balanceStartAndEndDateTime(startTime.get(), endTime.get()));
        }
        this.targetIndex = targetIndex;
        this.newStartDateTime = startTime;
        this.newEndDateTime = balancedEndTime;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToSchedule = lastShownList.get(targetIndex - 1);

        Task updatedTask = new Task(taskToSchedule);
        updatedTask.setStartDateTime(newStartDateTime);
        updatedTask.setEndDateTime(newEndDateTime);

        try {
            model.updateTask(taskToSchedule, updatedTask);         
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedTask));
    }

    //@author
    public static String getName() {
        return COMMAND_WORD;
    }
        
    public static String getFormat() {
        return COMMAND_FORMAT;
    }
        
    public static String getDescription() {
        return COMMAND_DESCRIPTION;
    }

}
