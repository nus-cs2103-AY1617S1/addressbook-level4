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
    
    private static PriorityQueue<ReadOnlyActivity> reminderQueue;
    
    {
        reminderQueue = new PriorityQueue<>(DEFAULT_SIZE, new Comparator<ReadOnlyActivity>() {
            public int compare(ReadOnlyActivity first, ReadOnlyActivity second) {
                return first.getReminder().compareTo(second.getReminder());
            }
        });
    }
    
    public UpcomingReminders() {}
    
    public UpcomingReminders(Collection<ReadOnlyActivity> activities) {
        for (ReadOnlyActivity activity : activities) {
            if (activity.getReminder().getCalendarValue() != null && !activity.hasReminderPassed()) {
                reminderQueue.add(activity);
            }
        }
    }
    
    public void initialize(Collection<Activity> activities) {
        for (ReadOnlyActivity activity : activities) {
            if (activity.getReminder().getCalendarValue() != null && !activity.hasReminderPassed()) {
                reminderQueue.add(activity);
            }
        }
    }
    
    /**
     * Adds an activity to the reminder queue. Activities without reminder or a passed reminder will not be added.
     * @return true if the activity is added to the queue.
     */
    public boolean addReminder(ReadOnlyActivity newActivity) {
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
    public boolean removeReminder(ReadOnlyActivity activity) {
        return reminderQueue.remove(activity);
    }
    
    /**
     * Returns a list of events that is upcoming.
     * @return a list of the events with start time closest to current time.
     */
    public static ArrayList<ReadOnlyActivity> popNextReminders() {
        ArrayList<ReadOnlyActivity> nextRemindedActivities = new ArrayList<>();
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, -10);
        Date offset10Secs = cal.getTime();
        
        while (!reminderQueue.isEmpty() && reminderQueue.peek().getReminder().getCalendarValue().getTime().after(offset10Secs)) {
            nextRemindedActivities.add(reminderQueue.poll());
        }
        
        return nextRemindedActivities;
    }
}
