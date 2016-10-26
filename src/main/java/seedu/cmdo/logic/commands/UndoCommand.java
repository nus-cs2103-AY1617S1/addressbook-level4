package seedu.cmdo.logic.commands;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.cmdo.commons.exceptions.CannotUndoException;
import seedu.cmdo.model.task.ReadOnlyTask;
import seedu.cmdo.storage.StorageManager;

public class UndoCommand extends Command {
	
	public static final String COMMAND_WORD = "undo";
	private ArrayList<ReadOnlyTask> taskMasterList;
	private StorageManager taskOrganiser;
    private ObservableList<ReadOnlyTask> taskObservableList = FXCollections.observableArrayList();
	
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "undos previous action\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_UNDO_SUCCESS = "Undone!";
	
	public UndoCommand() {}

	@Override
	public CommandResult execute() {

//    	  try {
//    		  model.undoOne(); 
//    	  } catch (CannotUndoException cue) {
//    		  return new CommandResult(cue.getMessage());
//    	  }
		
		try {
			model.undo();
		} catch (CannotUndoException cue) {
			return new CommandResult(cue.getMessage());
		}
    	 
		return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS));
	}
}
