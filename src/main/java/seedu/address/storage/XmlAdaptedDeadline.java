package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deadline.Deadline;

import javax.xml.bind.annotation.XmlValue;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedDeadline {

    @XmlValue
    public String deadlineDate;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedDeadline() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedDeadline(Deadline source) {
        deadlineDate = source.deadlineDate;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Deadline toModelType() throws IllegalValueException {
        return new Deadline(deadlineDate);
    }

}