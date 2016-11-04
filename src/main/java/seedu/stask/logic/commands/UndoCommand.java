package seedu.stask.logic.commands;

import seedu.stask.commons.util.CommandUtil;
import seedu.stask.model.task.UniqueTaskList;
import seedu.stask.model.undo.UndoTask;

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

        int duplicateOrClashTaskResult = 0;

        try {
            switch (toUndo.getCommand()){

            case AddCommand.COMMAND_WORD:
                model.deleteTask(toUndo.getPostData());
                break;

            case DeleteCommand.COMMAND_WORD:
                duplicateOrClashTaskResult = model.addTask(toUndo.getPostData());
                break;

            case EditCommand.COMMAND_WORD:
                model.deleteTask(toUndo.getPostData());               
                duplicateOrClashTaskResult = model.addTask(toUndo.getPreData());
                break;

            case DoneCommand.COMMAND_WORD:
                model.uncompleteTask(toUndo.getPostData());               
                break;

            }

            //Add into redo stack
            model.addRedo(toUndo);

            //Determine if duplicate exist
            CommandResult temporary = new CommandResult(String.format(MESSAGE_SUCCESS, toUndo.getCommand()));
            return CommandUtil.generateCommandResult(temporary,duplicateOrClashTaskResult);
        }
        catch (UniqueTaskList.TaskNotFoundException tnfe){
            assert false : "Task not found not possible";
        return new CommandResult(MESSAGE_UNDO_NOT_POSSIBLE);
        }

    }
}
//@@author
