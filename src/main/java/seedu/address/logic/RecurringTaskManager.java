package seedu.address.logic;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskOcurrence;
import seedu.address.model.task.UniqueTaskList;

//@@author A0135782Y
/**
 * Handles the behaviour of recurring tasks Dictates when should the recurring
 * tasks be shown This class is using a singleton pattern. Use
 * RecurringTaskManager.getInstance() to get the instance of the class
 */
public class RecurringTaskManager {
    private static final int APPEND_INCREMENT = 1;
    private static final double NUM_MONTHS_IN_YEAR = 12.0;
    private static final double NUM_WEEKS_IN_MONTH = 4.0;
    private static final double NUM_DAYS_IN_WEEK = 7.0;
    private static final int NUMBER_OF_DAYS_IN_A_WEEK = 7;
    private static RecurringTaskManager instance;
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private UniqueTaskList repeatingTasks;

    public void setTaskList(UniqueTaskList referenceList) {
        assert referenceList != null : "Reference Task list cannot be null";
        logger.fine("Initializing with RecurringTaskManager to manage: " + referenceList.toString());
        repeatingTasks = referenceList;
    }

    public void updateAnyRecurringTasks() {
        assert repeatingTasks != null : "Repeating Task list reference cannot be null";
        logger.info("=============================[ RecurringTaskManager Updating ]===========================");
        for (ReadOnlyTask task : repeatingTasks) {
            if (task.getRecurringType().equals(RecurringType.NONE)) {
                continue;
            }
            updateRecurringTask(task);
        }
    }

    /**
     * Corrects the recurring task that are overdued to reflect the present and
     * future recurring dates
     * 
     * @param task
     *            Added task that might have dates that are overdued at the
     *            start
     */
    public void correctAddingOverdueTasks(Task task) {
        assert task != null : "task that needs correcting cannot be null!";
        correctAddingOverdueTasks(task, LocalDate.now());
    }

    /**
     * Helps to correct date till a certain date. Helps with testing of the
     * correcting of date.
     * 
     * @param task
     *            Task to be correct, cannot be null
     * @param currentDate
     *            LocalDate that we are correcting towards, cannot be null
     */
    public void correctAddingOverdueTasks(Task task, LocalDate currentDate) {
        assert !CollectionUtil.isAnyNull(task, currentDate);
        if (task.getRecurringType().equals(RecurringType.NONE)) {
            return;
        }
        LocalDate localDateCurrently = currentDate;
        LocalDate startDateInLocalDate = null;
        if (!task.getComponentForNonRecurringType().hasOnlyEndDate()) {
            startDateInLocalDate = task.getComponentForNonRecurringType().getStartDate().getDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
        }
        LocalDate endDateInLocalDate = task.getComponentForNonRecurringType().getEndDate().getDate().toInstant()
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
     * 
     * @param task
     *            Task that we are interested in correcting
     * @param localDateCurrently
     *            Local date should not be null and should be the date that we
     *            are interested in.
     * @param startDateInLocalDate
     *            Converted form of start date.
     * @param endDateInLocalDate
     *            Converted form of end date.
     */
    private void attemptCorrectYearlyRecurringTask(Task task, LocalDate localDateCurrently,
            LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedYear;
        if (startDateInLocalDate != null) {
            elapsedYear = (int) Math
                    .ceil(ChronoUnit.MONTHS.between(startDateInLocalDate, localDateCurrently) / NUM_MONTHS_IN_YEAR);
        } else {
            elapsedYear = (int) Math
                    .ceil(ChronoUnit.MONTHS.between(endDateInLocalDate, localDateCurrently) / NUM_MONTHS_IN_YEAR);
        }
        if (elapsedYear > 0) {
            correctYearlyRecurringTask(task, elapsedYear);
        } else {
            final int elapsedDay = (int) ChronoUnit.DAYS.between(startDateInLocalDate, localDateCurrently);
            if (elapsedDay > 0) {
                correctYearlyRecurringTask(task, 1);
            }
        }
    }

    /**
     * Corrects recurring tasks that are overdued to the next possible recurring
     * slot. Does not correct the tasks if it is not overdued.
     * 
     * @param task
     *            Task that we are interested in correcting
     * @param localDateCurrently
     *            Local date should not be null and should be the date that we
     *            are interested in.
     * @param startDateInLocalDate
     *            Converted form of start date.
     * @param endDateInLocalDate
     *            Converted form of end date.
     */
    private void attemptCorrectMonthlyRecurringTask(Task task, LocalDate localDateCurrently,
            LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedMonth;
        if (startDateInLocalDate != null) {
            elapsedMonth = (int) Math
                    .ceil(ChronoUnit.WEEKS.between(startDateInLocalDate, localDateCurrently) / NUM_WEEKS_IN_MONTH);
        } else {
            elapsedMonth = (int) Math
                    .ceil(ChronoUnit.WEEKS.between(endDateInLocalDate, localDateCurrently) / NUM_WEEKS_IN_MONTH);
        }
        if (elapsedMonth > 0) {
            correctMonthlyRecurringTask(task, elapsedMonth);
        } else {
            final int elapsedDay = (int) ChronoUnit.DAYS.between(startDateInLocalDate, localDateCurrently);
            if (elapsedDay > 0) {
                correctMonthlyRecurringTask(task, 1);
            }
        }
    }

    /**
     * Corrects recurring tasks that are overdued to the next possible recurring
     * slot. Does not correct the tasks if it is not overdued.
     * 
     * @param task
     *            Task that we are interested in correcting
     * @param localDateCurrently
     *            Local date should not be null and should be the date that we
     *            are interested in.
     * @param startDateInLocalDate
     *            Converted form of start date.
     * @param endDateInLocalDate
     *            Converted form of end date.
     */
    private void attemptCorrectWeeklyRecurringTask(Task task, LocalDate localDateCurrently,
            LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedWeek;
        if (startDateInLocalDate != null) {
            elapsedWeek = (int) (ChronoUnit.DAYS.between(startDateInLocalDate, localDateCurrently) / NUM_DAYS_IN_WEEK);
        } else {
            elapsedWeek = (int) (ChronoUnit.DAYS.between(endDateInLocalDate, localDateCurrently) / NUM_DAYS_IN_WEEK);
        }
        if (elapsedWeek > 0) {
            correctWeeklyRecurringTask(task, elapsedWeek);
        } else {
            final int elapsedDay = (int) ChronoUnit.DAYS.between(startDateInLocalDate, localDateCurrently);
            if (elapsedDay > 0) {
                correctWeeklyRecurringTask(task, 1);
            }
        }
    }

    /**
     * Corrects recurring tasks that are overdued to the next possible recurring
     * slot. Does not correct the tasks if it is not overdued.
     * 
     * @param task
     *            Task that we are interested in correcting
     * @param localDateCurrently
     *            Local date should not be null and should be the date that we
     *            are interested in.
     * @param startDateInLocalDate
     *            Converted form of start date.
     * @param endDateInLocalDate
     *            Converted form of end date.
     */
    private void attemptCorrectDailyRecurringTask(Task task, LocalDate localDateCurrently,
            LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedDay;
        if (startDateInLocalDate != null) {
            elapsedDay = (int) ChronoUnit.DAYS.between(startDateInLocalDate, localDateCurrently);
        } else {
            elapsedDay = (int) ChronoUnit.DAYS.between(endDateInLocalDate, localDateCurrently);
        }
        if (elapsedDay > 0) {
            correctDailyRecurringTask(task, elapsedDay);
        }
    }

    /**
     * Corrects the overdued yearly tasks.
     * 
     * @param task
     *            Task that we are correcting.
     * @param elapsedYear
     *            How many years to correct to.
     */
    private void correctYearlyRecurringTask(ReadOnlyTask task, int elapsedYear) {
        Calendar calendar = Calendar.getInstance();
        TaskDate correctedStartDate = new TaskDate();
        TaskDate correctedEndDate = new TaskDate();
        TaskDate startDate = task.getComponentForNonRecurringType().getStartDate();
        TaskDate endDate = task.getComponentForNonRecurringType().getEndDate();

        if (!task.getComponentForNonRecurringType().hasOnlyEndDate()) {
            calendar.setTime(startDate.getDate());
            calendar.add(Calendar.YEAR, elapsedYear);
            correctedStartDate.setDateInLong(calendar.getTime().getTime());
        } else {
            correctedStartDate.setDateInLong((new TaskDate()).getDateInLong());
        }

        calendar.setTime(endDate.getDate());
        calendar.add(Calendar.YEAR, elapsedYear);
        correctedEndDate.setDateInLong(calendar.getTime().getTime());

        task.getComponentForNonRecurringType().setStartDate(correctedStartDate);
        task.getComponentForNonRecurringType().setEndDate(correctedEndDate);
    }

    /**
     * Corrects the overdued monthly tasks.
     * 
     * @param task
     *            Task that we are correcting.
     * @param elapsedMonth
     *            How many months to correct to.
     */
    private void correctMonthlyRecurringTask(ReadOnlyTask task, int elapsedMonth) {
        Calendar calendar = Calendar.getInstance();
        TaskDate correctedStartDate = new TaskDate();
        TaskDate correctedEndDate = new TaskDate();
        TaskDate startDate = task.getComponentForNonRecurringType().getStartDate();
        TaskDate endDate = task.getComponentForNonRecurringType().getEndDate();

        if (!task.getComponentForNonRecurringType().hasOnlyEndDate()) {
            calendar.setTime(startDate.getDate());
            calendar.add(Calendar.MONTH, elapsedMonth);
            correctedStartDate.setDateInLong(calendar.getTime().getTime());
        } else {
            correctedStartDate.setDateInLong((new TaskDate()).getDateInLong());
        }

        calendar.setTime(endDate.getDate());
        calendar.add(Calendar.MONTH, elapsedMonth);
        correctedEndDate.setDateInLong(calendar.getTime().getTime());

        task.getComponentForNonRecurringType().setStartDate(correctedStartDate);
        task.getComponentForNonRecurringType().setEndDate(correctedEndDate);
    }

    /**
     * Corrects the overdued weekly tasks.
     * 
     * @param task
     *            Task that we are correcting.
     * @param elapsedWeek
     *            How many weeks to correct to.
     */
    private void correctWeeklyRecurringTask(ReadOnlyTask task, int elapsedWeek) {
        Calendar calendar = Calendar.getInstance();
        TaskDate correctedStartDate = new TaskDate();
        TaskDate correctedEndDate = new TaskDate();
        TaskDate startDate = task.getComponentForNonRecurringType().getStartDate();
        TaskDate endDate = task.getComponentForNonRecurringType().getEndDate();

        if (!task.getComponentForNonRecurringType().hasOnlyEndDate()) {
            calendar.setTime(startDate.getDate());
            calendar.add(Calendar.DAY_OF_MONTH, elapsedWeek * NUMBER_OF_DAYS_IN_A_WEEK);
            correctedStartDate.setDateInLong(calendar.getTime().getTime());
        } else {
            correctedStartDate.setDateInLong((new TaskDate()).getDateInLong());
        }

        calendar.setTime(endDate.getDate());
        calendar.add(Calendar.DAY_OF_MONTH, elapsedWeek * NUMBER_OF_DAYS_IN_A_WEEK);
        correctedEndDate.setDateInLong(calendar.getTime().getTime());

        task.getComponentForNonRecurringType().setStartDate(correctedStartDate);
        task.getComponentForNonRecurringType().setEndDate(correctedEndDate);
    }

    /**
     * Corrects the overdued daily tasks.
     * 
     * @param task
     *            Task that we are correcting.
     * @param elapsedDays
     *            How many days to correct to.
     */
    private void correctDailyRecurringTask(Task task, int elapsedDay) {
        Calendar calendar = Calendar.getInstance();
        TaskDate correctedStartDate = new TaskDate();
        TaskDate correctedEndDate = new TaskDate();
        TaskDate startDate = task.getComponentForNonRecurringType().getStartDate();
        TaskDate endDate = task.getComponentForNonRecurringType().getEndDate();

        if (!task.getComponentForNonRecurringType().hasOnlyEndDate()) {
            calendar.setTime(startDate.getDate());
            calendar.add(Calendar.DAY_OF_MONTH, elapsedDay);
            correctedStartDate.setDateInLong(calendar.getTime().getTime());
        } else {
            correctedStartDate.setDateInLong((new TaskDate()).getDateInLong());
        }

        calendar.setTime(endDate.getDate());
        calendar.add(Calendar.DAY_OF_MONTH, elapsedDay);
        correctedEndDate.setDateInLong(calendar.getTime().getTime());

        task.getComponentForNonRecurringType().setStartDate(correctedStartDate);
        task.getComponentForNonRecurringType().setEndDate(correctedEndDate);
    }

    /**
     * @param Updates
     *            recurring tasks to append a new date when their recurring
     *            period has elapsed
     * @return True if the recurring task has been updated False if the
     *         recurring tasks has not been updated;
     */
    private void updateRecurringTask(ReadOnlyTask task) {
        Calendar startDate = new GregorianCalendar();
        Calendar endDate = new GregorianCalendar();

        List<TaskOcurrence> dateComponents = task.getTaskDateComponent();
        TaskOcurrence lastAddedComponent = dateComponents.get(dateComponents.size() - 1);
        startDate.setTime(lastAddedComponent.getStartDate().getDate());
        endDate.setTime(lastAddedComponent.getEndDate().getDate());

        if (!lastAddedComponent.getStartDate().isValid()) {
            startDate = null;
        }
        appendRecurringTasks(task, startDate, endDate);
    }

    /**
     * Appends new task when the it is time to add the recurring task. Recurring
     * tasks that pass their recurring dates will be appended to show the next
     * task date. The LocalDate is assumed to be the current system date
     * 
     * @param task
     *            Task that we are interested in
     * @param startDate
     *            Start date of the task in Calendar form to account for time
     *            zone.
     * @param endDate
     *            Start date of the task in Calendar form to account for time
     *            zone.
     */
    private void appendRecurringTasks(ReadOnlyTask task, Calendar startDate, Calendar endDate) {
        appendRecurringTasks(task, startDate, endDate, LocalDate.now());
    }

    /**
     * Appends new task when the it is time to add the recurring task. Recurring
     * tasks that pass their recurring dates will be appended to show the next
     * task date.
     * 
     * @param task
     *            Task that we are interested in
     * @param startDate
     *            Start date of the task in Calendar form to account for time
     *            zone.
     * @param endDate
     *            Start date of the task in Calendar form to account for time
     *            zone.
     * @param currentDate
     *            Current date to measure the amount of tasks that have not been
     *            done.
     */
    public void appendRecurringTasks(ReadOnlyTask task, Calendar startDate, Calendar endDate, LocalDate currentDate) {
        assert !CollectionUtil.isAnyNull(task, startDate, endDate, currentDate);
        LocalDate localDateCurrently = currentDate;
        LocalDate startDateInLocalDate = null;
        if (startDate != null) {
            startDateInLocalDate = startDate.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        LocalDate endDateInLocalDate = endDate.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        switch (task.getRecurringType()) {
        case DAILY:
            attemptAppendDailyRecurringTasks(task, startDate, endDate, localDateCurrently, startDateInLocalDate,
                    endDateInLocalDate);
            break;
        case WEEKLY:
            attempAppendWeeklyRecurringTasks(task, startDate, endDate, localDateCurrently, startDateInLocalDate,
                    endDateInLocalDate);
            break;
        case MONTHLY:
            attemptAppendMonthlyRecurringTasks(task, startDate, endDate, localDateCurrently, startDateInLocalDate,
                    endDateInLocalDate);
            break;
        case YEARLY:
            attemptAppendYearlyRecurringTasks(task, startDate, endDate, localDateCurrently, startDateInLocalDate,
                    endDateInLocalDate);
            break;
        default:
            assert true : "Failed to set recurring type";
            break;
        }
    }

    /**
     * Appends yearly recurring tasks if the task has crossed over to a new year
     * 
     * @param task
     *            Task that we are interest in
     * @param startDate
     *            Start date of the task
     * @param endDate
     *            End date of the ask
     * @param localDateCurrently
     *            Current date that we are at now
     * @param startDateInLocalDate
     *            Converted start date as a LocalDate
     * @param endDateInLocalDate
     *            Converted end date as a LocalDate
     */
    private void attemptAppendYearlyRecurringTasks(ReadOnlyTask task, Calendar startDate, Calendar endDate,
            LocalDate localDateCurrently, LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedYear;
        if (startDateInLocalDate != null) {
            elapsedYear = (int) Math
                    .ceil(ChronoUnit.MONTHS.between(startDateInLocalDate, localDateCurrently) / NUM_MONTHS_IN_YEAR);
        } else {
            elapsedYear = (int) Math
                    .ceil(ChronoUnit.MONTHS.between(endDateInLocalDate, localDateCurrently) / NUM_MONTHS_IN_YEAR);
        }
        for (int i = 0; i < elapsedYear; i++) {
            appendYearlyRecurringTask(task, startDate, endDate, elapsedYear);
        }
    }

    /**
     * Appends monthly recurring tasks if the task has crossed over to a new
     * month
     * 
     * @param task
     *            Task that we are interest in
     * @param startDate
     *            Start date of the task
     * @param endDate
     *            End date of the ask
     * @param localDateCurrently
     *            Current date that we are at now
     * @param startDateInLocalDate
     *            Converted start date as a LocalDate
     * @param endDateInLocalDate
     *            Converted end date as a LocalDate
     */
    private void attemptAppendMonthlyRecurringTasks(ReadOnlyTask task, Calendar startDate, Calendar endDate,
            LocalDate localDateCurrently, LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedMonth;
        if (startDateInLocalDate != null) {
            elapsedMonth = (int) Math
                    .ceil(ChronoUnit.WEEKS.between(startDateInLocalDate, localDateCurrently) / NUM_WEEKS_IN_MONTH);
        } else {
            elapsedMonth = (int) Math
                    .ceil(ChronoUnit.WEEKS.between(endDateInLocalDate, localDateCurrently) / NUM_WEEKS_IN_MONTH);
        }
        for (int i = 0; i < elapsedMonth; i++) {
            appendMonthlyRecurringTask(task, startDate, endDate, elapsedMonth);
        }
    }

    /**
     * Appends weekly recurring tasks if the task has crossed over to a new week
     * 
     * @param task
     *            Task that we are interest in
     * @param startDate
     *            Start date of the task
     * @param endDate
     *            End date of the ask
     * @param localDateCurrently
     *            Current date that we are at now
     * @param startDateInLocalDate
     *            Converted start date as a LocalDate
     * @param endDateInLocalDate
     *            Converted end date as a LocalDate
     */
    private void attempAppendWeeklyRecurringTasks(ReadOnlyTask task, Calendar startDate, Calendar endDate,
            LocalDate localDateCurrently, LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedWeek;
        if (startDateInLocalDate != null) {
            elapsedWeek = (int) Math
                    .ceil((ChronoUnit.DAYS.between(startDateInLocalDate, localDateCurrently) / NUM_DAYS_IN_WEEK));
        } else {
            elapsedWeek = (int) Math
                    .ceil((ChronoUnit.DAYS.between(endDateInLocalDate, localDateCurrently) / NUM_DAYS_IN_WEEK));
        }
        for (int i = 0; i < elapsedWeek; i++) {
            appendWeeklyRecurringTask(task, startDate, endDate, elapsedWeek);
        }
    }

    /**
     * Appends daily recurring tasks if the task has crossed over to a new day
     * 
     * @param task
     *            Task that we are interest in
     * @param startDate
     *            Start date of the task
     * @param endDate
     *            End date of the ask
     * @param localDateCurrently
     *            Current date that we are at now
     * @param startDateInLocalDate
     *            Converted start date as a LocalDate
     * @param endDateInLocalDate
     *            Converted end date as a LocalDate
     */
    private void attemptAppendDailyRecurringTasks(ReadOnlyTask task, Calendar startDate, Calendar endDate,
            LocalDate localDateCurrently, LocalDate startDateInLocalDate, LocalDate endDateInLocalDate) {
        final int elapsedDay;
        if (startDateInLocalDate != null) {
            elapsedDay = (int) ChronoUnit.DAYS.between(startDateInLocalDate, localDateCurrently);
        } else {
            elapsedDay = (int) ChronoUnit.DAYS.between(endDateInLocalDate, localDateCurrently);
        }
        for (int i = 0; i < elapsedDay; i++) {
            appendDailyRecurringTask(task, startDate, endDate, elapsedDay);
        }
    }

    /**
     * Updates Yearly recurring tasks to the their latest date slot.
     * 
     * @param task
     *            Recurring task to be considered.
     * @param startDate
     *            The start date of this task if any. Null values represents
     *            that start date is not present.
     * @param endDate
     *            The end date of the is task.
     * @param elapsedYear
     *            The years that have elapsed.
     */
    private void appendYearlyRecurringTask(ReadOnlyTask task, Calendar startDate, Calendar endDate, int elapsedYear) {
        // Append a new date to the current task
        Calendar calendar = Calendar.getInstance();
        TaskDate editedStartDate = new TaskDate();
        TaskDate editedEndDate = new TaskDate();
        if (startDate != null) {
            calendar.setTime(startDate.getTime());
            calendar.add(Calendar.YEAR, APPEND_INCREMENT);
            editedStartDate.setDateInLong(calendar.getTime().getTime());
            startDate.setTime(editedStartDate.getDate());
        } else {
            editedStartDate.setDateInLong((new TaskDate()).getDateInLong());
        }

        calendar.setTime(endDate.getTime());
        calendar.add(Calendar.YEAR, APPEND_INCREMENT);
        editedEndDate.setDateInLong(calendar.getTime().getTime());
        endDate.setTime(editedEndDate.getDate());

        TaskOcurrence newAppendedDate = new TaskOcurrence((Task) task, editedStartDate, editedEndDate);
        task.appendRecurringDate(newAppendedDate);
        repeatingTasks.appendTaskComponent(newAppendedDate);
    }

    /**
     * Updates Monthly recurring tasks to the their latest date slot.
     * 
     * @param task
     *            Recurring task to be considered.
     * @param startDate
     *            The start date of this task if any. Null values represents
     *            that start date is not present.
     * @param endDate
     *            The end date of the is task.
     * @param elapsedYear
     *            The months that have elapsed.
     */
    private void appendMonthlyRecurringTask(ReadOnlyTask task, Calendar startDate, Calendar endDate, int elapsedMonth) {
        // Append a new date to the current task
        // Append a new date to the current task
        Calendar calendar = Calendar.getInstance();
        TaskDate editedStartDate = new TaskDate();
        TaskDate editedEndDate = new TaskDate();
        if (startDate != null) {
            calendar.setTime(startDate.getTime());
            calendar.add(Calendar.MONTH, APPEND_INCREMENT);
            editedStartDate.setDateInLong(calendar.getTime().getTime());
            startDate.setTime(editedStartDate.getDate());
        } else {
            editedStartDate.setDateInLong((new TaskDate()).getDateInLong());
        }

        calendar.setTime(endDate.getTime());
        calendar.add(Calendar.MONTH, APPEND_INCREMENT);
        editedEndDate.setDateInLong(calendar.getTime().getTime());
        endDate.setTime(editedEndDate.getDate());

        TaskOcurrence newAppendedDate = new TaskOcurrence((Task) task, editedStartDate, editedEndDate);
        task.appendRecurringDate(newAppendedDate);
        repeatingTasks.appendTaskComponent(newAppendedDate);
    }

    /**
     * Updates Weekly recurring tasks to the their latest date slot.
     * 
     * @param task
     *            Recurring task to be considered.
     * @param startDate
     *            The start date of this task if any. Null values represents
     *            that start date is not present.
     * @param endDate
     *            The end date of the is task.
     * @param elapsedYear
     *            The weeks that have elapsed.
     */
    private void appendWeeklyRecurringTask(ReadOnlyTask task, Calendar startDate, Calendar endDate, int elapsedWeek) {
        // Append a new date to the current task
        Calendar calendar = Calendar.getInstance();
        TaskDate editedStartDate = new TaskDate();
        TaskDate editedEndDate = new TaskDate();
        if (startDate != null) {
            calendar.setTime(startDate.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, NUMBER_OF_DAYS_IN_A_WEEK);
            editedStartDate.setDateInLong(calendar.getTime().getTime());
            startDate.setTime(editedStartDate.getDate());
        } else {
            editedStartDate.setDateInLong((new TaskDate()).getDateInLong());
        }

        calendar.setTime(endDate.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, NUMBER_OF_DAYS_IN_A_WEEK);
        editedEndDate.setDateInLong(calendar.getTime().getTime());
        endDate.setTime(editedEndDate.getDate());

        TaskOcurrence newAppendedDate = new TaskOcurrence((Task) task, editedStartDate, editedEndDate);
        task.appendRecurringDate(newAppendedDate);
        repeatingTasks.appendTaskComponent(newAppendedDate);
    }

    /**
     * Updates Daily recurring tasks to the their latest date slot.
     * 
     * @param task
     *            Recurring task to be considered.
     * @param startDate
     *            The start date of this task if any. Null values represents
     *            that start date is not present.
     * @param endDate
     *            The end date of the is task.
     * @param elapsedYear
     *            The days that have elapsed.
     */
    private void appendDailyRecurringTask(ReadOnlyTask task, Calendar startDate, Calendar endDate, int elapsedDay) {
        // Append a new date to the current task
        Calendar calendar = Calendar.getInstance();
        TaskDate editedStartDate = new TaskDate();
        TaskDate editedEndDate = new TaskDate();
        if (startDate != null) {
            calendar.setTime(startDate.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, APPEND_INCREMENT);
            editedStartDate.setDateInLong(calendar.getTime().getTime());
            startDate.setTime(editedStartDate.getDate());
        } else {
            editedStartDate.setDateInLong((new TaskDate()).getDateInLong());
        }

        calendar.setTime(endDate.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, APPEND_INCREMENT);
        editedEndDate.setDateInLong(calendar.getTime().getTime());
        endDate.setTime(editedEndDate.getDate());

        TaskOcurrence newAppendedDate = new TaskOcurrence((Task) task, editedStartDate, editedEndDate);
        task.appendRecurringDate(newAppendedDate);
        repeatingTasks.appendTaskComponent(newAppendedDate);
    }

    public static RecurringTaskManager getInstance() {
        if (instance == null) {
            instance = new RecurringTaskManager();
        }
        return instance;
    }
}
