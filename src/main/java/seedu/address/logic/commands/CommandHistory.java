package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

//@@author A0138978E
/*
 * Stores all previously entered commands (in textual form) and allows for specific access to previous/next commands
 */
public class CommandHistory {

	// Stores all the previous commands
	private List<String> commandHistoryList = new ArrayList<String>();
	
	// Index into the list to return previous/next commands
	private int listPointer = -1;
	
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

	private void boundListPointer() {
		// Keep listPointer within the list bounds
		if (listPointer < 0) {
			listPointer = 0;
		} else if (listPointer >= commandHistoryList.size()) {
			listPointer = commandHistoryList.size() - 1;
		}
	}
	
	
}
