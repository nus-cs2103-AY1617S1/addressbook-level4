package seedu.todo.logic.commands;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;

public class AddCommandTest extends CommandTest {
    @Override
    protected BaseCommand commandUnderTest() {
        return new AddTodoCommand();
    }

    @Test
    public void testAdd() throws IllegalValueException {
        this.setParameter("Hello World");
        this.execute();
        this.assertTotalTaskCount(1);
    }
}
