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
        // TODO: fix sorting problems when adding tasks
        model.add("Task 1", task-> {
            task.setPinned(true);
            task.setLocation("NUS");
        });
        
        model.add("Task 3", task-> {
            task.setDescription("Description"); 
            task.setPinned(false);
        });
        
        model.add("Task 2");
    }
    
    
    @Test(expected=IllegalValueException.class)
    public void testEditInvalidIndex() throws Exception {
        setParameter("4");
        setParameter("l", "If this prints out this might hurt");
        execute();
    }
    
    @Test
    public void testEditPinned() throws Exception {
        setParameter("1");
        setParameter("p", null);
        execute();
        
        ImmutableTask toEditPin = getTaskAt(1);
        assertEquals("Task 1", toEditPin.getTitle());
        assertTrue(toEditPin.isPinned());
        assertFalse(toEditPin.getDescription().isPresent());
        assertFalse(toEditPin.getLocation().isPresent());
    }
    
    @Test
    public void testEditLocation() throws Exception {
        setParameter("3");
        setParameter("l", "NTU");
        execute();
        
        ImmutableTask toEditLocation = getTaskAt(3);
        assertEquals("Task 3", toEditLocation.getTitle());
        assertFalse(toEditLocation.isPinned());
        assertFalse(toEditLocation.getDescription().isPresent());
        assertEquals("NTU", toEditLocation.getLocation().get());
    }
    
    @Test
    public void testEditDescription() throws Exception {
        setParameter("2");
        setParameter("m", "Some other description");
        execute();
        
        ImmutableTask toEditDesc = getTaskAt(2);
        assertEquals("Task 2", toEditDesc.getTitle());
        assertFalse(toEditDesc.isPinned());
        assertFalse(toEditDesc.getLocation().isPresent());
        assertEquals("Some other description", toEditDesc.getDescription().get());
    }
    
    @Test
    public void testDeleteField() throws Exception {
        setParameter("2");
        setParameter("m", "");
        execute();
        
        ImmutableTask toDeleteField = getTaskAt(2);
        assertEquals("Task 2", toDeleteField.getTitle());
        assertFalse(toDeleteField.isPinned());
        assertFalse(toDeleteField.getDescription().isPresent());
        assertFalse(toDeleteField.getLocation().isPresent());
    }
    
    @Test
    public void testEditMoreThanOneParameter() throws Exception {
        setParameter("1");
        setParameter("m", "New description");
        setParameter("l", "Singapura");
        execute();
        
        ImmutableTask toEditTwoThings = getTaskAt(1);
        assertEquals("Task 1", toEditTwoThings.getTitle());
        assertFalse(toEditTwoThings.isPinned());
        assertEquals("New description", toEditTwoThings.getDescription().get());
        assertEquals("Singapura", toEditTwoThings.getLocation().get());
    }
}
