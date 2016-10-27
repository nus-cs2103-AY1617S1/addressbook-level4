package seedu.taskscheduler.logic.parser;

import static seedu.taskscheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.InputMismatchException;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.logic.commands.AddCommand;
import seedu.taskscheduler.logic.commands.Command;
import seedu.taskscheduler.logic.commands.IncorrectCommand;
import seedu.taskscheduler.model.task.Task;

//@@author A0148145E

/**
 * Parses add command user input.
 */
public class AddCommandParser extends CommandParser {

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    public Command prepareCommand(String args) {

        try {
            Task task = generateTaskFromArgs(args);
            if (task == null) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));      
            } else {
                return new AddCommand(task);
            }
        } catch (InputMismatchException ime) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE)); 
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}
