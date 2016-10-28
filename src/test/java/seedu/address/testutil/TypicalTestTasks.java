package seedu.address.testutil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {


    public static TestTask friend, friendEvent,lunch, book, work, movie, meeting, travel, project, workshop,lecture,lectureVerifier;

    //@@author A0146123R
    public TypicalTestTasks() {
        try {
            friend =  new TaskBuilder().withName("Meet old friends").withDeadline("")
                    .withTags("friends").build();
            friendEvent=new TaskBuilder().withName("Meet old friends").withDeadline("11.10.2016")
            		.withTags("friends").build();
            lunch = new TaskBuilder().withName("Eat lunch with friends").withDeadline("11.10.2016-12")
                    .withTags("lunch", "friends").build();
            book = new TaskBuilder().withName("Read book").withDeadline("").build();
            work = new TaskBuilder().withName("Work").withDeadline("11.10.2016").build();
            movie = new TaskBuilder().withName("Watch movie").withDeadline("11.10.2016-16").build();
            meeting = new TaskBuilder().withName("Project meeting").withEventDate("11.10.2016-10", "11.10.2016-12").build();
            travel = new TaskBuilder().withName("Travel").withEventDate("11.10.2016", "15.10.2016").build();

 
            //Manually added
            project = new TaskBuilder().withName("Project due").withDeadline("11.10.2016").build();
            workshop = new TaskBuilder().withName("Attend workshop").withEventDate("11.10.2016-10", "11.10.2016-16").build();
            lecture=new TaskBuilder().withName("CS2103 Lecture").withEventDate("14.10.2016-14", "14.10.2016-16").withRecurringFrequency("weekly").build();
            
            //Used to verify recurring data only
            lectureVerifier=new TaskBuilder().withName("CS2103 Lecture").withEventDate(getNextFriday()+"-14", getNextFriday()+"-16").withRecurringFrequency("weekly").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
            ab.addTask(new Task(friend));
            ab.addTask(new Task(friendEvent));
            ab.addTask(new Task(lunch));
            ab.addTask(new Task(book));
            ab.addTask(new Task(work));
            ab.addTask(new Task(movie));
            ab.addTask(new Task(meeting));
            ab.addTask(new Task(travel));
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{friend, friendEvent,lunch, book, work, movie, meeting, travel};
    }
    //@@author

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
   //@@author A0142325R 
    private static String getNextFriday() {
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        c.setTime(c.getTime());
       
        // search until the next upcoming Friday
        while (c.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
            c.add(Calendar.DAY_OF_WEEK, 1);
        }
        return dateFormat.format(c.getTime());
    }
}
