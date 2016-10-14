package seedu.address.ui;

import java.util.Stack;

public class CommandHistoryManager {
	
	private Stack<String> previousCommands;
	private Stack<String> nextCommands;
	private String firstEnteredCommand;
	private boolean isFirstCommand;
	
	public CommandHistoryManager() {
		previousCommands = new Stack<>();
		nextCommands = new Stack<>();
		firstEnteredCommand = "";
		isFirstCommand = true;
	}
	
	public void rememberCommand(String command) {
		if (isFirstCommand) {
			firstEnteredCommand = command;
			isFirstCommand = false;
		}
		
		previousCommands.push(command);
		nextCommands.clear();
	}
	
	public String getPreviousCommand() {		
		if (previousCommands.isEmpty()) {
			return firstEnteredCommand;
		}
		else {
			String cmd = previousCommands.pop();
			nextCommands.push(cmd);
			return cmd;
		}
	}
	
	public String getNextCommand() {
		if (nextCommands.isEmpty()) {
			return "";
		}
		else {
			return nextCommands.pop();
		}
	}
}
