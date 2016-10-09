package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.ActivityManager;
import seedu.address.model.activity.Activity;

/**
 * A utility class to help with building ActivityManager objects.
 * Example usage: <br>
 *     {@code ActivityManager ab = new ActivityManagerBuilder().withActivity(new Activity("buy milk")).withTag("Friend").build();}
 */
public class ActivityManagerBuilder {

    private ActivityManager activityManager;

    public ActivityManagerBuilder(ActivityManager activityManager){
        this.activityManager = activityManager;
    }

    public ActivityManagerBuilder withActivity(Activity activity) {
        activityManager.addActivity(activity);
        return this;
    }

    public ActivityManagerBuilder withTag(String tagName) throws IllegalValueException {
        activityManager.addTag(new Tag(tagName));
        return this;
    }

    public ActivityManager build(){
        return activityManager;
    }
}
