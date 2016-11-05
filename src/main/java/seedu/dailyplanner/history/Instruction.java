package seedu.dailyplanner.history;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.text.html.HTML.Tag;
import seedu.dailyplanner.model.tag.*;
import seedu.dailyplanner.model.tag.UniqueTagList;
import seedu.dailyplanner.model.task.*;

public class Instruction {

	private String reverseCommand;
	private String name;
	private DateTime start;
	private DateTime end;
	private boolean isComplete;
	private boolean isPinned;
	private UniqueTagList tags;

	public Instruction(String cmd, String name, DateTime start, DateTime end, boolean complete, boolean pin,
			UniqueTagList tags) {
		this.reverseCommand = cmd;
		this.name = name;
		this.start = start;
		this.end = end;
		this.isComplete = complete;
		this.isPinned = pin;
		this.tags = tags;
	}

	public String getReverse() {
		return reverseCommand;
	}

	public void setReverse(String cmd) {
		this.reverseCommand = cmd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DateTime getTaskStart() {
		return start;
	}

	public void setTaskStart(DateTime start) {
		this.start = start;
	}

	public DateTime getTaskEnd() {
		return end;
	}

	public void setTaskEnd(DateTime end) {
		this.end = end;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean complete) {
		this.isComplete = complete;
	}

	public boolean isPinned() {
		return isPinned;
	}

	public void setPinned(boolean pin) {
		this.isPinned = pin;
	}

	public UniqueTagList getTag() {
		return tags;
	}

	public void setTag(UniqueTagList tags) {
		this.tags = tags;
	}
}