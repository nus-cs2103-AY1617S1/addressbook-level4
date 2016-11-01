package seedu.taskmanager.logic.commands;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    public EditCommand(int targetIndex, Optional<String> name, Optional<String> startDate, 
                       Optional<String> startTime, Optional<String> endDate, Optional<String> endTime, 
                       Optional<List<String>> tagsToAdd, Optional<List<String>> tagsToRemove)
            throws IllegalValueException {
        
        assert containsInputForAtLeastOneParameter(name, startDate, startTime, endDate, 
                                                    endTime, tagsToAdd, tagsToRemove);
        
        this.targetIndex = targetIndex;
        if (name.isPresent()) {
            this.name = new Name(name.get());
        }
        if (startDate.isPresent()) {
            this.startDate = new ItemDate(startDate.get());
        }
        if (startTime.isPresent()) {
            this.startTime = new ItemTime(startTime.get());
        }
        if (endDate.isPresent()) {
            this.endDate = new ItemDate(endDate.get());
        }
        if (endTime.isPresent()) {
            this.endTime = new ItemTime(endTime.get());
        }
        
        if (tagsToAdd.isPresent()) {
            this.tagsToAdd = createUniqueTagList(tagsToAdd.get());
        }
        
        if (tagsToRemove.isPresent()) {
            this.tagsToRemove = createUniqueTagList(tagsToRemove.get());
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
     * Creates a UniqueTagList from a List of Strings
     */
    private UniqueTagList createUniqueTagList(List<String> tags) throws IllegalValueException {
        assert isToBeEdited(tags);
        
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
     * Checks if itemToReplace is an event and if its end datetime comes before its start datetime 
     */
    private boolean isEventEndDateTimeBeforeStartDateTime(Item itemToReplace) {
        return itemToReplace.getItemType().isAnEvent() 
               && isEndDateTimeBeforeStartDateTime(itemToReplace.getStartDate(), itemToReplace.getStartTime(), 
                                                   itemToReplace.getEndDate(), itemToReplace.getEndTime());
    }

    /**
     * Removes tags from item being edited if possible 
     * @return tag not found command result if an attempt to remove a non existent tag is made
     */
    private void removeTagsIfApplicable(ReadOnlyItem itemToEdit, Item itemToReplace) throws TagNotFoundException{
        if (isToBeEdited(this.tagsToRemove)) {
            UniqueTagList tagListToEdit = getTagListToEditForTagRemoval(itemToEdit, itemToReplace);
            tagListToEdit.remove(tagsToRemove);
            itemToReplace.setTags(tagListToEdit);
        } 
    }

    /**
     * Gets appropriate tag list to remove tags from (dependent on whether user is adding tags in same command)
     */
    private UniqueTagList getTagListToEditForTagRemoval(ReadOnlyItem itemToEdit, Item itemToReplace) {
        if (isToBeEdited(this.tagsToAdd)) {
            return itemToReplace.getTags();
        } else {
            return itemToEdit.getTags();
        }
    }

    /**
     * Adds tags to item being edited if possible 
     */
    private void addTagsIfAvailable(ReadOnlyItem itemToEdit, Item itemToReplace) {
        if (isToBeEdited(this.tagsToAdd)) {
            UniqueTagList tagListToEdit = itemToEdit.getTags();
            tagListToEdit.mergeFrom(this.tagsToAdd);
            itemToReplace.setTags(tagListToEdit);
        }
    }

    private void setEndTimeIfAvailable(Item itemToReplace) {
        if (isToBeEdited(this.endTime)) {
            itemToReplace.setEndTime(this.endTime);
        }
    }

    private void setEndDateIfAvailable(Item itemToReplace) {
        if (isToBeEdited(this.endDate)) {
            itemToReplace.setEndDate(this.endDate);
        }
    }

    private void setStartTimeIfAvailable(Item itemToReplace) {
        if (isToBeEdited(this.startTime)) {
            itemToReplace.setStartTime(this.startTime);
        }
    }

    private void setStartDateIfAvailable(Item itemToReplace) {
        if (isToBeEdited(this.startDate)) {
            itemToReplace.setStartDate(this.startDate);
        }
    }

    private void setNameIfAvailable(Item itemToReplace) {
        if (isToBeEdited(this.name)) {
            itemToReplace.setName(this.name);
        }
    }

    /**
     * Checks if end datetime comes before start datetime
     */
    private boolean isEndDateTimeBeforeStartDateTime(ItemDate startItemDate, ItemTime startItemTime, ItemDate endItemDate, ItemTime endItemTime) {
        if (isEndDateEqualsStartDate(startItemDate, endItemDate)) {
            return isEndTimeBeforeStartTime(startItemTime, endItemTime);
        } else {
            return isEndDateBeforeStartDate(startItemDate, endItemDate);
        }
    }
    
    /**
     * Checks if endItemDate comes before startItemDate, false otherwise
     */
    private boolean isEndDateBeforeStartDate(ItemDate startItemDate, ItemDate endItemDate) {
        return compareStartDateToEndDate(startItemDate, endItemDate) < 0;
    }
    
    /**
     * Checks if endItemDate is equal to startItemDate, false otherwise
     */
    private boolean isEndDateEqualsStartDate(ItemDate startItemDate, ItemDate endItemDate) {
        return compareStartDateToEndDate(startItemDate, endItemDate) == 0;
    }
    
    /**
     * Compares start date to end date
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
     * Checks if endItemTime comes before startItemTime, false otherwise
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
     * Detects if parameters input by user is not valid for the item being edited
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
     * Checks if at least one parameter contains input
     */
    private boolean containsInputForAtLeastOneParameter(Optional<String> name, Optional<String> startDate,
                        Optional<String> startTime, Optional<String> endDate, Optional<String> endTime, 
                        Optional<List<String>> tagsToAdd, Optional<List<String>> tagsToRemove) {
        
        return name.isPresent() || startDate.isPresent() || startTime.isPresent() || endDate.isPresent() 
               || endTime.isPresent() || tagsToAdd.isPresent() || tagsToRemove.isPresent();
    }

    /**
     * Checks if argument is to be edited
     */
    private boolean isToBeEdited(Object argument) {
        return argument != null;
    }
    
}
