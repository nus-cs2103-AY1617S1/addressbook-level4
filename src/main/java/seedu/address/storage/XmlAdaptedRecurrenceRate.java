package seedu.address.storage;

import java.text.ParseException;

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
    public XmlAdaptedRecurrenceRate() {}

    
    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedRecurrenceRate(RecurrenceRate source) {
        assert source.rate != null && source.timePeriod != null;
        this.rate = source.rate.toString().toLowerCase();
        this.timePeriod = source.timePeriod.toString().toLowerCase();
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     * @throws ParseException 
     */
    public RecurrenceRate toModelType() throws IllegalValueException, ParseException {
        return new RecurrenceRate(this.rate, this.timePeriod);
    }
}
