package seedu.todo.logic.commands;

import org.junit.Before;
import org.junit.Test;

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
    
    
    @Test(expected=ValidationException.class)
    public void testInvalidDelete() throws Exception {
        setParameter("4");
        execute(true);
    }

    @Test
    public void testDeleteFirst() throws Exception {
        ImmutableTask toDelete = getTaskAt(1);
        setParameter("1");
        execute(true);
        assertTaskNotExist(toDelete);
    }
    
    @Test
    public void testSuccessiveDeletes() throws Exception {
        ImmutableTask toDeleteFirst = getTaskAt(1);
        ImmutableTask toDeleteNext = getTaskAt(2);
        ImmutableTask toDeleteLast = getTaskAt(3);
        
        setParameter("1");
        execute(true);
        assertTaskNotExist(toDeleteFirst);
        assertTaskExist(toDeleteNext);
        assertTaskExist(toDeleteLast);

        setParameter("1");
        execute(true);
        assertTaskNotExist(toDeleteFirst);
        assertTaskNotExist(toDeleteNext);
        assertTaskExist(toDeleteLast);

        setParameter("1");
        execute(true);
        assertTaskNotExist(toDeleteFirst);
        assertTaskNotExist(toDeleteNext);
        assertTaskNotExist(toDeleteLast);
    }
    
}
