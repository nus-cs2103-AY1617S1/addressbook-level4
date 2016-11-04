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
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    private static SimpleDateFormat dateParser = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String priority;
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
     * Converts a given Task into this class for JAXB use. Future changes to
     * this will not affect the created XmlAdaptedTask.
     *
     * @param source the task to convert
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        saveName(source);
        savePriority(source);
        saveStartDateIfPresent(source);
        saveEndDateIfPresent(source);
        saveRecurrenceRateIfPresent(source);
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task
     * object.
     *
     * @throws IllegalValueException if there were any data constraints violated
     *             in the adapted task
     * @throws ParseException
     */
    public Task toModelType() throws IllegalValueException, ParseException {
        Name nameForModel = getNameFromStoredTask();
        Priority priorityForModel = getPriorityFromStoredTask();

        Date startDateForModel = getStartDateFromStoredTask();
        Date endDateForModel = getEndDateFromStoredTask();
        RecurrenceRate recurrenceRateForModel = getRecurrenceRateFromStoredTask();

        return new Task(nameForModel, startDateForModel, endDateForModel, recurrenceRateForModel,
                priorityForModel);
    }

    /**
     * Saves the name of the source ReadOnlyTask into the XmlAdaptedTask.
     * 
     * @param source the Task to convert into the XmlAdaptedTask
     */
    private void saveName(ReadOnlyTask source) {
        name = source.getName().name;
    }

    /**
     * Saves the priority of the source ReadOnlyTask into the XmlAdaptedTask.
     * 
     * @param source the Task to convert into the XmlAdaptedTask
     */
    private void savePriority(ReadOnlyTask source) {
        String priorityString = source.getPriorityValue().toString();
        priority = priorityString.toLowerCase();
    }

    /**
     * Saves the start date of the source ReadOnlyTask into the XmlAdaptedTask.
     * 
     * @param source the Task to convert into the XmlAdaptedTask
     */
    private void saveStartDateIfPresent(ReadOnlyTask source) {
        boolean hasStartDate = source.getStartDate().isPresent();

        if (hasStartDate) {
            startDate = source.getStartDate().get().toString();
        }
    }

    /**
     * Saves the end date of the source ReadOnlyTask into the XmlAdaptedTask.
     * 
     * @param source the Task to convert into the XmlAdaptedTask
     */
    private void saveEndDateIfPresent(ReadOnlyTask source) {
        boolean hasEndDate = source.getEndDate().isPresent();

        if (hasEndDate) {
            endDate = source.getEndDate().get().toString();
        }
    }

    /**
     * Saves the recurrence rate of the source ReadOnlyTask into the
     * XmlAdaptedTask.
     * 
     * @param source the Task to convert into the XmlAdaptedTask
     */
    private void saveRecurrenceRateIfPresent(ReadOnlyTask source) {
        boolean hasRecurrenceRate = source.getRecurrenceRate().isPresent();

        if (hasRecurrenceRate) {
            recurrenceRate = new XmlAdaptedRecurrenceRate(source.getRecurrenceRate().get());
        }
    }

    /**
     * Retrieves the Name associated with the stored task.
     * 
     * @return the Name associated with the stored task
     */
    private Name getNameFromStoredTask() {
        assert name != null;

        return new Name(name);
    }

    /**
     * Retrieves the Priority associated with the stored task. If the priority
     * string in the stored task does not match any valid priority strings, the
     * task will be given a default medium priority.
     * 
     * @return the Priority associated with the stored task
     */
    private Priority getPriorityFromStoredTask() {
        //assert priority != null;
        
        return Priority.convertStringToPriority(priority);
    }

    /**
     * Retrieves the start date Date associated with the stored task. Returns
     * null if there is no start date associated with the stored task.
     * 
     * @return the start date Date associated with the task, or null if no start
     *         date is associated with the current stored task.
     * @throws ParseException if the format of the start date does not conform
     *             to the expected format
     */
    private Date getStartDateFromStoredTask() throws ParseException {
        if (startDate == null) {
            return null;
        }
        return dateParser.parse(startDate);
    }

    /**
     * Retrieves the end date Date associated with the stored task. Returns null
     * if there is no end date associated with the stored task.
     * 
     * @return the end date Date associated with the task, or null if no end
     *         date is associated with the current stored task.
     * @throws ParseException if the format of the end date does not conform to
     *             the expected format
     */
    private Date getEndDateFromStoredTask() throws ParseException {
        if (endDate == null) {
            return null;
        }

        return dateParser.parse(endDate);
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
    private RecurrenceRate getRecurrenceRateFromStoredTask() throws IllegalValueException {
        if (recurrenceRate == null) {
            return null;
        }

        return recurrenceRate.toModelType();
    }

}
