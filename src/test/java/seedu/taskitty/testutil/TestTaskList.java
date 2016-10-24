//@@author A0139930B
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
        this.todoList.sort(null);
        this.deadlineList.sort(null);
        this.eventList.sort(null);
    }
    
    private void splitTaskList(TestTask[] taskList) {
        for (TestTask task : taskList) {
            addTaskToList(task);
        }
    }
    
    public void addTaskToList(TestTask task) {
        switch (task.getPeriod().getNumArgs()) {
        
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
    
    public TestTask getTaskFromList(int index, char category) {
        return getCategoryList(category).get(index);  
    }
    
    public void editTaskFromList(int index, char category, TestTask task) {
        removeTaskFromList(index, category);
        addTaskToList(task);
    }
    
    public void removeTaskFromList(int index, char category) {
        getCategoryList(category).remove(index);
    }
    
    public void removeTaskFromList(int index) {
        removeTaskFromList(index, ' ');;
    }
    
    public void markTaskAsDoneInList(int index, char category, TestTask task) {
    	removeTaskFromList(index, category);
    	task.markAsDone();
    	addTaskToList(task);
    }
    
    public int size(char category) {
        return getCategoryList(category).size();
    }
    
    public int size() {
        return size(' ');
    }
    
    public List<TestTask> getCategoryList(char category) {
        switch (category) {
        
        case(Task.TODO_CATEGORY_CHAR) :
            return todoList;
        
        case (Task.DEADLINE_CATEGORY_CHAR) :
            return deadlineList;
            
        case (Task.EVENT_CATEGORY_CHAR) :
            return eventList;
            
        default :
            return todoList;
        }
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
