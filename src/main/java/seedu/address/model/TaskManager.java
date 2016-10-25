package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.task.Task;
import seedu.address.model.task.Time;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Recurrence;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the task-manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;
    
    public static int floatingCounter;
    public static int todayCounter;
    public static int tomorrowCounter;
    public static int upcomingCounter;
    public static int overdueCounter;
    
    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public TaskManager() {}

    /**
     * Tasks and Tags are copied into this taskmanager
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this taskmanager
     */
    public TaskManager(UniqueTaskList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTaskManager getEmptyTaskManager() {
        return new TaskManager();
    }

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyTaskManager newData) {
        resetData(newData.getTaskList(), newData.getTagList());
        counter();
    }

//// task-level operations

    /**
     * Adds a task to the task manager.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(p);
        tasks.add(p);
        counter();
    }

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of task tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : taskTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        task.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
        	counter();
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    public void doneTask(ReadOnlyTask task) throws UniqueTaskList.TaskNotFoundException {
    	tasks.done(task);
    	counter();
    }
	
    public void clearDone() throws UniqueTaskList.TaskNotFoundException{
	   	for (int i = 0; i < tasks.getInternalList().size(); i++ ) {
			if (tasks.getInternalList().get(i).getDone().getDoneValue() == true) {
				tasks.remove(tasks.getInternalList().get(i));
				i--;
			}
		}
	   	counter();
	}
    
    public void undoneTask(ReadOnlyTask task) throws UniqueTaskList.TaskNotFoundException {
    	tasks.undone(task);
    	counter();
    }

    public void editTaskName(ReadOnlyTask task, String newInfo) throws UniqueTaskList.TaskNotFoundException, IllegalValueException {
        tasks.editTaskName(task, new Name(newInfo));
counter();

    }
    
    public void editTaskStartTime(ReadOnlyTask task, String newInfo) throws UniqueTaskList.TaskNotFoundException, IllegalValueException {
        tasks.editStartTime(task, new Time(newInfo));
        counter();

    }
    
    public void editTaskEndTime(ReadOnlyTask task, String newInfo) throws UniqueTaskList.TaskNotFoundException, IllegalValueException {
        tasks.editEndTime(task, new Time(newInfo));
        counter();

    }
    
    public void editTaskRecurFreq(ReadOnlyTask task, String newRecur) throws TaskNotFoundException {
	tasks.editRecurFreq(task, new Recurrence(newRecur));
    counter();

    }
    
//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                && this.tasks.equals(((TaskManager) other).tasks)
                && this.tags.equals(((TaskManager) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }


	public void counter() {
		int floating = 0;
		int today = 0;
		int tomorrow = 0;
		int upcoming = 0;
		
		 for (int i = 0; i < tasks.getInternalList().size(); i++) {
			 if (tasks.getInternalList().get(i).getStartTime().isMissing() 
					 && tasks.getInternalList().get(i).getEndTime().isMissing()
					 	&& tasks.getInternalList().get(i).getDone().getDoneValue() == false) {
				 floating++;
				 
			 }
			 if (((tasks.getInternalList().get(i).getStartTime().isToday(tasks.getInternalList().get(i).getStartTime().appearOnUIFormatForDate()))
					 || tasks.getInternalList().get(i).getEndTime().isToday(tasks.getInternalList().get(i).getEndTime().appearOnUIFormatForDate()))
					 	&& tasks.getInternalList().get(i).getDone().getDoneValue() == false) {
				 today++;
				 
			 }
			 if ((tasks.getInternalList().get(i).getStartTime().isTomorrow(tasks.getInternalList().get(i).getStartTime().appearOnUIFormatForDate())
					 || tasks.getInternalList().get(i).getEndTime().isTomorrow(tasks.getInternalList().get(i).getEndTime().appearOnUIFormatForDate()))
					 	&& tasks.getInternalList().get(i).getDone().getDoneValue() == false) {
				 tomorrow++;
				
			 }
			 if ((tasks.getInternalList().get(i).getStartTime().isUpcoming(tasks.getInternalList().get(i).getStartTime().appearOnUIFormatForDate())
					 || tasks.getInternalList().get(i).getEndTime().isUpcoming(tasks.getInternalList().get(i).getEndTime().appearOnUIFormatForDate()))
					 	&& tasks.getInternalList().get(i).getDone().getDoneValue() == false) {
				 upcoming++;
				 
			 }
		 }
		 
		 floatingCounter = floating;
		 todayCounter = today;
		 tomorrowCounter = tomorrow;
		 upcomingCounter = upcoming;
		 System.out.println("Floating: " + floatingCounter);
		 System.out.println("Today: " + todayCounter);
		 System.out.println("Tomorrow: " + tomorrowCounter);
		 System.out.println("Upcoming: " + upcomingCounter);
		 
	}
	
}
