package seedu.address.model.task;

public class Status {
	private enum DoneStatus {
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
