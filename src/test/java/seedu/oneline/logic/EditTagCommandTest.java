// @@author A0140156R

package seedu.oneline.logic;

import org.junit.Test;

import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.ReadOnlyTaskBook;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.TagColor;
import seedu.oneline.model.task.Task;
import seedu.oneline.model.task.TaskName;
import seedu.oneline.model.task.TaskRecurrence;
import seedu.oneline.model.task.TaskTime;

public class EditTagCommandTest extends LogicTestManager {
    
    Tag tag;
    Tag newTag;
    Task task;
    Task taskNewTag;
    TagColor color;
    TagColor newColor;
    TaskBook taskBookEmpty;
    TaskBook taskBookWithTag;
    TaskBook taskBookWithNewTag;
    TaskBook taskBookWithNewColor;
    TaskBook taskBookWithNewTagAndColor;
    
    {
        try {
            tag = Tag.getTag("Tag");
            newTag = Tag.getTag("NewTag");
            color = new TagColor("red");
            newColor = new TagColor("blue");
            task = new Task(new TaskName("Task"),
                    TaskTime.getDefault(), TaskTime.getDefault(), TaskTime.getDefault(), 
                    TaskRecurrence.getDefault(), tag);
            taskNewTag = new Task(new TaskName("Task"),
                    TaskTime.getDefault(), TaskTime.getDefault(), TaskTime.getDefault(), 
                    TaskRecurrence.getDefault(), newTag);
            taskBookEmpty = new TaskBook();
            taskBookWithTag = new TaskBook(taskBookEmpty);
            taskBookWithTag.addTask(task);
            taskBookWithTag.setTagColor(tag, color);
            taskBookWithNewTag = new TaskBook(taskBookEmpty);
            taskBookWithNewTag.addTask(taskNewTag);
            taskBookWithNewTag.setTagColor(newTag, color);
            taskBookWithNewColor = new TaskBook(taskBookEmpty);
            taskBookWithNewColor.addTask(task);
            taskBookWithNewColor.setTagColor(tag, newColor);
            taskBookWithNewTagAndColor = new TaskBook(taskBookEmpty);
            taskBookWithNewTagAndColor.addTask(taskNewTag);
            taskBookWithNewTagAndColor.setTagColor(newTag, newColor);
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false;
        }
    }
    
    //---------------- Tests for EditTagCommand --------------------------------------
    /*
     * Format: edit #CurrentTag [#NewTag] [Color] 
     * 
     * == Equivalence partition ==
     * 
     * == CurrentTag ==
     * Invalid :    (1A) Values invalid for use as tags
     * Invalid :    (1B) Valid tag which is not in tag list
     * Valid   :    (1C) Valid tag in the tag list
     * 
     * == NewTag ==
     * Invalid :    (2A) Values invalid for use as tags
     * Invalid :    (2B) Valid tag which is already in the tag list
     * Invalid :    (2C) NewTag and Color not specified
     * Valid   :    (2D) Valid tag not already in the tag list
     * Valid   :    (2E) NewTag not specified, but Color is specified
     * 
     * == Color ==
     * Invalid :    (3A) Invalid Color
     * Invalid :    (3B) Color and NewTag not specified
     * Valid   :    (3C) Valid Color
     * Valid   :    (3D) Color not specified, but NewTag is specified
     * 
     */
    
    @Test
    public void editTag_allValid_updateFields() throws Exception {
        ReadOnlyTaskBook expected = null;
        // Testing combination (1C) (2D) (3C)
        model.resetData(taskBookWithTag);
        expected = taskBookWithNewTagAndColor;
        assertCommandBehavior("edit #Tag #NewTag blue",
                "Category updated: Tag renamed to NewTag, color updated to blue",
                expected, expected.getTaskList());
        // Testing combination (1C) (2D) (3D)
        model.resetData(taskBookWithTag);
        expected = taskBookWithNewTag;
        assertCommandBehavior("edit #Tag #NewTag",
                "Category updated: Tag renamed to NewTag",
                expected, expected.getTaskList());
        // Testing combination (1C) (2E) (3C)
        model.resetData(taskBookWithTag);
        expected = taskBookWithNewColor;
        assertCommandBehavior("edit #Tag blue",
                "Category updated: Tag color updated to blue",
                expected, expected.getTaskList());
    }
    
    @Test
    public void editTag_invalidCurTag_returnError() throws Exception {
        model.resetData(taskBookWithTag);
        ReadOnlyTaskBook expected = taskBookWithTag;
        
        // Testing combination (1A) (2D) (3C)
        assertCommandBehavior("edit #Invalid_Tag_Name #NewTag blue",
                Tag.MESSAGE_TAG_CONSTRAINTS,
                expected, expected.getTaskList());
        
        // Testing combination (1B) (2D) (3C)
        assertCommandBehavior("edit #NonexistentTag #NewTag blue",
                Messages.MESSAGE_EDIT_TAG_TAG_NOT_FOUND,
                expected, expected.getTaskList());
    }
    
    @Test
    public void editTag_invalidNewTag_returnError() throws Exception {
        model.resetData(taskBookWithTag);
        ReadOnlyTaskBook expected = taskBookWithTag;
        
         // Testing combination (1C) (2A) (3D)
        assertCommandBehavior("edit #Tag #Invalid_Tag",
                Tag.MESSAGE_TAG_CONSTRAINTS,
                expected, expected.getTaskList());
        
         // Testing combination (1C) (2B) (3D)
        assertCommandBehavior("edit #Tag #Tag",
                String.format(Tag.MESSAGE_DUPLICATE_TAG, "Tag"),
                expected, expected.getTaskList());
        
         // Testing combination (1C) (2C) (3B)
        assertCommandBehavior("edit",
                Messages.MESSAGE_EDIT_TAG_ARGS_INVALID_FORMAT,
                expected, expected.getTaskList());
    }
    
    @Test
    public void editTag_invalidColor_returnError() throws Exception {
        model.resetData(taskBookWithTag);
        ReadOnlyTaskBook expected = taskBookWithTag;
        
     // Testing combination (1C) (2E) (3C)
        assertCommandBehavior("edit #Tag NotARealColor",
                TagColor.MESSAGE_COLOR_CONSTRAINTS,
                expected, expected.getTaskList());
        
         // Testing combination (1C) (2C) (3B)
        assertCommandBehavior("edit",
                Messages.MESSAGE_EDIT_TAG_ARGS_INVALID_FORMAT,
                expected, expected.getTaskList());
    }
    
    @Test
    public void editTag_invalidInputs_returnError() throws Exception {
        model.resetData(taskBookWithTag);
        ReadOnlyTaskBook expected = taskBookWithTag;

        // Testing combination (1C) (2A) (3A)
       assertCommandBehavior("edit #Tag #Invalid_Tag NotARealColor",
               Tag.MESSAGE_TAG_CONSTRAINTS,
               expected, expected.getTaskList());
           
       // Testing combination (1C) (2A) (3A)
       assertCommandBehavior("edit #Tag NotARealColor #Invalid_Tag",
               Tag.MESSAGE_TAG_CONSTRAINTS,
               expected, expected.getTaskList());
        
        // Testing combination (1A) (2C) (3B)
        assertCommandBehavior("edit #Invalid_Tag_Name #Invalid_Tag NotARealColor",
                Tag.MESSAGE_TAG_CONSTRAINTS,
                expected, expected.getTaskList());
    }

}
