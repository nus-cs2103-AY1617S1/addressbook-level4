package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Task;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.RecurrenceRate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String priorityValue;
    @XmlElement
    private String startDate;
    @XmlElement
    private String endDate;
    @XmlElement(required = true)
    private String recurrenceRate;


    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().name;
        priorityValue = source.getPriorityValue().toString().toLowerCase();    
        recurrenceRate = source.getRecurrenceRate().recurrenceRate.toString();
        if (source.getStartDate().isPresent()){
            startDate = source.getStartDate().get().toString();
        }
        if (source.getEndDate().isPresent()){
            endDate = source.getEndDate().get().toString();
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     * @throws ParseException 
     */
    public Task toModelType() throws IllegalValueException, ParseException {
        SimpleDateFormat dateParser = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        Date startDateForModel = null, endDateForModel = null;
        RecurrenceRate recurrenceRateForModel;
        
        final String name = new String(this.name);
        Priority priority = Priority.MEDIUM;

        if (this.priorityValue.equals("high")) {
            priority = Priority.HIGH;
        } else if (this.priorityValue.equals("medium")) {
            priority = Priority.MEDIUM;
        } else if (this.priorityValue.equals("low")) {
            priority = Priority.LOW;
        }
        
        if (this.startDate != null){
            startDateForModel = dateParser.parse(this.startDate);
        }
        if (this.endDate != null){
            endDateForModel = dateParser.parse(this.endDate);
        }
        recurrenceRateForModel = new RecurrenceRate(Integer.parseInt(this.recurrenceRate));
    
        return new Task(new Name(name), startDateForModel, endDateForModel, recurrenceRateForModel, priority);
    }
}
