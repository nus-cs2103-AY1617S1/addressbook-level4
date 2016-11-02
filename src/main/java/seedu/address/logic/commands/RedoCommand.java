package seedu.address.logic.commands;

import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.undo.UndoTask;

//@@author A0139145E
/**
 * undo the previous command
 */
public class RedoCommand extends Command implements Undoable{

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redoes the last undone action from Task Book in this session\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undo action \"%1$s\" has been reverted";

    public static final String MESSAGE_REDO_NOT_POSSIBLE = "There are no actions available for redo";

    private UndoTask toRedo;
    
    public RedoCommand() {}

    @Override
    public CommandResult execute() {
        assert model != null;
        toRedo = model.redoTask();

        //No undoable action found.
        if (toRedo == null) { 
            return new CommandResult(MESSAGE_REDO_NOT_POSSIBLE);
        }

        boolean duplicateTaskResult = false;

        try {
            switch (toRedo.getCommand()){

            case AddCommand.COMMAND_WORD:
                duplicateTaskResult = model.addTask(toRedo.getPostData());
                break;

            case DeleteCommand.COMMAND_WORD:
                model.deleteTask(toRedo.getPostData());
                break;

            case EditCommand.COMMAND_WORD:
                model.deleteTask(toRedo.getPreData());  
                duplicateTaskResult = model.addTask(toRedo.getPostData());               
                break;

            case DoneCommand.COMMAND_WORD:
                model.completeTask(toRedo.getPostData());               
                break;

            }

            populateUndo();
            
            //Determine if duplicate exist
            if (duplicateTaskResult){
                return new CommandResult(String.format(MESSAGE_SUCCESS, toRedo.getCommand() 
                        + "\n" + AddCommand.MESSAGE_DUPLICATE_TASK));
            }
            else {
                return new CommandResult(String.format(MESSAGE_SUCCESS, toRedo.getCommand()));
            }

        }
        catch (UniqueTaskList.TaskNotFoundException tnfe){
            assert false : "Task not found not possible";
        return new CommandResult(MESSAGE_REDO_NOT_POSSIBLE);
        }

    }
    
    @Override
    public void populateUndo(){
        assert COMMAND_WORD != null;
        assert toRedo != null;
        model.addUndo(toRedo);
    }
}
//@@author
