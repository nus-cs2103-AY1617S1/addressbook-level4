package harmony.mastermind.logic.commands;


import harmony.mastermind.commons.core.EventsCenter;
import harmony.mastermind.commons.events.ui.ShowHelpRequestEvent;

/**@@author A0139194X
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;
    
    public static final String COMMAND_SUMMARY = "Getting help:"
            + "\n" + COMMAND_WORD;
    
    public static final String SHOWING_HELP_MESSAGE = "===Command Summary==="
            + "\n" + AddCommand.COMMAND_FORMAT + "\n"
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
    
    public static final String SUCCESSFULLY_SHOWN = "Command summary displayed.";

    public HelpCommand() {}

    //@@author A0139194X
    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent(SHOWING_HELP_MESSAGE));
        return new CommandResult(COMMAND_WORD, SUCCESSFULLY_SHOWN);
    }
}
