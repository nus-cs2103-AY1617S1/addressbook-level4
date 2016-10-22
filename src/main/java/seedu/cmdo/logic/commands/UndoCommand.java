package seedu.cmdo.logic.commands;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.cmdo.commons.core.Messages;
import seedu.cmdo.model.ReadOnlyToDoList;
import seedu.cmdo.model.StatusSaver;
import seedu.cmdo.model.ToDoList;
import seedu.cmdo.model.tag.UniqueTagList;
import seedu.cmdo.model.task.Task;
import seedu.cmdo.model.task.UniqueTaskList;
import seedu.cmdo.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.cmdo.storage.StorageManager;

public class UndoCommand extends Command {
	
	public static final String COMMAND_WORD = "undo";
//	public StatusSaver statusSaver;
	private ArrayList<Task> taskMasterList;
	private StorageManager taskOrganiser;
    private ObservableList<Task> taskObservableList = FXCollections.observableArrayList();
	
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "undos previous action\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_UNDO_SUCCESS = "Selected Task: %1$s";
	
	public UndoCommand(){
		assert statusSaver != null;
//		this.statusSaver = statusSaver;
	}

       @Override
	public CommandResult execute() {
    	   if (statusSaver.isUndoMasterStackEmpty()) {
	           return new CommandResult(Messages.MESSAGE_UNDO_LIMIT);
	        }
	     	statusSaver.retrieveLastStatus();
	        taskMasterList = statusSaver.getLastTaskMasterList();
	        taskObservableList = statusSaver.getLastTaskObservableList();
	        	        	        
	        UniqueTaskList tl = new UniqueTaskList();
	        UniqueTagList tags = new UniqueTagList();
	        for (Task t : taskMasterList) {
	        	try {
	        		tl.add(t);
	        	} catch (DuplicateTaskException dte) {

	        	}
	        }
	        ToDoList tdl = new ToDoList(tl, tags);
	        model.resetData(tdl);
	        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS));
	}
}
