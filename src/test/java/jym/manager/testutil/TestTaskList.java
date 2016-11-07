package jym.manager.testutil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@@author a0153617e
public class TestTaskList {
    private ArrayList<TestTask> testCompleteTasks;
    private ArrayList<TestTask> testIncompleteTasks;
    private int numberOfTask;
    
    /**
     * Constructs an empty test task list
     */
    public TestTaskList() {
        clear();
    }
    
    /**
     * Constructs a test task list from lists of incomplete list and complete list
     */
    public TestTaskList(List<TestTask> incompleteList, List<TestTask> completeList) {
        testCompleteTasks = new ArrayList<TestTask>(completeList);
        testIncompleteTasks = new ArrayList<TestTask>(incompleteList);
        numberOfTask = incompleteList.size() + completeList.size();
    }
    
    /**
     * Constructs a test task list from an array of test tasks
     */
    public TestTaskList(TestTask[] testTasks) {
        this();
        for (TestTask task : testTasks) {
            if (task.getStatus().isComplete()) {
                testCompleteTasks.add(task);
//                Collections.sort(testCompleteTasks);
            } else {
                testIncompleteTasks.add(task);
//                Collections.sort(testIncompleteTasks);
            }
        }
        numberOfTask = testTasks.length;
    }
    
    public TestTask[] getCompleteList() {
        TestTask[] completeTasks = new TestTask[testCompleteTasks.size()];
        return testCompleteTasks.toArray(completeTasks);
    }
    
    public TestTask[] getIncompleteList() {
        TestTask[] incompleteTasks = new TestTask[testIncompleteTasks.size()];
        return testIncompleteTasks.toArray(incompleteTasks);
    }
    
    public int getNumberOfTask() {
    	return numberOfTask;
    }
    
    public void clear() {
        testCompleteTasks = new ArrayList<TestTask>();
        testIncompleteTasks = new ArrayList<TestTask>();
        numberOfTask = 0;
    }
    
    /**
     * Add tasks to the list of tasks.
     * @param tasks an array of tasks.
     * @param tasksToAdd The tasks that are to be added into the original array.
     * @return The modified array of tasks.
     */
    public void addTasksToList(TestTask taskToAdd) {
        testIncompleteTasks.add(taskToAdd);
//        Collections.sort(testIncompleteTasks);
        numberOfTask++;
    }
    
    /**
     * Removes a subset from the list of tasks.
     * @param tasksToRemove The subset of tasks.
     * @param isFromIncompleteList Whether to delete from incomplete list or complete list
     * @return The modified tasks after removal of the subset from tasks.
     */
    public void removeTasksFromList(TestTask[] tasksToDelete, boolean isFromIncompleteList) {
        for (int i = 0; i < tasksToDelete.length; i++) {
            if (isFromIncompleteList) {
                testIncompleteTasks.remove(tasksToDelete[i]);
            } else {
                testCompleteTasks.remove(tasksToDelete[i]);
            }
            numberOfTask--;
        }
    }

    /**
     * Marks a subset from the list of incomplete tasks.
     * @param tasksToMark The subset of tasks.
     * @return The modified tasks after marking of the subset from tasks.
     */
    public void markTasksFromList(TestTask[] tasksToMark) {
        for (int i = 0; i < tasksToMark.length; i++) {
            testIncompleteTasks.remove(tasksToMark[i]);
            testCompleteTasks.add(tasksToMark[i]);
        }
//        Collections.sort(testCompleteTasks);
    }
    
    /**
     * edits a task in the list tasks.
     * @param index the index of task to edit
     * @param isFromIncompleteList Whether to edit from incomplete list or complete list
     */
    public void editTask(int index, TestTask newTask, boolean isFromIncompleteList) {
    	if (isFromIncompleteList) {
    		testIncompleteTasks.set(index-1, newTask);
    	}
    	else {
    		testCompleteTasks.set(index-1, newTask);
    	}
    }
}

