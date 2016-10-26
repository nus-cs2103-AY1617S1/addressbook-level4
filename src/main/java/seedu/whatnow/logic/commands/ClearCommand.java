package seedu.whatnow.logic.commands;

import java.util.Stack;

import seedu.whatnow.model.WhatNow;

/**
 * Clears WhatNow.
 */
public class ClearCommand extends UndoAndRedo {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "WhatNow has been cleared!";
    
    public static Stack<WhatNow> reqStack;
    public ClearCommand() {}

    @Override
    public CommandResult execute() {
        assert model != null;
        
        model.resetData(WhatNow.getEmptyWhatNow());
        model.getUndoStack().push(this);
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    //@@author A0139128A
	@Override
	public CommandResult undo() {
		assert model != null;
		model.revertData();
		return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
	}
	
	//@@author A0139128A
	@Override
	public CommandResult redo() {
		model.resetData(WhatNow.getEmptyWhatNow());
		return new CommandResult(RedoCommand.MESSAGE_SUCCESS);
	}
}
