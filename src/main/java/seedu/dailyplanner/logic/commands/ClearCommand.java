package seedu.dailyplanner.logic.commands;

import seedu.dailyplanner.model.DailyPlanner;

/**
 * Clears the daily planner.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Daily Planner has been cleared!";

    public ClearCommand() {}

    //@@author A0139102U
    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(DailyPlanner.getEmptyDailyPlanner());
        model.resetPinBoard();
        model.setLastTaskAddedIndex(0);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
