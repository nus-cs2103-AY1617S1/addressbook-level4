package seedu.flexitrack.logic.commands;

import seedu.flexitrack.model.FlexiTrack;

/**
 * Clears the FlexiTrack.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clear the to do lists in FlexiTrack.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "FlexiTrack has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(FlexiTrack.getEmptyFlexiTrack());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
