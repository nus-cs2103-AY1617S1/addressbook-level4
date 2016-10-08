package harmony.mastermind.logic.commands;


import harmony.mastermind.commons.core.EventsCenter;
import harmony.mastermind.commons.events.ui.ShowHelpRequestEvent;
import seedu.addressbook.commands.AddCommand;
import seedu.addressbook.commands.ClearCommand;
import seedu.addressbook.commands.DeleteCommand;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.commands.FindCommand;
import seedu.addressbook.commands.HelpCommand;
import seedu.addressbook.commands.ListCommand;
import seedu.addressbook.commands.ViewAllCommand;
import seedu.addressbook.commands.ViewCommand;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;
    
    public static final String COMMAND_SUMMARY = "Getting help:"
            + "\n" + COMMAND_WORD;
    
    public static final String SHOWING_HELP_MESSAGE = "===Command Summary==="
            + "\n" + AddCommand.COMMAND_SUMMARY + "\n"
            + "\n" + EditCommand.COMMAND_SUMMARY + "\n"
            + "\n" + UndoCommand.COMMAND_SUMMARY + "\n"
            + "\n" + MarkCommand.COMMAND_SUMMARY + "\n"
            + "\n" + DeleteCommand.COMMAND_SUMMARY + "\n"
            + "\n" + ClearCommand.COMMAND_SUMMARY + "\n"
            + "\n" + FindCommand.COMMAND_SUMMARY + "\n"
            + "\n" + ListCommand.COMMAND_SUMMARY + "\n"
            + "\n" + HelpCommand.COMMAND_SUMMARY + "\n"
            + "\n" + ExitCommand.COMMAND_SUMMARY + "\n"
            + "====================";

    public HelpCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
}
