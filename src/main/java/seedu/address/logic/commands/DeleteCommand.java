package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.item.ReadOnlyItem;
import seedu.address.model.item.UniquePersonList.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the item identified by the index number used in the last item listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ITEM_SUCCESS = "Deleted Item: %1$s";

    private int targetIndex;
    private ArrayList<Integer> targetIndexes;
    private boolean hasMultipleIndexes = false;

    private ArrayList<ReadOnlyItem> deletedItems;
    
    /*
     * Deletes deadline, task, or event by index.
     */
    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        hasMultipleIndexes = false;
    }
    
    /*
     * Deletes multiple deadlines, tasks, or events by index.
     */
    public DeleteCommand(ArrayList<Integer> targetIndexes) {
        this.targetIndexes = targetIndexes;
        hasMultipleIndexes = true;
    }

    @Override
    public CommandResult execute() {
        
        if(hasMultipleIndexes){
            int numDeleted = 0;
            for(int indexToDelete : targetIndexes) {
                UnmodifiableObservableList<ReadOnlyItem> lastShownList = model.getFilteredPersonList();
                if (lastShownList.size() < indexToDelete) {
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }

                ReadOnlyItem personToDelete = lastShownList.get(indexToDelete - numDeleted);
                numDeleted += 1;
                deletedItems.add(personToDelete);
                
                try {
                    model.deleteItem(personToDelete);
                } catch (PersonNotFoundException pnfe) {
                    assert false : "The target item cannot be missing";
                }
            }
            return new CommandResult(String.format(MESSAGE_DELETE_ITEM_SUCCESS, deletedItems.toString()));
        }
        else {
            UnmodifiableObservableList<ReadOnlyItem> lastShownList = model.getFilteredPersonList();
            if (lastShownList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyItem personToDelete = lastShownList.get(targetIndex - 1);
            
            try {
                model.deleteItem(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target item cannot be missing";
            }
            
            return new CommandResult(String.format(MESSAGE_DELETE_ITEM_SUCCESS, personToDelete));
        }
    }
}
