package seedu.forgetmenot.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.forgetmenot.commons.exceptions.IllegalValueException;
import seedu.forgetmenot.model.task.Name;
import seedu.forgetmenot.model.task.ReadOnlyTask;
import seedu.forgetmenot.model.task.Recurrence;
import seedu.forgetmenot.model.task.Task;
import seedu.forgetmenot.model.task.Time;
import seedu.forgetmenot.model.task.UniqueTaskList;
import seedu.forgetmenot.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Wraps all data at the task-manager level Duplicates are not allowed (by
 * .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueTaskList tasks;

    public static int floatingCounter;
    public static int todayCounter;
    public static int tomorrowCounter;
    public static int upcomingCounter;
    public static int overdueCounter;

    {
        tasks = new UniqueTaskList();
    }

    public TaskManager() {
    }

    /**
     * Tasks are copied into this task manager
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this(toBeCopied.getUniqueTaskList());
    }

    /**
     * Tasks are copied into this task manager
     */
    public TaskManager(UniqueTaskList tasks) {
        resetData(tasks.getInternalList());
    }

    public static ReadOnlyTaskManager getEmptyTaskManager() {
        return new TaskManager();
    }

    //// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyTaskManager newData) {
        resetData(newData.getTaskList());
        counter();
    }

    //// task-level operations

    /**
     * Adds a task to the task manager.
     *
     * @throws UniqueTaskList.DuplicateTaskException
     *             if an equivalent task already exists.
     * @@author A0147619W
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        tasks.add(p);
        counter();
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            counter();
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

    // @@author A0147619W
    public void sortTasksList() {
        tasks.sortList();
    }

    // @@author A0139198N
    public void doneTask(ReadOnlyTask task) throws UniqueTaskList.TaskNotFoundException {
        tasks.done(task);
        counter();
    }

    // @@author A0139198N
    public void clearDone() throws UniqueTaskList.TaskNotFoundException {
        for (int i = 0; i < tasks.getInternalList().size(); i++) {
            if (tasks.getInternalList().get(i).getDone().getDoneValue() == true) {
                tasks.remove(tasks.getInternalList().get(i));
                i--;
            }
        }
        counter();
    }

    // @@author A0139198N
    public void undoneTask(ReadOnlyTask task) throws UniqueTaskList.TaskNotFoundException {
        tasks.undone(task);
        counter();
    }

    // @@author A0139671X
    public void editTaskName(ReadOnlyTask task, String newInfo)
            throws UniqueTaskList.TaskNotFoundException, IllegalValueException {
        tasks.editTaskName(task, new Name(newInfo));
        counter();
    }

    // @@author A0139671X
    public void editTaskStartTime(ReadOnlyTask task, String newInfo)
            throws UniqueTaskList.TaskNotFoundException, IllegalValueException {
        tasks.editStartTime(task, new Time(newInfo));
        counter();
    }

    // @@author A0139671X
    public void editTaskEndTime(ReadOnlyTask task, String newInfo)
            throws UniqueTaskList.TaskNotFoundException, IllegalValueException {
        tasks.editEndTime(task, new Time(newInfo));
        counter();

    }

    // @@author A0139671X
    public void editTaskRecurFreq(ReadOnlyTask task, String newRecur) throws TaskNotFoundException {
        tasks.editRecurFreq(task, new Recurrence(newRecur));
        counter();

    }

    //// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, ";
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                        && this.tasks.equals(((TaskManager) other).tasks));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(tasks);
    }

    // @@author A0139198N
    public void counter() {
        int floating = 0;
        int today = 0;
        int tomorrow = 0;
        int upcoming = 0;
        int overdue = 0;

        for (int i = 0; i < tasks.getInternalList().size(); i++) {

            Task toCount = tasks.getInternalList().get(i);

            if (toCount.getStartTime().isMissing() && toCount.getEndTime().isMissing()
                    && toCount.getDone().getDoneValue() == false) {
                floating++;
            }

            if (((toCount.getStartTime().isToday(toCount.getStartTime().appearOnUIFormatForDate()))
                    || toCount.getEndTime().isToday(toCount.getEndTime().appearOnUIFormatForDate()))
                    && toCount.getDone().getDoneValue() == false) {
                today++;
            }

            if ((toCount.getStartTime().isTomorrow(toCount.getStartTime().appearOnUIFormatForDate())
                    || toCount.getEndTime().isTomorrow(toCount.getEndTime().appearOnUIFormatForDate()))
                    && toCount.getDone().getDoneValue() == false) {
                tomorrow++;
            }

            if ((toCount.getStartTime().isUpcoming(toCount.getStartTime().appearOnUIFormatForDate())
                    || toCount.getEndTime().isUpcoming(toCount.getEndTime().appearOnUIFormatForDate()))
                    && toCount.getDone().getDoneValue() == false) {
                upcoming++;
            }

            if ((toCount.checkOverdue()) && !toCount.getDone().getDoneValue())
                overdue++;
        }
        floatingCounter = floating;
        todayCounter = today;
        tomorrowCounter = tomorrow;
        upcomingCounter = upcoming;
        overdueCounter = overdue;
    }

}
