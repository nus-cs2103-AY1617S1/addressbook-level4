package seedu.oneline.logic.commands;

public abstract class MutatingCommand extends Command {
    protected CommandAction undoAction = null;
    private int stateWhenDone = -1;
    
    protected static abstract class CommandAction {
        public abstract void execute() throws Exception;
    }
    
    public void undo() {
        // Todo
    }

    @Override
    public boolean isMutating() {
        return true;
    }
    
}
