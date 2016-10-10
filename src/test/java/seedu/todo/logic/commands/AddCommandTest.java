package seedu.todo.logic.commands;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;

public class AddCommandTest extends CommandTest {
    @Override
    protected BaseCommand commandUnderTest() {
        return new AddCommand();
    }

    @Test
    public void testAddTask() throws IllegalValueException {
        setParameter("Hello World");
        execute();
        assertTotalTaskCount(1);
        assertEquals("Hello World", this.getTaskAt(1).getTitle());
        assertFalse(this.getTaskAt(1).isPinned());
        assertFalse(this.getTaskAt(1).getDescription().isPresent());
        assertFalse(this.getTaskAt(1).getLocation().isPresent());
    }
    
    @Test
    public void testAddTaskWithLocation() throws IllegalValueException {
        setParameter("Hello NUS");
        setParameter("l", "NUS");
        execute();
        assertTotalTaskCount(1);
        assertEquals("Hello NUS", this.getTaskAt(1).getTitle());
        assertFalse(this.getTaskAt(1).isPinned());
        assertFalse(this.getTaskAt(1).getDescription().isPresent());      
        assertTrue(this.getTaskAt(1).getLocation().isPresent());
        assertEquals("NUS", this.getTaskAt(1).getLocation().get());
    }
    
    @Test
    public void testAddTaskWithDescription() throws IllegalValueException {
        setParameter("Destroy World");
        setParameter("m", "Remember to get Dynamites on sale!");
        execute();
        assertTotalTaskCount(1);
        assertEquals("Destroy World", this.getTaskAt(1).getTitle());
        assertFalse(this.getTaskAt(1).isPinned());
        assertTrue(this.getTaskAt(1).getDescription().isPresent());      
        assertFalse(this.getTaskAt(1).getLocation().isPresent());
        assertEquals("Remember to get Dynamites on sale!", this.getTaskAt(1).getDescription().get());
    }
    
    @Test
    public void testAddPinnedTask() throws IllegalValueException {
        setParameter("Li Kai's Presentation");
        setParameter("p",null);
        execute();
        assertTotalTaskCount(1);
        assertEquals("Li Kai's Presentation", this.getTaskAt(1).getTitle());
        assertTrue(this.getTaskAt(1).isPinned());
        assertFalse(this.getTaskAt(1).getDescription().isPresent());      
        assertFalse(this.getTaskAt(1).getLocation().isPresent());
    }
    
    @Test
    public void testAddMultipleParameters() throws IllegalValueException {
        setParameter("Task 1");
        setParameter("p",null);
        setParameter("l","COM1");
        setParameter("m","Useless task");
        execute();
        assertTotalTaskCount(1);
        assertEquals("Task 1", this.getTaskAt(1).getTitle());
        assertTrue(this.getTaskAt(1).isPinned());
        assertTrue(this.getTaskAt(1).getDescription().isPresent());      
        assertTrue(this.getTaskAt(1).getLocation().isPresent());
        assertEquals("COM1", this.getTaskAt(1).getLocation().get());
        assertEquals("Useless task", this.getTaskAt(1).getDescription().get());
    }
}
