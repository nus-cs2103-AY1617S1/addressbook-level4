package seedu.todo.logic.commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;

public class EditCommandTest extends CommandTest {
    @Override
    protected BaseCommand commandUnderTest() {
        return new EditCommand();    
    }
    
    @Before
    public void setUp() throws Exception {
        model.add("Task 3", task->{task.setDescription(null); task.setPinned(true);task.setLocation("NUS");});
        model.add("Task 2", task->{task.setDescription("Description"); task.setPinned(false);task.setLocation(null);});
        model.add("Task 1");
    }
    
    @Test(expected=IllegalValueException.class)
    public void testEditInvalidIndex() throws IllegalValueException {
    	this.setParameter("4");
    	this.setParameter("l", "If this prints out this might hurt");
    	execute();
    }
    
    @Test
    public void testEditPinned() throws IllegalValueException{
    	this.setParameter("1");
    	this.setParameter("p", null);
    	execute();
    	assertEquals(true, this.getTaskAt(1).isPinned());
    }
    
    @Test
    public void testEditLocation() throws IllegalValueException {
        this.setParameter("3");
        this.setParameter("l", "NTU");
        execute();
        assertEquals("NTU", this.getTaskAt(3).getLocation().get());
    }
    
    @Test
    public void testEditDescription() throws IllegalValueException {
        this.setParameter("2");
        this.setParameter("m", "Some other description");
        execute();
        assertEquals("Some other description", this.getTaskAt(2).getDescription().get());
    }
    
    @Test
    public void testDeleteField() throws IllegalValueException{
    	this.setParameter("2");
    	this.setParameter("m", null);
    	execute();
    	assertEquals(false, this.getTaskAt(2).getDescription().isPresent());
    }
}