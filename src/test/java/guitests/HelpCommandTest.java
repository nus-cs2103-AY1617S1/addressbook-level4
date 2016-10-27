package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.flexitrack.commons.core.EventsCenter;
import seedu.flexitrack.logic.Logic;
import seedu.flexitrack.logic.LogicManager;
import seedu.flexitrack.logic.commands.AddCommand;
import seedu.flexitrack.logic.commands.ClearCommand;
import seedu.flexitrack.logic.commands.CommandResult;
import seedu.flexitrack.logic.commands.DeleteCommand;
import seedu.flexitrack.logic.commands.EditCommand;
import seedu.flexitrack.logic.commands.ExitCommand;
import seedu.flexitrack.logic.commands.FindCommand;
import seedu.flexitrack.logic.commands.HelpCommand;
import seedu.flexitrack.logic.commands.ListCommand;
import seedu.flexitrack.logic.commands.MarkCommand;
import seedu.flexitrack.logic.commands.SelectCommand;
import seedu.flexitrack.logic.commands.UnmarkCommand;
import seedu.flexitrack.model.Model;
import seedu.flexitrack.model.ModelManager;
import seedu.flexitrack.storage.StorageManager;
//@@author A0138455Y
public class HelpCommandTest extends FlexiTrackGuiTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    @Before
    public void setup() {
        model = new ModelManager();
        String tempFlexiTrackerFile = saveFolder.getRoot().getPath() + "TempFlexiTracker.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempFlexiTrackerFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);
    }

    @After
    public void teardown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void execute_help_add() throws Exception {
        // help for add command
        String help_Add = "help add";
        assertValidHelpSuccess(help_Add, AddCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_help_clear() throws Exception {
        // help for clear command
        String help_clear = "help clear";
        assertValidHelpSuccess(help_clear, ClearCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_help_delete() throws Exception {
        // help for delete command
        String help_Delete = "help delete";
        assertValidHelpSuccess(help_Delete, DeleteCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_help_edit() throws Exception {
        // help for edit command
        String help_Edit = "help edit";
        assertValidHelpSuccess(help_Edit, EditCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_help_exit() throws Exception {
        // help for Exit command
        String help_Exit = "help exit";
        assertValidHelpSuccess(help_Exit, ExitCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_help_find() throws Exception {
        // help for find command
        String help_Find = "help find";
        assertValidHelpSuccess(help_Find, FindCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_help_list() throws Exception {
        // help for list command
        String help_List = "help list";
        assertValidHelpSuccess(help_List, ListCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_help_mark() throws Exception {
        // help for mark command
        String help_Mark = "help mark";
        assertValidHelpSuccess(help_Mark, MarkCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_help_select() throws Exception {
        // help for select command
        String help_Select = "help select";
        assertValidHelpSuccess(help_Select, SelectCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_help_unmark() throws Exception {
        // help for unmark command
        String help_Unmark = "help unmark";
        assertValidHelpSuccess(help_Unmark, UnmarkCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_invalid_help() throws Exception {
        // help for invalid command
        String help_Invalid = "help addasd";
        assertInvalidHelpCommandSuccess(help_Invalid, HelpCommand.HELP_MESSAGE_USAGE);

        // help for second invalid command
        String help_Invalid2 = "help :<14afa";
        assertInvalidHelpCommandSuccess(help_Invalid2, HelpCommand.HELP_MESSAGE_USAGE);

    }

    private void assertValidHelpSuccess(String inputCommand, String expectedMessage) {
        // Execute the command
        CommandResult result = logic.execute(inputCommand);

        // Confirm the ui display elements should contain the right data;
        assertEquals(expectedMessage, result.feedbackToUser);
    }

    private void assertInvalidHelpCommandSuccess(String inputCommand, String expectedMessage) {
        // Execute the command
        CommandResult result = logic.execute(inputCommand);

        // Confirm the ui display elements should contain the right data;
        assertEquals(expectedMessage, result.feedbackToUser);
    }

}
