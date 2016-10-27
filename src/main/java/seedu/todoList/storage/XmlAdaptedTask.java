package seedu.todoList.storage;

import seedu.todoList.model.task.*;
import seedu.todoList.commons.exceptions.IllegalValueException;

//@@author A0144061U-reused
/**
 * JAXB-friendly version of the task.
 */
public interface XmlAdaptedTask {

    /**
     * Converts this jaxb-friendly adapted task object into the model's task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException;
}
