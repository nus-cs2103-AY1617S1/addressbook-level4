package seedu.taskman.testutil;

import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.model.TaskMan;
import seedu.taskman.model.event.Task;
import seedu.taskman.model.event.UniqueActivityList;
import seedu.taskman.model.tag.Tag;

/**
 * A utility class to help with building TaskMan objects.
 * Example usage: <br>
 *     {@code TaskMan ab = new TaskManBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class TaskManBuilder {

    private TaskMan taskMan;

    public TaskManBuilder(TaskMan taskMan){
        this.taskMan = taskMan;
    }

    public TaskManBuilder withTask(Task task) throws UniqueActivityList.DuplicateActivityException {
        taskMan.addTask(task);
        return this;
    }

    public TaskManBuilder withTag(String tagName) throws IllegalValueException {
        taskMan.addTag(new Tag(tagName));
        return this;
    }

    public TaskMan build(){
        return taskMan;
    }
}
