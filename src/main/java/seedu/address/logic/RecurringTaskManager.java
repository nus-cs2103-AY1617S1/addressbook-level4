package seedu.address.logic;

import java.time.LocalDate;
import java.time.ZoneId;
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
    private static RecurringTaskManager instance;
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private UniqueTaskList repeatingTasks;

    public void setTaskList(UniqueTaskList referenceList) {
        assert referenceList != null : "Reference Task list cannot be null";
        logger.fine("Initializing with RecurringTaskManager to manage: " + referenceList.toString());
        repeatingTasks = referenceList;
    }

    public boolean updateAnyRecurringTasks() {
        return updateAnyRecurringTasks(LocalDate.now());
    }

    /**
     * Test method to test updateAnyRecurringTasks is working as intended.
     * 
     * @param currentDate The date we want to update towards.
     */
    public boolean updateAnyRecurringTasks(LocalDate currentDate) {
        assert repeatingTasks != null : "Repeating Task list reference cannot be null";
        logger.info("=============================[ RecurringTaskManager Updating ]===========================");
        boolean hasUpdated = false;
        for (ReadOnlyTask task : repeatingTasks) {
            if (task.getRecurringType().equals(RecurringType.NONE)) {
                continue;
            }
            updateRecurringTask(task, currentDate);
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
        if (task.getRecurringType().equals(RecurringType.NONE)) {
            return;
        }
        LocalDate localDateCurrently = currentDate;
        LocalDate startDateInLocalDate = null;
        if (!task.getLastAppendedComponent().hasOnlyEndDate()) {
            startDateInLocalDate = task.getLastAppendedComponent().getStartDate().getDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
        }
        LocalDate endDateInLocalDate = task.getLastAppendedComponent().getEndDate().getDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
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
            correctYearlyRecurringTask(task, elapsedYear);
        } else {
            final int elapsedDay = RecurringTaskUtil.getElapsedPeriod(localDateCurrently, startDateInLocalDate, 
                    endDateInLocalDate, RecurringType.DAILY);
            if (elapsedDay > 0) {
                correctYearlyRecurringTask(task, 1);
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
            correctMonthlyRecurringTask(task, elapsedMonth);
        } else {
            final int elapsedDay = RecurringTaskUtil.getElapsedPeriod(localDateCurrently, startDateInLocalDate, 
                    endDateInLocalDate, RecurringType.DAILY);
            if (elapsedDay > 0) {
                correctMonthlyRecurringTask(task, 1);
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
            correctWeeklyRecurringTask(task, elapsedWeek);
        } else {
            final int elapsedDay = RecurringTaskUtil.getElapsedPeriod(localDateCurrently, startDateInLocalDate, 
                    endDateInLocalDate, RecurringType.DAILY);
            if (elapsedDay > 0) {
                correctWeeklyRecurringTask(task, 1);
            }
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
            correctDailyRecurringTask(task, elapsedDay);
        }
    }

    /**
     * Corrects the overdued yearly tasks.
     */
    private void correctYearlyRecurringTask(ReadOnlyTask task, int elapsedYear) {
        RecurringTaskUtil.correctRecurringTask(task, elapsedYear, RecurringType.YEARLY);
    }

    /**
     * Corrects the overdued monthly tasks.
     */
    private void correctMonthlyRecurringTask(ReadOnlyTask task, int elapsedMonth) {
        RecurringTaskUtil.correctRecurringTask(task, elapsedMonth, RecurringType.MONTHLY);
    }

    /**
     * Corrects the overdued weekly tasks.
     */
    private void correctWeeklyRecurringTask(ReadOnlyTask task, int elapsedWeek) {
        RecurringTaskUtil.correctRecurringTask(task, elapsedWeek, RecurringType.WEEKLY);
    }

    /**
     * Corrects the overdued daily tasks..
     */
    private void correctDailyRecurringTask(Task task, int elapsedDay) {
        RecurringTaskUtil.correctRecurringTask(task, elapsedDay, RecurringType.DAILY);
    }

    /**
     * Test method to test if updateRecurringTask is working as intended
     */
    public void updateRecurringTask(ReadOnlyTask task, LocalDate currentDate) {
        Calendar startDate = new GregorianCalendar();
        Calendar endDate = new GregorianCalendar();

        List<TaskOccurrence> dateComponents = task.getTaskDateComponent();
        TaskOccurrence lastAddedComponent = dateComponents.get(dateComponents.size() - 1);
        startDate.setTime(lastAddedComponent.getStartDate().getDate());
        endDate.setTime(lastAddedComponent.getEndDate().getDate());

        if (!lastAddedComponent.getStartDate().isValid()) {
            startDate = null;
        }
        appendRecurringTasks(task, startDate, endDate, currentDate);        
    }

    /**
     * Appends new task when the it is time to add the recurring task. Recurring
     * tasks that pass their recurring dates will be appended to show the next
     * task date.
     */
    public void appendRecurringTasks(ReadOnlyTask task, Calendar startDate, Calendar endDate, LocalDate currentDate) {
        assert !CollectionUtil.isAnyNull(task, endDate, currentDate);
        LocalDate localDateCurrently = currentDate;
        LocalDate startDateInLocalDate = null;
        if (startDate != null) {
            startDateInLocalDate = DateFormatterUtil.dateToLocalDate(startDate.getTime());
        }
        LocalDate endDateInLocalDate = DateFormatterUtil.dateToLocalDate(endDate.getTime());
        switch (task.getRecurringType()) {
        case DAILY:
            attemptAppendDailyRecurringTasks(task, startDate, endDate, localDateCurrently, 
                    startDateInLocalDate, endDateInLocalDate);
            break;
        case WEEKLY:
            attempAppendWeeklyRecurringTasks(task, startDate, endDate, localDateCurrently, 
                    startDateInLocalDate, endDateInLocalDate);
            break;
        case MONTHLY:
            attemptAppendMonthlyRecurringTasks(task, startDate, endDate, localDateCurrently, 
                    startDateInLocalDate, endDateInLocalDate);
            break;
        case YEARLY:
            attemptAppendYearlyRecurringTasks(task, startDate, endDate, localDateCurrently, 
                    startDateInLocalDate, endDateInLocalDate);
            break;
        default:
            assert false : "Failed to set recurring type";
            break;
        }
    }

    /**
     * Appends yearly recurring tasks if the task has crossed over to a new year
     */
    private void attemptAppendYearlyRecurringTasks(ReadOnlyTask task, Calendar startDate, Calendar endDate,
            LocalDate localDateCurrently, LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedYear = RecurringTaskUtil.getNumElapsedTaskToAppend(localDateCurrently, startDateInLocalDate, endDateInLocalDate, RecurringType.YEARLY);
        for (int i = 0; i < elapsedYear; i++) {
            appendYearlyRecurringTask(task, startDate, endDate, elapsedYear);
        }
    }

    /**
     * Appends monthly recurring tasks if the task has crossed over to a new
     * month
     */
    private void attemptAppendMonthlyRecurringTasks(ReadOnlyTask task, Calendar startDate, Calendar endDate,
            LocalDate localDateCurrently, LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedMonth = RecurringTaskUtil.getNumElapsedTaskToAppend(localDateCurrently, startDateInLocalDate, endDateInLocalDate, RecurringType.MONTHLY);
        for (int i = 0; i < elapsedMonth; i++) {
            appendMonthlyRecurringTask(task, startDate, endDate, elapsedMonth);
        }
    }

    /**
     * Appends weekly recurring tasks if the task has crossed over to a new week
     */
    private void attempAppendWeeklyRecurringTasks(ReadOnlyTask task, Calendar startDate, Calendar endDate,
            LocalDate localDateCurrently, LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedWeek = RecurringTaskUtil.getNumElapsedTaskToAppend(localDateCurrently, startDateInLocalDate, endDateInLocalDate, RecurringType.WEEKLY);
        for (int i = 0; i < elapsedWeek; i++) {
            appendWeeklyRecurringTask(task, startDate, endDate, elapsedWeek);
        }
    }

    /**
     * Appends daily recurring tasks if the task has crossed over to a new day
     */
    private void attemptAppendDailyRecurringTasks(ReadOnlyTask task, Calendar startDate, Calendar endDate,
            LocalDate localDateCurrently, LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedDay = RecurringTaskUtil.getNumElapsedTaskToAppend(localDateCurrently, startDateInLocalDate, endDateInLocalDate, RecurringType.DAILY);
        for (int i = 0; i < elapsedDay; i++) {
            appendDailyRecurringTask(task, startDate, endDate, elapsedDay);
        }
    }

    /**
     * Updates Yearly recurring tasks to the their latest date slot.
     */
    private void appendYearlyRecurringTask(ReadOnlyTask task, Calendar startDate, Calendar endDate, int elapsedYear) {
        RecurringTaskUtil.appendRecurringTask(task, startDate, endDate, repeatingTasks, RecurringType.YEARLY);
    }

    /**
     * Updates Monthly recurring tasks to the their latest date slot.
     */
    private void appendMonthlyRecurringTask(ReadOnlyTask task, Calendar startDate, Calendar endDate, int elapsedMonth) {
        RecurringTaskUtil.appendRecurringTask(task, startDate, endDate, repeatingTasks, RecurringType.MONTHLY);
    }

    /**
     * Updates Weekly recurring tasks to the their latest date slot.
     */
    private void appendWeeklyRecurringTask(ReadOnlyTask task, Calendar startDate, Calendar endDate, int elapsedWeek) {
        RecurringTaskUtil.appendRecurringTask(task, startDate, endDate, repeatingTasks, RecurringType.WEEKLY);
    }

    /**
     * Updates Daily recurring tasks to the their latest date slot.
     */
    private void appendDailyRecurringTask(ReadOnlyTask task, Calendar startDate, Calendar endDate, int elapsedDay) {
        RecurringTaskUtil.appendRecurringTask(task, startDate, endDate, repeatingTasks, RecurringType.DAILY);
    }

    public static RecurringTaskManager getInstance() {
        if (instance == null) {
            instance = new RecurringTaskManager();
        }
        return instance;
    }
}
