//@@author A0093896H
package seedu.todo.logic;

import static seedu.todo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.TagCommand;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.tag.UniqueTagList;
import seedu.todo.model.task.Task;
import seedu.todo.model.task.UniqueTaskList.TaskNotFoundException;


/**
 * Test class for the tag command's logic
 */
public class TagLogicTest extends CommandLogicTest {
    
    @Test
    public void execute_tag_successful() throws IllegalValueException, TaskNotFoundException {
        Task toBeTagged = helper.generateFullTask(0);
        expectedTDL.addTask(toBeTagged);
        
        UniqueTagList tags = new UniqueTagList();
        tags.add(new Tag("yay"));
        expectedTDL.addTaskTags(toBeTagged, tags);
        
        Task toBeTaggedInModel = helper.generateFullTask(0);
        model.addTask(toBeTaggedInModel);        
        
        assertCommandBehavior("tag 1 yay",
                String.format(TagCommand.MESSAGE_SUCCESS, toBeTagged.getName()),
                expectedTDL,
                expectedTDL.getTaskList());
    }
    
    @Test
    public void execute_tagInvalidArgsFormat_errorMessageShown() throws IllegalValueException {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("tag", expectedMessage);
    }

    @Test
    public void execute_tagIndexNotFound_errorMessageShown() throws IllegalValueException {
        assertIndexNotFoundBehaviorForCommand("tag");
    }
}
