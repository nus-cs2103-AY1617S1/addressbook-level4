package seedu.taskmanager.logic.commands;

import java.util.ArrayList;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.commons.core.UnmodifiableObservableList;
import seedu.taskmanager.model.item.ReadOnlyItem;
import seedu.taskmanager.model.item.UniqueItemList.ItemNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    
    //@@author A0140060A
    public static final String SHORT_COMMAND_WORD = "del";
    //@@author 
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the item identified by the index number used in the last item listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ITEM_SUCCESS = "Deleted Item: %1$s";

    private int targetIndex;
    private ArrayList<Integer> targetIndexes;
    private boolean hasMultipleIndexes = false;

    private ArrayList<ReadOnlyItem> deletedItems = new ArrayList<ReadOnlyItem>();
    
    /*
     * Deletes deadline, task, or event by keyword.
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

    //@@author A0143641M
    /**
     * Deletes events, tasks, or deadlines by a single index or ascending multiple indexes.
     */
    @Override
    public CommandResult execute() {
        
        if(!hasMultipleIndexes){

            UnmodifiableObservableList<ReadOnlyItem> lastShownList = model.getFilteredItemList();
            if (lastShownList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
            }

            ReadOnlyItem personToDelete = lastShownList.get(targetIndex - 1);
            
            try {
                model.deleteItem(personToDelete, String.format(MESSAGE_DELETE_ITEM_SUCCESS, personToDelete));
            } catch (ItemNotFoundException pnfe) {
                assert false : "The target item cannot be missing";
            }
            
            return new CommandResult(String.format(MESSAGE_DELETE_ITEM_SUCCESS, personToDelete));
        }

        else {
            int numDeleted = 1;
            for(int indexToDelete : targetIndexes) {
                UnmodifiableObservableList<ReadOnlyItem> lastShownList = model.getFilteredItemList();
                if (lastShownList.size() < (indexToDelete - numDeleted + 1)) {
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
                }

            ReadOnlyItem personToDelete = lastShownList.get(indexToDelete - numDeleted);
            numDeleted += 1;
            deletedItems.add(personToDelete);
            
            try {
                model.deleteItem(personToDelete, String.format(MESSAGE_DELETE_ITEM_SUCCESS, deletedItems));
            } catch (ItemNotFoundException pnfe) {
                assert false : "The target item cannot be missing";
            }
            }
        return new CommandResult(String.format(MESSAGE_DELETE_ITEM_SUCCESS, deletedItems));
        }
    }
}
