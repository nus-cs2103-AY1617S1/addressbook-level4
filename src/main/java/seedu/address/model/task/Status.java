package seedu.address.model.task;

public class Status {
	public enum DoneStatus {
		DONE, NOT_DONE
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
	
}
