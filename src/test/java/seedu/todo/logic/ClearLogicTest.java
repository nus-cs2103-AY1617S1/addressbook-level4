 //@@author A0093896H
package seedu.todo.logic;

import java.util.Collections;

import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.ClearCommand;
import seedu.todo.model.DoDoBird;


/**
 * Test class for the clear command's logic
 */
public class ClearLogicTest extends CommandLogicTest {
    @Test
    public void execute_clear() throws IllegalValueException {
        model.addTask(helper.generateFullTask(1));
        model.addTask(helper.generateFullTask(2));
        model.addTask(helper.generateFullTask(3));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new DoDoBird(), Collections.emptyList());
    }
}
