package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.ActivityList;
import seedu.address.model.activity.FloatingActivity;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.*;

/**
 * Wraps all data at the activity-manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class ActivityManager implements ReadOnlyActivityManager {

    private final ActivityList activities;
    private final UniqueTagList tags;

    {
        activities = new ActivityList();
        tags = new UniqueTagList();
    }

    public ActivityManager() {}

    /**
     * Persons and Tags are copied into this activity manager
     */
    public ActivityManager(ReadOnlyActivityManager toBeCopied) {
        this(toBeCopied.getActivityList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Persons and Tags are copied into this activity manager
     */
    public ActivityManager(ActivityList activities, UniqueTagList tags) {
        resetData(activities.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyActivityManager getEmptyActivityManager() {
        return new ActivityManager();
    }

//// list overwrite operations

    public ObservableList<Activity> getActivities() {
        return activities.getInternalList();
    }

    public void setActivties(List<Activity> activities) {
        this.activities.getInternalList().setAll(activities);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends Activity> newActivities, Collection<Tag> newTags) {
        setActivties(new ArrayList<Activity>(newActivities));
        setTags(newTags);
    }

    public void resetData(ReadOnlyActivityManager newData) {
        resetData(newData.getActivityList().getInternalList(), newData.getTagList());
    }

//// activity-level operations

    /**
     * Adds an activity to the activity manager.
     * Also checks the new acitivity's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     */
    public void addActivity(Activity activity) {
//        syncTagsWithMasterList(activity);
        activities.add(activity);
    }

    /**
     * Ensures that every tag in this person:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
//    private void syncTagsWithMasterList(Activity activity) {
//        final UniqueTagList personTags = activity.getTags();
//        tags.mergeFrom(personTags);
//
//        // Create map with values = tag object references in the master list
//        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
//        for (Tag tag : tags) {
//            masterTagObjects.put(tag, tag);
//        }
//
//        // Rebuild the list of person tags using references from the master list
//        final Set<Tag> commonTagReferences = new HashSet<>();
//        for (Tag tag : personTags) {
//            commonTagReferences.add(masterTagObjects.get(tag));
//        }
//        activity.setTags(new UniqueTagList(commonTagReferences));
//    }

    public boolean removeActivity(Activity key) throws ActivityList.ActivityNotFoundException {
        if (activities.remove(key)) {
            return true;
        } else {
            throw new ActivityList.ActivityNotFoundException();
        }
    }

    public boolean updateActivity(Activity key, String newName) throws ActivityList.ActivityNotFoundException {
    	if (activities.update(key, newName)) {
    		key.setName(newName);
    		return true;
    	} else {
    		throw new ActivityList.ActivityNotFoundException();
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
    public List<Activity> getListActivity() {
        return Collections.unmodifiableList(activities.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

//    @Override
//    public UniquePersonList getUniquePersonList() {
//        return this.activities;
//    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityManager // instanceof handles nulls
                && this.toString().equals(other.toString()));
                // TODO: Re-implement correct equality when tags are corrected
                /*this.activities.equals(((ActivityManager) other).activities)
                 && this.tags.equals(((ActivityManager) other).tags) );*/               
                
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(activities, tags);
    }

	@Override
	public ActivityList getActivityList() {
		return activities;
	}
}
