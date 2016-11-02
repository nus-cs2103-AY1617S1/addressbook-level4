package seedu.jimi.logic.commands;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.jimi.commons.core.Messages;
import seedu.jimi.commons.core.UnmodifiableObservableList;
import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0140133B
/**
 * Deletes a task or a range of tasks identified using it's last displayed index from Jimi.
 */
public class DeleteCommand extends Command implements TaskBookEditor {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the specified task/event from Jimi.\n"
            + "You can specify the task/event by entering its index number given in the last listing. \n"
            + "Parameters: INDEX (must be t<positive integer> or e<positive integer>)\n"
            + "Example: " + COMMAND_WORD + " t1\n"
            + "\n"
            + "You can also delete a range of tasks. \n"
            + "Example: " + COMMAND_WORD + " t1 to t3";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = 
            "Jimi has deleted the following: \n"
            + "%1$s";

    private String startIdx;
    private String endIdx;
    
    public DeleteCommand() {}
    
    
    public DeleteCommand(String startIdx, String endIdx) throws IllegalValueException {
        this.startIdx = startIdx.trim();
        // If end index is not specified, default it to startIdx.
        this.endIdx = endIdx == null ? startIdx : endIdx.trim(); 
    }
    
    @Override
    public CommandResult execute() {
        if (!isIndicesReferringToSameLists()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        /* At this point, both indices refer to the same list as asserted by above condition.
         * So {@code optionalList} refers to the list specified by both indices.
         */
        Optional<UnmodifiableObservableList<ReadOnlyTask>> optionalList = determineListFromIndexPrefix(startIdx);
        
        // actual integer index is everything after the first character prefix.
        int actualStartIdx = Integer.parseInt(startIdx.substring(1));
        int actualEndIdx = Integer.parseInt(endIdx.substring(1));
        if (!isValidIndices(optionalList, actualStartIdx, actualEndIdx)) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = optionalList.get();
        // endIdx can exceed list size, but apply a ceiling of lastShownList.size().
        actualEndIdx = Math.min(actualEndIdx, lastShownList.size());
        
        List<ReadOnlyTask> toDelete = lastShownList.subList(actualStartIdx - 1, actualEndIdx);
        String deletedFeedback = getListOfTasksAsText(toDelete);
        deleteListOfTasks(toDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, deletedFeedback));
    }

    @Override
    public boolean isValidCommandWord(String commandWord) {
        for (int i = 1; i <= COMMAND_WORD.length(); i++) {
            if (commandWord.toLowerCase().equals(COMMAND_WORD.substring(0, i))) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getMessageUsage() {
        return MESSAGE_USAGE;
    }
    
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
    
    /*
     * ===============================================================
     *                          Helper Methods
     * ===============================================================
     */
    
    /** Returns true if indices are valid i.e. not end before start, start more than list size. */
    private boolean isValidIndices(Optional<UnmodifiableObservableList<ReadOnlyTask>> optionalList, int start,
            int end) {
        return !(!optionalList.isPresent() || optionalList.get().size() < start || end < start);
    }
    
    /** Returns true if prefixes of both indices are the same. */
    private boolean isIndicesReferringToSameLists() {
        return startIdx.charAt(0) == endIdx.charAt(0);
    }
    
    /** Converts {@code list} to a string with each task on an indexed newline. */
    private String getListOfTasksAsText(List<ReadOnlyTask> list) {
        return IntStream.range(0, list.size())
                .mapToObj(i -> (i + 1) + ". " + list.get(i).toString())
                .collect(Collectors.joining("\n"));
    }

    /** Deletes everything in {@code toDelete} from {@code model}. */
    private void deleteListOfTasks(List<ReadOnlyTask> toDelete) {
        toDelete.stream().forEach(t -> {
            try {
                model.deleteTask(t);
            } catch (TaskNotFoundException e) {
                assert false : "The target task cannot be missing";
            }
        });
    }
    
}
