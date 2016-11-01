package seedu.flexitrack.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.model.task.DateTimeInfo;
import seedu.flexitrack.model.task.ReadOnlyTask;
import seedu.flexitrack.model.task.Task;
import seedu.flexitrack.model.task.UniqueTaskList;
import seedu.flexitrack.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.flexitrack.model.task.UniqueTaskList.IllegalEditException;
import seedu.flexitrack.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Wraps all data at the task-tracker level Duplicates are not allowed (by
 * .equals comparison)
 */
public class FlexiTrack implements ReadOnlyFlexiTrack {

    private final UniqueTaskList task;
    private UniqueTaskList blockList = new UniqueTaskList();
    
    {
        task = new UniqueTaskList();
    }

    public FlexiTrack() {
    }

    /**
     * Tasks are copied into this taskstracker
     */
    public FlexiTrack(ReadOnlyFlexiTrack toBeCopied) {
        this(toBeCopied.getUniqueTaskList());
    }

    /**
     * Tasks are copied into this taskstracker
     */
    public FlexiTrack(UniqueTaskList tasks) {
        resetData(tasks.getInternalList());
    }

    public static ReadOnlyFlexiTrack getEmptyFlexiTrack() {
        return new FlexiTrack();
    }

    //// list overwrite operations

    public ObservableList<Task> getTasks() {
        return task.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.task.getInternalList().setAll(tasks);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyFlexiTrack newData) {
        resetData(newData.getTaskList());
    }

    //// task-level operations

    /**
     * Adds a task to the tasks tracker.
     *
     * @throws UniqueTaskList.DuplicateTaskException
     *             if an equivalent task already exists.
     */
    public void addTask(Task p) throws DuplicateTaskException {
        task.add(p);
    }

    //@@author A0127855W
    /**
     * Edits a Task in the tasks tracker.
     * 
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     * @throws TaskNotFoundException if specified task is not found.
     */
    public Task editTask(ReadOnlyTask taskToEdit, String[] args)
            throws IllegalEditException, IllegalValueException {
        return task.edit(taskToEdit, args);
    }
  //@@author

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (task.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    //// util methods

    @Override
    public String toString() {
        return task.getInternalList().size() + " tasks.";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(task.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.task;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FlexiTrack // instanceof handles nulls
                        && this.task.equals(((FlexiTrack) other).task));
    }
    
  //@@author A0127855W
    /**
     * Sorts the flexitrack according to the ReadOnlyTask comparator
     */
    public void sort(){
    	task.sort();
    }
    
  //@@author
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(task);
    }
    
    //@@author A0138455Y
    public Task markTask(ReadOnlyTask targetIndex) throws IllegalValueException {
        return task.mark(targetIndex, Boolean.TRUE);
    }

    public Task unmarkTask(ReadOnlyTask targetIndex) throws IllegalValueException {
        return task.mark(targetIndex, Boolean.FALSE);
    }
    
    public boolean checkBlock(Task toCheck) throws DuplicateTaskException {
        setBlockList();

        if(blockList.getInternalList().size()==0) {
            //System.out.println("block list equal to 0");
            return false;
        }
        for(Task forCheck: blockList) {
            if(compareDate(toCheck,forCheck)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean checkOverlapEvent(Task toCheck) {
        if(task.getInternalList().size()==0) {
            return false;
        }
        for(Task forCheck: task) {
            if(compareDate(toCheck,forCheck)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     * @param toCheck
     * @param blockDate
     * @return ture if 2 event overlapping each other
     */
    private boolean compareDate(Task toCheck, Task blockDate) {
        Date start1 = toCheck.getStartTime().getTimeInfo().getTimingInfo().getDates().get(0);
        Date start2 = blockDate.getStartTime().getTimeInfo().getTimingInfo().getDates().get(0);
        Date end1 = toCheck.getEndTime().getTimeInfo().getTimingInfo().getDates().get(0);
        Date end2 = blockDate.getEndTime().getTimeInfo().getTimingInfo().getDates().get(0);

        if((start1.compareTo(start2)>=0 && start1.compareTo(end2)<=0) || 
                (end1.compareTo(start2)>=0 && end1.compareTo(end2)<=0)) {
            return true;
        }
        return false;
    }
    
    /**
     * return a list of Block task
     * @throws DuplicateTaskException
     */
    private void setBlockList() throws DuplicateTaskException {
        for(Task toAdd: task) {
            if(toAdd.getName().toString().contains("(Blocked) ")) {
                blockList.add(toAdd);
            }
        }
    }

    // @@author A0127686R
    /**
     * Find the next available time slots that has minimum gap as specified by
     * the users. If there are less gap then specified, return the starting time
     * where there are no more events
     * 
     * @param keyword       Represent the keyword in number. 0 represent minute and 4 represent years 
     * @param length        The length of the duration 
     * @param numberOfSlot  The number of slot to be found 
     * @return              The list of dates where gap are available
     */
    public List<DateTimeInfo> findNextAvailableSlots(int keyword, int length, int numberOfSlot) {
        DateTimeInfo dateNow = DateTimeInfo.getCurrentTime();
        List<DateTimeInfo> listOfPossibleTiming = new ArrayList<DateTimeInfo>();

        for (Task task : task.getInternalList()) {

            if (listOfPossibleTiming.size() > (numberOfSlot * 2 - 1)) {
                return listOfPossibleTiming;
            }

            if (canTheGapBeFound(task, dateNow, keyword, length)) {
                listOfPossibleTiming.add(dateNow);
                listOfPossibleTiming.add(task.getStartTime());
                dateNow = task.getEndTime();
            }

            if (DateTimeInfo.isInTheFuture(dateNow, task.getEndTime())) {
                dateNow = task.getEndTime();
            }
        }
        listOfPossibleTiming.add(dateNow);
        return listOfPossibleTiming;
    }

    /**
     * Process the data to see if there is any gap available
     * 
     * @param task          The current task of interest 
     * @param dateNow       The current Date 
     * @param keyword       Represent the keyword in number. 0 represent minute and 4 represent years 
     * @param length        The length of the duration 
     * @return true when there is an available gap
     */
    private boolean canTheGapBeFound(Task task, DateTimeInfo dateNow, int keyword, int length) {
        if (task.getIsEvent() && DateTimeInfo.isInTheFuture(dateNow, task.getStartTime())) {
            return isTheLengthOfTheGapSatisfied(task, dateNow, keyword, length);
        }
        return false;
    }

    /**
     * Process the task data to calculate if there is enough gap between the events
     * 
     * @param task      The current task of interest 
     * @param dateNow   The current date 
     * @param keyword   Represent the keyword in number. 0 represent minute and 4 represent years 
     * @param length    The length of the duration 
     * @return          True if the event starts after the current timing.
     */
    private boolean isTheLengthOfTheGapSatisfied(Task task, DateTimeInfo dateNow, int keyword, int length) {
        int[] differenceInTime = new int[5];
        differenceInTime = DateTimeInfo.durationBetweenTwoTiming(dateNow.toString(), task.getStartingTimeInString());
        if (differenceInTime[0] >= 0) {
            differenceInTime[keyword] = differenceInTime[keyword] - length;
            return doesTheGapAtLeastAsLongAsTimingSpecified(keyword, differenceInTime);
        }
        return false;
    }

    /**
     * Decide if the gap between two timing is long enough to satisfied the user
     * 
     * @param keyword           Represent the keyword in number. 0 represent minute and 4 represent years 
     * @param differenceInTime  The difference in time 
     * @return                  True if the gap is longer than what user specified.
     */
    private boolean doesTheGapAtLeastAsLongAsTimingSpecified(int keyword, int[] differenceInTime) {
        int countNumberOfZero = 0;
        for (int i = keyword; i < 5; i++) {
            if (differenceInTime[i] > 0) {
                return true;
            } else if (differenceInTime[i] == 0) {
                countNumberOfZero = countNumberOfZero + 1;
            }
        }
        return (countNumberOfZero == (5 - keyword)) ? true : false;
    }
    
}
