package seedu.todo.logic.commands;

import org.junit.Test;
import seedu.todo.commons.exceptions.ValidationException;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

//@@author A0135817B
public class SaveCommandTest extends CommandTest {
    @Test
    public void testGetStorageLocation() throws Exception {
        when(storage.getLocation()).thenReturn("test location");
        execute(true);
        
        assertThat(result.getFeedback(), containsString("test location"));
        verify(storage, never()).save(eq(todolist), anyString());
    }
    
    @Test
    public void testSaveLocation() throws Exception {
        setParameter("new file");
        execute(true);
        verify(storage).save(todolist, "new file");
    }
    
    @Test(expected = ValidationException.class)
    public void testHandleFileError() throws Exception {
        setParameter("new file");
        doThrow(new IOException()).when(storage).save(todolist, "new file");
        execute(false);
    }
    
    @Override
    protected BaseCommand commandUnderTest() {
        return new SaveCommand();
    }
}
