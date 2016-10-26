package seedu.address.model.task;

public class Status {
    public enum DoneStatus {
        DONE {
            @Override
            public String toString() {
                return "Done";
            }
        }, 
	
        NOT_DONE {
            @Override
            public String toString() {
                return "Not done";
            }
        },
        
        OVERDUE {
        	@Override
        	public String toString() {
        		return "Overdue";
        	}
        }
    }
	
	public DoneStatus value;
	
	
	public Status(String string) {
		switch (string.trim().toLowerCase()) {
		case "done":
			value = DoneStatus.DONE;
			break;
		case "not done":
			value = DoneStatus.NOT_DONE;
			break;
		case "overdue":
			value = DoneStatus.OVERDUE;
			break;
		default:
			throw new IllegalArgumentException("Invalid string input");
		}
	}
	
	public boolean isDone() {
		return value.equals(DoneStatus.DONE);
	}
	
	public boolean isNotDone() {
		return value.equals(DoneStatus.NOT_DONE);
	}
	
	public boolean isOverdue() {
		return value.equals(DoneStatus.OVERDUE);
	}
	
    @Override
    public String toString() {
        return value.toString();
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
        	    && this.value.equals(((Status) other).value)); // state check
    }
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
