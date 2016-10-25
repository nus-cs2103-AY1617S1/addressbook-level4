package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;

/*
 * Serves as entry point for user input to be parsed into a TaskCommand
 */
//@@author A0143107U
public class TaskCommandsParser {
	/**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private String commandWord;
    private String arguments;
    
    public TaskCommandsParser() {}
    
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public TaskCommand parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectTaskCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Type help if you want to know the list of commands."));
        }
        commandWord = matcher.group("commandWord");
        arguments = matcher.group("arguments").trim();
        CommandParser command =  ParserSelector.getByCommandWord(commandWord);
        return command.prepareCommand(arguments);
    }
    
    /** 
     * Retrieves command word for testing purposes
     * @return command word string
     */
    public String getCommandWord(){
    	return commandWord;
    }
    /** 
     * Retrieves arguments for testing purposes
     * @return arguments string
     */
    public String getArguments(){
    	return arguments;
    }
}
