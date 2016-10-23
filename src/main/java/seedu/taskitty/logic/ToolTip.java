package seedu.taskitty.logic;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.taskitty.commons.core.LogsCenter;
import seedu.taskitty.logic.commands.AddCommand;
import seedu.taskitty.logic.commands.ClearCommand;
import seedu.taskitty.logic.commands.Command;
import seedu.taskitty.logic.commands.DeleteCommand;
import seedu.taskitty.logic.commands.DoneCommand;
import seedu.taskitty.logic.commands.EditCommand;
import seedu.taskitty.logic.commands.ExitCommand;
import seedu.taskitty.logic.commands.FindCommand;
import seedu.taskitty.logic.commands.HelpCommand;
import seedu.taskitty.logic.commands.UndoCommand;
import seedu.taskitty.logic.commands.ViewCommand;

import static seedu.taskitty.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

//@@author A0139930B
public class ToolTip {
    private final Logger logger = LogsCenter.getLogger(ToolTip.class);
    
    public static final String TOOLTIP_DELIMITER = " | ";
    private static final int COMMAND_WORD_POSITION = 0;
    private static final String COMMAND_WORD_DELIMITER = " ";
    
    private static final int COMMAND_WORD_COUNT_NO_MATCH = 0;
    private static final int COMMAND_WORD_COUNT_SINGLE_MATCH = 1;
    
    private FilteredList<String> commands;
    
    private static ToolTip tooltip;
    
    private ToolTip() {
        ObservableList<String> commandList = FXCollections.observableArrayList();
        commandList.addAll(Command.ALL_COMMAND_WORDS);
        commands = commandList.filtered(null);
    }
    
    /**
     * Gets the instance of tooltip to be used
     */
    public static ToolTip getInstance() {
        if (tooltip == null) {
            tooltip = new ToolTip();
        }
        return tooltip;
    }
    
    /**
     * Get the tooltip based on input
     * 
     * @param input to determine the tooltip to be shown
     */
    public String getToolTip(String input) {
        String[] splitInput = input.split(COMMAND_WORD_DELIMITER);
        
        //only interested in the first word
        String command = splitInput[COMMAND_WORD_POSITION];
        
        //filter the commands list to show only commands that match
        commands.setPredicate(p -> p.startsWith(command));
        
        String tooltip;
        if (!isCommandWordMatch()) {
            tooltip = MESSAGE_UNKNOWN_COMMAND;
        } else if (isSingleMatchFound()) {
            tooltip = getMatchCommandToolTipSingle(command);
        } else {
            tooltip = getMatchCommandToolTipAll();
        }
        
        return tooltip;
    }
    
    /**
     * Returns true if there is at least 1 command word that matches
     */
    private boolean isCommandWordMatch() {
        if (commands.size() != COMMAND_WORD_COUNT_NO_MATCH) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns true if there is exactly 1 command word that matches
     */
    private boolean isSingleMatchFound() {
        if (commands.size() == COMMAND_WORD_COUNT_SINGLE_MATCH) {
            return true;
        }
        return false;
    }
    
    /**
     * Finds the closest matching command and returns the appropriate tooltip
     *  
     * @param command to determine which command tooltip to show
     */
    private String getMatchCommandToolTipSingle(String command) {
        String tooltip;
        
        if (AddCommand.COMMAND_WORD.startsWith(command)) {
            tooltip = AddCommand.MESSAGE_USAGE;
        } else if (ViewCommand.COMMAND_WORD.startsWith(command)) {
            tooltip = ViewCommand.MESSAGE_USAGE;
        } else if (FindCommand.COMMAND_WORD.startsWith(command)) {
            tooltip = FindCommand.MESSAGE_USAGE;
        } else if (EditCommand.COMMAND_WORD.startsWith(command)) {
            tooltip = EditCommand.MESSAGE_USAGE;
        } else if (DeleteCommand.COMMAND_WORD.startsWith(command)) {
            tooltip = DeleteCommand.MESSAGE_USAGE;
        } else if (DoneCommand.COMMAND_WORD.startsWith(command)) {
            tooltip = DoneCommand.MESSAGE_USAGE;
        } else if (UndoCommand.COMMAND_WORD.startsWith(command)) {
            tooltip = UndoCommand.MESSAGE_USAGE;
        } else if (ClearCommand.COMMAND_WORD.startsWith(command)) {
            tooltip = ClearCommand.MESSAGE_USAGE;
        } else if (HelpCommand.COMMAND_WORD.startsWith(command)) {
            tooltip = HelpCommand.MESSAGE_USAGE;
        } else if (ExitCommand.COMMAND_WORD.startsWith(command)) {
            tooltip = ExitCommand.MESSAGE_USAGE;
        } else {
            tooltip = MESSAGE_UNKNOWN_COMMAND;
        }
        return tooltip;
    }
    
    /**
     * Returns a string representing the matched input, delimitered by TOOLTIP_DELIMITER
     */
    private String getMatchCommandToolTipAll() {
        assert commands.size() != COMMAND_WORD_COUNT_NO_MATCH
                && commands.size() != COMMAND_WORD_COUNT_SINGLE_MATCH;
        
        StringBuilder builder = new StringBuilder();
        
        builder.append(commands.get(0));
        for (int i = 1; i < commands.size(); i++) {
            builder.append(TOOLTIP_DELIMITER + commands.get(i));
        }
        
        return builder.toString();
    }
}
