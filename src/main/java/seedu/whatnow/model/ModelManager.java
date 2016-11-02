//@@author A0139772U
package seedu.whatnow.model;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.whatnow.commons.core.ComponentManager;
import seedu.whatnow.commons.core.Config;
import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.core.UnmodifiableObservableList;
import seedu.whatnow.commons.events.model.AddTaskEvent;
import seedu.whatnow.commons.events.model.ConfigChangedEvent;
import seedu.whatnow.commons.events.model.UpdateTaskEvent;
import seedu.whatnow.commons.events.model.WhatNowChangedEvent;
import seedu.whatnow.commons.exceptions.DataConversionException;
import seedu.whatnow.commons.util.StringUtil;
import seedu.whatnow.logic.commands.Command;
import seedu.whatnow.model.freetime.FreePeriod;
import seedu.whatnow.model.freetime.Period;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.Task;
import seedu.whatnow.model.task.UniqueTaskList;
import seedu.whatnow.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;

import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the WhatNow data. All changes to any model
 * should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static final String TASK_TYPE_FLOATING = "floating";
    private static final String TASK_TYPE_NOT_FLOATING = "not_floating";
    private static final String TASK_STATUS_COMPLETED = "completed";
    private static final String TASK_STATUS_INCOMPLETE = "incomplete";
    private static final String DEFAULT_START_TIME = "12:00am";
    private static final String DEFAULT_END_TIME = "11:59pm";

    private final WhatNow whatNow;
    private final FilteredList<Task> filteredTasks;
    private FilteredList<Task> filteredSchedules;
    private final Stack<String> stackOfUndo;
    private final Stack<String> stackOfRedo;
    private final Stack<ReadOnlyTask> stackOfOldTask;
    private final Stack<ReadOnlyTask> stackOfNewTask;
    private final Stack<ReadOnlyWhatNow> stackOfWhatNow;
    private final Stack<ReadOnlyTask> stackOfDeletedTasks;
    private final Stack<Integer> stackOfDeletedTaskIndex;
    private final Stack<ReadOnlyTask> stackOfDeletedTasksRedo;
    private final Stack<Integer> stackOfDeletedTaskIndexRedo;
    private final Stack<ReadOnlyTask> stackOfDeletedTasksAdd;
    private final Stack<ReadOnlyTask> stackOfDeletedTasksAddRedo;
    private final Stack<ReadOnlyTask> stackOfMarkDone;
    private final Stack<ReadOnlyTask> stackOfMarkDoneRedo;
    private final Stack<ReadOnlyTask> stackOfMarkUndone;
    private final Stack<ReadOnlyTask> stackOfMarkUndoneRedo;
    private final Stack<String> stackOfListTypes;
    private final Stack<String> stackOfListTypesRedo;
    private final HashMap<String, FreePeriod> freeTimes;

    // @@author A0139128A
    /**
     * Initializes a ModelManager with the given WhatNow WhatNow and its
     * variables should not be null
     */
    public ModelManager(WhatNow src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with WhatNow: " + src + " and user prefs " + userPrefs);

        whatNow = new WhatNow(src);
        new Config();
        filteredTasks = new FilteredList<>(whatNow.getTasks());
        filteredSchedules = new FilteredList<>(whatNow.getTasks());
        stackOfUndo = new Stack<>();
        stackOfRedo = new Stack<>();
        stackOfOldTask = new Stack<>();
        stackOfNewTask = new Stack<>();
        stackOfWhatNow = new Stack<>();
        stackOfDeletedTasks = new Stack<>();
        stackOfDeletedTaskIndex = new Stack<>();
        stackOfDeletedTasksRedo = new Stack<>();
        stackOfDeletedTaskIndexRedo = new Stack<>();
        stackOfDeletedTasksAdd = new Stack<>();
        stackOfDeletedTasksAddRedo = new Stack<>();
        stackOfMarkDone = new Stack<>();
        stackOfMarkDoneRedo = new Stack<>();
        stackOfMarkUndone = new Stack<>();
        stackOfMarkUndoneRedo = new Stack<>();
        stackOfListTypes = new Stack<>();
        stackOfListTypesRedo = new Stack<>();
        freeTimes = new HashMap<String, FreePeriod>();
        initialiseFreeTime();
    }

    // @@author A0141021H-reused
    public ModelManager() {
        this(new WhatNow(), new UserPrefs());
    }

    // @@author A0139128A
    public ModelManager(ReadOnlyWhatNow initialData, UserPrefs userPrefs) {
        whatNow = new WhatNow(initialData);
        new Config();
        filteredTasks = new FilteredList<>(whatNow.getTasks());
        filteredSchedules = new FilteredList<>(whatNow.getTasks());
        stackOfUndo = new Stack<>();
        stackOfRedo = new Stack<>();
        stackOfOldTask = new Stack<>();
        stackOfNewTask = new Stack<>();
        stackOfWhatNow = new Stack<>();
        stackOfDeletedTasks = new Stack<>();
        stackOfDeletedTaskIndex = new Stack<>();
        stackOfDeletedTasksRedo = new Stack<>();
        stackOfDeletedTaskIndexRedo = new Stack<>();
        stackOfDeletedTasksAdd = new Stack<>();
        stackOfDeletedTasksAddRedo = new Stack<>();
        stackOfMarkDone = new Stack<>();
        stackOfMarkDoneRedo = new Stack<>();
        stackOfMarkUndone = new Stack<>();
        stackOfMarkUndoneRedo = new Stack<>();
        stackOfListTypes = new Stack<>();
        stackOfListTypesRedo = new Stack<>();
        freeTimes = new HashMap<String, FreePeriod>();
        initialiseFreeTime();
    }

    // @@author A0139772U
    private void initialiseFreeTime() {
        for (int i = 0; i < filteredSchedules.size(); i++) {
            blockFreeTime(filteredSchedules.get(i));
        }
    }

    // @@author A0139128A
    @Override
    public void resetData(ReadOnlyWhatNow newData) {
        stackOfWhatNow.push(new WhatNow(whatNow));
        whatNow.resetData(newData);
        initialiseFreeTime();
        indicateWhatNowChanged();
    }

    @Override
    public synchronized void revertData() {
        whatNow.revertEmptyWhatNow(stackOfWhatNow.pop());
        indicateWhatNowChanged();
    }

    // @@author A0139128A-reused
    @Override
    public ReadOnlyWhatNow getWhatNow() {
        return whatNow;
    }

    // @@author A0139772U-reused
    /** Raises an event to indicate the model has changed */
    private void indicateWhatNowChanged() {
        raise(new WhatNowChangedEvent(whatNow));
    }

    // @@author A0141021H
    /** Raises an event to indicate the config has changed */
    private void indicateConfigChanged(Path destination, Config config) {
        raise(new ConfigChangedEvent(destination, config));
    }

    // @@author A0141021H-reused
    /** Raises an event to indicate that a task was added */
    private void indicateAddTask(Task task, boolean isUndo) {
        raise(new AddTaskEvent(task, isUndo));
    }

    // @@author A0141021H-reused
    /** Raises an event to indicate that a task was updated */
    private void indicateUpdateTask(Task task) {
        raise(new UpdateTaskEvent(task));
    }

    // @@author A0141021H
    @Override
    public synchronized void changeLocation(Path destination, Config config)
            throws DataConversionException, IOException, TaskNotFoundException {
        indicateConfigChanged(destination, config);
        indicateWhatNowChanged();
    }

    // @@author A0139128A-reused
    @Override
    public synchronized int deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        int indexRemoved = whatNow.removeTask(target);
        unblockFreeTime();
        indicateWhatNowChanged();
        return indexRemoved;
    }

    // @@author A0126240W-reused
    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        whatNow.addTask(task);
        blockFreeTime(task);
        updateFilteredListToShowAllIncomplete();
        indicateAddTask(task, false);
        indicateWhatNowChanged();
    }

    // @@author A0139128A
    public synchronized void addTaskSpecific(Task task, int idx) throws UniqueTaskList.DuplicateTaskException {
        whatNow.addTaskSpecific(task, idx);
        updateFilteredListToShowAllIncomplete();
        indicateAddTask(task, true);
        indicateWhatNowChanged();
    }

    // @@author A0126240W
    @Override
    public synchronized void updateTask(ReadOnlyTask old, Task toUpdate)
            throws TaskNotFoundException, DuplicateTaskException {
        whatNow.updateTask(old, toUpdate);
        indicateUpdateTask(toUpdate);
        unblockFreeTime();
        indicateWhatNowChanged();
    }

    // @@author A0139772U
    @Override
    public synchronized void markTask(ReadOnlyTask target) throws TaskNotFoundException {
        whatNow.markTask(target);
        indicateWhatNowChanged();
    }

    // @@author A0141021H
    @Override
    public synchronized void unMarkTask(ReadOnlyTask target) throws TaskNotFoundException {
        whatNow.unMarkTask(target);
        indicateWhatNowChanged();
    }

    // @@author A0139128A
    @Override
    public Stack<String> getUndoStack() {
        return stackOfUndo;
    }

    // @@author A0139128A
    @Override
    public Stack<String> getRedoStack() {
        return stackOfRedo;
    }

    // @@author A0139128A
    @Override
    public Stack<ReadOnlyTask> getOldTask() {
        return stackOfOldTask;
    }

    // @@author A0139128A
    @Override
    public Stack<ReadOnlyTask> getNewTask() {
        return stackOfNewTask;
    }

    // @@author A0139772U
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getAllTaskTypeList() {
        filteredTasks.setPredicate(null);
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    // @@author A0139128A
    @Override
    public Stack<ReadOnlyTask> getDeletedStackOfTasks() {
        return stackOfDeletedTasks;
    }

    // @@author A0139128A
    public Stack<Integer> getDeletedStackOfTasksIndex() {
        return stackOfDeletedTaskIndex;
    }

    // @@author A0139128A
    public Stack<Integer> getDeletedStackOfTasksIndexRedo() {
        return stackOfDeletedTaskIndexRedo;
    }

    // @@author A0139128A
    @Override
    public Stack<ReadOnlyTask> getDeletedStackOfTasksRedo() {
        return stackOfDeletedTasksRedo;
    }

    // @@author A0139128A
    @Override
    public Stack<ReadOnlyTask> getDeletedStackOfTasksAdd() {
        return stackOfDeletedTasksAdd;
    }

    // @@author A0139128A
    @Override
    public Stack<ReadOnlyTask> getDeletedStackOfTasksAddRedo() {
        return stackOfDeletedTasksAddRedo;
    }

    // @@author A0139128A
    @Override
    public Stack<ReadOnlyTask> getStackOfMarkDoneTask() {
        return stackOfMarkDone;
    }

    // @@author A0139128A
    @Override
    public Stack<ReadOnlyTask> getStackOfMarkDoneTaskRedo() {
        return stackOfMarkDoneRedo;
    }

    // @@author A0141021H
    @Override
    public Stack<ReadOnlyTask> getStackOfMarkUndoneTask() {
        return stackOfMarkUndone;
    }

    // @@author A0139128A
    public Stack<ReadOnlyTask> getStackOfMarkUndoneTaskRedo() {
        return stackOfMarkUndoneRedo;
    }

    // @@author A0139128A
    @Override
    public Stack<String> getStackOfListTypes() {
        return stackOfListTypes;
    }

    // @@author A0139128A
    @Override
    public Stack<String> getStackOfListTypesRedo() {
        return stackOfListTypesRedo;
    }

    // @@author A0139772U
    @Override
    public FreePeriod getFreeTime(String date) {
        if (freeTimes.get(date) == null) {
            freeTimes.put(date, new FreePeriod());
        }
        freeTimes.get(date).getList().sort(new Period());
        return freeTimes.get(date);
    }

    // @@author A0139772U
    /**
     * Remove from the freetime block the period that coincides with the task
     * duration
     */
    private void blockFreeTime(Task task) {
        String date = task.getTaskDate();
        String startDate = task.getStartDate();
        String endDate = task.getEndDate();
        String startTime = task.getStartTime();
        String endTime = task.getEndTime();
        if (date != null && startTime != null && endTime != null) {
            blockTaskWithOneDateTwoTime(date, startTime, endTime);
        } else if (date == null && startTime != null && endTime != null) {
            blockTaskWithTwoDateTwoTime(startDate, endDate, startTime, endTime);
        }
    }

    /**
     * Remove from the freetime block the period that coincides with the task
     * duration, for task with one date and two time
     */
    private void blockTaskWithOneDateTwoTime(String date, String startTime, String endTime) {
        if (freeTimes.get(date) == null) {
            FreePeriod newFreePeriod = new FreePeriod();
            newFreePeriod.block(startTime, endTime);
            freeTimes.put(date, newFreePeriod);
        } else {
            freeTimes.get(date).block(startTime, endTime);
        }
    }

    /**
     * Remove from the freetime block the period that coincides with the task
     * duration, for task with two date and two time
     */
    private void blockTaskWithTwoDateTwoTime(String startDate, String endDate, String startTime, String endTime) {
        if (freeTimes.get(startDate) == null) {
            FreePeriod newFreePeriod = new FreePeriod();
            newFreePeriod.block(startTime, DEFAULT_END_TIME);
            freeTimes.put(startDate, newFreePeriod);
        } else {
            freeTimes.get(startDate).block(startTime, DEFAULT_END_TIME);
        }
        if (freeTimes.get(endDate) == null) {
            FreePeriod newFreePeriod = new FreePeriod();
            newFreePeriod.block(DEFAULT_START_TIME, endTime);
            freeTimes.put(endDate, newFreePeriod);
        } else {
            freeTimes.get(endDate).block(DEFAULT_START_TIME, endTime);
        }
        blockDatesInBetween(startDate, endDate);
    }

    /**
     * Remove from the freetime block the period that coincides with the task
     * duration, between startdate and end date
     */
    private void blockDatesInBetween(String start, String end) {
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date startDate = df.parse(start);
            Date endDate = df.parse(end);
            cal.setTime(startDate);
            while (cal.getTime().before(endDate)) {
                cal.add(Calendar.DATE, 1);
                if (cal.getTime().equals(endDate)) {
                    break;
                }
                if (freeTimes.get(cal.getTime()) == null) {
                    FreePeriod newFreePeriod = new FreePeriod();
                    newFreePeriod.getList().clear();
                    freeTimes.put(df.format(cal.getTime()), newFreePeriod);
                } else {
                    freeTimes.get(cal.getTime()).getList().clear();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void unblockFreeTime() {
        freeTimes.clear();
        initialiseFreeTime();
    }

    // =========== Filtered Task List Accessors
    // ===============================================================
    // @@author A0139772U
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        updateFilteredListToShowAllIncomplete();
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getCurrentFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList(Set<String> keyword) {
        updateFilteredTaskList(keyword);
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        String[] taskType = { TASK_TYPE_FLOATING };
        Set<String> keyword = new HashSet<>(Arrays.asList(taskType));
        FXCollections.sort(filteredTasks.getSource());
        updateFilteredTaskList(new PredicateExpression(new TaskTypeQualifier(keyword)));
    }

    @Override
    public void updateFilteredListToShowAllIncomplete() {
        FXCollections.sort(filteredTasks.getSource());
        filteredTasks.setPredicate(p -> {
            if ((p.getTaskType().equals((TASK_TYPE_FLOATING)) && (p.getStatus().equals(TASK_STATUS_INCOMPLETE)))) {
                return true;
            } else {
                return false;
            }
        });
    }

    @Override
    public void updateFilteredListToShowAllCompleted() {
        FXCollections.sort(filteredTasks.getSource());
        filteredTasks.setPredicate(p -> {
            if ((p.getTaskType().equals((TASK_TYPE_FLOATING)) && (p.getStatus().equals(TASK_STATUS_COMPLETED)))) {
                return true;
            } else {
                return false;
            }
        });
    }

    @Override
    public void updateFilteredListToShowAllByStatus(Set<String> keyword) {
        updateFilteredTaskList(new PredicateExpression(new TaskStatusQualifier(keyword)));
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        filteredTasks.setPredicate(p -> {
            if ((keywords.stream().filter(key -> StringUtil.containsIgnoreCase(p.getName().fullName, key)).findAny()
                    .isPresent()) && p.getTaskType().equals(TASK_TYPE_FLOATING)) {
                return true;
            } else {
                return false;
            }
        });
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    // =========== Filtered Schedule List Accessors
    // ===============================================================
    // @@author A0139772U
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredScheduleList(boolean isUndo) {
        if (!isUndo) {
            updateFilteredScheduleListToShowAllIncomplete();
        }
        
        return new UnmodifiableObservableList<>(filteredSchedules);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getCurrentFilteredScheduleList() {
        return new UnmodifiableObservableList<>(filteredSchedules);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredScheduleList(Set<String> keyword) {
        updateFilteredScheduleList(keyword);
        return new UnmodifiableObservableList<>(filteredSchedules);
    }

    @Override
    public void updateFilteredScheduleListToShowAll() {
        String[] taskType = { TASK_TYPE_NOT_FLOATING };
        Set<String> keyword = new HashSet<>(Arrays.asList(taskType));
        FXCollections.sort(filteredSchedules.getSource());
        updateFilteredScheduleList(new PredicateExpression(new TaskTypeQualifier(keyword)));
    }

    @Override
    public void updateFilteredScheduleListToShowAllIncomplete() {
        FXCollections.sort(filteredSchedules.getSource());
        filteredSchedules.setPredicate(p -> {
            if ((p.getTaskType().equals((TASK_TYPE_NOT_FLOATING)) && (p.getStatus().equals(TASK_STATUS_INCOMPLETE)))) {
                return true;
            } else {
                return false;
            }
        });
    }

    @Override
    public void updateFilteredScheduleListToShowAllCompleted() {
        FXCollections.sort(filteredSchedules.getSource());
        filteredSchedules.setPredicate(p -> {
            if ((p.getTaskType().equals((TASK_TYPE_NOT_FLOATING)) && (p.getStatus().equals(TASK_STATUS_COMPLETED)))) {
                return true;
            } else {
                return false;
            }
        });
    }

    @Override
    public void updateFilteredScheduleListToShowAllByStatus(Set<String> keyword) {
        updateFilteredScheduleList(new PredicateExpression(new TaskStatusQualifier(keyword)));
    }

    @Override
    public void updateFilteredScheduleList(Set<String> keywords) {
        filteredSchedules.setPredicate(p -> {
            if ((keywords.stream().filter(key -> StringUtil.containsIgnoreCase(p.getName().fullName, key)).findAny()
                    .isPresent()) && p.getTaskType().equals(TASK_TYPE_NOT_FLOATING)) {
                return true;
            } else {
                return false;
            }
        });
    }

    private void updateFilteredScheduleList(Expression expression) {
        filteredSchedules.setPredicate(expression::satisfies);
    }

    // ========== Inner classes/interfaces used for filtering
    // ==================================================
    // @@author A0141021H
    interface Expression {
        boolean satisfies(ReadOnlyTask task);

        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);

        String toString();
    }

    // @@author A0139772U
    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword)).findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    // @@author A0139772U
    private class TaskStatusQualifier implements Qualifier {
        private Set<String> status;

        TaskStatusQualifier(Set<String> status) {
            this.status = status;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return status.stream().filter(keyword -> StringUtil.containsIgnoreCase(task.getStatus(), keyword)).findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "Status=" + String.join(", ", status);
        }
    }

    // @@author A0139772U
    private class TaskTypeQualifier implements Qualifier {
        private Set<String> taskType;

        TaskTypeQualifier(Set<String> taskType) {
            this.taskType = taskType;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return taskType.stream().filter(keyword -> StringUtil.containsIgnoreCase(task.getTaskType(), keyword))
                    .findAny().isPresent();
        }

        @Override
        public String toString() {
            return "TaskType=" + String.join(", ", taskType);
        }
    }
}