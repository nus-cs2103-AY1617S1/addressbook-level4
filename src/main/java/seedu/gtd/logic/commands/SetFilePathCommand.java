package seedu.gtd.logic.commands;


import seedu.gtd.commons.exceptions.IllegalValueException;

//@@author A0139072H

/**
 * Sets the file path of the saved tasklist to a new file
 **/
public class SetFilePathCommand extends Command {

    public static final String COMMAND_WORD = "setPath";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sets the new file location. "
            + "Parameters: nameofnewfile\n"
            + "Example: " + COMMAND_WORD
            + " internalFolder/name_of_new_file";

    public static final String MESSAGE_SUCCESS = "New file location set to: %1$s";
    public static final String MESSAGE_INVALID_LOC_TASK = "The file location is invalid!";

    private final String newFilePath;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public SetFilePathCommand(String givenString)
            throws IllegalValueException {
        this.newFilePath = givenString.trim();
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try{
        	model.setFilePathTask(newFilePath);
        } catch (Exception e) {
            return new CommandResult(MESSAGE_INVALID_LOC_TASK);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, newFilePath));
    }

}
