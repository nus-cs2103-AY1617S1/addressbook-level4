package seedu.taskitty.testutil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import edu.emory.mathcs.backport.java.util.Arrays;
import guitests.guihandles.TaskListPanelHandle;
import seedu.taskitty.commons.core.LogsCenter;
import seedu.taskitty.model.task.Task;

public class TestTaskList {
    private List<TestTask> todoList;
    private List<TestTask> deadlineList;
    private List<TestTask> eventList;
    
    private final Logger logger = LogsCenter.getLogger(TestTaskList.class);
    
    public TestTaskList(TestTask[] taskList) {
        clear();
        splitTaskList(taskList);
    }
    
    public TestTaskList(TestTask[] todoList, TestTask[] deadlineList, TestTask[] eventList) {
        this.todoList = new ArrayList<TestTask>(Arrays.asList(todoList));
        this.deadlineList = new ArrayList<TestTask>(Arrays.asList(deadlineList));
        this.eventList = new ArrayList<TestTask>(Arrays.asList(eventList));
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
            todoList.sort(null);
            break;
            
        case Task.DEADLINE_COMPONENT_COUNT :
            deadlineList.add(task);
            deadlineList.sort(null);
            break;
            
        case Task.EVENT_COMPONENT_COUNT :
            eventList.add(task);
            eventList.sort(null);
            break;
            
        default :
            logger.warning("Task did not fit in any list");
        }
    }
    
    public void removeTaskFromList(TestTask task) {
        
    }
    
    public void clear() {
        todoList = new ArrayList<TestTask>();
        deadlineList = new ArrayList<TestTask>();
        eventList = new ArrayList<TestTask>();
    }
    
    public TestTaskList copy() {
        return new TestTaskList(todoList.toArray(new TestTask[todoList.size()]),
                deadlineList.toArray(new TestTask[deadlineList.size()]),
                eventList.toArray(new TestTask[eventList.size()]));
    }
    
    public boolean isListMatching(TaskListPanelHandle taskListPanel) {
        return taskListPanel.isTodoListMatching(todoList.toArray(new TestTask[todoList.size()]))
        && taskListPanel.isDeadlineListMatching(deadlineList.toArray(new TestTask[deadlineList.size()]))
        && taskListPanel.isEventListMatching(eventList.toArray(new TestTask[eventList.size()]));
    }

}
