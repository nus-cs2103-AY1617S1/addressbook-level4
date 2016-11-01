package tars.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tars.commons.util.StringUtil;
import tars.model.Model;
import tars.model.Tars;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList;
import tars.model.task.DateTime;
import tars.model.task.Name;
import tars.model.task.Priority;
import tars.model.task.Status;
import tars.model.task.Task;
import tars.model.task.rsv.RsvTask;

/**
 * Test data helper with typical test data
 */
public class TypicalTestDataHelper {
    
    protected Task meetAdam() throws Exception {
        Name name = new Name("Meet Adam Brown");
        DateTime dateTime = new DateTime("01/09/2016 1400", "01/09/2016 1500");
        Priority priority = new Priority("m");
        Status status = new Status(false);
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        UniqueTagList tags = new UniqueTagList(tag1, tag2);
        return new Task(name, dateTime, priority, status, tags);
    }
    
    protected Task floatingTask() throws Exception {
        Name name = new Name("Do homework");
        DateTime dateTime = new DateTime(StringUtil.EMPTY_STRING, StringUtil.EMPTY_STRING);
        Priority priority = new Priority(StringUtil.EMPTY_STRING);
        Status status = new Status(false);
        UniqueTagList tags = new UniqueTagList();
        return new Task(name, dateTime, priority, status, tags);
    }
    
    /**
     * Generates a valid task using the given seed. Running this function with the same parameter
     * values guarantees the returned task will have the same state. Each unique seed will generate
     * a unique Task object.
     *
     * @param seed used to generate the task data field values
     */
    protected Task generateTask(int seed) throws Exception {
        int seed2 = (seed + 1) % 31 + 1; // Generate 2nd seed for DateTime value
        return new Task(new Name("Task " + seed),
                        new DateTime(seed + "/01/2016 1400", seed2 + "/01/2016 2200"),
                        new Priority("h"), new Status(false),
                        new UniqueTagList(new Tag("tag" + Math.abs(seed)),
                        new Tag("tag" + Math.abs(seed + 1))));
    }
    
    /**
     * Generates the correct add command based on the task given
     */
    protected String generateAddCommand(Task p) {
        StringBuffer cmd = new StringBuffer();
        cmd.append("add ").append(p.getName().toString());

        if (p.getDateTime().toString().length() > 0) {
            cmd.append(" /dt ").append(p.getDateTime().toString());
        }

        if (p.getPriority().toString().length() > 0) {
            cmd.append(" /p ").append(p.getPriority().toString());
        }

        UniqueTagList tags = p.getTags();
        for (Tag t : tags) {
            cmd.append(" /t ").append(t.tagName);
        }

        return cmd.toString();
    }
    
    /**
     * Generates an Tars with auto-generated undone tasks.
     */
    protected Tars generateTars(int numGenerated) throws Exception {
        Tars tars = new Tars();
        addToTars(tars, numGenerated);
        return tars;
    }
    
    /**
     * Generates an Tars based on the list of Tasks given.
     */
    protected Tars generateTars(List<Task> tasks) throws Exception {
        Tars tars = new Tars();
        addToTars(tars, tasks);
        return tars;
    }

    /**
     * Adds auto-generated Task objects to the given Tars
     * 
     * @param tars The Tars to which the Tasks will be added
     */
    protected void addToTars(Tars tars, int numGenerated) throws Exception {
        addToTars(tars, generateTaskList(numGenerated));
    }

    /**
     * Adds the given list of Tasks to the given Tars
     */
    protected void addToTars(Tars tars, List<Task> tasksToAdd) throws Exception {
        for (Task p : tasksToAdd) {
            tars.addTask(p);
        }
    }

    /**
     * Adds auto-generated Task objects to the given model
     * 
     * @param model The model to which the Tasks will be added
     */
    protected void addToModel(Model model, int numGenerated) throws Exception {
        addToModel(model, generateTaskList(numGenerated));
    }

    /**
     * Adds the given list of Tasks to the given model
     */
    protected void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
        for (Task p : tasksToAdd) {
            model.addTask(p);
        }
    }

    /**
     * Generates a list of Tasks based on the flags.
     */
    protected List<Task> generateTaskList(int numGenerated) throws Exception {
        List<Task> tasks = new ArrayList<>();
        for (int i = 1; i <= numGenerated; i++) {
            tasks.add(generateTask(i));
        }
        return tasks;
    }

    protected List<Task> generateTaskList(Task... tasks) {
        return Arrays.asList(tasks);
    }

    /**
     * Generates a Task object with given name. Other fields will have some
     * dummy values.
     */
    protected Task generateTaskWithName(String name) throws Exception {
        return new Task(new Name(name), new DateTime("05/09/2016 1400", "06/09/2016 2200"), new Priority("h"),
                new Status(false), new UniqueTagList(new Tag("tag")));
    }

    /**
     * Generates a Task object with given name. Other fields will have some
     * dummy values.
     */
    protected Task generateTaskWithEndDateOnly(String name) throws Exception {
        return new Task(new Name(name), new DateTime(null, "06/09/2016 2200"), new Priority("h"), new Status(false),
                new UniqueTagList(new Tag("tag")));
    }

    /**
     * Generates a Task object with given name and date time
     * 
     * @@author A0124333U
     */
    protected Task generateTaskWithNameAndDate(String name, DateTime dateTime) throws Exception {
        assert (dateTime != null && name != null);
        return new Task(new Name(name), dateTime, new Priority("h"), new Status(false),
                new UniqueTagList(new Tag("tag")));
    }

    /**
     * Generates a RsvTask object with given name and datetime(s)
     */
    protected RsvTask generateReservedTaskWithNameAndDate(String name, DateTime... dateTimes) throws Exception {
        ArrayList<DateTime> dateTimeList = new ArrayList<DateTime>();
        for (DateTime dt : dateTimes) {
            dateTimeList.add(dt);
        }
        return new RsvTask(new Name(name), dateTimeList);
    }

    /**
     * Generates a RsvTask object with given name and a dummy dateTime
     */
    protected RsvTask generateReservedTaskWithOneDateTimeOnly(String name) throws Exception {
        ArrayList<DateTime> dateTimeList = new ArrayList<DateTime>();
        dateTimeList.add(new DateTime("05/09/2016 1400", "06/09/2016 2200"));
        return new RsvTask(new Name(name), dateTimeList);
    }

    protected Tars fillModelAndTarsForFreeCommand(Model model) throws Exception {
        RsvTask rsvTask1 = generateReservedTaskWithNameAndDate("rsvTask1",
                new DateTime("29/10/2016 1400", "29/10/2016 1500"),
                new DateTime("30/10/2016 1400", "30/10/2016 1500"));
        RsvTask rsvTask2 = generateReservedTaskWithNameAndDate("rsvTask2",
                new DateTime("28/10/2016 0900", "28/10/2016 1400"));
        Task floatingTask = generateTaskWithNameAndDate("Floating Task", new DateTime("", ""));
        Task taskWithoutStartDate = generateTaskWithNameAndDate("Task without startdate",
                new DateTime("", "29/10/2016 1500"));
        Task task1 = generateTaskWithNameAndDate("Task 1", new DateTime("28/10/2016 2200", "29/10/2016 0100"));
        Task task2 = generateTaskWithNameAndDate("Task 2", new DateTime("29/10/2016 1430", "29/10/2016 1800"));
        Task task3 = generateTaskWithNameAndDate("Task 3", new DateTime("01/10/2016 1400", "01/10/2016 1500"));
        Task task4 = generateTaskWithNameAndDate("Task 4", new DateTime("10/10/2016 1500", "12/10/2016 1400"));
        
        Tars tars = new Tars();
        tars.addRsvTask(rsvTask1);
        tars.addRsvTask(rsvTask2);
        tars.addTask(floatingTask);
        tars.addTask(taskWithoutStartDate);
        tars.addTask(task1);
        tars.addTask(task2);
        tars.addTask(task3);
        tars.addTask(task4);

        model.addRsvTask(rsvTask1);
        model.addRsvTask(rsvTask2);
        model.addTask(floatingTask);
        model.addTask(taskWithoutStartDate);
        model.addTask(task1);
        model.addTask(task2);
        model.addTask(task3);
        model.addTask(task4);

        return tars;
    }
}
