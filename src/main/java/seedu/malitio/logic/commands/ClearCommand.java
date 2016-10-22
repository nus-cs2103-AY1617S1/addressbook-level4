package seedu.malitio.logic.commands;

import seedu.malitio.model.Malitio;

/**
 * Clears the malitio.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Malitio has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(Malitio.getEmptymalitio());
        model.getFuture().clear();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
