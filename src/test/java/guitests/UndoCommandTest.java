package guitests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import teamfour.tasc.logic.LogicManager;
import teamfour.tasc.logic.commands.UndoCommand;

public class UndoCommandTest extends AddressBookGuiTest {
    /*
    public static int commandsInStack;
    
    public void prepare() {
        commandBox.runCommand("add \"undo test case 1\"");
        commandBox.runCommand("add \"undo test case 2\"");
        commandBox.runCommand("add \"undo test case 3\"");
        commandsInStack = LogicManager.numUndoableCommands();
    }
    
    @Test
    public void undo() {
        prepare();
        
        //Undo first 2 commands
        assertUndoSuccess(2);

        //Invalid inputs of undo steps
        commandBox.runCommand("undo " + (LogicManager.numUndoableCommands() + 1));
        assertResultMessage(
                String.format(UndoCommand.MESSAGE_NUM_COMMANDS_NOT_ENOUGH, LogicManager.numUndoableCommands()));
        
        //Undo last command
        assertUndoSuccess(1);
    }
    /*
    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    /*
    private void assertUndoSuccess(int steps) {
        String command = (steps == 1) ? "undo" : "undo " + steps;
        commandsInStack -= steps;
        commandBox.runCommand(command);

        //confirm the undoable command stack contains correct number of commands
        assertTrue(commandsInStack == LogicManager.numUndoableCommands());

        //confirm the result message is correct
        assertResultMessage(String.format(UndoCommand.MESSAGE_SUCCESS, (steps == 1) ? "command" : steps + " commands"));
    }
    */
    
}
