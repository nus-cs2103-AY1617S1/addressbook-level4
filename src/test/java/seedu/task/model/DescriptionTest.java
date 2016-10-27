package seedu.task.model;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.Description;

//@@author A0127570H
public class DescriptionTest {
    
    @Test
    public void descriptionTest () throws Exception {
        try {            
            Description description = new Description (".,$#@%&():");
        } catch (IllegalValueException ive) {
            String expectedMessage = Description.MESSAGE_DESCRIPTION_CONSTRAINTS + "LOL";
            assertEquals(ive.getMessage(),expectedMessage);
        }
    }
    
}
