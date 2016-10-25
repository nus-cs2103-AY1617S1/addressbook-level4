package seedu.whatnow.logic.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.whatnow.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Lists all tasks in WhatNow to the user.
 */
public class ListCommand extends UndoAndRedo{

	public static final String COMMAND_WORD = "list";

	public static final String MESSAGE_SUCCESS = "Listed all tasks";

	public static final String MESSAGE_LIST_NOT_ENTERED = " No previous list command was entered";

	public static final String MESSAGE_LIST_NO_REDO_LIST = " No list command to redo";
	
	public static final String MESSAGE_USAGE = COMMAND_WORD 
			+ ": the task specified\n"
			+ "Parameters: done\n"
			+ "Examples: " + COMMAND_WORD + " done or " + COMMAND_WORD + " all or " + COMMAND_WORD;

	public String type;

	public ListCommand(String type) {
		this.type = type;
	}

	private void mapInputToCorrectArgumentForExecution() {
		if (type.equals("done")) {
			type = "completed";
		} else if (type.equals("all")) {
			type = "all";
		} else {
			type = "incomplete";
		}
	}

	@Override
	public CommandResult execute() {
		mapInputToCorrectArgumentForExecution();
		if (type.equals("all")) {
			model.updateFilteredListToShowAll();
			model.updateFilteredScheduleListToShowAll();
			model.getUndoStack().push(this);
			model.getStackOfListTypes().push("all");
		} else if (type.equals("incomplete")) {
			model.updateFilteredListToShowAllIncomplete();
			model.updateFilteredScheduleListToShowAllIncomplete();
			model.getUndoStack().push(this);
			model.getStackOfListTypes().push("incomplete");
		} else {
			model.updateFilteredListToShowAllCompleted();
			model.updateFilteredScheduleListToShowAllCompleted();
			model.getUndoStack().push(this);
			model.getStackOfListTypes().push("completed");
		}
		return new CommandResult(MESSAGE_SUCCESS);
	}

	@Override
	public CommandResult undo(){
		if(model.getStackOfListTypes().isEmpty()) {
			return new CommandResult(MESSAGE_LIST_NOT_ENTERED); 
		}
		else {
			String prevListCommand = model.getStackOfListTypes().pop();
			model.getStackOfListTypesRedo().push(prevListCommand);
			if(model.getStackOfListTypes().isEmpty()) {
				System.out.println("Undo first if condition");
				model.updateFilteredListToShowAllIncomplete();
				model.updateFilteredScheduleListToShowAllIncomplete();
				return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
			}
			else {
				String reqCommandListType = model.getStackOfListTypes().peek();
				if(reqCommandListType.equals("all")) {
					System.out.println("Undo second if condition");
					model.updateFilteredListToShowAll();
					model.updateFilteredScheduleListToShowAll();
					return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
				}
				else if(reqCommandListType.equals("incomplete")) {
					System.out.println("Undo third if conditon");
					model.updateFilteredListToShowAllIncomplete();
					model.updateFilteredScheduleListToShowAllIncomplete();
					return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
				}
				else {
					System.out.println("Undo forth if condition");
					model.updateFilteredListToShowAllCompleted();
					model.updateFilteredScheduleListToShowAllCompleted();
					return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
				}
			}
		}
	}

	@Override
	public CommandResult redo() {
		if(model.getStackOfListTypesRedo().isEmpty()) {
			return new CommandResult(MESSAGE_LIST_NO_REDO_LIST);
		}
		else {
			String prevCommandListType = model.getStackOfListTypesRedo().pop();
			model.getStackOfListTypes().push(prevCommandListType);
			if(prevCommandListType.equals("all")) {
				System.out.println("Redo second condition");
				model.updateFilteredListToShowAll();
				model.updateFilteredScheduleListToShowAll();
				return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
			}
			else if(prevCommandListType.equals("incomplete")) {
				System.out.println("Redo Third condition");
				model.updateFilteredListToShowAllIncomplete();
				model.updateFilteredScheduleListToShowAllIncomplete();
				return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
			}
			else {
				System.out.println("Redo Forth condition");
				model.updateFilteredListToShowAllCompleted();
				model.updateFilteredScheduleListToShowAllCompleted();
				return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
			}
			
			/*if(model.getStackOfListTypesRedo().isEmpty()) {
				System.out.println("Redo first condition");
				model.updateFilteredListToShowAllIncomplete();
				model.updateFilteredScheduleListToShowAllIncomplete();
				return new CommandResult(RedoCommand.MESSAGE_SUCCESS);
			}
			else {
				String reqCommandListType = model.getStackOfListTypesRedo().peek();
				if(reqCommandListType.equals("all")) {
					System.out.println("Redo second condition");
					model.updateFilteredListToShowAll();
					model.updateFilteredScheduleListToShowAll();
					return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
				}
				else if(reqCommandListType.equals("incomplete")) {
					System.out.println("Redo Third condition");
					model.updateFilteredListToShowAllIncomplete();
					model.updateFilteredScheduleListToShowAllIncomplete();
					return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
				}
				else {
					System.out.println("Redo Forth condition");
					model.updateFilteredListToShowAllCompleted();
					model.updateFilteredScheduleListToShowAllCompleted();
					return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
				}
			}*/
		}
	}
}
