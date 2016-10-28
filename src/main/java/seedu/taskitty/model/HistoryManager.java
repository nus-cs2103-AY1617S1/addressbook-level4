package seedu.taskitty.model;

import java.util.Stack;

import seedu.taskitty.model.task.ReadOnlyTask;

/**@@author A0139052L
 * Class to store information of commands executed by user that is required for undo/redo function
 *
 */
public class HistoryManager {
    
    private final Stack<String> historyCommandWords;
    private final Stack<String> historyCommandTexts;
    private final Stack<ReadOnlyTask> historyTasks;
    private final Stack<Integer> historyNumberOfTasks;
    private final Stack<ReadOnlyTaskManager> historyTaskManagers;
    
    public HistoryManager() {
        historyCommandWords = new Stack<String>();
        historyCommandTexts = new Stack<String>();
        historyTasks = new Stack<ReadOnlyTask>();
        historyNumberOfTasks = new Stack<Integer>();
        historyTaskManagers = new Stack<ReadOnlyTaskManager>();
    }
    
    public boolean hasPreviousValidCommand() {
        return !historyCommandWords.isEmpty();
    }
    
    public String getCommandWord() {
        assert !historyCommandWords.isEmpty();
        return historyCommandWords.pop();
    }
    
    public String getCommandText() {
        assert !historyCommandTexts.isEmpty();
        return historyCommandTexts.pop();
    }
    
    public ReadOnlyTask getTask() {
        assert !historyTasks.isEmpty();
        return historyTasks.pop();
    }       
    
    public int getNumberOfTasks() {
        assert !historyNumberOfTasks.isEmpty();
        return historyNumberOfTasks.pop();
    }
    
    public ReadOnlyTaskManager getTaskManager() {
        assert !historyTaskManagers.isEmpty();
        return historyTaskManagers.pop();
    }
    
    public void storeCommandWord(String command) {
        historyCommandWords.push(command);
    }
    
    public void storeCommandText(String commandText) {
        historyCommandTexts.push(commandText);
    }
    
    public void storeTask(ReadOnlyTask task) {
        historyTasks.push(task);
    }
    
    public void storeNumberOfTasks(int numberOfTask) {
        historyNumberOfTasks.push(numberOfTask);
    }
    
    public void storeTaskManager(ReadOnlyTaskManager taskManager) {
        historyTaskManagers.push(taskManager);
    }
    
    public void clear() {
        historyCommandWords.clear();
        historyCommandTexts.clear();
        historyTasks.clear();
        historyNumberOfTasks.clear();
        historyTaskManagers .clear();
    }
    
}
