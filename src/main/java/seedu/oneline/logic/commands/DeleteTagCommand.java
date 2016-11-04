//@@author A0138848M
package seedu.oneline.logic.commands;

import java.util.List;

import javafx.collections.transformation.FilteredList;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.Task;
import seedu.oneline.model.task.UniqueTaskList;

public class DeleteTagCommand extends DeleteCommand {

    private final String tagName;
    
    public DeleteTagCommand(String tagName) {
        this.tagName = tagName;
    }
    
    public static DeleteTagCommand createFromArgs(String args) throws IllegalValueException {
        String tagName = args.substring(1);
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        } else {
            return new DeleteTagCommand(tagName);
        }
    }
    
    /**
     * Pre-condition: model.replaceTask removes the tag all together
     * as long as all tasks tagged with the tag have their tags removed.
     */
    @Override
    public CommandResult execute() {
        FilteredList<Tag> tagList = model.getTagList().filtered(tag -> tag.getTagName().equals(tagName));
        if (tagList.isEmpty()){
            return new CommandResult(String.format(Tag.MESSAGE_INVALID_TAG, tagName));
        } else {
            // remove tags from tasks that are currently tagged 
            // model.replaceTask handles removing the tag
            List<ReadOnlyTask> taskWithTag = 
                    model.getFilteredTaskList().filtered(task -> task.getTag().getTagName().equals(tagName));
            Tag defaultTag = Tag.getDefault();
            while (!taskWithTag.isEmpty()){
                ReadOnlyTask oldTask = taskWithTag.get(0);
                Task newTask = oldTask.updateTag(defaultTag);
                try {
                    model.replaceTask(oldTask, newTask);
                } catch (UniqueTaskList.TaskNotFoundException e) {
                    assert false : "The target task cannot be missing";
                } catch (UniqueTaskList.DuplicateTaskException e) {
                    assert false : "The update task should not already exist";
                }
            }
            
            return new CommandResult(String.format(MESSAGE_DELETE_CAT_SUCCESS, tagName));
        }
    }
    
    @Override
    public boolean canUndo() {
        return true;
    }
    
}
