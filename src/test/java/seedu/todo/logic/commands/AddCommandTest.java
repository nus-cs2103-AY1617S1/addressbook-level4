package seedu.todo.logic.commands;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;

public class AddCommandTest extends CommandTest {
    @Override
    protected BaseCommand commandUnderTest() {
        return new AddCommand();
    }

    @Test
    public void testAdd() throws IllegalValueException, ValidationException {
        setParameter("Hello World");
        execute();
        assertTotalTaskCount(1);
        assertEquals("Hello World", this.getTaskAt(1).getTitle());
    }
}
