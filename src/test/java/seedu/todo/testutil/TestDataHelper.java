package seedu.todo.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.todo.model.Model;
import seedu.todo.model.DoDoBird;
import seedu.todo.model.task.Detail;
import seedu.todo.model.task.Name;
import seedu.todo.model.task.Priority;
import seedu.todo.model.task.Recurrence;
import seedu.todo.model.task.Recurrence.Frequency;
import seedu.todo.model.task.Task;
import seedu.todo.model.task.TaskDate;

/**
 * A utility class to generate test data.
 */
public class TestDataHelper{

    /**
     * Generates a valid full task using the given seed.
     * Running this function with the same parameter values guarantees the returned task will have the same state.
     * Each unique seed will generate a unique Task object.
     *
     * @param seed used to generate the task data field values
     */
    public Task generateFullTask(int seed) throws Exception {
        return new Task(
                new Name("Task " + seed),
                new Detail("House of " + seed),
                new TaskDate("2/3/2017 12:34 pm", TaskDate.TASK_DATE_ON),
                new TaskDate("2/3/2017 12:34 pm", TaskDate.TASK_DATE_BY),
                new Priority("low"),
                new Recurrence(Frequency.NONE)
        );
    }
    
    /**
     * Generates a valid floating task using the given seed.
     * Running this function with the same parameter values guarantees the returned task will have the same state.
     * Each unique seed will generate a unique Task object.
     *
     * @param seed used to generate the task data field values
     */
    public Task generateFloatingTask(int seed) throws Exception {
        return new Task(
                new Name("Task " + seed),
                new Detail("House of " + seed),
                new TaskDate("", TaskDate.TASK_DATE_ON),
                new TaskDate("", TaskDate.TASK_DATE_BY),
                new Priority("low"),
                new Recurrence(Frequency.NONE)
        );
    }
    
    /**
     * Generates a valid deadline task using the given seed.
     * Running this function with the same parameter values guarantees the returned task will have the same state.
     * Each unique seed will generate a unique Task object.
     *
     * @param seed used to generate the task data field values
     */
    public Task generateDeadlineTask(int seed) throws Exception {
        return new Task(
                new Name("Task " + seed),
                new Detail("House of " + seed),
                new TaskDate("", TaskDate.TASK_DATE_ON),
                new TaskDate("2/3/2017 12:34 pm", TaskDate.TASK_DATE_BY),
                new Priority("low"),
                new Recurrence(Frequency.NONE)
        );
    }
    
    /** Generates the correct add command based on the task given */
    public String generateAddCommand(Task p) {
        StringBuffer cmd = new StringBuffer();

        cmd.append("add ");
        cmd.append(p.getName().toString());
        if (p.getOnDate().getDate() != null) {
            cmd.append(" on ").append(p.getOnDate().toString());
        }
        if (p.getByDate().getDate() != null) {
            cmd.append(" by ").append(p.getByDate().toString());
        }
        cmd.append(" ;").append(p.getDetail());
        
        return cmd.toString();
    }

    /**
     * Generates an ToDoList with auto-generated tasks.
     */
    public DoDoBird generateToDoList(int numGenerated) throws Exception{
        DoDoBird toDoList = new DoDoBird();
        addToToDoList(toDoList, numGenerated);
        return toDoList;
    }

    /**
     * Generates an ToDoList based on the list of Tasks given.
     */
    public DoDoBird generateToDoList(List<Task> tasks) throws Exception{
        DoDoBird toDoList = new DoDoBird();
        addToToDoList(toDoList, tasks);
        return toDoList;
    }

    /**
     * Adds auto-generated Task objects to the given ToDoList
     * @param toDoList The ToDoList to which the Tasks will be added
     */
    public void addToToDoList(DoDoBird toDoList, int numGenerated) throws Exception{
        addToToDoList(toDoList, generateTaskList(numGenerated));
    }

    /**
     * Adds the given list of Tasks to the given ToDoList
     */
    public void addToToDoList(DoDoBird toDoList, List<Task> tasksToAdd) throws Exception{
        for (Task p: tasksToAdd){
            toDoList.addTask(p);
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
        for (Task p: tasksToAdd){
            model.addTask(p);
        }
    }

    /**
     * Generates a list of Tasks based on the flags.
     */
    public List<Task> generateTaskList(int numGenerated) throws Exception{
        List<Task> tasks = new ArrayList<>();
        for (int i = 1; i <= numGenerated; i++){
            tasks.add(generateFullTask(i));
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
                new Name(name),
                new Detail("1"),
                new TaskDate("5/3/2017 12:44 pm", TaskDate.TASK_DATE_ON),
                new TaskDate("5/3/2017 12:44 pm", TaskDate.TASK_DATE_BY),
                new Priority("low"),
                new Recurrence(Frequency.NONE)
        );
    }
    
    /**
     * Generates a Task object with given dates. Other fields will have some dummy values.
     */
    public Task generateTaskWithDates(String onDateString, String byDateString) throws Exception {
        return new Task(
                new Name("Task"),
                new Detail("1"),
                new TaskDate(onDateString, TaskDate.TASK_DATE_ON),
                new TaskDate(byDateString, TaskDate.TASK_DATE_BY),
                new Priority("low"),
                new Recurrence(Frequency.NONE)
        );
    }

    public Task generateNoPriorityTask(int i) throws Exception {
        return new Task(
                new Name("Task number " + i),
                new Detail("Index number " + i),
                new TaskDate("", TaskDate.TASK_DATE_ON),
                new TaskDate("2/3/2017 12:34 pm", TaskDate.TASK_DATE_BY),
                new Recurrence(Frequency.NONE)
        );
    }
}
