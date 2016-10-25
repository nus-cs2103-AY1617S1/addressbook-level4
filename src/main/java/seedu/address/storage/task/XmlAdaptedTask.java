package seedu.address.storage.task;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Task;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String description;
    
    @XmlElement(required = true)
    private boolean favorite;
    
    @XmlElement(required = true)
    private boolean complete;
    
    @XmlElement(required = true)
    private Date startDate;
    
    @XmlElement(required = true)
    private Date endDate;
    
    @XmlElement(required = true)
    private Class<?> taskType;
    
    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    //@@author A0138978E
    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(Task source) {
    	description = source.getDescription().toString();
    	favorite = source.isFavorite();
    	complete = source.isComplete();
    	taskType = source.getClass();
    	
    	// Set dates appropriately
    	if (source instanceof DeadlineTask) {
    		startDate = null;
    		endDate = ((DeadlineTask)source).getDeadline();
    	} else if (source instanceof EventTask) {
    		EventTask castedSource = (EventTask)source;
    		startDate = castedSource.getStartDate();
    		endDate = castedSource.getEndDate();    		
    	} else {
    		startDate = null;
    		endDate = null;
    	}
    	
    	
    }

    /**
     * Converts this jaxb-friendly adapted task object into an appropriate Task subclass..
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
    	if (taskType == Task.class || taskType == null) {
    		throw new IllegalValueException("Incorrect task type: " + taskType.toString());
    	}
    	
    	Task taskToReturn = null;
    	
    	// Set dates
    	if (taskType == FloatingTask.class) {
    		taskToReturn =  new FloatingTask(description);
    	} else if (taskType == DeadlineTask.class) {
    		taskToReturn =  new DeadlineTask(description, endDate);
    	} else if (taskType == EventTask.class) {
    		taskToReturn =  new EventTask(description, startDate, endDate);
    	} else {
    		throw new IllegalValueException("Incorrect task type: " + taskType.toString());
    	}
    	
    	// Set favorite
    	if (favorite) {
    		taskToReturn.setAsFavorite();
    	} else {
    		taskToReturn.setAsNotFavorite();
    	}
    	
    	//Set complete
    	if (complete) {
    		taskToReturn.setAsComplete();
    	}
    	
    	return taskToReturn;
        
    }
    
  //@@author
    public boolean equals(XmlAdaptedTask o) {
        if((this.complete==o.complete) && this.description.equals(o.description) &&
                this.endDate.equals(o.endDate) && this.startDate.equals(o.endDate) &&
                this.taskType.equals(o.taskType)) {
            return true;
        }
        
        return false;
    }
}
