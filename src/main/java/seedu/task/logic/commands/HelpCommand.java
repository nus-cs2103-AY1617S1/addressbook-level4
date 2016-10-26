package seedu.task.logic.commands;


import seedu.task.commons.events.ui.ShowHelpEvent;
import seedu.taskcommons.core.EventsCenter;

/**
 * Format full help instructions for every command for display.
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
			+ " mark\n" + COMMAND_WORD + " undo\n" + COMMAND_WORD +" clear\n" + COMMAND_WORD + " exit\n"
			+ "Parameters: help [KEY_WORD]\n"
			+ "Example: "+ COMMAND_WORD + " add\n\n";
	

	public HelpCommand(String commandWord, boolean helpWindowPopUp) {
		// TODO Auto-generated constructor stub
		this.commandWord = commandWord;
		this.isPopUp = helpWindowPopUp;
	}

	@Override
	public CommandResult execute() {

		if (isPopUp == true) {
			EventsCenter.getInstance().post(new ShowHelpEvent());
			return new CommandResult(commandWord);
		} else {
			return new CommandResult(commandWord);
		}
	
	}
}
