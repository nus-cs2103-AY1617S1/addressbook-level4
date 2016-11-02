package seedu.address.logic.commands;

import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.undo.UndoTask;

//@@author A0139145E
/**
 * undo the previous command
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the last reversible action from Task Book in this session\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Action \"%1$s\" has been reverted";

    public static final String MESSAGE_UNDO_NOT_POSSIBLE = "There are no actions available for undo";

    private UndoTask toUndo;
    
    public UndoCommand() {}

    @Override
    public CommandResult execute() {
        assert model != null;
        toUndo = model.undoTask();

        //No undoable action found.
        if (toUndo == null) { 
            return new CommandResult(MESSAGE_UNDO_NOT_POSSIBLE);
        }

        boolean duplicateTaskResult = false;

        try {
            switch (toUndo.getCommand()){

            case AddCommand.COMMAND_WORD:
                model.deleteTask(toUndo.getPostData());
                break;

            case DeleteCommand.COMMAND_WORD:
                duplicateTaskResult = model.addTask(toUndo.getPostData());
                break;

            case EditCommand.COMMAND_WORD:
                model.deleteTask(toUndo.getPostData());               
                duplicateTaskResult = model.addTask(toUndo.getPreData());
                break;

            case DoneCommand.COMMAND_WORD:
                model.uncompleteTask(toUndo.getPostData());               
                break;

            }

            //Add into redo stack
            model.addRedo(toUndo);
            
            //Determine if duplicate exist
            if (duplicateTaskResult){
                return new CommandResult(String.format(MESSAGE_SUCCESS, toUndo.getCommand() 
                        + "\n" + AddCommand.MESSAGE_DUPLICATE_TASK));
            }
            else {
                return new CommandResult(String.format(MESSAGE_SUCCESS, toUndo.getCommand()));
            }
        }
        catch (UniqueTaskList.TaskNotFoundException tnfe){
            assert false : "Task not found not possible";
        return new CommandResult(MESSAGE_UNDO_NOT_POSSIBLE);
        }

    }
}
//@@author
