package seedu.todo.logic.commands;

import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;

//@@author A0139021U
public class CommandPreviewTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    
    @Test
    public void testFilterAdd() throws Exception {
        List<CommandSummary> expected = CommandMap.getCommand("add").getCommandSummary();
        List<CommandSummary> actual = new CommandPreview("add").getPreview();
        assertTrue(isShallowCompareCommandSummaries(expected, actual));
    }

    @Test
    public void testFilterEmptyString() throws Exception {
        List<CommandSummary> expected = new ArrayList<>();
        List<CommandSummary> actual = new CommandPreview("").getPreview();
        assertEquals(expected, actual);
    }
    
    private boolean isShallowCompareCommandSummaries(List<CommandSummary> list, List<CommandSummary> otherList) {
        if (list.size() != otherList.size()) {
            return false;
        }
        
        for (int i = 0; i < list.size(); i++) {
            CommandSummary summary = list.get(i);
            CommandSummary otherSummary = list.get(i);
            
            boolean isEqual = summary.arguments.equals(otherSummary.arguments) && 
                    summary.command.equals(otherSummary.command) &&
                    summary.scenario.equals(otherSummary.scenario);
            
            if (!isEqual) {
                return false;
            }
        }
        return true;
    }
}
