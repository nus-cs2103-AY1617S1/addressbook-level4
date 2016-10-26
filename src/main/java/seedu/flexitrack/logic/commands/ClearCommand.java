package seedu.flexitrack.logic.commands;

import java.util.Stack;

import seedu.flexitrack.commons.core.UnmodifiableObservableList;
import seedu.flexitrack.model.FlexiTrack;
import seedu.flexitrack.model.Model;
import seedu.flexitrack.model.task.ReadOnlyTask;
import seedu.flexitrack.model.task.Task;
import seedu.flexitrack.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Clears the FlexiTrack.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_SHORTCUT = "c";
    public static final String MESSAGE_USAGE = COMMAND_WORD  + ", Shortcut [" + COMMAND_SHORTCUT + "]" + ": Clear the to do lists in FlexiTrack.\n" + "Example: "
            + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "FlexiTrack has been cleared!";

    //TODO: i think only allowed one MODEL 
    private static Model storeDataChange;
    private static Stack<Task> storeDataChanged = new Stack<Task>(); 

    public ClearCommand() {
    }

    @Override
    public CommandResult execute() {
        storeDataChange = model; 
        assert model != null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        for (int index=0; index<lastShownList.size() ; index ++){
            storeDataChanged.add(new Task (lastShownList.get(index)));
        }
        
        model.resetData(FlexiTrack.getEmptyFlexiTrack());
        recordCommand("clear"); 
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    public void executeUndo() {
        model = storeDataChange; 
        while (!storeDataChanged.isEmpty()){
            try {
                model.addTask(storeDataChanged.pop());
            } catch (DuplicateTaskException e) {
                //TODO error 
                e.printStackTrace();
            }
        }
    }
}
