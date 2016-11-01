package seedu.taskmanager.logic.commands;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import seedu.taskmanager.model.tag.Tag;
import seedu.taskmanager.model.tag.UniqueTagList;
import seedu.taskmanager.model.tag.UniqueTagList.DuplicateTagException;
import seedu.taskmanager.model.tag.UniqueTagList.TagNotFoundException;

//@@author A0140060A

/**
 * Edits an item identified using it's last displayed index from the task manager.
 */
public class EditCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(EditCommand.class);
    public static final String COMMAND_WORD = "edit";
    public static final String SHORT_COMMAND_WORD = "e";

    public static final String MESSAGE_USAGE = COMMAND_WORD
                                               + ": Edits the item identified by the index number used in the last item listing."
                                               + "\n" + "Parameters: INDEX (positive integer)" 
                                               + " n/[NAME]"
                                               + " sdt/[START_DATE_TIME]"
                                               + " sd/[START_DATE]"
                                               + " st/[START_TIME]"
                                               + "\n" + "                     " + "edt/[END_DATE_TIME]"
                                               + " ed/[END_DATE]"
                                               + " et/[END_TIME]"
                                               + "\n" + "Editable parameters are restricted to those the item was created with."
                                               + "\n" + "At least one parameter must be specifed (sdt/edt favoured over sd/st/ed/et)"
                                               + "\n" + "Example: " + COMMAND_WORD + " 1" + " n/buy milk";
    
    public static final String MESSAGE_EDIT_ITEM_SUCCESS = "Edited %1$s";
    public static final String MESSAGE_NOTHING_EDITED = "Nothing was changed!";
    
    private int targetIndex;
    private Name name;
    private ItemDate startDate;
    private ItemTime startTime;
    private ItemDate endDate;
    private ItemTime endTime;
    private UniqueTagList tagsToAdd;
    private UniqueTagList tagsToRemove;

    /*
     * Edits deadline, task, or event by index.
     *      
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(int targetIndex, String name, String startDate, String startTime, 
                       String endDate, String endTime, List<String> tagsToAdd, List<String> tagsToRemove)
            throws IllegalValueException {
        
        assert (containsInputForAtLeastOneParameter(name, startDate, startTime, endDate, 
                                                    endTime, tagsToAdd, tagsToRemove));
        
        this.targetIndex = targetIndex;
        if (containsInput(name)) {
            this.name = new Name(name);
        }
        if (containsInput(startDate)) {
            this.startDate = new ItemDate(startDate);
        }
        if (containsInput(startTime)) {
            this.startTime = new ItemTime(startTime);
        }
        if (containsInput(endDate)) {
            this.endDate = new ItemDate(endDate);
        }
        if (containsInput(endTime)) {
            this.endTime = new ItemTime(endTime);
        }
        
        if (containsInput(tagsToAdd)) {
            this.tagsToAdd = createUniqueTagList(tagsToAdd);
        }
        
        if (containsInput(tagsToRemove)) {
            this.tagsToRemove = createUniqueTagList(tagsToRemove);
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
        
        setNameIfAvailable(itemToReplace);
        setStartDateIfAvailable(itemToReplace);
        setStartTimeIfAvailable(itemToReplace);
        setEndDateIfAvailable(itemToReplace);
        setEndTimeIfAvailable(itemToReplace);
        addTagsIfAvailable(itemToEdit, itemToReplace);
        
        try {
            removeTagsIfApplicable(itemToEdit, itemToReplace);
        } catch (TagNotFoundException tnfe) {
            return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, tnfe.getMessage()));
        }

        if (isEventEndDateTimeBeforeStartDateTime(itemToReplace)) {
            logger.fine("detected event end datetime before start datetime");
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_END_DATE_TIME_BEFORE_START_DATE_TIME));
        }
        
        try {
            model.replaceItem(itemToEdit, itemToReplace, String.format(MESSAGE_EDIT_ITEM_SUCCESS, itemToReplace));
        } catch (ItemNotFoundException pnfe) {
            assert false : "The target item cannot be missing";
        } catch (UniqueItemList.DuplicateItemException e) {
            return new CommandResult(MESSAGE_NOTHING_EDITED);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_ITEM_SUCCESS, itemToReplace));
    }

    /**
     * @param tags
     * @param tagListToPopulate 
     * @throws IllegalValueException
     * @return created unique tag list
     */
    private UniqueTagList createUniqueTagList(List<String> tags) throws IllegalValueException {
        assert containsInput(tags);
        
        UniqueTagList uniqueTagList = new UniqueTagList();
        for (String tag : tags) {
            try {
                uniqueTagList.add(new Tag(tag));
            } catch (DuplicateTagException dte) {
            }
        }
        return uniqueTagList;
    }
    
    /**
     * @param itemToReplace
     * @return true if itemToReplace is an event and its end datetime comes before its start datetime 
     */
    private boolean isEventEndDateTimeBeforeStartDateTime(Item itemToReplace) {
        return itemToReplace.getItemType().isAnEvent() 
               && isEndDateTimeBeforeStartDateTime(itemToReplace.getStartDate(), itemToReplace.getStartTime(), 
                                                   itemToReplace.getEndDate(), itemToReplace.getEndTime());
    }

    /**
     * @param itemToEdit
     * @param itemToReplace
     * @return tag not found command result if an attempt to remove a non existent tag is made
     */
    private void removeTagsIfApplicable(ReadOnlyItem itemToEdit, Item itemToReplace) throws TagNotFoundException{
        if (containsInput(this.tagsToRemove)) {
            UniqueTagList tagListToEdit = getTagListToEditForTagRemoval(itemToEdit, itemToReplace);
            tagListToEdit.remove(tagsToRemove);
            itemToReplace.setTags(tagListToEdit);
        } 
    }

    /**
     * @param itemToEdit
     * @param itemToReplace
     * @return appropriate tag list to remove tags from 
     */
    private UniqueTagList getTagListToEditForTagRemoval(ReadOnlyItem itemToEdit, Item itemToReplace) {
        if (containsInput(this.tagsToAdd)) {
            return itemToReplace.getTags();
        } else {
            return itemToEdit.getTags();
        }
    }

    /**
     * @param itemToEdit
     * @param itemToReplace
     */
    private void addTagsIfAvailable(ReadOnlyItem itemToEdit, Item itemToReplace) {
        if (containsInput(this.tagsToAdd)) {
            UniqueTagList tagListToEdit = itemToEdit.getTags();
            tagListToEdit.mergeFrom(this.tagsToAdd);
            itemToReplace.setTags(tagListToEdit);
        }
    }

    /**
     * @param itemToReplace
     */
    private void setEndTimeIfAvailable(Item itemToReplace) {
        if (containsInput(this.endTime)) {
            itemToReplace.setEndTime(this.endTime);
        }
    }

    /**
     * @param itemToReplace
     */
    private void setEndDateIfAvailable(Item itemToReplace) {
        if (containsInput(this.endDate)) {
            itemToReplace.setEndDate(this.endDate);
        }
    }

    /**
     * @param itemToReplace
     */
    private void setStartTimeIfAvailable(Item itemToReplace) {
        if (containsInput(this.startTime)) {
            itemToReplace.setStartTime(this.startTime);
        }
    }

    /**
     * @param itemToReplace
     */
    private void setStartDateIfAvailable(Item itemToReplace) {
        if (containsInput(this.startDate)) {
            itemToReplace.setStartDate(this.startDate);
        }
    }

    /**
     * @param itemToReplace
     */
    private void setNameIfAvailable(Item itemToReplace) {
        if (containsInput(this.name)) {
            itemToReplace.setName(this.name);
        }
    }

    /**
     * @param startItemDate
     * @param endItemDate
     * @param startItemTime
     * @param endItemTime
     * @return true if end datetime comes before start datetime
     */
    private boolean isEndDateTimeBeforeStartDateTime(ItemDate startItemDate, ItemTime startItemTime, ItemDate endItemDate, ItemTime endItemTime) {
        if (isEndDateEqualsStartDate(startItemDate, endItemDate)) {
            return isEndTimeBeforeStartTime(startItemTime, endItemTime);
        } else {
            return isEndDateBeforeStartDate(startItemDate, endItemDate);
        }
    }
    
    /**
     * @param startItemDate
     * @param endItemDate
     * @return true if endItemDate comes before startItemDate, false otherwise
     */
    private boolean isEndDateBeforeStartDate(ItemDate startItemDate, ItemDate endItemDate) {
        return compareStartDateToEndDate(startItemDate, endItemDate) < 0;
    }
    
    /**
     * @param startItemDate
     * @param endItemDate
     * @return true if endItemDate is equal to startItemDate, false otherwise
     */
    private boolean isEndDateEqualsStartDate(ItemDate startItemDate, ItemDate endItemDate) {
        return compareStartDateToEndDate(startItemDate, endItemDate) == 0;
    }
    
    /**
     * @param startItemDate
     * @param endItemDate
     * @return -1 if endItemDate comes before startItemDate, 0 if endItemDate equals startItemDate, 1 otherwise
     */
    private int compareStartDateToEndDate(ItemDate startItemDate, ItemDate endItemDate) {
        int result = 1;
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(ItemDate.DATE_FORMAT);
            Date startDate = sdf.parse(startItemDate.toString());
            Date endDate = sdf.parse(endItemDate.toString());
            if (endDate.before(startDate)) {
                result = -1;
            } else if (endDate.equals(startDate)) {
                result = 0;
            }
        } catch (ParseException pe) {
            assert false : "Given date(s) is/are not parsable by SimpleDateFormat";
        }
        
        return result;
    }
    
    
    /**
     * @param startItemTime
     * @param endItemTime
     * @return true if endItemTime comes before startItemTime, false otherwise
     */
    private boolean isEndTimeBeforeStartTime(ItemTime startItemTime, ItemTime endItemTime) {
        boolean result = true;
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(ItemTime.TIME_FORMAT);
            Date startTime= sdf.parse(startItemTime.toString());
            Date endTime = sdf.parse(endItemTime.toString());
            result = endTime.before(startTime);
        } catch (ParseException pe) {
            assert false : "Given time(s) is not parsable by SimpleDateFormat";
        }
        
        return result;
    }

    /**
     * @param itemToReplace
     * @return true if parameters input by user is not valid for the item that is being edited
     */
    private boolean isInvalidInputForItemType(ReadOnlyItem itemToReplace) { 
        String itemType = itemToReplace.getItemType().toString();
        assert itemType.equals(ItemType.TASK_WORD) 
               || itemType.equals(ItemType.DEADLINE_WORD) 
               || itemType.equals(ItemType.EVENT_WORD);
        
        boolean result = true;
        
        if (itemType.equals(ItemType.TASK_WORD)) {
            result = startDate != null || startTime != null || endDate != null || endTime != null;
        }
        if (itemType.equals(ItemType.DEADLINE_WORD)) {
            result = startDate != null || startTime != null;
        }
        if (itemType.equals(ItemType.EVENT_WORD)) {
            result = false;
        }
        
        return result;
    }

    /**
     * @param name
     * @param startDate
     * @param startTime
     * @param endDate
     * @param endTime
     * @param tagsToAdd
     * @param tagsToRemove
     * @return true if at least one parameter contains user input
     */
    private boolean containsInputForAtLeastOneParameter(String name, String startDate, String startTime,
            String endDate, String endTime, List<String> tagsToAdd, List<String> tagsToRemove) {
        return containsInput(name) || containsInput(startDate) || containsInput(startTime) 
               || containsInput(endDate) || containsInput(endTime) || containsInput(tagsToAdd) 
               || containsInput(tagsToRemove);
    }

    /**
     * Checks if argument contains user input
     * @param argument argument to be checked
     */
    private boolean containsInput(Object argument) {
        return argument != null;
    }
    
}
