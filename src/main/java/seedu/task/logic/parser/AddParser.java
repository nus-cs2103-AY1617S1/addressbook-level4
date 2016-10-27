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
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Responsible for validating and preparing the arguments for AddCommand execution
 * @author kian ming
 */

//@@author A0127570H
public class AddParser implements Parser {
    
    public AddParser() {}
    
    /**
     * Parses arguments in the context of the add task or event command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command prepare(String args){
        
        if (args.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(descriptionPrefix, deadlinePrefix, 
                durationStartPrefix, durationEndPrefix);
        argsTokenizer.tokenize(args);
        
        try {           
            String name = argsTokenizer.getPreamble().get();
            Optional <String> description = argsTokenizer.getValue(descriptionPrefix);
            Optional <String> startDuration = argsTokenizer.getValue(durationStartPrefix);
            Optional <String> endDuration = argsTokenizer.getValue(durationEndPrefix);
            Optional <String> deadline = argsTokenizer.getValue(deadlinePrefix);
            
            if (startDuration.isPresent()) { //Only events have duration
                return new AddEventCommand(name, description.orElse(""), startDuration.orElse(""), endDuration.orElse(""));
            } else {
                return new AddTaskCommand(name, description.orElse(""), deadline.orElse(""));             
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (EmptyValueException e) {
            return new IncorrectCommand(e.getMessage());
        }
    } 
    
}
