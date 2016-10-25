package seedu.oneline.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.oneline.model.Model;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.UniqueTagList;
import seedu.oneline.model.task.Task;
import seedu.oneline.model.task.TaskName;
import seedu.oneline.model.task.TaskRecurrence;
import seedu.oneline.model.task.TaskTime;

/**
 * A utility class to generate test data.
 */
public class TestDataHelper{

    public Task myTask() throws Exception {
        TaskName name = new TaskName("Do seagull stuff");
        TaskTime startTime = new TaskTime("Sun Oct 16 21:35:45");
        TaskTime endTime = new TaskTime("Mon Oct 17 21:35:45");
        TaskTime deadline = new TaskTime("Sun Oct 23 21:35:45");
        TaskRecurrence recurrence = new TaskRecurrence("X");
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        UniqueTagList tags = new UniqueTagList(tag1, tag2);
        return new Task(name, startTime, endTime, deadline, recurrence, tags);
    }

    /**
     * Generates a valid task using the given seed.
     * Running this function with the same parameter values guarantees the returned task will have the same state.
     * Each unique seed will generate a unique Task object.
     *
     * @param seed used to generate the task data field values
     */
    public Task generateTask(int seed) throws Exception {
        return new Task(
                new TaskName("Task " + seed),
                new TaskTime("" + Math.abs(seed)),
                new TaskTime("" + seed),
                new TaskTime("" + seed),
                new TaskRecurrence("" + seed),
                new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
        );
    }

    /** Generates the correct add command based on the task given */
    public String generateAddCommand(Task p) {
        StringBuffer cmd = new StringBuffer();

        cmd.append("add ");

        cmd.append(p.getName().toString());
        cmd.append(" .from ").append(p.getStartTime());
        cmd.append(" .to ").append(p.getEndTime());
        cmd.append(" .due ").append(p.getDeadline());
        cmd.append(" .every ").append(p.getRecurrence());
        UniqueTagList tags = p.getTags();
        for(Tag t: tags){
            cmd.append(" #").append(t.tagName);
        }

        return cmd.toString();
    }

    /**
     * Generates an TaskBook with auto-generated tasks.
     */
    public TaskBook generateTaskBook(int numGenerated) throws Exception{
        TaskBook taskBook = new TaskBook();
        addToTaskBook(taskBook, numGenerated);
        return taskBook;
    }

    /**
     * Generates an AddressBook based on the list of Tasks given.
     */
    public TaskBook generateTaskBook(List<Task> tasks) throws Exception{
        TaskBook taskBook = new TaskBook();
        addToTaskBook(taskBook, tasks);
        return taskBook;
    }

    /**
     * Adds auto-generated Task objects to the given Task Book
     * @param taskBook The Task Book to which the Tasks will be added
     */
    public void addToTaskBook(TaskBook taskBook, int numGenerated) throws Exception{
        addToTaskBook(taskBook, generateTaskList(numGenerated));
    }

    /**
     * Adds the given list of Tasks to the given Task Book
     */
    public void addToTaskBook(TaskBook taskBook, List<Task> tasksToAdd) throws Exception{
        for(Task p: tasksToAdd){
            taskBook.addTask(p);
        }
    }

    /**
     * Adds auto-generated Task objects to the given model
     * @param model The model to which the Tasks will be added
     */
    public void addToModel(Model model, int numGenerated) throws Exception{
        addToModel(model, generateTaskList(numGenerated));
    }

    /**
     * Adds the given list of Tasks to the given model
     */
    public void addToModel(Model model, List<Task> tasksToAdd) throws Exception{
        for(Task p: tasksToAdd){
            model.addTask(p);
        }
    }

    /**
     * Generates a list of Tasks based on the flags.
     */
    public List<Task> generateTaskList(int numGenerated) throws Exception{
        List<Task> tasks = new ArrayList<>();
        for(int i = 1; i <= numGenerated; i++){
            tasks.add(generateTask(i));
        }
        return tasks;
    }

    public List<Task> generateTaskList(Task... tasks) {
        return Arrays.asList(tasks);
    }

    /**
     * Generates a Task object with given name. Other fields will have some dummy values.
     */
    public Task generateTaskWithName(String name) throws Exception {
        return new Task(
                new TaskName(name),
                new TaskTime(""),
                new TaskTime(""),
                new TaskTime(""),
                new TaskRecurrence(""),
                new UniqueTagList(new Tag("tag"))
        );
    }
}