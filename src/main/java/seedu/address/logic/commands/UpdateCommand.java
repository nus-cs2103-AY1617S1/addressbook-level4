package seedu.address.logic.commands;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Repeating;
import seedu.address.model.task.Startline;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0139097U
/**
 * Checks if tasks are overdue and repeats them if they are set to do so.
 */
public class UpdateCommand extends Command {

	public static final String UPDATE_SUCCESS = "Tasks successfully updated!";
	public static final String COMMAND_WORD = "update";
	
	private Task toAdd;
	String startline, deadline;
	
	public UpdateCommand(){}
	
	@Override
	public CommandResult execute() {
		boolean overdue = false;
		Calendar cal = Calendar.getInstance();
		List<Task> addList = new LinkedList();
		List<Task> deleteList = new LinkedList();
		ReadOnlyTaskManager taskmanager = model.getTaskManager();
		Iterator<Task> it = taskmanager.getUniqueTaskList().iterator(); 
		while(it.hasNext()){
			Task task = it.next();
			Calendar startlineCal = task.getStartline().calendar;
			Calendar deadlineCal = task.getDeadline().calendar;
			startline = task.getStartline().value;
			deadline = task.getDeadline().value;
			if(checkOverdue(cal, deadlineCal)){
				if(task.getRepeating().getRepeating()) {
					if(startlineCal != null){
						startlineCal = repeatDate(startlineCal, task);
						startline = mutateToDate(startlineCal);
					}
					else{
						startline = null;
					}
					if(deadlineCal != null){
						deadlineCal = repeatDate(deadlineCal, task);
						deadline = mutateToDate(deadlineCal);
						overdue = false;
					}
					else{
						deadline = null;
					}
				} else if((deadlineCal != null) && (!task.getName().toString().contains(" is completed"))) {
					overdue = true;
				} else {
					overdue = false;
				}
				
			} else {
				overdue = false;
			}
			Name name = task.getName();								
			Priority priority = task.getPriority();
			UniqueTagList tagSet = task.getTags();
			try{
				toAdd = new Task(name, new Startline(startline), new Deadline(deadline), priority, tagSet);
				toAdd.setRepeating(new Repeating(task.getRepeating().getRepeating(), task.getRepeating().getTimeInterval()));
			} catch (IllegalValueException ive) {
				return new CommandResult("FAILED " + ive.getMessage());
			}
			if(overdue) {
				toAdd.setOverdue(true);
			}
			addList.add(toAdd);
			deleteList.add(task);								
		}
		
		for(Task t: deleteList){
			try {
	            model.deleteTask(t);
	        } catch (TaskNotFoundException pnfe) {
	            assert false : "The target task cannot be missing";
	        }
		}
		
		for(Task modified: addList) {
			assert model != null;
	        try {
	            model.addTask(modified);
	        } catch (UniqueTaskList.DuplicateTaskException e) {
	        }
		}
		return new CommandResult(UPDATE_SUCCESS);
	}
	
	private String mutateToDate(Calendar cal){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm");
		return sdf.format(cal.getTime());
	}
	
	private boolean checkOverdue(Calendar current, Calendar toCheck){
		if(toCheck == null){
			return false;
		}
		if(current.after(toCheck)){
			return true;
		}
		else{
			return false;
		}
	}
	
	private Calendar repeatDate(Calendar toCheck, ReadOnlyTask task){
		if(task.getRepeating().getRepeating()){
			switch(task.getRepeating().getTimeInterval()){
				case "weekly":
					toCheck.add(Calendar.DATE, 7);
					break;
				case "monthly":
					toCheck.add(Calendar.MONTH, 1);
					break;
				case "yearly":
					toCheck.add(Calendar.YEAR, 1);
					break;
				default :
					break;						
			}
		}
		return toCheck;
	}

}
