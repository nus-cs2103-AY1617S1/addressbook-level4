package seedu.task.logic.parser;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskcommons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.CalendarCommand;
import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DeleteTaskCommand;
import seedu.task.logic.commands.EditTaskCommand;
import seedu.task.logic.commands.ExitCommand;
import seedu.task.logic.commands.FindCommand;
import seedu.task.logic.commands.HelpCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.MarkCommand;
import seedu.task.logic.commands.SaveCommand;
import seedu.task.logic.commands.SelectCommand;
import seedu.task.logic.commands.UndoCommand;

/**
 * Parses user input.
 */
public class ParserManager {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern ITEM_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    

    public ParserManager() {}
    
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
            return new AddParser().prepare(arguments);
            
        case EditTaskCommand.COMMAND_WORD:
            return new EditParser().prepare(arguments);

        case MarkCommand.COMMAND_WORD:
            return new MarkParser().prepare(arguments);
            
        case SelectCommand.COMMAND_WORD:
            return new SelectParser().prepare(arguments);

        case DeleteTaskCommand.COMMAND_WORD:
            return new DeleteParser().prepare(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearParser().prepare(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindParser().prepare(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListParser().prepare(arguments);
            
        case SaveCommand.COMMAND_WORD:
            return new SaveParser().prepare(arguments);
            
        case CalendarCommand.COMMAND_WORD:
        	return new CalendarParser().prepare(arguments);
            
        case UndoCommand.COMMAND_WORD:
        	return new UndoCommand();
            
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpParser().prepare(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
