package seedu.address.testutil;

import jym.manager.commons.exceptions.IllegalValueException;
import jym.manager.model.TaskManager;
import jym.manager.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withDescription("Alice Pauline").withTags("friends").build();
       //     .withAddress("123, Jurong West Ave 6, #08-111")
         //           .withTags("friends").build();
            benson = new TaskBuilder().withDescription("Benson Meier")
         //   .withAddress("311, Clementi Ave 2, #02-25")
         //           .withEmail("johnd@gmail.com").withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withDescription("Carl Kurz").build();
            daniel = new TaskBuilder().withDescription("Daniel Meier")
            	//	.withAddress("10th street")
            		.build();
            elle = new TaskBuilder().withDescription("Elle Meyer")
            		//withAddress("michegan ave")
            		.build();
            fiona = new TaskBuilder().withDescription("Fiona Kunz")
            	//	.withAddress("little tokyo")
            		.build();
            george = new TaskBuilder().withDescription("George Best")
            		//.withAddress("4th street")
            		.build();

            //Manually added
            hoon = new TaskBuilder().withDescription("Hoon Meier")
            		//.withAddress("little india")
            		.build();
            ida = new TaskBuilder().withDescription("Ida Mueller")
            		//.withAddress("chicago ave")
            		.build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskManager ab) {

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

    public TaskManager getTypicalAddressBook(){
        TaskManager ab = new TaskManager();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
