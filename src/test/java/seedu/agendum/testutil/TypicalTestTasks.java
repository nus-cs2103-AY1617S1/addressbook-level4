package seedu.agendum.testutil;

import java.time.LocalDateTime;

import seedu.agendum.commons.exceptions.IllegalValueException;
import seedu.agendum.model.ToDoList;
import seedu.agendum.model.task.Task;

public class TypicalTestTasks {

    public static final TestTask ALICE =  generateTaskWithName("meet Alice Pauline");
    public static final TestTask BENSON = generateTaskWithName("meet Benson Meier");
    public static final TestTask CARL = generateTaskWithName("meet Carl Kurz");
    public static final TestTask DANIEL = generateTaskWithName("meet Daniel Meier");
    public static final TestTask ELLE = generateTaskWithName("meet Elle Meyer");
    public static final TestTask FIONA = generateTaskWithName("meet Fiona Kunz");
    public static final TestTask GEORGE = generateTaskWithName("meet George Best");
    public static final TestTask HOON = generateTaskWithName("meet Hoon Meier");
    public static final TestTask IDA = generateTaskWithName("meet Ida Mueller");
    
    private static final LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
    private static final LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);


    public static void loadToDoListWithSampleData(ToDoList tdl) {
        try {
            tdl.addTask(new Task(ALICE));
            tdl.addTask(new Task(BENSON));
            tdl.addTask(new Task(CARL));
            tdl.addTask(new Task(DANIEL));
            tdl.addTask(new Task(ELLE));
            tdl.addTask(new Task(FIONA));
            tdl.addTask(new Task(GEORGE));
        } catch (IllegalValueException e) {
            assert false : "not possible";
        }
    }
    
    private static TestTask generateTaskWithName(String name) {
        try {
            return new TaskBuilder().withName(name).withUncompletedStatus().build();
        } catch (IllegalValueException ive) {
            assert false: "Not possible";
            return null;
        }
    }

    public static TestTask getEventTestTask() throws IllegalValueException {
        return new TaskBuilder().withName("meeting")
                                     .withUncompletedStatus()
                                     .withStartTime(yesterday)
                                     .withEndTime(tomorrow).build();
    }

    public static TestTask getDeadlineTestTask() throws IllegalValueException {
        return new TaskBuilder().withName("due soon")
                                        .withUncompletedStatus()
                                        .withEndTime(tomorrow).build();
    }

    public static TestTask getFloatingTestTask() throws IllegalValueException {
        return new TaskBuilder().withName("anytime").withUncompletedStatus().build();
    }
    
    public TestTask[] getTypicalTasks() {
        return new TestTask[]{ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE};
    }

    public ToDoList getTypicalToDoList(){
        ToDoList tdl = new ToDoList();
        loadToDoListWithSampleData(tdl);
        return tdl;
    }
}
