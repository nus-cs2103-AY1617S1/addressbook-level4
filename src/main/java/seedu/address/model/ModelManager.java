package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskType;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.task.UniqueTaskList.TimeslotOverlapException;
import seedu.address.commons.events.model.TaskListChangedEvent;
import seedu.address.commons.events.model.FilePathChangeEvent;
import seedu.address.commons.core.ComponentManager;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskList taskList;
    private final FilteredList<Task> filteredTasks;
    
    /**
     * Initializes a ModelManager with the given TaskList
     * TaskList and its variables should not be null
     */
    public ModelManager(TaskList src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        taskList = new TaskList(src);
        filteredTasks = new FilteredList<>(taskList.getTasks());
    }

    public ModelManager() {
        this(new TaskList(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskList initialData, UserPrefs userPrefs) {
        taskList = new TaskList(initialData);
        filteredTasks = new FilteredList<>(taskList.getTasks());
    }

    @Override
    public void resetData(ReadOnlyTaskList newData) {
        taskList.resetData(newData);
        indicateTaskListChanged();
    }

    @Override
    public ReadOnlyTaskList getTaskList() {
        return taskList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskListChanged() {
        raise(new TaskListChangedEvent(taskList));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskList.removeTask(target);
        indicateTaskListChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException, TimeslotOverlapException {
        taskList.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskListChanged();
    }

    
    @Override
	public void changeDirectory(String filePath) {
		// TODO Auto-generated method stub
		raise(new FilePathChangeEvent(filePath));
	}

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords, Set<String> tags, String startDate, String endDate, String deadline) {
        updateFilteredTaskList(new PredicateExpression(new FindQualifier(keywords, tags, startDate, endDate, deadline)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

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
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    
    private class TagQualifier implements Qualifier {
    	private Set<String> tagSet;
    	
    	TagQualifier(Set<String> tagSet) {
    		this.tagSet = tagSet;
    	}
    	
    	private String tagToString(ReadOnlyTask task) {
    		Set<Tag> tagSet = task.getTags().toSet();
    		Set<String> tagStringSet = new HashSet<String>();
    		for(Tag t : tagSet) {
    			tagStringSet.add(t.tagName);
    		}
    		return String.join(" ", tagStringSet);
    	}

		@Override
		public boolean run(ReadOnlyTask task) {
			if(tagSet.isEmpty()) {
				return true;
			}
			return tagSet.stream()
					.filter(tag -> StringUtil.containsIgnoreCase(tagToString(task), tag))
					.findAny()
					.isPresent();
		}
    	
		@Override 
		public String toString() {
			return "tag=" + String.join(", ", tagSet);
		}
    }

    private class PeriodQualifier implements Qualifier {
    	private final int START_DATE_INDEX = 0;
    	private final int END_DATE_INDEX = 1;
    	
		private String startTime;
		private String endTime;
		
		PeriodQualifier(String startTime, String endTime) {
			this.startTime = startTime;
			this.endTime = endTime;
		}
		
		private String[] extractTaskPeriod(ReadOnlyTask task) {
			TaskType type = task.getType();
			if(type.equals(TaskType.FLOATING)) {
				return null;
			}
			
			String startDate = task.getStartDate().getFormattedDate();
			String endDate = task.getEndDate().getFormattedDate();
			
			if(startDate.isEmpty() || endDate.isEmpty()) {
				return null;
			}
			return new String[]{ startDate, endDate };
		}

		@Override
		public boolean run(ReadOnlyTask task) {
			
			if(this.startTime.isEmpty() || this.endTime.isEmpty())
				return true;
				
			String[] timeArray = extractTaskPeriod(task);
			if(timeArray == null)
				return false;

			String startDate = timeArray[START_DATE_INDEX];
			String endDate = timeArray[END_DATE_INDEX];
			
			if(startDate.equals(this.startTime)
					&& endDate.equals(this.endTime))
				return true;
			return false;	
		}
		
		@Override
		public String toString() {
			return "start time=" + this.startTime + " end time=" + this.endTime;
		}
	}
    
    private class DeadlineQualifier implements Qualifier {
    	private String deadline;
    	
    	DeadlineQualifier(String deadline) {
    		this.deadline = deadline;
    	}

		@Override
		public boolean run(ReadOnlyTask task) {
			
			if(this.deadline.isEmpty())
				return true;
			
			if(task.getType().equals(TaskType.FLOATING))
				return false;
			
			String deadline = task.getEndDate().getFormattedDate();
			if(deadline.isEmpty())
				return false;
			
			if(deadline.equals(this.deadline))
				return true;
			
			return false;
		}
    	
    	@Override
    	public String toString() {
    		return "deadline=" + this.deadline;
    	}
    }
    
    private class FindQualifier implements Qualifier {
    	private NameQualifier nameQualifier;
    	private TagQualifier tagQualifier;
    	private PeriodQualifier periodQualifier;
    	private DeadlineQualifier deadlineQualifier;
    	
    	FindQualifier(Set<String> keywordSet, Set<String> tagSet, String startTime, String endTime, String deadline) {
    		this.nameQualifier = new NameQualifier(keywordSet);
    		this.tagQualifier = new TagQualifier(tagSet);
    		this.periodQualifier = new PeriodQualifier(startTime, endTime);
    		this.deadlineQualifier = new DeadlineQualifier(deadline);
    	}
    	
    	@Override
    	public boolean run(ReadOnlyTask task) {
    		return nameQualifier.run(task)
    				&& tagQualifier.run(task)
    				&& periodQualifier.run(task)
    				&& deadlineQualifier.run(task);
    	}
    	
    	@Override
    	public String toString() {
    		return nameQualifier.toString() + " "
    				+ tagQualifier.toString() + " "
    				+ periodQualifier.toString() + " "
    				+ deadlineQualifier.toString() + " ";
    	}
    }
}
