package seedu.todo.testutil;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.ToDoList;
import seedu.todo.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask buyGroceries, benson, sleep, goMeeting, meetElle, meetFiona, meetGeorge, meetHoon, meetIda;

    public TypicalTestTasks() {
        try {
            buyGroceries =  new TaskBuilder().withName("buy groceries").withByDate("16/12/2016")
                    .withOnDate("16/12/2016").withDetail("fish")
                    .withTags("urgent").build();
            benson = new TaskBuilder().withName("do assignment").withByDate("12/12/1234")
                    .withOnDate("12/12/1232").withDetail("CS2103T")
                    .withTags("urgent").build();
            sleep = new TaskBuilder().withName("sleep").withByDate("12/12/1234")
                    .withOnDate("12/12/1232").withDetail("urgent")
                    .withTags("urgent").build();
            goMeeting = new TaskBuilder().withName("attend meeting").withByDate("12/12/1234")
                    .withOnDate("12/12/1232").withDetail("discuss todolist")
                    .withTags("not so urgent").build();
            meetElle = new TaskBuilder().withName("meet Elle Meyer").withDetail("9482224").withByDate("12/12/1234")
                    .withOnDate("12/12/1232")
                    .withTags("friends").build();
            meetFiona = new TaskBuilder().withName("meet Fiona Kunz").withDetail("9482427").withByDate("12/12/1234")
                    .withOnDate("12/12/1232")
                    .withTags("friends").build();
            meetGeorge = new TaskBuilder().withName("meet George Best").withDetail("9482442").withByDate("12/12/1234")
                    .withOnDate("12/12/1232")
                    .withTags("friends").build();

            //Manually added
            meetHoon = new TaskBuilder().withName("meet Hoon Meier").withDetail("8482424").withByDate("12/12/1234")
                    .withOnDate("12/12/1232").withDetail("85355255")
                    .withTags("friends").build();
            meetIda = new TaskBuilder().withName("meet Ida Mueller").withDetail("8482131").withByDate("12/12/1234")
                    .withOnDate("12/12/1232").withDetail("85355255")
                    .withTags("friends").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadToDoListWithSampleData(ToDoList ab) {

        try {
            ab.addTask(new Task(buyGroceries));
            ab.addTask(new Task(benson));
            ab.addTask(new Task(sleep));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{buyGroceries, benson, sleep, goMeeting, meetElle, meetFiona, meetGeorge};
    }

    public ToDoList getTypicalToDoList(){
        ToDoList ab = new ToDoList();
        loadToDoListWithSampleData(ab);
        return ab;
    }
}
