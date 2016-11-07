package seedu.taskitty.testutil;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.model.TaskManager;
import seedu.taskitty.model.task.*;

/**
 *
 */
public class TypicalTestTask {

    //@@author A0139930B
    public TestTask todo, deadline, comingEvent, todayEvent, addTodo, addDeadline, overDeadline, addEvent, overEvent, 
                           editedDeadline, editedEvent;
    
    public TypicalTestTask() {
        try {
            todo = new TaskBuilder().withName("todo").withTags("random").build();
            deadline = new TaskBuilder().withName("upcoming deadline")
                    .withDeadline(TestUtil.getDateFromToday(2, 60), TestUtil.getTimeFromNow(60))
                    .build();
            comingEvent = new TaskBuilder().withName("upcoming event")
                    .withEvent(TestUtil.getDateFromToday(2, 100), TestUtil.getTimeFromNow(100),
                            TestUtil.getDateFromToday(2, 220), TestUtil.getTimeFromNow(220))
                    .build();
            todayEvent = new TaskBuilder().withName("today event")
                    .withEvent(TestUtil.getDateToday(), TestUtil.getTimeNow(),
                            TestUtil.getDateToday(), "23:59")
                    .withTags("random").build();
            
            //manually added
            addTodo = new TaskBuilder().withName("added todo").withTags("added").build();
            addDeadline = new TaskBuilder().withName("added deadline")
                    .withDeadline(TestUtil.getDateFromToday(2, -60), TestUtil.getTimeFromNow(-60))
                    .withTags("added").build();
            addEvent = new TaskBuilder().withName("added event")
                    .withEvent(TestUtil.getDateFromToday(1, 0), TestUtil.getTimeFromNow(120),
                            TestUtil.getDateFromToday(3, 120), TestUtil.getTimeFromNow(120))
                    .withTags("added").build();
            
            overDeadline = new TaskBuilder().withName("over deadline")
                    .withDeadline(TestUtil.getDateFromToday(-1, 60), TestUtil.getTimeFromNow(60))
                    .withTags("added").build();
            overEvent = new TaskBuilder().withName("over Event")
                    .withEvent(TestUtil.getDateFromToday(-2, -60), TestUtil.getTimeFromNow(-60),
                            TestUtil.getDateFromToday(-2, 0), TestUtil.getTimeNow())
                    .withTags("random").build();
            
            editedDeadline = new TaskBuilder().withName("upcoming deadline")
                    .withDeadline(TestUtil.getDateFromToday(3, 60), TestUtil.getTimeFromNow(60))
                    .build();
            /*editedDeadline = new TaskBuilder().withName("upcoming deadline")
                    .withDeadline(TestUtil.getDateFromToday(3, -60), TestUtil.getTimeFromNow(-60))
                    .withTags("added").build();*/
            editedEvent = new TaskBuilder().withName("today event")
                    .withEvent(TestUtil.getDateFromToday(1, 0), TestUtil.getTimeNow(),
                            TestUtil.getDateFromToday(1, 0), "23:59")
                    .withTags("random").build();
            
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        TypicalTestTask td = new TypicalTestTask();
        try {
            ab.addTask(new Task(td.todo));
            ab.addTask(new Task(td.deadline));
            ab.addTask(new Task(td.comingEvent));
            ab.addTask(new Task(td.todayEvent));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{todo, deadline, comingEvent, todayEvent};
    }
    
    //@@author
    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
