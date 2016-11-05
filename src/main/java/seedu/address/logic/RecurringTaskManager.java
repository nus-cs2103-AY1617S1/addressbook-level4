package seedu.address.logic;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.util.DateFormatterUtil;
import seedu.address.logic.util.RecurringTaskUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskOccurrence;
import seedu.address.model.task.UniqueTaskList;

//@@author A0135782Y
/**
 * Handles the behaviour of recurring tasks Dictates when should the recurring
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
     * Attempts to append a recurring task occurrence if 
     * 
     * @param task
     * @param currentDate
     */
    public void attemptAppendRecurringTask(ReadOnlyTask task, LocalDate currentDate) {
        Calendar startDate = new GregorianCalendar();
        Calendar endDate = new GregorianCalendar();

        TaskOccurrence lastAddedComponent = task.getLastAppendedComponent();
        startDate.setTime(lastAddedComponent.getStartDate().getDate());
        endDate.setTime(lastAddedComponent.getEndDate().getDate());

        if (!lastAddedComponent.getStartDate().isValid()) {
            startDate = null;
        }
        attemptAppendRecurringTask(task, startDate, endDate, currentDate);        
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
        LocalDate startDateInLocalDate = null;
        if (!task.getLastAppendedComponent().hasOnlyEndDate()) {
            startDateInLocalDate = DateFormatterUtil.dateToLocalDate(task.getLastAppendedComponent().getStartDate().getDate());
        }
        LocalDate endDateInLocalDate = DateFormatterUtil.dateToLocalDate(task.getLastAppendedComponent().getEndDate().getDate());
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
        final int elapsedPeriod = RecurringTaskUtil.getElapsedPeriod(localDateCurrently, startDateInLocalDate, 
                endDateInLocalDate, task.getRecurringType());
        if (elapsedPeriod > 0) {
            RecurringTaskUtil.correctRecurringTask(task, elapsedPeriod, task.getRecurringType());
        }        
    }

    /**
     * Appends new task when the it is time to add the recurring task. Recurring
     * tasks that pass their recurring dates will be appended to show the next
     * task date.
     */
    public void attemptAppendRecurringTask(ReadOnlyTask task, Calendar startDate, Calendar endDate, LocalDate currentDate) {
        assert !CollectionUtil.isAnyNull(task, endDate, currentDate);
        LocalDate localDateCurrently = currentDate;
        LocalDate startDateInLocalDate = null;
        if (startDate != null) {
            startDateInLocalDate = DateFormatterUtil.dateToLocalDate(startDate.getTime());
        }
        LocalDate endDateInLocalDate = DateFormatterUtil.dateToLocalDate(endDate.getTime());
        attemptAppendRecurringTask((Task) task, startDate, endDate, localDateCurrently,
                startDateInLocalDate, endDateInLocalDate);
    }

    private boolean handleRecurringPeriod(Task task) {
        if (task.getRecurringPeriod() == NO_MORE_RECURRING_PERIOD) {
            return true;   
        }
        task.decrementRecurringPeriod();
        return false;
    }

    private void attemptAppendRecurringTask(Task task, Calendar startDate, Calendar endDate,
            LocalDate localDateCurrently, LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedPeriod = RecurringTaskUtil.getNumElapsedTaskToAppend(localDateCurrently, startDateInLocalDate, endDateInLocalDate, task.getRecurringType());
        for (int i = 0; i < elapsedPeriod; i++) {
            if (handleRecurringPeriod(task)) {
                break;
            }
            appendRecurringTask(task, startDate, endDate);
        }        
    }
    
    private void appendRecurringTask(Task task, Calendar startDate, Calendar endDate) {
        RecurringTaskUtil.appendRecurringTask(task, startDate, endDate, repeatingTasks, task.getRecurringType());
    }
    
    public void updateRecurringTasks(TaskOccurrence target) {
        Task taskDesc = (Task) target.getTaskReference();
        if (taskDesc.getRecurringType().equals(RecurringType.NONE)
                || taskDesc.getRecurringPeriod() == NO_MORE_RECURRING_PERIOD) {
            return;
        }
        appendRecurringTask(target, taskDesc);
    }
    
    private void appendRecurringTaskWithPeriod(Task task) {
        TaskOccurrence occurrence = task.getLastAppendedComponent();
        if (task.getRecurringType().equals(RecurringType.NONE)
                || task.getRecurringPeriod() == Task.NO_RECURRING_PERIOD) {
            return;
        }
        appendRecurringTask(occurrence, task);
    }

    private void appendRecurringTask(TaskOccurrence target, Task taskDesc) {
        Calendar startDate = new GregorianCalendar();
        Calendar endDate = new GregorianCalendar();
        startDate.setTime(target.getStartDate().getDate());
        endDate.setTime(target.getEndDate().getDate());
        if (!target.getStartDate().isValid()) {
            startDate = null;
        }
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

    public void addTask(Task task) {
        if (!isTestMode) {
            correctOverdueNonRepeatingTasks(task);
        }
        appendRecurringTaskWithPeriod(task);        
    }
    
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
