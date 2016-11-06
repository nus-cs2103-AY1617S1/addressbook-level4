package seedu.agendum.logic.commands;

import seedu.agendum.commons.exceptions.IllegalValueException;

public class SyncCommand extends Command {
    // COMMAND_WORD, COMMAND_FORMAT, COMMAND_DESCRIPTION are for display in help window
    public static final String COMMAND_WORD = "sync";
    private static final String COMMAND_FORMAT = "sync <on/off>";
    private static final String COMMAND_DESCRIPTION = "Turn syncing on or off";
    private static final String MESSAGE_USAGE = COMMAND_WORD + "- "
            + COMMAND_DESCRIPTION;

    public static final String SYNC_ON_MESSAGE = "Google Calendar Sync is on";
    public static final String SYNC_OFF_MESSAGE = "Google Calendar Sync is off";

    public static final String MESSAGE_WRONG_OPTION = "Invalid option for sync.";

    private boolean syncOption;

    //@@author A0003878Y
    /**
     * Convenience constructor using name
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public SyncCommand(String option) throws IllegalValueException {

        if (option.trim().equalsIgnoreCase("on")) {
            syncOption = true;
        } else if (option.trim().equalsIgnoreCase("off")) {
            syncOption = false;
        } else {
            throw new IllegalValueException(MESSAGE_WRONG_OPTION);
        }
    }

    @Override
    public CommandResult execute() {
        if (syncOption) {
            model.activateModelSyncing();
            return new CommandResult(SYNC_ON_MESSAGE);
        } else {
            model.deactivateModelSyncing();
            return new CommandResult(SYNC_OFF_MESSAGE);
        }
    }

    //@@author
    public static String getName() {
        return COMMAND_WORD;
    }

    public static String getFormat() {
        return COMMAND_FORMAT;
    }

    public static String getDescription() {
        return COMMAND_DESCRIPTION;
    }
}
