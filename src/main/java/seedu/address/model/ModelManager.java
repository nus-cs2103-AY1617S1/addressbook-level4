package seedu.address.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.FilePathChangeEvent;
import seedu.address.commons.events.model.TaskListChangedEvent;
import seedu.address.commons.events.ui.AgendaTimeRangeChangedEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.RecurringTaskManager;
import seedu.address.logic.util.DateFormatterUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskOccurrence;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskType;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.task.UniqueTaskList.TimeslotOverlapException;

/**
 * Represents the in-memory model of the address book data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskMaster taskMaster;
    private final List<Task> tasks;
    private final FilteredList<TaskOccurrence> filteredTaskComponents;
    private Expression previousExpression;
    private TaskDate previousDate;

    //@@author A0135782Y
    /**
     * Initializes a ModelManager with the given TaskList TaskList and its
     * variables should not be null
     */
    public ModelManager(TaskMaster src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;
        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        taskMaster = new TaskMaster(src);
        tasks = taskMaster.getTasks();
        filteredTaskComponents = new FilteredList<>(taskMaster.getTaskComponentList());
        initRecurringTaskManager();
        previousDate = new TaskDate(new Date(System.currentTimeMillis()));
        previousExpression = new PredicateExpression(new InitialQualifier());
        
    }

    //@@author
    public ModelManager() {
        this(new TaskMaster(), new UserPrefs());
    }

    //@@author A0135782Y
    public ModelManager(ReadOnlyTaskMaster initialData, UserPrefs userPrefs) {
        taskMaster = new TaskMaster(initialData);
        tasks = taskMaster.getTasks();

        filteredTaskComponents = new FilteredList<>(taskMaster.getTaskComponentList());
        initRecurringTaskManager();
        previousDate = new TaskDate(new Date(System.currentTimeMillis()));
        previousExpression = new PredicateExpression(new InitialQualifier());
    }
    
    private void initRecurringTaskManager() {
        RecurringTaskManager.getInstance().setTaskList(taskMaster.getUniqueTaskList());
        if (RecurringTaskManager.getInstance().updateAnyRecurringTasks()) {
            indicateTaskListChanged();
        }
    }    
    //@@author

    @Override
    public void resetData(ReadOnlyTaskMaster newData) {
        taskMaster.resetData(newData);
        indicateTaskListChanged();
    }

    @Override
    public ReadOnlyTaskMaster getTaskMaster() {
        return taskMaster;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskListChanged() {
        raise(new TaskListChangedEvent(taskMaster));
    }

    @Override
    public synchronized void deleteTask(TaskOccurrence target) throws TaskNotFoundException {
        taskMaster.removeTask(target.getTaskReference());
        indicateTaskListChanged();
    }

    //@@author A0147995H
    @Override
    public synchronized void editTask(TaskOccurrence target, Name name, UniqueTagList tags, TaskDate startDate, TaskDate endDate,
            RecurringType recurringType) throws TaskNotFoundException, TimeslotOverlapException {
        taskMaster.updateTask(target, name, tags, startDate, endDate, recurringType);
        indicateTaskListChanged();
        updateFilteredTaskList(previousExpression);
    }
    //@@author

    //@@author A0135782Y
    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException, TimeslotOverlapException {
        taskMaster.addTask(task);
        RecurringTaskManager.getInstance().correctAddingOverdueTasks(task);
        updateFilteredListToShowAll();
        indicateTaskListChanged();
    }

    //@@author A0147967J
    @Override
    public synchronized void archiveTask(TaskOccurrence target) throws TaskNotFoundException {
        taskMaster.archiveTask(target);
        indicateTaskListChanged();
        updateFilteredTaskList(previousExpression);
        
    }

    @Override
    public void changeDirectory(String filePath) {
        raise(new FilePathChangeEvent(filePath));
    }
    //@@author

    // =========== Filtered Task List Accessory ===============================================================

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return new ArrayList<ReadOnlyTask>(tasks);
    }

    @Override
    public UnmodifiableObservableList<TaskOccurrence> getFilteredTaskComponentList() {
        return new UnmodifiableObservableList<>(filteredTaskComponents);
    }

    @Override
    public void updateFilteredListToShowAll() {
        previousExpression = new PredicateExpression(new ArchiveQualifier(false));
        filteredTaskComponents.setPredicate(new PredicateExpression(new ArchiveQualifier(false))::satisfies);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords, Set<String> tags, Date startDate, Date endDate,
            Date deadline) {
        updateFilteredTaskList(
                new PredicateExpression(new FindQualifier(keywords, tags, startDate, endDate, deadline)));
    }
    
    //@@A0147967J
    @Override
    public void updateFilteredTaskList(Expression expression) {
        previousExpression  = expression;
        filteredTaskComponents.setPredicate(expression::satisfies);
    }
    

    @Override
    public Expression getPreviousExpression() {
        return previousExpression;
    }
    
    @Override 
    public TaskDate getPreviousDate(){
        return previousDate;
    }
    
    @Override
    @Subscribe
    public void setSystemTime(AgendaTimeRangeChangedEvent atrce){
        previousDate = atrce.getInputDate();
        updateFilteredTaskList(new PredicateExpression(new DeadlineQualifier(DateFormatterUtil.getEndOfDay(atrce.getInputDate().getDate()))));
    }
    //@@author

    // ========== Inner classes/interfaces used for filtering ==================================================

    public interface Expression {
        boolean satisfies(TaskOccurrence t);

    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(TaskOccurrence task) {
            return qualifier.run(task);
        }

    }

    interface Qualifier {
        boolean run(TaskOccurrence task);

    }

    //@@author A0147967J
    private class InitialQualifier implements Qualifier {

        @Override
        public boolean run(TaskOccurrence task) {
            return true;
        }
        
    }
    
    private class TypeQualifier implements Qualifier {
        private TaskType typeKeyWords;

        TypeQualifier(TaskType typeKeyWords) {
            this.typeKeyWords = typeKeyWords;
        }

        @Override
        public boolean run(TaskOccurrence task) {
            return task.getTaskReference().getTaskType().equals(typeKeyWords) && !task.isArchived();
        }

    }
    
    //@@author A0135782Y
    private class ArchiveQualifier implements Qualifier {
        private boolean isArchived;

        ArchiveQualifier(boolean isItArchive) {
            this.isArchived = isItArchive;
        }

        @Override
        public boolean run(TaskOccurrence task) {
            return task.isArchived() == isArchived;
        }

    }
    //@@author

    //@@author A0147995H
    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(TaskOccurrence task) {
            if (nameKeyWords.isEmpty())
                return true;

            return nameKeyWords.stream().filter(
                    keyword -> StringUtil.containsIgnoreCase(task.getTaskReference().getName().fullName, keyword))
                    .findAny().isPresent();
        }

    }

    private class TagQualifier implements Qualifier {
        private Set<String> tagSet;

        TagQualifier(Set<String> tagSet) {
            this.tagSet = tagSet;
        }

        private String tagToString(TaskOccurrence task) {
            Set<Tag> tagSet = task.getTaskReference().getTags().toSet();
            Set<String> tagStringSet = new HashSet<String>();
            for (Tag t : tagSet) {
                tagStringSet.add(t.tagName);
            }
            return String.join(" ", tagStringSet);
        }

        @Override
        public boolean run(TaskOccurrence task) {
            if (tagSet.isEmpty()) {
                return true;
            }
            return tagSet.stream().filter(tag -> StringUtil.containsIgnoreCase(tagToString(task), tag)).findAny()
                    .isPresent();
        }

    }

    private class PeriodQualifier implements Qualifier {
        private final int START_DATE_INDEX = 0;
        private final int END_DATE_INDEX = 1;

        private Date startTime;
        private Date endTime;

        PeriodQualifier(Date startTime, Date endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        private Date[] extractTaskPeriod(TaskOccurrence task) {
            TaskType type = task.getTaskReference().getTaskType();
            if (type.equals(TaskType.FLOATING)) {
                return null;
            }

            Date startDate;
            if (task.getStartDate().getDateInLong() == TaskDate.DATE_NOT_PRESENT) {
                startDate = null;
            } else {
                startDate = new Date(task.getStartDate().getDateInLong());
            }
            Date endDate = new Date(task.getEndDate().getDateInLong());
            return new Date[] { startDate, endDate };
        }

        @Override
        public boolean run(TaskOccurrence task) {

            if (this.endTime == null)
                return true;

            Date[] timeArray = extractTaskPeriod(task);
            if (timeArray == null)
                return false;

            Date startDate = timeArray[START_DATE_INDEX];
            Date endDate = timeArray[END_DATE_INDEX];
            if (startDate == null) {
                if (!endDate.before(this.startTime) && !endDate.after(this.endTime)) {
                    return true;
                }
                return false;
            }
            if (!startDate.before(this.startTime) && !endDate.after(this.endTime))
                return true;
            return false;
        }

    }

    private class DeadlineQualifier implements Qualifier {
        private Date deadline;

        DeadlineQualifier(Date deadline) {
            this.deadline = deadline;
        }

        @Override
        public boolean run(TaskOccurrence task) {

            if (this.deadline == null)
                return true;

            if (task.getTaskReference().getTaskType().equals(TaskType.FLOATING))
                return false;

            Date deadline = new Date(task.getEndDate().getDateInLong());

            if ( (deadline.before(this.deadline) || this.deadline.equals(deadline)) && 
                    task.getStartDate().getDateInLong() == TaskDate.DATE_NOT_PRESENT)
                return true;

            return false;
        }

    }

    private class FindQualifier implements Qualifier {
        private NameQualifier nameQualifier;
        private TagQualifier tagQualifier;
        private PeriodQualifier periodQualifier;
        private DeadlineQualifier deadlineQualifier;
        private TypeQualifier typeQualifier = null;
        private ArchiveQualifier archiveQualifier;

        FindQualifier(Set<String> keywordSet, Set<String> tagSet, Date startTime, Date endTime, Date deadline) {
            if (keywordSet.contains("-C")) {
                this.archiveQualifier = new ArchiveQualifier(true);
            }
            if (keywordSet.contains("-F"))
                this.typeQualifier = new TypeQualifier(TaskType.FLOATING);
            this.nameQualifier = new NameQualifier(keywordSet);
            this.tagQualifier = new TagQualifier(tagSet);
            this.periodQualifier = new PeriodQualifier(startTime, endTime);
            this.deadlineQualifier = new DeadlineQualifier(deadline);
        }

        @Override
        public boolean run(TaskOccurrence task) {
            if (this.typeQualifier != null)
                return typeQualifier.run(task);
            if (this.archiveQualifier != null) {
                return archiveQualifier.run(task);
            }
            return nameQualifier.run(task) && tagQualifier.run(task) && periodQualifier.run(task)
                    && deadlineQualifier.run(task);
        }

    }
    //@@author

}
