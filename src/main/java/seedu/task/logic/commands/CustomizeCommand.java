package seedu.task.logic.commands;

import java.io.IOException;
import java.util.List;

import seedu.task.commons.core.Config;
import seedu.task.commons.core.Config.DublicatedValueCustomCommandsException;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.storage.StorageManager;

//@@author A0153411W
/**
 * Customize available commands with user's command formats 
 */
public class CustomizeCommand extends Command {

	public static final String COMMAND_WORD = "customize";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add customized format for specified command"
			+ " Parameters: command_format" + "\n Example: " + COMMAND_WORD + " LIST f/ls \n";

	private String commandWord;
	private String userCommand;

	public CustomizeCommand() {
	}
	
	public CustomizeCommand(String commandWord, String userCommand) {
		this.commandWord = commandWord.toLowerCase();
		this.userCommand = userCommand.toLowerCase();
	}

	@Override
	public CommandResult execute() {
		Config config = Config.getInstance();
		String configFilePathUsed = Config.DEFAULT_CONFIG_FILE;
		
		if(commandWord==null && userCommand==null)
			return showCustomCommands(config);
		
		//Check if default command format is available 
		if (!isCommandWordPresent(commandWord))
			return new CommandResult("Command:" + commandWord + " is not found.");
		try {
			config.setCustomCommandFormat(commandWord, userCommand);
			ConfigUtil.saveConfig(config, configFilePathUsed);
			new StorageManager(config.getTaskManagerFilePath(), config.getUserPrefsFilePath());
			return new CommandResult("Add customized format: " + userCommand + " for command: " + commandWord);
		} catch (IOException e) {
			return new CommandResult(
					"Failed to add customized format: " + userCommand + " for command: " + commandWord);
		} catch (DublicatedValueCustomCommandsException e) {
			return new CommandResult(
					"Failed to add customized format: " + userCommand + " for command: " + commandWord);
		}
	}
	
	/**
	 * Show All available custom user's commands
	 */
	private CommandResult showCustomCommands(Config config){
		return new CommandResult(MESSAGE_USAGE+"Current custom commands: \n"+config.getCustomCommands());
	}

	/**
	 * Check if default command format is available 
	 */
	private boolean isCommandWordPresent(String commandWord) {
		List<String> commands = Command.getAllCommands();
		return commands.contains(commandWord);
	}

	@Override
	public CommandResult executeUndo() {
		return null;
	}

	@Override
	public boolean isReversible() {
		return false;
	}

}