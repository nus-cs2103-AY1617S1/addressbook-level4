package seedu.address.logic.commands;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Startline;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Checks if tasks are overdue and repeats them if they are set to do so.
 * @author User
 *
 */
public class UpdateCommand extends Command {

	public static final String UPDATE_SUCCESS = "Tasks successfully updated! Tasks updated: %1$s";
	public static final String COMMAND_WORD = "update";
	
	private Task toAdd; 
	
	public UpdateCommand(){}
	
	
	
	
	
	@Override
	public CommandResult execute() {
		Calendar cal = Calendar.getInstance();
		ReadOnlyTaskManager taskmanager = model.getAddressBook();
		for(ReadOnlyTask task: taskmanager.getTaskList()){
			Calendar startlineCal = task.getStartline().calendar;
			Calendar deadlineCal = task.getDeadline().calendar;
			if(checkOverdue(cal, deadlineCal)){
				startlineCal = repeatDate(startlineCal, task);
				deadlineCal = repeatDate(deadlineCal, task);
				Name name = task.getName();
				String startline = mutateToDate(startlineCal);
				String deadline = mutateToDate(deadlineCal);
				Priority priority = task.getPriority();
				UniqueTagList tagSet = task.getTags();
				
				try {
		            model.deleteTask(task);
		        } catch (TaskNotFoundException pnfe) {
		            assert false : "The target task cannot be missing";
		        }
				
				try{
					toAdd = new Task(name, new Startline(startline), new Deadline(deadline), priority, tagSet);
				} catch (IllegalValueException ive) {}
				
				
		        assert model != null;
		        try {
		            model.addPerson(toAdd);
		        } catch (UniqueTaskList.DuplicateTaskException e) {
		        }
				
			}						
			

		}					
		return new CommandResult(UPDATE_SUCCESS);
	}
	
	private String mutateToDate(Calendar cal){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:MM");
		return sdf.format(cal.getTime());
	}
	
	private boolean checkOverdue(Calendar current, Calendar toCheck){
		if(toCheck.after(current)){
			return true;
		}
		else{
			return false;
		}
	}
	
	private Calendar repeatDate(Calendar toCheck, ReadOnlyTask task){
		// TODO: Overdue implementation
		if(task.getRepeating().getRepeating()){
			switch(task.getRepeating().getTimeInterval()){
				case "weekly":
					toCheck.add(Calendar.DATE, 7);
				case "monthly":
					toCheck.add(Calendar.MONTH, 1);
				case "yearly":
					toCheck.add(Calendar.YEAR, 1);
				default :
					;						
			}
		}
		return toCheck;
	}

}
