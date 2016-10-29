package guitests;

import org.junit.Test;

import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.testutil.TaskBuilder;
import seedu.todoList.testutil.TestTask;

import static org.junit.Assert.assertEquals;

public class CommandBoxTest extends ListGuiTest {

    @Test
    public void commandBox_commandSucceeds_textCleared() throws IllegalValueException {
        TestTask taskToAdd = new TaskBuilder().withName("TODO 123").withStartDate("28-11-2016").withEndDate("29-11-2016").withPriority("1").withDone("false").build();

        commandBox.runCommand(taskToAdd.getAddCommand());
        assertEquals(commandBox.getCommandInput(), "");
    }

    @Test
    public void commandBox_commandFails_textStays(){
        commandBox.runCommand("invalid command");
        assertEquals(commandBox.getCommandInput(), "invalid command");
        //TODO: confirm the text box color turns to red
    }

}
