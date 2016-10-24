package seedu.address.logic.commands;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.RecurrenceRate;
import seedu.address.model.item.Task;
import seedu.address.testutil.TestOptionalStringTask;

//@@author A0139655U
public class AddCommandTest {

    TestOptionalStringTask optionalStringTask;
    AddCommand command;
    
    @Test
    public void addCommand_invalidRate() {
        optionalStringTask = new TestOptionalStringTask("eat bingsu from the beach", "11pm", "1am", "0", "days", "medium");
        try {
            command = new AddCommand(optionalStringTask.taskName, optionalStringTask.startDate, 
                    optionalStringTask.endDate, optionalStringTask.rate, optionalStringTask.timePeriod, optionalStringTask.priority);
            assert false;
        } catch (IllegalValueException ive) {
            assertEquals(ive.getMessage(), RecurrenceRate.MESSAGE_VALUE_CONSTRAINTS);
        }
    }
    
    @Test
    public void addCommand_invalidTimePeriod() {
        optionalStringTask = new TestOptionalStringTask("eat bingsu from the beach", "11pm", "1am", "5", "bobs", "medium");
        try {
            command = new AddCommand(optionalStringTask.taskName, optionalStringTask.startDate, 
                    optionalStringTask.endDate, optionalStringTask.rate, optionalStringTask.timePeriod, optionalStringTask.priority);
            assert false;
        } catch (IllegalValueException ive) {
            assertEquals(ive.getMessage(), RecurrenceRate.MESSAGE_VALUE_CONSTRAINTS);
        }
    }

    @Test
    public void addCommand_validInput() {
        optionalStringTask = new TestOptionalStringTask("lower word count from 1000 to 500", "11pm", "1am", "1", "Monday", "k");
        try {
            command = new AddCommand(optionalStringTask.taskName, optionalStringTask.startDate, 
                    optionalStringTask.endDate, optionalStringTask.rate, optionalStringTask.timePeriod, optionalStringTask.priority);
            Task task = command.getToAdd();
            //assertEquals(task.toString(), "lower word count from 1000 to 500, Priority: medium, ")
        } catch (IllegalValueException ive) {
            assert false;
        }
    }
}
