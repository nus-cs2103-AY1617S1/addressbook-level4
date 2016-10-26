package seedu.address.ui;

import java.util.Stack;

//@@author A0141019U
public class CommandHistoryManager {
	
	private Stack<String> previousCommands = new Stack<>();;
	private Stack<String> nextCommands = new Stack<>();;
	private String firstEnteredCommand = "";
	private String latestCommand = "";
	private boolean isFirstCommand = true;
	
	public CommandHistoryManager() {}
	
	public void rememberCommand(String command) {
		if (isFirstCommand) {
			firstEnteredCommand = command;
			isFirstCommand = false;
		}
		
		latestCommand = command;
		previousCommands.push(command);
		nextCommands.clear();
	}
	
	public String getPreviousCommand() {		
		if (previousCommands.isEmpty()) {
			return firstEnteredCommand;
		}
		else {
			String cmd = previousCommands.pop();			
			if (!cmd.equals(latestCommand)) {
				nextCommands.push(cmd);
			}
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
