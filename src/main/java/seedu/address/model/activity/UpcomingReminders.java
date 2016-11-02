package seedu.address.model.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;

import seedu.address.model.activity.Activity;

/**
 * Keeps a queue of upcoming events.
 * Events that are ongoing or over will not be added to the queue.
 */
public class UpcomingReminders {
    
    private static final int DEFAULT_SIZE = 50;
    
    private static PriorityQueue<Activity> reminderQueue;
    
    {
        reminderQueue = new PriorityQueue<>(DEFAULT_SIZE, new Comparator<Activity>() {
            public int compare(Activity first, Activity second) {
                return first.getReminder().compareTo(second.getReminder());
            }
        });
    }
    
    public UpcomingReminders() {}
    
    public UpcomingReminders(Collection<Activity> activities) {
        for (Activity activity : activities) {
            if (activity.getReminder().getCalendarValue() != null && !activity.hasReminderPassed()) {
                reminderQueue.add(activity);
            }
        }
    }
    
    public static void initialize(Collection<Activity> activities) {
        for (Activity activity : activities) {
            if (activity.getReminder().getCalendarValue() != null && !activity.hasReminderPassed()) {
                reminderQueue.add(activity);
            }
        }
    }
    
    /**
     * Adds an activity to the reminder queue. Activities without reminder or a passed reminder will not be added.
     * @return true if the activity is added to the queue.
     */
    public static boolean addReminder(Activity newActivity) {
        if (newActivity.getReminder().getCalendarValue() != null && !newActivity.hasReminderPassed()) {
            return reminderQueue.add(newActivity);
        } else {
            return false;
        }
    }
    
    /**
     * Removes the activity from the reminder queue, if it exists.
     * @return true if the reminder is successfully removed.
     */
    public static boolean removeReminder(Activity activity) {
        return reminderQueue.remove(activity);
    }
    
    /**
     * Returns a list of events that is upcoming.
     * @return a list of the events with start time closest to current time.
     */
    public static ArrayList<Activity> popNextReminders() {
        ArrayList<Activity> nextRemindedActivities = new ArrayList<>();
        
        while (reminderQueue.peek().hasReminderPassed()) {
            nextRemindedActivities.add(reminderQueue.poll());
        }
        
        return nextRemindedActivities;
    }
}
