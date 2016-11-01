package w15c2.tusk.logic.parser;

import w15c2.tusk.logic.commands.taskcommands.SetStorageCommand;
import w15c2.tusk.logic.commands.taskcommands.TaskCommand;

//@@author A0138978E
/*
 * Parses the setstorage command
 */
public class SetStorageCommandParser extends CommandParser{
    public static final String COMMAND_WORD = SetStorageCommand.COMMAND_WORD;
    public static final String ALTERNATE_COMMAND_WORD = SetStorageCommand.ALTERNATE_COMMAND_WORD;

    @Override
    public TaskCommand prepareCommand(String arguments) {
        return new SetStorageCommand(arguments);
    }

}
