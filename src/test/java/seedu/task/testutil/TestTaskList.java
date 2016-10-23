package seedu.task.testutil;

import java.util.ArrayList;
import java.util.List;

public class TestTaskList {
    private ArrayList<TestTask> testCompleteTasks;
    private ArrayList<TestTask> testIncompleteTasks;
    
    public TestTaskList() {
        testCompleteTasks = new ArrayList<TestTask>();
        testIncompleteTasks = new ArrayList<TestTask>();
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
            }
            else {
                testIncompleteTasks.add(task);
            }
        }
    }
    
    public TestTask[] getList(boolean isCompleteList) {
        if (isCompleteList) {
            return getCompleteList();
        }
        else {
            return getIncompleteList();
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
    
}
