package seedu.address.logic;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.core.EventsCenter;
import seedu.address.logic.commands.*;
import seedu.address.logic.parser.CommandParser;
import seedu.address.history.UndoableCommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.testutil.StorageStub;
import seedu.address.testutil.UndoableCommandHistoryStub;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.*;

//@@author A0093960X
public class TooltipTest {

    private CommandParser commandParser;

    @Before
    public void setup() {
        commandParser = new CommandParser();
        EventsCenter.getInstance().registerHandler(this);
    }

    @After
    public void teardown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void tooltip_undoneListInvalidCommandInput_incorrectCommandTooltip() {
        assertToolTipBehaviorUndoneList("   ", MESSAGE_TOOLTIP_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void tooltip_doneListInvalidCommandInput_incorrectCommandTooltip() {
        assertToolTipBehaviorDoneList("   ", MESSAGE_TOOLTIP_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void tooltip_doneListInvalidCommand_restrictedCommandTooltip() {
        assertToolTipBehaviorDoneList("add", MESSAGE_DONE_LIST_RESTRICTED_COMMANDS);
        assertToolTipBehaviorDoneList("edit", MESSAGE_DONE_LIST_RESTRICTED_COMMANDS);
        assertToolTipBehaviorDoneList("done", MESSAGE_DONE_LIST_RESTRICTED_COMMANDS);
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfAdd_addTooltip() {
        // simple add tooltip
        assertToolTipBehaviorUndoneList("a", AddCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("add", AddCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("    add", AddCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_detailedAddInput_detailedAddTooltip() {
        // complicated add tooltip
        assertToolTipBehaviorUndoneList("add f",
                AddCommand.TOOL_TIP + "\n\tAdding task: \n\tName:\tf\n\tPriority:\tmedium");
        assertToolTipBehaviorUndoneList("meet akshay at 1pm", AddCommand.TOOL_TIP
                + "\n\tAdding task: \n\tName:\tmeet akshay\n\tStart Date:\t1pm\n\tPriority:\tmedium");
        assertToolTipBehaviorUndoneList("do cs2103 tests",
                AddCommand.TOOL_TIP + "\n\tAdding task: \n\tName:\tdo cs2103 tests\n\tPriority:\tmedium");
        assertToolTipBehaviorUndoneList("   lolo l ",
                AddCommand.TOOL_TIP + "\n\tAdding task: \n\tName:\tlolo l\n\tPriority:\tmedium");
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfEdit_editTooltip() {
        // simple edit tooltip
        assertToolTipBehaviorUndoneList("e", EditCommand.TOOL_TIP + "\n" + ExitCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("edit", EditCommand.TOOL_TIP);

    }

    @Test
    public void tooltip_detailedEditInput_detailedEditTooltip() {
        // complicated edit tooltip
        assertToolTipBehaviorUndoneList("edit 0",
                EditCommand.TOOL_TIP + "\n\tEditing task at INDEX 0: \n\tName:\tNo Change"
                        + "\n\tStart Date:\tNo Change" + "\n\tEnd Date:\t\tNo Change"
                        + "\n\tRecurrence Rate:\tNo Change" + "\n\tPriority:\tNo Change");
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfClear_clearTooltip() {
        assertToolTipBehaviorUndoneList("c", ClearCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("clear", ClearCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("clear a", ClearCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfDelete_deleteTooltip() {
        assertToolTipBehaviorUndoneList("d", DeleteCommand.TOOL_TIP + "\n" + DoneCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("delete", DeleteCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("delete 100", DeleteCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfDone_doneTooltip() {
        assertToolTipBehaviorUndoneList("d", DeleteCommand.TOOL_TIP + "\n" + DoneCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("done", DoneCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("done 100", DoneCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfExit_exitTooltip() {
        assertToolTipBehaviorUndoneList("e", EditCommand.TOOL_TIP + "\n" + ExitCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("exit", ExitCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("exit 0", ExitCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfFind_findTooltip() {
        assertToolTipBehaviorUndoneList("f", FindCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("find", FindCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("find looking for my favourite task!", FindCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfHelp_helpTooltip() {
        assertToolTipBehaviorUndoneList("h", HelpCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("help", HelpCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("help me please DearJim", HelpCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfList_listTooltip() {
        assertToolTipBehaviorUndoneList("l", ListCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("list", ListCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("list done", ListCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfRedo_redoTooltip() {
        assertToolTipBehaviorUndoneList("r", RedoCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("redo", RedoCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("redo done", RedoCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfSelect_selectTooltip() {
        assertToolTipBehaviorUndoneList("s", SelectCommand.TOOL_TIP + "\n" + StoreCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("select", SelectCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("select 1", SelectCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfUndo_undoTooltip() {
        assertToolTipBehaviorUndoneList("u", UndoCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("undo", UndoCommand.TOOL_TIP);
        assertToolTipBehaviorUndoneList("undo done", UndoCommand.TOOL_TIP);
    }

    /**
     * Sends the inputCommand to the Logic component to generate a tooltip that
     * will be compared against the expectedTooltip, assumes that the user is
     * currently viewing the undone list.
     * 
     * @param userInput the user input
     * @param expectedTooltip expected tool tip to be shown to user
     */
    private void assertToolTipBehaviorUndoneList(String userInput, String expectedToolTip) {
        String generatedToolTip = commandParser.parseForTooltip(userInput, false);
        assertEquals(expectedToolTip, generatedToolTip);
    }

    /**
     * Sends the inputCommand to the Logic component to generate a tooltip that
     * will be compared against the expectedTooltip, assumes that the user is
     * currently viewing the done list.
     * 
     * @param userInput the user input
     * @param expectedTooltip expected tool tip to be shown to user
     */
    private void assertToolTipBehaviorDoneList(String userInput, String expectedToolTip) {
        String generatedToolTip = commandParser.parseForTooltip(userInput, true);
        assertEquals(expectedToolTip, generatedToolTip);
    }
}
