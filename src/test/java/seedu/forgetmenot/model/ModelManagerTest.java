package seedu.forgetmenot.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.forgetmenot.commons.exceptions.IllegalValueException;
import seedu.forgetmenot.model.task.Done;
import seedu.forgetmenot.model.task.Name;
import seedu.forgetmenot.model.task.Recurrence;
import seedu.forgetmenot.model.task.Task;
import seedu.forgetmenot.model.task.Time;
import seedu.forgetmenot.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.forgetmenot.testutil.TaskBuilder;
import seedu.forgetmenot.testutil.TestTask;

//@@author A0139671X
public class ModelManagerTest {
    
    @Test
    public void editTask_editName_changesNameOfTask() throws IllegalValueException, TaskNotFoundException {
        Task taskToChange = new Task(new Name("first name"), new Done(false), new Time("tmr 10pm"), new Time("tmr 11pm"), new Recurrence(""));
        Task taskToCheck = new Task(new Name("second name"), new Done(false), new Time("tmr 10pm"), new Time("tmr 11pm"), new Recurrence(""));
        ModelManager testModel = new ModelManager();
        testModel.addTask(taskToChange);
        testModel.editTask(taskToChange, "second name", null, null);
        
        assertEquals(taskToCheck, testModel.getTaskManager().getUniqueTaskList().getInternalList().get(0));
    }
    
    @Test
    public void editTask_editStartTime_changesStartOfTask() throws IllegalValueException, TaskNotFoundException {
        Task taskToChange = new Task(new Name("first name"), new Done(false), new Time("tmr 10pm"), new Time("tmr 11pm"), new Recurrence(""));
        Task taskToCheck = new Task(new Name("first name"), new Done(false), new Time("tmr 9pm"), new Time("tmr 11pm"), new Recurrence(""));
        ModelManager testModel = new ModelManager();
        testModel.addTask(taskToChange);
        testModel.editTask(taskToChange, null, "tomorrow 9pm", null);
        
        assertEquals(taskToCheck, testModel.getTaskManager().getUniqueTaskList().getInternalList().get(0));
    }
    @Test
    public void editTask_editEndTime_changesEndOfTask() throws IllegalValueException, TaskNotFoundException {
        Task taskToChange = new Task(new Name("first name"), new Done(false), new Time("tmr 10pm"), new Time("tmr 11pm"), new Recurrence(""));
        Task taskToCheck = new Task(new Name("first name"), new Done(false), new Time("tmr 10pm"), new Time("tmr 12am"), new Recurrence(""));
        ModelManager testModel = new ModelManager();
        testModel.addTask(taskToChange);
        testModel.editTask(taskToChange, null, null, "tmr midnight");
        
        assertEquals(taskToCheck, testModel.getTaskManager().getUniqueTaskList().getInternalList().get(0));
    }

    @Test
    public void addRecurringTask_addDefaultNumberOfRecurringEventTask_addsNineInstancesToTaskManager()
            throws IllegalValueException {
        TestTask recurringTask = new TaskBuilder().withName("recurring task").withStartTime("tomorrow 9pm")
                .withEndTime("tomorrow 10pm").withDone(false).withRecurrence("day").build();
        ModelManager testModel = new ModelManager();
        testModel.addRecurringTask(recurringTask);

        TestTask toCheck;
        StringBuilder addedTime = new StringBuilder("");
        for (int i = 0; i < Recurrence.DEFAULT_OCCURENCE - 1; i++) {
            addedTime.insert(0, "day after ");
            toCheck = new TaskBuilder().withName("recurring task").withStartTime(addedTime + "tomorrow 9pm")
                    .withEndTime(addedTime + "tomorrow 10pm").withDone(false).withRecurrence("day").build();
            assertEquals(testModel.getTaskManager().getUniqueTaskList().getInternalList().get(i), toCheck);

        }
    }

    @Test
    public void addRecurringTask_addSpecifiedNumberOfRecurringEventTask_addsSpecifiedInstancesToTaskManager()
            throws IllegalValueException {
        String specifiedOccurenceKeyword = " x";
        String specifiedOccurences = "5";
        TestTask recurringTask = new TaskBuilder().withName("recurring task 5 times").withStartTime("tomorrow 9pm")
                .withEndTime("tomorrow 10pm").withDone(false)
                .withRecurrence("2 days" + specifiedOccurenceKeyword + specifiedOccurences).build();
        ModelManager testModel = new ModelManager();
        testModel.addRecurringTask(recurringTask);

        TestTask toCheck;
        StringBuilder addedTime = new StringBuilder("");
        for (int i = 0; i < Integer.parseInt(specifiedOccurences) - 1; i++) {
            addedTime.insert(0, "2 days after ");
            toCheck = new TaskBuilder().withName("recurring task 5 times").withStartTime(addedTime + "tomorrow 9pm")
                    .withEndTime(addedTime + "tomorrow 10pm").withDone(false).withRecurrence("2 days").build();
            assertEquals(testModel.getTaskManager().getUniqueTaskList().getInternalList().get(i), toCheck);

        }
    }
    
    @Test
    public void addRecurringTask_addDefaultNumberOfRecurringDeadlineTask_addsNineInstancesToTaskManager()
            throws IllegalValueException {
        TestTask recurringTask = new TaskBuilder().withName("recurring deadline task").withStartTime("")
                .withEndTime("tomorrow 10pm").withDone(false).withRecurrence("day").build();
        ModelManager testModel = new ModelManager();
        testModel.addRecurringTask(recurringTask);

        TestTask toCheck;
        StringBuilder addedTime = new StringBuilder("");
        for (int i = 0; i < Recurrence.DEFAULT_OCCURENCE - 1; i++) {
            addedTime.insert(0, "day after ");
            toCheck = new TaskBuilder().withName("recurring deadline task").withStartTime("")
                    .withEndTime(addedTime + "tomorrow 10pm").withDone(false).withRecurrence("day").build();
            assertEquals(testModel.getTaskManager().getUniqueTaskList().getInternalList().get(i), toCheck);

        }
    }
    
//    @Test
//    public void addRecurringTask_addDefaultNumberOfRecurringStartOnlyTask_addsNineInstancesToTaskManager()
//            throws IllegalValueException {
//        TestTask recurringTask = new TaskBuilder().withName("recurring start only task").withStartTime("tmr 10am")
//                .withEndTime("").withDone(false).withRecurrence("day").build();
//        ModelManager testModel = new ModelManager();
//        testModel.addRecurringTask(recurringTask);
//
//        TestTask toCheck;
//        StringBuilder addedTime = new StringBuilder("");
//        for (int i = 0; i < Recurrence.DEFAULT_OCCURENCE - 1; i++) {
//            addedTime.insert(0, "day after ");
//            toCheck = new TaskBuilder().withName("recurring start only task").withStartTime(addedTime + "tmr 10am")
//                    .withEndTime("").withDone(false).withRecurrence("day").build();
//            assertEquals(testModel.getTaskManager().getUniqueTaskList().getInternalList().get(i), toCheck);
//
//        }
//    }
    
    //@@author A0139198N
    @Test
    public void doneCommand_doneASpecificTask_DoneValueChangesToDone() throws IllegalValueException, TaskNotFoundException {
        Task taskToDone = new Task(new Name("done task"), new Done(false), new Time("tmr 10pm"), new Time("tmr 11am"), new Recurrence(""));
        Task taskToCheck = new Task(new Name("done task"), new Done(true), new Time("tmr 10pm"), new Time("tmr 11am"), new Recurrence(""));
        ModelManager testModel = new ModelManager();
        testModel.addTask(taskToDone);
        testModel.doneTask(taskToDone);
        
        assertEquals(taskToCheck, testModel.getTaskManager().getUniqueTaskList().getInternalList().get(0));
    }
    
    @Test
    public void undoneCommand_undoneASpecificTask_DoneValueChangesToUndone() throws IllegalValueException, TaskNotFoundException {
        Task taskToDone = new Task(new Name("done task"), new Done(true), new Time("tmr 10pm"), new Time("tmr 11am"), new Recurrence(""));
        Task taskToCheck = new Task(new Name("done task"), new Done(false), new Time("tmr 10pm"), new Time("tmr 11am"), new Recurrence(""));
        ModelManager testModel = new ModelManager();
        testModel.addTask(taskToDone);
        testModel.undoneTask(taskToDone);
        
        assertEquals(taskToCheck, testModel.getTaskManager().getUniqueTaskList().getInternalList().get(0));
    }
    
    @Test
    public void sortTasks_addListOfUnsortedTasks_trueIfSorted() throws IllegalValueException {
    	Task task1 = new Task(new Name("task1"), new Done(false), new Time("5 days later"), new Time("next week"), new Recurrence(""));
    	Task task2 = new Task(new Name("task2"), new Done(false), new Time("today"), new Time("tmr"), new Recurrence(""));
    	Task task3 = new Task(new Name("task3"), new Done(false), new Time("tmr"), new Time("day after tmr 11am"), new Recurrence(""));
    	Task task4 = new Task(new Name("task4"), new Done(false), new Time("day after tmr"), new Time("4 days later"), new Recurrence(""));
    	Task task5 = new Task(new Name("task5"), new Done(false), new Time("1/1/18"), new Time("2/2/18"), new Recurrence(""));
    	
    	ModelManager initialUnsortedModel = new ModelManager();
    	initialUnsortedModel.addTask(task1);
    	initialUnsortedModel.addTask(task2);
    	initialUnsortedModel.addTask(task3);
    	initialUnsortedModel.addTask(task4);
    	initialUnsortedModel.addTask(task5);
    	
    	initialUnsortedModel.sortTasks();
 
    	ModelManager initialSortedModel = new ModelManager();
    	initialSortedModel.addTask(task2);
    	initialSortedModel.addTask(task3);
    	initialSortedModel.addTask(task4);
    	initialSortedModel.addTask(task1);
    	initialSortedModel.addTask(task5);
    	
    	for(int i = 0; i < 5; i++)
    		assertEquals(initialSortedModel.getTaskManager().getTaskList().get(i), initialUnsortedModel.getTaskManager().getTaskList().get(i));
    	
    }
    
}
