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
 * tasks be shown This class is using a singleton pattern. Use
 * RecurringTaskManager.getInstance() to get the instance of the class
 */
public class RecurringTaskManager {
    private static final int NO_MORE_RECURRING_PERIOD = 0;
    private static RecurringTaskManager instance;
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private UniqueTaskList repeatingTasks;

    public void setTaskList(UniqueTaskList referenceList) {
        assert referenceList != null : "Reference Task list cannot be null";
        logger.fine("Initializing with RecurringTaskManager to manage: " + referenceList.toString());
        repeatingTasks = referenceList;
    }

    public boolean appendAnyRecurringTasks() {
        return appendAnyRecurringTasks(LocalDate.now());
    }

    /**
     * Test method to test updateAnyRecurringTasks is working as intended.
     * 
     * @param currentDate The date we want to update towards.
     */
    public boolean appendAnyRecurringTasks(LocalDate currentDate) {
        assert repeatingTasks != null : "Repeating Task list reference cannot be null";
        logger.info("=============================[ RecurringTaskManager Updating ]===========================");
        boolean hasUpdated = false;
        for (ReadOnlyTask task : repeatingTasks) {
            if (task.getRecurringType().equals(RecurringType.NONE)) {
                continue;
            }
            appendRecurringTask(task, currentDate);
            hasUpdated = true;
        }
        return hasUpdated;
    }    
    
    /**
     * Corrects the recurring task that are overdued to reflect the present and
     * future recurring dates
     * 
     * @param task Added task that might have dates that are overdued at the start
     */
    public void correctAddingOverdueTasks(Task task) {
        assert task != null : "task that needs correcting cannot be null!";
        correctAddingOverdueTasks(task, LocalDate.now());
    }

    /**
     * Helps to correct date till a certain date. Helps with testing of the
     * correcting of date.
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
        switch (task.getRecurringType()) {
        case DAILY:
            attemptCorrectDailyRecurringTask(task, localDateCurrently, startDateInLocalDate, endDateInLocalDate);
            break;
        case WEEKLY:
            attemptCorrectWeeklyRecurringTask(task, localDateCurrently, startDateInLocalDate, endDateInLocalDate);
            break;
        case MONTHLY:
            attemptCorrectMonthlyRecurringTask(task, localDateCurrently, startDateInLocalDate, endDateInLocalDate);
            break;
        case YEARLY:
            attemptCorrectYearlyRecurringTask(task, localDateCurrently, startDateInLocalDate, endDateInLocalDate);
            break;
        default:
            assert false : "Recurring Type must always be specified";
            break;
        }
    }

    /**
     * Corrects recurring tasks that are overdued to the next possible recurring
     * slot. Does not correct the tasks if it is not overdued.
     */
    private void attemptCorrectYearlyRecurringTask(Task task, LocalDate localDateCurrently,
            LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedYear = RecurringTaskUtil.getElapsedPeriod(localDateCurrently, startDateInLocalDate,
                endDateInLocalDate, RecurringType.YEARLY);
        if (elapsedYear > 0) {
            RecurringTaskUtil.correctRecurringTask(task, elapsedYear, RecurringType.YEARLY);
        } else {
            final int elapsedDay = RecurringTaskUtil.getElapsedPeriod(localDateCurrently, startDateInLocalDate,
                    endDateInLocalDate, RecurringType.DAILY);
            if (elapsedDay > 0) {
                RecurringTaskUtil.correctRecurringTask(task, 1, RecurringType.YEARLY);
            }
        }
    }

    /**
     * Corrects recurring tasks that are overdued to the next possible recurring
     * slot. Does not correct the tasks if it is not overdued.
     */
    private void attemptCorrectMonthlyRecurringTask(Task task, LocalDate localDateCurrently,
            LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedMonth = RecurringTaskUtil.getElapsedPeriod(localDateCurrently, startDateInLocalDate, 
                endDateInLocalDate, RecurringType.MONTHLY);
        if (elapsedMonth > 0) {
            RecurringTaskUtil.correctRecurringTask(task, elapsedMonth, RecurringType.MONTHLY);
        } else {
            final int elapsedDay = RecurringTaskUtil.getElapsedPeriod(localDateCurrently, startDateInLocalDate, 
                    endDateInLocalDate, RecurringType.DAILY);
            if (elapsedDay > 0) {
                RecurringTaskUtil.correctRecurringTask(task, 1, RecurringType.MONTHLY);
            }
        }
    }

    /**
     * Corrects recurring tasks that are overdued to the next possible recurring
     * slot. Does not correct the tasks if it is not overdued.
     */
    private void attemptCorrectWeeklyRecurringTask(Task task, LocalDate localDateCurrently,
            LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedWeek = RecurringTaskUtil.getElapsedPeriod(localDateCurrently, startDateInLocalDate, 
                endDateInLocalDate, RecurringType.WEEKLY);
        if (elapsedWeek > 0) {
            RecurringTaskUtil.correctRecurringTask(task, elapsedWeek, RecurringType.WEEKLY);
        }
    }

    /**
     * Corrects recurring tasks that are overdued to the next possible recurring
     * slot. Does not correct the tasks if it is not overdued.
     */
    private void attemptCorrectDailyRecurringTask(Task task, LocalDate localDateCurrently,
            LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedDay = RecurringTaskUtil.getElapsedPeriod(localDateCurrently, startDateInLocalDate, 
                endDateInLocalDate, RecurringType.DAILY);
        if (elapsedDay > 0) {
            RecurringTaskUtil.correctRecurringTask(task, elapsedDay, RecurringType.DAILY);
        }
    }

    /**
     * Test method to test if updateRecurringTask is working as intended
     */
    public void appendRecurringTask(ReadOnlyTask task, LocalDate currentDate) {
        Calendar startDate = new GregorianCalendar();
        Calendar endDate = new GregorianCalendar();

        List<TaskOccurrence> dateComponents = task.getTaskDateComponent();
        TaskOccurrence lastAddedComponent = dateComponents.get(dateComponents.size() - 1);
        startDate.setTime(lastAddedComponent.getStartDate().getDate());
        endDate.setTime(lastAddedComponent.getEndDate().getDate());

        if (!lastAddedComponent.getStartDate().isValid()) {
            startDate = null;
        }
        attemptAppendRecurringTasks(task, startDate, endDate, currentDate);        
    }

    /**
     * Appends new task when the it is time to add the recurring task. Recurring
     * tasks that pass their recurring dates will be appended to show the next
     * task date.
     */
    public void attemptAppendRecurringTasks(ReadOnlyTask task, Calendar startDate, Calendar endDate, LocalDate currentDate) {
        assert !CollectionUtil.isAnyNull(task, endDate, currentDate);
        LocalDate localDateCurrently = currentDate;
        LocalDate startDateInLocalDate = null;
        if (startDate != null) {
            startDateInLocalDate = DateFormatterUtil.dateToLocalDate(startDate.getTime());
        }
        LocalDate endDateInLocalDate = DateFormatterUtil.dateToLocalDate(endDate.getTime());
        switch (task.getRecurringType()) {
        case DAILY:
            attemptAppendDailyRecurringTasks((Task) task, startDate, endDate, localDateCurrently, 
                    startDateInLocalDate, endDateInLocalDate);
            break;
        case WEEKLY:
            attempAppendWeeklyRecurringTasks((Task) task, startDate, endDate, localDateCurrently, 
                    startDateInLocalDate, endDateInLocalDate);
            break;
        case MONTHLY:
            attemptAppendMonthlyRecurringTasks((Task) task, startDate, endDate, localDateCurrently, 
                    startDateInLocalDate, endDateInLocalDate);
            break;
        case YEARLY:
            attemptAppendYearlyRecurringTasks((Task) task, startDate, endDate, localDateCurrently, 
                    startDateInLocalDate, endDateInLocalDate);
            break;
        default:
            assert false : "Failed to set recurring type";
            break;
        }
    }

    private boolean handleRecurringPeriod(Task task) {
        if (task.getRecurringPeriod() == NO_MORE_RECURRING_PERIOD) {
            return true;   
        }
        task.decrementRecurringPeriod();
        return false;
    }

    /**
     * Appends yearly recurring tasks if the task has crossed over to a new year
     */
    private void attemptAppendYearlyRecurringTasks(Task task, Calendar startDate, Calendar endDate,
            LocalDate localDateCurrently, LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedYear = RecurringTaskUtil.getNumElapsedTaskToAppend(localDateCurrently, startDateInLocalDate, endDateInLocalDate, RecurringType.YEARLY);
        for (int i = 0; i < elapsedYear; i++) {
            if (handleRecurringPeriod(task)) {
                break;
            }
            appendYearlyRecurringTask(task, startDate, endDate);
        }
    }    
    
    /**
     * Appends monthly recurring tasks if the task has crossed over to a new
     * month
     */
    private void attemptAppendMonthlyRecurringTasks(Task task, Calendar startDate, Calendar endDate,
            LocalDate localDateCurrently, LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedMonth = RecurringTaskUtil.getNumElapsedTaskToAppend(localDateCurrently, startDateInLocalDate, endDateInLocalDate, RecurringType.MONTHLY);
        for (int i = 0; i < elapsedMonth; i++) {
            if (handleRecurringPeriod(task)) {
                break;
            }
            appendMonthlyRecurringTask(task, startDate, endDate);
        }
    }

    /**
     * Appends weekly recurring tasks if the task has crossed over to a new week
     */
    private void attempAppendWeeklyRecurringTasks(Task task, Calendar startDate, Calendar endDate,
            LocalDate localDateCurrently, LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedWeek = RecurringTaskUtil.getNumElapsedTaskToAppend(localDateCurrently, startDateInLocalDate, endDateInLocalDate, RecurringType.WEEKLY);
        for (int i = 0; i < elapsedWeek; i++) {
            if (handleRecurringPeriod(task)) {
                break;
            }
            appendWeeklyRecurringTask(task, startDate, endDate);
        }
    }

    /**
     * Appends daily recurring tasks if the task has crossed over to a new day
     */
    private void attemptAppendDailyRecurringTasks(Task task, Calendar startDate, Calendar endDate,
            LocalDate localDateCurrently, LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedDay = RecurringTaskUtil.getNumElapsedTaskToAppend(localDateCurrently, startDateInLocalDate, endDateInLocalDate, RecurringType.DAILY);
        for (int i = 0; i < elapsedDay; i++) {
            if (handleRecurringPeriod(task)) {
                break;
            }
            appendDailyRecurringTask(task, startDate, endDate);
        }
    }

    /**
     * Updates Yearly recurring tasks to the their latest date slot.
     */
    private void appendYearlyRecurringTask(ReadOnlyTask task, Calendar startDate, Calendar endDate) {
        RecurringTaskUtil.appendRecurringTask(task, startDate, endDate, repeatingTasks, RecurringType.YEARLY);
    }

    /**
     * Updates Monthly recurring tasks to the their latest date slot.
     */
    private void appendMonthlyRecurringTask(ReadOnlyTask task, Calendar startDate, Calendar endDate) {
        RecurringTaskUtil.appendRecurringTask(task, startDate, endDate, repeatingTasks, RecurringType.MONTHLY);
    }

    /**
     * Updates Weekly recurring tasks to the their latest date slot.
     */
    private void appendWeeklyRecurringTask(ReadOnlyTask task, Calendar startDate, Calendar endDate) {
        RecurringTaskUtil.appendRecurringTask(task, startDate, endDate, repeatingTasks, RecurringType.WEEKLY);
    }

    /**
     * Updates Daily recurring tasks to the their latest date slot.
     */
    private void appendDailyRecurringTask(ReadOnlyTask task, Calendar startDate, Calendar endDate) {
        RecurringTaskUtil.appendRecurringTask(task, startDate, endDate, repeatingTasks, RecurringType.DAILY);
    }
    
    public void updateRecurringTasks(TaskOccurrence target) {
        ReadOnlyTask taskDesc = target.getTaskReference();
        if (taskDesc.getRecurringType().equals(RecurringType.NONE) ||
                taskDesc.getRecurringPeriod() <= NO_MORE_RECURRING_PERIOD) {
            return;
        }
        Calendar startDate = new GregorianCalendar();
        Calendar endDate = new GregorianCalendar();
        startDate.setTime(target.getStartDate().getDate());
        endDate.setTime(target.getEndDate().getDate());
        if (!target.getStartDate().isValid()) {
            startDate = null;
        }
        switch (taskDesc.getRecurringType()) {
            case DAILY:
                appendDailyRecurringTask(taskDesc, startDate, endDate);
                break;
            case WEEKLY:
                appendWeeklyRecurringTask(taskDesc, startDate, endDate);
                break;
            case MONTHLY:
                appendMonthlyRecurringTask(taskDesc, startDate, endDate);
                break;
            case YEARLY:
                appendYearlyRecurringTask(taskDesc, startDate, endDate);
                break;
            default:
                break;
        }
        ((Task)taskDesc).decrementRecurringPeriod();
    }

    public static RecurringTaskManager getInstance() {
        if (instance == null) {
            instance = new RecurringTaskManager();
        }
        return instance;
    }
}
