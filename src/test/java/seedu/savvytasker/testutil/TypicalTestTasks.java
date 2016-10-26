package seedu.savvytasker.testutil;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.savvytasker.commons.exceptions.IllegalValueException;
import seedu.savvytasker.model.SavvyTasker;
import seedu.savvytasker.model.task.PriorityLevel;
import seedu.savvytasker.model.task.Task;
import seedu.savvytasker.model.task.TaskList.DuplicateTaskException;
import seedu.savvytasker.model.task.TaskList.InvalidDateException;

//@@author A0139915W
/**
 *  Test tasks used to test cases
 */
public class TypicalTestTasks {

    public TestTask highPriority, medPriority, lowPriority, furthestDue, 
                            nearerDue, notSoNearerDue, earliestDue, longDue, happy, haloween;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            
    public TypicalTestTasks() {
        try {
            highPriority =  new TaskBuilder().withId(0).withTaskName("High Priority Task")
                                .withPriority(PriorityLevel.High).build();
            medPriority =  new TaskBuilder().withId(1).withTaskName("Medium Priority Task")
                                .withPriority(PriorityLevel.Medium).build();
            lowPriority =  new TaskBuilder().withId(2).withTaskName("Low Priority Task")
                                .withPriority(PriorityLevel.Low).build();
            furthestDue =  new TaskBuilder().withId(3).withTaskName("Furthest Due Task")
                                .withEndDateTime(getDate("01/12/2016")).build();
            nearerDue =  new TaskBuilder().withId(4).withTaskName("Nearer Due Task")
                                .withEndDateTime(getDate("01/11/2016")).build();
            notSoNearerDue =  new TaskBuilder().withId(5).withTaskName("Not So Nearer Due Task")
                    .withEndDateTime(getDate("02/11/2016")).build();
            earliestDue =  new TaskBuilder().withId(6).withTaskName("Earliest Due Task")
                                .withEndDateTime(getDate("01/10/2016")).build();
            longDue =  new TaskBuilder().withId(7).withTaskName("Long Due Task")
                    .withEndDateTime(getDate("01/1/2016")).withArchived(true).build();
            
            //Manually added
            happy = new TaskBuilder().withId(8).withTaskName("Happy Task").build();
            haloween = new TaskBuilder().withId(9).withTaskName("Haloween Task").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadSavvyTaskerWithSampleData(SavvyTasker st) {
        TypicalTestTasks td = new TypicalTestTasks();
        try {
            st.addTask(new Task(td.highPriority));
            st.addTask(new Task(td.medPriority));
            st.addTask(new Task(td.lowPriority));
            st.addTask(new Task(td.furthestDue));
            st.addTask(new Task(td.nearerDue));
            st.addTask(new Task(td.notSoNearerDue));
            st.addTask(new Task(td.earliestDue));
            st.addTask(new Task(td.longDue));
        } catch (DuplicateTaskException e) {
            assert false : "not possible";
        } catch (InvalidDateException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{ highPriority, medPriority, lowPriority, 
                furthestDue, nearerDue, notSoNearerDue, earliestDue };
    }

    public SavvyTasker getTypicalSavvyTasker(){
        SavvyTasker st = new SavvyTasker();
        loadSavvyTaskerWithSampleData(st);
        return st;
    }
    
    private Date getDate(String ddmmyyyy) {
        try {
            return format.parse(ddmmyyyy);
        } catch (Exception e) {
            assert false; //should not get an invalid date....
        }
        return null;
    }
}
//@@author A0139915W
