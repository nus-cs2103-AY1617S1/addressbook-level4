package seedu.todo.logic.commands;

import com.google.common.collect.ImmutableList;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.ImmutableTodoList;
import seedu.todo.model.task.Task;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LoadCommandTest extends CommandTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    
    @Mock private ImmutableTodoList tasks;
    
    @Override
    protected BaseCommand commandUnderTest() {
        return new LoadCommand();
    }
    
    @Test
    public void testSaveLocation() throws Exception {
        setParameter("new file");
        when(storage.read("new file")).thenReturn(tasks);
        when(tasks.getTasks()).thenReturn(ImmutableList.of(new Task("Hello world")));
            
        execute(true);
        assertEquals("Hello world", getTaskAt(1).getTitle());
    }

    @Test(expected = ValidationException.class)
    public void testHandleFileError() throws Exception {
        setParameter("new file");
        doThrow(new FileNotFoundException()).when(storage).read("new file");
        execute(false);
    }

}
