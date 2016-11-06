package w15c2.tusk.testutil;

import w15c2.tusk.model.task.Description;
import w15c2.tusk.model.task.Task;

/**
 * A mutable task object. For testing only.
 */
public class TestTask extends Task {

	private Description description;
	private boolean pinned;
	private boolean completed;
	
	public TestTask(){
	}
	
    public TestTask(Description description) {
		super(description);
	}

    public TestTask(TestTask task2) {
		this.description = task2.getDescription();
		this.pinned = task2.isPinned();
		this.completed = task2.isCompleted();
	}

	public void setDescription(Description description) {
        this.description = description;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public boolean isPinned() {
        return pinned;
    }

    @Override
    public String toString() {
		return description.toString();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getDescription().getContent() + " ");
        sb.append("a/" + this.isPinned() + " ");
        sb.append("e/" + this.isCompleted() + " ");
        return sb.toString();
    }

	@Override
	public TestTask copy() {
		Description newDescription = new Description(this.description.getContent());
		TestTask newTask = new TestTask(newDescription);
		
		// Copy pin status
		if (this.isPinned()) {
			newTask.setAsPin();
		} else {
			newTask.setAsNotPin();
		}
		
		// Copy completed status
		if (this.isCompleted()) {
			newTask.setAsComplete();
		} else {
			newTask.setAsUncomplete();
		}
		return newTask;
	 }

	@Override
	public String getTaskDetails(boolean withTime) {
		return String.format("[Test Task][Description: %s]", description);
	}
	
	@Override
	public int compareTo(Task other) {
	    if(this.equals(other)){
	    	return 0;
	    }
	    else{
	    	return 1;
	    }
//		//checks if description are equal
//		if(this.getDescription().equals(other.getDescription())){
//			return -1;
//		}
//		//checks if both have the same pinned or unpinned status
//		else if (this.isPinned() && !other.isPinned()) {
//			return -1;
//		} else if (!this.isPinned() && other.isPinned()) {
//			return 1;
//		} 
//		//checks if both have the same completed or uncompleted status
//		else if (this.isCompleted() && !other.isCompleted()) {
//			return -1;
//		} else if (!this.isCompleted() && other.isCompleted()) {
//			return 1;
//		}
//		return 0;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == this){
    		return true;
    	}
    	if(!(obj instanceof TestTask)){
    		return false;
    	}
    	if(this.getDescription()==null){
    		return false;
    	}
		TestTask otherTask = (TestTask)obj;

		return (this.isPinned() == otherTask.isPinned()) 
				&& (this.isCompleted() == otherTask.isCompleted()) 
				&& (this.getDescription().toString().equals(otherTask.getDescription().toString()));
	}

}
