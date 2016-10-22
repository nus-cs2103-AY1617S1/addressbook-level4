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
<<<<<<< HEAD
    	this.date = source.getStartDate().date;
=======
    	this.startDate = source.getDate().date;
>>>>>>> d876d25f67ebfba58b9d933e317b1adad2bd2edc
    	this.endDate = source.getEndDate().endDate;
    	this.priority = source.getPriority().toString();
    }
    
    public XmlAdaptedTodo(ReadOnlyTask source) {
    	this((Todo) source);
    }

    public Task toModelType() throws IllegalValueException {
        final Name name = new Name(this.name);
        final StartDate date = new StartDate(this.startDate);
        final EndDate endDate = new EndDate(this.endDate);
        final Priority priority = new Priority(this.priority);
        return new Todo(name, date, endDate, priority);
    }
}

