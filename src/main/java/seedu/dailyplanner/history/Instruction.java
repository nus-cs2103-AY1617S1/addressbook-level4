package seedu.dailyplanner.history;
import seedu.dailyplanner.model.task.*;
//@@author A0139102U
public class Instruction {

	private String reverseCommand;
	private ReadOnlyTask task;

	public Instruction(String cmd, ReadOnlyTask task) {
		this.reverseCommand = cmd;
		this.task = task;
	}

	public String getReverse() {
		return reverseCommand;
	}

	public void setReverse(String cmd) {
		this.reverseCommand = cmd;
	}
	
	public ReadOnlyTask getTask() {
		return this.task;
	}
}