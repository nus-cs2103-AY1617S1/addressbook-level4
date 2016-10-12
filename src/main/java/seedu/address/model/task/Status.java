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
        }
    }
	
	public DoneStatus value;
	
	public Status(DoneStatus status) {
		value = status;
	}

	
	public Status(String string) {
		switch (string.trim().toLowerCase()) {
		case "done":
			value = DoneStatus.DONE;
		case "not done":
			value = DoneStatus.NOT_DONE;
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
                || (other instanceof Status // instanceof handles nulls
        	    && this.value.equals(((Status) other).value)); // state check
    }
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
