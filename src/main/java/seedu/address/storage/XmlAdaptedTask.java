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

//@@author A0093960X
/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedTask {

    private static SimpleDateFormat dateParser = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String priorityValue;
    @XmlElement
    private String startDate;
    @XmlElement
    private String endDate;
    @XmlElement
    private XmlAdaptedRecurrenceRate recurrenceRate;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     *            XmlAdaptedPerson
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        saveName(source);
        savePriority(source);
        saveStartDateIfPresent(source);
        saveEndDateIfPresent(source);
        saveRecurrenceRateIfPresent(source);
    }

    /**
     * Saves the priority of the source ReadOnlyTask into the XmlAdaptedTask.
     * 
     * @param source
     */
    private void savePriority(ReadOnlyTask source) {
        String priorityString = source.getPriorityValue().toString();
        priorityValue = priorityString.toLowerCase();
    }

    /**
     * Saves the name of the source ReadOnlyTask into the XmlAdaptedTask.
     * 
     * @param source
     */
    private void saveName(ReadOnlyTask source) {
        name = source.getName().getTaskName();
    }

    /**
     * Saves the recurrence rate of the source ReadOnlyTask into the
     * XmlAdaptedTask.
     * 
     * @param source
     */
    private void saveRecurrenceRateIfPresent(ReadOnlyTask source) {
        boolean hasRecurrenceRate = hasRecurrenceRate(source);
        if (hasRecurrenceRate) {
            recurrenceRate = new XmlAdaptedRecurrenceRate(source.getRecurrenceRate().get());
        }
    }

    /**
     * Saves the end date of the source ReadOnlyTask into the XmlAdaptedTask.
     * 
     * @param source
     */
    private void saveEndDateIfPresent(ReadOnlyTask source) {
        boolean hasEndDate = taskHasEndDate(source);

        if (hasEndDate) {
            endDate = source.getEndDate().get().toString();
        }
    }

    /**
     * Saves the start date of the source ReadOnlyTask into the XmlAdaptedTask.
     * 
     * @param source
     */
    private void saveStartDateIfPresent(ReadOnlyTask source) {
        boolean hasStartDate = taskHasStartDate(source);
        if (hasStartDate) {
            startDate = source.getStartDate().get().toString();
        }
    }

    /**
     * Returns whether the source ReadOnlyTask has a recurrence rate.
     * 
     * @param source the ReadOnlyTask to check
     * @return boolean representing if it has recurrence rate
     */
    private boolean hasRecurrenceRate(ReadOnlyTask source) {
        return source.getRecurrenceRate().isPresent();
    }

    /**
     * Returns whether the source ReadOnlyTask has an end date.
     * 
     * @param source the ReadOnlyTask to check
     * @return boolean representing if it has end date
     */
    private boolean taskHasEndDate(ReadOnlyTask source) {
        return source.getEndDate().isPresent();
    }

    /**
     * Returns whether the source ReadOnlyTask has a start date.
     * 
     * @param source the ReadOnlyTask to check
     * @return boolean representing if it has start date
     */
    private boolean taskHasStartDate(ReadOnlyTask source) {
        return source.getStartDate().isPresent();
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person
     * object.
     *
     * @throws IllegalValueException if there were any data constraints violated
     *             in the adapted person
     * @throws ParseException
     */
    public Task toModelType() throws IllegalValueException, ParseException {
        final String nameForModel = retrieveNameFromStoredTask();
        Priority priorityForModel = retrievePriorityFromStoredTask();

        Date startDateForModel = retrieveStartDateFromStoredTask();
        Date endDateForModel = retrieveEndDateFromStoredTask();
        RecurrenceRate recurrenceRateForModel = retrieveRecurrenceRateFromStoredTask();

        return new Task(new Name(nameForModel), startDateForModel, endDateForModel, recurrenceRateForModel,
                priorityForModel);
    }

    /**
     * Retrieves the ReccurenceRate associated with the stored task. Returns
     * null if there is no recurrence rate associated with the stored task.
     * 
     * @return the RecurrenceRate associated with the task, or null if no
     *         recurrence rate is associated with the current stored task.
     * 
     * @throws IllegalValueException
     * @throws ParseException
     */
    private RecurrenceRate retrieveRecurrenceRateFromStoredTask() throws IllegalValueException, ParseException {
        if (this.recurrenceRate == null) {
            return null;
        }
        return recurrenceRate.toModelType();
    }

    /**
     * Retrieves the end date Date associated with the stored task. Returns
     * null if there is no end date associated with the stored task.
     * 
     * @return the end date Date associated with the task, or null if no
     *         end date is associated with the current stored task.
     * @throws ParseException
     */
    private Date retrieveEndDateFromStoredTask() throws ParseException {
        if (this.endDate == null) {
            return null;
        }

        return dateParser.parse(this.endDate);
    }

    /**
     * Retrieves the start date Date associated with the stored task. Returns
     * null if there is no start date associated with the stored task.
     * 
     * @return the start date Date associated with the task, or null if no
     *         start date is associated with the current stored task.
     * @throws ParseException
     */
    private Date retrieveStartDateFromStoredTask() throws ParseException {
        if (this.startDate == null) {
            return null;
        }
        return dateParser.parse(this.startDate);
    }

    /**
     * Retrieves the Priority associated with the stored task.
     * 
     * @return the Priority associated with the stored task
     * @throws ParseException
     */
    private Priority retrievePriorityFromStoredTask() throws IllegalValueException {
        String storedPriority = this.priorityValue;

        switch (storedPriority) {
            case "high":
            case "h":
                return Priority.HIGH;
            case "medium":
            case "med":
            case "m":
                return Priority.MEDIUM;
            case "low":
            case "l":
                return Priority.LOW;
            default:
                // invalid value
                throw new IllegalValueException("The priority value stored is invalid");
            }
    }

    /**
     * Retrieves the Name associated with the stored task.
     * 
     * @return the Name associated with the stored task
     * @throws ParseException
     */
    private String retrieveNameFromStoredTask() {
        assert this.name != null;

        return new String(this.name);
    }
}
