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

    public static final String COMMAND_DESCRIPTION = "Shows program usage instructions";
    
    public static final String COMMAND_SUMMARY = "Getting help:"
            + "\n" + COMMAND_WORD;
    
    private ArrayList<String> commandList;
    private ArrayList<String> formatList;
    private ArrayList<String> descriptionList;
    
    public static final String SUCCESSFULLY_SHOWN = "Command summary displayed.";

    public HelpCommand() {
        initInfo();
    }

    private void initInfo() {
        if (commandList == null || formatList == null || descriptionList == null) {
            initCommandWords();
            initFormat();
            initDescription();
        }
    }
    
    private void initCommandWords() {
        commandList = new ArrayList<String>();
        commandList.add(AddCommand.COMMAND_KEYWORD_ADD + ", " + AddCommand.COMMAND_KEYWORD_DO);
        commandList.add(EditCommand.COMMAND_KEYWORD_EDIT + ", " 
        + EditCommand.COMMAND_KEYWORD_UPDATE + ", "
        + EditCommand.COMMAND_KEYWORD_CHANGE);
        commandList.add(UndoCommand.COMMAND_WORD);
        commandList.add(RedoCommand.COMMAND_WORD);
        commandList.add(MarkCommand.COMMAND_WORD);
        commandList.add(UnmarkCommand.COMMAND_WORD);
        commandList.add(DeleteCommand.COMMAND_WORD);
        commandList.add(ClearCommand.COMMAND_WORD);
        commandList.add(FindCommand.COMMAND_WORD);
        commandList.add(ListCommand.COMMAND_WORD);
        commandList.add(UpcomingCommand.COMMAND_WORD);
        commandList.add(RelocateCommand.COMMAND_WORD);
        commandList.add(ImportCommand.COMMAND_WORD);
        commandList.add(ExportCommand.COMMAND_KEYWORD_EXPORT);
        commandList.add(ActionHistoryCommand.COMMAND_KEYWORD_ACTIONHISTORY);
        commandList.add(HelpCommand.COMMAND_WORD);
        commandList.add(ExitCommand.COMMAND_WORD);
    }
    
    private void initFormat() {
        formatList = new ArrayList<String>();
        formatList.add(AddCommand.COMMAND_FORMAT);
        formatList.add(EditCommand.COMMAND_FORMAT);
        formatList.add(UndoCommand.COMMAND_WORD);
        formatList.add(RedoCommand.COMMAND_WORD);
        formatList.add(MarkCommand.COMMAND_FORMAT);
        formatList.add(UnmarkCommand.COMMAND_FORMAT);
        formatList.add(DeleteCommand.COMMAND_FORMAT);
        formatList.add(ClearCommand.COMMAND_WORD);
        formatList.add(FindCommand.COMMAND_FORMAT);
        formatList.add(ListCommand.COMMAND_FORMAT);
        formatList.add(UpcomingCommand.COMMAND_FORMAT);
        formatList.add(RelocateCommand.COMMAND_FORMAT);
        formatList.add(ImportCommand.COMMAND_FORMAT);
        formatList.add(ExportCommand.COMMAND_FORMAT);
        formatList.add(ActionHistoryCommand.COMMAND_KEYWORD_ACTIONHISTORY);
        formatList.add(HelpCommand.COMMAND_WORD);
        formatList.add(ExitCommand.COMMAND_WORD);
    }
    
    private void initDescription() {
        descriptionList = new ArrayList<String>();
        descriptionList.add(AddCommand.COMMAND_DESCRIPTION);
        descriptionList.add(EditCommand.COMMAND_DESCRIPTION);
        descriptionList.add(UndoCommand.COMMAND_DESCRIPTION);
        descriptionList.add(RedoCommand.COMMAND_DESCRIPTION);
        descriptionList.add(MarkCommand.COMMAND_DESCRIPTION);
        descriptionList.add(UnmarkCommand.COMMAND_DESCRIPTION);
        descriptionList.add(DeleteCommand.COMMAND_DESCRIPTION);
        descriptionList.add(ClearCommand.COMMAND_DESCRIPTION);
        descriptionList.add(FindCommand.COMMAND_DESCRIPTION);
        descriptionList.add(ListCommand.COMMAND_DESCRIPTION);
        descriptionList.add(UpcomingCommand.COMMAND_DESCRIPTION);
        descriptionList.add(RelocateCommand.COMMAND_DESCRIPTION);
        descriptionList.add(ImportCommand.COMMAND_DESCRIPTION);
        descriptionList.add(ExportCommand.COMMAND_DESCRIPTION);
        descriptionList.add(ActionHistoryCommand.COMMAND_DESCRIPTION);
        descriptionList.add(HelpCommand.COMMAND_DESCRIPTION);
        descriptionList.add(ExitCommand.COMMAND_DESCRIPTION);
    }
    
    //@@author A0139194X
    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent(commandList, formatList, descriptionList));
        return new CommandResult(COMMAND_WORD, SUCCESSFULLY_SHOWN);
    }

}
