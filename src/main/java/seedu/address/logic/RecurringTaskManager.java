package seedu.address.logic;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
 * @author User
 *
 */
public class RecurringTaskManager {
    private static final int NUMBER_OF_DAYS_IN_A_WEEK = 7;
    private static RecurringTaskManager instance;
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    
    private LocalDate initialisedTime;
    private UniqueTaskList repeatingTasks;
    
    private RecurringTaskManager() {
    }
    
    public void setInitialisedTime() {
        initialisedTime = LocalDate.now();
        updateRecurringTasks(); // updates once every boot up
    }
    
    public void setTaskList(UniqueTaskList referenceList) {
        repeatingTasks = referenceList;
    }
    
    /**
     * Only updates if a day has elapsed
     */
    public void update() {
        assert repeatingTasks != null : "Repeating Task list reference cannot be null";
        long daysElapsed = ChronoUnit.DAYS.between(initialisedTime, LocalDate.now());
        if (daysElapsed <= 0) {
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
            isUpdateRecurringTask(task);
        }
    }

    private boolean isUpdateRecurringTask(ReadOnlyTask task) {
        List<TaskDateComponent> dateComponents = task.getTaskDateComponent();
        TaskDateComponent lastAddedComponent =  dateComponents.get(dateComponents.size()-1);
        Calendar currentDate = new GregorianCalendar();
        Calendar startDate = new GregorianCalendar();
        Calendar endDate = new GregorianCalendar();
        Calendar resultingDate = new GregorianCalendar();
        currentDate.setTime(new Date());
        startDate.setTime(lastAddedComponent.getStartDate().getDate());
        endDate.setTime(lastAddedComponent.getEndDate().getDate());
        resultingDate.setTimeInMillis(currentDate.getTimeInMillis() - endDate.getTimeInMillis());
        
        if(!lastAddedComponent.getStartDate().isValid()) {
            startDate = null;
        }
        switch (task.getRecurringType()) {
            case DAILY:
                final int elapsedDay = resultingDate.get(Calendar.DAY_OF_MONTH)-1;
                if (elapsedDay >= 1) {
                    executeDailyRecurringTask(task, startDate, endDate, elapsedDay);
                    return true;
                }
                break;
            case WEEKLY:
                final int elapsedWeek = (resultingDate.get(Calendar.DAY_OF_MONTH)-1) / NUMBER_OF_DAYS_IN_A_WEEK;
                if (elapsedWeek >= 1) {
                    executeWeeklyRecurringTask(task, startDate, endDate, elapsedWeek);
                    return true;
                }
                break;
            case MONTHLY:
                final int elapsedMonth = resultingDate.get(Calendar.MONTH);
                if (elapsedMonth >= 1){
                    executeMonthlyRecurringTask(task, startDate, endDate, elapsedMonth);
                    return true;
                }
                break;
            case YEARLY:
                final int elapsedYear = resultingDate.get(Calendar.YEAR);
                if (elapsedYear >= 1) {
                    executeYearlyRecurringTask(task, startDate, endDate, elapsedYear);
                    return true;
                }
                break;
            default:
                assert true : "Failed to set recurring type";
        }
        return false;
    }

    private void executeYearlyRecurringTask(ReadOnlyTask task, Calendar startDate,
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

    private void executeMonthlyRecurringTask(ReadOnlyTask task, Calendar startDate,
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

    private void executeWeeklyRecurringTask(ReadOnlyTask task, Calendar startDate,
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

    private void executeDailyRecurringTask(ReadOnlyTask task, Calendar startDate,
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
    
    public void archiveRecurringTask(ReadOnlyTask task) {
        switch(task.getRecurringType()) {
        case DAILY:
            archiveDailyRecurringTask(task);
            break;
        case WEEKLY:
            archiveWeeklyRecurringTask(task);
            break;
        case MONTHLY:
            archiveMonthlyRecurringTask(task);
            break;
        case YEARLY:
            archiveYearlyRecurringTask(task);
            break;
        }
    }

    private void archiveYearlyRecurringTask(ReadOnlyTask task) {
        Task mutableTask = (Task)task;
        TaskDateComponent component = mutableTask.getTaskDateComponent().get(0);
        
        LocalDate localStartDate;
        Date convertedStartDate;
        LocalDate localEndDate;
        Date convertedEndDate;
        
        if (component.getStartDate().isValid()) {
            localStartDate = mutableTask.getStartDate().getDate().
                    toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            localStartDate = localStartDate.plusYears(1);
            convertedStartDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            
        } else {
            convertedStartDate = (new TaskDate()).getDate();
        }
        localEndDate = mutableTask.getEndDate().getDate().
                toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localEndDate = localEndDate.plusYears(1);
        convertedEndDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        component.setStartDate(new TaskDate(convertedStartDate));
        component.setEndDate(new TaskDate(convertedEndDate) );
    }

    private void archiveMonthlyRecurringTask(ReadOnlyTask task) {
        Task mutableTask = (Task)task;
        TaskDateComponent component = mutableTask.getTaskDateComponent().get(0);
        
        LocalDate localStartDate;
        Date convertedStartDate;
        LocalDate localEndDate;
        Date convertedEndDate;
        
        if (component.getStartDate().isValid()) {
            localStartDate = mutableTask.getStartDate().getDate().
                    toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            localStartDate = localStartDate.plusMonths(1);
            convertedStartDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            
        } else {
            convertedStartDate = (new TaskDate()).getDate();
        }
        localEndDate = mutableTask.getEndDate().getDate().
                toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localEndDate = localEndDate.plusMonths(1);
        convertedEndDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        component.setStartDate(new TaskDate(convertedStartDate));
        component.setEndDate(new TaskDate(convertedEndDate) );        
    }

    private void archiveWeeklyRecurringTask(ReadOnlyTask task) {
        Task mutableTask = (Task)task;
        TaskDateComponent component = mutableTask.getTaskDateComponent().get(0);
        
        LocalDate localStartDate;
        Date convertedStartDate;
        LocalDate localEndDate;
        Date convertedEndDate;
        
        if (component.getStartDate().isValid()) {
            localStartDate = mutableTask.getStartDate().getDate().
                    toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            localStartDate = localStartDate.plusWeeks(1);
            convertedStartDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            
        } else {
            convertedStartDate = (new TaskDate()).getDate();
        }
        localEndDate = mutableTask.getEndDate().getDate().
                toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localEndDate = localEndDate.plusWeeks(1);
        convertedEndDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        component.setStartDate(new TaskDate(convertedStartDate));
        component.setEndDate(new TaskDate(convertedEndDate) );        
    }

    private void archiveDailyRecurringTask(ReadOnlyTask task) {
        Task mutableTask = (Task)task;
        TaskDateComponent component = mutableTask.getTaskDateComponent().get(0);
        
        LocalDate localStartDate;
        Date convertedStartDate;
        LocalDate localEndDate;
        Date convertedEndDate;
        
        if (component.getStartDate().isValid()) {
            localStartDate = component.getStartDate().getDate().
                    toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            localStartDate = localStartDate.plusDays(1);
            convertedStartDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            
        } else {
            convertedStartDate = (new TaskDate()).getDate();
        }
        localEndDate = component.getEndDate().getDate().
                toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localEndDate = localEndDate.plusDays(1);
        convertedEndDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        component.setStartDate(new TaskDate(convertedStartDate));
        component.setEndDate(new TaskDate(convertedEndDate) );        
    }

    public static RecurringTaskManager getInstance() {
        if (instance == null) {
            instance = new RecurringTaskManager();
        }
        return instance;
    }

    public void removeCompletedRecurringTasks(TaskList initialData) {
        List<TaskDateComponent> components = initialData.getTaskComponentList();
        List<ReadOnlyTask> toBeDeleted = new ArrayList<ReadOnlyTask>();
        for(TaskDateComponent t : components) {
            if (t.getTaskReference().getRecurringType() == RecurringType.NONE) {
                continue;
            }
            Date toConsider = t.getEndDate().getDate();
            LocalDate toConsiderConverted = toConsider.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate today = LocalDate.now();
            long elapsedVal = 0;
            switch(t.getTaskReference().getRecurringType()) {
                case DAILY:
                    elapsedVal = ChronoUnit.DAYS.between(toConsiderConverted, today); 
                    break;
                case WEEKLY:
                    elapsedVal = ChronoUnit.WEEKS.between(toConsiderConverted, today);
                    break;
                case MONTHLY:
                    elapsedVal = ChronoUnit.MONTHS.between(toConsiderConverted, today);
                    break;
                case YEARLY:
                    elapsedVal = ChronoUnit.YEARS.between(toConsiderConverted, today);
                    break;
                default:
                    break;
            }
            
            if ( elapsedVal < 0) {
                toBeDeleted.add(t.getTaskReference());
            }
        }
        for(ReadOnlyTask t : toBeDeleted) {
            try {
                initialData.removeTask(t);
            } catch (TaskNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleRecurringTask(TaskDate start, TaskDate end, RecurringType type) {
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
                break;        }
    }
}
