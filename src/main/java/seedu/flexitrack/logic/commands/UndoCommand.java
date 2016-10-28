//@@author A0127686R
package seedu.flexitrack.logic.commands;

import java.util.Stack;

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
    
    static Stack<String> commandRecord = new Stack<String>(); 

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
            undo = new AddCommand();
            break; 
        case "delete":   
            undo = new DeleteCommand();
            break; 
        case "mark":   
            undo = new MarkCommand(); 
            break;
        case "unmark":  
            undo = new UnmarkCommand(); 
            break;
        case "clear": 
            undo = new ClearCommand(); 
            break; 
        case "edit":   
            undo = new EditCommand(); 
            break;            
        case "block":   
            undo = new BlockCommand(); 
            break;
        }
        undo.setData(model);
        assert undo != null; 
        undo.executeUndo();
        commandRecord.pop();
        model.indicateFlexiTrackerChanged();
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
   
}
