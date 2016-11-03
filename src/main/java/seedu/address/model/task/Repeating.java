package seedu.address.model.task;

//@@author A0139097U

public class Repeating {
	private boolean isRepeating;
	private String timeInterval;
	
	public Repeating(){
		this.isRepeating = false;
		this.timeInterval = null;
	}
	
	public Repeating(boolean isRepeating, String timeInterval){
		setRepeating(isRepeating);
		setTimeInterval(timeInterval);
	}
	
	// ========= Getters ===============
	// =================================
	
	public boolean getRepeating(){
		return this.isRepeating;
	}
	
	public String getTimeInterval(){
		return this.timeInterval;
	}
	
	// ========= Setters ===============
	// =================================
	
	public void setRepeating(boolean value){
		this.isRepeating = value;
	}
	
	public void setTimeInterval(String timeInterval){
		this.timeInterval = timeInterval;
	}		
}
