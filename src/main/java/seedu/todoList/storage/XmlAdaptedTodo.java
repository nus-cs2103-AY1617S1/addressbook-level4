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
    	this.date = source.getDate().date;
    	this.endDate = source.getEndDate().endDate;
=======
    	this.startDate = source.getStartDate().date;
    	this.endDate = source.getEndDate().date;
>>>>>>> a6d44dc7e69055ef975aa340b5da8a8ec532aa33
    	this.priority = source.getPriority().toString();
    }
    
    public XmlAdaptedTodo(ReadOnlyTask source) {
    	this((Todo) source);
    }

    public Task toModelType() throws IllegalValueException {
        final Name name = new Name(this.name);
<<<<<<< HEAD
        final Date date = new Date(this.date);
        final EndDate endDate = new EndDate(this.endDate);
        final Priority priority = new Priority(this.priority);
        return new Todo(name, date, endDate, priority);
=======
        final Date startDate = new Date(this.startDate);
        final Date endDate = new Date(this.endDate);
        final Priority priority = new Priority(this.priority);
        return new Todo(name, startDate, endDate, priority);
>>>>>>> a6d44dc7e69055ef975aa340b5da8a8ec532aa33
    }
}

