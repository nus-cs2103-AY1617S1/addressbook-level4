package tars.logic.parser;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tars.commons.core.Messages;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.util.DateTimeUtil;
import tars.logic.commands.Command;
import tars.logic.commands.FindCommand;
import tars.logic.commands.IncorrectCommand;
import tars.model.task.TaskQuery;

public class FindCommandParser extends CommandParser {
    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more whitespace

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command prepareCommand(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(namePrefix, priorityPrefix,
                dateTimePrefix, donePrefix, undonePrefix, tagPrefix);
        argsTokenizer.tokenize(args);

        if (argsTokenizer.numPrefixFound() == 0) {
            return new FindCommand(generateKeywordSetFromArgs(args.trim()));
        }

        TaskQuery taskQuery;
        try {
            taskQuery = createTaskQuery(argsTokenizer);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (DateTimeException dte) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE);
        }

        return new FindCommand(taskQuery);
    }

    private ArrayList<String> generateKeywordSetFromArgs(String keywordsArgs) {
        String[] keywordsArray = keywordsArgs.split("\\s+");
        return new ArrayList<String>(Arrays.asList(keywordsArray));
    }

    private TaskQuery createTaskQuery(ArgumentTokenizer argsTokenizer)
            throws DateTimeException, IllegalValueException {
        TaskQuery taskQuery = new TaskQuery();
        Boolean statusDone = true;
        Boolean statusUndone = false;

        taskQuery.createNameQuery(argsTokenizer.getValue(namePrefix).orElse(EMPTY_STRING)
                .replaceAll("( )+", EMPTY_SPACE_ONE));
        taskQuery.createDateTimeQuery(DateTimeUtil
                .getDateTimeFromArgs(argsTokenizer.getValue(dateTimePrefix).orElse(EMPTY_STRING)));
        taskQuery.createPriorityQuery(argsTokenizer.getValue(priorityPrefix).orElse(EMPTY_STRING));
        if (!argsTokenizer.getValue(donePrefix).orElse(EMPTY_STRING).isEmpty()
                && !argsTokenizer.getValue(undonePrefix).orElse(EMPTY_STRING).isEmpty()) {
            throw new IllegalValueException(TaskQuery.MESSAGE_BOTH_STATUS_SEARCHED_ERROR);
        } else {
            if (!argsTokenizer.getValue(donePrefix).orElse(EMPTY_STRING).isEmpty()) {
                taskQuery.createStatusQuery(statusDone);
            }
            if (!argsTokenizer.getValue(undonePrefix).orElse(EMPTY_STRING).isEmpty()) {
                taskQuery.createStatusQuery(statusUndone);
            }
        }
        taskQuery.createTagsQuery(argsTokenizer.getMultipleRawValues(tagPrefix).orElse(EMPTY_STRING)
                .replaceAll("( )+", EMPTY_SPACE_ONE));

        return taskQuery;
    }

}
