package seedu.taskmanager.logic.commands;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.*;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.commons.core.UnmodifiableObservableList;
import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.item.Item;
import seedu.taskmanager.model.item.ItemDate;
import seedu.taskmanager.model.item.ItemTime;
import seedu.taskmanager.model.item.ItemType;
import seedu.taskmanager.model.item.Name;
import seedu.taskmanager.model.item.ReadOnlyItem;
import seedu.taskmanager.model.item.UniqueItemList;
import seedu.taskmanager.model.item.UniqueItemList.ItemNotFoundException;

/**
 * Edits an item identified using it's last displayed index from the task manager.
 */
public class EditCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(EditCommand.class);
    public static final String COMMAND_WORD = "edit";
    public static final String SHORT_COMMAND_WORD = "e";

    public static final String MESSAGE_USAGE = COMMAND_WORD
                                               + ": Edits the item identified by the index number used in the last item listing.\n"
                                               + "Editable parameters are restricted to those the item was created with. \n"
                                               + "Parameters: INDEX (must be a positive integer)" 
                                               + " n/[NAME]"
                                               + " sd/[START_DATE]"
                                               + " st/[START_TIME]"
                                               + " ed/[END_DATE]"
                                               + " et/[END_TIME]"
                                               + " (at least one parameter must be specifed)"
                                               + "\n" + "Example: " + COMMAND_WORD + " 1" + " n/buy milk";

    public static final String MESSAGE_EDIT_ITEM_SUCCESS = "Edited %1$s";
    
    int targetIndex;
    Name newName;
    ItemDate newStartDate;
    ItemTime newStartTime;
    ItemDate newEndDate;
    ItemTime newEndTime;

    /*
     * Edits deadline, task, or event by index.
     *      
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(int targetIndex, String name, String startDate, String startTime, String endDate, String endTime)
            throws IllegalValueException {
        
        //At least one parameter is being edited
        assert (name != null || startDate != null || startTime!= null || endDate != null || endTime != null);
        
        this.targetIndex = targetIndex;
        if (name != null) {
            this.newName = new Name(name);
        }
        if (startDate != null) {
            this.newStartDate = new ItemDate(startDate);
        }
        if (startTime != null) {
            this.newStartTime = new ItemTime(startTime);
        }
        if (endDate != null) {
            this.newEndDate = new ItemDate(endDate);
        }
        if (endTime != null) {
            this.newEndTime = new ItemTime(endTime);
        }
        
        logger.fine("EditCommand object successfully created!");
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyItem> lastShownList = model.getFilteredItemList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        ReadOnlyItem itemToEdit = lastShownList.get(targetIndex - 1);
        Item itemToReplace = new Item(itemToEdit);
        
        if (isInvalidInputForItemType(itemToReplace)) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        
        if (newName != null) {
            itemToReplace.setName(newName);
        }
        if (newStartDate != null) {
            itemToReplace.setStartDate(newStartDate);
        }
        if (newStartTime != null) {
            itemToReplace.setStartTime(newStartTime);
        }
        if (newEndDate != null) {
            itemToReplace.setEndDate(newEndDate);
        }
        if (newEndTime != null) {
            itemToReplace.setEndTime(newEndTime);
        }
        
        try {
            model.replaceItem(itemToEdit, itemToReplace, String.format(MESSAGE_EDIT_ITEM_SUCCESS, itemToReplace));
        } catch (ItemNotFoundException pnfe) {
            assert false : "The target item cannot be missing";
        } catch (UniqueItemList.DuplicateItemException e) {
            return new CommandResult(MESSAGE_DUPLICATE_ITEM);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_ITEM_SUCCESS, itemToReplace));
    }

    /**
     * @param itemToReplace
     * @return true if parameters input by user is not valid for the item that is being edited
     */
    private boolean isInvalidInputForItemType(Item itemToReplace) { 
        String itemType = itemToReplace.getItemType().toString();
        assert itemType.equals(ItemType.TASK_WORD) 
               || itemType.equals(ItemType.DEADLINE_WORD) 
               || itemType.equals(ItemType.EVENT_WORD);
        
        if (itemType.equals(ItemType.TASK_WORD)) {
            return newStartDate != null || newStartTime != null || newEndDate != null || newEndTime != null;
        }
        if (itemType.equals(ItemType.DEADLINE_WORD)) {
            return newStartDate != null || newStartTime != null;
        }
        if (itemType.equals(ItemType.EVENT_WORD)) {
            return false;
        }
        
        return true;
    }

}
