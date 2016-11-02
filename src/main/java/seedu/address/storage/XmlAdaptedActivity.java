package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.Reminder;
import seedu.address.model.activity.task.*;
import seedu.address.model.activity.event.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import javax.xml.bind.annotation.XmlElement;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedActivity {

    @XmlElement(required = true)
    private String type;
    @XmlElement(required = true)
    private String name;
    @XmlElement
    private String line1;
    @XmlElement
    private String line2;
    @XmlElement
    private String reminder;
    @XmlElement
    private boolean completion;
    @XmlElement
    private boolean recurring;
    @XmlElement
    private String reminderRecurring;
    @XmlElement
    private String duedateRecurring;
    @XmlElement
    private String starttimeRecurring;
    @XmlElement
    private String endtimeRecurring;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedActivity() {
    }

    /**
     * Converts a given Activity into this class for JAXB use.
     *
     * @param source
     *            future changes to this will not affect the created
     *            XmlAdaptedActivity
     */
    public XmlAdaptedActivity(ReadOnlyActivity source) {

        String sourceType = source.getClass().getSimpleName().toLowerCase();

        name = source.getName().fullName;
        reminder = source.getReminder().toSave();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }

        if (source.getCompletionStatus()) {
            completion = true;
        } else {
            completion = false;
        }
        if (source.getReminder().value != null) {
            if (source.getReminder().recurring)
                recurring = true;
            else
                recurring = false;
        }

        if (source.getReminder().value != null)
            reminderRecurring = source.getReminder().RecurringMessage;
        switch (sourceType) {
        case "activity":
            type = "activity";
            line1 = null;
            line2 = null;
            break;

        case "task":
            type = "task";
            line1 = ((ReadOnlyTask) source).getDueDate().toSave();
            line2 = ((ReadOnlyTask) source).getPriority().value;
            break;
            //@@author A0131813R
        case "event":
            type = "event";
            line1 = ((ReadOnlyEvent) source).getStartTime().toSave();
            line2 = ((ReadOnlyEvent) source).getEndTime().toSave();
            if (((ReadOnlyEvent) source).getStartTime().value != null) {
                if (((ReadOnlyEvent) source).getStartTime().recurring)
                    recurring = true;
                else
                    recurring = false;
            }
            if (((ReadOnlyEvent) source).getStartTime() != null
                    && ((ReadOnlyEvent) source).getStartTime().RecurringMessage!=null)
                starttimeRecurring = ((ReadOnlyEvent) source).getStartTime().RecurringMessage;
            if (((ReadOnlyEvent) source).getEndTime() != null)
                    if(((ReadOnlyEvent) source).getEndTime().RecurringMessage!=null)
                endtimeRecurring = ((ReadOnlyEvent) source).getEndTime().RecurringMessage;
            break;

        }
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source
     *            future changes to this will not affect the created
     *            XmlAdaptedActivity
     */
    public XmlAdaptedActivity(ReadOnlyTask source) {
        type = "task";
        name = source.getName().fullName;
        line1 = source.getDueDate().toString();
        line2 = source.getPriority().value;
        reminder = source.getReminder().toString();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }

        if (source.getCompletionStatus()) {
            completion = true;
        } else {
            completion = false;
        }
    }
    //@@author 
    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source
     *            future changes to this will not affect the created
     *            XmlAdaptedActivity
     */
    public XmlAdaptedActivity(ReadOnlyEvent source) {
        type = "event";
        name = source.getName().fullName;
        line1 = source.getStartTime().toString();
        line2 = source.getEndTime().toString();
        reminder = source.getReminder().toString();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }

        if (source.getCompletionStatus()) {
            completion = true;
        } else {
            completion = false;
        }
        if (source.getReminder().value != null) {
            if (source.getReminder().recurring || (source).getStartTime().recurring)
                recurring = true;
            else
                recurring = false;
        }
        if (source.getReminder() != null && !source.getReminder().RecurringMessage.isEmpty())
            reminderRecurring = source.getReminder().RecurringMessage;
        if ((source).getStartTime().value != null) {
            if ((source).getStartTime().recurring)
                recurring = true;
            else
                recurring = false;
        }
        if (((ReadOnlyEvent) source).getStartTime() != null
                && ((ReadOnlyEvent) source).getStartTime().RecurringMessage!=null)
            starttimeRecurring = ((ReadOnlyEvent) source).getStartTime().RecurringMessage;
        if (((ReadOnlyEvent) source).getEndTime() != null)
                if(((ReadOnlyEvent) source).getEndTime().RecurringMessage!=null)
            endtimeRecurring = ((ReadOnlyEvent) source).getEndTime().RecurringMessage;
    }

    /**
     * Converts this jaxb-friendly adapted Activity object into the model's
     * Activity object.
     *
     * @throws IllegalValueException
     *             if there were any data constraints violated in the adapted
     *             person
     */
    //@@author A0131813R
    public Activity toModelType() throws IllegalValueException {
        final List<Tag> activityTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            activityTags.add(tag.toModelType());
        }

        final Name name = new Name(this.name);
        final Reminder reminder = new Reminder(this.reminder);
        reminder.recurring= this.recurring;
        reminder.RecurringMessage=this.reminderRecurring;
        final UniqueTagList tags = new UniqueTagList(activityTags);

        switch (this.type) {

        case "activity":
            Activity act= new Activity(name, reminder, tags);
            act.setCompletionStatus(this.completion);
            return act;

        case "task":
            final DueDate duedate = new DueDate(this.line1);
            final Priority priority = new Priority(this.line2);
            // How to use Image to set priority?

            Task task = new Task(name, duedate, priority, reminder, tags);
            task.setCompletionStatus(this.completion);
            return task;
            
        case "event":

            final StartTime start = new StartTime(this.line1);
            start.recurring=this.recurring;
            start.RecurringMessage= this.starttimeRecurring;
            final EndTime end = new EndTime(this.line2);
            end.recurring = this.recurring;
            end.RecurringMessage = this.endtimeRecurring;
            return new Event(name, start, end, reminder, tags);

        }
        return null;

    }
}
