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

    private final Tag tag;
    
    public DeleteTagCommand(Tag tag) {
        assert tag != null;
        assert !tag.equals(Tag.getDefault());
        this.tag = tag;
    }
    
    public static DeleteTagCommand createFromArgs(String args) throws IllegalValueException {
        String tagName = args.substring(1);
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        try {
            return new DeleteTagCommand(Tag.getTag(tagName));
        } catch (IllegalValueException e) {
            assert false : "tagName must be valid for a tag";
        }
        return null;
    }
    
    /**
     * Pre-condition: model.replaceTask removes the tag all together
     * as long as all tasks tagged with the tag have their tags removed.
     */
    @Override
    public CommandResult execute() {
        if (!tagExists()){
            return new CommandResult(String.format(Tag.MESSAGE_INVALID_TAG, tag.toString()));
        } else {
            // remove tags from tasks that are currently tagged 
            // model.replaceTask handles removing the tag
            List<ReadOnlyTask> taskWithTag = getTasksWithTag();
            while (!taskWithTag.isEmpty()){
                ReadOnlyTask oldTask = taskWithTag.get(0);
                untagTask(oldTask);
            }
            return new CommandResult(String.format(MESSAGE_DELETE_CAT_SUCCESS, tag.toString()));
        }
    }

    /**
     * @return true iff the tag is used in the taskbook
     */
    private boolean tagExists() {
        FilteredList<Tag> tagList = model.getTagList().filtered(taskTag -> taskTag.equals(tag));
        return !tagList.isEmpty();
    }
    
    /**
     * @return all tasks with the specified tag
     */
    private List<ReadOnlyTask> getTasksWithTag() {
        return model.getFilteredTaskList().filtered(
                task -> task.getTag().equals(tag));
    }

    /**
     * Removes tag from specified task in the model
     * 
     * @param defaultTag
     * @param oldTask
     */
    private void untagTask(ReadOnlyTask oldTask) {
        Tag defaultTag = Tag.getDefault();
        Task newTask = oldTask.updateTag(defaultTag);
        try {
            model.replaceTask(oldTask, newTask);
        } catch (UniqueTaskList.TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "The update task should not already exist";
        }
    }
    
    @Override
    public boolean canUndo() {
        return true;
    }
    
}
