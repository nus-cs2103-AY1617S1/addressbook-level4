package seedu.todolist.model.task;

import java.util.Objects;

import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * Represents a Task in the to do list.
 * Guarantees: name is present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Interval interval;
    private Location location;
    private Remarks remarks;
    private Status status;
    private Notification notification;

    /**
     * Only Name field must be present and not null. Other fields can be null.
     */
    public Task(Name name, Interval interval, Location location, Remarks remarks, Status status) {
        assert name != null;
        this.name = name;
        this.interval = interval;
        this.location = location;
        this.remarks = remarks;
        this.status = status;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getInterval(), source.getLocation(), source.getRemarks(), source.getStatus());
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public Interval getInterval() {
        return interval;
    }

    @Override
    public Location getLocation() {
        return location;
    }
    
    @Override
    public Remarks getRemarks() {
    	return remarks;
    }
    
    @Override
    public Status getStatus() {
        return status;
    }
    
    @Override
    public Notification getNotification() {
    	return notification;
    }
    
    public void setNotification(int bufferTime) {
    	this.notification = new Notification(bufferTime);
    }
    
    public void sendNotification() {
    	String title = "name ah";
    	//name.toString();
        String message = "try lah";
        		//String.format("is happening %1$s", interval.toString());
        NotificationType notificationType = NotificationType.SUCCESS;
        
        TrayNotification tray = new TrayNotification(title, message, notificationType);
        tray.showAndWait();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, interval, location, remarks);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public int compareTo(ReadOnlyTask task) {
        return this.interval.compareTo(task.getInterval());
    }

}
