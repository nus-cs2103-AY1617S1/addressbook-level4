package seedu.address.logic.parser;

import seedu.address.logic.commands.taskcommands.SetStorageCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;

//@@author A0138978E
/*
 * Parses the setstorage command
 */
public class SetStorageCommandParser extends CommandParser{
    public static final String COMMAND_WORD = SetStorageCommand.COMMAND_WORD;

    @Override
    public TaskCommand prepareCommand(String arguments) {
        return new SetStorageCommand(arguments);
    }

}
