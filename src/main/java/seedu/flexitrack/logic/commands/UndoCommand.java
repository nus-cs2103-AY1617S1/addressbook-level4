package seedu.flexitrack.logic.commands;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.logic.LogicManager;
import seedu.flexitrack.model.FlexiTrack;
import seedu.flexitrack.model.tag.Tag;

/**
 * Clears the FlexiTrack.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_SHORTCUT = "ud"; // TODO: impiment ctrl + Z 
    public static final String MESSAGE_USAGE = COMMAND_WORD  + ", Shortcut [" + COMMAND_SHORTCUT + "]" + ": Clear the to do lists in FlexiTrack.\n" + "Example: "
            + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Your last command has been undo!";
    public static final String MESSAGE_NOT_SUCCESS = "You have no command to undo!";
    public static Stack<String> commandRecord = new Stack<String>(); 
    
    public UndoCommand() {
    }

    @Override
    public CommandResult execute() {
        Command undo = null; 
        if (commandRecord.size() == 0 ){ 
            return new CommandResult(String.format(MESSAGE_NOT_SUCCESS));
        }
        switch (commandRecord.peek()){
        case "add":
            (new FindCommand(AddCommand.storeDataChanged.peek().getName().fullName)).execute();
            undo = new DeleteCommand(1);
            break; 
        case "delete": 
            undo = new DeleteCommand(1);
            break; 
        case "mark": 
            undo = new MarkCommand(1); 
            break;
        case "unmark": 
            undo = new UnmarkCommand(1); 
            break;
        }
        undo.setData(model);
        assert undo != null; 
        undo.executeUndo();
        commandRecord.pop();
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
   
}
