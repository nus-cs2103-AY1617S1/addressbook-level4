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
	private Date start;
	private Date end;
	private boolean isComplete;
	private boolean isPinned;
	private UniqueTagList tags;

	public Instruction(String cmd, String name, Date start, Date end, boolean complete,
			boolean pin, UniqueTagList tags) {
		this.reverseCommand = cmd;
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
	public Date getTaskStart() {
		return start;
	}
	public void setTaskStart(Date start) {
		this.start = start;
	}
	public Date getTaskEnd() {
		return end;
	}
	public void setTaskEnd(Date end) {
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