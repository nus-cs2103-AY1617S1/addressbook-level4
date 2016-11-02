package seedu.todolist.model.task;

/**
 * Represents the notification set for a task
 */
public class Notification {
	private int bufferTime;
	
	public Notification(int bufferTime){
		this.bufferTime = bufferTime;
	}
	
	public int getBufferTime() {
		return bufferTime;
	}
	
	public void setBufferTime(int bufferTime) {
		this.bufferTime = bufferTime;
	}
}
