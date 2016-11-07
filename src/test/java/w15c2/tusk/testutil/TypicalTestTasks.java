package w15c2.tusk.testutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import w15c2.tusk.commons.collections.UniqueItemCollection;
import w15c2.tusk.model.Alias;
import w15c2.tusk.model.ModelManager;
import w15c2.tusk.model.task.DeadlineTask;
import w15c2.tusk.model.task.EventTask;
import w15c2.tusk.model.task.FloatingTask;
import w15c2.tusk.model.task.Task;

/**
 * Creates a model manager with sample tasks loaded for testing purposes
 */
public class TypicalTestTasks {

    public static Task task1, task2, task3, task4, task5, task6, task7, extraTask1, extraTask2;
    public static Alias alias1, alias2, extraAlias;

    public TypicalTestTasks() {
        try {
        	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            task1 = new FloatingTask("Initial Test Task 1");
            task2 = new FloatingTask("Initial Test Task 2");
            task3 = new DeadlineTask("Initial Test Task 3", df.parse("20/03/2016"));
            task4 = new DeadlineTask("Initial Test Task 4", df.parse("30/12/2016"));
            task5 = new EventTask("Initial Test Task 5", df.parse("17/06/2016"), df.parse("20/06/2016"));
            task6 = new EventTask("Initial Test Task 6", df.parse("27/10/2016"), df.parse("29/10/2016"));
            task7 = new FloatingTask("Initial Test Task 7");
            
            extraTask1 =  new FloatingTask("Extra Test Task 1");
            extraTask2 =  new FloatingTask("Extra Test Task 2");
            
            alias1 = new Alias("am", "add meeting");
            alias2 = new Alias("ad", "add dinner");
            
            extraAlias = new Alias("ae", "add event");
        } catch (ParseException e) {
			e.printStackTrace();
            assert false : "not possible";
		}
    }

    public static void loadModelManagerWithSampleData(ModelManager tm) {
        try {
            tm.addTask(task1.copy());
            tm.addTask(task2.copy());
            tm.addTask(task3.copy());
            tm.addTask(task4.copy());
            tm.addTask(task5.copy());
            tm.addTask(task6.copy());
            tm.addTask(task7.copy());
            tm.addAlias(new Alias(alias1));
            tm.addAlias(new Alias(alias2));
        } catch (UniqueItemCollection.DuplicateItemException e) {
            assert false : "not possible";
        }
    }

    public Task[] getTypicalTasks() {
        return new Task[]{task1, task2, task3, task4, task5, task6, task7};
    }

    public ModelManager getTypicalModelManager() {
        ModelManager tm = new ModelManager();
        loadModelManagerWithSampleData(tm);
        return tm;
    }
}

