package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1"
    		+ "Example: " + COMMAND_WORD + " 1, 2, 3";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted task: %1$s";

    public final ArrayList<Integer> targetIndexes;

    public DeleteCommand(ArrayList<Integer> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }


    @Override
    public CommandResult execute() {
        ArrayList<Integer> pass = new ArrayList<Integer>(targetIndexes);
        Collections.sort(targetIndexes);
        Collections.reverse(targetIndexes);
        //System.out.println(targetIndexes);
        //System.out.println(pass);

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();
        
        //ArrayList<ReadOnlyTask> pass = new ArrayList<ReadOnlyTask>();
        
        for(int i =0; i < targetIndexes.size(); i++){
            if (lastShownList.size() < targetIndexes.get(i)) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyTask personToDelete = lastShownList.get(targetIndexes.get(i) - 1);
            
            //pass.add(personToDelete);
            
            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, pass));
    }

}
