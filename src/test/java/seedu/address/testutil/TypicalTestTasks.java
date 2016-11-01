package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskBook;
import seedu.address.model.task.Task;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask one, two, three, four, five;

    public TypicalTestTasks() {
        try {
            one = new DatedTaskBuilder().withName("buy milk").withDescription("lots of it")
                    .withDatetime("11-NOV-2017 11:11").withStatus("NONE").build();
            two = new DatedTaskBuilder().withName("buy some milk").withDescription("not so much")
                    .withDatetime("12-NOV-2017 12:00").withStatus("NONE").build();
            three = new DatedTaskBuilder().withName("buy some milk").withDescription("just a little")
                    .withDatetime("13-DEC-2017 21:33").withStatus("NONE").build();
            
            //Manually added
            four = new DatedTaskBuilder().withName("Buy some cheese").withDescription("blue ones")
                    .withDatetime("15-DEC-2017 17:33").withStatus("NONE").build();
            five = new DatedTaskBuilder().withName("Buy more cheese").withDescription("smelly ones")
                    .withDatetime("24-DEC-2017 18:33").withStatus("NONE").build();
            
            
            
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskBookWithSampleData(TaskBook ab) {

    	ab.addTask(new Task(one));
        ab.addTask(new Task(two));
        ab.addTask(new Task(three));
    
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{one, two, three};
    }

    public TaskBook getTypicalAddressBook(){
        TaskBook ab = new TaskBook();
        loadTaskBookWithSampleData(ab);
        return ab;
    }
}
