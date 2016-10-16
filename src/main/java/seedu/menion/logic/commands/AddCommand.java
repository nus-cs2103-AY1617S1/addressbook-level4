package seedu.menion.logic.commands;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.activity.*;
import seedu.menion.model.activity.UniqueActivityList.TaskNotFoundException;

import java.util.ArrayList;


/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = "Adding a Floating Task: "+ COMMAND_WORD + " buy lunch n: hawker food\n"
            + "Adding a Task: "+ COMMAND_WORD + " complete cs2103t by: 10-08-2016 1900 n: must complete urgent\n"
    		+ "Adding a Event: "+ COMMAND_WORD + " project meeting from: 10-10-2016 1400 to: 10-10-2016 1800 n: celebrate\n";

    public static final String MESSAGE_SUCCESS = "New activity added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This activity already exists in the Menion";

    private final Activity toAdd;
    public final EventStub eventStub = null;

    private ActivityName name;
    private ActivityDate startDate;
    private ActivityTime startTime;
    private ActivityDate endDate;
    private ActivityTime endTime;
    private Note note;
    private String activityType;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(ArrayList<String> activityDetails) throws IllegalValueException {
    	
        if (activityDetails.size() == Activity.FLOATING_TASK_LENGTH) {
            activityType = activityDetails.get(Activity.INDEX_ACTIVITY_TYPE);
            name = new ActivityName(activityDetails.get(Activity.INDEX_ACTIVITY_NAME));
            note = new Note(activityDetails.get(Activity.INDEX_ACTIVITY_NOTE));
            this.toAdd = new Activity(activityType, name, note);
        } else if (activityDetails.size() == Activity.TASK_LENGTH) {
            activityType = activityDetails.get(Activity.INDEX_ACTIVITY_TYPE);
            name = new ActivityName(activityDetails.get(Activity.INDEX_ACTIVITY_NAME));
            note = new Note(activityDetails.get(Activity.INDEX_ACTIVITY_NOTE));
            startDate = new ActivityDate(activityDetails.get(Activity.INDEX_ACTIVITY_STARTDATE));
            startTime = new ActivityTime(activityDetails.get(Activity.INDEX_ACTIVITY_STARTTIME));
            this.toAdd = new Activity(activityType, name, note, startDate, startTime);
        } else {
            activityType = activityDetails.get(Activity.INDEX_ACTIVITY_TYPE);
            name = new ActivityName(activityDetails.get(Activity.INDEX_ACTIVITY_NAME));
            note = new Note(activityDetails.get(Activity.INDEX_ACTIVITY_NOTE));
            startDate = new ActivityDate(activityDetails.get(Activity.INDEX_ACTIVITY_STARTDATE));
            startTime = new ActivityTime(activityDetails.get(Activity.INDEX_ACTIVITY_STARTTIME));
            endDate = new ActivityDate(activityDetails.get(Activity.INDEX_ACTIVITY_ENDDATE));
            endTime = new ActivityTime(activityDetails.get(Activity.INDEX_ACTIVITY_ENDTIME));
            this.toAdd = new Activity(activityType, name, note, startDate, startTime, endDate, endTime);
        }
      //  this.eventStub = new EventStub(activityDetails);
    }

    @Override
    public CommandResult execute() {
    	assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueActivityList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }

    /*
     * undo delete the added activity previously
     */
	@Override
	public boolean undo() {
		assert model != null;
		 try {
	            model.deleteTask(toAdd);
	            return true;
	     } 
		 catch (TaskNotFoundException pnfe) {
	            // there will not be a task not found exception here
	        	return false;
	     }
	}
}
