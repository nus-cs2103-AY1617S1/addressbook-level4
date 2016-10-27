package seedu.address.logic.commands;

import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.undo.UndoTask;

//@@author A0139145E
/**
 * Lists all persons in the address book to the user.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the last reversible action from Task Book in this session\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Action \"%1$s\" has been reverted";

    public static final String MESSAGE_UNDO_NOT_POSSIBLE = "There are no actions available for undo";

    public UndoCommand() {}

    @Override
    public CommandResult execute() {
        assert model != null;
        UndoTask toUndo = model.undoTask();

        //No undoable action found.
        if (toUndo == null) { 
            return new CommandResult(MESSAGE_UNDO_NOT_POSSIBLE);
        }

        try {
            switch (toUndo.getCommand()){

            case AddCommand.COMMAND_WORD:
                model.deleteTask(toUndo.getPostData());
                break;

            case DeleteCommand.COMMAND_WORD:
                model.addTask(toUndo.getPostData());
                break;

            case EditCommand.COMMAND_WORD:
                model.deleteTask(toUndo.getPostData());
                model.addTask(toUndo.getPreData());
                break;

            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, toUndo.getCommand()));
        }
        catch (UniqueTaskList.TaskNotFoundException tnfe){
            //Not possible
            return new CommandResult(MESSAGE_UNDO_NOT_POSSIBLE);
        }
        catch (UniqueTaskList.DuplicateTaskException dte){
            //Not possible
            return new CommandResult(MESSAGE_UNDO_NOT_POSSIBLE);
        }

    }
}
//@@author
