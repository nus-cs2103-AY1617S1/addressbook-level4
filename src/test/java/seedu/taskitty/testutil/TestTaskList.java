package seedu.taskitty.testutil;

import java.util.ArrayList;
import java.util.logging.Logger;

import guitests.guihandles.TaskListPanelHandle;
import seedu.taskitty.commons.core.LogsCenter;
import seedu.taskitty.model.task.Task;

public class TestTaskList {
    private ArrayList<TestTask> todoList;
    private ArrayList<TestTask> deadlineList;
    private ArrayList<TestTask> eventList;
    
    private final Logger logger = LogsCenter.getLogger(TestTaskList.class);
    
    public TestTaskList(TestTask[] taskList) {
        clear();
        splitTaskList(taskList);
    }
    
    private void splitTaskList(TestTask[] taskList) {
        for (TestTask task : taskList) {
            addTaskToList(task);
        }
    }
    
    public void addTaskToList(TestTask task) {
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
            
        default :
            logger.warning("Task did not fit in any list");
        }
    }
    
    public void clear() {
        todoList = new ArrayList<TestTask>();
        deadlineList = new ArrayList<TestTask>();
        eventList = new ArrayList<TestTask>();
    }
    
    public boolean isListMatching(TaskListPanelHandle taskListPanel) {
        return taskListPanel.isTodoListMatching(todoList.toArray(new TestTask[todoList.size()]))
        && taskListPanel.isDeadlineListMatching(deadlineList.toArray(new TestTask[deadlineList.size()]))
        && taskListPanel.isEventListMatching(eventList.toArray(new TestTask[eventList.size()]));
    }

}
