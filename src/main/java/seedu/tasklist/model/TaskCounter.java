package seedu.tasklist.model;

import java.util.List;

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
		setCountersToList(src.getTaskList());
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
    	setCountersToList(tlce.data.getTaskList());
    	EventsCenter.getInstance().post(new TaskCountersChangedEvent(this));
    }
    
    private void setCountersToList(List<ReadOnlyTask> taskList){
    	for(ReadOnlyTask task: taskList){
    		incrementCounters(task);
    	}
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
