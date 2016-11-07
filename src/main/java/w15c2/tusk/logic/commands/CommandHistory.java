package w15c2.tusk.logic.commands;

import java.util.ArrayList;
import java.util.List;

//@@author A0138978E
/**
 * Stores all previously entered commands (in textual form) and allows for specific access to previous/next commands
 */
public class CommandHistory {

	// Stores all the previous commands
	private List<String> commandHistoryList = new ArrayList<String>();
	
	// Index into the list to return previous/next commands
	private int listPointer = -1;
	
	/**
	 * Adds a new command to the historical list of commands
	 * @param commandText the text of the command to be added
	 */
	public void addCommandTextToHistory(String commandText) {
		assert commandHistoryList != null;
		assert commandText != null;
		
		commandHistoryList.add(commandText);
		
		// Reset the pointer to just after the latest element
		listPointer = commandHistoryList.size();
	}
	
	public String getPreviousCommand() {
		// Go back by one command
		listPointer--;
		
		return getCommandByListPointer();
	}
	
	public String getNextCommand() {
		// Go forward by one command
		listPointer++;
		
		return getCommandByListPointer();
	}
	
	/**
	 * Gets a command from the command history based on the current list pointer
	 * @return a command from the command history
	 */
	private String getCommandByListPointer() {
		assert commandHistoryList != null;
		// Ensure that the list pointer isn't outside the bounds of the list 
		boundListPointer();
		
		// Handle edge case of no elements in list
		if (commandHistoryList.isEmpty()) {
			// Return a blank command
			return "";
		} else {
			return commandHistoryList.get(listPointer);
		}
	}

	/**
	 * Forces the list pointer to stay within the bounds of the list
	 */
	private void boundListPointer() {
		if (listPointer < 0) {
			listPointer = 0;
		} else if (listPointer >= commandHistoryList.size()) {
			listPointer = commandHistoryList.size() - 1;
		}
	}
	
	
}
