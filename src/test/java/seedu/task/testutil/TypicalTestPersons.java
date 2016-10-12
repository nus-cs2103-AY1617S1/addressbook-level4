package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.person.*;

/**
 *
 */
public class TypicalTestPersons {

    public static TestPerson taskA, taskB, taskC, taskD, taskE, taskF, taskG, taskH, taskI;

    public TypicalTestPersons() {
        try {
            taskA =  new PersonBuilder().withName("Accompany mom to the doctor").withAddress("Khoo Teck Puat Hospital")
                    .withEmail("1200hrs").withPhone("1000hrs")
                    .withTags("gwsMum").build();
            taskB = new PersonBuilder().withName("Borrow software engineering book").withAddress("National Library")
                    .withEmail("1400hrs").withPhone("1300hrs")
                    .withTags("study", "seRocks").build();
            taskC = new PersonBuilder().withName("Call Jim").withPhone("1200hrs").withEmail("1300hrs").withAddress("NUS").build();
            taskD = new PersonBuilder().withName("Do homework").withPhone("1400hrs").withEmail("1500hrs").withAddress("Home").build();
            taskE = new PersonBuilder().withName("Edit AddressBook file").withPhone("1500hrs").withEmail("1600hrs").withAddress("Home").build();
            taskF = new PersonBuilder().withName("Finish up the project").withPhone("1600hrs").withEmail("1700hrs").withAddress("Home").build();
            taskG = new PersonBuilder().withName("Go for a jog").withPhone("1700hrs").withEmail("1800hrs").withAddress("Botanic Gardens").build();

            //Manually added
            taskH = new PersonBuilder().withName("Help Jim with his task").withPhone("1800hrs").withEmail("1900hrs").withAddress("Jim house").build();
            taskI = new PersonBuilder().withName("Iron new clothes").withPhone("1900hrs").withEmail("2000hrs").withAddress("Home").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskManager ab) {

        try {
            ab.addTask(new Task(taskA));
            ab.addTask(new Task(taskB));
            ab.addTask(new Task(taskC));
            ab.addTask(new Task(taskD));
            ab.addTask(new Task(taskE));
            ab.addTask(new Task(taskF));
            ab.addTask(new Task(taskG));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestPerson[] getTypicalPersons() {
        return new TestPerson[]{taskA, taskB, taskC, taskD, taskE, taskF, taskG};
    }

    public TaskManager getTypicalAddressBook(){
        TaskManager ab = new TaskManager();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
