package seedu.menion.storage;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.activity.*;
import seedu.menion.model.tag.Tag;
import seedu.menion.model.tag.UniqueTagList;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Activity.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String activityType;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String note;
    @XmlElement(required = false)
    private String startDate;
    @XmlElement(required = false)
    private String startTime;
    @XmlElement(required = false)
    private String endDate;
    @XmlElement(required = false)
    private String endTime;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Activity into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyActivity source) {
        
        if (source.getActivityType() == Activity.FLOATING_TASK_TYPE) {
            activityType = source.getActivityType().toString();
            name = source.getActivityName().fullName;
            note = source.getNote().toString();
        } else if (source.getActivityType() == Activity.TASK_TYPE) {
            activityType = source.getActivityType().toString();
            name = source.getActivityName().fullName;
            note = source.getNote().toString();
            startDate = source.getActivityStartDate().toString();
            startTime = source.getActivityStartTime().toString();
        } else if (source.getActivityType() == Activity.EVENT_TYPE) {
            activityType = source.getActivityType().toString();
            name = source.getActivityName().fullName;
            note = source.getNote().toString();
            startDate = source.getActivityStartDate().toString();
            startTime = source.getActivityStartTime().toString();
            endDate = source.getActivityEndDate().toString();
            endTime = source.getActivityEndTime().toString();
        }
    }

    /**
     * Converts this jaxb-friendly adapted Activity object into the model's Activity object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Activity
     */
    public Activity toModelType() throws IllegalValueException {
        if (this.activityType == Activity.FLOATING_TASK_TYPE) {
            final String type = this.activityType;
            final ActivityName name = new ActivityName(this.name);
            final Note note = new Note(this.note);
            return new Activity(type, name, note);
        } else if (this.activityType == Activity.TASK_TYPE) {
            final String type = this.activityType;
            final ActivityName name = new ActivityName(this.name);
            final Note note = new Note(this.note);
            final ActivityDate startDate = new ActivityDate(this.startDate);
            final ActivityTime startTime = new ActivityTime(this.startTime);
            return new Activity(type, name, note, startDate, startTime);
        } else {
            final String type = this.activityType;
            final ActivityName name = new ActivityName(this.name);
            final Note note = new Note(this.note);
            final ActivityDate startDate = new ActivityDate(this.startDate);
            final ActivityTime startTime = new ActivityTime(this.startTime);
            final ActivityDate endDate = new ActivityDate(this.endDate);
            final ActivityTime endTime = new ActivityTime(this.endTime);
            return new Activity(type, name, note, startDate, startTime, endDate, endTime);
        }
    }
}
