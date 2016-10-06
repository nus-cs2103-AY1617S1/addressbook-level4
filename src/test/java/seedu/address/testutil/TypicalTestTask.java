package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.person.*;

/**
 *
 */
public class TypicalTestTask {

    public static TestTask attendMeeting, buyMilk, createPlan, deliverGoods, eatDinner, flyKite, goConcert, helpFriend, interview;

    public TypicalTestTask() {
        try {
            attendMeeting =  new TaskBuilder().withName("Attend Meeting").build();
            buyMilk = new TaskBuilder().withName("Buy Milk").build();
            createPlan = new TaskBuilder().withName("Create Plan").build();
            deliverGoods = new TaskBuilder().withName("Deliver Goods").build();
            eatDinner = new TaskBuilder().withName("Eat Dinner").build();
            flyKite = new TaskBuilder().withName("Fly Kite").build();
            goConcert = new TaskBuilder().withName("Go Concert").build();

          //Manually added
            helpFriend = new TaskBuilder().withName("Help Friend").build();
            interview = new TaskBuilder().withName("Interview").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskManager ab) {

        try {
            ab.addTask(new Task(attendMeeting));
            ab.addTask(new Task(buyMilk));
            ab.addTask(new Task(createPlan));
            ab.addTask(new Task(deliverGoods));
            ab.addTask(new Task(eatDinner));
            ab.addTask(new Task(flyKite));
            ab.addTask(new Task(goConcert));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{attendMeeting, buyMilk, createPlan, deliverGoods, eatDinner, flyKite, goConcert};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
