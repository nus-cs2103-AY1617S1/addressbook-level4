package seedu.todo.logic.commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
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
    
    
    @Test(expected=IllegalValueException.class)
    public void testInvalidDelete() throws IllegalValueException{
        setParameter("4");
        execute();
    }

    @Test
    public void testDeleteFirst() throws IllegalValueException {
        ImmutableTask toDelete = getTaskAt(1);
        setParameter("1");
        execute();
        assertTaskNotExist(toDelete);
    }
    
    @Test
    public void testSuccessiveDeletes() throws IllegalValueException {
        ImmutableTask toDeleteFirst = getTaskAt(1);
        ImmutableTask toDeleteNext = getTaskAt(1);
        ImmutableTask toDeleteLast = getTaskAt(1);
        
        setParameter("1");
        execute();
        assertTaskNotExist(toDeleteFirst);
        assertTaskExist(toDeleteNext);
        assertTaskExist(toDeleteLast);
        
        setParameter("1");
        execute();
        assertTaskNotExist(toDeleteFirst);
        assertTaskNotExist(toDeleteNext);
        assertTaskExist(toDeleteLast);
        
        setParameter("1");
        execute();
        assertTaskNotExist(toDeleteFirst);
        assertTaskNotExist(toDeleteNext);
        assertTaskNotExist(toDeleteLast);
    }
    
}
