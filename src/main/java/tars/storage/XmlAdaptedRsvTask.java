package tars.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import tars.commons.exceptions.IllegalValueException;
import tars.model.task.DateTime;
import tars.model.task.Name;
import tars.model.task.Priority;
import tars.model.task.rsv.RsvTask;

/**
 * JAXB-friendly version of the RsvTask.
 */
public class XmlAdaptedRsvTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement
    private String priority;

    @XmlElement
    private List<DateTime> reservedDateTime;

    @XmlElement
    private List<String> tempTags;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedRsvTask() {
    }

    /**
     * Converts a given Reserved Task into this class for JAXB use.
     *
     * @param source
     *            future changes to this will not affect the created
     *            XmlAdaptedRsvTask
     */
    public XmlAdaptedRsvTask(RsvTask source) {
        name = source.getName().taskName;
        priority = source.getPriority().priorityLevel;
        reservedDateTime = new ArrayList<>();
        for (DateTime dt : source.getDateTimeSet()) {
            reservedDateTime.add(dt);
        }
        tempTags = new ArrayList<>();
        for (String tag : source.getTempTagSet()) {
            tempTags.add(tag);
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's RsvTask
     * object.
     *
     * @throws IllegalValueException
     *             if there were any data constraints violated in the adapted
     *             rsvTask
     */
    public RsvTask toModelType() throws IllegalValueException {

        Set<DateTime> dateTimeSet = new HashSet<>();
        for (DateTime dt : this.reservedDateTime) {
            dateTimeSet.add(new DateTime(dt.startDateString, dt.endDateString));
        }

        Set<String> tempTagSet = new HashSet<>();
        if (tempTags != null) {
            for (String tag : this.tempTags) {
                tempTagSet.add(tag);
            }
        }

        final Name name = new Name(this.name);
        final Priority priority = new Priority(this.priority);

        return new RsvTask(name, dateTimeSet, priority, tempTagSet);
    }

}