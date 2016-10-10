package seedu.todo.logic.commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.task.ImmutableTask;

public class DeleteCommandTest extends CommandTest {
    @Override
    protected BaseCommand commandUnderTest() {
        return new DeleteCommand();
    }

    @Before
    public void setUp() throws Exception {
        model.add("Task 3");
        model.add("Task 2");
        model.add("Task 1");
    }

    @Test
    public void testDeleteFirst() throws IllegalValueException, ValidationException {
        ImmutableTask toDelete = getTaskAt(1);
        setParameter("1");
        execute();
        assertTaskNotExist(toDelete);
    }
}
