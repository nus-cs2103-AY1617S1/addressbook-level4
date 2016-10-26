package seedu.address.logic.commands;

import seedu.address.model.undo.UndoTask;

//@@author A0139145E
/**
 * Lists all persons in the address book to the user.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the last reversible action in the task book.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undid last action %1$s";

    public UndoCommand() {}

    @Override
    public CommandResult execute() {
        assert model != null;
        UndoTask toUndo = model.undoTask();
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
        catch (Exception e){
            return new CommandResult("Failed to Undo");
        }

    }

    @Override
    public boolean isMutating() {
        return false;
    }

}
//@@author
