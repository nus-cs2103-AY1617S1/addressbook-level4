package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ChangeStatusCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.ArgumentTokenizer.Prefix;

public class TokParser {

    // @@author A0141019U
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    // Different regexps for different permutations of arguments
    private static final Pattern ADD_COMMAND_FORMAT_1 = Pattern
            .compile("(?i)(?<taskType>event|ev|deadline|dl|someday|sd)(?<addTaskArgs>.*)");
    private static final Pattern ADD_COMMAND_FORMAT_2 = Pattern
            .compile("(?i)(?<addTaskArgs>.*)(?<taskType>event|ev|deadline|dl|someday|sd)");

    private static final Pattern EVENT_ARGS_FORMAT_1 = Pattern.compile(
            "(?i)'(?<taskName>.*\\S+.*)'(\\s+on\\s+)?(?<date>\\S+)?\\s+from\\s+(?<startTime>\\S+\\s?\\S+)\\s+to\\s+(?<endTime>\\S+\\s?\\S+)");
    private static final Pattern EVENT_ARGS_FORMAT_2 = Pattern.compile(
            "(?i)'(?<taskName>.*\\S+.*)'\\s+from\\s+(?<startTime>\\S+\\s?\\S+)\\s+to\\s+(?<endTime>\\S+\\s?\\S+)(\\s+on\\s+)?(?<date>\\S+)?");
    private static final Pattern EVENT_ARGS_FORMAT_3 = Pattern.compile(
            "(?i)from\\s+(?<startTime>\\S+\\s?\\S+)\\s+to\\s+(?<endTime>\\S+\\s?\\S+)(\\s+on\\s+)?(?<date>\\S+)?\\s+'(?<taskName>.*\\S+.*)'");

    private static final Pattern DEADLINE_ARGS_FORMAT_1 = Pattern
            .compile("(?i)'(?<taskName>.*\\S+.*)'\\s+by\\s+(?<dateTime>.+)");
    private static final Pattern DEADLINE_ARGS_FORMAT_2 = Pattern
            .compile("(?i)by\\s+(?<dateTime>.+)\\s+'(?<taskName>.*\\S+.*)'");

    private static final Pattern SOMEDAY_ARGS_FORMAT = Pattern.compile("'(?<taskName>.*\\S+.*)'");

    
    private static final Prefix startDateTimePrefix = new Prefix("from ");
    private static final Prefix endDateTimePrefix = new Prefix("to ");
    private static final Prefix datePrefix = new Prefix("on ");
    
    
    // @@author A0141019U-reused
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").trim();
        final String arguments = matcher.group("arguments").trim();

        System.out.println("command: " + commandWord);
        System.out.println("arguments: " + arguments);

        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

//      case FindCommand.COMMAND_WORD:
//          return prepareFind(arguments);
//
//      case ListCommand.COMMAND_WORD:
//          return prepareList(arguments);
//
//      case DeleteCommand.COMMAND_WORD:
//          return prepareDelete(arguments);
//
//      case EditCommand.COMMAND_WORD:
//          return prepareEdit(arguments);
//
//      case ChangeStatusCommand.COMMAND_WORD_DONE:
//          return prepareChangeStatus(arguments, "done");
//
//      case ChangeStatusCommand.COMMAND_WORD_PENDING:
//          return prepareChangeStatus(arguments, "pending");

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    // @@author A0141019U
    private Command prepareAdd(String arguments) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(startDateTimePrefix, endDateTimePrefix, datePrefix);
        argsTokenizer.tokenize(arguments);
        
        try {
            System.out.println("pre: " + argsTokenizer.getPreamble());
            System.out.println("st date: " + argsTokenizer.getValue(startDateTimePrefix));
            System.out.println("end date: " + argsTokenizer.getValue(endDateTimePrefix));
            System.out.println("date: " + argsTokenizer.getValue(datePrefix));
        } 
        catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } 
        
        return null;
    }

    // @@author A0141019U
    private Command prepareAddEvent(String arguments) {
        arguments = arguments.trim();

        ArrayList<Matcher> matchers = new ArrayList<>();
        matchers.add(EVENT_ARGS_FORMAT_1.matcher(arguments));
        matchers.add(EVENT_ARGS_FORMAT_2.matcher(arguments));
        matchers.add(EVENT_ARGS_FORMAT_3.matcher(arguments));

        // Null values will always be overwritten if the matcher matches.
        String taskName = null;
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;

        boolean isAnyMatch = false;

        for (Matcher matcher : matchers) {
            if (matcher.matches()) {
                isAnyMatch = true;

                taskName = matcher.group("taskName").trim();

                String startTime = matcher.group("startTime").trim();
                String endTime = matcher.group("endTime").trim();

                Optional<String> dateOpt = Optional.ofNullable(matcher.group("date"));
                String date = dateOpt.orElse("");

                try {
                    System.out.println("start: " + date + " " + startTime);
                    System.out.println("end: " + date + " " + endTime);

                    startDateTime = DateParser.parse(date + " " + startTime);
                    endDateTime = DateParser.parse(date + " " + endTime);
                } catch (ParseException e) {
                    return new IncorrectCommand(e.getMessage());
                }

                try {
                    return new AddCommand(taskName, startDateTime, endDateTime);
                } catch (IllegalValueException e) {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                }
            }
        }

        if (!isAnyMatch) {
            System.out.println("no match");
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            return new AddCommand(taskName, startDateTime, endDateTime);
        } catch (IllegalValueException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }
    
    
    public static void main(String[] args) {
        TokParser t = new TokParser();
        t.parseCommand("add event 'dance' from 5pm today to 4pm tomorrow");
    }
}
