package seedu.stask.testutil;

import seedu.stask.commons.exceptions.IllegalValueException;
import seedu.stask.model.TaskBook;
import seedu.stask.model.task.Task;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask datedOne, datedTwo, datedThree, datedFour, datedFive;
    public static TestTask undatedOne, undatedTwo, undatedThree, undatedFour, undatedFive;

    public TypicalTestTasks() {
        try {
            //Auto-generated dated tasks
            datedOne = new TestTaskBuilder().withName("Module B Project").withDescription("Submit report to IVLE")
                    .withDatetime("10-SEP-2017 22:00").withStatus("NONE").build();
            datedTwo = new TestTaskBuilder().withName("Module A Exam").withDescription("MPSH 1A")
                    .withDatetime("11-SEP-2017 10:00 to 12:00").withStatus("NONE").build();
            datedThree = new TestTaskBuilder().withName("Pay insurance premium").withDescription("AIA")
                    .withDatetime("23-OCT-2017 18:00").withStatus("NONE").build();
            
            //Manually added
            datedFour = new TestTaskBuilder().withName("File income tax").withDescription("online tax portal")
                    .withDatetime("29-OCT-2017 23:59").withStatus("NONE").build();
            datedFive = new TestTaskBuilder().withName("Xmas dinner").withDescription("at vivocity")
                    .withDatetime("24-DEC-2017 19:00").withStatus("NONE").build();
            
            //Auto-generated undated tasks
            undatedOne = new TestTaskBuilder().withName("Catch up on korean drama").withDescription("Doctors")
                    .withDatetime("").withStatus("NONE").build();
            undatedTwo = new TestTaskBuilder().withName("Consider Netflix subscription").withDescription("cost")
                    .withDatetime("").withStatus("NONE").build();
            undatedThree = new TestTaskBuilder().withName("December travel plans").withDescription("5D4N")
                    .withDatetime("").withStatus("NONE").build();
            
            //Manually added
            undatedFour = new TestTaskBuilder().withName("Restock toiletries").withDescription("toilet roll")
                    .withDatetime("").withStatus("NONE").build();
            undatedFive = new TestTaskBuilder().withName("Walk Muffin").withDescription("5km route")
                    .withDatetime("").withStatus("NONE").build();
            
            
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskBookWithSampleData(TaskBook ab) {

    	ab.addTask(new Task(datedOne));
        ab.addTask(new Task(datedTwo));
        ab.addTask(new Task(datedThree));
        
        ab.addTask(new Task(undatedOne));
        ab.addTask(new Task(undatedTwo));
        ab.addTask(new Task(undatedThree));
    
    }

    public TestTask[] getTypicalDatedTasks() {
        return new TestTask[]{datedOne, datedTwo, datedThree};
    }

    public TestTask[] getTypicalUndatedTasks() {
        return new TestTask[]{undatedOne, undatedTwo, undatedThree};
    }
    
    public TaskBook getTypicalAddressBook(){
        TaskBook ab = new TaskBook();
        loadTaskBookWithSampleData(ab);
        return ab;
    }
}
