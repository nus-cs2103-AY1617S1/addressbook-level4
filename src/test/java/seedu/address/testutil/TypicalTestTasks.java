package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.SavvyTasker;
import seedu.address.model.person.*;
import seedu.address.model.person.TaskList.DuplicateTaskException;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask hello, happy, haloween;

    public TypicalTestTasks() {
        try {
            hello =  new TaskBuilder().withTaskName("Hello Task").build();
            
            //Manually added
            happy = new TaskBuilder().withTaskName("Happy Task").build();
            haloween = new TaskBuilder().withTaskName("Haloween Task").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadSavvyTaskerWithSampleData(SavvyTasker st) {

        try {
            st.addTask(new Task(hello));
        } catch (DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{hello};
    }

    public SavvyTasker getTypicalSavvyTasker(){
        SavvyTasker st = new SavvyTasker();
        loadSavvyTaskerWithSampleData(st);
        return st;
    }
}
