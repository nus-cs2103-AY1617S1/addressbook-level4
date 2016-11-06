package seedu.task.testutil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.todolist.model.task.Status;

//@@author A0138601M
public class TestTaskList {
    private ArrayList<TestTask> testCompleteTasks;
    private ArrayList<TestTask> testIncompleteTasks;
    private ArrayList<TestTask> testOverdueTasks;
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
    public TestTaskList(List<TestTask> incompleteList, List<TestTask> completeList, List<TestTask> overdueList) {
        testCompleteTasks = new ArrayList<TestTask>(completeList);
        testIncompleteTasks = new ArrayList<TestTask>(incompleteList);
        testOverdueTasks = new ArrayList<TestTask>(overdueList);
        numberOfTask = incompleteList.size() + completeList.size() + overdueList.size();
    }
    
    /**
     * Constructs a test task list from an array of test tasks
     */
    public TestTaskList(TestTask[] testTasks) {
        this();
        for (TestTask task : testTasks) {
            if (task.getStatus().isComplete()) {
                testCompleteTasks.add(task);
                Collections.sort(testCompleteTasks);
            } else if (task.getStatus().isIncomplete()){
                testIncompleteTasks.add(task);
                Collections.sort(testIncompleteTasks);
            } else if (task.getStatus().isOverdue()){
                testOverdueTasks.add(task);
                Collections.sort(testOverdueTasks);
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
    
    public TestTask[] getOverdueList() {
        TestTask[] overdueTasks = new TestTask[testOverdueTasks.size()];
        return testOverdueTasks.toArray(overdueTasks);
    }
    
    public int getNumberOfTask() {
        return numberOfTask;
    }
    
    /**
     * Empty all three list
     */
    public void clear() {
        testCompleteTasks = new ArrayList<TestTask>();
        testIncompleteTasks = new ArrayList<TestTask>();
        testOverdueTasks = new ArrayList<TestTask>();
        numberOfTask = 0;
    }
    
    /**
     * Add tasks to the list of tasks.
     * @param tasks an array of tasks.
     * @param tasksToAdd The tasks that are to be added into the original array.
     * @return The modified array of tasks.
     */
    public void addTasksToList(TestTask taskToAdd) {
        switch (taskToAdd.getStatus().getType()) {
        
        case Complete :
            testCompleteTasks.add(taskToAdd);
            Collections.sort(testCompleteTasks);
            break;
            
        case Incomplete :
            testIncompleteTasks.add(taskToAdd);
            Collections.sort(testIncompleteTasks);
            break;
            
        case Overdue :
            testOverdueTasks.add(taskToAdd);
            Collections.sort(testOverdueTasks);
            break;
            
        default :
            assert false : "Type must be either Complete, Incomplete or Overdue";
            break;
        
        }
        
        numberOfTask++;
    }
    
    /**
     * Removes a subset from the list of tasks.
     * @param targetIndexes The indexes of task targeted tasks
     * @param type determines the list to work on
     * @return The modified task list after removal of the subset from the list.
     */
    public void removeTasksFromList(int[] targetIndexes, Status.Type type) {
        ArrayList<TestTask> targetList = getTargetList(type);
        ArrayList<TestTask> tasksToDelete = getTargetTasks(targetIndexes, targetList);
        for (int i = 0; i < tasksToDelete.size(); i++) {
            targetList.remove(tasksToDelete.get(i));
            numberOfTask--;
        }
    }

    /**
     * Marks a subset from the list of incomplete tasks.
     * @param tasksToMark The subset of tasks.
     * @return The modified tasks after marking of the subset from tasks.
     */
    public void markTasksFromList(int[] targetIndexes, Status.Type type) {
        ArrayList<TestTask> targetList = getTargetList(type);
        ArrayList<TestTask> tasksToMark = getTargetTasks(targetIndexes, targetList);
        for (int i = 0; i < tasksToMark.size(); i++) {
            targetList.remove(tasksToMark.get(i));
            testCompleteTasks.add(tasksToMark.get(i));
        }
        Collections.sort(testCompleteTasks);
    }
    
    /**
     * Gets a list of task that were targeted for operations
     * @param targetIndexes The indexes of task targeted tasks
     * @param targetList the list to work on
     * @return an array of task to be operated on
     */
    private ArrayList<TestTask> getTargetTasks(int[] targetIndexes, ArrayList<TestTask> targetList) {
        ArrayList<TestTask> targetTasks = new ArrayList<TestTask>();
        for (int index : targetIndexes) {
            targetTasks.add(targetList.get(index - 1)); //-1 because array uses zero indexing
        }
        return targetTasks;
    }

    /**
     * @param type determine the list to be worked on
     * @return the list that were targeted
     */
    private ArrayList<TestTask> getTargetList(Status.Type type) {
        switch (type) {
        
        case Complete :
            return testCompleteTasks;
            
        case Incomplete :
            return testIncompleteTasks;
            
        case Overdue :
            return testOverdueTasks;
            
        default :
            assert false : "Type must be either Complete, Incomplete or Overdue";
            return null;
        }
    }
    
    //@@author A0146682X
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
    //@@author  
}
