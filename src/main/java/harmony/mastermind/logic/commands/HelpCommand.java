package harmony.mastermind.logic.commands;


import java.util.ArrayList;

import harmony.mastermind.commons.core.EventsCenter;
import harmony.mastermind.commons.events.ui.ShowHelpRequestEvent;
import harmony.mastermind.logic.HelpPopupEntry;

/**@@author A0139194X
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;
    
    public static final String COMMAND_SUMMARY = "Getting help:"
            + "\n" + COMMAND_WORD;
    
    public static final ArrayList<String> commandWords = new ArrayList<String>();
    public static final ArrayList<String> format = new ArrayList<String>();
    public static final ArrayList<String> description = new ArrayList<String>();
    
    public static final String SUCCESSFULLY_SHOWN = "Command summary displayed.";

    public HelpCommand() {
        initInfo();
    }

    private void initInfo() {
        initCommandWords();
        initFormat();
        initDescription();
    }
    
    private void initCommandWords() {
        commandWords.add(AddCommand.COMMAND_KEYWORD_ADD + ", " + AddCommand.COMMAND_KEYWORD_DO);
        commandWords.add(EditCommand.COMMAND_KEYWORD_EDIT + ", " 
        + EditCommand.COMMAND_KEYWORD_UPDATE + ", "
        + EditCommand.COMMAND_KEYWORD_CHANGE);
        commandWords.add(UndoCommand.COMMAND_WORD);
        commandWords.add(RedoCommand.COMMAND_WORD);
        commandWords.add(MarkCommand.COMMAND_WORD);
        commandWords.add(UnmarkCommand.COMMAND_WORD);
        commandWords.add(DeleteCommand.COMMAND_WORD);
        commandWords.add(ClearCommand.COMMAND_WORD);
        commandWords.add(FindCommand.COMMAND_WORD);
        commandWords.add(ListCommand.COMMAND_WORD);
        commandWords.add(UpcomingCommand.COMMAND_WORD);
        commandWords.add(RelocateCommand.COMMAND_WORD);
        commandWords.add(ImportCommand.COMMAND_WORD);
        commandWords.add(ExportCommand.COMMAND_KEYWORD_EXPORT);
        commandWords.add(ActionHistoryCommand.COMMAND_KEYWORD_ACTIONHISTORY);
        commandWords.add(HelpCommand.COMMAND_WORD);
        commandWords.add(ExitCommand.COMMAND_WORD);
    }
    
    private void initFormat() {
        commandWords.add(AddCommand.COMMAND_FORMAT);
        commandWords.add(EditCommand.COMMAND_FORMAT);
        commandWords.add(UndoCommand.COMMAND_WORD);
        commandWords.add(RedoCommand.COMMAND_WORD);
        commandWords.add(MarkCommand.COMMAND_FORMAT);
        commandWords.add(UnmarkCommand.COMMAND_FORMAT);
        commandWords.add(DeleteCommand.COMMAND_FORMAT);
        commandWords.add(ClearCommand.COMMAND_WORD);
        commandWords.add(FindCommand.COMMAND_FORMAT);
        commandWords.add(ListCommand.COMMAND_FORMAT);
        commandWords.add(UpcomingCommand.COMMAND_FORMAT);
        commandWords.add(RelocateCommand.COMMAND_FORMAT);
        commandWords.add(ImportCommand.COMMAND_FORMAT);
        commandWords.add(ExportCommand.COMMAND_FORMAT);
        commandWords.add(HelpCommand.COMMAND_WORD);
        commandWords.add(ExitCommand.COMMAND_WORD);
    }
    
    private void initDescription() {
        
    }
    
    //@@author A0139194X
    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent(SHOWING_HELP_MESSAGE));
        return new CommandResult(COMMAND_WORD, SUCCESSFULLY_SHOWN);
    }

}
