package seedu.todoList.storage;

import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;
import seedu.todoList.model.task.attributes.Priority;
import seedu.todoList.commons.exceptions.IllegalValueException;

import javax.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the task.
 */
public class XmlAdaptedTodo implements XmlAdaptedTask {
    
	@XmlElement(required = true)
	private String name;
	@XmlElement(required = true)
	private String startDate;
	@XmlElement(required = true)
	private String endDate;
	@XmlElement(required = true)
	private String priority;
	@XmlElement(required = true)
	private String isDone;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTodo () {}


    /**
     * Converts a given task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedtask
     */
    public XmlAdaptedTodo(Todo source) {
    	this.name = source.getName().name;
    	this.startDate = source.getStartDate().saveDate;
    	this.endDate = source.getEndDate().saveEndDate;
    	this.priority = source.getPriority().savePriority;
    	this.isDone = source.getDone().isDone;
    }
    
    public XmlAdaptedTodo(ReadOnlyTask source) {
    	this((Todo) source);
    }

    public Task toModelType() throws IllegalValueException {
        final Name name = new Name(this.name);
        final StartDate date = new StartDate(this.startDate);
        final EndDate endDate = new EndDate(this.endDate);
        final Priority priority = new Priority(this.priority);
        final Done isDone = new Done(this.isDone);
        return new Todo(name, date, endDate, priority, isDone);
    }
}

