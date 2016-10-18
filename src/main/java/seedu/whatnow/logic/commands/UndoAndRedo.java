package seedu.whatnow.logic.commands;

public abstract class UndoAndRedo extends Command{
	public abstract CommandResult undo();
	
	public abstract CommandResult redo();
}
