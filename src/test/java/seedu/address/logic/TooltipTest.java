package seedu.address.logic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seedu.address.commons.core.EventsCenter;
import seedu.address.logic.commands.*;
import seedu.address.logic.parser.CommandParser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.*;

//@@author A0093960X
public class TooltipTest {

    private static final String NEWLINE = "\n";
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
        assertTooltipBehaviorUndoneList("   ", MESSAGE_TOOLTIP_INVALID_COMMAND_FORMAT);
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
    public void tooltip_undoneListCommandBeginningSubstringsOfAdd_addTooltip() {
        assertTooltipBehaviorUndoneList("a", AddCommand.TOOL_TIP);
        assertTooltipBehaviorUndoneList("add", AddCommand.TOOL_TIP);
        assertTooltipBehaviorUndoneList("    add", AddCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_undoneListDetailedAddInput_detailedAddTooltip() {
        assertTooltipBehaviorUndoneList("add Meet Hoon Meier from 2pm to 3pm repeat every month",
                buildAddTooltip("Meet Hoon Meier", "medium", "every month", "2pm", "3pm"));
        assertTooltipBehaviorUndoneList("meet akshay from today to tomorrow repeat every 3 days -high",
                buildAddTooltip("meet akshay", "high", "every 3 days", "today", "tomorrow"));
        assertTooltipBehaviorUndoneList("\"edit my essay by today\" by today -high",
                buildAddTooltip("edit my essay by today", "high", null, null, "today"));
    }

    @Test
    public void tooltip_undoneListCommandBeginningSubstringsOfEdit_editTooltip() {
        assertTooltipBehaviorUndoneList("edit", EditCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_detailedEditInput_detailedEditTooltip() {
        assertTooltipBehaviorUndoneList("edit 3 Call the school to ask something from 9am to 10am repeat every day -low",
                buildEditTooltip("3", "Call the school to ask something", "low", "every day", "9am", "10am"));
        assertTooltipBehaviorUndoneList("edit 100 repeat every 2 days -high -reset start",
                buildEditTooltip("100", "No Change", "high", "every 2 days", "RESET", "No Change"));
        assertTooltipBehaviorUndoneList("edit 342 from 1am -reset start end priority",
                buildEditTooltip("342", "No Change", "RESET", "No Change", "RESET", "RESET"));
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfClear_clearTooltip() {
        assertTooltipBehaviorUndoneList("c", ClearCommand.TOOL_TIP);
        assertTooltipBehaviorUndoneList("clear", ClearCommand.TOOL_TIP);
        assertTooltipBehaviorUndoneList("clear a", ClearCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfDelete_deleteTooltip() {
        assertTooltipBehaviorUndoneList("delete", DeleteCommand.TOOL_TIP);
        assertTooltipBehaviorUndoneList("delete 100", DeleteCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfDone_doneTooltip() {
        assertTooltipBehaviorUndoneList("done", DoneCommand.TOOL_TIP);
        assertTooltipBehaviorUndoneList("done 100", DoneCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfExit_exitTooltip() {
        assertTooltipBehaviorUndoneList("exit", ExitCommand.TOOL_TIP);
        assertTooltipBehaviorUndoneList("exit 0", ExitCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfFind_findTooltip() {
        assertTooltipBehaviorUndoneList("f", FindCommand.TOOL_TIP);
        assertTooltipBehaviorUndoneList("find", FindCommand.TOOL_TIP);
        assertTooltipBehaviorUndoneList("find looking for my favourite task!", FindCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfHelp_helpTooltip() {
        assertTooltipBehaviorUndoneList("h", HelpCommand.TOOL_TIP);
        assertTooltipBehaviorUndoneList("help", HelpCommand.TOOL_TIP);
        assertTooltipBehaviorUndoneList("help me please DearJim", HelpCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfList_listTooltip() {
        assertTooltipBehaviorUndoneList("l", ListCommand.TOOL_TIP);
        assertTooltipBehaviorUndoneList("list", ListCommand.TOOL_TIP);
        assertTooltipBehaviorUndoneList("list done", ListCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfRedo_redoTooltip() {
        assertTooltipBehaviorUndoneList("r", RedoCommand.TOOL_TIP);
        assertTooltipBehaviorUndoneList("redo", RedoCommand.TOOL_TIP);
        assertTooltipBehaviorUndoneList("redo done", RedoCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfSelect_selectTooltip() {
        assertTooltipBehaviorUndoneList("select", SelectCommand.TOOL_TIP);
        assertTooltipBehaviorUndoneList("select 1", SelectCommand.TOOL_TIP);
    }
    
    @Test
    public void tooltip_commandBeginningSubstringsOfStore_storeTooltip() {
        assertTooltipBehaviorUndoneList("store", StoreCommand.TOOL_TIP);
        assertTooltipBehaviorUndoneList("store TodoList", StoreCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_commandBeginningSubstringsOfUndo_undoTooltip() {
        assertTooltipBehaviorUndoneList("u", UndoCommand.TOOL_TIP);
        assertTooltipBehaviorUndoneList("undo", UndoCommand.TOOL_TIP);
        assertTooltipBehaviorUndoneList("undo done", UndoCommand.TOOL_TIP);
    }

    @Test
    public void tooltip_ambiguousInput_multipleTooltips() {
        assertTooltipBehaviorUndoneList("d", String.join(NEWLINE, DeleteCommand.TOOL_TIP, DoneCommand.TOOL_TIP));
        assertTooltipBehaviorUndoneList("e", String.join(NEWLINE, EditCommand.TOOL_TIP, ExitCommand.TOOL_TIP));
        assertTooltipBehaviorUndoneList("s", String.join(NEWLINE, SelectCommand.TOOL_TIP, StoreCommand.TOOL_TIP));
    }

    /**
     * Sends the inputCommand to the Logic component to generate a tooltip that
     * will be compared against the expectedTooltip, assumes that the user is
     * currently viewing the undone list.
     * 
     * @param userInput the user input
     * @param expectedTooltip expected tool tip to be shown to user
     */
    private void assertTooltipBehaviorUndoneList(String userInput, String expectedToolTip) {
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

    /**
     * Helper method to build the add tooltip for testing purposes If a
     * parameter is passed null, that field will be assumed to be absent from
     * the tooltip.
     * 
     * @param name the String that will appear in the name field
     * @param priority the String that will appear in the priority field
     * @param recurrence the String that will appear in the recurrence field
     * @param startDate the String that will appear in the start date field
     * @param endDate the String that will appear in the end date field
     */
    private String buildAddTooltip(String name, String priority, String recurrence, String startDate, String endDate) {
        StringBuilder sb = new StringBuilder();
        sb.append(AddCommand.TOOL_TIP);
        sb.append("\n\tAdding task: ");
        sb.append("\n\tName:\t" + name);

        if (startDate != null) {
            sb.append("\n\tStart Date:\t" + startDate);
        }
        if (endDate != null) {
            sb.append("\n\tEnd Date:\t\t" + endDate);
        }
        if (recurrence != null) {
            sb.append("\n\tRecurrence Rate:\t" + recurrence);
        }
        sb.append("\n\tPriority:\t" + priority);

        return sb.toString();
    }

    /**
     * Helper method to build the edit tooltip for testing purposes.
     * 
     * @param index the String that will appear in the index field
     * @param name the String that will appear in the name field
     * @param priority the String that will appear in the priority field
     * @param recurrence the String that will appear in the recurrence field
     * @param startDate the String that will appear in the start date field
     * @param endDate the String that will appear in the end date field
     */
    private String buildEditTooltip(String index, String name, String priority, String recurrence, String startDate,
            String endDate) {
        StringBuilder sb = new StringBuilder();
        sb.append(EditCommand.TOOL_TIP);
        sb.append("\n\tEditing task at INDEX " + index + ": ");
        sb.append("\n\tName:\t" + name);
        sb.append("\n\tStart Date:\t" + startDate);
        sb.append("\n\tEnd Date:\t\t" + endDate);
        sb.append("\n\tRecurrence Rate:\t" + recurrence);
        sb.append("\n\tPriority:\t" + priority);

        return sb.toString();
    }
}
