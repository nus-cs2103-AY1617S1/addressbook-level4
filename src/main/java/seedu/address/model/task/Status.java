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
	
	private DoneStatus status;
	
	public Status() {
		this.status = DoneStatus.NOT_DONE;
	}
	
	public DoneStatus getStatus() {
		return status;
	}
	
	public void setStatus(DoneStatus status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return status.toString();
	}
	
}
