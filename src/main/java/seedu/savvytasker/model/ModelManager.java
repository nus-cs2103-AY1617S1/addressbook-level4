package seedu.savvytasker.model;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.lang.time.DateUtils;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.savvytasker.commons.core.ComponentManager;
import seedu.savvytasker.commons.core.LogsCenter;
import seedu.savvytasker.commons.core.UnmodifiableObservableList;
import seedu.savvytasker.commons.events.model.AliasSymbolChangedEvent;
import seedu.savvytasker.commons.events.model.SavvyTaskerChangedEvent;
import seedu.savvytasker.commons.util.StringUtil;
import seedu.savvytasker.model.alias.AliasSymbol;
import seedu.savvytasker.model.alias.DuplicateSymbolKeywordException;
import seedu.savvytasker.model.alias.SymbolKeywordNotFoundException;
import seedu.savvytasker.model.task.FindType;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.model.task.Task;
import seedu.savvytasker.model.task.TaskList.InvalidDateException;
import seedu.savvytasker.model.task.TaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the savvy tasker data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
	private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
	Date onDate = new Date();
	Date firstDayOfSelectedWeek = new Date();

	//@@author A0138431L
	private final SavvyTasker savvyTasker;
	private final FilteredList<Task> filteredTasks;
	private final SortedList<Task> sortedAndFilteredTasks;
	private final FilteredList<Task> filteredFloatingTasks;
	private final SortedList<Task> sortedAndFilteredFloatingTasks;
	private final FilteredList<Task> filteredDay1Tasks;
	private final SortedList<Task> sortedAndFilteredDay1Tasks;
	private final FilteredList<Task> filteredDay2Tasks;
	private final SortedList<Task> sortedAndFilteredDay2Tasks;
	private final FilteredList<Task> filteredDay3Tasks;
	private final SortedList<Task> sortedAndFilteredDay3Tasks;
	private final FilteredList<Task> filteredDay4Tasks;
	private final SortedList<Task> sortedAndFilteredDay4Tasks;
	private final FilteredList<Task> filteredDay5Tasks;
	private final SortedList<Task> sortedAndFilteredDay5Tasks;
	private final FilteredList<Task> filteredDay6Tasks;
	private final SortedList<Task> sortedAndFilteredDay6Tasks;
	private final FilteredList<Task> filteredDay7Tasks;
	private final SortedList<Task> sortedAndFilteredDay7Tasks;
	private final FilteredList<Task> filteredUpcomingTasks;
	private final SortedList<Task> sortedAndFilteredUpcomingTasks;

	/**
	 * Initializes a ModelManager with the given SavvyTasker
	 * and its variables should not be null
	 */
	public ModelManager(SavvyTasker src) {
		super();
		assert src != null;

		logger.fine("Initializing with savvy tasker: " + src);

		savvyTasker = new SavvyTasker(src);
		filteredTasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredTasks = new SortedList<>(filteredTasks, new TaskSortedByDefault());

		filteredFloatingTasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredFloatingTasks = new SortedList<>(filteredFloatingTasks, new TaskSortedByDefault());

		filteredDay1Tasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredDay1Tasks = new SortedList<>(filteredDay1Tasks, new TaskSortedByDefault());
		filteredDay2Tasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredDay2Tasks = new SortedList<>(filteredDay2Tasks, new TaskSortedByDefault());
		filteredDay3Tasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredDay3Tasks = new SortedList<>(filteredDay3Tasks, new TaskSortedByDefault());
		filteredDay4Tasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredDay4Tasks = new SortedList<>(filteredDay4Tasks, new TaskSortedByDefault());
		filteredDay5Tasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredDay5Tasks = new SortedList<>(filteredDay5Tasks, new TaskSortedByDefault());
		filteredDay6Tasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredDay6Tasks = new SortedList<>(filteredDay6Tasks, new TaskSortedByDefault());
		filteredDay7Tasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredDay7Tasks = new SortedList<>(filteredDay7Tasks, new TaskSortedByDefault());

		filteredUpcomingTasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredUpcomingTasks = new SortedList<>(filteredUpcomingTasks, new TaskSortedByDefault());

		updateFilteredListToShowActive(); // shows only active tasks on start
	}

	public ModelManager() {
		this(new SavvyTasker());
	}

	public ModelManager(ReadOnlySavvyTasker initialData) {
		savvyTasker = new SavvyTasker(initialData);
		filteredTasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredTasks = new SortedList<>(filteredTasks, new TaskSortedByDefault());

		filteredFloatingTasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredFloatingTasks = new SortedList<>(filteredFloatingTasks, new TaskSortedByDefault());

		filteredDay1Tasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredDay1Tasks = new SortedList<>(filteredDay1Tasks, new TaskSortedByDefault());
		filteredDay2Tasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredDay2Tasks = new SortedList<>(filteredDay2Tasks, new TaskSortedByDefault());
		filteredDay3Tasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredDay3Tasks = new SortedList<>(filteredDay3Tasks, new TaskSortedByDefault());
		filteredDay4Tasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredDay4Tasks = new SortedList<>(filteredDay4Tasks, new TaskSortedByDefault());
		filteredDay5Tasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredDay5Tasks = new SortedList<>(filteredDay5Tasks, new TaskSortedByDefault());
		filteredDay6Tasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredDay6Tasks = new SortedList<>(filteredDay6Tasks, new TaskSortedByDefault());
		filteredDay7Tasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredDay7Tasks = new SortedList<>(filteredDay7Tasks, new TaskSortedByDefault());

		filteredUpcomingTasks = new FilteredList<>(savvyTasker.getTasks());
		sortedAndFilteredUpcomingTasks = new SortedList<>(filteredUpcomingTasks, new TaskSortedByDefault());

		updateFilteredListToShowActive(); // shows only active tasks on start
	}
	//@@author

	@Override
	public void resetData(ReadOnlySavvyTasker newData) {
		savvyTasker.resetData(newData);
		indicateSavvyTaskerChanged();
	}

	@Override
	public ReadOnlySavvyTasker getSavvyTasker() {
		return savvyTasker;
	}

	/** Raises an event to indicate the model has changed */
	private void indicateSavvyTaskerChanged() {
		raise(new SavvyTaskerChangedEvent(savvyTasker));
	}
	//@@author A0139916U

	private void indicateAliasSymbolAdded(AliasSymbol symbol) {
		raise(new AliasSymbolChangedEvent(symbol, AliasSymbolChangedEvent.Action.Added));
	}

	private void indicateAliasSymbolRemoved(AliasSymbol symbol) {
		raise(new AliasSymbolChangedEvent(symbol, AliasSymbolChangedEvent.Action.Removed));
	}
	//@@author


	//@@author A0139915W
    @Override
    public synchronized Task deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        Task taskDeleted = savvyTasker.removeTask(target);
        indicateSavvyTaskerChanged();
        return taskDeleted;
    }

    @Override
    public synchronized Task modifyTask(ReadOnlyTask target, Task replacement) throws TaskNotFoundException, InvalidDateException {
        Task taskModified = savvyTasker.replaceTask(target, replacement);
        indicateSavvyTaskerChanged();
        return taskModified;
    }

    @Override
    public synchronized Task addTask(Task t) throws InvalidDateException {
        Task taskAdded = savvyTasker.addTask(t);
        updateFilteredListToShowActive();
        indicateSavvyTaskerChanged();
        return taskAdded;
    }
    
    @Override
    public synchronized LinkedList<Task> addRecurringTask(Task recurringTask) throws InvalidDateException {
        LinkedList<Task> recurringTasks = savvyTasker.addRecurringTasks(recurringTask);
        updateFilteredListToShowActive();
        indicateSavvyTaskerChanged();
        return recurringTasks;
    }
    //@@author

	//@@author A0139916U
    @Override
    public synchronized void addAliasSymbol(AliasSymbol symbol) throws DuplicateSymbolKeywordException {
        savvyTasker.addAliasSymbol(symbol);
        indicateSavvyTaskerChanged();
        indicateAliasSymbolAdded(symbol);
    }

    @Override
    public synchronized void removeAliasSymbol(AliasSymbol symbol) throws SymbolKeywordNotFoundException {
        savvyTasker.removeAliasSymbol(symbol);
        indicateSavvyTaskerChanged();
        indicateAliasSymbolRemoved(symbol);
    }
    
    @Override
    public int getAliasSymbolCount() {
        return savvyTasker.getAliasSymbolCount();
    }
    //@@author

	//=========== Filtered/Sorted Task List Accessors ===============================================================

    //@@author A0139915W
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<ReadOnlyTask>(sortedAndFilteredTasks);
    }

    @Override
    public void updateFilteredListToShowActiveSortedByDueDate() {
        updateFilteredListToShowActive(new TaskSortedByDueDate());
    }

    @Override
    public void updateFilteredListToShowActiveSortedByPriorityLevel() {
        updateFilteredListToShowActive(new TaskSortedByPriorityLevel());
    }
    
    @Override
    public void updateFilteredListToShowActive() {
        updateFilteredTaskList(new PredicateExpression(new TaskIsActiveQualifier()));
    }
    
	private void updateFilteredListToShowActive(Comparator<Task> comparator) {
		updateFilteredTaskList(
				new PredicateExpression(new TaskIsActiveQualifier()),
				comparator);
	}

	@Override
	public void updateFilteredListToShowArchived() {
		updateFilteredTaskList(new PredicateExpression(new TaskIsArchivedQualifier()));
	}

	@Override
	public void updateFilteredTaskList(FindType findType, String[] keywords) {
		assert findType != null;
		Qualifier qualifier = null;
		switch (findType)
		{
		case Partial:
			qualifier = new TaskNamePartialMatchQualifier(keywords);
			break;
		case Full:
			qualifier = new TaskNameFullMatchQualifier(keywords);
			break;
		case Exact:
			qualifier = new TaskNameExactMatchQualifier(keywords);
			break;
		case Category:
			qualifier = new CategoryPartialMatchQualifier(keywords);
			break;
		default:
			assert false; // should never get here.
			break;
		}
		updateFilteredTaskList(new PredicateExpression(qualifier));
	}

	private void updateFilteredTaskList(Expression expression) {
		updateFilteredTaskList(expression, new TaskSortedByDefault());
	}

	private void updateFilteredTaskList(Expression expression, Comparator<Task> comparator) {
		filteredTasks.setPredicate(expression::satisfies);
		sortedAndFilteredTasks.setComparator(comparator);
	}
	//@@author

	//@author A0138431L
	//Get filtered task list according to date category

	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getFilteredFloatingTasks() {
		updateFilteredListToShowFloating();
		return new UnmodifiableObservableList<ReadOnlyTask>(filteredFloatingTasks);
	}

	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getFilteredDailyTasks(int dayOfWeek, Date date) {
		this.onDate = date;
		updateFilteredListToShowDaily(dayOfWeek);
		switch(dayOfWeek) {
		case 0:
			return new UnmodifiableObservableList<ReadOnlyTask>(filteredDay1Tasks);
		case 1:
			return new UnmodifiableObservableList<ReadOnlyTask>(filteredDay2Tasks);
		case 2:
			return new UnmodifiableObservableList<ReadOnlyTask>(filteredDay3Tasks);
		case 3:
			return new UnmodifiableObservableList<ReadOnlyTask>(filteredDay4Tasks);
		case 4:
			return new UnmodifiableObservableList<ReadOnlyTask>(filteredDay5Tasks);
		case 5:
			return new UnmodifiableObservableList<ReadOnlyTask>(filteredDay6Tasks);
		case 6:
			return new UnmodifiableObservableList<ReadOnlyTask>(filteredDay7Tasks);
		default:
			return new UnmodifiableObservableList<ReadOnlyTask>(filteredDay1Tasks);
		}
	}

	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getFilteredUpcomingTasks(Date date) {
		this.firstDayOfSelectedWeek = date;
		updateFilteredListToShowUpcoming();
		return new UnmodifiableObservableList<ReadOnlyTask>(filteredUpcomingTasks);
	}


	//Binding isFloating quantifier predicate to filtered list
	@Override
	public void updateFilteredListToShowFloating() {
		updateFilteredFloatingTaskList(new PredicateExpression(new TaskIsFloatingQualifier()));
	}

	private void updateFilteredFloatingTaskList(Expression expression) {
		updateFilteredFloatingTaskList(expression, new TaskSortedByDefault());
	}

	private void updateFilteredFloatingTaskList(Expression expression, Comparator<Task> comparator) {
		filteredFloatingTasks.setPredicate(expression::satisfies);
		sortedAndFilteredFloatingTasks.setComparator(comparator);
	}

	//Binding isOnDate quantifier predicate to filtered list
	@Override
	public void updateFilteredListToShowDaily(int dayOfWeek) {
		updateFilteredDailyTaskList(new PredicateExpression(new TaskIsOnDateQualifier()), dayOfWeek);
	}

	private void updateFilteredDailyTaskList(Expression expression, int dayOfWeek) {
		updateFilteredDailyTaskList(expression, new TaskSortedByDefault(), dayOfWeek);
	}

	private void updateFilteredDailyTaskList(Expression expression, Comparator<Task> comparator, int dayOfWeek) {
		switch(dayOfWeek) {
		case 0:
			filteredDay1Tasks.setPredicate(expression::satisfies);
			sortedAndFilteredDay1Tasks.setComparator(comparator);
		case 1:
			filteredDay2Tasks.setPredicate(expression::satisfies);
			sortedAndFilteredDay2Tasks.setComparator(comparator);
		case 2:
			filteredDay3Tasks.setPredicate(expression::satisfies);
			sortedAndFilteredDay3Tasks.setComparator(comparator);
		case 3:
			filteredDay4Tasks.setPredicate(expression::satisfies);
			sortedAndFilteredDay4Tasks.setComparator(comparator);
		case 4:
			filteredDay5Tasks.setPredicate(expression::satisfies);
			sortedAndFilteredDay5Tasks.setComparator(comparator);
		case 5:
			filteredDay6Tasks.setPredicate(expression::satisfies);
			sortedAndFilteredDay6Tasks.setComparator(comparator);
		case 6:
			filteredDay7Tasks.setPredicate(expression::satisfies);
			sortedAndFilteredDay7Tasks.setComparator(comparator);

		}
	}

	//Binding isUpcoming quantifier predicate to filtered list
	@Override
	public void updateFilteredListToShowUpcoming() {
		updateFilteredUpcomingTaskList(new PredicateExpression(new TaskIsUpcomingQualifier()));
	}

	private void updateFilteredUpcomingTaskList(Expression expression) {
		updateFilteredUpcomingTaskList(expression, new TaskSortedByDefault());
	}

	private void updateFilteredUpcomingTaskList(Expression expression, Comparator<Task> comparator) {
		filteredUpcomingTasks.setPredicate(expression::satisfies);
		sortedAndFilteredUpcomingTasks.setComparator(comparator);
	}

	//@@author

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
        
        /**
         * Helper method to build Set<String> from String[]
         * @param keywords list of keywords
         */
        default Set<String> createSet(String[] keywords) {
            HashSet<String> _keywords = new HashSet<String>();
            for (String keyword : keywords) {
                _keywords.add(keyword);
            }
            return _keywords;
        }
    }

    //@@author A0139915W
    /**
     * Qualifier matching a partial word from the set of keywords
     */
    private class CategoryPartialMatchQualifier implements Qualifier {
        private Set<String> keyWordsToMatch;

        CategoryPartialMatchQualifier(String[] keyWordsToMatch) {
            this.keyWordsToMatch = createSet(keyWordsToMatch);
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return keyWordsToMatch.stream()
                    .filter(keyword -> StringUtil.containsPartialIgnoreCase(task.getCategory(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "category(PartialMatch)=" + String.join(", ", keyWordsToMatch);
        }
    }

    /**
     * Qualifier matching a partial word from the set of keywords
     */
    private class TaskNamePartialMatchQualifier implements Qualifier {
        private Set<String> keyWordsToMatch;

        TaskNamePartialMatchQualifier(String[] keyWordsToMatch) {
            this.keyWordsToMatch = createSet(keyWordsToMatch);
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return keyWordsToMatch.stream()
                    .filter(keyword -> StringUtil.containsPartialIgnoreCase(task.getTaskName(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "taskName(PartialMatch)=" + String.join(", ", keyWordsToMatch);
        }
    }

    /**
     * Qualifier matching a full word from the set of keywords
     */
    private class TaskNameFullMatchQualifier implements Qualifier {
        private Set<String> keyWordsToMatch;

        TaskNameFullMatchQualifier(String[] keyWordsToMatch) {
            this.keyWordsToMatch = createSet(keyWordsToMatch);
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return keyWordsToMatch.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getTaskName(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "taskName(FullMatch)=" + String.join(", ", keyWordsToMatch);
        }
    }

    /**
     * Qualifier matching a exactly from the set of keywords
     */
    private class TaskNameExactMatchQualifier implements Qualifier {
        private Set<String> keyWordsToMatch;

        TaskNameExactMatchQualifier(String[] keyWordsToMatch) {
            this.keyWordsToMatch = new HashSet<String>();
            this.keyWordsToMatch.add(buildSingleString(keyWordsToMatch));
        }
        
        /**
         * Builds a single string to be matched exactly against the task name.
         * @param keyWordsToMatch list of keywords to match.
         * @return A single string built from the list of keywords.
         */
        private String buildSingleString(String[] keyWordsToMatch) {
            StringBuilder sb = new StringBuilder();
            List<String> keywords = Arrays.asList(keyWordsToMatch);
            Iterator<String> itr = keywords.iterator();
            while (itr.hasNext()) {
                sb.append(itr.next());
                if (itr.hasNext()) {
                    sb.append(" ");
                }
            }
            return sb.toString();
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return keyWordsToMatch.stream()
                    .filter(keyword -> StringUtil.containsExactIgnoreCase(task.getTaskName(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "taskName(ExactMatch)=" + String.join(", ", keyWordsToMatch);
        }
    }

    /**
     * Qualifier for checking if {@link Task} is active. Tasks that are not archived are active.
     *
     */
    private class TaskIsActiveQualifier implements Qualifier {

        @Override
        public boolean run(ReadOnlyTask task) {
            return !task.isArchived();
        }

        @Override
        public String toString() {
            return "isArchived=false";
        }
    }
    
    /**
     * Qualifier for checking if {@link Task} is archived
     *
     */
    private class TaskIsArchivedQualifier implements Qualifier {

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.isArchived();
        }

        @Override
        public String toString() {
            return "isArchived=true";
        }
    }//@@author

	//@@author A0138431L
	/**
	 * Qualifier for checking if {@link Task} is an overdue task
	 *
	 * A overdue task is a deadline or event task with end dateTime after current dateTime
	 * 
	 * @return true if the task is overdue
	 * 
	 */
	private class TaskIsOverdueQualifier implements Qualifier {

		@Override
		public boolean run(ReadOnlyTask task) {

			Date today = new Date();

			boolean isOverdue = false;

			if (task.getEndDateTime() != null) {

				Date endDateTime = task.getEndDateTime();

				if (endDateTime.compareTo(today)<0 && task.isArchived() == false) {

					isOverdue = true;
				}

			}

			return isOverdue;

		}

		@Override
		public String toString() {
			return "isOverdue=true";
		}
	}

	/**
	 * Qualifier for checking if {@link Task} is a floating task
	 *
	 * A floating task do not have start or end time
	 * 
	 * @return true if the task falls on the date specified. else return false
	 * 
	 */
	private class TaskIsFloatingQualifier implements Qualifier {

		@Override
		public boolean run(ReadOnlyTask task) {

			boolean isFloating = false;

			if(task.getStartDateTime() == null && task.getEndDateTime() == null && task.isArchived() == false) {

				isFloating = true;

			}

			return isFloating;        

		}

		@Override
		public String toString() {
			return "isFloating=true";
		}
	}

	/**
	 * Qualifier for checking if {@link Task} falls on the selected date
	 *
	 * Check whether the task is on the date specified (for deadline tasks)
	 * Check whether the date specified is within the range of date the task (for event tasks)
	 * Includes task that are completed.
	 * 
	 * @return true if the task falls on the date specified. else return false
	 *
	 */
	private class TaskIsOnDateQualifier implements Qualifier {

		@Override
		public boolean run(ReadOnlyTask task) {

			Date expectedDate = onDate;

			boolean isOnDate = false;

			//Archived Task
			if(task.isArchived() == true){

				isOnDate = false;

			}
			//Deadline Task
			else if(task.getStartDateTime() == null && task.getEndDateTime() != null) {

				Date endDateTime = task.getEndDateTime();

				if (DateUtils.isSameDay(endDateTime, expectedDate)) {

					isOnDate = true;

				}	

			}
			//Event Task
			else if(task.getStartDateTime() != null && task.getEndDateTime() != null) {

				Date startDateTime = task.getStartDateTime();
				Date endDateTime = task.getEndDateTime();

				if (DateUtils.isSameDay(startDateTime, expectedDate)) {

					isOnDate = true;

				} else if (DateUtils.isSameDay(endDateTime, expectedDate)) {

					isOnDate = true;

				} else if (startDateTime.compareTo(expectedDate)<0 && expectedDate.compareTo(endDateTime)<0) {

					isOnDate = true;

				}
			}

			return isOnDate;        
		}

		@Override
		public String toString() {
			return "isOnDate=true";
		}
	}

	/**
	 * Qualifier for checking if {@link Task} task is upcoming
	 *
	 * A upcoming task is a task that will happen after the last day, 2359 of selected week
	 * 
	 * @return true if the task is a upcoming task
	 * 
	 */
	private class TaskIsUpcomingQualifier implements Qualifier {

		@Override
		public boolean run(ReadOnlyTask task) {

			Date lastDateOfExpectedWeek = firstDayOfSelectedWeek;

			//convert date object to calendar object and add 7 days, last day of the selected week
			Calendar calendarExpectedDate = Calendar.getInstance();
			calendarExpectedDate.setTime(lastDateOfExpectedWeek);
			calendarExpectedDate.add(Calendar.DAY_OF_MONTH, 7);
			calendarExpectedDate.set(Calendar.HOUR_OF_DAY,23);
			calendarExpectedDate.set(Calendar.MINUTE,59);
			calendarExpectedDate.set(Calendar.SECOND,59);

			//convert calendar object back to date object
			lastDateOfExpectedWeek = calendarExpectedDate.getTime();

			boolean isUpcoming = true;

			//Archived Task
			if(task.isArchived() == true){

				isUpcoming = false;

			}

			//Floating Task
			else if(task.getStartDateTime() == null && task.getEndDateTime() == null) {

				isUpcoming = false;

			} 
			//Deadline Task
			else if(task.getStartDateTime() == null && task.getEndDateTime() != null) {


				if (task.getEndDateTime().compareTo(lastDateOfExpectedWeek)<0) {

					isUpcoming = false;

				}	

			}
			//Event Task
			else {

				if (task.getStartDateTime().compareTo(lastDateOfExpectedWeek)<0) {

					isUpcoming = false;

				}	

			}

			return isUpcoming;
		}

		@Override
		public String toString() {
			return "isUpcoming=true";
		}
	}//@@author
    
	//@@author A0139915W

	//========== Inner classes/interfaces used for sorting ==================================================
    
    /**
     * Compares {@link Task} by their default field, id
     */
    private class TaskSortedByDefault implements Comparator<Task> {
        
        @Override
        public int compare(Task task1, Task task2) {
            if (task1 == null && task2 == null) {
                return 0;
            } else if (task1 == null) {
                return 1;
            } else if (task2 == null) {
                return -1;
            } else {
                return task1.getId() - task2.getId();
            }
        }
        
    }
    
    /**
     * Compares {@link Task} by their DueDate
     */
    private class TaskSortedByDueDate implements Comparator<Task> {

        @Override
        public int compare(Task task1, Task task2) {
            if (task1 == null && task2 == null) {
                return 0;
            } else if (task1 == null) {
                return 1;
            } else if (task2 == null) {
                return -1;
            } else {
                // End dates can be nulls (floating tasks)
                // Check for existence of endDateTime before comparing
                if (task1.getEndDateTime() == null &&
                    task2.getEndDateTime() == null) {
                    return 0;
                } else if (task1.getEndDateTime() == null) {
                    return 1;
                } else if (task2.getEndDateTime() == null) {
                    return -1;
                } else {
                    return task1.getEndDateTime().compareTo(task2.getEndDateTime());
                }
            }
        }
        
    }
    
    /**
     * Compares {@link Task} by their PriorityLevel
     */
    private class TaskSortedByPriorityLevel implements Comparator<Task> {

        @Override
        public int compare(Task task1, Task task2) {
            if (task1 == null && task2 == null) return 0;
            else if (task1 == null) return 1;
            else if (task2 == null) return -1;
            else {
                return task2.getPriority().compareTo(task1.getPriority());
            }
        }
        
    }
    //@@author

}