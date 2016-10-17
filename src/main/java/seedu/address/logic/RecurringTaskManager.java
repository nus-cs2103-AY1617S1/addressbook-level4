package seedu.address.logic;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskDateComponent;
import seedu.address.model.task.TaskType;
import seedu.address.model.task.UniqueTaskList;

public class RecurringTaskManager {
    private static RecurringTaskManager instance;
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    
    private LocalDate initialisedTime;
    private UniqueTaskList repeatingTasks;
    private boolean hasUpdates;
    private RecurringTaskManager() {
        hasUpdates = false;
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
        switch (task.getRecurringType()) {
            case DAILY:
                long elapsedDay = ChronoUnit.DAYS.between(lastAddedEndDate, currentDate);
                if (elapsedDay >= 1) {
                    for(int step = 0; step < elapsedDay; step++) {
                        lastAddedComponent = dateComponents.get(dateComponents.size()-1);
                        if (lastAddedComponent.getStartDate().getDateInLong() != (TaskDate.DATE_NOT_PRESENT)){
                            lastAddedStartDate = lastAddedComponent.getStartDate().getDate().
                                toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        } else {
                            lastAddedStartDate = null;
                        }
                        lastAddedEndDate = lastAddedComponent.getEndDate().getDate().
                                toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        appendDailyRecurringTask(task, lastAddedStartDate, lastAddedEndDate);
                    }
                    return true;
                }
                break;
            case WEEKLY:
                long elapsedWeek = ChronoUnit.WEEKS.between(lastAddedEndDate, currentDate);
                if (elapsedWeek >= 1) {
                    for(int step = 0; step < elapsedWeek; step++) {
                        lastAddedComponent = dateComponents.get(dateComponents.size()-1);
                        if (lastAddedComponent.getStartDate().getDateInLong() != (TaskDate.DATE_NOT_PRESENT)){
                            lastAddedStartDate = lastAddedComponent.getStartDate().getDate().
                                toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        } else {
                            lastAddedStartDate = null;
                        }
                        lastAddedEndDate = lastAddedComponent.getEndDate().getDate().
                                toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        appendWeeklyRecurringTask(task, lastAddedStartDate, lastAddedEndDate);
                    }
                    return true;
                }
                break;
            case MONTHLY:
                long elapsedMonth = ChronoUnit.MONTHS.between(lastAddedEndDate, currentDate);
                if (elapsedMonth >= 1){
                    for(int step = 0; step < elapsedMonth; step++) {
                        lastAddedComponent = dateComponents.get(dateComponents.size()-1);
                        if (lastAddedComponent.getStartDate().getDateInLong() != (TaskDate.DATE_NOT_PRESENT)){
                            lastAddedStartDate = lastAddedComponent.getStartDate().getDate().
                                toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        } else {
                            lastAddedStartDate = null;
                        }
                        lastAddedEndDate = lastAddedComponent.getEndDate().getDate().
                                toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        appendMonthlyRecurringTask(task, lastAddedStartDate, lastAddedEndDate);
                    }
                    return true;
                }
                break;
            case YEARLY:
                long elapsedYear = ChronoUnit.YEARS.between(lastAddedEndDate, currentDate);
                if (elapsedYear >= 1) {
                    for(int step = 0; step < elapsedYear; step++) {
                        lastAddedComponent = dateComponents.get(dateComponents.size()-1);
                        if (lastAddedComponent.getStartDate().getDateInLong() != (TaskDate.DATE_NOT_PRESENT)){
                            lastAddedStartDate = lastAddedComponent.getStartDate().getDate().
                                toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        } else {
                            lastAddedStartDate = null;
                        }
                        lastAddedEndDate = lastAddedComponent.getEndDate().getDate().
                                toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        appendYearlyRecurringTask(task, lastAddedStartDate, lastAddedEndDate);
                    }
                    return true;
                }
                break;
            default:
                assert true : "Failed to set recurring type";
        }
        return false;
    }

    private void appendYearlyRecurringTask(ReadOnlyTask task, LocalDate lastAddedStartDate,
            LocalDate lastAddedEndDate) {
        LocalDate newLocalStartDate;
        Date convertedStartDate;
        
        if(lastAddedStartDate != null) {
            newLocalStartDate = lastAddedStartDate.plusYears(1);
            convertedStartDate = Date.from(newLocalStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }else {
            convertedStartDate = (new TaskDate()).getDate();
        }
        LocalDate newLocalEndDate = lastAddedEndDate.plusYears(1);
        Date convertedEndDate = Date.from(newLocalEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        Task mutableTask = (Task) task;
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent(mutableTask, new TaskDate(convertedStartDate), new TaskDate(convertedEndDate));
        mutableTask.appendRecurringTaskDate(newRepeatingTaskToAdd);
        
        repeatingTasks.getInternalComponentList().add(newRepeatingTaskToAdd);
    }

    private void appendMonthlyRecurringTask(ReadOnlyTask task, LocalDate lastAddedStartDate,
            LocalDate lastAddedEndDate) {
        LocalDate newLocalStartDate;
        Date convertedStartDate;
        
        if(lastAddedStartDate != null) {
            newLocalStartDate = lastAddedStartDate.plusMonths(1);
            convertedStartDate = Date.from(newLocalStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }else {
            convertedStartDate = (new TaskDate()).getDate();
        }
        LocalDate newLocalEndDate = lastAddedEndDate.plusMonths(1);
        Date convertedEndDate = Date.from(newLocalEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        Task mutableTask = (Task) task;
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent(mutableTask, new TaskDate(convertedStartDate), new TaskDate(convertedEndDate));
        mutableTask.appendRecurringTaskDate(newRepeatingTaskToAdd);
        
        repeatingTasks.getInternalComponentList().add(newRepeatingTaskToAdd);
    }

    private void appendWeeklyRecurringTask(ReadOnlyTask task, LocalDate lastAddedStartDate,
            LocalDate lastAddedEndDate) {
        // Append a new date to the current task
        LocalDate newLocalStartDate;
        Date convertedStartDate;
        
        if(lastAddedStartDate != null) {
            newLocalStartDate = lastAddedStartDate.plusWeeks(1);
            convertedStartDate = Date.from(newLocalStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }else {
            convertedStartDate = (new TaskDate()).getDate();
        }
        LocalDate newLocalEndDate = lastAddedEndDate.plusWeeks(1);
        Date convertedEndDate = Date.from(newLocalEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        Task mutableTask = (Task) task;
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent(mutableTask, new TaskDate(convertedStartDate), new TaskDate(convertedEndDate));
        mutableTask.appendRecurringTaskDate(newRepeatingTaskToAdd);
        
        repeatingTasks.getInternalComponentList().add(newRepeatingTaskToAdd);
    }

    private void appendDailyRecurringTask(ReadOnlyTask task, LocalDate lastAddedStartDate,
            LocalDate lastAddedEndDate) {
        // Append a new date to the current task
        LocalDate newLocalStartDate;
        Date convertedStartDate;
        
        if(lastAddedStartDate != null) {
            newLocalStartDate = lastAddedStartDate.plusDays(1);
            convertedStartDate = Date.from(newLocalStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }else {
            convertedStartDate = (new TaskDate()).getDate();
        }
        LocalDate newLocalEndDate = lastAddedEndDate.plusDays(1);
        Date convertedEndDate = Date.from(newLocalEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        Task mutableTask = (Task) task;
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent(mutableTask, new TaskDate(convertedStartDate), new TaskDate(convertedEndDate));
        mutableTask.appendRecurringTaskDate(newRepeatingTaskToAdd);
        
        repeatingTasks.getInternalComponentList().add(newRepeatingTaskToAdd);
    }

    public static RecurringTaskManager getInstance() {
        if (instance == null) {
            instance = new RecurringTaskManager();
        }
        return instance;
    }
}
