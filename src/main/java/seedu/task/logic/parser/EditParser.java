package seedu.task.logic.parser;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.EditEventCommand;
import seedu.task.logic.commands.EditTaskCommand;
import seedu.task.logic.commands.IncorrectCommand;

/**
 * Prepares EditTaskCommand or EditEventCommand according to the input argument.
 * @author kian ming
 */

public class EditParser implements Parser {

    // remember to trim 
    private static final Pattern EDIT_TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?:-t)\\s(?<index>\\d*)"
                    + "(?<newname>(?: /name [^/]+)*)"
                    + "(?<newdescription>(?: /desc [^/]+)*)?"
                    + "(?<newdeadline>(?: /by [^/]+)*)?"
                    );

 // remember to trim 
    private static final Pattern EDIT_EVENT_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?:-e)\\s(?<index>\\d*)"
                    + "(?<newname>(?: /name [^/]+)*)"
                    + "(?<newdescription>(?: /desc [^/]+)*)?"
                    + "(?<newduration>(?: /from [^/]+)*)?"
                    );
    
    
    /**
     * Parses arguments in the context of the edit command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command prepare(String args) {
        final Matcher taskMatcher = EDIT_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        final Matcher eventMatcher = EDIT_EVENT_DATA_ARGS_FORMAT.matcher(args.trim());
        
        if (taskMatcher.matches()){
            try {
                return new EditTaskCommand(
                        Integer.parseInt(taskMatcher.group("index").trim()),
                        isFieldToBeEdited(taskMatcher.group("newname")),
                        taskMatcher.group("newname").replaceFirst("/name","").trim(),
                        isFieldToBeEdited(taskMatcher.group("newdescription")),
                        taskMatcher.group("newdescription").replaceFirst("/desc", "").trim(),
                        isFieldToBeEdited(taskMatcher.group("newdeadline")),
                        taskMatcher.group("newdeadline").replaceFirst("/by", "").trim()
                );
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        } else if (eventMatcher.matches()){
            try {
                return new EditEventCommand(
                        Integer.parseInt(eventMatcher.group("index").trim()),
                        isFieldToBeEdited(eventMatcher.group("newname")),
                        eventMatcher.group("newname").replaceFirst("/name","").trim(),
                        isFieldToBeEdited(eventMatcher.group("newdescription")),
                        eventMatcher.group("newdescription").replaceFirst("/desc", "").trim(),
                        isFieldToBeEdited(eventMatcher.group("newduration")),
                        eventMatcher.group("newduration").replaceFirst("/from", "").trim()
                );
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        }else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
    }
    
    private boolean isFieldToBeEdited(String field) {
        assert field != null;
        return !field.isEmpty();
    }

}