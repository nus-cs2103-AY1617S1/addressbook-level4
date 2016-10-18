package seedu.address.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.TaskList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskDateComponent;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Handles the behaviour of recurring tasks
 * Dictates when should the recurring tasks be shown
 * This class is using a singleton pattern.
 * Use RecurringTaskManager.getInstance() to get the instance of the class
 * @author User
 *
 */
public class RecurringTaskManager {
    private static final int UPDATE_THRESHOLD = 1;
    private static final int NUMBER_OF_DAYS_IN_A_WEEK = 7;
    private static RecurringTaskManager instance;
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    
    private UniqueTaskList repeatingTasks;
    private Date initialisedDate;
    
    private RecurringTaskManager() {
    }
    
    /**
     * Initalised the time for recurring task manager to track
     * Updates any of the outdated task to their recurring next date 
     */
    public void setInitialisedTime() {
        updateRecurringTasks(); // updates once every boot up
        initialisedDate = new Date();
    }
    
    public void setTaskList(UniqueTaskList referenceList) {
        repeatingTasks = referenceList;
    }
    
    /**
     * Only updates if a day has elapsed
     * Updates tasks that have not been done
     */
    public void update() {
        assert repeatingTasks != null : "Repeating Task list reference cannot be null";
        //long daysElapsed = ChronoUnit.DAYS.between(initialisedTime, LocalDate.now());
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis((new Date().getTime()) - initialisedDate.getTime());
        int daysElapsed = c.get(Calendar.DAY_OF_MONTH) - 1;
        if (daysElapsed < UPDATE_THRESHOLD) {
            return;
        }
        updateRecurringTasks();
    }
    
    public void updateRecurringTasks() {
        assert repeatingTasks != null : "Repeating Task list reference cannot be null";
        logger.info("=============================[ RecurringTaskManager Updating ]===========================");
        for(ReadOnlyTask task : repeatingTasks){
            if (task.getRecurringType().equals(RecurringType.NONE)) {
                continue;
            }
            updateRecurringTask(task);
        }
    }

    /**
     * Updates any of the recurring tasks to their next available date slot
     * 
     * @param Recurring task to be updated
     * @return True if the recurring task has been updated
     *          False if the recurring tasks has not been updated;
     */
    private boolean updateRecurringTask(ReadOnlyTask task) {
        List<TaskDateComponent> dateComponents = task.getTaskDateComponent();
        TaskDateComponent lastAddedComponent =  dateComponents.get(dateComponents.size()-1);
        Calendar currentDate = new GregorianCalendar();
        Calendar startDate = new GregorianCalendar();
        Calendar endDate = new GregorianCalendar();
        Calendar resultingDate = new GregorianCalendar();
        currentDate = Calendar.getInstance();
        startDate.setTime(lastAddedComponent.getStartDate().getDate());
        endDate.setTime(lastAddedComponent.getEndDate().getDate());
        resultingDate.setTimeInMillis(currentDate.getTimeInMillis() - endDate.getTimeInMillis());
        
        if(!lastAddedComponent.getStartDate().isValid()) {
            startDate = null;
        }
        if ((currentDate.getTimeInMillis() - endDate.getTimeInMillis()) < 0) {
            return false;
        }
        switch (task.getRecurringType()) {
            case DAILY:
                final int elapsedDay = resultingDate.get(Calendar.DAY_OF_MONTH)-1;
                if (elapsedDay >= 1) {
                    updateDailyRecurringTask(task, startDate, endDate, elapsedDay);
                    return true;
                }
                break;
            case WEEKLY:
                System.out.println(resultingDate.get(Calendar.DAY_OF_MONTH)-1);
                final int elapsedWeek = (resultingDate.get(Calendar.DAY_OF_MONTH)-1) / NUMBER_OF_DAYS_IN_A_WEEK;
                if (elapsedWeek >= 1) {
                    updateWeeklyRecurringTask(task, startDate, endDate, elapsedWeek);
                    return true;
                } 
                final int weekRemainder = (resultingDate.get(Calendar.DAY_OF_MONTH)-1) % NUMBER_OF_DAYS_IN_A_WEEK;
                if (weekRemainder > 0) {
                    updateWeeklyRecurringTask(task, startDate, endDate, 1);
                    return true;
                }
                break;
            case MONTHLY:
                final int elapsedMonth = resultingDate.get(Calendar.MONTH);
                if (elapsedMonth >= 1){
                    updateMonthlyRecurringTask(task, startDate, endDate, elapsedMonth);
                    return true;
                }
                break;
            case YEARLY:
                final int elapsedYear = resultingDate.get(Calendar.YEAR);
                if (elapsedYear >= 1) {
                    updateYearlyRecurringTask(task, startDate, endDate, elapsedYear);
                    return true;
                }
                break;
            default:
                assert true : "Failed to set recurring type";
        }
        return false;
    }

    /**
     * Updates Yearly recurring tasks to the their latest date slot.
     * 
     * @param task Recurring task to be considered.
     * @param startDate The start date of this task if any. Null values represents that start date is not present.
     * @param endDate The end date of the is task.
     * @param elapsedYear The years that have elapsed.
     */
    private void updateYearlyRecurringTask(ReadOnlyTask task, Calendar startDate,
            Calendar endDate, int elapsedYear) {
        // Append a new date to the current task
        Calendar calendar = Calendar.getInstance();
        TaskDate editedStartDate = new TaskDate();
        TaskDate editedEndDate = new TaskDate();
        if(startDate != null) {
            calendar.setTime(startDate.getTime());
            calendar.add(Calendar.YEAR, elapsedYear);
            editedStartDate.setDateInLong(calendar.getTime().getTime());
        }else {
            editedStartDate.setDateInLong((new TaskDate()).getDateInLong());
        }

        calendar.setTime(endDate.getTime());
        calendar.add(Calendar.YEAR, elapsedYear);
        editedEndDate.setDateInLong(calendar.getTime().getTime());
        
        int idx = repeatingTasks.getInternalComponentList().indexOf(task.getTaskDateComponent().get(0));
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent((Task)task, new TaskDate(editedStartDate), new TaskDate(editedEndDate));
        repeatingTasks.getInternalComponentList().set(idx, newRepeatingTaskToAdd);
    }

    /**
     * Updates Monthly recurring tasks to the their latest date slot.
     * 
     * @param task Recurring task to be considered.
     * @param startDate The start date of this task if any. Null values represents that start date is not present.
     * @param endDate The end date of the is task.
     * @param elapsedYear The months that have elapsed.
     */
    private void updateMonthlyRecurringTask(ReadOnlyTask task, Calendar startDate,
            Calendar endDate, int elapsedMonth) {
        // Append a new date to the current task
        // Append a new date to the current task
        Calendar calendar = Calendar.getInstance();
        TaskDate editedStartDate = new TaskDate();
        TaskDate editedEndDate = new TaskDate();
        if(startDate != null) {
            calendar.setTime(startDate.getTime());
            calendar.add(Calendar.MONTH, elapsedMonth);
            editedStartDate.setDateInLong(calendar.getTime().getTime());
        }else {
            editedStartDate.setDateInLong((new TaskDate()).getDateInLong());
        }

        calendar.setTime(endDate.getTime());
        calendar.add(Calendar.MONTH, elapsedMonth);
        editedEndDate.setDateInLong(calendar.getTime().getTime());
        
        int idx = repeatingTasks.getInternalComponentList().indexOf(task.getTaskDateComponent().get(0));
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent((Task)task, new TaskDate(editedStartDate), new TaskDate(editedEndDate));
        repeatingTasks.getInternalComponentList().set(idx, newRepeatingTaskToAdd);
    }

    /**
     * Updates Weekly recurring tasks to the their latest date slot.
     * 
     * @param task Recurring task to be considered.
     * @param startDate The start date of this task if any. Null values represents that start date is not present.
     * @param endDate The end date of the is task.
     * @param elapsedYear The weeks that have elapsed.
     */
    private void updateWeeklyRecurringTask(ReadOnlyTask task, Calendar startDate,
            Calendar endDate, int elapsedWeek) {
        // Append a new date to the current task
        Calendar calendar = Calendar.getInstance();
        TaskDate editedStartDate = new TaskDate();
        TaskDate editedEndDate = new TaskDate();
        if(startDate != null) {
            calendar.setTime(startDate.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, elapsedWeek * NUMBER_OF_DAYS_IN_A_WEEK);
            editedStartDate.setDateInLong(calendar.getTime().getTime());
        }else {
            editedStartDate.setDateInLong((new TaskDate()).getDateInLong());
        }

        calendar.setTime(endDate.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, elapsedWeek * NUMBER_OF_DAYS_IN_A_WEEK);
        editedEndDate.setDateInLong(calendar.getTime().getTime());
        
        int idx = repeatingTasks.getInternalComponentList().indexOf(task.getTaskDateComponent().get(0));
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent((Task)task, new TaskDate(editedStartDate), new TaskDate(editedEndDate));
        repeatingTasks.getInternalComponentList().set(idx, newRepeatingTaskToAdd);
    }

    /**
     * Updates Daily recurring tasks to the their latest date slot.
     * 
     * @param task Recurring task to be considered.
     * @param startDate The start date of this task if any. Null values represents that start date is not present.
     * @param endDate The end date of the is task.
     * @param elapsedYear The days that have elapsed.
     */
    private void updateDailyRecurringTask(ReadOnlyTask task, Calendar startDate,
            Calendar endDate, int elapsedDay) {
        // Append a new date to the current task
        Calendar calendar = Calendar.getInstance();
        TaskDate editedStartDate = new TaskDate();
        TaskDate editedEndDate = new TaskDate();
        if(startDate != null) {
            calendar.setTime(startDate.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, elapsedDay);
            editedStartDate.setDateInLong(calendar.getTime().getTime());
        }else {
            editedStartDate.setDateInLong((new TaskDate()).getDateInLong());
        }

        calendar.setTime(endDate.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, elapsedDay);
        editedEndDate.setDateInLong(calendar.getTime().getTime());
        
        int idx = repeatingTasks.getInternalComponentList().indexOf(task.getTaskDateComponent().get(0));
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent((Task)task, new TaskDate(editedStartDate), new TaskDate(editedEndDate));
        repeatingTasks.getInternalComponentList().set(idx, newRepeatingTaskToAdd);
    }
    

    public static RecurringTaskManager getInstance() {
        if (instance == null) {
            instance = new RecurringTaskManager();
        }
        return instance;
    }

    /**
     * Remove completed recurring task to not show until their next recurring date
     * 
     * @param task Recurring task to be considered for removal
     */
    public void removeCompletedRecurringTasks(TaskList task) {
        List<TaskDateComponent> components = task.getTaskComponentList();
        List<ReadOnlyTask> toBeDeleted = new ArrayList<ReadOnlyTask>();
        for(TaskDateComponent t : components) {
            if (t.getTaskReference().getRecurringType() == RecurringType.NONE) {
                continue;
            }
            Date toConsider = t.getEndDate().getDate();
            Date today = new Date();
            Calendar calendar = Calendar.getInstance();
            Calendar toBeConsidered = new GregorianCalendar();
            toBeConsidered.setTime(toConsider);
            Calendar todayDate = new GregorianCalendar();
            todayDate.setTime(today);
            calendar.setTimeInMillis(todayDate.getTimeInMillis() - toBeConsidered.getTimeInMillis());

            long elapsedVal = 0;
            System.out.println(calendar.get(Calendar.DAY_OF_MONTH) - 1);
            switch(t.getTaskReference().getRecurringType()) {
                case DAILY:
                    elapsedVal = calendar.get(Calendar.DAY_OF_MONTH) - 1; 
                    break;
                case WEEKLY:
                    elapsedVal = (calendar.get(Calendar.DAY_OF_MONTH) - 1) / NUMBER_OF_DAYS_IN_A_WEEK;
                    break;
                case MONTHLY:
                    elapsedVal = calendar.get(Calendar.MONTH);
                    break;
                case YEARLY:
                    elapsedVal = calendar.get(Calendar.YEAR);
                    break;
                default:
                    break;
            }
            
            if ( elapsedVal > 0) {
                toBeDeleted.add(t.getTaskReference());
            }
        }
        for(ReadOnlyTask t : toBeDeleted) {
            try {
                task.removeTask(t);
            } catch (TaskNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles the saving of recurring tasks in the xml file.
     * 
     * @param start Start date of the recurring task.
     * @param end End date of the recurring task.
     * @param type Whether the task is daily, weekly, monthly or yearly task.
     */
    public void handleRecurringTaskSaving(TaskDate start, TaskDate end, RecurringType type) {
        Calendar local = Calendar.getInstance();
        switch(type) {
            case DAILY:
                local.setTime(end.getDate());
                local.add(Calendar.DAY_OF_MONTH, 1);
                end.setDateInLong(local.getTimeInMillis());
                if (start.getDateInLong() != TaskDate.DATE_NOT_PRESENT) {
                    local.setTime(start.getDate());
                    local.add(Calendar.DAY_OF_MONTH, 1);
                    start.setDateInLong(local.getTimeInMillis());                    
                }
                break;
            case WEEKLY:
                local.setTime(end.getDate());
                local.add(Calendar.DAY_OF_MONTH, NUMBER_OF_DAYS_IN_A_WEEK);
                end.setDateInLong(local.getTimeInMillis());
                if (start.getDateInLong() != TaskDate.DATE_NOT_PRESENT) {
                    local.setTime(start.getDate());
                    local.add(Calendar.DAY_OF_MONTH, NUMBER_OF_DAYS_IN_A_WEEK);
                    start.setDateInLong(local.getTimeInMillis());                    
                }
                break;
            case MONTHLY:
                local.setTime(end.getDate());
                local.add(Calendar.MONTH, 1);
                end.setDateInLong(local.getTimeInMillis());
                if (start.getDateInLong() != TaskDate.DATE_NOT_PRESENT) {
                    local.setTime(start.getDate());
                    local.add(Calendar.MONTH, 1);
                    start.setDateInLong(local.getTimeInMillis());                    
                }
                break;
            case YEARLY:
                local.setTime(end.getDate());
                local.add(Calendar.YEAR, 1);
                end.setDateInLong(local.getTimeInMillis());
                if (start.getDateInLong() != TaskDate.DATE_NOT_PRESENT) {
                    local.setTime(start.getDate());
                    local.add(Calendar.YEAR, 1);
                    start.setDateInLong(local.getTimeInMillis());                    
                }
                break;       
        }
    }
}
