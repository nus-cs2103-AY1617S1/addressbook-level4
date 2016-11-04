package seedu.taskitty.logic.commands;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.commons.core.UnmodifiableObservableList;
import seedu.taskitty.commons.util.AppUtil;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.model.task.Task;

//@@author A0130853L
/**
 * Marks a task as done identified using it's last displayed index from the task manager.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD + " [index] [more indexes]...";
    public static final String MESSAGE_USAGE = "This command marks tasks in TasKitty as done, Meow!"
            + "\n[index] is the index eg. t1, d1, e1.";

    public static final String MESSAGE_MARK_TASK_AS_DONE_SUCCESS_HEADER = "%1$s" + " tasks marked as done: ";
    public static final String MESSAGE_DUPLICATE_MARK_AS_DONE_ERROR_HEADER = "These tasks has already been marked as done: ";
    
    private boolean hasInvalidIndex;  
    private boolean hasDuplicateMarkAsDoneTask;  
    private boolean hasDuplicateIndexesProvided;
    
    private final List<Pair<Integer, Integer>> listOfIndexes;
    
    private final String commandText;
    
    private int categoryIndex;
    private int targetIndex;
    private String currentTaskIndex;
    
    private UnmodifiableObservableList<ReadOnlyTask> lastShownList;
    private ArrayList<ReadOnlyTask> listOfTasksToMarkDone;
    private ReadOnlyTask taskToMark;
    
    private StringBuilder invalidIndexMessage;
    private StringBuilder duplicateMarkAsDoneMessage;
    private StringBuilder duplicateIndexesProvidedMessage;
    private StringBuilder resultMessage;
    
    
    public DoneCommand(List<Pair<Integer, Integer>> listOfIndexes, String commandText) {
        assert listOfIndexes != null;
        this.listOfIndexes = listOfIndexes;
        this.hasInvalidIndex = false;
        this.hasDuplicateMarkAsDoneTask = false;
        this.hasDuplicateIndexesProvided = false;
        this.commandText = commandText;
    }

    @Override
    public CommandResult execute() {
     
        initialiseMessageBuildersAndTasksToMarkList();
        evaluatePresenceOfErrors();
        
        String errorMessage = generateErrorMessage();
        if (errorMessage != null) { // there are errors
            return new CommandResult(errorMessage);
        }
        
        executeMarkTasks();

        return new CommandResult(generateSuccessMessage());
    }
    
    /**
     * This method evaluates each entered index for 3 types of errors: invalid index, duplicate mark as done,
     * and duplicate indexes entered, and then sets the relevant boolean variables as true accordingly.
     */
    private void evaluatePresenceOfErrors() {
        for (Pair<Integer, Integer> indexPair: listOfIndexes) {
            setRelevantIndexesAndList(indexPair);
            if (hasInvalidIndex()) {
                continue;                                
            }
            if (hasDuplicateMarkAsDone()) {
                continue;
            }
            evaluateHasDuplicateIndexes();     
        }
    }
    
    /**
     * This method initialises the error message builders for each of the possible error cases, as well as the success message
     * builder for the case of successful execution.
     * It also initialises an empty arraylist to store the tasks to be marked as done so as to iterate through them subsequently.
     */
    private void initialiseMessageBuildersAndTasksToMarkList() {
        listOfTasksToMarkDone = new ArrayList<ReadOnlyTask>();
        invalidIndexMessage = new StringBuilder(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX + ": ");
        duplicateMarkAsDoneMessage = new StringBuilder(MESSAGE_DUPLICATE_MARK_AS_DONE_ERROR_HEADER);
        duplicateIndexesProvidedMessage = new StringBuilder(Messages.MESSAGE_DUPLICATE_INDEXES_PROVIDED + ": ");
        resultMessage = new StringBuilder(String.format(MESSAGE_MARK_TASK_AS_DONE_SUCCESS_HEADER, listOfIndexes.size()));
    }
    
    /**
     * This method takes in the relevant index that is currently being evaluated and extracts the actual index of the task in the list in
     * each iteration of evaluation. It also targets the correct list out of the 3 lists.
     */
    private void setRelevantIndexesAndList(Pair<Integer, Integer> indexPair) {
        categoryIndex = indexPair.getKey();
        targetIndex = indexPair.getValue();
        assert categoryIndex >= 0 && categoryIndex < 3;
        
        currentTaskIndex = Task.CATEGORIES[categoryIndex] + targetIndex + " ";
        
        lastShownList = AppUtil.getCorrectListBasedOnCategoryIndex(model, categoryIndex);
    }
    
    /**
     * This method calls the model to mark the specified tasks as done and stores the command for usage during undo/redo.
     * @throws TaskNotFoundException
     * @throws DuplicateMarkAsDoneException
     */
    private void executeMarkTasks() {
        model.markTasksAsDone(listOfTasksToMarkDone);
        model.storeCommandInfo(COMMAND_WORD, commandText, listOfTasksToMarkDone);
    }
    
    /**
     * This method generates a string representing the collated lists of tasks that were successfully marked as done
     * built from the success message builder.
     */
    private String generateSuccessMessage() {
        resultMessage.delete(resultMessage.length() - 2, resultMessage.length());// remove the extra separator at the end
        return resultMessage.toString();     
    }
    
    /** This method generates an error message based on the truth values of the 3 boolean error variables.
     * If none of them are true, it means there are no errors detected and hence it returns null.
     * @return either the specific error message based on one of the boolean variables, or null.
     */
    private String generateErrorMessage() {
        if (hasInvalidIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return invalidIndexMessage.toString().trim();
        }
        
        if (hasDuplicateIndexesProvided) {
            indicateAttemptToExecuteIncorrectCommand();
            return duplicateIndexesProvidedMessage.toString().trim();
        }
        
        if (hasDuplicateMarkAsDoneTask) {
            return duplicateMarkAsDoneMessage.toString().trim();
        }
        
        return null; // no errors
    }
    
    /**
     * This method evaluates to true if an invalid index is detected, and false otherwise.
     * If true, it also appends the problematic task index to the message builder.
     */
    private boolean hasInvalidIndex() {
        if (lastShownList.size() < targetIndex) {
            invalidIndexMessage.append(currentTaskIndex);
            return hasInvalidIndex = true;                               
        }
        return false;
    }
    
    /**
     * This method evaluates to true if a task that has already been marked as done is being marked again, and false otherwise.
     * If true, it also appends the problematic task index to the message builder.
     */
    private boolean hasDuplicateMarkAsDone() {
        taskToMark = lastShownList.get(targetIndex - 1); 
        if (taskToMark.getIsDone()) {
            duplicateMarkAsDoneMessage.append(currentTaskIndex);
            return hasDuplicateMarkAsDoneTask = true;
        }
        return false;
    }
    
    /**
     *  This method checks if there are duplicate indexes that were entered by the user to mark as done.
     *  If there are, then it appends the duplicated index to the message builder.
     *  If not, it appends to the success message builder instead.
     */
    private void evaluateHasDuplicateIndexes() {
        if (!listOfTasksToMarkDone.contains(taskToMark)) {
            listOfTasksToMarkDone.add(taskToMark);
            resultMessage.append(taskToMark.getName() + ", ");
        } else {
            duplicateIndexesProvidedMessage.append(currentTaskIndex);
            hasDuplicateIndexesProvided = true;  
        }
    }
}
