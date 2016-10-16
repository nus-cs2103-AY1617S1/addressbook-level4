package seedu.todo.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import seedu.todo.commons.exceptions.ValidationException;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

public class SaveCommandTest extends CommandTest {
    @Test
    public void testGetStorageLocation() throws Exception {
        when(storage.getLocation()).thenReturn("test location");
        execute(true);
        
        assertThat(result.getFeedback(), containsString("test location"));
        verify(storage, never()).save(eq(model), anyString());
    }
    
    @Test
    public void testSaveLocation() throws Exception {
        setParameter("new file");
        execute(true);
        verify(storage).save(model, "new file");
    }
    
    @Test(expected = ValidationException.class)
    public void testHandleFileError() throws Exception {
        setParameter("new file");
        doThrow(new IOException()).when(storage).save(model, "new file");
        execute(false);
    }
    
    @Override
    protected BaseCommand commandUnderTest() {
        return new SaveCommand();
    }
}
