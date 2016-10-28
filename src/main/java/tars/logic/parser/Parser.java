package tars.logic.parser;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tars.logic.commands.AddCommand;
import tars.logic.commands.CdCommand;
import tars.logic.commands.ClearCommand;
import tars.logic.commands.Command;
import tars.logic.commands.ConfirmCommand;
import tars.logic.commands.DeleteCommand;
import tars.logic.commands.DoCommand;
import tars.logic.commands.EditCommand;
import tars.logic.commands.ExitCommand;
import tars.logic.commands.FindCommand;
import tars.logic.commands.HelpCommand;
import tars.logic.commands.IncorrectCommand;
import tars.logic.commands.ListCommand;
import tars.logic.commands.RedoCommand;
import tars.logic.commands.RsvCommand;
import tars.logic.commands.TagCommand;
import tars.logic.commands.UdCommand;
import tars.logic.commands.UndoCommand;

/**
 * Parses user input.
 * 
 * @@author A0139924W
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Used for mapping a list of known command
     */
    private static Map<String, Class<? extends CommandParser>> commandParserMap =
            new HashMap<String, Class<? extends CommandParser>>();

    static {
        fillCommandMap();
    }

    private static void fillCommandMap() {
        commandParserMap.put(AddCommand.COMMAND_WORD, AddCommandParser.class);
        commandParserMap.put(RsvCommand.COMMAND_WORD, RsvCommandParser.class);
        commandParserMap.put(EditCommand.COMMAND_WORD, EditCommandParser.class);
        commandParserMap.put(DeleteCommand.COMMAND_WORD, DeleteCommandParser.class);
        commandParserMap.put(ConfirmCommand.COMMAND_WORD, ConfirmCommandParser.class);
        commandParserMap.put(ClearCommand.COMMAND_WORD, ClearCommandParser.class);
        commandParserMap.put(FindCommand.COMMAND_WORD, FindCommandParser.class);
        commandParserMap.put(ListCommand.COMMAND_WORD, ListCommandParser.class);
        commandParserMap.put(UndoCommand.COMMAND_WORD, UndoCommandParser.class);
        commandParserMap.put(RedoCommand.COMMAND_WORD, RedoCommandParser.class);
        commandParserMap.put(DoCommand.COMMAND_WORD, DoCommandParser.class);
        commandParserMap.put(UdCommand.COMMAND_WORD, UdCommandParser.class);
        commandParserMap.put(CdCommand.COMMAND_WORD, CdCommandParser.class);
        commandParserMap.put(TagCommand.COMMAND_WORD, TagCommandParser.class);
        commandParserMap.put(ExitCommand.COMMAND_WORD, ExitCommandParser.class);
        commandParserMap.put(HelpCommand.COMMAND_WORD, HelpCommandParser.class);
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (!commandParserMap.containsKey(commandWord)) {
            return new IncorrectCommandParser().prepareCommand(arguments);
        }

        try {
            return commandParserMap.get(commandWord).newInstance().prepareCommand(arguments);
        } catch (Exception ex) {
            return new IncorrectCommandParser().prepareCommand(arguments);
        }
    }

}
