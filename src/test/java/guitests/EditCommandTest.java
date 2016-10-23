package guitests;

import org.junit.Before;
import org.junit.Test;

import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.logic.commands.EditCommand;
import seedu.taskmanager.model.item.ItemDate;
import seedu.taskmanager.model.item.ItemType;
import seedu.taskmanager.model.item.Name;
import seedu.taskmanager.model.tag.Tag;
import seedu.taskmanager.model.tag.UniqueTagList;
import seedu.taskmanager.model.tag.UniqueTagList.DuplicateTagException;
import seedu.taskmanager.model.item.ItemTime;
import seedu.taskmanager.testutil.TestItem;
import seedu.taskmanager.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX;
import static seedu.taskmanager.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.taskmanager.logic.commands.EditCommand.MESSAGE_EDIT_ITEM_SUCCESS;
import static seedu.taskmanager.logic.commands.EditCommand.MESSAGE_USAGE;
import static seedu.taskmanager.logic.commands.EditCommand.COMMAND_WORD;
import static seedu.taskmanager.logic.commands.EditCommand.SHORT_COMMAND_WORD;
import static seedu.taskmanager.logic.commands.EditCommand.MESSAGE_TAG_NOT_FOUND;
import static seedu.taskmanager.model.item.ItemTime.MESSAGE_TIME_CONSTRAINTS;
import static seedu.taskmanager.model.item.ItemDate.MESSAGE_DATE_CONSTRAINTS;
import static seedu.taskmanager.logic.commands.Command.MESSAGE_DUPLICATE_ITEM;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class EditCommandTest extends TaskManagerGuiTest {
    private static final Logger logger = LogsCenter.getLogger(EditCommandTest.class);
    
    private String firstTestName = "Survive 2103";
    private String secondTestName = "Survive the semester";
    private String thirdTestName = "Survive university";
    private String validStartDate = "1900-05-05";
    private String validStartTime = "22:06";
    private String validEndDate = "2320-12-05";
    private String validEndTime = "23:06";
    private String invalidTime = "46:99";
    private String invalidDate = "12345679";
    private String nonexistentTag = "nonexistent";
    private UniqueTagList tagsToAdd = new UniqueTagList();
    private UniqueTagList tagsToRemove = new UniqueTagList();
    TestItem[] currentList;

    @Before
    public void setUp() throws Exception {
        tagsToAdd.add(new Tag("work"));
        tagsToRemove.add(new Tag("play"));
        
        
    }
    
    @Test
    public void edit_Success() throws IllegalValueException {
        //edit the first in the list which is an event
        TestItem[] currentList = td.getTypicalItems();
        int targetIndex = 1;
        TestItem itemToEdit = currentList[targetIndex-1];
        
        assertEditSuccess(COMMAND_WORD, targetIndex, firstTestName, null, null, null, null, null, null, currentList);
        assertEditSuccess(SHORT_COMMAND_WORD, targetIndex, null, validStartDate, null, null, null, null, tagsToRemove, currentList);
        
        itemToEdit.setName(new Name(firstTestName));
        itemToEdit.setStartDate(new ItemDate(validStartDate));
        currentList = TestUtil.replaceItemFromList(currentList, itemToEdit, targetIndex-1);
    
        //edit the last in the list which is an event
        targetIndex = currentList.length;
        assertEditSuccess(COMMAND_WORD, targetIndex, null, null, null, validEndDate, null, tagsToAdd, null, currentList);
        tagsToRemove = new UniqueTagList();
        tagsToRemove.add(new Tag("work"));
        assertEditSuccess(COMMAND_WORD, targetIndex, secondTestName, null, null, null, null, tagsToAdd, tagsToRemove, currentList);
        
        tagsToAdd.add(new Tag("notplay"));
        
        assertEditSuccess(COMMAND_WORD, targetIndex, null, null, null, null, validEndTime, tagsToAdd, null, currentList);
        assertEditSuccess(SHORT_COMMAND_WORD, targetIndex, null, null, validStartTime, null, null, null, null, currentList);
        assertEditSuccess(SHORT_COMMAND_WORD, targetIndex, null, validStartDate, null, null, null, null, null, currentList);
        
        itemToEdit = currentList[targetIndex-1];
        itemToEdit.setName(new Name(secondTestName));
        currentList = TestUtil.replaceItemFromList(currentList, itemToEdit, targetIndex-1);
        
        //edit from the middle of the list which is a task
        targetIndex = currentList.length/2;
        itemToEdit = currentList[targetIndex-1];
        
        assertEditSuccess(COMMAND_WORD, targetIndex, thirdTestName, null, null, null, null, tagsToAdd, null, currentList);
        
        itemToEdit.setName(new Name(thirdTestName));
        currentList = TestUtil.replaceItemFromList(currentList, itemToEdit, targetIndex-1);

        //edit from the middle of the list which is a deadline
        targetIndex = 8;
        
        assertEditSuccess(COMMAND_WORD, targetIndex, null, null, null, validEndDate, null, null, null, currentList);
        assertEditSuccess(SHORT_COMMAND_WORD, targetIndex, thirdTestName, null, null, null, null, tagsToAdd, null, currentList);
        
        tagsToRemove.add(new Tag("notwork"));
        
        assertEditSuccess(COMMAND_WORD, targetIndex, null, null, null, null, validEndTime, tagsToAdd, null, currentList);        
    }
    
    @Test
    public void edit_enterInvalidIndex_invalidCommandMessage() {
        //invalid commands
        commandBox.runCommand("edit notAnIndex");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
    
    @Test
    public void edit_enterInvalidCommandWord_unknownCommandMessage() {
        commandBox.runCommand("edits 1");
        assertResultMessage(MESSAGE_UNKNOWN_COMMAND);
    }
        
    @Test
    public void edit_noParameterSpecified_invalidCommandMessage() {
        commandBox.runCommand("edit 1");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        
    }
    
    @Test
    public void edit_invalidParameterSpecified_invalidCommandMessage() {
        TestItem[] currentList = td.getTypicalItems();
        //invalid parameters for item type task
        commandBox.runCommand("edit " + (currentList.length/2 + 1) + " et/" + validEndTime);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        
        commandBox.runCommand("edit " + (currentList.length/2 + 1) + " ed/" + validEndDate);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        
        commandBox.runCommand("edit " + currentList.length/2 + " st/" + validStartTime);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        
        commandBox.runCommand("edit " + currentList.length/2 + " sd/" + validStartDate);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        
        //invalid parameters for item type deadline
        commandBox.runCommand("edit 2  st/" + validStartTime);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        
        commandBox.runCommand("edit 2 sd/" + validStartDate);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }
    
    
    @Test
    public void edit_addDuplicateTag_duplicateItemMessage() {
        commandBox.runCommand("edit 2 #work");
        assertResultMessage(MESSAGE_DUPLICATE_ITEM);
    }
    
    @Test
    public void edit_deleteNonExistentTag_TagNotFoundMessage() {
        commandBox.runCommand("edit 2 #-" + nonexistentTag );
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                            String.format(MESSAGE_TAG_NOT_FOUND, "[" + nonexistentTag + "]")));
    }
       
    @Test
    public void edit_invalidIndexSpecified_invalidIndexMessage() {
        TestItem[] currentList = td.getTypicalItems();
        //invalid index
        commandBox.runCommand("edit " + currentList.length + 1 + " n/" + firstTestName);
        assertResultMessage(MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }
    
    @Test
    public void edit_invalidTimeFormat_timeConstraintsMessage() {
        TestItem[] currentList = td.getTypicalItems();
        //invalid time
        commandBox.runCommand("edit " + currentList.length + " et/" + invalidTime);
        assertResultMessage(MESSAGE_TIME_CONSTRAINTS);
    }

    @Test
    public void edit_invalidDateFormat_dateConstraintsMessage() {
        TestItem[] currentList = td.getTypicalItems();
        //invalid date
        commandBox.runCommand("edit " + currentList.length + " ed/" + invalidDate);
        assertResultMessage(MESSAGE_DATE_CONSTRAINTS);
    }

    /**
     * Runs the edit command to edit the item at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to edit the first item in the list, 1 should be given as the target index.
     * @param tagsToAdd TODO
     * @param tagsToRemove TODO
     * @param currentList A copy of the current list of items (before editing).
     */
    private void assertEditSuccess(String commandWord, int targetIndexOneIndexed, String name, 
                                   String startDate, String startTime, String endDate, 
                                   String endTime, UniqueTagList tagsToAdd, UniqueTagList tagsToRemove, final TestItem[] currentList) throws IllegalValueException {
        TestItem itemToEdit = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        if (name != null) {
            itemToEdit.setName(new Name(name));
        }
        if (startDate != null) {
            itemToEdit.setStartDate(new ItemDate(startDate));
        }
        if (startTime != null) {
            itemToEdit.setStartTime(new ItemTime(startTime));
        }
        if (endDate != null) {
            itemToEdit.setEndDate(new ItemDate(endDate));
        }
        if (endTime != null) {
            itemToEdit.setEndTime(new ItemTime(endTime));
        }
        if (tagsToAdd != null) {
            UniqueTagList tagsToEdit = itemToEdit.getTags();
            tagsToEdit.mergeFrom(tagsToAdd);
            itemToEdit.setTags(tagsToEdit);
        }
        if (tagsToRemove != null) {
            UniqueTagList tagsToEdit = itemToEdit.getTags();
            UniqueTagList updatedTags = new UniqueTagList();
            for (Tag tag : tagsToEdit) {
                if (!tagsToRemove.contains(tag)) {
                    try {
                        updatedTags.add(tag);
                    } catch (DuplicateTagException dte) {
                    }
                }
            }
            itemToEdit.setTags(updatedTags);
        }
        
        TestItem[] expectedList = TestUtil.replaceItemFromList(currentList, itemToEdit, targetIndexOneIndexed-1);
        
        final StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append(commandWord + " " + targetIndexOneIndexed);
        if (name != null) {
            commandBuilder.append(" n/")
                          .append(name);
        }
        if (startDate != null) {
            commandBuilder.append(" sd/")
                          .append(startDate);
        }
        if (startTime != null) {
            commandBuilder.append(" st/")
                          .append(startTime);
        }
        if (endDate != null) {
            commandBuilder.append(" ed/")
                          .append(endDate);
        }
        if (endTime != null) {
            commandBuilder.append(" et/")
                          .append(endTime);
        }
        if (tagsToAdd != null) {
            Set<Tag> tagsToAddSet = tagsToAdd.toSet();
            for (Tag tag : tagsToAddSet) {
                String tagString = tag.toString();
                assert tagString.length() >= 2;
                tagString = tagString.substring(1, tagString.length() - 1);
                commandBuilder.append(" #")
                              .append(tagString);
            }
        }
        if (tagsToRemove != null) {
            Set<Tag> tagsToRemoveSet = tagsToRemove.toSet();
            for (Tag tag : tagsToRemoveSet) {
                String tagString = tag.toString();
                assert tagString.length() >= 2;
                tagString = tagString.substring(1, tagString.length() - 1);
                commandBuilder.append(" #-")
                              .append(tagString);

            }
        }
        
        logger.info(commandBuilder.toString());
        commandBox.runCommand(commandBuilder.toString());

        //confirm the list now contains all items including the edited item
        assertTrue(shortItemListPanel.isListMatching(expectedList));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_EDIT_ITEM_SUCCESS, itemToEdit));
    }

}
