package seedu.taskman.testutil;

import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.model.TaskMan;
import seedu.taskman.model.event.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withTitle("Alice Pauline").withFrequency("22").withDeadline("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withTitle("Benson Meier").withFrequency("22").withDeadline("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withTitle("Carl Kurz").withDeadline("95352563").withFrequency("22").withSchedule("1").build();
            daniel = new TaskBuilder().withTitle("Daniel Meier").withDeadline("87652533").withFrequency("22").withSchedule("1").build();
            elle = new TaskBuilder().withTitle("Elle Meyer").withDeadline("9482224").withFrequency("22").withSchedule("1").build();
            fiona = new TaskBuilder().withTitle("Fiona Kunz").withDeadline("9482427").withFrequency("22").withSchedule("1").build();
            george = new TaskBuilder().withTitle("George Best").withDeadline("9482442").withFrequency("22").withSchedule("1").build();

            //Manually added
            hoon = new TaskBuilder().withTitle("Hoon Meier").withDeadline("8482424").withFrequency("22").withSchedule("1").build();
            ida = new TaskBuilder().withTitle("Ida Mueller").withDeadline("8482131").withFrequency("22").withSchedule("1").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManWithSampleData(TaskMan ab) {

        try {
            ab.addTask(new Task(alice));
            ab.addTask(new Task(benson));
            ab.addTask(new Task(carl));
            ab.addTask(new Task(daniel));
            ab.addTask(new Task(elle));
            ab.addTask(new Task(fiona));
            ab.addTask(new Task(george));
        } catch (UniqueActivityList.DuplicateActivityException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskMan getTypicalTaskMan(){
        TaskMan ab = new TaskMan();
        loadTaskManWithSampleData(ab);
        return ab;
    }
}
