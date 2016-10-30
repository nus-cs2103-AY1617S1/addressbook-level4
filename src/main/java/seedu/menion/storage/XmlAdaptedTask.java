package seedu.menion.storage;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.activity.*;


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
    @XmlElement(required = true)
    private String endDate;
    @XmlElement(required = true)
    private String endTime;
    @XmlElement(required = true)
    private String status;
    @XmlElement(required = true)
    private String activityTimePassed;
    @XmlElement(required = true)
    private String emailSent;


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
            activityType = source.getActivityType().toString();
            name = source.getActivityName().fullName;
            note = source.getNote().toString();
            startDate = source.getActivityStartDate().toString();
            startTime = source.getActivityStartTime().toString();
            status = source.getActivityStatus().toString();
            activityTimePassed = source.isTimePassed().toString();
            emailSent = source.isEmailSent().toString();
    }

    /**
     * Converts this jaxb-friendly adapted Activity object into the model's Activity object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Activity
     */
    public Activity toModelType() throws IllegalValueException {
            final String type = this.activityType;
            final ActivityName name = new ActivityName(this.name);
            final Note note = new Note(this.note);
            final ActivityDate startDate = new ActivityDate(this.startDate);
            final ActivityTime startTime = new ActivityTime(this.startTime);
            final Completed status = new Completed(this.status);
            final Boolean emailSent = Boolean.parseBoolean(this.emailSent);
            final Boolean activityTimePassed = Boolean.parseBoolean(this.activityTimePassed);
            return new Activity(type, name, note, startDate, startTime, status, activityTimePassed, emailSent);
    }
}
