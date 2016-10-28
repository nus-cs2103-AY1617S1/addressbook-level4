package seedu.taskitty.model;

import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import seedu.taskitty.commons.core.ComponentManager;
import seedu.taskitty.commons.core.LogsCenter;
import seedu.taskitty.commons.core.UnmodifiableObservableList;
import seedu.taskitty.commons.events.model.TaskManagerChangedEvent;
import seedu.taskitty.commons.exceptions.NoPreviousValidCommandException;
import seedu.taskitty.commons.exceptions.NoRecentUndoCommandException;
import seedu.taskitty.commons.util.DateUtil;
import seedu.taskitty.commons.util.StringUtil;
import seedu.taskitty.logic.commands.AddCommand;
import seedu.taskitty.logic.commands.ClearCommand;
import seedu.taskitty.logic.commands.DeleteCommand;
import seedu.taskitty.logic.commands.DoneCommand;
import seedu.taskitty.logic.commands.EditCommand;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.model.task.Task;
import seedu.taskitty.model.task.UniqueTaskList;
import seedu.taskitty.model.task.UniqueTaskList.DuplicateMarkAsDoneException;
import seedu.taskitty.model.task.UniqueTaskList.TaskNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<Task> allTasks;

    private FilteredList<Task> filteredTodos;
    private FilteredList<Task> filteredDeadlines;
    private FilteredList<Task> filteredEvents;
    private ObservableValue<String> date;
    
    private final SessionTaskInfoStorage undoTaskInfo;
    private final SessionTaskInfoStorage redoTaskInfo;

    /**
     * Initializes a ModelManager with the given TaskManager
     * TaskManager and its variables should not be null
     */
    public ModelManager(TaskManager src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task manager: " + src + " and user prefs " + userPrefs);

        taskManager = new TaskManager(src);
        allTasks = new FilteredList<Task>(taskManager.getAllTasks());
        filteredTodos = new FilteredList<Task>(taskManager.getFilteredTodos());
        filteredDeadlines = new FilteredList<Task>(taskManager.getFilteredDeadlines());
        filteredEvents = new FilteredList<Task>(taskManager.getFilteredEvents());
        undoTaskInfo = new SessionTaskInfoStorage();
        redoTaskInfo = new SessionTaskInfoStorage();
        taskManager.sortList();
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        taskManager = new TaskManager(initialData);
        allTasks = new FilteredList<Task>(taskManager.getAllTasks());
        filteredTodos = new FilteredList<Task>(taskManager.getFilteredTodos());
        filteredDeadlines = new FilteredList<Task>(taskManager.getFilteredDeadlines());
        filteredEvents = new FilteredList<Task>(taskManager.getFilteredEvents());
        undoTaskInfo = new SessionTaskInfoStorage();
        redoTaskInfo = new SessionTaskInfoStorage();
        taskManager.sortList();
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    //@@author A0139052L
    @Override
    public synchronized void deleteTasks(List<ReadOnlyTask> taskList) throws TaskNotFoundException {
        for (ReadOnlyTask targetTask: taskList) {
            taskManager.removeTask(targetTask);
        }
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {        
        taskManager.addTask(task);
        indicateTaskManagerChanged();
    }    
        
    //@@author A0130853L
    @Override
    public synchronized void markTasksAsDone(List<ReadOnlyTask> taskList) throws UniqueTaskList.TaskNotFoundException, DuplicateMarkAsDoneException{
        for (ReadOnlyTask targetTask: taskList) {
            taskManager.markTaskAsDoneTask(targetTask);
        }
    	indicateTaskManagerChanged();
    }
    
    //@@author A0135793W
   	@Override
    public synchronized void editTask(ReadOnlyTask target, Task task) throws UniqueTaskList.TaskNotFoundException, UniqueTaskList.DuplicateTaskException {   	    
   	    taskManager.addTask(task);
        indicateTaskManagerChanged();
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }
   	
    //@@author A0139052L   	
   	@Override
    public synchronized void storeAddCommandInfo(ReadOnlyTask addedTask, String commandText) {
        undoTaskInfo.storeCommandWord(AddCommand.COMMAND_WORD);
        undoTaskInfo.storeTask(addedTask);
        undoTaskInfo.storeCommandText(AddCommand.COMMAND_WORD + commandText);
        redoTaskInfo.clear();
    }
   	
   	@Override
    public synchronized void storeEditCommandInfo(ReadOnlyTask taskBeforeEdit, ReadOnlyTask taskAfterEdit, String commandText) {
   	    undoTaskInfo.storeCommandWord(EditCommand.COMMAND_WORD);
   	    undoTaskInfo.storeTask(taskAfterEdit);
   	    undoTaskInfo.storeTask(taskBeforeEdit);
   	    undoTaskInfo.storeCommandText(EditCommand.COMMAND_WORD + commandText);
   	    redoTaskInfo.clear();
   	}
   	
   	@Override
    public synchronized void storeDeleteCommandInfo(List<ReadOnlyTask> deletedTasks, String commandText) {
        undoTaskInfo.storeCommandWord(DeleteCommand.COMMAND_WORD);
        for (ReadOnlyTask deletedTask: deletedTasks) {
            undoTaskInfo.storeTask(deletedTask);
        }
        undoTaskInfo.storeNumberOfTasks(deletedTasks.size());
        undoTaskInfo.storeCommandText(DeleteCommand.COMMAND_WORD + commandText);
        redoTaskInfo.clear();
    }
   	
   	@Override
    public synchronized void storeDoneCommandInfo(List<ReadOnlyTask> markedTasks, String commandText) {
        undoTaskInfo.storeCommandWord(DoneCommand.COMMAND_WORD);
        for (ReadOnlyTask markedTask: markedTasks) {
            undoTaskInfo.storeTask(markedTask);
        }
        undoTaskInfo.storeNumberOfTasks(markedTasks.size());
        undoTaskInfo.storeCommandText(DoneCommand.COMMAND_WORD + commandText);
        redoTaskInfo.clear();
    }
   	
   	@Override
    public synchronized void storeClearCommandInfo() {
        undoTaskInfo.storeCommandWord(ClearCommand.COMMAND_WORD);
        undoTaskInfo.storeTaskManager(new TaskManager(taskManager));
        undoTaskInfo.storeCommandText(ClearCommand.COMMAND_WORD);
        redoTaskInfo.clear();
    }
   	
   	@Override     
    public synchronized String undo() throws NoPreviousValidCommandException {
        if (!undoTaskInfo.hasPreviousValidCommand()) {
            throw new NoPreviousValidCommandException(null);
        }
        return revertBackPreviousState(undoTaskInfo, redoTaskInfo, false);        
    }        
    
   	@Override     
    public synchronized String redo() throws NoRecentUndoCommandException {
        if (!redoTaskInfo.hasRecentUndoCommand()) {
            throw new NoRecentUndoCommandException(null);
        }
        return revertBackPreviousState(redoTaskInfo, undoTaskInfo, true);        
    } 
    //@@author
    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getTaskList() {
        return new UnmodifiableObservableList<>(allTasks);
    }
    
    //@@author A0139930B
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTodoList() {
        return new UnmodifiableObservableList<>(filteredTodos);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDeadlineList() {
        return new UnmodifiableObservableList<>(filteredDeadlines);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredEventList() {
        return new UnmodifiableObservableList<>(filteredEvents);
    }
    
    //@@author
    @Override
    public void updateFilteredListToShowAll() {
        allTasks.setPredicate(null);
        filteredTodos.setPredicate(null);
        filteredDeadlines.setPredicate(null);
        filteredEvents.setPredicate(null);
    }
    
    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }
    //@@author A0130853L
    @Override
    public void updateFilteredDoneList() {
    	updateFilteredTaskList(new PredicateExpression(p -> p.getIsDone() == true));
    }
    
    @Override
    public void updateToDefaultList() {
    	allTasks.setPredicate(p -> !p.getIsDone() && (p.isTodo() || p.isDeadline() || isEventAndIsNotBeforeToday(p)));
    	filteredTodos.setPredicate(p -> !p.getIsDone());
    	filteredDeadlines.setPredicate(p -> !p.getIsDone());
    	filteredEvents.setPredicate(p -> !p.getIsDone() && isEventAndIsNotBeforeToday(p));
    }

	@Override
	public void updateFilteredDateTaskList(LocalDate date) {
		allTasks.setPredicate(p -> p.isTodo() || isDeadlineAndIsNotAfterDate(p, date) || isEventAndDateIsWithinEventPeriod(p, date));
		filteredTodos.setPredicate(null);
		filteredDeadlines.setPredicate(p -> isDeadlineAndIsNotAfterDate(p, date));
		filteredEvents.setPredicate(p -> isEventAndDateIsWithinEventPeriod(p, date));
	}
	
	//@@author
    private void updateFilteredTaskList(Expression expression) {
        allTasks.setPredicate(expression::satisfies);
        filteredTodos.setPredicate(expression::satisfies);
        filteredDeadlines.setPredicate(expression::satisfies);
        filteredEvents.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask person);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask person) {
            return qualifier.run(person);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask person);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }
        
        //@@author A0130853L
        @Override
        public boolean run(ReadOnlyTask person) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(person.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }
        
        //@@author
        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    
    
  //========== Inner class used for storing info needed for undoing/redoing functions ==================================================
    //@@author A0139052L
    private class SessionTaskInfoStorage {
        
        private final Stack<String> historyCommandWords;
        private final Stack<String> historyCommandTexts;
        private final Stack<ReadOnlyTask> historyTasks;
        private final Stack<Integer> historyNumberOfTasks;
        private final Stack<ReadOnlyTaskManager> historyTaskManagers;
        
        SessionTaskInfoStorage() {
            historyCommandWords = new Stack<String>();
            historyCommandTexts = new Stack<String>();
            historyTasks = new Stack<ReadOnlyTask>();
            historyNumberOfTasks = new Stack<Integer>();
            historyTaskManagers = new Stack<ReadOnlyTaskManager>();
        }
        
        private boolean hasPreviousValidCommand() {
            return !historyCommandWords.isEmpty();
        }
        
        private boolean hasRecentUndoCommand() {
            return !historyCommandWords.isEmpty();
        }
        
        private String getCommandWord() {
            assert !historyCommandWords.isEmpty();
            return historyCommandWords.pop();
        }
        
        private String getCommandText() {
            assert !historyCommandTexts.isEmpty();
            return historyCommandTexts.pop();
        }
        
        private ReadOnlyTask getTask() {
            assert !historyTasks.isEmpty();
            return historyTasks.pop();
        }       
        
        private int getNumberOfTasks() {
            assert !historyNumberOfTasks.isEmpty();
            return historyNumberOfTasks.pop();
        }
        
        private ReadOnlyTaskManager getTaskManager() {
            assert !historyTaskManagers.isEmpty();
            return historyTaskManagers.pop();
        }
        
        private void storeCommandWord(String command) {
            historyCommandWords.push(command);
        }
        
        private void storeCommandText(String commandText) {
            historyCommandTexts.push(commandText);
        }
        
        private void storeTask(ReadOnlyTask task) {
            historyTasks.push(task);
        }
        
        private void storeNumberOfTasks(int numberOfTask) {
            historyNumberOfTasks.push(numberOfTask);
        }
        
        private void storeTaskManager(ReadOnlyTaskManager taskManager) {
            historyTaskManagers.push(taskManager);
        }
        
        private void clear() {
            historyCommandWords.clear();
            historyCommandTexts.clear();
            historyTasks.clear();
            historyNumberOfTasks.clear();
            historyTaskManagers .clear();
        }
    }
    
    //========== Private methods used within ModelManager ==================================================
    
    
    //@@author A0130853L
    /**
     * Evaluates if the task is a deadline and is not after the specified date.
     * @param task
     * @param date
     * @return the evaluated boolean expression
     */
    private boolean isDeadlineAndIsNotAfterDate(Task task, LocalDate date) {
		return task.isDeadline() && !task.getPeriod().getEndDate().getDate().isAfter(date);
	}
    
	/**
	 * Evaluates if the task is an event and the specified date is within the event period.
	 * @param task
	 * @param date
	 * @return the evaluated boolean expression
	 */
	private boolean isEventAndDateIsWithinEventPeriod(Task task, LocalDate date) {
		return task.isEvent() && !(task.getPeriod().getEndDate().getDate().isBefore(date) || task.getPeriod().getStartDate().getDate().isAfter(date));
	}
	
	/** 
	 * Evaluates if the task is an event and event is from today onwards.
	 * @param task
	 *@return the evaluated boolean expression
	 */
	private boolean isEventAndIsNotBeforeToday(Task task) {
		LocalDate today = DateUtil.createCurrentDate();
		return task.isEvent() && !(task.getPeriod().getEndDate().getDate().isBefore(today));
	}
	
	//@@author A0139052L
	/**
	 * Reverts back to the previous state by undoing/redoing the previous action
	 * @param toGetInfo the storage in which to get the info from
	 * @param toStoreInfo the storage in which to store the info into
	 * @param isRedo check if it is undo/redo calling this method
	 * @return the commandText string for result message in Undo/Redo Command
	 */
	private String revertBackPreviousState(SessionTaskInfoStorage toGetInfo, SessionTaskInfoStorage toStoreInfo, boolean isRedo) {
        String commandWord = toGetInfo.getCommandWord();
        toStoreInfo.storeCommandWord(commandWord);
        
        try {
            switch(commandWord) {
            
            case AddCommand.COMMAND_WORD:
                ReadOnlyTask taskAdded = toGetInfo.getTask();
                if (isRedo) {
                    taskManager.addTask((Task) taskAdded);
                } else {
                    taskManager.removeTask(taskAdded); 
                } 
                toStoreInfo.storeTask(taskAdded);
                break;
                
            case DeleteCommand.COMMAND_WORD:
                int numberOfTasksDeleted = toGetInfo.getNumberOfTasks();
                toStoreInfo.storeNumberOfTasks(numberOfTasksDeleted);
                ReadOnlyTask taskDeleted;
                for (int i = 0; i < numberOfTasksDeleted; i++) {
                    taskDeleted = toGetInfo.getTask();
                    if (isRedo) {
                        taskManager.removeTask(taskDeleted);
                    } else {
                        taskManager.addTask((Task) taskDeleted);
                    }                    
                    toStoreInfo.storeTask(taskDeleted);
                }
                break;
                
            case EditCommand.COMMAND_WORD:
                ReadOnlyTask taskBeforeEdit = toGetInfo.getTask();
                ReadOnlyTask taskAfterEdit = toGetInfo.getTask();
                if (isRedo) {
                    taskManager.addTask((Task) taskAfterEdit);
                    taskManager.removeTask(taskBeforeEdit);                    
                } else {
                    taskManager.addTask((Task) taskBeforeEdit);
                    taskManager.removeTask(taskAfterEdit);
                }
                toStoreInfo.storeTask(taskAfterEdit);
                toStoreInfo.storeTask(taskBeforeEdit);
                break;
                
            case ClearCommand.COMMAND_WORD:
                ReadOnlyTaskManager previousTaskManager = toGetInfo.getTaskManager();
                if (isRedo) {
                    resetData(TaskManager.getEmptyTaskManager());
                } else {
                    resetData(previousTaskManager);
                }
                toStoreInfo.storeTaskManager(previousTaskManager);
                break;
                
            case DoneCommand.COMMAND_WORD:
                int numberOfTasksMarked = toGetInfo.getNumberOfTasks();
                ReadOnlyTask taskToRevertMark;
                for (int i = 0; i < numberOfTasksMarked; i++) {
                    taskToRevertMark = toGetInfo.getTask();
                    if (isRedo) {
                        taskManager.markTaskAsDoneTask(taskToRevertMark);
                    } else {
                        taskManager.unMarkTaskAsDoneTask(taskToRevertMark);
                    }
                    toStoreInfo.storeTask(taskToRevertMark);
                }
                break;
                
            default:
                assert false: "Should not have an invalid Command Word";
            }            
        } catch (Exception e) {
            assert false: "Should not be unable to undo/redo previous command action";
        }
        String commandText = toGetInfo.getCommandText();
        toStoreInfo.storeCommandText(commandText);
        indicateTaskManagerChanged();
        return commandText;
    }
	
//	/**
//     *  returns true is there is a previous valid command input by user
//     *  and false otherwise
//     */
//    private boolean hasPreviousValidCommand() {
//        return !historyCommands.isEmpty();
//    }
//    
//	//@@author A0139052L
//    /**
//     *  returns the Task Manager from the previous state
//     */	
//    private ReadOnlyTaskManager getPreviousTaskManager() {
//        return historyTaskManagers.pop();
//    }
//    
//    /**
//     * returns the Predicate from the previous state
//     */
//    private Predicate getPreviousPredicate() {
//        return historyPredicates.pop();
//    }
//    
//    /**
//     * returns the previous valid command input by the user
//     */
//    private String getPreviousValidCommand() {
//        return historyCommands.pop();
//    }
//        
//    public synchronized void saveState(String command) {
//        historyTaskManagers.push(new TaskManager(taskManager));
//        historyCommands.push(command);
//        historyPredicates.push(filteredTodos.getPredicate());
//    }
//    
//    public synchronized void removeUnchangedState() {
//        historyTaskManagers.pop();
//        historyCommands.pop();
//        historyPredicates.pop();
//    }
//    
//    public synchronized String undo() throws NoPreviousValidCommandException {
//        if (!hasPreviousValidCommand()) {            
//            throw new NoPreviousValidCommandException(null);
//        }
//        assert !historyPredicates.isEmpty() && !historyTaskManagers.isEmpty();
//        resetData(getPreviousTaskManager());   
//        updateFilteredTaskList(getPreviousPredicate());
//        return getPreviousValidCommand();
//    }
}
