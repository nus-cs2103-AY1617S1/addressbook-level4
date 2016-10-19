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
    private List<DateTime> reservedDateTime;

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
        reservedDateTime = new ArrayList<>();
        for (DateTime dt : source.getDateTimeList()) {
            reservedDateTime.add(dt);
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

        ArrayList<DateTime> dateTimeList = new ArrayList<DateTime>();
        for (DateTime dt : this.reservedDateTime) {
            dateTimeList.add(new DateTime(dt.startDateString, dt.endDateString));
        }
        
        final Name name = new Name(this.name);

        return new RsvTask(name, dateTimeList);
    }

}

