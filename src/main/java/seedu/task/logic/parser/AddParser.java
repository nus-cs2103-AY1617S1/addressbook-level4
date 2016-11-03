package seedu.task.logic.parser;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.task.commons.exceptions.EmptyValueException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.AddEventCommand;
import seedu.task.logic.commands.AddTaskCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;

//@@author A0127570H
/**
 * Responsible for validating and preparing the arguments for AddCommand execution
 * @author kian ming
 */

public class AddParser implements Parser {
    
    private String name;
    private Optional <String> description;
    private Optional <String> startDuration;
    private Optional <String> endDuration;
    private Optional <String> deadline;
    
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
            getTokenizerValue(argsTokenizer);
            
            if (startDuration.isPresent()) { //Only events have duration
                return new AddEventCommand(name, description.orElse(""), startDuration.orElse(""), endDuration.orElse(""));
            } else {
                return new AddTaskCommand(name, description.orElse(""), deadline.orElse(""));             
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (EmptyValueException e) {
            return new IncorrectCommand(e.getMessage());
        }
    }

    private void getTokenizerValue(ArgumentTokenizer argsTokenizer) throws EmptyValueException {
        name = argsTokenizer.getPreamble().get();
        description = argsTokenizer.getValue(descriptionPrefix);
        startDuration = argsTokenizer.getValue(durationStartPrefix);
        endDuration = argsTokenizer.getValue(durationEndPrefix);
        deadline = argsTokenizer.getValue(deadlinePrefix);
    } 
    
}
