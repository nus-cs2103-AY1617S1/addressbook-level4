package seedu.todo.logic.commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.task.ImmutableTask;

public class EditCommandTest extends CommandTest {
    @Override
    protected BaseCommand commandUnderTest() {
        return new EditCommand();    
    }
    
    @Before
    public void setUp() throws Exception {
    	//TODO fix sorting problems when adding tasks
        model.add("Task 1", task->{task.setDescription(null); 
        						   task.setPinned(true);
        						   task.setLocation("NUS");});
        model.add("Task 3", task->{task.setDescription("Description"); 
        						   task.setPinned(false);
        						   task.setLocation(null);});
        model.add("Task 2");
        
    }
    
    
    @Test(expected=IllegalValueException.class)
    public void testEditInvalidIndex() throws IllegalValueException {
        setParameter("4");
        setParameter("l", "If this prints out this might hurt");
        execute();
    }
    
    @Test
    public void testEditPinned() throws IllegalValueException{
        ImmutableTask toEditPin=getTaskAt(1);
        setParameter("1");
        setParameter("p", null);
        execute();
    	assertEquals("Task 1", toEditPin.getTitle());
        assertTrue(toEditPin.isPinned());
        assertFalse(toEditPin.getDescription().isPresent());
        assertFalse(toEditPin.getLocation().isPresent());
    }
    
    @Test
    public void testEditLocation() throws IllegalValueException {
    	ImmutableTask toEditLocation=getTaskAt(3);
        setParameter("3");
        setParameter("l", "NTU");
        execute();
        assertEquals("Task 3", toEditLocation.getTitle());
        assertFalse(toEditLocation.isPinned());
        assertFalse(toEditLocation.getDescription().isPresent());
        assertTrue(toEditLocation.getLocation().isPresent());
        assertEquals("NTU", toEditLocation.getLocation().get());
    }
    
    @Test
    public void testEditDescription() throws IllegalValueException {
    	ImmutableTask toEditDesc=getTaskAt(2);
        setParameter("2");
        setParameter("m", "Some other description");
        execute();
        assertEquals("Task 2", toEditDesc.getTitle());
        assertFalse(toEditDesc.isPinned());
        assertTrue(toEditDesc.getDescription().isPresent());
        assertFalse(toEditDesc.getLocation().isPresent());
        assertEquals("Some other description", toEditDesc.getDescription().get());
    }
    
    @Test
    public void testDeleteField() throws IllegalValueException{
    	ImmutableTask toDeleteField=getTaskAt(2);
    	setParameter("2");
    	setParameter("m", null);
    	execute();
    	assertEquals("Task 2", toDeleteField.getTitle());
        assertFalse(toDeleteField.isPinned());
        assertFalse(toDeleteField.getDescription().isPresent());
        assertFalse(toDeleteField.getLocation().isPresent());
    }
    
    @Test
    public void testEditMoreThanOneParameter() throws IllegalValueException{
    	ImmutableTask toEditTwoThings=getTaskAt(1);
    	setParameter("1");
    	setParameter("m", "New description");
    	setParameter("l", "Singapura");
    	execute();
    	assertEquals("Task 1", toEditTwoThings.getTitle());
        assertFalse(toEditTwoThings.isPinned());
        assertTrue(toEditTwoThings.getDescription().isPresent());
        assertTrue(toEditTwoThings.getLocation().isPresent());
        assertEquals("New description", toEditTwoThings.getDescription().get());
        assertEquals("Singapura", toEditTwoThings.getLocation().get());
        
    }
}