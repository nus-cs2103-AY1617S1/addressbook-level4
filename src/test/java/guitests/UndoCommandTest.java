//@@author A0097627N
package guitests;

import guitests.guihandles.TaskCardHandle;

import org.junit.Test;

import seedu.savvytasker.logic.commands.UndoCommand;
import seedu.savvytasker.logic.commands.HelpCommand;
import seedu.savvytasker.testutil.TestTask;
import seedu.savvytasker.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.savvytasker.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.savvytasker.logic.commands.UndoCommand.MESSAGE_UNDO_ACKNOWLEDGEMENT;

public class UndoCommandTest extends SavvyTaskerGuiTest {

    TestTask[] expectedList = td.getTypicalTasks();
    TestTask[] currentList = td.getTypicalTasks();
    TestTask firstTaskToAdd = td.happy;
    TestTask secondTaskToAdd = td.haloween;
    TestTask pjmTaskToAdd = td.pjm;
    TestTask projectMeetingTaskToAdd = td.projectMeeting;

    @Test
    // undo one add command
    public void undoAddTest() {
        expectedList = td.getTypicalTasks();
        commandBox.runCommand(firstTaskToAdd.getAddCommand());
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(MESSAGE_UNDO_ACKNOWLEDGEMENT);
    }

    @Test
    // undo a delete command
    public void undoDeleteTest() {
        expectedList = td.getTypicalTasks();
        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(MESSAGE_UNDO_ACKNOWLEDGEMENT);
    }

    @Test
    // undo clear command
    public void undoClearTest() {
        expectedList = td.getTypicalTasks();
        commandBox.runCommand("clear");
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(MESSAGE_UNDO_ACKNOWLEDGEMENT);
    }

    @Test
    // undo alias command
    public void undoAliasTest() {
        expectedList = td.getTypicalTasks();
        expectedList = TestUtil.addTasksToList(expectedList, pjmTaskToAdd);
        commandBox.runCommand("alias k/pjm r/Project Meeting");
        commandBox.runCommand("undo"); 
        assertResultMessage(MESSAGE_UNDO_ACKNOWLEDGEMENT);
        commandBox.runCommand(pjmTaskToAdd.getAddCommand());
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    @Test
    // undo unalias command
    public void undoUnaliasTest() {
        expectedList = TestUtil.addTasksToList(currentList, projectMeetingTaskToAdd);
        commandBox.runCommand("alias k/pjm r/Project Meeting");
        commandBox.runCommand("unalias pjm");
        commandBox.runCommand("undo");
        assertResultMessage(MESSAGE_UNDO_ACKNOWLEDGEMENT);
        commandBox.runCommand(pjmTaskToAdd.getAddCommand());
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    // undo mark command
    @Test
    public void undoMarkTest() {
        expectedList = td.getTypicalTasks();
        commandBox.runCommand("mark 1");
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(MESSAGE_UNDO_ACKNOWLEDGEMENT);
    }

    // undo two add commands
    @Test
    public void undoTwoAddTest() {
        expectedList = td.getTypicalTasks();
        commandBox.runCommand(firstTaskToAdd.getAddCommand());
        commandBox.runCommand(secondTaskToAdd.getAddCommand());
        commandBox.runCommand("undo");
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(MESSAGE_UNDO_ACKNOWLEDGEMENT);
    }

    // undo two delete commands
    @Test
    public void undoTwoDeleteTest() {
        expectedList = td.getTypicalTasks();
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo");
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(MESSAGE_UNDO_ACKNOWLEDGEMENT);
    }

    // undo a delete command followed by an add command
    @Test
    public void undoDeleteAddTest() {
        expectedList = td.getTypicalTasks();
        commandBox.runCommand(firstTaskToAdd.getAddCommand());
        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo");
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(MESSAGE_UNDO_ACKNOWLEDGEMENT);
    }

    // undo an add command followed by a delete command
    @Test
    public void undoAddDeleteTest() {
        expectedList = td.getTypicalTasks();
        commandBox.runCommand("delete 1");
        commandBox.runCommand(firstTaskToAdd.getAddCommand());
        commandBox.runCommand("undo");
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(MESSAGE_UNDO_ACKNOWLEDGEMENT);
    }

    // invalid command
    @Test
    public void invalidTest() {
        commandBox.runCommand("undos");
        assertResultMessage("Input: undos\n" + String.format(MESSAGE_UNKNOWN_COMMAND, HelpCommand.MESSAGE_USAGE));
    }
}
// @@author
