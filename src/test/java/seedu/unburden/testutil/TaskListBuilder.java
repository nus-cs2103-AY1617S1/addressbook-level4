package seedu.unburden.testutil;

import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.model.ListOfTask;
import seedu.unburden.model.tag.Tag;
import seedu.unburden.model.task.Task;
import seedu.unburden.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ListOfTask ab = new TaskListBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class TaskListBuilder {

    private ListOfTask listOfTask;

    public TaskListBuilder(ListOfTask listOfTask){
        this.listOfTask = listOfTask;
    }

    public TaskListBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        listOfTask.addTask(task);
        return this;
    }

    public TaskListBuilder withTag(String tagName) throws IllegalValueException {
        listOfTask.addTag(new Tag(tagName));
        return this;
    }

    public ListOfTask build(){
        return listOfTask;
    }
}
