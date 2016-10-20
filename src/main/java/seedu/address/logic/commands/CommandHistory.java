package seedu.address.logic.commands;

import java.util.EmptyStackException;
import java.util.Stack;

import seedu.address.model.Undo;

public class CommandHistory {
	
	private static Stack<String> prevCmd = new Stack<String>();
	private static Stack<String> nextCmd = new Stack<String>();
	private static Stack<Undo> mutateCmd = new Stack<Undo>();
	
	public static void addPrevCmd(String commandText) {
		while (!nextCmd.isEmpty()) {
			prevCmd.push(nextCmd.pop());
		}
		prevCmd.push(commandText);
	}
	
	public static void addNextCmd(String commandText) {
		nextCmd.push(commandText);
	}
	
	
	public static String getPrevCmd() {
		String result = "";
		if (prevCmd.size() > 0) {
			if (prevCmd.size() == 1) {
				result = prevCmd.peek();
			} else {
				result = prevCmd.pop();
				nextCmd.push(result);
			}
		}
		return result;
	}

	public static String getNextCmd() {
		String result = "";
		if (!nextCmd.isEmpty()) {
			result = nextCmd.pop();
			prevCmd.push(result);
		}
		return result;
	}

	public static void addMutateCmd(Undo undo) {
		mutateCmd.push(undo);
	}
	
	public static Undo getMutateCmd() throws EmptyStackException{
		if (mutateCmd.size() > 0)
			return mutateCmd.pop();
		else
			throw new EmptyStackException();
	}
	
	public static void flushMutateCmd() {
		mutateCmd.clear();
	}
}
