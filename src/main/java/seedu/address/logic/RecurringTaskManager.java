package seedu.address.logic;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.util.RecurringTaskUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskOccurrence;
import seedu.address.model.task.UniqueTaskList;

//@@author A0135782Y
/**
 * Handles the behavior of recurring tasks Dictates when should the recurring
 * tasks be shown.
 * This class is using a singleton pattern. 
 * Use RecurringTaskManager.getInstance() to get the instance of the class
 */
public class RecurringTaskManager {
    private static final int NO_MORE_RECURRING_PERIOD = 0;
    private static RecurringTaskManager instance;
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private UniqueTaskList repeatingTasks;
    private boolean isTestMode;

    public void setTaskList(UniqueTaskList referenceList) {
        assert referenceList != null : "Reference Task list cannot be null";
        logger.fine("Initializing with RecurringTaskManager to manage: " + referenceList.toString());
        repeatingTasks = referenceList;
        isTestMode = false;
    }

    /**
     * Appends any recurring tasks with task occurrences based on their recurring type.
     * 
     * @return True if any task has been updated.
     */
    public boolean appendAnyRecurringTasks() {
        return appendAnyRecurringTasks(LocalDate.now());
    }

    /**
     * Test method to test appendAnyRecurringTasks is working as intended.
     * 
     * @param currentDate The date we want to update towards.
     * @return True if any task has been updated.
     */
    public boolean appendAnyRecurringTasks(LocalDate currentDate) {
        assert repeatingTasks != null : "Repeating Task list reference cannot be null";
        logger.info("=============================[ RecurringTaskManager Updating ]===========================");
        boolean hasUpdated = false;
        for (ReadOnlyTask task : repeatingTasks) {
            if (task.getRecurringType().equals(RecurringType.NONE)) {
                continue;
            }
            attemptAppendRecurringTask(task, currentDate);
            hasUpdated = true;
        }
        return hasUpdated;
    }    
    
    /**
     * Attempts to append a recurring task occurrence to the task if it is possible
     * 
     * @param task Recurring Task to be considered, cannot be null
     * @param currentDate The current date as aLocalDate object.
     */
    public void attemptAppendRecurringTask(ReadOnlyTask task, LocalDate currentDate) {
        TaskOccurrence lastAddedComponent = task.getLastAppendedComponent();
        Calendar startDate = RecurringTaskUtil.getStartCalendar(lastAddedComponent);
        Calendar endDate = RecurringTaskUtil.getEndCalendar(lastAddedComponent);        
        attemptAppendRecurringTask((Task) task, startDate, endDate, currentDate);        
    }

    /**
     * Helper method to append a recurring task occurrence to the task if it is possible
     * Appends a recurring task if there is an elapsed period.
     * 
     * @param task Recurring Task to be considered, cannot be null
     * @param currentDate The current date as aLocalDate object.
     */    
    private void attemptAppendRecurringTask(Task task, Calendar startDate, Calendar endDate, LocalDate localDateCurrently) {
        LocalDate startDateInLocalDate = RecurringTaskUtil.getStartLocalDate(task.getLastAppendedComponent());
        LocalDate endDateInLocalDate = RecurringTaskUtil.getEndLocalDate(task.getLastAppendedComponent());
        final int elapsedPeriod = RecurringTaskUtil.getElapsedPeriodToAppend(localDateCurrently, startDateInLocalDate, endDateInLocalDate, task.getRecurringType());
        for (int i = 0; i < elapsedPeriod; i++) {
            if (handleRecurringPeriod(task)) {
                break;
            }
            appendRecurringTask(task, startDate, endDate);
        }        
    }    
    
    /**
     * Appends the recurring task with a recurring task occurrence.
     * Mutates the repeatingTasks list with the new task occurrence that is appended.
     * 
     * @param task Task which we are appending a new task occurrence to.
     * @param startDate Start date of the last appended task occurrence
     * @param endDate End date of the last appended task occurence
     */
    private void appendRecurringTask(Task task, Calendar startDate, Calendar endDate) {
        RecurringTaskUtil.appendRecurringTask(task, startDate, endDate, repeatingTasks, task.getRecurringType());
    }
    
    /**
     * Corrects the recurring task that are overdued.
     * Only recurring tasks with no recurring period will be corrected.
     * 
     * @param task Recurring task with no stated recurring period.
     */
    public void correctOverdueNonRepeatingTasks(Task task) {
        assert task != null : "task that needs correcting cannot be null!";
        correctAddingOverdueTasks(task, LocalDate.now());
    }

    /**
     * Test method for correcting of overdued recurring tasks with no stated
     * recurring period.
     * 
     * @param task Task to be correct, cannot be null
     * @param currentDate LocalDate that we are correcting towards, cannot be null
     */
    public void correctAddingOverdueTasks(Task task, LocalDate currentDate) {
        assert !CollectionUtil.isAnyNull(task, currentDate);
        if (task.getRecurringType().equals(RecurringType.NONE) 
                || task.getRecurringPeriod() != Task.NO_RECURRING_PERIOD) {
            return;
        }
        LocalDate localDateCurrently = currentDate;
        LocalDate startDateInLocalDate = RecurringTaskUtil.getStartLocalDate(task.getLastAppendedComponent());
        LocalDate endDateInLocalDate = RecurringTaskUtil.getEndLocalDate(task.getLastAppendedComponent());
        attemptCorrectRecurringTask(task, localDateCurrently, startDateInLocalDate, endDateInLocalDate);
    }
    
    /**
     * Attempts to correct recurring tasks without recurring period
     * Corrects the recurring tasks to the next possible date.
     * 
     * @param task Task must be recurring and not have a recurring period.
     * @param localDateCurrently The date currently as a LocalDate form.
     * @param startDateInLocalDate The start date of the task occurrence as a LocalDate object.
     * @param endDateInLocalDate The end date of the task occurrence as a LocalDate object.
     * @param type The current recurring type to correct to.
     */
    private void attemptCorrectRecurringTask(Task task, LocalDate localDateCurrently,
            LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedPeriod = RecurringTaskUtil.getElapsedPeriodToCorrect(localDateCurrently, startDateInLocalDate, 
                endDateInLocalDate, task.getRecurringType());
        if (elapsedPeriod > 0) {
            RecurringTaskUtil.correctRecurringTask(task, elapsedPeriod, task.getRecurringType());
        }        
    }

    /**
     * Helper method to handle the recurring period of a recurring task.
     * 
     * @param task Recurring task with or without a recurring period.
     * @return True if there is no longer any recurring period left
     */
    private boolean handleRecurringPeriod(Task task) {
        if (task.getRecurringPeriod() == NO_MORE_RECURRING_PERIOD) {
            return true;   
        }
        task.decrementRecurringPeriod();
        return false;
    }
    
    /**
     * Update recurring tasks after it has been updated.
     * Recurring tasks with recurring period decremented to zero will be ignored.
     * Recurring tasks without recurring period will be updated to the next available slot.
     * 
     * @param target Task Occurrence of the task that we have just archived
     */
    public void updateRecurringTasks(TaskOccurrence target) {
        Task taskDesc = (Task) target.getTaskReference();
        if (taskDesc.getRecurringType().equals(RecurringType.NONE)
                || taskDesc.getRecurringPeriod() == NO_MORE_RECURRING_PERIOD) {
            return;
        }
        Calendar startDate = RecurringTaskUtil.getStartCalendar(target);
        Calendar endDate = RecurringTaskUtil.getEndCalendar(target);
        appendRecurringTask(taskDesc, startDate, endDate);
    }
    
    /**
     * Appends recurring tasks based on its recurring period.
     * A recurring tasks with x recurring period will be appended with x-1 additional task occurrences.
     * 
     * @param task Recurring task with recurring period.
     */
    private void appendRecurringTaskWithPeriod(Task task) {
        TaskOccurrence occurrence = task.getLastAppendedComponent();
        if (task.getRecurringType().equals(RecurringType.NONE)
                || task.getRecurringPeriod() == Task.NO_RECURRING_PERIOD) {
            return;
        }
        appendMultipleTaskOccurences(task);
    }

    /**
     * Appends multiple task occurrences for recurring task with recurrinc period.
     * 
     * @param taskDesc Recurring tasks with recurring period.
     */
    private void appendMultipleTaskOccurences(Task taskDesc) {
        TaskOccurrence target = taskDesc.getLastAppendedComponent();
        Calendar startDate = RecurringTaskUtil.getStartCalendar(target);
        Calendar endDate = RecurringTaskUtil.getEndCalendar(target );
        while(taskDesc.getRecurringPeriod() != NO_MORE_RECURRING_PERIOD) {
            appendRecurringTask((Task) taskDesc, startDate, endDate);
            if (taskDesc.getRecurringPeriod()  == Task.NO_RECURRING_PERIOD) {
                return;
            }
            (taskDesc).decrementRecurringPeriod();
            target = taskDesc.getLastAppendedComponent();
            if (startDate != null) {
                startDate.setTime(target.getStartDate().getDate());
            }
            endDate.setTime(target.getEndDate().getDate());
        } 
    }

    /**
     * Handles the adding of a task in ModelManager
     * 
     * @param task A Task does not need to be recurring
     */
    public void addTask(Task task) {
        if (!isTestMode) {
            correctOverdueNonRepeatingTasks(task);
        }
        appendRecurringTaskWithPeriod(task);        
    }
    
    /**
     * Set the mode of recurring manager. 
     * If isTestMode set to true, recurring manager will not correct recurring tasks for testing purpose.
     * 
     * @param isTestMode If recurring task manager is being tested
     */
    public void setTestMode(boolean isTestMode) {
        this.isTestMode = isTestMode;
    }
    
    public static RecurringTaskManager getInstance() {
        if (instance == null) {
            instance = new RecurringTaskManager();
        }
        return instance;
    }
}
