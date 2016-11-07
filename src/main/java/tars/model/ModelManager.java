package tars.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import tars.commons.core.ComponentManager;
import tars.commons.core.LogsCenter;
import tars.commons.core.UnmodifiableObservableList;
import tars.commons.events.model.TarsChangedEvent;
import tars.commons.events.ui.RsvTaskAddedEvent;
import tars.commons.events.ui.TaskAddedEvent;
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.util.DateTimeUtil;
import tars.commons.util.StringUtil;
import tars.logic.commands.Command;
import tars.model.qualifiers.DateQualifier;
import tars.model.qualifiers.FlagSearchQualifier;
import tars.model.qualifiers.Qualifier;
import tars.model.qualifiers.QuickSearchQualifier;
import tars.model.tag.ReadOnlyTag;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList.DuplicateTagException;
import tars.model.tag.UniqueTagList.TagNotFoundException;
import tars.model.task.DateTime;
import tars.model.task.ReadOnlyTask;
import tars.model.task.Status;
import tars.model.task.Task;
import tars.model.task.TaskQuery;
import tars.model.task.UniqueTaskList.TaskNotFoundException;
import tars.model.task.rsv.RsvTask;
import tars.model.task.rsv.UniqueRsvTaskList.RsvTaskNotFoundException;

/**
 * Represents the in-memory model of tars data. All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {

    private static final Logger logger =
            LogsCenter.getLogger(ModelManager.class);
    private static final String LIST_ARG_DATETIME = "/dt";
    private static final String LIST_ARG_PRIORITY = "/p";
    private static final String LIST_KEYWORD_DESCENDING = "dsc";

    private static String MESSAGE_INITIALIZING_TARS =
            "Initializing with tars %1$s and user prefs %2$s";
    private static String CONFLICTING_TASK = "\nTask %1$s: %2$s";
    private static String CONFLICTING_RSV_TASK = "\nRsvTask %1$s: %2$s";

    private final Tars tars;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<RsvTask> filteredRsvTasks;
    private final Stack<Command> undoableCmdHistStack;
    private final Stack<Command> redoableCmdHistStack;

    /**
     * Initializes a ModelManager with the given Tars Tars and its variables should not be null
     */
    public ModelManager(Tars src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine(String.format(MESSAGE_INITIALIZING_TARS, src, userPrefs));

        tars = new Tars(src);
        filteredTasks = new FilteredList<>(tars.getTasks());
        filteredRsvTasks = new FilteredList<>(tars.getRsvTasks());
        undoableCmdHistStack = new Stack<>();
        redoableCmdHistStack = new Stack<>();
    }

    public ModelManager() {
        this(new Tars(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTars initialData) {
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
    public void overwriteDataFromNewFilePath(ReadOnlyTars newData) {
        tars.resetData(newData);
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


    // @@author A0139924W
    @Override
    public synchronized void renameTasksWithNewTag(ReadOnlyTag toBeRenamed,
            Tag newTag) throws IllegalValueException, TagNotFoundException,
            DuplicateTagException {

        tars.getUniqueTagList().update(toBeRenamed, newTag);
        tars.renameTasksWithNewTag(toBeRenamed, newTag);

        indicateTarsChanged();
    }

    // @@author A0139924W
    @Override
    public synchronized ArrayList<ReadOnlyTask> removeTagFromAllTasks(
            ReadOnlyTag toBeDeleted)
            throws TagNotFoundException, IllegalValueException {

        ArrayList<ReadOnlyTask> editedTasks =
                tars.removeTagFromAllTasks(toBeDeleted);
        tars.getUniqueTagList().remove(new Tag(toBeDeleted));

        indicateTarsChanged();
        return editedTasks;
    }

    // @@author A0139924W
    @Override
    public synchronized void addTagToAllTasks(ReadOnlyTag toBeAdded,
            ArrayList<ReadOnlyTask> allTasks) throws DuplicateTagException,
            IllegalValueException, TagNotFoundException {
        tars.addTagToAllTasks(toBeAdded, allTasks);
        tars.getUniqueTagList().add(new Tag(toBeAdded));

        indicateTarsChanged();
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target)
            throws TaskNotFoundException {
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
    public synchronized void deleteRsvTask(RsvTask target)
            throws RsvTaskNotFoundException {
        tars.removeRsvTask(target);
        indicateTarsChanged();
    }

    @Override
    public synchronized void addRsvTask(RsvTask rsvTask)
            throws DuplicateTaskException {
        tars.addRsvTask(rsvTask);
        raise(new RsvTaskAddedEvent(tars.getRsvTaskList().size(), rsvTask));
        raise(new RsvTaskAddedEvent(tars.getRsvTaskList().size(), rsvTask));
        indicateTarsChanged();
    }

    // @@author A0121533W
    @Override
    public synchronized void mark(ArrayList<ReadOnlyTask> toMarkList,
            Status status) throws DuplicateTaskException {
        tars.mark(toMarkList, status);
        indicateTarsChanged();
    }

    // @@author A0124333U
    /**
     * Returns a string of tasks and rsv tasks whose datetime conflicts with a specified datetime
     */
    public String getTaskConflictingDateTimeWarningMessage(
            DateTime dateTimeToCheck) {
        StringBuilder conflictingTasksStringBuilder =
                new StringBuilder(StringUtil.EMPTY_STRING);

        if (dateTimeToCheck.getEndDate() == null) {
            return StringUtil.EMPTY_STRING;
        }

        appendConflictingTasks(conflictingTasksStringBuilder, dateTimeToCheck);
        appendConflictingRsvTasks(conflictingTasksStringBuilder,
                dateTimeToCheck);

        return conflictingTasksStringBuilder.toString();
    }

    private void appendConflictingTasks(
            StringBuilder conflictingTasksStringBuilder,
            DateTime dateTimeToCheck) {

        int taskCount = 1;
        for (ReadOnlyTask t : tars.getTaskList()) {

            if (t.getStatus().status == Status.UNDONE && DateTimeUtil
                    .isDateTimeConflicting(t.getDateTime(), dateTimeToCheck)) {
                conflictingTasksStringBuilder.append(String
                        .format(CONFLICTING_TASK, taskCount, t.getAsText()));
                taskCount++;
            }
        }
    }

    private void appendConflictingRsvTasks(
            StringBuilder conflictingTasksStringBuilder,
            DateTime dateTimeToCheck) {

        int rsvCount = 1;

        for (RsvTask rt : tars.getRsvTaskList()) {
            if (rt.getDateTimeList().stream()
                    .filter(dateTimeSource -> DateTimeUtil
                            .isDateTimeConflicting(dateTimeSource,
                                    dateTimeToCheck))
                    .count() > 0) {
                conflictingTasksStringBuilder.append(String
                        .format(CONFLICTING_RSV_TASK, rsvCount, rt.toString()));
                rsvCount++;

            }
        }

    }

    /**
     * Returns a sorted arraylist of filled datetime slots in a specified date Datetimes with no
     * startdate are not added into the list
     */
    public ArrayList<DateTime> getListOfFilledTimeSlotsInDate(
            DateTime dateToCheck) {
        ArrayList<DateTime> listOfDateTime = new ArrayList<DateTime>();

        addTimeSlotsFromTasks(listOfDateTime, dateToCheck);
        addTimeSlotsFromRsvTasks(listOfDateTime, dateToCheck);

        Collections.sort(listOfDateTime);

        return listOfDateTime;
    }

    private void addTimeSlotsFromTasks(ArrayList<DateTime> listOfDateTime,
            DateTime dateToCheck) {
        for (ReadOnlyTask t : tars.getTaskList()) {
            if (t.getStatus().status == Status.UNDONE
                    && t.getDateTime().getStartDate() != null
                    && DateTimeUtil.isDateTimeWithinRange(t.getDateTime(),
                            dateToCheck)) {
                listOfDateTime.add(t.getDateTime());
            }
        }
    }

    private void addTimeSlotsFromRsvTasks(ArrayList<DateTime> listOfDateTime,
            DateTime dateToCheck) {
        
        for (RsvTask rt : tars.getRsvTaskList()) {
            for (DateTime dt : rt.getDateTimeList()) {
                if (dt.getStartDate() != null && DateTimeUtil
                        .isDateTimeWithinRange(dt, dateToCheck)) {
                    listOfDateTime.add(dt);
                }
            }
        }
    }


    // @@author A0139924W
    @Override
    public synchronized void replaceTask(ReadOnlyTask toUndo, Task replacement)
            throws DuplicateTaskException {
        tars.replaceTask(toUndo, replacement);
        indicateTarsChanged();
    }

    // @@author

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

    public void updateFilteredTaskListUsingQuickSearch(
            ArrayList<String> quickSearchKeywords) {
        updateFilteredTaskList(new PredicateExpression(
                new QuickSearchQualifier(quickSearchKeywords)));
    }

    public void updateFilteredTaskListUsingFlags(TaskQuery taskQuery) {
        updateFilteredTaskList(
                new PredicateExpression(new FlagSearchQualifier(taskQuery)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    // @@author A0140022H
    public void updateFilteredTaskListUsingDate(DateTime dateTime) {
        updateFilteredTaskList(
                new PredicateExpression(new DateQualifier(dateTime)));
    }

    /**
     * Sorts filtered list based on keywords
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

    // @@author

    // ========== Inner class/interface used for filtering ==========

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
    
}
