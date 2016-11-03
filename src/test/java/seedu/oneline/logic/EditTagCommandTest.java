package seedu.oneline.logic;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.oneline.commons.core.Messages;
import seedu.oneline.model.ReadOnlyTaskBook;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.task.ReadOnlyTask;

public class EditTagCommandTest extends LogicTestManager {
    
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
     * Valid   :    (2C) Valid tag not already in the tag list
     * Valid   :    (2D) NewTag not specified, but Color is specified
     * 
     * == Color ==
     * Invalid :    (3A) Invalid Color
     * Valid   :    (3B) Valid Color
     * Valid   :    (3C) Color not specified, but NewTag is specified
     * 
     */
    
    @Test
    public void editTag_allValid1_invalidFormat() {
        // Testing combination (1C) (2C) (3B)

        // Testing combination (1C) (2C) (3B)
        
    }
    
    @Test
    public void editTag_invalidCurTag_throwError() {
        ReadOnlyTaskBook emptyTaskBook = TaskBook.getEmptyTaskBook();
        // Testing combination (1A) (2C) (3B)
        try {
            assertCommandBehavior("edit #Invalid_Tag_Name #ValidTag red",
                    Messages.MESSAGE_EDIT_TAG_ARGS_INVALID_FORMAT,
                    emptyTaskBook, new ArrayList<ReadOnlyTask>());
        } catch (Exception e) {
            assert false;
        }
        // Testing combination (1B) (2C) (3B)
        try {
            assertCommandBehavior("edit #NonexistentTag #ValidTag red",
                    Messages.MESSAGE_EDIT_TAG_TAG_NOT_FOUND,
                    emptyTaskBook, new ArrayList<ReadOnlyTask>());
        } catch (Exception e) {
            assert false;
        }
    }
    
    @Test
    public void editTag_nonExistentTag_throwError() {
        // Testing combination (2) (6)

    }
    
    
    
}
