package harmony.mastermind.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import harmony.mastermind.commons.core.ComponentManager;
import harmony.mastermind.commons.core.LogsCenter;
import harmony.mastermind.commons.core.UnmodifiableObservableList;
import harmony.mastermind.commons.events.model.TaskManagerChangedEvent;
import harmony.mastermind.commons.events.storage.RelocateFilePathEvent;
import harmony.mastermind.commons.events.ui.TabChangedEvent;
import harmony.mastermind.commons.exceptions.FolderDoesNotExistException;
import harmony.mastermind.commons.exceptions.NotRecurringTaskException;
import harmony.mastermind.commons.util.StringUtil;
import harmony.mastermind.logic.commands.CommandResult;
import harmony.mastermind.logic.commands.Redoable;
import harmony.mastermind.logic.commands.Undoable;
import harmony.mastermind.memory.Memory;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.task.ArchiveTaskList;
import harmony.mastermind.model.task.ReadOnlyTask;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.UniqueTaskList;
import harmony.mastermind.model.task.UniqueTaskList.TaskNotFoundException;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

// @@author A0124797R
/**
 * Represents the in-memory model of the address book data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final int FAIL = -1;
    private final TaskManager taskManager;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Task> filteredFloatingTasks;
    private final FilteredList<Task> filteredEvents;
    private final FilteredList<Task> filteredDeadlines;
    private final FilteredList<Task> filteredArchives;
    private final Stack<Undoable> undoHistory;
    private final Stack<Redoable> redoHistory;
    private final Stack<String> commandHistory;

    public static final String TAB_HOME = "Home";
    public static final String TAB_TASKS = "Tasks";
    public static final String TAB_EVENTS = "Events";
    public static final String TAB_DEADLINES = "Deadlines";
    public static final String TAB_ARCHIVES = "Archives";

    private String currentTab;

    /**
     * Initializes a ModelManager with the given TaskManager TaskManager and its
     * variables should not be null
     */
    public ModelManager(TaskManager src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task manager: "
                    + src
                    + " and user prefs "
                    + userPrefs);

        taskManager = new TaskManager(src);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
        filteredFloatingTasks = new FilteredList<>(taskManager.getFloatingTasks());
        filteredEvents = new FilteredList<>(taskManager.getEvents());
        filteredDeadlines = new FilteredList<>(taskManager.getDeadlines());
        filteredArchives = new FilteredList<>(taskManager.getArchives());
        undoHistory = new Stack<>();
        redoHistory = new Stack<>();
        commandHistory = new Stack<String>();
        currentTab = TAB_HOME;
    }

    //@@author
    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    // @@author A0124797R
    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        taskManager = new TaskManager(initialData);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
        filteredFloatingTasks = new FilteredList<>(taskManager.getFloatingTasks());
        filteredEvents = new FilteredList<>(taskManager.getEvents());
        filteredDeadlines = new FilteredList<>(taskManager.getDeadlines());
        filteredArchives = new FilteredList<>(taskManager.getArchives());
        undoHistory = new Stack<>();
        redoHistory = new Stack<>();
        commandHistory = new Stack<String>();
        indicateTaskManagerChanged();
        currentTab = TAB_HOME;
    }

    //@@author
    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        clearUndoHistory();
        clearRedoHistory();
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

    // @@author A0124797R
    @Override
    /** update current tab to the specified tab*/
    public void updateCurrentTab(String tab) {
        this.currentTab = tab;
    }

    // @@author A0138862W
    @Override
    /*
     * push the command to the undo history.
     * @see harmony.mastermind.model.Model#pushToUndoHistory(harmony.mastermind.logic.commands.Undoable)
     */
    public void pushToUndoHistory(Undoable command) {
        undoHistory.push(command);
    }

    // @@author A0138862W
    @Override
    /** undo last action performed **/
    public CommandResult undo() throws EmptyStackException {
        CommandResult commandResult = undoHistory.pop().undo();
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
        return commandResult;
    }

    @Override
    // @@author A0138862W
    /*
     * push the command to the redo history. Should only be called after an undo operation
     * @see harmony.mastermind.model.Model#pushToRedoHistory(harmony.mastermind.logic.commands.Redoable)
     */
    public void pushToRedoHistory(Redoable command) {
        redoHistory.push(command);
    }

    @Override
    // @@author A0138862W
    /** redo the action that being undone function **/
    public CommandResult redo() throws EmptyStackException {
        CommandResult commandResult = redoHistory.pop().redo();
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
        return commandResult;
    }

    @Override
    /**
     * This method should only be called when the user entered a new command
     * other than redo/undo
     **/
    // @@author A0138862W
    public void clearRedoHistory() {
        redoHistory.clear();
    }

    // @@author A0139194X
    /**
     * This method should only be called when the user entered a new command
     * other than redo/undo
     **/
    public void clearUndoHistory() {
        undoHistory.clear();
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    // @@author A0124797R
    @Override
    /** Deletes the given archived Task */
    public synchronized void deleteArchive(ReadOnlyTask target) throws TaskNotFoundException, ArchiveTaskList.TaskNotFoundException {
        taskManager.removeArchive(target);
        indicateTaskManagerChanged();
    }

    //@@author
    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    // @@author A0124797R
    @Override
    public synchronized void markTask(Task target) throws TaskNotFoundException {
        taskManager.markTask(target);
        indicateTaskManagerChanged();
    }
    
    @Override
    public synchronized void markDue(ArrayList<Task> targets) throws TaskNotFoundException {
        for (Task t: targets) {
            taskManager.markTask(t);
        }
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void unmarkTask(Task target) throws ArchiveTaskList.TaskNotFoundException, UniqueTaskList.DuplicateTaskException {
        taskManager.unmarkTask(target);
        indicateTaskManagerChanged();
    }
    
    @Override
    public String getCurrentTab(){
        return this.currentTab;
    }


    // @@author A0124797R
    //=========== Filtered List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredFloatingTaskList() {
        return new UnmodifiableObservableList<>(filteredFloatingTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredEventList() {
        return new UnmodifiableObservableList<>(filteredEvents);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDeadlineList() {
        return new UnmodifiableObservableList<>(filteredDeadlines);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredArchiveList() {
        return new UnmodifiableObservableList<>(filteredArchives);
    }
    
    // =========== Methods for file access ================================

    // @@author A0124797R-unused
    //remove the use of importing txt file
    @Override
    public synchronized BufferedReader importFile(String fileToImport) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(fileToImport));
        
        return br;
    }
    
    // @@author: A0139194X
    @Override
    public synchronized void relocateSaveLocation(String newFilePath) throws FolderDoesNotExistException {
        raise(new RelocateFilePathEvent(newFilePath));
        indicateTaskManagerChanged();
    }
    

    // =========== Methods for Recurring Tasks=============================

    // @@author A0124797R
    @Override
    public synchronized void addNextTask(Task task) throws UniqueTaskList.DuplicateTaskException, NotRecurringTaskException {
        taskManager.addNextTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    //@@author
    // =========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    // @@author A0124797R
    @Override
    public ObservableList<Task> getListToMark() {
        return getCurrentObservableList();
    }

    // @@author A0124797R
    @Override
    public void updateFilteredListToShow(String tab) {
        switch (tab) {
            case TAB_HOME:
                currentTab = TAB_HOME;
                break;
            case TAB_TASKS:
                currentTab = TAB_TASKS;
                break;
            case TAB_EVENTS:
                currentTab = TAB_EVENTS;
                break;
            case TAB_DEADLINES:
                currentTab = TAB_DEADLINES;
                break;
            case TAB_ARCHIVES:
                currentTab = TAB_ARCHIVES;
                break;
        }
        updateFilteredListToShowAll();
    }
    
    // @@author A0124797R
    @Override
    public void updateFilteredListToShowAll() {
        switch (currentTab) {
            case TAB_HOME:
                filteredTasks.setPredicate(null);
                break;
            case TAB_TASKS:
                filteredFloatingTasks.setPredicate(null);
                break;
            case TAB_EVENTS:
                filteredEvents.setPredicate(null);
                break;
            case TAB_DEADLINES:
                filteredDeadlines.setPredicate(null);
                break;
            case TAB_ARCHIVES:
                filteredArchives.setPredicate(null);
                break;
        }
    }
    
    @Override
    public int getCurrentListSize() {
        switch (currentTab) {
            case TAB_HOME:
                return filteredTasks.size();
            case TAB_TASKS:
                return filteredFloatingTasks.size();
            case TAB_EVENTS:
                return filteredEvents.size();
            case TAB_DEADLINES:
                return filteredDeadlines.size();
            case TAB_ARCHIVES:
                return filteredArchives.size();
            default:
                //should not reach here
                return FAIL;
        }
    }

    @Override
    public void updateFilteredListToShowUpcoming(long time, String taskType) {
        updateFilteredList(new PredicateExpression(new DateQualifier(time, taskType)));
    }

    //@@author generated
    @Override
    public void updateFilteredList(Set<String> keywords) {
        updateFilteredList(new PredicateExpression(new NameQualifier(keywords)));
        indicateTaskManagerChanged();
    }

    // @@author A0124797R
    @Override
    public void updateFilteredTagTaskList(Set<Tag> keywords) {
        updateFilteredList(new PredicateExpression(new TagQualifier(keywords)));
    }

    /**
     * update filtered list of specific tabs
     * @param expression
     */
    private void updateFilteredList(Expression expression) {
        switch (currentTab) {
            case TAB_HOME:
                filteredTasks.setPredicate(expression::satisfies);
                break;
            case TAB_TASKS:
                filteredFloatingTasks.setPredicate(expression::satisfies);
                break;
            case TAB_EVENTS:
                filteredEvents.setPredicate(expression::satisfies);
                break;
            case TAB_DEADLINES:
                filteredDeadlines.setPredicate(expression::satisfies);
                break;
            case TAB_ARCHIVES:
                filteredArchives.setPredicate(expression::satisfies);
                break;
        }
    }

    // @@author A0124797R
    /**
     * Returns an ObservableList of the filtered tasks in current Tab
     */
    private ObservableList<Task> getCurrentObservableList() {
        ObservableList<Task> list = filteredTasks;
        
        switch (currentTab) {
            case "Home":
                list = filteredTasks;
                break;
            case "Tasks":
                list = filteredFloatingTasks;
                break;
            case "Events":
                list = filteredEvents;
                break;
            case "Deadlines":
                list = filteredDeadlines;
                break;
            case "Archives":
                list = filteredArchives;
                break;
        }

        return list;
    }

    // @@author A0124797R
    @Override
    /** Returns an UnmodifiableObservableList of filtered tasks in current Tab */
    public UnmodifiableObservableList<ReadOnlyTask> getCurrentList() {
        return new UnmodifiableObservableList<ReadOnlyTask>(getCurrentObservableList());
    }

    //@@author
    private void searchTask(String keyword, Memory memory) {
        taskManager.searchTask(keyword, memory);
    }

    // ========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);

        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);

        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream().filter(keyword -> StringUtil.containsIgnoreCase(task.getName(), keyword)).findAny().isPresent();
        }

        @Override
        public String toString() {
            return "name="
                   + String.join(", ", nameKeyWords);
        }
    }

    // @@author A0124797R
    /**
     * used as a qualifier to filter tasks by tags
     */
    private class TagQualifier implements Qualifier {
        private Set<Tag> tagKeyWords;

        TagQualifier(Set<Tag> tagKeyWords) {
            this.tagKeyWords = tagKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            final Set<Tag> tagList = task.getTags().toSet();

            return !Collections.disjoint(tagList, tagKeyWords);
        }

        @Override
        public String toString() {
            return "tags="
                   + String.join(", ", tagKeyWords.toString());
        }
    }

    /**
     * used as a qualifier to filter dates to use in {@code UpcomingCommand}
     */
    private class DateQualifier implements Qualifier {
        private final String TYPE_DEADLINE = "deadlines";
        private final String TYPE_EVENT = "events";
        private final String TYPE_DUE = "due";
        
        private long timeNow;
        private long oneWeekFromNow;
        private final long oneWeek = 604800000;
        private String taskType;

        DateQualifier(long time, String taskType) {
            this.timeNow = new Date().getTime();
            this.oneWeekFromNow = time + oneWeek;
            this.taskType = taskType;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            switch (taskType) {
                case TYPE_DEADLINE:
                    return isUpcomingDeadline(task);
                case TYPE_EVENT:
                    return isUpcomingEvent(task);
                case TYPE_DUE:
                    return isTaskDue(task);
                default:
                    return isUpcomingAll(task);
            }
        }

        @Override
        public String toString() {
            return "Date before:"
                   + oneWeekFromNow;
        }
        
        private boolean isUpcomingAll(ReadOnlyTask task) {
            if (task.isFloating()) {
                return true;
            } else {
                return isUpcoming(task);
            }
        }
        
        private boolean isUpcomingEvent(ReadOnlyTask task) {
            if (task.isFloating() || task.isDeadline()) {
                return false;
            } else {
                return isUpcoming(task);
            }
        }
        
        private boolean isUpcomingDeadline(ReadOnlyTask task) {
            if (task.isFloating() || task.isEvent()) {
                return false;
            } else {
                return isUpcoming(task);
            }
        }
        
        private boolean isTaskDue(ReadOnlyTask task) {
            if (task.isFloating()) {
                return false;
            } else {
                return isDue(task);
            }
        }
        
        /**
         * Checks if end date of task is within one week from now.
         */
        private boolean isUpcoming(ReadOnlyTask task) {
            long taskTime = task.getEndDate().getTime();
            boolean isUpcoming = taskTime < oneWeekFromNow && taskTime > timeNow;
            return isUpcoming;
        }
        
        /**
         * Checks if tasks has already past
         */
        private boolean isDue(ReadOnlyTask task) {
            long taskTime = task.getEndDate().getTime();
            return taskTime < timeNow;
        }
    }

    
    /**
     * handle changing of tabs when using specific commands
     * @param event
     */
    @Subscribe
    private void handleTabChangedEvent(TabChangedEvent event){
        this.updateCurrentTab(event.toTabId);
    }
    
    //@@author
    @Override
    public void searchTask(String input) {
        // implementing next milestone
    }


}
