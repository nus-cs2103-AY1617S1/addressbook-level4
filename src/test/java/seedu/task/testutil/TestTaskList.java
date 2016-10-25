package seedu.task.testutil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@@author A0138601M
public class TestTaskList {
    private ArrayList<TestTask> testCompleteTasks;
    private ArrayList<TestTask> testIncompleteTasks;
    
    public TestTaskList() {
        clear();
    }
    
    public TestTaskList(List<TestTask> incompleteList, List<TestTask> completeList) {
        testCompleteTasks = new ArrayList<TestTask>(completeList);
        testIncompleteTasks = new ArrayList<TestTask>(incompleteList);
    }
    
    public TestTaskList(TestTask[] testTasks) {
        this();
        for (TestTask task : testTasks) {
            if (task.getStatus().isComplete()) {
                testCompleteTasks.add(task);
                Collections.sort(testCompleteTasks);
            }
            else {
                testIncompleteTasks.add(task);
                Collections.sort(testIncompleteTasks);
            }
        }
    }
    
    public TestTask[] getCompleteList() {
        TestTask[] completeTasks = new TestTask[testCompleteTasks.size()];
        return testCompleteTasks.toArray(completeTasks);
    }
    
    public TestTask[] getIncompleteList() {
        TestTask[] incompleteTasks = new TestTask[testIncompleteTasks.size()];
        return testIncompleteTasks.toArray(incompleteTasks);
    }
    
    public void clear() {
        testCompleteTasks = new ArrayList<TestTask>();
        testIncompleteTasks = new ArrayList<TestTask>();
    }
    
    /**
     * Add tasks to the list of tasks.
     * @param tasks A array of tasks.
     * @param tasksToAdd The tasks that are to be added into the original array.
     * @return The modified array of tasks.
     */
    public void addTasksToList(TestTask taskToAdd) {
        testIncompleteTasks.add(taskToAdd);
        Collections.sort(testIncompleteTasks);
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
            }
            else {
                testCompleteTasks.remove(tasksToDelete[i]);
            }
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
        Collections.sort(testCompleteTasks);
    }
    
}
