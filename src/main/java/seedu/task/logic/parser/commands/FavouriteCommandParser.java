package seedu.task.logic.parser.commands;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.FavoriteCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.UnfavoriteCommand;
import seedu.task.logic.parser.CommandParser;

public class FavouriteCommandParser {
    
    public static Command prepareFavorite(String args) {
        Optional<Integer> index = CommandParser.parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavoriteCommand.MESSAGE_USAGE));
        }
        return new FavoriteCommand(index.get());
    }
    public static Command prepareUnfavorite(String args) {
        Optional<Integer> index = CommandParser.parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnfavoriteCommand.MESSAGE_USAGE));
        }
        return new UnfavoriteCommand(index.get());
    }
}
