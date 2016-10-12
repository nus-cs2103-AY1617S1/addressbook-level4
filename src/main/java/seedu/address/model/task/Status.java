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
	
	public DoneStatus status;
	
	public Status() {
		this.status = DoneStatus.NOT_DONE;
	}
	
	
	@Override
	public String toString() {
		return status.toString();
	}
	
	@Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
			|| (other instanceof Status // instanceof handles nulls
			&& this.status.equals(((Status) other).status)); // state check
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }
}
