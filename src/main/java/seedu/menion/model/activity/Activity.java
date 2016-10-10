package seedu.menion.model.activity;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.commons.util.CollectionUtil;
import seedu.menion.model.tag.UniqueTagList;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a Person in the address book. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Activity implements ReadOnlyActivity {

    // Types of Activity
    public static final String FLOATING_TASK_TYPE = "Floating Task";
    public static final String TASK_TYPE = "Task";
    public static final String EVENT_TYPE = "Event";

    public static final Integer FLOATING_TASK_LENGTH = 3; // ActivityType,
                                                          // ActivityName, Note
    public static final Integer TASK_LENGTH = 5; // ActivityType, Note,
                                                 // ActivityName StartDate,
                                                 // StartTime
    public static final Integer EVENT_LENGTH = 7; // ActivityType, Note,
                                                  // ActivityName, StartDate,
                                                  // StartTime, EndDate, EndTime

    // Indexes of the parameters to retrieve from the constructor.
    public static final Integer INDEX_ACTIVITY_TYPE = 0;
    public static final Integer INDEX_ACTIVITY_NAME = 1;
    public static final Integer INDEX_ACTIVITY_NOTE = 2;
    public static final Integer INDEX_ACTIVITY_STARTDATE = 3;
    public static final Integer INDEX_ACTIVITY_STARTTIME = 4;
    public static final Integer INDEX_ACTIVITY_ENDDATE = 5;
    public static final Integer INDEX_ACTIVITY_ENDTIME = 6;

    private ActivityName name;
    private ActivityDate startDate;
    private ActivityTime startTime;
    private ActivityDate endDate;
    private ActivityTime endTime;
    private Note note;
    private String activityType;
    
    // Every Activity Object will have an array list of it's details for ease of accessibility
    private ArrayList<String> activityDetails;
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     * 
     * @throws IllegalValueException
     */
    public Activity(ArrayList<String> activityDetails) throws IllegalValueException {
        if (activityDetails.size() == FLOATING_TASK_LENGTH) {
            this.activityType = activityDetails.get(INDEX_ACTIVITY_TYPE);
            this.name = new ActivityName(activityDetails.get(INDEX_ACTIVITY_NAME));
            this.note = new Note(activityDetails.get(INDEX_ACTIVITY_NOTE));
        } else if (activityDetails.size() == TASK_LENGTH) {
            this.activityType = activityDetails.get(INDEX_ACTIVITY_TYPE);
            this.name = new ActivityName(activityDetails.get(INDEX_ACTIVITY_NAME));
            this.note = new Note(activityDetails.get(INDEX_ACTIVITY_NOTE));
            this.startDate = new ActivityDate(activityDetails.get(INDEX_ACTIVITY_STARTDATE));
            this.startTime = new ActivityTime(activityDetails.get(INDEX_ACTIVITY_STARTTIME));
        } else if (activityDetails.size() == EVENT_LENGTH) {
            this.activityType = activityDetails.get(INDEX_ACTIVITY_TYPE);
            this.name = new ActivityName(activityDetails.get(INDEX_ACTIVITY_NAME));
            this.note = new Note(activityDetails.get(INDEX_ACTIVITY_NOTE));
            this.startDate = new ActivityDate(activityDetails.get(INDEX_ACTIVITY_STARTDATE));
            this.startTime = new ActivityTime(activityDetails.get(INDEX_ACTIVITY_STARTTIME));
            this.endDate = new ActivityDate(activityDetails.get(INDEX_ACTIVITY_ENDDATE));
            this.endTime = new ActivityTime(activityDetails.get(INDEX_ACTIVITY_ENDTIME));
        }
        setActivityDetails();
    }

    /**
     * Copy constructor.
     * 
     * @throws IllegalValueException
     */
    public Activity(ReadOnlyActivity source) throws IllegalValueException {
        this(source.getActivityDetails());
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
    public UniqueTagList getTags() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setActivityDetails() {
        if (activityType == FLOATING_TASK_TYPE) {
            activityDetails = new ArrayList<String>(3);
            activityDetails.set(INDEX_ACTIVITY_TYPE, activityType);
            activityDetails.set(INDEX_ACTIVITY_NAME, name.toString());
            activityDetails.set(INDEX_ACTIVITY_NOTE, note.toString());
        }
        else if (activityType == TASK_TYPE) {
            activityDetails = new ArrayList<String>(5);
            activityDetails.set(INDEX_ACTIVITY_TYPE, activityType);
            activityDetails.set(INDEX_ACTIVITY_NAME, name.toString());
            activityDetails.set(INDEX_ACTIVITY_NOTE, note.toString());
            activityDetails.set(INDEX_ACTIVITY_STARTDATE, startDate.toString());
            activityDetails.set(INDEX_ACTIVITY_STARTTIME, startTime.toString());
        }
        else if (activityType == EVENT_TYPE) {
            activityDetails = new ArrayList<String>(7);
            activityDetails.set(INDEX_ACTIVITY_TYPE, activityType);
            activityDetails.set(INDEX_ACTIVITY_NAME, name.toString());
            activityDetails.set(INDEX_ACTIVITY_NOTE, note.toString());
            activityDetails.set(INDEX_ACTIVITY_STARTDATE, startDate.toString());
            activityDetails.set(INDEX_ACTIVITY_STARTTIME, startTime.toString());
            activityDetails.set(INDEX_ACTIVITY_ENDDATE, endDate.toString());
            activityDetails.set(INDEX_ACTIVITY_ENDTIME, endTime.toString());
        }
    }
    
    /**
     * returns the arrayList consisting of an activity's details.
     */
    @Override
    public ArrayList<String> getActivityDetails() {
        return activityDetails;
    }

}
