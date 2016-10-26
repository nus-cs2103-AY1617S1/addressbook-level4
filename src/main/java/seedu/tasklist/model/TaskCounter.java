package seedu.tasklist.model;

import com.google.common.eventbus.Subscribe;

import seedu.tasklist.commons.core.EventsCenter;
import seedu.tasklist.commons.events.model.TaskCountersChangedEvent;
import seedu.tasklist.commons.events.model.TaskListChangedEvent;
import seedu.tasklist.model.task.ReadOnlyTask;
//@@author A0146107M
public class TaskCounter {

	private int total;
	private int floating;
	private int overdue;
	private int tomorrow;
	private int today;
	private int other;
	private int upcoming;
	
	public TaskCounter(ReadOnlyTaskList src){
		reinitializeValues();
		for(ReadOnlyTask task: src.getTaskList()){
    		incrementCounters(task);
    	}
		EventsCenter.getInstance().registerHandler(this);
		EventsCenter.getInstance().post(new TaskCountersChangedEvent(this));
	}
	
    @Subscribe
    private void modelChangedEvent(TaskListChangedEvent abce) {
    	reinitializeValues();
    	for(ReadOnlyTask task: abce.data.getTaskList()){
    		incrementCounters(task);
    	}
    	EventsCenter.getInstance().post(new TaskCountersChangedEvent(this));
    }
    
    private void incrementCounters(ReadOnlyTask task){
    	total++;
    	if(task.isFloating()){
    		floating++;
    	}
    	else if(task.isEvent() && !task.isOverDue()){
    		upcoming++;
    	}
    	else {
    		other++;
    	}
    	
    	if(task.isOverDue()){
    		overdue++;
    	}
    	else if(task.isTomorrow()){
    		tomorrow++;
    	}
    	else if(task.isToday()){
    		today++;
    	}
    }
    
    private void reinitializeValues(){
    	 this.total = 0;
    	 this.floating = 0;
    	 this.overdue = 0;
    	 this.tomorrow = 0;
    	 this.today = 0;
    	 this.other = 0;
    	 this.upcoming = 0;
    }
    
	public int getTotal() {
		return total;
	}

	public int getFloating() {
		return floating;
	}

	public int getOverdue() {
		return overdue;
	}

	public int getTomorrow() {
		return tomorrow;
	}

	public int getToday() {
		return today;
	}

	public int getOther() {
		return other;
	}

	public int getUpcoming() {
		return upcoming;
	}	
}
