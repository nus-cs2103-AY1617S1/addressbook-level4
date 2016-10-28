package tars.model;

import javafx.collections.ObservableList;
import tars.model.task.Task;
import tars.model.task.DateTime;
import tars.model.task.Name;
import tars.model.task.Priority;
import tars.model.task.ReadOnlyTask;
import tars.model.task.Status;
import tars.model.task.UniqueTaskList;
import tars.model.task.UniqueTaskList.TaskNotFoundException;
import tars.model.task.rsv.RsvTask;
import tars.model.task.rsv.UniqueRsvTaskList;
import tars.model.task.rsv.UniqueRsvTaskList.RsvTaskNotFoundException;
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.util.DateTimeUtil;
import tars.logic.parser.ArgumentTokenizer;
import tars.logic.parser.Prefix;
import tars.model.tag.ReadOnlyTag;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList;
import tars.model.tag.UniqueTagList.DuplicateTagException;
import tars.model.tag.UniqueTagList.TagNotFoundException;

import java.time.DateTimeException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the tars level Duplicates are not allowed (by .equals
 * comparison)
 */
public class Tars implements ReadOnlyTars {

	private final UniqueTaskList tasks;
	private final UniqueTagList tags;
    private final UniqueRsvTaskList rsvTasks;

	private static final int DATETIME_INDEX_OF_ENDDATE = 1;
	private static final int DATETIME_INDEX_OF_STARTDATE = 0;
	
	private static final String EMPTY_STRING = "";

	{
		tasks = new UniqueTaskList();
		tags = new UniqueTagList();
		rsvTasks = new UniqueRsvTaskList();
	}

	public Tars() {
	}

	/**
	 * Tasks and Tags are copied into this tars
	 */
	public Tars(ReadOnlyTars toBeCopied) {
		this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList(), toBeCopied.getUniqueRsvTaskList());
	}

	/**
	 * Tasks and Tags are copied into this tars
	 */
	public Tars(UniqueTaskList tasks, UniqueTagList tags, UniqueRsvTaskList rsvTasks) {
		resetData(tasks.getInternalList(), rsvTasks.getInternalList(), tags.getInternalList());
	}

	public static ReadOnlyTars getEmptyTars() {
		return new Tars();
	}

	//// list overwrite operations

	public ObservableList<Task> getTasks() {
		return tasks.getInternalList();
	}
	
	public ObservableList<RsvTask> getRsvTasks() {
        return rsvTasks.getInternalList();
    }

	public void setTasks(List<Task> tasks) {
		this.tasks.getInternalList().setAll(tasks);
	}
	
    public void setRsvTasks(List<RsvTask> rsvTasks) {
        this.rsvTasks.getInternalList().setAll(rsvTasks);
    }

	/**
	 * Replaces task in tars internal list
	 *
	 * @@author A0121533W
	 * @throws DuplicateTaskException
	 *             if replacement task is the same as the task to replace
	 */
	public void replaceTask(ReadOnlyTask toReplace, Task replacement) throws DuplicateTaskException {
		if (toReplace.isSameStateAs(replacement)) {
			throw new DuplicateTaskException();
		}
		ObservableList<Task> list = this.tasks.getInternalList();
		int toReplaceIndex = -1;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).isSameStateAs(toReplace)) {
				toReplaceIndex = i;
				break;
			}
		}
		list.set(toReplaceIndex, replacement);
	}

	public void setTags(Collection<Tag> tags) {
		this.tags.getInternalList().setAll(tags);
	}

	public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<RsvTask> newRsvTasks, Collection<Tag> newTags) {
		setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
		setRsvTasks(newRsvTasks.stream().collect(Collectors.toList()));
		setTags(newTags);
	}

	public void resetData(ReadOnlyTars newData) {
		resetData(newData.getTaskList(), newData.getRsvTaskList(), newData.getTagList());
	}

	//// task-level operations

	/**
	 * Adds a task to tars. Also checks the new task's tags and updates
	 * {@link #tags} with any new tags found, and updates the Tag objects in the
	 * task to point to those in {@link #tags}.
	 *
	 * @throws UniqueTaskList.DuplicateTaskException
	 *             if an equivalent task already exists.
	 */
	public void addTask(Task p) throws DuplicateTaskException {
		syncTagsWithMasterList(p);
		tasks.add(p);
	}
	
    /**
     * Adds a reserved task to tars.
     *
     * @@author A0124333U
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent reserved task already exists.
     */
    public void addRsvTask(RsvTask rt) throws DuplicateTaskException {
        rsvTasks.add(rt);
    }

	/**
	 * Edits a task in tars
	 * 
	 * @@author A0121533W
	 * @throws UniqueTaskList.TaskNotFoundException
	 *             if task to edit could not be found.
	 * @throws DateTimeException
	 *             if problem encountered while parsing dateTime.
	 * @throws DuplicateTagException
	 *             if the Tag to add is a duplicate of an existing Tag in the
	 *             list.
	 * @throws TagNotFoundException
	 *             if no such tag could be found.
	 * @throws IllegalValueException
	 *             if argument(s) in argsToEdit is/are invalid.
	 */
	public Task editTask(ReadOnlyTask toEdit, ArgumentTokenizer argsTokenizer) throws TaskNotFoundException,
			DateTimeException, DuplicateTagException, TagNotFoundException, IllegalValueException {
		if (!tasks.getInternalList().contains(toEdit)) {
			throw new TaskNotFoundException();
		}

        Prefix namePrefix = new Prefix("/n");
        Prefix priorityPrefix = new Prefix("/p");
        Prefix dateTimePrefix = new Prefix("/dt");
        Prefix addTagPrefix = new Prefix("/ta");
        Prefix removeTagPrefix = new Prefix("/tr");

		Task taskToEdit = new Task(toEdit);

		// Edit Name
		if (!argsTokenizer.getValue(namePrefix).orElse(EMPTY_STRING).equals(EMPTY_STRING)) {
			Name editedName = new Name(argsTokenizer.getValue(namePrefix).get());
			taskToEdit.setName(editedName);
		}

        // Edit Priority
        if (!argsTokenizer.getValue(priorityPrefix).orElse(EMPTY_STRING).equals(EMPTY_STRING)) {
            Priority editedPriority = new Priority(argsTokenizer.getValue(priorityPrefix).get());
            taskToEdit.setPriority(editedPriority);
        }

		// Edit DateTime
        if (!argsTokenizer.getValue(dateTimePrefix).orElse(EMPTY_STRING).equals(EMPTY_STRING)) {
            String[] dateTimeArray =
                    DateTimeUtil.getDateTimeFromArgs(argsTokenizer.getValue(dateTimePrefix).get());
            DateTime editedDateTime = new DateTime(dateTimeArray[DATETIME_INDEX_OF_STARTDATE],
                    dateTimeArray[DATETIME_INDEX_OF_ENDDATE]);
            taskToEdit.setDateTime(editedDateTime);
        }

		// Add Tags
        Set<String> tagsToAdd =
                argsTokenizer.getMultipleValues(addTagPrefix).orElse(new HashSet<>());
        for (String t : tagsToAdd) {
            Tag toAdd = new Tag(t);
            UniqueTagList replacement = taskToEdit.getTags();
            replacement.add(toAdd);
            taskToEdit.setTags(replacement);
        }

		// Remove Tags
        Set<String> tagsToRemove =
                argsTokenizer.getMultipleValues(removeTagPrefix).orElse(new HashSet<>());
        for (String t : tagsToRemove) {
            Tag toRemove = new Tag(t);
            UniqueTagList replacement = taskToEdit.getTags();
            replacement.remove(toRemove);
            taskToEdit.setTags(replacement);
        }

		replaceTask(toEdit, taskToEdit);
		syncTagsWithMasterList(taskToEdit);
		
		return taskToEdit;
	}

	/**
	 * Marks every task in respective lists as done or undone
	 * 
	 * @@author A0121533W
	 * @throws DuplicateTaskException
	 */
	public void mark(ArrayList<ReadOnlyTask> toMarkList, Status status) throws DuplicateTaskException {
		for (ReadOnlyTask t : toMarkList) {
			if (!t.getStatus().equals(status)) {
				// prevent marking tasks which are already marked
				Task toMark = new Task(t);
				toMark.setStatus(status);
				replaceTask(t, toMark);
			}
		}
	}

	/**
	 * Ensures that every tag in this task: - exists in the master list
	 * {@link #tags} - points to a Tag object in the master list
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
			return true;
		} else {
			throw new UniqueTaskList.TaskNotFoundException();
		}
	}
	
	public boolean removeRsvTask(RsvTask key) throws RsvTaskNotFoundException {
        if (rsvTasks.remove(key)) {
            return true;
        } else {
            throw new RsvTaskNotFoundException();
        }
    }

	/** 
	 * Sorts internal list by priority from low to high
	 * 
	 * @@author A0140022H 
	 */
	public void sortByPriority() {
		this.tasks.getInternalList().sort(new Comparator<Task>() {
		    @Override
			public int compare(Task o1, Task o2) {
				return o1.getPriority().compareTo(o2.getPriority());
			}
		});
	}
	
	/** 
	 * Sorts internal list by priority from high to low
	 * 
	 * @@author A0140022H 
	 */
	public void sortByPriorityDescending() {
		this.tasks.getInternalList().sort(new Comparator<Task>() {
			@Override
			public int compare(Task o1, Task o2) {
				return o2.getPriority().compareTo(o1.getPriority());
			}
		});
	}
	
	/** 
	 * Sorts internal list by earliest end dateTime first
	 * 
	 * @@author A0140022H 
	 */
	public void sortByDatetime() {
		this.tasks.getInternalList().sort(new Comparator<Task>() {
			@Override
			public int compare(Task o1, Task o2) {
				return o1.getDateTime().compareTo(o2.getDateTime());
			}
		});
	}
	
	/** 
	 * Sorts internal list by latest end dateTime first
	 * 
	 * @@author A0140022H 
	 */
	public void sortByDatetimeDescending() {
		this.tasks.getInternalList().sort(new Comparator<Task>() {
			@Override
			public int compare(Task o1, Task o2) {
				return o2.getDateTime().compareTo(o1.getDateTime());
			}
		});
	}

	//// tag-level operations

	public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
		tags.add(t);
	}

	public void removeTag(Tag t) throws UniqueTagList.TagNotFoundException {
		tags.remove(t);
	}
	
	/**
     * Rename all task which has the old tag with the new tag
     * 
     * @param oldTag tag to be replaced with new tag name
     * @param tagToUpdate new tag name
     * @throws IllegalValueException if the given tag name string is invalid.
     * @throws TagNotFoundException if there is no matching tags.
     */
    public void renameTag(ReadOnlyTag oldTag, Tag newTag)
            throws IllegalValueException, TagNotFoundException, DuplicateTagException {

        for (int i = 0; i < tasks.getInternalList().size(); i++) {
            Task toEdit = new Task(tasks.getInternalList().get(i));
            UniqueTagList tags = toEdit.getTags();
            if (tags.contains(new Tag(oldTag))) {
                tags.remove(new Tag(oldTag));
                tags.add(newTag);
                toEdit.setTags(tags);
                tasks.getInternalList().set(i, toEdit);
            }
        }
    }
    
    /**
     * Delete tag from all tasks
     * 
     * @param toBeDeleted
     * @throws IllegalValueException if the given tag name string is invalid.
     * @throws TagNotFoundException if there is no matching tags.
     */
    public void deleteTag(ReadOnlyTag toBeDeleted)
            throws IllegalValueException, TagNotFoundException, DuplicateTagException {

        for (int i = 0; i < tasks.getInternalList().size(); i++) {
            Task toEdit = new Task(tasks.getInternalList().get(i));
            UniqueTagList tags = toEdit.getTags();
            if (tags.contains(new Tag(toBeDeleted))) {
                tags.remove(new Tag(toBeDeleted));
                toEdit.setTags(tags);
                tasks.getInternalList().set(i, toEdit);
            }
        }
    }

	//// util methods

	@Override
	public String toString() {
		return tasks.getInternalList().size() + " tasks, " + rsvTasks.getInternalList().size() + " reserved tasks," + tags.getInternalList().size() + " tags";
	}

	@Override
	public List<ReadOnlyTask> getTaskList() {
		return Collections.unmodifiableList(tasks.getInternalList());
	}

	@Override
    public List<RsvTask> getRsvTaskList() {
        return Collections.unmodifiableList(rsvTasks.getInternalList());
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
    public UniqueRsvTaskList getUniqueRsvTaskList() {
        return this.rsvTasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tars // instanceof handles nulls
                        && this.tasks.equals(((Tars) other).tasks)
                        && this.rsvTasks.equals(((Tars) other).rsvTasks)
                        && this.tags.equals(((Tars) other).tags));
    }

	@Override
	public int hashCode() {
		// use this method for custom fields hashing instead of implementing
		// your own
		return Objects.hash(tasks, tags, rsvTasks);
	}

}
