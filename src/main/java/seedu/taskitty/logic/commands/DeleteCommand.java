package seedu.taskitty.logic.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.util.Pair;
import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.commons.core.UnmodifiableObservableList;
import seedu.taskitty.commons.util.AppUtil;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.model.task.Task;

//@@author A0139052L
/**
 * Deletes a task identified using it's last displayed index from the task manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD + " [index] [more indexes]...";
    public static final String MESSAGE_USAGE = "This command deletes tasks from TasKitty, Meow!"
            + "\n[index] is the index eg. t1, d1, e1-3.";

    public static final String MESSAGE_DELETE_TASK_SUCCESS_HEADER = " %1$s Tasks Deleted: ";
    
    private boolean hasInvalidIndex;
    
    private boolean hasDuplicateIndexesProvided;
    
    private final List<Pair<Integer, Integer>> listOfIndexes;
    
    private final String commandText;
    
    private int targetIndex;
    private String currentTaskIndex;
    private Optional<String> errorMessage;
    
    private UnmodifiableObservableList<ReadOnlyTask> lastShownList;
    private ArrayList<ReadOnlyTask> listOfTaskToDelete;
    
    private StringBuilder invalidIndexMessageBuilder;
    private StringBuilder resultMessageBuilder;
    private StringBuilder duplicateIndexesProvidedMessageBuilder;
    
    public DeleteCommand(List<Pair<Integer, Integer>> listOfIndexes, String commandText) {
        assert listOfIndexes != null;
        this.listOfIndexes = listOfIndexes;
        this.hasInvalidIndex = false;
        this.hasDuplicateIndexesProvided = false;
        this.commandText = commandText;
    }

    @Override
    public CommandResult execute() {                       
        
        initialiseMessageBuildersAndList();
        evaluatePresenceOfErrors();
        
        generateErrorMessage();
        if (errorMessage.isPresent()) { // there are errors
            return new CommandResult(errorMessage.get());
        }                  
        executeDeleteTasks();
        
        return new CommandResult(generateSuccessMessage());
    }

    /**
     * This method evaluates each entered index for 3 types of errors: invalid index, duplicate mark as done,
     * and duplicate indexes entered, and then sets the relevant boolean variables as true accordingly.
     */
    private void evaluatePresenceOfErrors() {
        for (Pair<Integer, Integer> indexPair: listOfIndexes) {
            setRelevantIndexesAndList(indexPair);
            if (isInvalidIndex()) {
                continue;                                
            }
            evaluateHasDuplicateIndexes();     
        }
    }
    
    /**
     * Initialises the necessary StringBuilders for recording the message to be returned to the user 
     * and the List to store all the indexes of tasks to delete from the task manager
     */
    private void initialiseMessageBuildersAndList() {
        listOfTaskToDelete = new ArrayList<ReadOnlyTask>();
        invalidIndexMessageBuilder = new StringBuilder(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX + ": ");
        duplicateIndexesProvidedMessageBuilder = new StringBuilder(Messages.MESSAGE_DUPLICATE_INDEXES_PROVIDED + ": ");
        resultMessageBuilder = new StringBuilder(String.format(MESSAGE_DELETE_TASK_SUCCESS_HEADER, listOfIndexes.size()));
    }
    
    /**
     * This method takes in the relevant index that is currently being evaluated and extracts the actual index of the task in the list in
     * each iteration of evaluation. It also targets the correct list out of the 3 lists.
     */
    private void setRelevantIndexesAndList(Pair<Integer, Integer> indexPair) {
        int categoryIndex = indexPair.getKey();
        targetIndex = indexPair.getValue();
        assert categoryIndex >= 0 && categoryIndex < 3;
        
        currentTaskIndex = Task.CATEGORIES[categoryIndex] + targetIndex + " ";
        
        lastShownList = AppUtil.getCorrectListBasedOnCategoryIndex(model, categoryIndex);
    }
    
    /**
     * This method calls the model to mark the specified tasks as done and stores the command for usage during undo/redo.
     * @throws TaskNotFoundException
     */
    private void executeDeleteTasks() {
        model.deleteTasks(listOfTaskToDelete);
        model.storeCommandInfo(COMMAND_WORD, commandText, listOfTaskToDelete);
    }
    
    /**
     * This method generates a string representing the collated lists of tasks that were successfully marked as done
     * built from the success message builder.
     */
    private String generateSuccessMessage() {
        resultMessageBuilder.delete(resultMessageBuilder.length() - 2, resultMessageBuilder.length());// remove the extra separator at the end
        return resultMessageBuilder.toString();     
    }
    
    /** 
     * Returns an error message if an error was detected, else an empty Optional is returned
     */
    private void generateErrorMessage() {
        if (hasInvalidIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            errorMessage = Optional.of(invalidIndexMessageBuilder.toString().trim());
        } else if (hasDuplicateIndexesProvided) {
            indicateAttemptToExecuteIncorrectCommand();
            errorMessage = Optional.of(duplicateIndexesProvidedMessageBuilder.toString().trim());
        }  else {     
            errorMessage = Optional.empty(); // no errors
        }
    }
    
    /**
     * This method evaluates to true if an invalid index is detected, and false otherwise.
     * If true, it also appends the problematic task index to the message builder.
     */
    private boolean isInvalidIndex() {
        if (lastShownList.size() < targetIndex) {
            invalidIndexMessageBuilder.append(currentTaskIndex);
            return hasInvalidIndex = true;                               
        }        
        return false;
    }
    
    /**
     *  Checks if the current task is a duplicate task to delete, 
     *  and appends the index to the error message builder if so
     *  else it adds the task to the listOfTaskToDelete and appends the task name to the success message builder
     */
    private void evaluateHasDuplicateIndexes() {
        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);
        if (!listOfTaskToDelete.contains(taskToDelete)) {
            listOfTaskToDelete.add(taskToDelete);
            resultMessageBuilder.append(taskToDelete.getName() + ", ");
        } else {
            duplicateIndexesProvidedMessageBuilder.append(currentTaskIndex);
            hasDuplicateIndexesProvided = true;  
        }
    }
}
