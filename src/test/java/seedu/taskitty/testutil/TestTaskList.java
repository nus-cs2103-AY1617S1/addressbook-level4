package seedu.taskitty.testutil;

import java.util.ArrayList;

import seedu.taskitty.model.task.Task;

public class TestTaskList {
    private ArrayList<TestTask> todoList;
    private ArrayList<TestTask> deadlineList;
    private ArrayList<TestTask> eventList;
    
    public TestTaskList(TestTask[] taskList) {
        splitTaskList(taskList);
    }
    
    private void splitTaskList(TestTask[] taskList) {
        for (TestTask task : taskList) {
            switch (task.getNumArgs()) {
            
            case Task.TASK_COMPONENT_COUNT :
                todoList.add(task);
                break;
                
            case Task.DEADLINE_COMPONENT_COUNT :
                deadlineList.add(task);
                break;
                
            case Task.EVENT_COMPONENT_COUNT :
                eventList.add(task);
                break;
            }
        }
    }

}
