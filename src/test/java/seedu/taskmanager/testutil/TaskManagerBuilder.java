package seedu.taskmanager.testutil;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.model.item.Item;
import seedu.taskmanager.model.item.UniqueItemList;
import seedu.taskmanager.model.tag.Tag;

//@@author A0140060A-reused
/**
 * A utility class to help with building TaskManager objects.
 * Example usage: <br>
 *     {@code TaskManager ab = new TaskManagerBuilder().withItem("CS2103").withTag("Work").build();}
 */
public class TaskManagerBuilder {

    private TaskManager taskManager;

    public TaskManagerBuilder(TaskManager taskManager){
        this.taskManager = taskManager;
    }

    public TaskManagerBuilder withItem(Item item) throws UniqueItemList.DuplicateItemException {
        taskManager.addItem(item);
        return this;
    }

    public TaskManagerBuilder withTag(String tagName) throws IllegalValueException {
        taskManager.addTag(new Tag(tagName));
        return this;
    }

    public TaskManager build(){
        return taskManager;
    }
}
