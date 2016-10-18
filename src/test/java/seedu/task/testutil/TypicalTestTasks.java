package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask cs2103, laundry, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            cs2103 =  new TaskBuilder().withName("Do CS 2103").//withAddress("123, Jurong West Ave 6, #08-111")
                    //.withEmail("alice@gmail.com").withPhone("85355255")
                    withTags("friends").build();
            laundry = new TaskBuilder().withName("Meier").//withAddress("311, Clementi Ave 2, #02-25")
                    //.withEmail("johnd@gmail.com").withPhone("98765432")
                    withTags("urgent", "important").build();
            carl = new TaskBuilder().withName("Meet Carl").build();//withPhone("95352563").withEmail("heinz@yahoo.com").withAddress("wall street").build();
            daniel = new TaskBuilder().withName("Have lunch with Meier").build();//.withPhone("87652533").withEmail("cornelia@google.com").withAddress("10th street").build();
            elle = new TaskBuilder().withName("Take Ellie out on a date").build();//.withPhone("9482224").withEmail("werner@gmail.com").withAddress("michegan ave").build();
            fiona = new TaskBuilder().withName("Buy a Shrek and Fiona Toy").build();//withPhone("9482427").withEmail("lydia@gmail.com").withAddress("little tokyo").build();
            george = new TaskBuilder().withName("Watch George Best Videos").build();//withPhone("9482442").withEmail("anna@google.com").withAddress("4th street").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withTags("omg").build();//withPhone("8482424").withEmail("stefan@mail.com").withAddress("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").build();//withPhone("8482131").withEmail("hans@google.com").withAddress("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        try {
            ab.addTask(new Task(cs2103));
            ab.addTask(new Task(laundry));
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
        return new TestTask[]{cs2103, laundry, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
