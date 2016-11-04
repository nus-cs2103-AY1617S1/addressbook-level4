package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.item.RecurrenceRate;

//@@author A0093960X
public class XmlAdaptedRecurrenceRate {

    @XmlElement(required = true)
    private String rate;
    @XmlElement(required = true)
    private String timePeriod;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedRecurrenceRate() {
    }

    /**
     * Converts a given RecurrenceRate into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     *            XmlAdaptedRecurrenceRate
     */
    public XmlAdaptedRecurrenceRate(RecurrenceRate source) {
        assert source.getRate() != null && source.getTimePeriod() != null;

        rate = source.getRate().toString();
        timePeriod = source.getTimePeriod().toString();

    }

    /**
     * Converts this jaxb-friendly adapted RecurrenceRate object into the
     * model's RecurrenceRate object.
     *
     * @throws IllegalValueException if there were any data constraints violated
     *             in the adapted recurrence rate
     */
    public RecurrenceRate toModelType() throws IllegalValueException {
        return new RecurrenceRate(this.rate, this.timePeriod);
    }
}
