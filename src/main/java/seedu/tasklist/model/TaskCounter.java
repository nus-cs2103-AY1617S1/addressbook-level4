package seedu.tasklist.model;

import com.google.common.eventbus.Subscribe;

import seedu.tasklist.commons.core.EventsCenter;
import seedu.tasklist.commons.events.model.TaskCountersChangedEvent;
import seedu.tasklist.commons.events.model.TaskListChangedEvent;
import seedu.tasklist.model.task.ReadOnlyTask;
//@@author A0146107M
/**
 * Counts the numbers of each type of task
 *
 */
public class TaskCounter {

	private int total;
	private int floating;
	private int overdue;
	private int tomorrow;
	private int today;
	private int other;
	private int upcoming;
	
	
	/**
	 * Constructor with ReadOnlyTaskList
	 * 
	 * @param src ReadOnlyTaskList containing initial data
	 */
	public TaskCounter(ReadOnlyTaskList src){
		reinitializeValues();
		for(ReadOnlyTask task: src.getTaskList()){
    		incrementCounters(task);
    	}
		EventsCenter.getInstance().registerHandler(this);
		EventsCenter.getInstance().post(new TaskCountersChangedEvent(this));
	}
	
	/**
	 * Handler for TaskListChangedEvent
	 * 
	 * @param tlce TaskListChangedEvent containing new list
	 */
    @Subscribe
    private void modelChangedEvent(TaskListChangedEvent tlce) {
    	reinitializeValues();
    	for(ReadOnlyTask task: tlce.data.getTaskList()){
    		incrementCounters(task);
    	}
    	EventsCenter.getInstance().post(new TaskCountersChangedEvent(this));
    }
    
    
	/**
	 * Increments counters based on given task
	 * 
	 * @param task Task to be counted
	 */
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
    
	/**
	 * Resets all counters
	 * 
	 */
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
