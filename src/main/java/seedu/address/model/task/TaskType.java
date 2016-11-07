package seedu.address.model.task;

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
			break;
		case "deadline":
			value = Type.DEADLINE;
			break;
		case "someday":
			value = Type.SOMEDAY;
			break;
		default:
			throw new IllegalArgumentException("Invalid string input");
		}
	}
	
	public boolean isEventTask() {
		return value.equals(Type.EVENT);
	}
	
	public boolean isDeadlineTask() {
		return value.equals(Type.DEADLINE);
	}
	
	public boolean isSomedayTask() {
		return value.equals(Type.SOMEDAY);
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
