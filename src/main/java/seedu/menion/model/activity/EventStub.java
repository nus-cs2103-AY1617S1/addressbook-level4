package seedu.menion.model.activity;

import java.util.ArrayList;

public class EventStub {
	public String name;
	public String startDate;
	public String startTime;
	public String endDate;
	public String endTime;
	public String notes;
	
	public EventStub(ArrayList<String> details)  {
		this.name = details.get(1);
		this.startDate = details.get(2);
		this.startTime = details.get(3);
		this.endDate = details.get(4);
		this.endTime = details.get(5);
		this.notes = details.get(6);
	}
}
