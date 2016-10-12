package seedu.address.model.task;

import seedu.address.model.task.Status.DoneStatus;

public class TaskType {
	
	public enum Type {
        EVENT {
            @Override
            public String toString() {
                return "Event";
            }
        }, 
        
        DEADLINE {
            @Override
            public String toString() {
                return "Deadline";
            }
        }, 
        
        SOMEDAY {
        	@Override
        	public String toString() {
        		return "Someday";
        	}
        }
    }
	
	public Type value;
	
	public TaskType(Type type) {
		value = type;
	}
	
	public TaskType(String type) throws IllegalArgumentException {
		switch (type.trim().toLowerCase()) {
		case "event":
			value = Type.EVENT;
		case "deadline":
			value = Type.DEADLINE;
		case "someday":
			value = Type.SOMEDAY;
		default:
			throw new IllegalArgumentException("Invalid string input");
		}
	}
	
	@Override
    public String toString() {
        return value.toString();
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskType // instanceof handles nulls
        	    && this.value.equals(((TaskType) other).value)); // state check
    }
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
