package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.DateTimeParser;
import seedu.address.model.item.*;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedItem {

    @XmlElement(required = true)
    private String description;
    
    @XmlElement(required = false)
    private String startDate;
    
    @XmlElement(required = false)
    private String endDate;
    
    
    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedItem() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedItem(ReadOnlyItem source) {
        description = source.getDescription().getFullDescription();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
          
        if (source.getStartDate() == null) {
        	startDate = "";
        } else {
        	startDate = source.getStartDate().format(formatter);
        }
        
        if (source.getEndDate() == null) {
        	endDate = "";
        } else {
        	endDate = source.getEndDate().format(formatter);
        }
    }

    /**
     * Converts this jaxb-friendly adapted item object into the model's Item object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Item toModelType() throws IllegalValueException {
        final Description description = new Description(this.description);
        LocalDateTime start;
        LocalDateTime end;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (this.startDate.equals("")) {
        	start = null;
        } else {
        	start = LocalDateTime.parse(startDate, formatter);
        }
        if (this.endDate.equals("")) {
        	end = null;
        } else {
        	end = LocalDateTime.parse(endDate, formatter);
        }
        return new Item(description, start, end);
    }
}
