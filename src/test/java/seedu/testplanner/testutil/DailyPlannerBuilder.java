package seedu.testplanner.testutil;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.model.DailyPlanner;
import seedu.dailyplanner.model.category.Category;
import seedu.dailyplanner.model.task.Task;
import seedu.dailyplanner.model.task.UniqueTaskList;
//@@author A0139102U
/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class DailyPlannerBuilder {

    private DailyPlanner dailyPlanner;

    public DailyPlannerBuilder(DailyPlanner dailyPlanner){
        this.dailyPlanner = dailyPlanner;
    }

    public DailyPlannerBuilder withPerson(Task person) throws UniqueTaskList.DuplicateTaskException {
        dailyPlanner.addTask(person);
        return this;
    }

    public DailyPlannerBuilder withTag(String tagName) throws IllegalValueException {
        dailyPlanner.addCategory(new Category(tagName));
        return this;
    }

    public DailyPlanner build(){
        return dailyPlanner;
    }
}
