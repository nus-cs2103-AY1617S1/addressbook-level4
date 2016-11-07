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
		//set values to 0
		reinitializeValues();
		//count tasks
		setCountersToList(src.getTaskList());
		//listen for events
		EventsCenter.getInstance().registerHandler(this);
		//indicate taskcounter has changed
		EventsCenter.getInstance().post(new TaskCountersChangedEvent(this));
	}
	
	/**
	 * Handler for TaskListChangedEvent
	 * 
	 * @param tlce TaskListChangedEvent containing new list
	 */
    @Subscribe
    private void modelChangedEvent(TaskListChangedEvent tlce) {
    	//reset counters
    	reinitializeValues();
    	//re-count tasks
    	setCountersToList(tlce.data.getTaskList());
    	//indicate taskcounter has changed
    	EventsCenter.getInstance().post(new TaskCountersChangedEvent(this));
    }
    
	/**
	 * Increments counters based on given list of tasks
	 * 
	 * @param taskList List of tasks to be counted
	 */
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
    
    /**
	 * Get total number of tasks
	 * 
	 * @return total number of tasks
	 */
	public int getTotal() {
		return total;
	}

    /**
	 * Get number of floating tasks
	 * 
	 * @return number of floating tasks
	 */
	public int getFloating() {
		return floating;
	}

    /**
	 * Get number of overdue tasks
	 * 
	 * @return number of overdue tasks
	 */
	public int getOverdue() {
		return overdue;
	}

    /**
	 * Get number of tasks starting tomorrow
	 * 
	 * @return number of tasks starting tomorrow
	 */
	public int getTomorrow() {
		return tomorrow;
	}

    /**
	 * Get number of tasks starting today
	 * 
	 * @return number of tasks starting today
	 */
	public int getToday() {
		return today;
	}

    /**
	 * Get number of other tasks
	 * 
	 * @return number of other tasks
	 */
	public int getOther() {
		return other;
	}

    /**
	 * Get number of upcoming events
	 * 
	 * @return number of upcoming events
	 */
	public int getUpcoming() {
		return upcoming;
	}	
}
