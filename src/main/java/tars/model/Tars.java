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
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.flags.Flag;
import tars.commons.util.DateTimeUtil;
import tars.commons.util.ExtractorUtil;
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

	private static final int DATETIME_INDEX_OF_ENDDATE = 1;
	private static final int DATETIME_INDEX_OF_STARTDATE = 0;

	{
		tasks = new UniqueTaskList();
		tags = new UniqueTagList();
	}

	public Tars() {
	}

	/**
	 * Tasks and Tags are copied into this tars
	 */
	public Tars(ReadOnlyTars toBeCopied) {
		this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
	}

	/**
	 * Tasks and Tags are copied into this tars
	 */
	public Tars(UniqueTaskList tasks, UniqueTagList tags) {
		resetData(tasks.getInternalList(), tags.getInternalList());
	}

	public static ReadOnlyTars getEmptyTars() {
		return new Tars();
	}

	//// list overwrite operations

	public ObservableList<Task> getTasks() {
		return tasks.getInternalList();
	}

	public void setTasks(List<Task> tasks) {
		this.tasks.getInternalList().setAll(tasks);
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

	public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags) {
		setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
		setTags(newTags);
	}

	public void resetData(ReadOnlyTars newData) {
		resetData(newData.getTaskList(), newData.getTagList());
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
	public Task editTask(ReadOnlyTask toEdit, HashMap<Flag, String> argsToEdit) throws TaskNotFoundException,
			DateTimeException, DuplicateTagException, TagNotFoundException, IllegalValueException {
		if (!tasks.getInternalList().contains(toEdit)) {
			throw new TaskNotFoundException();
		}

		Flag nameFlag = new Flag(Flag.NAME, false);
		Flag priorityFlag = new Flag(Flag.PRIORITY, false);
		Flag dateTimeFlag = new Flag(Flag.DATETIME, false);
		Flag addTagFlag = new Flag(Flag.ADDTAG, true);
		Flag removeTagFlag = new Flag(Flag.REMOVETAG, true);

		Task taskToEdit = new Task(toEdit);

		// Edit Name
		String nameData = argsToEdit.get(nameFlag).replace(Flag.NAME + " ", "");
		if (nameData != "") {
			Name editedName = new Name(nameData);
			taskToEdit.setName(editedName);
		}

		// Edit Priority
		String priorityData = argsToEdit.get(priorityFlag).replace(Flag.PRIORITY + " ", "");
		if (priorityData != "") {
			Priority editedPriority = new Priority(priorityData);
			taskToEdit.setPriority(editedPriority);
		}

		// Edit DateTime
		String dateTimeData = argsToEdit.get(dateTimeFlag).replace(Flag.DATETIME + " ", "");
		if (dateTimeData != "") {
			String[] dateTimeArray = DateTimeUtil.getDateTimeFromArgs(dateTimeData);
			DateTime editedDateTime = new DateTime(dateTimeArray[DATETIME_INDEX_OF_STARTDATE],
					dateTimeArray[DATETIME_INDEX_OF_ENDDATE]);
			taskToEdit.setDateTime(editedDateTime);
		}

		// Add Tags
		String tagsToAddData = argsToEdit.get(addTagFlag);
		Set<String> tagsToAdd = ExtractorUtil.getTagsFromArgs(tagsToAddData, addTagFlag);
		for (String t : tagsToAdd) {
			Tag toAdd = new Tag(t);
			UniqueTagList replacement = taskToEdit.getTags();
			replacement.add(toAdd);
			taskToEdit.setTags(replacement);
		}

		// Remove Tags
		String tagsToRemoveData = argsToEdit.get(removeTagFlag);
		Set<String> tagsToRemove = ExtractorUtil.getTagsFromArgs(tagsToRemoveData, removeTagFlag);
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
	public void mark(ArrayList<ReadOnlyTask> toMarkList, String status) throws DuplicateTaskException {
		if (status.equals(Flag.DONE)) {
			Status done = new Status(true);
			for (ReadOnlyTask t : toMarkList) {
				if (!t.getStatus().equals(done)) {
					// prevent marking tasks as done when it is done
					Task toMark = new Task(t);
					toMark.setStatus(done);
					replaceTask(t, toMark);
				}
			}
		} else if (status.equals(Flag.UNDONE)) {
			Status undone = new Status(false);
			for (ReadOnlyTask t : toMarkList) {
				if (!t.getStatus().equals(undone)) {
					// prevent marking tasks as undone when it is undone
					Task toMark = new Task(t);
					toMark.setStatus(undone);
					replaceTask(t, toMark);
				}
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

	//// util methods

	@Override
	public String toString() {
		return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() + " tags";
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
				|| (other instanceof Tars // instanceof handles nulls
						&& this.tasks.equals(((Tars) other).tasks) && this.tags.equals(((Tars) other).tags));
	}

	@Override
	public int hashCode() {
		// use this method for custom fields hashing instead of implementing
		// your own
		return Objects.hash(tasks, tags);
	}

}
