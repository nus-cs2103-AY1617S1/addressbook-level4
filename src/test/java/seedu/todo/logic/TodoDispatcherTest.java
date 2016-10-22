package seedu.todo.logic;

import org.junit.Test;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.AddCommand;
import seedu.todo.logic.commands.EditCommand;
import seedu.todo.logic.commands.ExitCommand;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

//@@author A0135817B
public class TodoDispatcherTest {
    private Dispatcher d = new TodoDispatcher();
    
    @Test
    public void testFullCommand() throws Exception {
        assertThat(d.dispatch("add"), instanceOf(AddCommand.class));
        assertThat(d.dispatch("exit"), instanceOf(ExitCommand.class));
    }
    
    @Test
    public void testPartialCommand() throws Exception {
        assertThat(d.dispatch("ed"), instanceOf(EditCommand.class));
        assertThat(d.dispatch("a"), instanceOf(AddCommand.class));
    }
    
    @Test(expected = IllegalValueException.class)
    public void testAmbiguousCommand() throws Exception {
        d.dispatch("e");
    }
    
    @Test(expected = IllegalValueException.class)
    public void testNonExistentCommand() throws Exception {
        d.dispatch("applejack");
    }
}
