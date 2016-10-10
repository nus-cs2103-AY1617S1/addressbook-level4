package taskle.testutil;

import taskle.commons.exceptions.IllegalValueException;
import taskle.model.TaskManager;
import taskle.model.person.FloatTask;
import taskle.model.person.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask attendMeeting, buyMilk, createPlan, deliverGoods, eatDinner, flyKite, goConcert, helpFriend, interview;

    public TypicalTestTasks() {
        try {
            attendMeeting =  new TaskBuilder().withName("Attend Meeting").build();
            buyMilk = new TaskBuilder().withName("Buy Milk").build();
            createPlan = new TaskBuilder().withName("Create Plan").build();
            deliverGoods = new TaskBuilder().withName("Deliver Goods For Milk").build();
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
            ab.addTask(new FloatTask(attendMeeting));
            ab.addTask(new FloatTask(buyMilk));
            ab.addTask(new FloatTask(createPlan));
            ab.addTask(new FloatTask(deliverGoods));
            ab.addTask(new FloatTask(eatDinner));
            ab.addTask(new FloatTask(flyKite));
            ab.addTask(new FloatTask(goConcert));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{attendMeeting, buyMilk, createPlan, deliverGoods, eatDinner, flyKite, goConcert};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}

