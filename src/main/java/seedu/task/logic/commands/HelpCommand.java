package seedu.task.logic.commands;

//@@author A0125534L

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.ShowHelpEvent;

/**
 * Format full help instructions for every command for display.
 * 
 */
public class HelpCommand extends Command {

	public final String commandWord;
	public boolean isPopUp;
	public static final String COMMAND_WORD = "help";

	public static final String MESSAGE_USAGE = COMMAND_WORD + "\n"
			+ "Shows program commands usage instructions.\n" 
			+ "Example: " + COMMAND_WORD + "\n\n"  
			+ "List of available commands for help\n" + COMMAND_WORD + " add\n" + COMMAND_WORD + " delete\n"
			+ COMMAND_WORD + " find\n" + COMMAND_WORD + " list\n" + COMMAND_WORD + " select\n" + COMMAND_WORD
			+ " mark\n" + COMMAND_WORD + " undo\n" + COMMAND_WORD + " show\n" + COMMAND_WORD + " save\n" + COMMAND_WORD + " clear\n" + COMMAND_WORD + " exit\n"
			+ "Parameters: help [KEY_WORD]\n"
			+ "Example: "+ COMMAND_WORD + " add\n\n";
	
	public HelpCommand(String commandWord, boolean helpWindowPopUp) { //values passed from help parser
		this.commandWord = commandWord;
		this.isPopUp = helpWindowPopUp;
	}

	@Override
	public CommandResult execute() {

		if (isPopUp == true) { //check if there is a need to have the help popup window
			EventsCenter.getInstance().post(new ShowHelpEvent());
			return new CommandResult(commandWord);
		} else {
			return new CommandResult(commandWord);
		}
	
	}
}
