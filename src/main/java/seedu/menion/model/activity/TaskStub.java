package seedu.menion.model.activity;

import java.util.ArrayList;

public class TaskStub {
	public String name;
	public String deadlineDate;
	public String deadlineTime;
	public String notes;
	
	public TaskStub(ArrayList<String> details)  {
		this.name = details.get(1);
		this.deadlineDate = details.get(2);
		this.deadlineTime = details.get(3);
		this.notes = details.get(4);
	}
}
