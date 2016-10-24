package seedu.address.testutil;

import java.util.ArrayList;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.commons.util.DateChecker;
import seedu.menion.logic.commands.CompleteCommand;
import seedu.menion.logic.commands.EditCommand;
import seedu.menion.logic.commands.UnCompleteCommand;
import seedu.menion.model.activity.*;

/**
 * A mutable Activity object. For testing only.
 */
public class TestActivity implements ReadOnlyActivity {

    private ActivityName name;
    private ActivityDate startDate;
    private ActivityTime startTime;
    private ActivityDate endDate;
    private ActivityTime endTime;
    private Note note;
    private String activityType;
    private Completed status;
    
    // Every Activity Object will have an array list of it's details for ease of
    // accessibility
    private ArrayList<String> activityDetails;

    /**
     * For floatingTask
     * Every field must be present and not null.
     * @return 
     */
    public TestActivity(String type, ActivityName name, Note note, Completed status) {
        this.activityType = type;
        this.name = name;
        this.note = note;
        this.status = status;
        setActivityDetails();
    }
    
    /**
     * For Task
     * Every field must be present and not null.
     */
    public TestActivity(String type, ActivityName name, Note note, ActivityDate startDate, ActivityTime startTime, Completed status) {
        this.activityType = type;
        this.name = name;
        this.note = note;
        this.startDate = startDate;
        this.startTime = startTime;
        this.status = status;
        setActivityDetails();
    }
    
    /**
     * For Event
     * Every field must be present and not null.
     */
    public TestActivity(String type, ActivityName name, Note note, ActivityDate startDate, ActivityTime startTime, ActivityDate endDate, ActivityTime endTime, Completed status) {
        this.activityType = type;
        this.name = name;
        this.note = note;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.status = status;
        setActivityDetails();
    }
    

    /**
     * Copy constructor.
     * 
     */
    public TestActivity (ReadOnlyActivity source) {
        
        if (source.getActivityType().equals(Activity.FLOATING_TASK_TYPE)) {
            activityType = source.getActivityType();
            name = source.getActivityName();
            note = source.getNote();
            status = source.getActivityStatus();
        } else if (source.getActivityType().equals(Activity.TASK_TYPE)) {
            activityType = source.getActivityType();;
            name = source.getActivityName();
            note = source.getNote();
            startDate = source.getActivityStartDate();
            startTime = source.getActivityStartTime();
            status = source.getActivityStatus();
        } else if (source.getActivityType().equals(Activity.EVENT_TYPE)) {
            activityType = source.getActivityType();;
            name = source.getActivityName();
            note = source.getNote();
            startDate = source.getActivityStartDate();
            startTime = source.getActivityStartTime();
            endDate = source.getActivityEndDate();
            endTime = source.getActivityEndTime();
            status = source.getActivityStatus();
        }
        this.activityDetails = source.getActivityDetails();

    }

    @Override
    public void setCompleted() {
        this.status = new Completed(true);
    }

    @Override
    public void setUncompleted() {
        this.status = new Completed(false);
    }
    
    @Override
    public Completed getActivityStatus() {
        return this.status;
    }
    @Override
    public ActivityName getActivityName() {
        return this.name;
    }

    @Override
    public Note getNote() {
        return this.note;
    }

    @Override
    public ActivityDate getActivityStartDate() {
        return this.startDate;
    }

    @Override
    public ActivityTime getActivityStartTime() {
        return this.startTime;
    }

    @Override
    public ActivityDate getActivityEndDate() {
        return this.endDate;
    }

    @Override
    public ActivityTime getActivityEndTime() {
        return this.endTime;
    }

    @Override
    public String getActivityType() {
        return this.activityType;
    }

    @Override
    public void setActivityDetails() {
        if (activityType == Activity.FLOATING_TASK_TYPE) {
            activityDetails = new ArrayList<String>(Activity.FLOATING_TASK_LENGTH);
            activityDetails.add(activityType);
            activityDetails.add(name.toString());
            activityDetails.add(note.toString());
            activityDetails.add(status.toString());
        } else if (activityType == Activity.TASK_TYPE) {
            activityDetails = new ArrayList<String>(Activity.TASK_LENGTH);
            activityDetails.add(activityType);
            activityDetails.add(name.toString());
            activityDetails.add(note.toString());
            activityDetails.add(startDate.toString());
            activityDetails.add(startTime.toString());
            activityDetails.add(status.toString());
        } else if (activityType == Activity.EVENT_TYPE) {
            activityDetails = new ArrayList<String>(Activity.EVENT_LENGTH);
            activityDetails.add(activityType);
            activityDetails.add(name.toString());
            activityDetails.add(note.toString());
            activityDetails.add(startDate.toString());
            activityDetails.add(startTime.toString());
            activityDetails.add(endDate.toString());
            activityDetails.add(endTime.toString());
            activityDetails.add(status.toString());
        }
    }

    /**
     * returns the arrayList consisting of an activity's details.
     */
    @Override
    public ArrayList<String> getActivityDetails() {
        return activityDetails;
    }


    public String getAddCommand() {
        StringBuilder build = new StringBuilder();
        
        if (activityType.equals(Activity.FLOATING_TASK_TYPE)) {
            build.append("add ");
            build.append(this.name.toString());
            build.append(" n:");
            build.append(this.getNote().toString());
        } else if (activityType.equals(Activity.TASK_TYPE)) {
            build.append("add ");
            build.append(this.name.toString());
            build.append(" by: ");
            build.append(this.getActivityStartDate().value);
            build.append(" ");
            build.append(this.getActivityStartTime().value);
            build.append(" n:");
            build.append(this.getNote().toString());
        } else if (activityType.equals(Activity.EVENT_TYPE)) {
            build.append("add ");
            build.append(this.name.toString());
            build.append(" by: ");
            build.append(this.getActivityStartDate().value);
            build.append(" ");
            build.append(this.getActivityStartTime().value);
            build.append(" to: ");
            build.append(this.getActivityEndDate().value);
            build.append(" ");
            build.append(this.getActivityEndTime().value);
            build.append(" n:");
            build.append(this.getNote().toString());
        }
        
        return build.toString();
    }
    
    //@@author A0139164A
    /**
     * @param index of the Activity to complete
     * @return complete command for the given Activity.
     */
    public String getCompleteCommand(int index) {
        
        StringBuilder build = new StringBuilder();
        
        if (activityType.equals(Activity.FLOATING_TASK_TYPE)) {
            build.append(CompleteCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.FLOATING_TASK_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
        } else if (activityType.equals(Activity.TASK_TYPE)) {
            build.append(CompleteCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.TASK_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
        } else if (activityType.equals(Activity.EVENT_TYPE)) {
            build.append(CompleteCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.EVENT_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
        }
        
        return build.toString();
    }
    
    /**
     * @param index of the Activity to complete
     * @return complete command for the given Activity.
     */
    public String getUncompleteCommand(int index) {
        
        StringBuilder build = new StringBuilder();
        
        if (activityType.equals(Activity.FLOATING_TASK_TYPE)) {
            build.append(UnCompleteCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.FLOATING_TASK_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
        } else if (activityType.equals(Activity.TASK_TYPE)) {
            build.append(UnCompleteCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.TASK_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
        } else if (activityType.equals(Activity.EVENT_TYPE)) {
            build.append(UnCompleteCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.EVENT_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
        }
        
        return build.toString();
    }
    
    /**
     * @param index of the Activity to edit, and it's newName
     * @return edit name command for the given Activity.
     */
    public String getEditNameCommand(int index, String newName) {
        StringBuilder build = new StringBuilder();
        
        if (activityType.equals(Activity.FLOATING_TASK_TYPE)) {
            build.append(EditCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.FLOATING_TASK_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
            build.append(" ");
            build.append(EditCommand.NAME_PARAM);
            build.append(" ");
            build.append(newName);
        } else if (activityType.equals(Activity.TASK_TYPE)) {
            build.append(EditCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.TASK_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
            build.append(" ");
            build.append(EditCommand.NAME_PARAM);
            build.append(" ");
            build.append(newName);
        } else if (activityType.equals(Activity.EVENT_TYPE)) {
            build.append(EditCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.EVENT_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
            build.append(" ");
            build.append(EditCommand.NAME_PARAM);
            build.append(" ");
            build.append(newName);
        }
        return build.toString();
    }
    
    @Override
    public Activity get() {
        return null;
    }

    /**
     * @Author Marx Low A0139164A
     * List of methods to set Activity's param : Name, Note, startDate, startTime
     * Exception handling to be editted ----------> ALERT! (Assumes User to pass in correct parameters)
     */
    @Override
    public void setActivityName(String newName) {
        assert (newName != null);
        try {
            this.name = new ActivityName(newName);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setActivityNote(String newNote) {
        assert (newNote != null);
        try {
            this.note = new Note(newNote);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setActivityStartDateTime(String newDate, String newTime) throws IllegalValueException {
        boolean isTask = this.activityType.equals(Activity.TASK_TYPE);
        boolean isEvent = this.activityType.equals(Activity.EVENT_TYPE);
        assert (isTask || isEvent);
        
        ActivityDate newDateObject = new ActivityDate(newDate);
        ActivityTime newTimeObject = new ActivityTime(newTime);
        if (isEvent) {
            DateChecker check = new DateChecker();
            check.validEventDate(newDateObject, newTimeObject, this.endDate, this.endTime);
        }
        this.startDate = newDateObject;
        this.startTime = newTimeObject;

    }

    @Override
    public void setActivityEndDateTime(String newDate, String newTime) throws IllegalValueException {
        boolean isEvent = this.activityType.equals(Activity.EVENT_TYPE);
        DateChecker check = new DateChecker();
        assert (isEvent);
        ActivityDate newDateObject = new ActivityDate(newDate);
        ActivityTime newTimeObject = new ActivityTime(newTime);
        check.validEventDate(this.startDate, this.startTime, newDateObject, newTimeObject);
        this.endDate = newDateObject;
        this.endTime = newTimeObject;
    }
  
    @Override
    public String toString() {
        switch(this.activityType){
        case Activity.FLOATING_TASK_TYPE:
            return getFloatingTaskAsText();
        case Activity.TASK_TYPE:
            return getTaskAsText();
        case Activity.EVENT_TYPE:
            return getEventAsText();
        }
        return null;
    }

}