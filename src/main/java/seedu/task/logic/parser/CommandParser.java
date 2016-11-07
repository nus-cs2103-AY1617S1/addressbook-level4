package seedu.task.logic.parser;

import seedu.task.logic.commands.*;
import seedu.task.commons.util.StringUtil;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import seedu.task.logic.parser.commands.*;

//@@author A0147944U
/**
 * Parses user input.
 */
public class CommandParser {

    /**
     * Used for initial separation of command word and args.
     */

    private static final Pattern BASIC_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT =
            Pattern.compile("(?<targetIndex>.+)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_WORD_ALT:
            return AddCommandParser.prepareAdd(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_WORD_ALT:
            return EditCommandParser.prepareEdit(arguments);

        case SelectCommand.COMMAND_WORD:
            return SelectCommandParser.prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD_ALT:
            return DeleteCommandParser.prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_WORD_ALT:
            return FindCommandParser.prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_WORD_ALT:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_WORD_ALT:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_WORD_ALT:
            return UndoCommandParser.prepareUndo(arguments);

        case DirectoryCommand.COMMAND_WORD:
        case DirectoryCommand.COMMAND_WORD_ALT:
            return DirectoryCommandParser.prepareDirectory(arguments);

        case BackupCommand.COMMAND_WORD:
        case BackupCommand.COMMAND_WORD_ALT:
            return BackupCommandParser.prepareBackup(arguments);

        case SortCommand.COMMAND_WORD:
        case SortCommand.COMMAND_WORD_ALT:
            return SortCommandParser.prepareSort(arguments);

        case RepeatCommand.COMMAND_WORD:
            return RepeatCommandParser.prepareRepeat(arguments);

        case DoneCommand.COMMAND_WORD:
            return DoneCommandParser.prepareDone(arguments);

        case UndoneCommand.COMMAND_WORD:
            return DoneCommandParser.prepareUndone(arguments);

        case FavoriteCommand.COMMAND_WORD:
        case FavoriteCommand.COMMAND_WORD_ALT_1:
        case FavoriteCommand.COMMAND_WORD_ALT_2:
            return FavouriteCommandParser.prepareFavorite(arguments);

        case UnfavoriteCommand.COMMAND_WORD:
        case UnfavoriteCommand.COMMAND_WORD_ALT_1:
        case UnfavoriteCommand.COMMAND_WORD_ALT_2:
            return FavouriteCommandParser.prepareUnfavorite(arguments);

        case RefreshCommand.COMMAND_WORD:
        case RefreshCommand.COMMAND_WORD_ALT:
            return new RefreshCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned
     * integer is given as the index. Returns an {@code Optional.empty()}
     * otherwise.
     */
    public static Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

}

