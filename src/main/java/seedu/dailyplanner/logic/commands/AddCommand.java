package seedu.dailyplanner.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.commons.util.DateUtil;
import seedu.dailyplanner.model.category.Category;
import seedu.dailyplanner.model.category.UniqueCategoryList;
import seedu.dailyplanner.model.task.*;
import seedu.dailyplanner.history.*;

/**
 * Adds a task to the daily planner.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
  //@@author A0139102U
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the daily planner. "
            + "Format: add [TASKNAME] s/[STARTDATE] [STARTTIME] e/[ENDDATE] [ENDTIME] c/CATEGORY...\n" + "Example: "
            + COMMAND_WORD + " CS2103 Assignment s/today 10pm e/11pm c/urgent c/important";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the daily planner";
    public static final String MESSAGE_WARNING_CLASH = "Warning! Current timeslot clashes with the following task: %1$s";
    private List<ReadOnlyTask> taskList;
    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     * 
     * @param endDate
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    
    public AddCommand(String taskName, DateTime start, DateTime end, Set<String> cats) throws IllegalValueException {
        final Set<Category> catSet = new HashSet<>();
        for (String catName : cats) {
            catSet.add(new Category(catName));
        }
        this.toAdd = new Task(taskName, start, end, false, false, new UniqueCategoryList(catSet));

    }
  //@@author A0140124B
    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            taskList = model.getDailyPlanner().getTaskList();
            model.getHistory().stackDeleteInstruction(toAdd);
            model.addTask(toAdd);
            model.updatePinBoard();
            
            int indexOfClashingTask = getIndexOfClashingTask(toAdd);

            if (isClash(indexOfClashingTask))
                return new CommandResult(
                        String.format(MESSAGE_WARNING_CLASH, taskList.get(indexOfClashingTask)));

            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }//@@author

    private boolean isClash(int indexOfClashingTask) {
        return indexOfClashingTask > -1;
    }
  //@@author A0139102U
    /**
     * Returns the index of the task clashing with argument, returns -1 if no
     * clash
     */
    public int getIndexOfClashingTask(Task toCheck) {

        if (!(DateUtil.hasStartandEndTime(toCheck))) {
            return -1;
        }
        Time toAddStartTiming = toCheck.getStart().getTime();
        Time toAddEndTiming = toCheck.getEnd().getTime();

        for (int i = 0; i < taskList.size(); i++) {
            ReadOnlyTask storedTask = taskList.get(i);
            if (DateUtil.hasStartandEndTime(storedTask)) {
                if (notSameTask(toCheck, storedTask)) {
                    if (isSameStartDate(toCheck, storedTask)) {
                        Time tasksEndTiming = storedTask.getEnd().getTime();
                        Time tasksStartTiming = storedTask.getStart().getTime();

                        if (isStartTimeClashing(toAddStartTiming, tasksEndTiming, tasksStartTiming)) {
                            return i;
                        }
                        if (isEndTimeClashing(toAddEndTiming, tasksEndTiming, tasksStartTiming)) {
                            return i;
                        }
                        if (timingSpansEntireTask(toAddStartTiming, toAddEndTiming, tasksEndTiming, tasksStartTiming)) {
                            return i;
                        }
                    }
                }

            }
        }
        return -1;
    }

    private boolean notSameTask(Task toCheck, ReadOnlyTask storedTask) {
        return !(toCheck == storedTask);
    }

    private boolean timingSpansEntireTask(Time toAddStartTiming, Time toAddEndTiming, Time tasksEndTiming,
            Time tasksStartTiming) {
        return ((toAddEndTiming.compareTo(tasksEndTiming) > 0) || (toAddEndTiming.compareTo(tasksEndTiming) == 0))
                && ((toAddStartTiming.compareTo(tasksStartTiming) < 0)
                        || (toAddStartTiming.compareTo(tasksStartTiming) == 0));
    }

    private boolean isEndTimeClashing(Time toAddEndTiming, Time tasksEndTiming, Time tasksStartTiming) {
        return (toAddEndTiming.compareTo(tasksStartTiming) > 0) && (toAddEndTiming.compareTo(tasksEndTiming) < 0);
    }

    private boolean isStartTimeClashing(Time toAddStartTiming, Time tasksEndTiming, Time tasksStartTiming) {
        return (toAddStartTiming.compareTo(tasksEndTiming) < 0) && (toAddStartTiming.compareTo(tasksStartTiming) > 0);
    }

    private boolean isSameStartDate(Task toCheck, ReadOnlyTask storedTask) {
        return toCheck.getStart().getDate().compareTo(storedTask.getStart().getDate()) == 0;
    }

}
