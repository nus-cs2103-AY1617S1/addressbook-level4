package seedu.todoList.storage;

import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;
import seedu.todoList.model.task.attributes.EndTime;
import seedu.todoList.commons.exceptions.IllegalValueException;

import javax.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the task.
 */
public class XmlAdaptedDeadline implements XmlAdaptedTask {
    
	@XmlElement(required = true)
	private String name;
	@XmlElement(required = true)
	private String startDate;
	@XmlElement(required = true)
    private String endTime;
	@XmlElement(required = true)
    private String isDone;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedDeadline () {}


    /**
     * Converts a given task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedtask
     */
    public XmlAdaptedDeadline(Deadline source) {
    	name = source.getName().name;
    	startDate = source.getDate().saveDate;
        endTime = source.getEndTime().saveEndTime;
        isDone = source.getDone().isDone;
    }
    
    public XmlAdaptedDeadline(ReadOnlyTask source) {
    	this((Deadline) source);
    }

    public Task toModelType() throws IllegalValueException {
        final Name name = new Name(this.name);
        final StartDate date = new StartDate(this.startDate);
        final EndTime endTime = new EndTime(this.endTime);
        final Done isDone = new Done(this.isDone);
        return new Deadline(name, date, endTime, isDone);
    }
}

