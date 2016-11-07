package seedu.lifekeeper.model;

import javafx.collections.ObservableList;
import seedu.lifekeeper.model.activity.Activity;
import seedu.lifekeeper.model.activity.ActivityManager;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;
import seedu.lifekeeper.model.activity.UniqueActivityList;
import seedu.lifekeeper.model.activity.UpcomingReminders;
import seedu.lifekeeper.model.activity.UniqueActivityList.DuplicateTaskException;
import seedu.lifekeeper.model.activity.UniqueActivityList.TaskNotFoundException;
import seedu.lifekeeper.model.tag.Tag;
import seedu.lifekeeper.model.tag.UniqueTagList;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class LifeKeeper implements ReadOnlyLifeKeeper {

    private final UniqueActivityList activities;
    private final UniqueTagList tags;
    private final UpcomingReminders nextReminders;

    {
        activities = new UniqueActivityList();
        tags = new UniqueTagList();
        nextReminders = new UpcomingReminders();
    }

    public LifeKeeper() {}

    /**
     * Activities and Tags are copied into this addressbook
     */
    public LifeKeeper(ReadOnlyLifeKeeper toBeCopied) {
        this(toBeCopied.getUniqueActivityList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Activities and Tags are copied into this addressbook
     */
    public LifeKeeper(UniqueActivityList activities, UniqueTagList tags) {
        resetData(activities.getInternalList(), tags.getInternalList());
        nextReminders.initialize(activities.getInternalList());
    }

    public static ReadOnlyLifeKeeper getEmptyAddressBook() {
        return new LifeKeeper();
    }

//// list overwrite operations

    public ObservableList<Activity> getAllEntries() {
        return activities.getInternalList();
    }
    
    public ObservableList<Tag> getTag() {
        return tags.getInternalList();
    }

    public void setActivities(List<Activity> activities) {
        this.activities.getInternalList().setAll(activities);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyActivity> newActivities, Collection<Tag> newTags) {
        List<Activity> activities = newActivities.stream().map(Activity::create).collect(Collectors.toList()); 
    	setActivities(activities);
        setTags(newTags);
        nextReminders.initialize(activities);
    }

    public void resetData(ReadOnlyLifeKeeper newData) {
        resetData(newData.getActivityList(), newData.getTagList());
    }

//// activity-level operations

    /**
     * Adds a activity to the address book.
     * Also checks the new activity's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the activity to point to those in {@link #tags}.
     *
     * @throws UniqueActivityList.DuplicateTaskException if an equivalent activity already exists.
     */
    public void addActivity(Activity p) throws UniqueActivityList.DuplicateTaskException {
        syncTagsWithMasterList(p);
        activities.addTo(p);
        nextReminders.addReminder(p);
    }
    

	public void addActivity(int index, Activity activity) throws UniqueActivityList.DuplicateTaskException {
        syncTagsWithMasterList(activity);
        activities.addAt(index, activity);
        nextReminders.addReminder(activity);
	}

    /**
     * Ensures that every tag in this activity:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Activity activity) {
        final UniqueTagList activityTags = activity.getTags();
        tags.mergeFrom(activityTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of activity tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : activityTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        activity.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removeActivity(ReadOnlyActivity key) throws UniqueActivityList.TaskNotFoundException {
        if (activities.remove(key)) {
            nextReminders.removeReminder(key);
            return true;
        } else {
            throw new UniqueActivityList.TaskNotFoundException();
        }
    }
    
    //@@author A0125680H
    public Activity editTask(Activity task, Activity newParams, String type) throws TaskNotFoundException, DuplicateTaskException {
            if (activities.contains(task)) {
                Activity newTask = ActivityManager.editUnaffectedParams(task, newParams, type);
                activities.edit(task, newTask);
                nextReminders.removeReminder(task);
                nextReminders.addReminder(newTask);
                
                return newTask;
            } else {
                throw new UniqueActivityList.TaskNotFoundException();
            }
    }

	public void markTask(Activity task, boolean isComplete) throws TaskNotFoundException {
        if (activities.contains(task)) {
            activities.mark(task, isComplete);
        } else {
            throw new UniqueActivityList.TaskNotFoundException();
        }
	}
    
//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return activities.getInternalList().size() + " activities, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyActivity> getActivityList() {
        return Collections.unmodifiableList(activities.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueActivityList getUniqueActivityList() {
        return this.activities;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LifeKeeper // instanceof handles nulls
                && this.activities.equals(((LifeKeeper) other).activities)
                && this.tags.equals(((LifeKeeper) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(activities, tags);
    }



}
