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
import seedu.malitio.model.task.UniqueEventList.EventNotFoundException;
import seedu.malitio.model.task.UniqueFloatingTaskList;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineCompletedException;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineMarkedException;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineNotFoundException;
import seedu.malitio.model.task.UniqueDeadlineList.DuplicateDeadlineException;
import seedu.malitio.model.task.UniqueFloatingTaskList.DuplicateFloatingTaskException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskCompletedException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskMarkedException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the application level
 * Duplicates are not allowed (by .equals comparison)
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

    public Malitio() {}

    /**
     * Tasks, Schedules and Tags are copied into this Malitio
     */
    //@@author A0129595N
    public Malitio(ReadOnlyMalitio toBeCopied) {
        this(toBeCopied.getUniqueFloatingTaskList(), toBeCopied.getUniqueDeadlineList(), toBeCopied.getUniqueEventList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this Malitio
     */
    public Malitio(UniqueFloatingTaskList tasks, UniqueDeadlineList deadlines, UniqueEventList event, UniqueTagList tags) {
        resetData(tasks.getInternalList(), deadlines.getInternalList(), event.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyMalitio getEmptymalitio() {
        return new Malitio();
    }

//// list overwrite operations

    public ObservableList<FloatingTask> getFloatingTasks() {
        return tasks.getInternalList();
    }
    
    public ObservableList<Deadline> getDeadlines() {
        return deadlines.getInternalList();
    }
    
    public ObservableList<Event> getEvents() {
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

    //@@author
    public void resetData(Collection<? extends ReadOnlyFloatingTask> newTasks, Collection<? extends ReadOnlyDeadline> newDeadlines,Collection<? extends ReadOnlyEvent> newEvents, Collection<Tag> newTags) {
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

    //@@author A0129595N
    public void resetData(ReadOnlyMalitio newData) {
        resetData(newData.getFloatingTaskList(), newData.getDeadlineList(), newData.getEventList(), newData.getTagList());
    }


//// task-level operations

    /**
     * Adds a task to Malitio.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueFloatingTaskList.DuplicateFloatingTaskException if an equivalent task already exists.
     */
    public void addFloatingTask(FloatingTask p) throws UniqueFloatingTaskList.DuplicateFloatingTaskException {
        syncTagsWithMasterList(p);
        tasks.add(p);
    }
    
    public void addFloatingTask(FloatingTask p, int index) throws UniqueFloatingTaskList.DuplicateFloatingTaskException {
        syncTagsWithMasterList(p);
        tasks.add(p, index);
        
    }
    
    /**
     * Adds a deadline to Malitio.
     * Also checks the new Deadline's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the deadline to point to those in {@link #tags}.
     *
     * @throws UniqueDeadlineList.DuplicateDeadlineException if an equivalent deadline already exists.
     */
    public void addDeadline(Deadline p) throws UniqueDeadlineList.DuplicateDeadlineException {
        syncTagsWithMasterList(p);
        deadlines.add(p);
        sortDeadline();
    }
    
    /**
     * Adds a event to Malitio.
     * Also checks the new Event's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the event to point to those in {@link #tags}.
     *
     * @throws UniqueEventList.DuplicateEventException if an equivalent event already exists.
     */
    public void addEvent(Event p) throws UniqueEventList.DuplicateEventException {
        syncTagsWithMasterList(p);
        events.add(p);
        sortEvent();
    }

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(FloatingTask task) {
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
    
    private void syncTagsWithMasterList(Deadline deadline) {
        final UniqueTagList taskTags = deadline.getTags();
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
        deadline.setTags(new UniqueTagList(commonTagReferences));
    }
    
    private void syncTagsWithMasterList(Event event) {
        final UniqueTagList taskTags = event.getTags();
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
        event.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removeTask(ReadOnlyFloatingTask key) throws UniqueFloatingTaskList.FloatingTaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueFloatingTaskList.FloatingTaskNotFoundException();
        }
    }
    
    public boolean removeDeadline(ReadOnlyDeadline key) throws UniqueDeadlineList.DeadlineNotFoundException {
        if (deadlines.remove(key)) {
            return true;
        } else {
            throw new UniqueDeadlineList.DeadlineNotFoundException();
        }
    }
    
    public boolean removeEvent(ReadOnlyEvent key) throws EventNotFoundException {
        if (events.remove(key)) {
            return true;
        } else {
            throw new UniqueEventList.EventNotFoundException();
        }     
    }
    
    public void editFloatingTask(FloatingTask edited, ReadOnlyFloatingTask beforeEdit) throws DuplicateFloatingTaskException, FloatingTaskNotFoundException {
        syncTagsWithMasterList(edited);
        tasks.edit(edited, beforeEdit);
    }
    
    public void editDeadline(Deadline edited, ReadOnlyDeadline beforeEdit) throws DuplicateDeadlineException, DeadlineNotFoundException {
        syncTagsWithMasterList(edited);
        deadlines.edit(edited, beforeEdit);
    }
    
    public void editEvent(Event edited, ReadOnlyEvent beforeEdit) throws DuplicateEventException, EventNotFoundException {
        syncTagsWithMasterList(edited);
        events.edit(edited, beforeEdit);
    }
    
	public void completeTask(ReadOnlyFloatingTask taskToComplete) throws FloatingTaskCompletedException, FloatingTaskNotFoundException {
        tasks.complete(taskToComplete);
	}
	
	public void completeDeadline(ReadOnlyDeadline deadlineToComplete) throws DeadlineCompletedException, DeadlineNotFoundException {
		deadlines.complete(deadlineToComplete);
		
	}
	
	public void markTask(ReadOnlyFloatingTask taskToMark) throws FloatingTaskNotFoundException, FloatingTaskMarkedException {
	    tasks.mark(taskToMark);
	}
	
	public void markDeadline(ReadOnlyDeadline deadlineToMark) throws DeadlineNotFoundException, DeadlineMarkedException {
	    deadlines.mark(deadlineToMark);
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
    //@@author
    private void sortEvent() {
    	events.sort();
    }
    
    private void sortDeadline() {
    	deadlines.sort();
    }


    @Override
    //@@author A0129595N
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Malitio // instanceof handles nulls
                && this.tasks.equals(((Malitio) other).tasks)
                && this.deadlines.equals(((Malitio) other).deadlines)
                && this.events.equals(((Malitio) other).events)
                && this.tags.equals(((Malitio) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, deadlines, events, tags);
    }

}
