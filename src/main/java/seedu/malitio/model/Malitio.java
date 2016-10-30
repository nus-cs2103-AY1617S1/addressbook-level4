package seedu.malitio.model;

import javafx.collections.ObservableList;
import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.Deadline;
import seedu.malitio.model.task.Event;
import seedu.malitio.model.task.FloatingTask;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;
import seedu.malitio.model.task.UniqueDeadlineList;
import seedu.malitio.model.task.UniqueEventList;
import seedu.malitio.model.task.UniqueEventList.DuplicateEventException;
import seedu.malitio.model.task.UniqueEventList.EventMarkedException;
import seedu.malitio.model.task.UniqueEventList.EventNotFoundException;
import seedu.malitio.model.task.UniqueEventList.EventUnmarkedException;
import seedu.malitio.model.task.UniqueFloatingTaskList;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineCompletedException;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineMarkedException;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineNotFoundException;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineUnmarkedException;
import seedu.malitio.model.task.UniqueDeadlineList.DuplicateDeadlineException;
import seedu.malitio.model.task.UniqueFloatingTaskList.DuplicateFloatingTaskException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskCompletedException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskMarkedException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskNotFoundException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskUnmarkedException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the application level Duplicates are not allowed (by
 * .equals comparison)
 */
public class Malitio implements ReadOnlyMalitio {

    private final UniqueFloatingTaskList tasks;
    private final UniqueDeadlineList deadlines;
    private final UniqueEventList events;
    private final UniqueTagList tags;

    {
        tasks = new UniqueFloatingTaskList();
        deadlines = new UniqueDeadlineList();
        events = new UniqueEventList();
        tags = new UniqueTagList();
    }

    public Malitio() {
    }

    /**
     * Tasks, Schedules and Tags are copied into this Malitio
     */
    public Malitio(ReadOnlyMalitio toBeCopied) {
        this(toBeCopied.getUniqueFloatingTaskList(), toBeCopied.getUniqueDeadlineList(),
                toBeCopied.getUniqueEventList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this Malitio
     */
    public Malitio(UniqueFloatingTaskList tasks, UniqueDeadlineList deadlines, UniqueEventList event,
            UniqueTagList tags) {
        resetData(tasks.getInternalList(), deadlines.getInternalList(), event.getInternalList(),
                tags.getInternalList());
    }

    public static ReadOnlyMalitio getEmptymalitio() {
        return new Malitio();
    }

    //// list overwrite operations

    public ObservableList<FloatingTask> getFloatingTasks() {
        return tasks.getInternalList();
    }

    public ObservableList<Deadline> getDeadlines() {
        deadlines.sort();
        return deadlines.getInternalList();
    }

    public ObservableList<Event> getEvents() {
        events.sort();
        return events.getInternalList();
    }

    public void setTasks(List<FloatingTask> floatingTask) {
        this.tasks.getInternalList().setAll(floatingTask);
    }

    public void setDeadlines(List<Deadline> deadlines) {
        this.deadlines.getInternalList().setAll(deadlines);
    }

    public void setEvents(List<Event> events) {
        this.events.getInternalList().setAll(events);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyFloatingTask> newTasks,
            Collection<? extends ReadOnlyDeadline> newDeadlines, Collection<? extends ReadOnlyEvent> newEvents,
            Collection<Tag> newTags) {
        setTasks(newTasks.stream().map(FloatingTask::new).collect(Collectors.toList()));
        setDeadlines(newDeadlines.stream().map(Deadline::new).collect(Collectors.toList()));
        setEvents(newEvents.stream().map(t -> {
            try {
                return new Event(t);
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyMalitio newData) {
        resetData(newData.getFloatingTaskList(), newData.getDeadlineList(), newData.getEventList(),
                newData.getTagList());
    }

    //// task-level operations

    /**
     * Adds a task to Malitio. Also checks the new task's tags and updates
     * {@link #tags} with any new tags found, and updates the Tag objects in the
     * task to point to those in {@link #tags}.
     *
     * @throws UniqueFloatingTaskList.DuplicateFloatingTaskException
     *             if an equivalent task already exists.
     * @throws DuplicateDeadlineException
     * @throws DuplicateEventException
     */
    public void addTask(Object p)
            throws DuplicateFloatingTaskException, DuplicateDeadlineException, DuplicateEventException {
        addToCorrectList(p);
        syncTagsWithMasterList(p);
    }

    /**
     * Checks for the type of the p and adds to the correct list in Malitio.
     * 
     * @param p
     *            task which can be FloatingTask, Deadline or Event
     * @throws DuplicateFloatingTaskException
     * @throws DuplicateDeadlineException
     * @throws DuplicateEventException
     */
    private void addToCorrectList(Object p)
            throws DuplicateFloatingTaskException, DuplicateDeadlineException, DuplicateEventException {
        if (isFloatingTask(p)) {
            tasks.add((FloatingTask) p);
        } else if (isDeadline(p)) {
            deadlines.add((Deadline) p);
            deadlines.sort();
        } else {
            events.add((Event) p);
            events.sort();
        }
    }

    public void addTask(Object p, int index) throws UniqueFloatingTaskList.DuplicateFloatingTaskException {
        syncTagsWithMasterList(p);
        tasks.add((FloatingTask) p, index);
    }

    /**
     * Ensures that every tag in this task: - exists in the master list
     * {@link #tags} - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Object task) {
        final UniqueTagList taskTags = getTagsListFromTask(task);
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
        setTagsToTask(task, commonTagReferences);
    }

    private boolean isFloatingTask(Object p) {
        return p instanceof FloatingTask || p instanceof ReadOnlyFloatingTask;
    }

    private boolean isDeadline(Object p) {
        return p instanceof Deadline || p instanceof ReadOnlyDeadline;
    }

    /**
     * Check for the correct task type and set tags to it.
     * 
     * @param task
     *            task can be either FloatingTask, Deadline or Event
     * @param commonTagReferences
     *            set of tags to be added to the task
     */
    private void setTagsToTask(Object task, final Set<Tag> commonTagReferences) {
        if (isFloatingTask(task)) {
            ((FloatingTask) task).setTags(new UniqueTagList(commonTagReferences));
        } else if (isDeadline(task)) {
            ((Deadline) task).setTags(new UniqueTagList(commonTagReferences));
        } else {
            ((Event) task).setTags(new UniqueTagList(commonTagReferences));
        }
    }

    /**
     * Check for the correct task type andget tag list from it.
     * 
     * @param task
     *            task can be either FloatingTask, Deadline or Event
     * @return UniqueTagList of the task
     */
    private UniqueTagList getTagsListFromTask(Object task) {
        UniqueTagList taskTags;
        if (isFloatingTask(task)) {
            taskTags = ((FloatingTask) task).getTags();
        } else if (isDeadline(task)) {
            taskTags = ((Deadline) task).getTags();
        } else {
            taskTags = ((Event) task).getTags();
        }
        return taskTags;
    }

    public boolean removeTask(Object key)
            throws FloatingTaskNotFoundException, DeadlineNotFoundException, EventNotFoundException {
        if (isFloatingTask(key)) {
            return removeFloatingTask(key);
        } else if (isDeadline(key)) {
            return removeDeadline(key);
        } else {
            return removeEvent(key);
        }
    }

    private boolean removeEvent(Object key) throws EventNotFoundException {
        if (events.remove((ReadOnlyEvent) key)) {
            return true;
        } else {
            throw new EventNotFoundException();
        }
    }

    private boolean removeDeadline(Object key) throws DeadlineNotFoundException {
        if (deadlines.remove((ReadOnlyDeadline) key)) {
            return true;
        } else {
            throw new DeadlineNotFoundException();
        }
    }

    private boolean removeFloatingTask(Object key) throws FloatingTaskNotFoundException {
        if (tasks.remove((ReadOnlyFloatingTask) key)) {
            return true;
        } else {
            throw new FloatingTaskNotFoundException();
        }
    }

    public void editTask(Object edited, Object beforeEdit)
            throws FloatingTaskNotFoundException, DuplicateFloatingTaskException, DuplicateDeadlineException,
            DeadlineNotFoundException, DuplicateEventException, EventNotFoundException {
        syncTagsWithMasterList(edited);
        editTaskAccordingToTaskType(edited, beforeEdit);
    }

    /**
     * Checks for the task type of the edited and beforeEdit objects and assign
     * the editing accordingly.
     * 
     * @param edited
     *            the edited task
     * @param beforeEdit
     *            the task to be edited
     * @throws DuplicateFloatingTaskException
     * @throws FloatingTaskNotFoundException
     * @throws DuplicateDeadlineException
     * @throws DeadlineNotFoundException
     * @throws DuplicateEventException
     * @throws EventNotFoundException
     */
    private void editTaskAccordingToTaskType(Object edited, Object beforeEdit)
            throws DuplicateFloatingTaskException, FloatingTaskNotFoundException, DuplicateDeadlineException,
            DeadlineNotFoundException, DuplicateEventException, EventNotFoundException {
        if (edited instanceof FloatingTask && beforeEdit instanceof ReadOnlyFloatingTask) {
            tasks.edit((FloatingTask) edited, (ReadOnlyFloatingTask) beforeEdit);
        } else if (edited instanceof Deadline && beforeEdit instanceof ReadOnlyDeadline) {
            deadlines.edit((Deadline) edited, (ReadOnlyDeadline) beforeEdit);
            deadlines.sort();
        } else {
            events.edit((Event) edited, (Event) beforeEdit);
            events.sort();
        }
    }

    public void completeTask(ReadOnlyFloatingTask taskToComplete)
            throws FloatingTaskCompletedException, FloatingTaskNotFoundException {
        tasks.complete(taskToComplete);
    }

    public void completeDeadline(ReadOnlyDeadline deadlineToComplete)
            throws DeadlineCompletedException, DeadlineNotFoundException {
        deadlines.complete(deadlineToComplete);

    }

    public void markTask(Object taskToMark, boolean marked)
            throws FloatingTaskNotFoundException, FloatingTaskMarkedException, FloatingTaskUnmarkedException,
            DeadlineNotFoundException, DeadlineMarkedException, DeadlineUnmarkedException, EventNotFoundException,
            EventMarkedException, EventUnmarkedException {
        if (isFloatingTask(taskToMark)) {
            tasks.mark((ReadOnlyFloatingTask) taskToMark, marked);
        } else if (isDeadline(taskToMark)) {
            deadlines.mark((ReadOnlyDeadline) taskToMark, marked);
        } else {
            events.mark((ReadOnlyEvent) taskToMark, marked);
        }
    }

    public void markTask(ReadOnlyFloatingTask taskToMark, boolean marked)
            throws FloatingTaskNotFoundException, FloatingTaskMarkedException, FloatingTaskUnmarkedException {
        tasks.mark(taskToMark, marked);
    }

    public void markDeadline(ReadOnlyDeadline deadlineToMark, boolean marked)
            throws DeadlineNotFoundException, DeadlineMarkedException, DeadlineUnmarkedException {
        deadlines.mark(deadlineToMark, marked);
    }

    public void markEvent(ReadOnlyEvent eventToMark, boolean marked)
            throws EventNotFoundException, EventMarkedException, EventUnmarkedException {
        events.mark(eventToMark, marked);
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() + " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyFloatingTask> getFloatingTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    public List<ReadOnlyDeadline> getDeadlineList() {
        return Collections.unmodifiableList(deadlines.getInternalList());
    }

    @Override
    public List<ReadOnlyEvent> getEventList() {
        return Collections.unmodifiableList(events.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueFloatingTaskList getUniqueFloatingTaskList() {
        return this.tasks;
    }

    @Override
    public UniqueDeadlineList getUniqueDeadlineList() {
        return this.deadlines;
    }

    @Override
    public UniqueEventList getUniqueEventList() {
        return this.events;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }

    /**
     * sort events by start date
     */
    private void sortEvent() {
        events.sort();
    }

    private void sortDeadline() {
        deadlines.sort();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Malitio // instanceof handles nulls
                        && this.tasks.equals(((Malitio) other).tasks)
                        && this.deadlines.equals(((Malitio) other).deadlines)
                        && this.events.equals(((Malitio) other).events) && this.tags.equals(((Malitio) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(tasks, deadlines, events, tags);
    }

}
