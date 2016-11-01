package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.*;
import seedu.address.model.Alias;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import javax.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedAlias {

    @XmlElement(required = true)
    private String key;
    @XmlElement(required = true)
    private String value;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedAlias() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedAlias(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        
        // TODO add dates
        final Name name = new Name(this.name);
        final TaskType taskType = new TaskType(this.taskType);
        final Status status = new Status(this.status);
        
        final Optional<LocalDateTime> startDate;
        if (this.startDate.equals("")) {
        	startDate = Optional.empty();
        }
        else {
        	startDate = Optional.of(LocalDateTime.parse(this.startDate));
        }
        
        final Optional<LocalDateTime> endDate;
        if (this.endDate.equals("")) {
        	endDate = Optional.empty();
        }
        else {
        	endDate = Optional.of(LocalDateTime.parse(this.endDate));
        }
        
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, taskType, status, startDate, endDate, tags);
    }
}
