package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.PersonNotFoundException;

/** 
 * Edits a person identified using its last displayed index in the task manager.
 * @author Ronald
 *
 */


public class EditCommand extends Command{
    
    public static final String COMMAND_WORD = "edit";
    
    public static final String DESCRIPTION_WORD = "des";
    public static final String DATE_WORD = "date";
    public static final String START_WORD = "start";
    public static final String END_WORD = "end";
    public static final String TAG_WORD = "tag";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an existing task in Simply. "
            +"Parameters: INDEX <section to delete> <edited information>\n" 
            +"Example: " + COMMAND_WORD + " 1 " + DESCRIPTION_WORD + " beach party\t\t"
            +"Example: " + COMMAND_WORD + " 1 " + DATE_WORD + " 120516\n"
            +"Example: " + COMMAND_WORD + " 1 " + START_WORD + " 1600\t\t\t"
            +"Example: " + COMMAND_WORD + " 1 " + END_WORD + " 2300\n"
    		+"Example: " + COMMAND_WORD + " 1 " + TAG_WORD + " sentosa";
    
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s";
    
    public final Integer targetIndex;
    public final String editArgs;

    public EditCommand(Integer index, String args) {
        // TODO Auto-generated constructor stub
        this.targetIndex = index;
        this.editArgs = args;
    }

    @Override
    public CommandResult execute() {
        //ArrayList<Integer> pass = new ArrayList<Integer>(targetIndexes);
        //Collections.sort(targetIndexes);
        //Collections.reverse(targetIndexes);
        //System.out.println(targetIndexes);
        //System.out.println(pass);

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();
        
        //ArrayList<ReadOnlyTask> pass = new ArrayList<ReadOnlyTask>();
        
        /*for(int i =0; i < targetIndexes.size(); i++){
            if (lastShownList.size() < targetIndexes.get(i)) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyTask personToEdit = lastShownList.get(targetIndexes.get(i) - 1);
            
            //pass.add(personToDelete);
            
            try {
                model.editPerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
        }*/
        
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        ReadOnlyTask personToEdit = lastShownList.get(targetIndex - 1);
        
        try {
            model.editPerson(personToEdit, editArgs);
            assert false: "The target task cannot be missing";
        } catch (PersonNotFoundException | IllegalValueException pnfe) {
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, targetIndex));
    }

}
