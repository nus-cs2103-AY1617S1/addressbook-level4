package w15c2.tusk.logic.parser;

import w15c2.tusk.logic.commands.Command;
import w15c2.tusk.logic.commands.SetStorageCommand;

//@@author A0138978E
/*
 * Parses the setstorage command
 */
public class SetStorageCommandParser extends CommandParser{
    public static final String COMMAND_WORD = SetStorageCommand.COMMAND_WORD;
    public static final String ALTERNATE_COMMAND_WORD = SetStorageCommand.ALTERNATE_COMMAND_WORD;

    @Override
    public Command prepareCommand(String arguments) {
        return new SetStorageCommand(arguments);
    }

}
