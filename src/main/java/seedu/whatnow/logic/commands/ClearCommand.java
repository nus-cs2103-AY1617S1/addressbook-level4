package seedu.whatnow.logic.commands;

import seedu.whatnow.model.WhatNow;

/**
 * Clears WhatNow.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "WhatNow has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(WhatNow.getEmptyWhatNow());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
