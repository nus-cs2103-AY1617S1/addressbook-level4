package seedu.todo.testutil;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.ToDoList;
import seedu.todo.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withName("Alice Pauline").withByDate("12/12/1234")
                    .withOnDate("12/12/1232").withDetail("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withByDate("12/12/1234")
                    .withOnDate("12/12/1232").withDetail("85355255")
                    .withTags("friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withDetail("95352563").withByDate("12/12/1234")
                    .withOnDate("12/12/1232").withDetail("85355255")
                    .withTags("friends").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withDetail("87652533").withByDate("12/12/1234")
                    .withOnDate("12/12/1232").withDetail("85355255")
                    .withTags("friends").build();
            elle = new TaskBuilder().withName("Elle Meyer").withDetail("9482224").withByDate("12/12/1234")
                    .withOnDate("12/12/1232").withDetail("85355255")
                    .withTags("friends").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withDetail("9482427").withByDate("12/12/1234")
                    .withOnDate("12/12/1232").withDetail("85355255")
                    .withTags("friends").build();
            george = new TaskBuilder().withName("George Best").withDetail("9482442").withByDate("12/12/1234")
                    .withOnDate("12/12/1232").withDetail("85355255")
                    .withTags("friends").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withDetail("8482424").withByDate("12/12/1234")
                    .withOnDate("12/12/1232").withDetail("85355255")
                    .withTags("friends").build();
            ida = new TaskBuilder().withName("Ida Mueller").withDetail("8482131").withByDate("12/12/1234")
                    .withOnDate("12/12/1232").withDetail("85355255")
                    .withTags("friends").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadToDoListWithSampleData(ToDoList ab) {

        try {
            ab.addTask(new Task(alice));
            ab.addTask(new Task(benson));
            ab.addTask(new Task(carl));
            ab.addTask(new Task(daniel));
            ab.addTask(new Task(elle));
            ab.addTask(new Task(fiona));
            ab.addTask(new Task(george));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public ToDoList getTypicalToDoList(){
        ToDoList ab = new ToDoList();
        loadToDoListWithSampleData(ab);
        return ab;
    }
}
