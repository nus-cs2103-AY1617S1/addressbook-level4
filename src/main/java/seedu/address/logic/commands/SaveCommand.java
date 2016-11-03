package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SaveEvent;
import seedu.address.storage.XmlAddressBookStorage;

public class SaveCommand extends Command {
    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows the save file location to be specified.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "New save location: " + XmlAddressBookStorage.getFilePathForSaveCommand();

    public SaveCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new SaveEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
}
