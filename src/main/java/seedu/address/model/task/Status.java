package seedu.address.model.task;

public class Status implements Comparable<Status> {
    public enum StatusType {
        DONE {
            @Override
            public String toString() {
                return "Done";
            }
        }, 
	
        PENDING {
            @Override
            public String toString() {
                return "Pending";
            }
        },
        
        OVERDUE {
        	@Override
        	public String toString() {
        		return "Overdue";
        	}
        }
    }
	
	public StatusType value;
	

	public Status(String string) {
		assert string != null;
		
		switch (string.trim().toLowerCase()) {
		case "done":
			value = StatusType.DONE;
			break;
		case "pending":
			value = StatusType.PENDING;
			break;
		case "overdue":
			value = StatusType.OVERDUE;
			break;
		default:
			throw new IllegalArgumentException("Invalid string input");
		}
	}
	
	public boolean isDone() {
		return value.equals(StatusType.DONE);
	}
	
	public boolean isNotDone() {
		return value.equals(StatusType.PENDING);
	}
	
	public boolean isOverdue() {
		return value.equals(StatusType.OVERDUE);
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

    //@@author A0141019U
    // overdue < not done < done
	@Override
	public int compareTo(Status other) {
		if (this.equals(other)) {
			return 0;
		}
		else if (this.isOverdue() || other.isDone()) {
			return -1;
		}
		else {
			return 1;
		}
	}
}
