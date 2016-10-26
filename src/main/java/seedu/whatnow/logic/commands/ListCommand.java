package seedu.whatnow.logic.commands;
//@@author A0139772U

/**
 * Lists all tasks in WhatNow to the user.
 */
public class ListCommand extends UndoAndRedo{

	public static final String COMMAND_WORD = "list";

	public static final String MESSAGE_SUCCESS = "Listed all tasks";

	public static final String MESSAGE_LIST_NOT_ENTERED = " No previous list command was entered";

	public static final String MESSAGE_LIST_NO_REDO_LIST = " No list command to redo";

	public static final String TASK_STATUS_DONE = "done";
	
	public static final String TASK_STATUS_COMPLETED = "completed";
	
	public static final String TASK_STATUS_ALL = "all";
	
	public static final String TASK_STATUS_INCOMPLETE = "incomplete";
	
	public static final String MESSAGE_USAGE = COMMAND_WORD 
			+ ": the task specified\n"
			+ "Parameters: done\n"
			+ "Examples: " + COMMAND_WORD + " done or " + COMMAND_WORD + " all or " + COMMAND_WORD;

	public String type;

	public ListCommand(String type) {
		this.type = type;
	}

	private void mapInputToCorrectArgumentForExecution() {
		if (type.equals(TASK_STATUS_DONE)) {
			type = TASK_STATUS_COMPLETED;
		} else if (type.equals(TASK_STATUS_ALL)) {
			type = TASK_STATUS_ALL;
		} else {
			type = TASK_STATUS_INCOMPLETE;
		}
	}

	@Override
	public CommandResult execute() {
		mapInputToCorrectArgumentForExecution();
		if (type.equals(TASK_STATUS_ALL)) {
			model.updateFilteredListToShowAll();
			model.updateFilteredScheduleListToShowAll();
			model.getUndoStack().push(this);
			model.getStackOfListTypes().push(TASK_STATUS_ALL);
		} else if (type.equals(TASK_STATUS_INCOMPLETE)) {
			model.updateFilteredListToShowAllIncomplete();
			model.updateFilteredScheduleListToShowAllIncomplete();
			model.getUndoStack().push(this);
			model.getStackOfListTypes().push(TASK_STATUS_INCOMPLETE);
		} else {
			model.updateFilteredListToShowAllCompleted();
			model.updateFilteredScheduleListToShowAllCompleted();
			model.getUndoStack().push(this);
			model.getStackOfListTypes().push(TASK_STATUS_COMPLETED);
		}
		return new CommandResult(MESSAGE_SUCCESS);
	}
	//@@author A0139128A
	@Override
	public CommandResult undo(){
		if(model.getStackOfListTypes().isEmpty()) {
			return new CommandResult(MESSAGE_LIST_NOT_ENTERED); 
		}
		else {
			String prevListCommand = model.getStackOfListTypes().pop();
			model.getStackOfListTypesRedo().push(prevListCommand);
			if(model.getStackOfListTypes().isEmpty()) {
				model.updateFilteredListToShowAllIncomplete();
				model.updateFilteredScheduleListToShowAllIncomplete();
				return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
			}
			else {
				String reqCommandListType = model.getStackOfListTypes().peek();
				if(reqCommandListType.equals(TASK_STATUS_ALL)) {
					model.updateFilteredListToShowAll();
					model.updateFilteredScheduleListToShowAll();
					return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
				}
				else if(reqCommandListType.equals(TASK_STATUS_INCOMPLETE)) {
					model.updateFilteredListToShowAllIncomplete();
					model.updateFilteredScheduleListToShowAllIncomplete();
					return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
				}
				else {
					model.updateFilteredListToShowAllCompleted();
					model.updateFilteredScheduleListToShowAllCompleted();
					return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
				}
			}
		}
	}
	
	//@@author A0139128A
	@Override
	public CommandResult redo() {
		if(model.getStackOfListTypesRedo().isEmpty()) {
			return new CommandResult(MESSAGE_LIST_NO_REDO_LIST);
		}
		else {
			String prevCommandListType = model.getStackOfListTypesRedo().pop();
			model.getStackOfListTypes().push(prevCommandListType);
			if(prevCommandListType.equals(TASK_STATUS_ALL)) {
				model.updateFilteredListToShowAll();
				model.updateFilteredScheduleListToShowAll();
				return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
			}
			else if(prevCommandListType.equals(TASK_STATUS_INCOMPLETE)) {
				model.updateFilteredListToShowAllIncomplete();
				model.updateFilteredScheduleListToShowAllIncomplete();
				return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
			}
			else {
				model.updateFilteredListToShowAllCompleted();
				model.updateFilteredScheduleListToShowAllCompleted();
				return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
			}
		}
	}
}
