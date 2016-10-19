package seedu.address.logic;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
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

public class RecurringTaskManager {
    private static RecurringTaskManager instance;
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    
    private LocalDate initialisedTime;
    private UniqueTaskList repeatingTasks;
    private RecurringTaskManager() {
    }
    
    public void setInitialisedTime() {
        initialisedTime = LocalDate.now();
        updateRepeatingTasks(); // updates once every boot up
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
        updateRepeatingTasks();
    }
    
    public void updateRepeatingTasks() {
        assert repeatingTasks != null : "Repeating Task list reference cannot be null";
        logger.info("=============================[ RecurringTaskManager Updating ]===========================");
        for(ReadOnlyTask task : repeatingTasks){
            isUpdateRecurringTask(task);
        }
    }

    private boolean isUpdateRecurringTask(ReadOnlyTask task) {
        List<TaskDateComponent> dateComponents = task.getTaskDateComponent();
        TaskDateComponent lastAddedComponent =  dateComponents.get(dateComponents.size()-1);
        LocalDate currentDate = LocalDate.now();
        LocalDate lastAddedStartDate = lastAddedComponent.getStartDate().getDate().
                toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate lastAddedEndDate = lastAddedComponent.getEndDate().getDate().
                toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (!lastAddedComponent.getStartDate().isValid()) {
            lastAddedStartDate = null;
        }
        switch (task.getRecurringType()) {
            case DAILY:

                long elapsedDay = ChronoUnit.DAYS.between(lastAddedEndDate, currentDate);
                if (elapsedDay >= 1) {
                    executeDailyRecurringTask(task, lastAddedStartDate, lastAddedEndDate, elapsedDay);
                    return true;
                }
                break;
            case WEEKLY:
                long elapsedWeek = ChronoUnit.WEEKS.between(lastAddedEndDate, currentDate);
                if (elapsedWeek >= 1) {
                    executeWeeklyRecurringTask(task, lastAddedStartDate, lastAddedEndDate, elapsedWeek);
                    return true;
                }
                break;
            case MONTHLY:
                long elapsedMonth = ChronoUnit.MONTHS.between(lastAddedEndDate, currentDate);
                if (elapsedMonth >= 1){
                    executeMonthlyRecurringTask(task, lastAddedStartDate, lastAddedEndDate, elapsedMonth);
                    return true;
                }
                break;
            case YEARLY:
                long elapsedYear = ChronoUnit.YEARS.between(lastAddedEndDate, currentDate);
                if (elapsedYear >= 1) {
                    executeYearlyRecurringTask(task, lastAddedStartDate, lastAddedEndDate, elapsedYear);
                    return true;
                }
                break;
            default:
                assert true : "Failed to set recurring type";
        }
        return false;
    }

    private void executeYearlyRecurringTask(ReadOnlyTask task, LocalDate lastAddedStartDate,
            LocalDate lastAddedEndDate, long elapsedYear) {
        // Append a new date to the current task
        LocalDate newLocalStartDate;
        Date convertedStartDate;
        
        if(lastAddedStartDate != null) {
            newLocalStartDate = lastAddedStartDate.plusYears(elapsedYear);
            convertedStartDate = Date.from(newLocalStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }else {
            convertedStartDate = (new TaskDate()).getDate();
        }
        LocalDate newLocalEndDate = lastAddedEndDate.plusYears(elapsedYear);
        Date convertedEndDate = Date.from(newLocalEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        int idx = repeatingTasks.getInternalComponentList().indexOf(task.getTaskDateComponent().get(0));
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent((Task)task, new TaskDate(convertedStartDate), new TaskDate(convertedEndDate));
        repeatingTasks.getInternalComponentList().set(idx, newRepeatingTaskToAdd);
    }

    private void executeMonthlyRecurringTask(ReadOnlyTask task, LocalDate lastAddedStartDate,
            LocalDate lastAddedEndDate, long elapsedMonth) {
        // Append a new date to the current task
        LocalDate newLocalStartDate;
        Date convertedStartDate;
        
        if(lastAddedStartDate != null) {
            newLocalStartDate = lastAddedStartDate.plusMonths(elapsedMonth);
            convertedStartDate = Date.from(newLocalStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }else {
            convertedStartDate = (new TaskDate()).getDate();
        }
        LocalDate newLocalEndDate = lastAddedEndDate.plusMonths(elapsedMonth);
        Date convertedEndDate = Date.from(newLocalEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        int idx = repeatingTasks.getInternalComponentList().indexOf(task.getTaskDateComponent().get(0));
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent((Task)task, new TaskDate(convertedStartDate), new TaskDate(convertedEndDate));
        repeatingTasks.getInternalComponentList().set(idx, newRepeatingTaskToAdd);
    }

    private void executeWeeklyRecurringTask(ReadOnlyTask task, LocalDate lastAddedStartDate,
            LocalDate lastAddedEndDate, long elapsedWeek) {
        // Append a new date to the current task
        LocalDate newLocalStartDate;
        Date convertedStartDate;
        
        if(lastAddedStartDate != null) {
            newLocalStartDate = lastAddedStartDate.plusWeeks(elapsedWeek);
            convertedStartDate = Date.from(newLocalStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }else {
            convertedStartDate = (new TaskDate()).getDate();
        }
        LocalDate newLocalEndDate = lastAddedEndDate.plusWeeks(elapsedWeek);
        Date convertedEndDate = Date.from(newLocalEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        int idx = repeatingTasks.getInternalComponentList().indexOf(task.getTaskDateComponent().get(0));
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent((Task)task, new TaskDate(convertedStartDate), new TaskDate(convertedEndDate));
        repeatingTasks.getInternalComponentList().set(idx, newRepeatingTaskToAdd);
    }

    private void executeDailyRecurringTask(ReadOnlyTask task, LocalDate lastAddedStartDate,
            LocalDate lastAddedEndDate, long elapsedDay) {
        // Append a new date to the current task
        LocalDate newLocalStartDate;
        Date convertedStartDate;
        
        if(lastAddedStartDate != null) {
            newLocalStartDate = lastAddedStartDate.plusDays(elapsedDay);
            convertedStartDate = Date.from(newLocalStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }else {
            convertedStartDate = (new TaskDate()).getDate();
        }
        LocalDate newLocalEndDate = lastAddedEndDate.plusDays(elapsedDay);
        Date convertedEndDate = Date.from(newLocalEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        int idx = repeatingTasks.getInternalComponentList().indexOf(task.getTaskDateComponent().get(0));
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent((Task)task, new TaskDate(convertedStartDate), new TaskDate(convertedEndDate));
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
}
