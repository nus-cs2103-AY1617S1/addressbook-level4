package seedu.agendum.testutil;

import seedu.agendum.commons.exceptions.IllegalValueException;
import seedu.agendum.model.ToDoList;
import seedu.agendum.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withName("meet Alice Pauline").withUncompletedStatus().build();
            benson = new TaskBuilder().withName("meet Benson Meier").withUncompletedStatus().build();
            carl = new TaskBuilder().withName("meet Carl Kurz").withUncompletedStatus().build();
            daniel = new TaskBuilder().withName("meet Daniel Meier").withUncompletedStatus().build();
            elle = new TaskBuilder().withName("meet Elle Meyer").withUncompletedStatus().build();
            fiona = new TaskBuilder().withName("meet Fiona Kunz").withUncompletedStatus().build();
            george = new TaskBuilder().withName("meet George Best").withUncompletedStatus().build();

            //Manually added
            hoon = new TaskBuilder().withName("meet Hoon Meier").withUncompletedStatus().build();
            ida = new TaskBuilder().withName("meet Ida Mueller").withUncompletedStatus().build();
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
