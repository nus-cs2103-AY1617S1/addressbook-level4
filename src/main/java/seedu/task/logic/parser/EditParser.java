package seedu.task.logic.parser;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.commons.exceptions.EmptyValueException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.AddEventCommand;
import seedu.task.logic.commands.AddTaskCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.CommandResult;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.EditEventCommand;
import seedu.task.logic.commands.EditTaskCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.parser.ArgumentTokenizer.Prefix;
import seedu.taskcommons.core.Messages;

/**
 * Prepares EditTaskCommand or EditEventCommand according to the input argument.
 * @author kian ming
 */

public class EditParser implements Parser {
    
    private static final String INDEX_VALIDATION_REGEX = "[0-9]+";
    
    public EditParser() {}
    
    /**
     * Parses arguments in the context of the edit command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command prepare(String args) {
        
        
        if (args.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(descriptionPrefix, deadlinePrefix, 
                durationStartPrefix, durationEndPrefix, taskPrefix, eventPrefix, namePrefix);
       
        argsTokenizer.tokenize(args.trim());
        
        try {           
            Optional <String> taskIndex = argsTokenizer.getValue(taskPrefix);
            Optional <String> eventIndex = argsTokenizer.getValue(eventPrefix);
            Optional <String> name = argsTokenizer.getValue(namePrefix);
            Optional <String> description = argsTokenizer.getValue(descriptionPrefix);
            Optional <String> startDuration = argsTokenizer.getValue(durationStartPrefix);
            Optional <String> endDuration = argsTokenizer.getValue(durationEndPrefix);
            Optional <String> deadline = argsTokenizer.getValue(deadlinePrefix);
            
            if (eventIndex.isPresent()) { 
                int index = getIndex(eventIndex.get().trim());
                return new EditEventCommand(index, name.orElse(""), description.orElse(""), 
                            startDuration.orElse(""), endDuration.orElse(""));                              
            } else if (taskIndex.isPresent()) {  
                int index = getIndex(taskIndex.get().trim());
                return new EditTaskCommand(index, name.orElse(""), description.orElse(""), deadline.orElse(""));             
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        } catch (EmptyValueException e) {
            return new IncorrectCommand(e.getMessage());
        } catch (IndexOutOfBoundsException ie) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }
    }
    
    /**
     * @throws IllegalValueException String index is invalid
     */
    public static int getIndex(String test) throws IllegalValueException {
        if (!(test.trim().matches(INDEX_VALIDATION_REGEX))) {
            throw new IllegalValueException (Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }
        return Integer.parseInt(test.trim());
    }

}