package seedu.dailyplanner.history;

import java.util.HashSet;
import java.util.Set;

import javax.swing.text.html.HTML.Tag;
import seedu.dailyplanner.model.tag.*;
import seedu.dailyplanner.model.tag.UniqueTagList;
import seedu.dailyplanner.model.task.*;
public class Instruction {
	
	private String reverse;
	
	private int taskIndex;
	private String taskName;
	private String taskDate;
	private String taskStart;
	private String taskEnd;
	private UniqueTagList tag;
	
	/*public Instruction(String reverse, int taskIndex, String taskName, String taskDate, String taskStart,
			String taskEnd, UniqueTagList tag) {
		super();
		this.reverse = reverse;
		this.taskIndex = taskIndex;
		this.taskName = taskName;
		this.taskDate = taskDate;
		this.taskStart = taskStart;
		this.taskEnd = taskEnd;
		this.tag = tag;
	}*/
	
	public Instruction(String reverse, String taskName, String taskDate, String taskStart,
			String taskEnd, UniqueTagList pushTag) {
		
		this.reverse = reverse;
		//this.taskIndex = taskIndex;
		this.taskName = taskName;
		this.taskDate = taskDate;
		this.taskStart = taskStart;
		this.taskEnd = taskEnd;
		this.tag = pushTag;
	}
	
	public String getReverse() {
		return reverse;
	}
	public void setReverse(String reverse) {
		this.reverse = reverse;
	}
	
	public int getTaskIndex() {
		return taskIndex;
	}
	public void setTaskIndex(int taskIndex) {
		this.taskIndex = taskIndex;
	}
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getTaskDate() {
		return taskDate;
	}
	public void setTaskDate(String taskDate) {
		this.taskDate = taskDate;
	}
	
	public String getTaskStart() {
		return taskStart;
	}
	public void setTaskStart(String taskStart) {
		this.taskStart = taskStart;
	}
	
	public String getTaskEnd() {
		return taskEnd;
	}
	public void setTaskEnd(String taskEnd) {
		this.taskEnd = taskEnd;
	}
	
	public UniqueTagList getTag() {
		return tag;
	}
	public void setTag(UniqueTagList tag) {
		this.tag = tag;
	}
	
	
}