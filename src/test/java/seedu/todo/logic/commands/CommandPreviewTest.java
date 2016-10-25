package seedu.todo.logic.commands;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.google.common.collect.Sets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Rule;

//@@author A0135817B
public class CommandPreviewTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    
    @Before
    public void setUpPreview() throws Exception {
        Set<String> mockCommands = Sets.newHashSet("add", "delete");
    }
    
    @Test
    public void testFilter() throws Exception {
        // TODO: find way to mock static methods
    }
}
