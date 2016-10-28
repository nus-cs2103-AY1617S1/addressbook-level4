package seedu.taskitty.model;

import java.util.List;
import java.util.Stack;

import seedu.taskitty.model.task.ReadOnlyTask;

/**@@author A0139052L
 * Class to store information of commands executed by user that is required for undo/redo function
 *
 */
public class CommandHistoryManager {
    
    private final Stack<String> historyCommandWords;
    private final Stack<String> historyCommandTexts;
    private final Stack<ReadOnlyTask> historyTasks;
    private final Stack<List<ReadOnlyTask>> historyListOfTasks;
    private final Stack<ReadOnlyTaskManager> historyTaskManagers;
    
    public CommandHistoryManager() {
        historyCommandWords = new Stack<String>();
        historyCommandTexts = new Stack<String>();
        historyTasks = new Stack<ReadOnlyTask>();
        historyListOfTasks = new Stack<List<ReadOnlyTask>>();
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
    
    public List<ReadOnlyTask> getListOfTasks() {
        assert !historyListOfTasks.isEmpty();
        return historyListOfTasks.pop();
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
    
    public void storeListOfTasks(List<ReadOnlyTask> listOfTasks) {
        historyListOfTasks.push(listOfTasks);
    }
    
    public void storeTaskManager(ReadOnlyTaskManager taskManager) {
        historyTaskManagers.push(taskManager);
    }
    
    public void clear() {
        historyCommandWords.clear();
        historyCommandTexts.clear();
        historyTasks.clear();
        historyListOfTasks.clear();
        historyTaskManagers .clear();
    }
    
}
