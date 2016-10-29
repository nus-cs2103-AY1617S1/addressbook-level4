package seedu.address.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.FilePathChangeEvent;
import seedu.address.commons.events.model.TaskListChangedEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.RecurringTaskManager;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskOcurrence;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskType;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.task.UniqueTaskList.TimeslotOverlapException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskMaster taskMaster;
    private final List<Task> tasks;
    private final FilteredList<TaskOcurrence> filteredTaskComponents;
    
    //@@author A0135782Y
    /**
     * Initializes a ModelManager with the given TaskList
     * TaskList and its variables should not be null
     */
    public ModelManager(TaskMaster src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;
        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        taskMaster = new TaskMaster(src);
        tasks = taskMaster.getTasks();
        filteredTaskComponents = new FilteredList<>(taskMaster.getTaskComponentList());
        RecurringTaskManager.getInstance().setTaskList(taskMaster.getUniqueTaskList());
        RecurringTaskManager.getInstance().updateAnyRecurringTasks();
        
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
        RecurringTaskManager.getInstance().setTaskList(taskMaster.getUniqueTaskList());
        RecurringTaskManager.getInstance().updateAnyRecurringTasks();
      
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
    public synchronized void deleteTask(TaskOcurrence target) throws TaskNotFoundException {
        taskMaster.removeTask(target.getTaskReference());
        indicateTaskListChanged();
    }
    
    //@@author A0147995H
    @Override
    public synchronized void editTask(Task target, Name name, UniqueTagList tags,
    		TaskDate startDate, TaskDate endDate, RecurringType recurringType) throws TaskNotFoundException, TimeslotOverlapException {
    	taskMaster.updateTask(target, name, tags, startDate, endDate, recurringType);
    	indicateTaskListChanged();
    	updateFilteredListToShowAll();
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
    public synchronized void archiveTask(TaskOcurrence target) throws TaskNotFoundException {
        taskMaster.archiveTask(target);
        indicateTaskListChanged();
        updateFilteredListToShowAll();
    }
    
    @Override
	public void changeDirectory(String filePath) {
		raise(new FilePathChangeEvent(filePath));
	}
    //@@author

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return new ArrayList<ReadOnlyTask>(tasks);
    }

    @Override
    public UnmodifiableObservableList<TaskOcurrence> getFilteredTaskComponentList() {
        return new UnmodifiableObservableList<>(filteredTaskComponents);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTaskComponents.setPredicate(new PredicateExpression(new ArchiveQualifier(true))::unsatisfies);       
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords, Set<String> tags, Date startDate, Date endDate, Date deadline) {
        updateFilteredTaskList(new PredicateExpression(new FindQualifier(keywords, tags, startDate, endDate, deadline)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTaskComponents.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(TaskOcurrence t);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(TaskOcurrence task) {
            return qualifier.run(task);
        }
        
        
        public boolean unsatisfies(TaskOcurrence task) {
            return !qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(TaskOcurrence task);
        String toString();
    }
    
    //@@author A0147967J
    private class TypeQualifier implements Qualifier {
        private TaskType typeKeyWords;

        TypeQualifier(TaskType typeKeyWords) {
            this.typeKeyWords = typeKeyWords;
        }

        @Override
        public boolean run(TaskOcurrence task) {
        	return task.getTaskReference().getTaskType().equals(typeKeyWords) && !task.isArchived();
        }

        @Override
        public String toString() {
            return "type=" + typeKeyWords.toString();
        }
    }
    //@@author

    //@@author A0135782Y
    private class ArchiveQualifier implements Qualifier {
        private boolean isArchived;

        ArchiveQualifier(boolean isItArchive) {
            this.isArchived= isItArchive;
        }

        @Override
        public boolean run(TaskOcurrence task) {
            return task.isArchived() == isArchived;
        }

        @Override
        public String toString() {
            return "type=" + isArchived;
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
        public boolean run(TaskOcurrence task) {
        	if(nameKeyWords.isEmpty())
        		return true;
        		
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getTaskReference().getName().fullName, keyword))
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
    	
    	private String tagToString(TaskOcurrence task) {
    		Set<Tag> tagSet = task.getTaskReference().getTags().toSet();
    		Set<String> tagStringSet = new HashSet<String>();
    		for(Tag t : tagSet) {
    			tagStringSet.add(t.tagName);
    		}
    		return String.join(" ", tagStringSet);
    	}

		@Override
		public boolean run(TaskOcurrence task) {
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
		
		private Date[] extractTaskPeriod(TaskOcurrence task) {
			TaskType type = task.getTaskReference().getTaskType();
			if(type.equals(TaskType.FLOATING)) {
				return null;
			}
			
			if(task.getStartDate().getDateInLong() == TaskDate.DATE_NOT_PRESENT) {
				return null;
			}
			
			Date startDate = new Date(task.getStartDate().getDateInLong());
			Date endDate = new Date(task.getEndDate().getDateInLong());
			return new Date[]{ startDate, endDate };
		}

		@Override
		public boolean run(TaskOcurrence task) {
			
			if(this.endTime == null)
				return true;
				
			Date[] timeArray = extractTaskPeriod(task);
			if(timeArray == null)
				return false;

			Date startDate = timeArray[START_DATE_INDEX];
			Date endDate = timeArray[END_DATE_INDEX];
			
			if(!startDate.before(this.startTime)
					&& !endDate.after(this.endTime))
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

		@Override
		public boolean run(TaskOcurrence task) {
			
			if(this.deadline == null)
				return true;
			
			if(task.getTaskReference().getTaskType().equals(TaskType.FLOATING))
				return false;
			
			if(task.getEndDate().getDateInLong() == TaskDate.DATE_NOT_PRESENT)
				return false;
			
			Date deadline = new Date(task.getEndDate().getDateInLong());
			
			if((deadline.before(this.deadline) || this.deadline.equals(deadline))
					&& task.getStartDate().getDateInLong() == TaskDate.DATE_NOT_PRESENT)
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
    	private ArchiveQualifier archiveQualifier;
    	
    	FindQualifier(Set<String> keywordSet, Set<String> tagSet, Date startTime, Date endTime, Date deadline) {
    		if(keywordSet.contains("-C")) {
    			this.archiveQualifier = new ArchiveQualifier(true);
    		}
    		if(keywordSet.contains("-F"))
    			this.typeQualifier = new TypeQualifier(TaskType.FLOATING);
    		this.nameQualifier = new NameQualifier(keywordSet);
    		this.tagQualifier = new TagQualifier(tagSet);
    		this.periodQualifier = new PeriodQualifier(startTime, endTime);
    		this.deadlineQualifier = new DeadlineQualifier(deadline);
    	}
    	
    	@Override
    	public boolean run(TaskOcurrence task) {
    		if(this.typeQualifier!=null)
    			return typeQualifier.run(task);
    		if(this.archiveQualifier != null) {
    		    return archiveQualifier.run(task);
    		}
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
    				+ deadlineQualifier.toString() + " "
    				+ archiveQualifier.toString() + " ";
    	}
    }
    //@@author
}
