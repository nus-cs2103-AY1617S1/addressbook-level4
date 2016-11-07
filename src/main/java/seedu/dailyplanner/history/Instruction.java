package seedu.dailyplanner.history;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.text.html.HTML.Tag;

import seedu.dailyplanner.model.category.*;
import seedu.dailyplanner.model.task.*;

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