package seedu.address.logic.commands;

import java.util.Stack;

import seedu.address.model.Undo;

public class CommandHistory {
	
	private static Stack<Command> prevCmd = new Stack<Command>();
	private static Stack<Command> nextCmd = new Stack<Command>();
	private static Stack<Undo> mutateCmd = new Stack<Undo>();
	
	public static void addPrevCmd(Command command) {
		prevCmd.push(command);
		nextCmd.clear();
	}
	
	public static void addNextCmd(Command command) {
		nextCmd.push(command);
	}
	
	public static void addMutateCmd(Undo undo) {
		mutateCmd.push(undo);
	}
	
	public static Command getPrevCmd() {
		Command result = prevCmd.pop();
		nextCmd.push(result);
		return result;
	}

	public static Command getNextCmd() {
		Command result = nextCmd.pop();
		prevCmd.push(result);
		return result;
	}
	
	public static Undo getMutateCmd() {
		return mutateCmd.pop();
	}
}
