package seedu.todo.logic.commands;


import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.exceptions.ValidationException;

//@@author A00923282A
public class FindCommandTest extends CommandTest {
    
    @Override
    protected BaseCommand commandUnderTest() {
        return new FindCommand();
    }

    
    @Before
    public void setUp() throws Exception {
        model.add("CS2101 Project Task");
        model.add("CS2103T project");
        model.add("Unrelated task");
        model.add("Unrelated CS2101 that expands");
    }
    


    @Test
    public void testFindSuccessful() throws ValidationException {
        assertVisibleTaskCount(4);
        setParameter("CS2101");
        execute(true);
        assertVisibleTaskCount(2);
        }
    
    @Test
    public void testCaseInsensitive() throws ValidationException{
        assertVisibleTaskCount(4);
        setParameter("project");
        execute(true);
        assertVisibleTaskCount(2);
    }
    
    @Test
    public void testMultipleParamters() throws ValidationException{
        assertVisibleTaskCount(4);
        setParameter("task expands");
        execute(true);
        assertVisibleTaskCount(3);
    }
    
    @Test
    public void testUnsuccessfulFind() throws ValidationException{
        assertVisibleTaskCount(4);
        setParameter("team");
        execute(true);
        assertVisibleTaskCount(0);
    }

}
