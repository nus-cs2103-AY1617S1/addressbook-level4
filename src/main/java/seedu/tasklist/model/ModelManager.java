package seedu.tasklist.model;

import javafx.collections.transformation.FilteredList;
import seedu.tasklist.commons.core.Config;
import seedu.tasklist.commons.core.EventsCenter;
import seedu.tasklist.commons.core.ComponentManager;
import seedu.tasklist.commons.core.LogsCenter;
import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.commons.events.TickEvent;
import seedu.tasklist.commons.events.model.TaskCountersChangedEvent;
import seedu.tasklist.commons.events.model.TaskListChangedEvent;
import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.logic.commands.UndoCommand;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.EndTime;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.StartTime;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.TaskDetails;
import seedu.tasklist.model.task.UniqueTaskList;
import seedu.tasklist.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.tasklist.model.task.UniqueTaskList.TaskNotFoundException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.common.eventbus.Subscribe;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

/**
 * Represents the in-memory model of the task list data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

  //@@author A0144919W    
    public static LinkedList<UndoInfo> undoStack = new LinkedList<UndoInfo>();
    public static LinkedList<UndoInfo> redoStack = new LinkedList<UndoInfo>();
  //@@author
    private final TaskList taskList;
    private final FilteredList<Task> filteredTasks;
  //@@author A0146107M
    private final TaskCounter taskCounter;
  //@@author
    /**
     * Initializes a ModelManager with the given TaskList TaskList and its
     * variables should not be null
     */
    public ModelManager(TaskList src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with tasklist: " + src + " and user prefs " + userPrefs);

        taskList = new TaskList(src);
        filteredTasks = new FilteredList<>(taskList.getTasks());
        taskCounter = new TaskCounter(src);
        EventsCenter.getInstance().registerHandler(this);
    }

    public ModelManager() {
        this(new TaskList(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskList initialData, UserPrefs userPrefs) {
        taskList = new TaskList(initialData);
        filteredTasks = new FilteredList<>(taskList.getTasks());
        taskCounter = new TaskCounter(initialData);
        EventsCenter.getInstance().registerHandler(this);
    }
    //@@author A0144919W
    @Override
    public void resetData(ReadOnlyTaskList newData) {
        if (newData.isEmpty()) { // clear or redo clear was executed
            List<Task> listOfTasks = (List<Task>) (List<?>) taskList.getTaskList();
            addToUndoStack(UndoCommand.CLR_CMD_ID, null, listOfTasks.toArray(new Task[listOfTasks.size()]));
        }
        taskList.resetData(newData);
        indicateTaskListChanged();
        clearRedoStack();
    }
    //@@author A0146107M
    @Subscribe
    private void tickEvent(TickEvent te) {
    	indicateTaskListChanged();
    }
    //@@author A0144919W
    private void clearRedoStack() {
        redoStack.clear();
    }

    @Override
    public void clearTaskUndo(ArrayList<Task> tasks) throws TaskNotFoundException {
        TaskList oldTaskList = new TaskList();
        oldTaskList.setTasks(tasks);
        taskList.resetData(oldTaskList);
    }

    @Override
    public ReadOnlyTaskList getTaskList() {
        return taskList;
    }
    //@@author
    //@@author A0146107M
    @Override
    public TaskCounter getTaskCounter(){
    	return taskCounter;
    }
    
    /** Raises an event to indicate the model has changed */
    private void indicateTaskListChanged() {
        raise(new TaskListChangedEvent(taskList));
    }
    //@@author A0144919W
    @Override
    public void deleteTaskUndo(ReadOnlyTask target) throws TaskNotFoundException {
        taskList.removeTask(target);
        updateFilteredListToShowIncomplete();
        indicateTaskListChanged();
    }
    //@@author
    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskList.removeTask(target);
        updateFilteredListToShowIncomplete();
        indicateTaskListChanged();
        addToUndoStack(UndoCommand.DEL_CMD_ID, null, (Task) target);
        clearRedoStack();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskList.addTask(task);
        updateFilteredListToShowIncomplete();
        indicateTaskListChanged();
        addToUndoStack(UndoCommand.ADD_CMD_ID, null, task);
        clearRedoStack();
    }
    //@@author A0142102E
    @Override
    public boolean isOverlapping(Task task) {
    	return taskList.isOverlapping(task);
    }
    //@@author A0144919W
    @Override
    public void addTaskUndo(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskList.addTask(task);
        updateFilteredListToShowIncomplete();
        indicateTaskListChanged();
    }

    @Override
    public synchronized void updateTask(Task taskToUpdate, TaskDetails taskDetails, String startTime,
            String endTime, Priority priority, UniqueTagList tags, String frequency)
            throws IllegalValueException {
        Task originalTask = new Task(taskToUpdate);
        taskList.updateTask(taskToUpdate, taskDetails, startTime, endTime, priority, frequency);
        updateFilteredListToShowIncomplete();
        indicateTaskListChanged();
        addToUndoStack(UndoCommand.UPD_CMD_ID, null, taskToUpdate, originalTask);
        clearRedoStack();
    }

    @Override
    public void updateTaskUndo(Task taskToUpdate, TaskDetails taskDetails, StartTime startTime, EndTime endTime,
            Priority priority, UniqueTagList tags, String frequency) throws IllegalValueException {
        taskList.updateTask(taskToUpdate, taskDetails, startTime, endTime, priority, frequency);
        updateFilteredListToShowIncomplete();
        indicateTaskListChanged();
    }

    @Override
    public synchronized void markTaskAsComplete(ReadOnlyTask task) throws TaskNotFoundException {
        taskList.markTaskAsComplete(task);
        updateFilteredListToShowIncomplete();
        indicateTaskListChanged();
        addToUndoStack(UndoCommand.DONE_CMD_ID, null, (Task) task);
        clearRedoStack();
    }

    @Override
    public synchronized void markTaskAsIncomplete(ReadOnlyTask task) throws TaskNotFoundException {
        taskList.markTaskAsIncomplete(task);
        updateFilteredListToShowIncomplete();
        indicateTaskListChanged();
    }

    @Override
    public void addToUndoStack(int undoID, String filePath, Task... tasks) {
        UndoInfo undoInfo = new UndoInfo(undoID, filePath, tasks);
        undoStack.push(undoInfo);
    }

    // =========== Filtered Person List Accessors
    // ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    public UnmodifiableObservableList<Task> getListOfTasks() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        sortByDateAndPriority();
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredListToShowIncomplete() {
        updateFilteredListToShowAll();
        updateFilteredTaskList(new PredicateExpression(new DefaultDisplayQualifier()));
    }

    public void updateFilteredList() {
        updateFilteredListToShowIncomplete();
        indicateTaskListChanged();
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        sortByDateAndPriority();
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    public Set<String> getKeywordsFromList(List<ReadOnlyTask> tasks) {
        Set<String> keywords = new HashSet<String>();
        for (ReadOnlyTask task : tasks) {
            keywords.addAll(Arrays.asList(task.getTaskDetails().toString().split(" ")));
        }
        return keywords;
    }

    //@@author A0142102E
    @Override
    public void updateFilteredListToShowComplete() {
        updateFilteredListToShowAll();
        updateFilteredTaskList(new PredicateExpression(new CompletedQualifier()));
    }

    @Override
    public void updateFilteredListToShowFloating() {
        updateFilteredListToShowAll();
        updateFilteredTaskList(new PredicateExpression(new FloatingQualifier()));
    }

    @Override
    public void updateFilteredListToShowOverDue() {
        updateFilteredListToShowAll();
        updateFilteredTaskList(new PredicateExpression(new OverDueQualifier()));
    }

    @Override
    public void updateFilteredListToShowRecurring() {
        updateFilteredListToShowAll();
        updateFilteredTaskList(new PredicateExpression(new RecurringQualifier()));
    }
    
    @Override
    public void updateFilteredListToShowOverlapping(Task task) {
        updateFilteredListToShowAll();
        updateFilteredTaskList(new PredicateExpression(new OverlappingQualifier(task)));
    }
    
    @Override
    public void updateFilteredListToShowPriority(String priority) {
        updateFilteredListToShowAll();
        updateFilteredTaskList(new PredicateExpression(new PriorityQualifier(priority)));
    }

    @Override
    public void updateFilteredListToShowDate(String date) {
        updateFilteredListToShowAll();
        updateFilteredTaskList(new PredicateExpression(new DateQualifier(date)));
    }

    private void sortByDateAndPriority() {
        // Collections.sort(taskList.getListOfTasks(), Comparators.DATE_TIME);
        // Collections.sort(taskList.getListOfTasks(), Comparators.PRIORITY);
    	Collections.sort(taskList.getListOfTasks());
    }

    // ========== Inner classes/interfaces used for filtering
    // ==================================================
    //@@author A0144919W-unused
    //unused as now, the task list is sorted without using these comparators
    private static class Comparators {
        public static Comparator<Task> DATE_TIME = new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
        };
        public static Comparator<Task> PRIORITY = new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                // extract only the date without time from the card string for
                // start time
                String date1 = o1.getStartTime().toCardString().split(" ")[0];
                String date2 = o2.getStartTime().toCardString().split(" ")[0];
                if (date1.equals(date2))
                    return o1.getPriority().compareTo(o2.getPriority());
                else
                    return o1.getStartTime().compareTo(o2.getStartTime());
            }
        };
    }
    //@@author
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

    private class DefaultDisplayQualifier implements Qualifier {

        DefaultDisplayQualifier() {

        }

        @Override
        public boolean run(ReadOnlyTask person) {
            return !person.isComplete();
        }
    }

    private class CompletedQualifier implements Qualifier {
        @Override
        public boolean run(ReadOnlyTask person) {
            return person.isComplete();
        }
    }
    /* @@author A0135769N */
    private class FloatingQualifier implements Qualifier {
        @Override
        public boolean run(ReadOnlyTask person) {
            return person.isFloating();
        }
    }

    private class OverDueQualifier implements Qualifier {
        @Override
        public boolean run(ReadOnlyTask person) {
            return person.isOverDue();
        }
    }
    
    //@@author A0142102E
    private class PriorityQualifier implements Qualifier {
        private String priority;

        public PriorityQualifier(String priority) {
            this.priority = priority.replaceFirst("p/", "");
        }

        @Override
        public boolean run(ReadOnlyTask person) {
            return person.getPriority().priorityLevel.equals(this.priority);
        }
    }

    private class DateQualifier implements Qualifier {
        private final Calendar requestedTime;

        public DateQualifier(String time) {
            requestedTime = Calendar.getInstance();
            List<DateGroup> dates = new Parser().parse(time);
            requestedTime.setTime(dates.get(0).getDates().get(0));
        }

        @Override
        public boolean run(ReadOnlyTask person) {
            return DateUtils.isSameDay(person.getStartTime().time, requestedTime)
                    || (person.getStartTime().toCardString().equals("-")
                            && DateUtils.isSameDay(person.getEndTime().time, requestedTime));
        }
    }

    private class RecurringQualifier implements Qualifier {
        @Override
        public boolean run(ReadOnlyTask person) {
            return person.isRecurring();
        }
    }
    
    private class OverlappingQualifier implements Qualifier {
        private Task task;
    	
    	public OverlappingQualifier(Task task) {
            this.task = task;
        }
    	
    	@Override
        public boolean run(ReadOnlyTask person) {

    		// Overlapping task is task w/start only and compared task is an event
    		if (task.getEndTime().toCardString().equals("-") && !person.getEndTime().toCardString().equals("-")) {
    			return !task.getStartTime().toCardString().equals("-")
    					&& !person.getStartTime().toCardString().equals("-")
    					&& !task.getStartTime().getAsCalendar().after(person.getEndTime().getAsCalendar())
    					&& !person.getStartTime().getAsCalendar().after(task.getStartTime().getAsCalendar());
    		}
    		
    		// Overlapping task is an event and compared task is task w/start only DONE
    		else if (!task.getEndTime().toCardString().equals("-") && person.getEndTime().toCardString().equals("-")) {
    			return !person.getStartTime().toCardString().equals("-")
    					&& !task.getStartTime().getAsCalendar().after(person.getStartTime().getAsCalendar())
    					&& !task.getEndTime().getAsCalendar().before(person.getStartTime().getAsCalendar());
    		}
    		
    		// Compare 2 events DONE
    		else if (!task.getEndTime().toCardString().equals("-") && !person.getEndTime().toCardString().equals("-")) {
    			return !person.getStartTime().toCardString().equals("-")
    					&& !task.getStartTime().getAsCalendar().after(person.getStartTime().getAsCalendar())
    					&& !person.getStartTime().getAsCalendar().after(task.getEndTime().getAsCalendar());
    		}
    		
    		// Compare 2 tasks w/start only DONE
    		return task.getStartTime().getAsCalendar().equals(person.getStartTime().getAsCalendar());
        }
    }
    //@@author A0146107M
    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;
        private Pattern NAME_QUERY;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
            this.NAME_QUERY = Pattern.compile(getRegexFromString(), Pattern.CASE_INSENSITIVE);
        }

        private String getRegexFromString() {
            String result = "";
            for (String keyword : nameKeyWords) {
                for (char c : keyword.toCharArray()) {
                    switch (c) {
                    case '*':
                        result += ".*";
                        break;
                    default:
                        result += c;
                    }
                }
            }
            return result;
        }

        @Override
        public boolean run(ReadOnlyTask person) {
            Matcher matcher = NAME_QUERY.matcher(person.getTaskDetails().taskDetails);
            return matcher.matches();
        }
        //@@author
        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    //@@author A0144919W
    @Override
    public LinkedList<UndoInfo> getUndoStack() {
        return undoStack;
    }

    @Override
    public LinkedList<UndoInfo> getRedoStack() {
        return redoStack;
    }
    /* @@author A0135769N */
    @Override
    public void changeFileStorage(String filePath) throws IOException, ParseException, JSONException {
        if (filePath.equals("default")) {
            filePath = "/data/tasklist.xml";
        }
        File targetListFile = new File(filePath);
        FileReader read = new FileReader("config.json");
        JSONObject obj = (JSONObject) new JSONParser().parse(read);
        String currentFilePath = (String) obj.get("taskListFilePath");
        File currentTaskListPath = new File(currentFilePath);
        Config config = new Config();
        try {
            Files.move(currentTaskListPath.toPath(), targetListFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        config.setTaskListFilePath(filePath);
        addToUndoStack(UndoCommand.STR_CMD_ID, currentFilePath);
        clearRedoStack();
    }
    //@@author A0144919W
    @Override
    public String changeFileStorageUndo(String filePath) throws IOException, ParseException, JSONException {
        if (filePath.equals("default")) {
            filePath = "/data/tasklist.xml";
        }
        File targetListFile = new File(filePath);
        FileReader read = new FileReader("config.json");
        JSONObject obj = (JSONObject) new JSONParser().parse(read);
        String currentFilePath = (String) obj.get("taskListFilePath");
        File currentTaskListPath = new File(currentFilePath);
        Config config = new Config();
        try {
            Files.move(currentTaskListPath.toPath(), targetListFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        config.setTaskListFilePath(filePath);
        return currentFilePath;
    }

    @Override
    public void deleteTaskRedo(Task target) throws TaskNotFoundException {
        taskList.removeTask(target);
        updateFilteredListToShowIncomplete();
        indicateTaskListChanged();
        addToUndoStack(UndoCommand.DEL_CMD_ID, null, (Task) target);
    }

    @Override
    public void addTaskRedo(Task task) throws DuplicateTaskException {
        taskList.addTask(task);
        updateFilteredListToShowIncomplete();
        indicateTaskListChanged();
        addToUndoStack(UndoCommand.ADD_CMD_ID, null, task);
    }

    @Override
    public void markTaskAsCompleteRedo(Task task) throws TaskNotFoundException {
        taskList.markTaskAsComplete(task);
        updateFilteredListToShowIncomplete();
        indicateTaskListChanged();
        addToUndoStack(UndoCommand.DONE_CMD_ID, null, (Task) task);
    }

    @Override
    public void resetDataRedo(ReadOnlyTaskList newData) {
        if (newData.isEmpty()) { // clear or redo clear was executed
            List<Task> listOfTasks = (List<Task>) (List<?>) taskList.getTaskList();
            addToUndoStack(UndoCommand.CLR_CMD_ID, null, listOfTasks.toArray(new Task[listOfTasks.size()]));
        }
        taskList.resetData(newData);
        indicateTaskListChanged();
    }

    @Override
    public void changeFileStorageRedo(String filePath) throws IOException, ParseException, JSONException {
        if (filePath.equals("default")) {
            filePath = "/data/tasklist.xml";
        }
        File targetListFile = new File(filePath);
        FileReader read = new FileReader("config.json");
        JSONObject obj = (JSONObject) new JSONParser().parse(read);
        String currentFilePath = (String) obj.get("taskListFilePath");
        File currentTaskListPath = new File(currentFilePath);
        Config config = new Config();
        try {
            Files.move(currentTaskListPath.toPath(), targetListFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        config.setTaskListFilePath(filePath);
        addToUndoStack(UndoCommand.STR_CMD_ID, currentFilePath);
    }
}
