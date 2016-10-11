package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ToDoList;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask grocery, house, family, car, dog;

    public TypicalTestTasks() {
        try {
            grocery = new TaskBuilder().withDetail("Buy more milk").build();
            house = new TaskBuilder().withDetail("Paint the house").withDueByDate("12/31/2016").build();
            family = new TaskBuilder().withDetail("Give Kelly a bath").withDueByDate("12/31/2016").withDueByTime("2:00").build();
            car = new TaskBuilder().withDetail("Add gas").withPriority("high").build();
            dog = new TaskBuilder().withDetail("Invent automatic dog toilet").withTags("dog").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadToDoListWithSampleData(ToDoList td) {

        try {
            td.addTask(new Task(grocery));
            td.addTask(new Task(house));
            td.addTask(new Task(family));
            td.addTask(new Task(car));
            td.addTask(new Task(dog));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{grocery, house, family, car, dog};
    }

    public ToDoList getTypicalToDoList(){
        ToDoList ab = new ToDoList();
        loadToDoListWithSampleData(ab);
        return ab;
    }
}
