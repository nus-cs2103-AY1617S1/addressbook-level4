package seedu.todo.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;

/**
 * Test class for the store and reset command's logic
 */
public class StoreLogicTest extends CommandLogicTest {
    
    @Test
    public void execute_reset() throws IllegalValueException {
        String origLocation = config.getToDoListFilePath();
        logic.execute("reset");
        
        assertEquals(origLocation, config.getToDoListFilePath());
    }
    
    
    @Test
    public void execute_store() throws IllegalValueException {
        logic.execute("store " + alternateFolder.getRoot().getPath());
        String newLocation = config.getToDoListFilePath();
        assertEquals(newLocation, config.getToDoListFilePath());
        
        logic.execute("reset"); //reset config
    }
}
