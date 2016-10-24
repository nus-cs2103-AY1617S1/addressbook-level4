package seedu.savvytasker.logic.commands;

public class UnmarkCommand extends Command {

    @Override
    public CommandResult execute() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean canUndo() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean redo() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean undo() {
        // TODO Auto-generated method stub
        return false;
    }
    
    /**
     * Check if command is an undo command
     * @return true if the command is an undo operation, false otherwise
     */
    @Override
    public boolean isUndo() {
        return false;
    }
    
    /**
     * Check if command is a redo command
     * @return true if the command is a redo operation, false otherwise
     */
    @Override
    public boolean isRedo(){
        return false;
    }
}
