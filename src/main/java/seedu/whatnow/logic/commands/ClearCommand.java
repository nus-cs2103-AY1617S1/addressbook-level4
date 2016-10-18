package seedu.whatnow.logic.commands;

import seedu.whatnow.model.WhatNow;

/**
 * Clears WhatNow.
 */
public class ClearCommand extends UndoAndRedo {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "WhatNow has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(WhatNow.getEmptyWhatNow());
        model.getUndoStack().push(this);
        return new CommandResult(MESSAGE_SUCCESS);
    }


	@Override
	public CommandResult undo() {
		assert model != null;
		return null;
	}


	@Override
	public CommandResult redo() {
		// TODO Auto-generated method stub
		return null;
	}
}
