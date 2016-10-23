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
    
    private static final String TOOLTIP_POSSIBLE_COMMANDS = "These are the possible commands, Meow!";
    
    private static ToolTip tooltip;
    
    private FilteredList<String> commands;
    
    private String message;
    private String description;
    
    private ToolTip() {
        ObservableList<String> commandList = FXCollections.observableArrayList();
        commandList.addAll(Command.ALL_COMMAND_WORDS);
        commands = commandList.filtered(null);
        clearToolTip();
    }
    
    /**
     * Gets the instance of ToolTip to be used
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
    public void createToolTip(String input) {
        clearToolTip();
        String[] splitedInput = input.split(COMMAND_WORD_DELIMITER);
        
        //only interested in the first word, which is the command word
        String command = splitedInput[COMMAND_WORD_POSITION];
        
        //filter the commands list to show only commands that match
        commands.setPredicate(p -> p.startsWith(command));
        
        if (!isCommandWordMatch()) {
            setToolTip(MESSAGE_UNKNOWN_COMMAND);
        } else if (isSingleMatchFound()) {
            getMatchCommandToolTipSingle(command);
        } else {
            getMatchCommandToolTipAll();
        }
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
    private void getMatchCommandToolTipSingle(String command) {
        if (AddCommand.COMMAND_WORD.startsWith(command)) {
            
            setToolTip(AddCommand.MESSAGE_PARAMETER, AddCommand.MESSAGE_USAGE);
            
        } else if (ViewCommand.COMMAND_WORD.startsWith(command)) {
            
            setToolTip(ViewCommand.MESSAGE_PARAMETER, ViewCommand.MESSAGE_USAGE);
            
        } else if (FindCommand.COMMAND_WORD.startsWith(command)) {
            
            setToolTip(FindCommand.MESSAGE_PARAMETER, FindCommand.MESSAGE_USAGE);
            
        } else if (EditCommand.COMMAND_WORD.startsWith(command)) {
            
            setToolTip(EditCommand.MESSAGE_PARAMETER, EditCommand.MESSAGE_USAGE);
            
        } else if (DeleteCommand.COMMAND_WORD.startsWith(command)) {
            
            setToolTip(DeleteCommand.MESSAGE_PARAMETER, DeleteCommand.MESSAGE_USAGE);
            
        } else if (DoneCommand.COMMAND_WORD.startsWith(command)) {
            
            setToolTip(DoneCommand.MESSAGE_PARAMETER, DoneCommand.MESSAGE_USAGE);
            
        } else if (UndoCommand.COMMAND_WORD.startsWith(command)) {
            
            setToolTip(UndoCommand.MESSAGE_PARAMETER, UndoCommand.MESSAGE_USAGE);
            
        } else if (ClearCommand.COMMAND_WORD.startsWith(command)) {
            
            setToolTip(ClearCommand.MESSAGE_PARAMETER, ClearCommand.MESSAGE_USAGE);
            
        } else if (HelpCommand.COMMAND_WORD.startsWith(command)) {
            
            setToolTip(HelpCommand.MESSAGE_PARAMETER, HelpCommand.MESSAGE_USAGE);
            
        } else if (ExitCommand.COMMAND_WORD.startsWith(command)) {
            
            setToolTip(ExitCommand.MESSAGE_PARAMETER, ExitCommand.MESSAGE_USAGE);
            
        } else {
            setToolTip(MESSAGE_UNKNOWN_COMMAND);
        }
    }
    
    /**
     * Returns a string representing the matched input, delimitered by TOOLTIP_DELIMITER
     */
    private void getMatchCommandToolTipAll() {
        assert commands.size() != COMMAND_WORD_COUNT_NO_MATCH
                && commands.size() != COMMAND_WORD_COUNT_SINGLE_MATCH;
        
        StringBuilder commandBuilder = new StringBuilder();
        
        commandBuilder.append(commands.get(0));
        for (int i = 1; i < commands.size(); i++) {
            commandBuilder.append(TOOLTIP_DELIMITER + commands.get(i));
        }
        
        setToolTip(commandBuilder.toString(), TOOLTIP_POSSIBLE_COMMANDS);
    }
    
    /**
     * Set the tooltip and description back to empty string
     */
    private void clearToolTip() {
        message = "";
        description = "";
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getDecription() {
        return description;
    }
    
    /**
     * Sets the tooltip to the given parameter and description to blank
     */
    private void setToolTip(String tooltip) {
        setToolTip(tooltip, "");
    }
    
    /**
     * Sets the tooltip and description to the given parameters
     */
    private void setToolTip(String tooltip, String description) {
        this.message = tooltip;
        this.description = description;
    }
}
