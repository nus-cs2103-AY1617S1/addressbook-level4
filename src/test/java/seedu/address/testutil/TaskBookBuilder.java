package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Item;
import seedu.address.model.item.UniqueItemList;
import seedu.address.model.tag.Tag;
import seedu.address.model.TaskBook;

/**
 * A utility class to help with building Taskbook objects.
 * Example usage: <br>
 *     {@code TaskBook ab = new TaskBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class TaskBookBuilder {

    private TaskBook taskBook;

    public TaskBookBuilder(TaskBook taskBook){
        assert taskBook != null;
        this.taskBook = taskBook;
    }

    public TaskBookBuilder withItem(Item item) throws UniqueItemList.DuplicateItemException {
        taskBook.addItem(item);
        return this;
    }
    
    /*
    public TaskBookBuilder withTag(String tagName) throws IllegalValueException {
        taskBook.addTag(new Tag(tagName));
        return this;
    }
    */
    
    public TaskBook build(){
        return taskBook;
    }
}
