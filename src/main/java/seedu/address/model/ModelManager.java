package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskType;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.task.UniqueTaskList.TimeslotOverlapException;
import seedu.address.commons.events.model.TaskListChangedEvent;
import seedu.address.commons.events.model.FilePathChangeEvent;
import seedu.address.commons.core.ComponentManager;

import java.util.ArrayDeque;
import java.util.Date;
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
    public synchronized void editTask(Task target, Name name, UniqueTagList tags,
    		TaskDate startDate, TaskDate endDate) throws TaskNotFoundException, TimeslotOverlapException {
    	taskList.updateTask(target, name, tags, startDate, endDate);
    	indicateTaskListChanged();
    	updateFilteredListToShowAll();
    }
    
    @Override
    public synchronized void archiveTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskList.archiveTask(target);
        indicateTaskListChanged();
        updateFilteredListToShowAll();
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
        filteredTasks.setPredicate(new PredicateExpression(new TypeQualifier(TaskType.COMPLETED))::unsatisfies);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords, Set<String> tags, Date startDate, Date endDate, Date deadline) {
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
        
        
        public boolean unsatisfies(ReadOnlyTask task) {
            return !qualifier.run(task);
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
    
    private class TypeQualifier implements Qualifier {
        private TaskType typeKeyWords;

        TypeQualifier(TaskType typeKeyWords) {
            this.typeKeyWords = typeKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {

            return task.getType().equals(typeKeyWords);
        }

        @Override
        public String toString() {
            return "type=" + typeKeyWords.toString();
        }
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
        	if(nameKeyWords.isEmpty())
        		return true;
        		
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
    	
		private Date startTime;
		private Date endTime;
		
		PeriodQualifier(Date startTime, Date endTime) {
			this.startTime = startTime;
			this.endTime = endTime;
		}
		
		private Date[] extractTaskPeriod(ReadOnlyTask task) {
			TaskType type = task.getType();
			if(type.equals(TaskType.FLOATING)) {
				return null;
			}
			
			if(task.getStartDate().getDate() == TaskDate.DATE_NOT_PRESENT
					|| task.getEndDate().getDate() == TaskDate.DATE_NOT_PRESENT) {
				return null;
			}
			
			Date startDate = new Date(task.getStartDate().getDate());
			Date endDate = new Date(task.getEndDate().getDate());
			return new Date[]{ startDate, endDate };
		}

		@Override
		public boolean run(ReadOnlyTask task) {
			
			if(this.startTime == null || this.endTime == null)
				return true;
				
			Date[] timeArray = extractTaskPeriod(task);
			if(timeArray == null)
				return false;

			Date startDate = timeArray[START_DATE_INDEX];
			Date endDate = timeArray[END_DATE_INDEX];
			
			if((startDate.after(this.startTime)||(startDate.getDate()==this.startTime.getDate()&&startDate.getMonth()==this.startTime.getMonth()))
					&& (endDate.before(this.endTime)||(endDate.getDate()==this.endTime.getDate()&&endDate.getMonth()==this.endTime.getMonth())))
				return true;
			return false;	
		}
		
		@Override
		public String toString() {
			if(this.startTime == null || this.endTime == null)
				return "";
			return "start time=" + this.startTime.toString()
				+ " end time=" + this.endTime.toString();
		}
	}
    
    private class DeadlineQualifier implements Qualifier {
    	private Date deadline;
    	
    	DeadlineQualifier(Date deadline) {
    		this.deadline = deadline;
    	}

		@SuppressWarnings("deprecation")
		@Override
		public boolean run(ReadOnlyTask task) {
			
			if(this.deadline == null)
				return true;
			
			if(task.getType().equals(TaskType.FLOATING))
				return false;
			
			if(task.getEndDate().getDate() == TaskDate.DATE_NOT_PRESENT)
				return false;
			
			Date deadline = new Date(task.getEndDate().getDate());
			
			//Strictly before
			if(deadline.before(this.deadline)
					&& task.getStartDate().getDate() == TaskDate.DATE_NOT_PRESENT)
				return true;
			
			//When only enter a date
			if(deadline.getDate()==this.deadline.getDate() && deadline.getMonth()==this.deadline.getMonth()
					&& task.getStartDate().getDate() == TaskDate.DATE_NOT_PRESENT)
				return true;
			
			return false;
		}
    	
    	@Override
    	public String toString() {
    		if(this.deadline == null)
    			return "";
    		
    		return "deadline=" + this.deadline.toString();
    	}
    }
    
    private class FindQualifier implements Qualifier {
    	private NameQualifier nameQualifier;
    	private TagQualifier tagQualifier;
    	private PeriodQualifier periodQualifier;
    	private DeadlineQualifier deadlineQualifier;
    	private TypeQualifier typeQualifier = null;
    	
    	FindQualifier(Set<String> keywordSet, Set<String> tagSet, Date startTime, Date endTime, Date deadline) {
    		if(keywordSet.contains("-C"))
    			this.typeQualifier = new TypeQualifier(TaskType.COMPLETED);
    		if(keywordSet.contains("-F"))
    			this.typeQualifier = new TypeQualifier(TaskType.FLOATING);
    		this.nameQualifier = new NameQualifier(keywordSet);
    		this.tagQualifier = new TagQualifier(tagSet);
    		this.periodQualifier = new PeriodQualifier(startTime, endTime);
    		this.deadlineQualifier = new DeadlineQualifier(deadline);
    	}
    	
    	@Override
    	public boolean run(ReadOnlyTask task) {
    		if(this.typeQualifier!=null)
    			return typeQualifier.run(task);
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
