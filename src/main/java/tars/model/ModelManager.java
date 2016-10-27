package tars.model;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import tars.commons.core.ComponentManager;
import tars.commons.core.LogsCenter;
import tars.commons.core.UnmodifiableObservableList;
import tars.commons.events.model.TarsChangedEvent;
import tars.commons.events.ui.TaskAddedEvent;
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.util.DateTimeUtil;
import tars.commons.util.StringUtil;
import tars.logic.commands.Command;
import tars.logic.parser.ArgumentTokenizer;
import tars.model.task.Task;
import tars.model.task.TaskQuery;
import tars.model.tag.ReadOnlyTag;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList.DuplicateTagException;
import tars.model.tag.UniqueTagList.TagNotFoundException;
import tars.model.task.DateTime;
import tars.model.task.DateTime.IllegalDateException;
import tars.model.task.ReadOnlyTask;
import tars.model.task.UniqueTaskList.TaskNotFoundException;
import tars.model.task.rsv.RsvTask;
import tars.model.task.rsv.UniqueRsvTaskList.RsvTaskNotFoundException;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of tars data. All changes to any model should
 * be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Tars tars;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<RsvTask> filteredRsvTasks;
    private final Stack<Command> undoableCmdHistStack;
    private final Stack<Command> redoableCmdHistStack;

	private static final String LIST_ARG_DATETIME = "/dt";
	private static final String LIST_ARG_PRIORITY = "/p";
	private static final String LIST_KEYWORD_DESCENDING = "dsc";


    /**
     * Initializes a ModelManager with the given Tars Tars and its variables
     * should not be null
     */
    public ModelManager(Tars src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with tars: " + src + " and user prefs " + userPrefs);

        tars = new Tars(src);
        filteredTasks = new FilteredList<>(tars.getTasks());
        filteredRsvTasks = new FilteredList<>(tars.getRsvTasks());
        undoableCmdHistStack = new Stack<>();
        redoableCmdHistStack = new Stack<>();
    }

    public ModelManager() {
        this(new Tars(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTars initialData, UserPrefs userPrefs) {
        tars = new Tars(initialData);
        filteredTasks = new FilteredList<>(tars.getTasks());
        filteredRsvTasks = new FilteredList<>(tars.getRsvTasks());
        undoableCmdHistStack = new Stack<>();
        redoableCmdHistStack = new Stack<>();
    }

    @Override
    public void resetData(ReadOnlyTars newData) {
        tars.resetData(newData);
        indicateTarsChanged();
    }

    @Override
    public ReadOnlyTars getTars() {
        return tars;
    }

    @Override
    public Stack<Command> getUndoableCmdHist() {
        return undoableCmdHistStack;
    }

    @Override
    public Stack<Command> getRedoableCmdHist() {
        return redoableCmdHistStack;
    }

    @Override
    public ObservableList<? extends ReadOnlyTag> getUniqueTagList() {
        return tars.getUniqueTagList().getInternalList();
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTarsChanged() {
        raise(new TarsChangedEvent(tars));
    }

    @Override
    /**
     * @@author A0121533W
     */
    public synchronized Task editTask(ReadOnlyTask toEdit, ArgumentTokenizer argsTokenizer)
            throws TaskNotFoundException, DateTimeException, IllegalDateException, DuplicateTagException,
            TagNotFoundException, IllegalValueException {
        Task editedTask = tars.editTask(toEdit, argsTokenizer);
        indicateTarsChanged();
        return editedTask;
    }

    @Override
    /** @@author A0139924W */
    public synchronized void renameTag(ReadOnlyTag oldTag, String newTagName)
            throws IllegalValueException, TagNotFoundException, DuplicateTagException {
        Tag newTag = new Tag(newTagName);

        tars.renameTag(oldTag, newTag);
        tars.getUniqueTagList().update(oldTag, newTag);

        indicateTarsChanged();
    }

    @Override
    /** @@author A0139924W */
    public synchronized void deleteTag(ReadOnlyTag toBeDeleted)
            throws DuplicateTagException, IllegalValueException, TagNotFoundException {
        tars.deleteTag(toBeDeleted);
        tars.getUniqueTagList().remove(new Tag(toBeDeleted));

        indicateTarsChanged();
    }
    
    @Override
    public synchronized void unEditTask(Task toUndo, Task replacement)
            throws DuplicateTaskException {
        tars.replaceTask(toUndo, replacement);
        indicateTarsChanged();
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        tars.removeTask(target);
        indicateTarsChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        tars.addTask(task);
        raise(new TaskAddedEvent(tars.getTaskList().size(), task));
        updateFilteredListToShowAll();
        indicateTarsChanged();
    }

    @Override
    public synchronized void deleteRsvTask(RsvTask target) throws RsvTaskNotFoundException {
        tars.removeRsvTask(target);
        indicateTarsChanged();
    }

    @Override
    public synchronized void addRsvTask(RsvTask rsvTask) throws DuplicateTaskException {
        tars.addRsvTask(rsvTask);
        indicateTarsChanged();
    }

    @Override
    /**
     * @@author A0121533W
     */
    public synchronized void mark(ArrayList<ReadOnlyTask> toMarkList, String status) throws DuplicateTaskException {
        tars.mark(toMarkList, status);
        indicateTarsChanged();

    }

    public String getTaskConflictingDateTimeWarningMessage(DateTime dateTimeToCheck) {
        StringBuilder conflictingTasksStringBuilder = new StringBuilder("");
        int taskCount = 1;
        int rsvCount = 1;

        if (dateTimeToCheck.getEndDate() == null) {
            return "";
        }

        for (ReadOnlyTask t : tars.getTaskList()) {

            if (DateTimeUtil.isDateTimeWithinRange(t.getDateTime(), dateTimeToCheck)) {
                conflictingTasksStringBuilder.append("\nTask ").append(taskCount).append(": ").append(t.getAsText());
                taskCount++;
            }
        }

        for (RsvTask rt : tars.getRsvTaskList()) {
            if (rt.getDateTimeList().stream()
                    .filter(dateTimeSource -> DateTimeUtil.isDateTimeWithinRange(dateTimeSource, dateTimeToCheck))
                    .count() > 0) {
                conflictingTasksStringBuilder.append("\nRsvTask ").append(rsvCount).append(": ").append(rt.toString());
                rsvCount++;

            }
        }

        return conflictingTasksStringBuilder.toString();
    }

    // =========== Filtered Task List Accessors ===========

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public UnmodifiableObservableList<RsvTask> getFilteredRsvTaskList() {
        return new UnmodifiableObservableList<>(filteredRsvTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    public void updateFilteredTaskListUsingQuickSearch(ArrayList<String> quickSearchKeywords) {
        updateFilteredTaskList(new PredicateExpression(new QuickSearchQualifier(quickSearchKeywords)));
    }

    public void updateFilteredTaskListUsingFlags(TaskQuery taskQuery) {
        updateFilteredTaskList(new PredicateExpression(new FlagSearchQualifier(taskQuery)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    /**
     * @@author A0140022H
     */
    public void updateFilteredTaskListUsingDate(DateTime dateTime) {
        updateFilteredTaskList(new PredicateExpression(new DateQualifier(dateTime)));
    }
    
    /**
     * Sorts filtered list based on keywords
     * 
     * @@author A0140022H
     */
    public void sortFilteredTaskList(Set<String> keywords) {
        if (keywords.contains(LIST_ARG_PRIORITY)) {
            if (keywords.contains(LIST_KEYWORD_DESCENDING)) {
                tars.sortByPriorityDescending();
            } else {
                tars.sortByPriority();
            }
        } else if (keywords.contains(LIST_ARG_DATETIME)) {
            if (keywords.contains(LIST_KEYWORD_DESCENDING)) {
                tars.sortByDatetimeDescending();
            } else {
                tars.sortByDatetime();
            }
        }
    }

    // ========== Inner classes/interfaces used for filtering ==========

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

    private class QuickSearchQualifier implements Qualifier {
        private final ArrayList<String> quickSearchKeywords;

        QuickSearchQualifier(ArrayList<String> quickSearchKeywords) {
            this.quickSearchKeywords = quickSearchKeywords;
        }

        private String removeLabels(String taskAsString) {
            String editedString = taskAsString.replace("[", "").replace("]", " ").replace("DateTime: ", "")
                    .replace("Priority: ", "").replace("Status: ", "").replace("Tags: ", "");
            return editedString;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            String taskAsString = removeLabels(task.getAsText());
            return quickSearchKeywords.stream().filter(keyword -> StringUtil.containsIgnoreCase(taskAsString, keyword))
                    .count() == quickSearchKeywords.size();
        }

    }

    private class FlagSearchQualifier implements Qualifier {
        private TaskQuery taskQuery;
        private final static String EMPTY_STRING = "";

        FlagSearchQualifier(TaskQuery taskQuery) {
            this.taskQuery = taskQuery;
        }

        @Override
        public boolean run(ReadOnlyTask task) {

            Boolean isTaskFound = true;

            if (taskQuery.getNameKeywordsAsList().get(0) != EMPTY_STRING) {
                isTaskFound = taskQuery.getNameKeywordsAsList().stream()
                        .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().taskName, keyword))
                        .count() == taskQuery.getNameKeywordsAsList().size();
                if (!isTaskFound) {
                    return false;
                }
            }

            if (taskQuery.getDateTimeQueryRange() != null) {
                isTaskFound = DateTimeUtil.isDateTimeWithinRange(task.getDateTime(), taskQuery.getDateTimeQueryRange());
                if (!isTaskFound) {
                    return false;
                }
            }

            if (taskQuery.getPriorityKeywordsAsList().get(0) != EMPTY_STRING) {
                isTaskFound = taskQuery.getPriorityKeywordsAsList().stream()
                        .filter(keyword -> StringUtil.containsIgnoreCase(task.priorityString(), keyword))
                        .count() == taskQuery.getPriorityKeywordsAsList().size();
                if (!isTaskFound) {
                    return false;
                }
            }

            if (taskQuery.getStatusQuery() != EMPTY_STRING) {
                isTaskFound = taskQuery.getStatusQuery() == task.getStatus().toString();
                if (!isTaskFound) {
                    return false;
                }
            }

            if (taskQuery.getTagKeywordsAsList().get(0) != EMPTY_STRING) {
                String stringOfTags = task.tagsString().replace(",", "").replace("[", "").replace("]", "");
                isTaskFound = taskQuery.getTagKeywordsAsList().stream()
                        .filter(keyword -> StringUtil.containsIgnoreCase(stringOfTags, keyword))
                        .count() == taskQuery.getTagKeywordsAsList().size();
                if (!isTaskFound) {
                    return false;
                }
            }

            return isTaskFound;
        }
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        /**
         * @@author A0124333U
         * @param task
         * @return true if ALL keywords are found in the task name
         */
        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().taskName, keyword))
                    .count() == nameKeyWords.size();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    /**
     * @@author A0140022H
     */
    private class DateQualifier implements Qualifier {
        private final LocalDateTime startDateTime;
        private final LocalDateTime endDateTime;
        private final DateTime dateTimeQuery;

        DateQualifier(DateTime dateTime) {
            if(dateTime.getStartDate() != null) {
                startDateTime = DateTimeUtil.setLocalTime(dateTime.getStartDate(), 0, 0, 0);
                endDateTime = DateTimeUtil.setLocalTime(dateTime.getEndDate(), 23, 59, 59);
            } else {
                startDateTime = DateTimeUtil.setLocalTime(dateTime.getEndDate(), 0, 0, 0);
                endDateTime = DateTimeUtil.setLocalTime(dateTime.getEndDate(), 23, 59, 59);
            }

            dateTimeQuery = new DateTime();
            dateTimeQuery.setStartDateTime(startDateTime);
            dateTimeQuery.setEndDateTime(endDateTime);
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return DateTimeUtil.isDateTimeWithinRange(task.getDateTime(), dateTimeQuery);
        }

    }

}
