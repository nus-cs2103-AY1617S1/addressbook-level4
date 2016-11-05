package seedu.task.logic.commands;

import java.util.ArrayList;
import java.util.HashSet;

import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.RollBackCommand;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.ReadOnlyTask;

/**
 * Undo previous commands that was input by the user.
 * 
 * @@author A0147335E
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo a command which was previously input by user. ";
    public static final String MESSAGE_SUCCESS = "Undo: ";
    public static final String MESSAGE_FAIL = "Cannot undo anymore!";

    public static final String UNDO_EDIT_NAME = "name";
    public static final String UNDO_EDIT_START_TIME = "start";
    public static final String UNDO_EDIT_END_TIME = "end";
    public static final String UNDO_EDIT_DEADLINE = "deadline";
    public static final String UNDO_EDIT_TAG = "tag";

    public static final String EMPTY_STRING = "";
    public static final String DELIMITER = " ";
    public static final String NEW_LINE = "\n";
    
    public static final int UNDO_ONE_TIME = 1;
    
    public static final int COMMAND_NAME = 0;
    public static final int COMMAND_INDEX = 1;
    public static final int COMMAND_FIELD = 2;
    
    public static final int FIRST_INDEX_OF_LIST= 0;

    public final int numOfTimes;

    public final boolean isMultiUndo;

    /**
     * Constructor for undo one command only
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public UndoCommand() {
        numOfTimes = UNDO_ONE_TIME;
        isMultiUndo = false;
    }

    /**
     * Constructor for undo multiple commands
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public UndoCommand(int numOfTimes) {
        this.numOfTimes = numOfTimes;
        isMultiUndo = true;
    }

    @Override
    public CommandResult execute(boolean isUndo) {
        if (isUndoListEmpty()) {
            return new CommandResult(MESSAGE_FAIL);
        }
        String outputUndoList = EMPTY_STRING;
        outputUndoList = executeUndoCommand(outputUndoList);
        return new CommandResult(outputUndoList);
    }

    private String executeUndoCommand(String outputUndoList) {
        for (int i = 0; i < numOfTimes; i++) {
            if (!isUndoListEmpty()) {
                outputUndoList += DELIMITER + MESSAGE_SUCCESS + getPreviousCommandText() + NEW_LINE;
                String[] commandParts = splitPreviousCommandTextIntoFourParts();
                String previousCommand = getPreviousCommandName(commandParts);
                
                switch (previousCommand) {

                case AddCommand.COMMAND_WORD:
                    prepareUndoAdd();
                    break;

                case DeleteCommand.COMMAND_WORD:
                    prepareUndoDelete(commandParts);
                    break;

                case EditCommand.COMMAND_WORD:
                    prepareUndoEdit(commandParts);
                    break;

                case ClearCommand.COMMAND_WORD:
                    prepareUndoClear();
                    break;

                case DoneCommand.COMMAND_WORD:
                    prepareUndoDone(commandParts);
                    break;

                case UndoneCommand.COMMAND_WORD:
                    prepareUndoUndone(commandParts);
                    break;

                case FavoriteCommand.COMMAND_WORD:
                    prepareUndoFavorite(commandParts);
                    break;

                case UnfavoriteCommand.COMMAND_WORD:
                    prepareUndoUnfavorite(commandParts);
                    break;

                case RefreshCommand.COMMAND_WORD:
                    prepareUndoRefreshCommand();
                    break;

                default:
                    break;
                }
                
                if (!isPreviousCommandListEmpty()) {
                    removePreviousCommandText();
                }
                
            } else {
                if (!isMultiUndo) {
                    outputUndoList = MESSAGE_FAIL;
                }
            }
        }
        return outputUndoList;
    }

    private void removePreviousCommandText() {
        getPreviousCommandList().remove(lastIndexOfPreviousCommandList());
    }

    private String getPreviousCommandName(String[] getIndex) {
        return getIndex[COMMAND_NAME];
    }

    private String[] splitPreviousCommandTextIntoFourParts() {
        return getPreviousCommandText().split(DELIMITER, 4);
    }

    private String getPreviousCommandText() {
        return getPreviousCommandList().get(lastIndexOfPreviousCommandList());
    }

    private ArrayList<String> getPreviousCommandList() {
        return history.getPreviousCommandList();
    }

    private int lastIndexOfPreviousCommandList() {
        return getPreviousCommandList().size() - 1;
    }

    private boolean isUndoListEmpty() {
        return getUndoList().size() == 0;
    }

    private ArrayList<RollBackCommand> getUndoList() {
        return history.getUndoList();
    }

    private void prepareUndoRefreshCommand() {
        Command command = new RefreshCommand();
        setData(command);
        executeCommand(command);
        removePreviousCommand();
    }

    private void prepareUndoDone(String[] commandParts) {
        int index = Integer.parseInt(commandParts[COMMAND_INDEX]);
        Command command = new UndoneCommand(index);
        setData(command);
        executeCommand(command);
        removePreviousCommand();
    }

    private void prepareUndoUndone(String[] commandParts) {
        int index = Integer.parseInt(commandParts[COMMAND_INDEX]);
        Command command = new DoneCommand(index);
        setData(command);
        executeCommand(command);
        removePreviousCommand();
    }

    private void prepareUndoFavorite(String[] commandParts) {
        int index = Integer.parseInt(commandParts[COMMAND_INDEX]);
        Command command = new UnfavoriteCommand(index);
        setData(command);
        executeCommand(command);
        removePreviousCommand();
    }

    private void prepareUndoUnfavorite(String[] commandParts) {
        int index = Integer.parseInt(commandParts[COMMAND_INDEX]);
        Command command = new FavoriteCommand(index);
        setData(command);
        executeCommand(command);
        removePreviousCommand();
    }

    private void prepareUndoClear() {
        int undoIndex = lastIndexOfUndoList();

        while (isLastIndexClearCommand(undoIndex)) {
            HashSet<String> getTags = getTags(undoIndex);

            try {
                Command command = addCommand(undoIndex, getTags);
                setData(command);
                command.execute(FIRST_INDEX_OF_LIST);

            } catch (IllegalValueException e) {

            }
            removePreviousCommand(undoIndex);
            undoIndex = decrement(undoIndex);
            if (undoIndex < 0) {
                break;
            }
            if (!isLastIndexClearCommand(undoIndex)) {
                break;
            }
        }

    }

    private int lastIndexOfUndoList() {
        int undoIndex = getUndoList().size() - 1;
        return undoIndex;
    }

    private boolean isLastIndexClearCommand(int undoIndex) {
        return getUndoList().get(undoIndex).getCommandWord().equals(ClearCommand.COMMAND_WORD);
    }

    private void prepareUndoEdit(String[] commandParts) {
        int index = Integer.parseInt(commandParts[COMMAND_INDEX]);
        String toEditItem = commandParts[COMMAND_FIELD].replace(",", EMPTY_STRING);
        String toEdit = EMPTY_STRING;

        HashSet<String> tagStringSet = null;

        int undoIndex = lastIndexOfUndoList();

        switch (toEditItem) {

        case UNDO_EDIT_NAME:
            toEdit = getUndoList().get(undoIndex).getOldTask().getName().toString();
            break;
        case UNDO_EDIT_START_TIME:
            toEdit = getUndoList().get(undoIndex).getOldTask().getStartTime().toString();
            break;
        case UNDO_EDIT_END_TIME:
            toEdit = getUndoList().get(undoIndex).getOldTask().getEndTime().toString();
            break;
        case UNDO_EDIT_DEADLINE:
            toEdit = getUndoList().get(undoIndex).getOldTask().getDeadline().toString();
            break;
        case UNDO_EDIT_TAG:
            HashSet<Tag> tagSet = new HashSet<>(getUndoList().get(undoIndex).getNewTask().getTags().toSet());
            tagStringSet = new HashSet<>(tagSet.size());
            break;
        }

        try {
            Command command = new EditCommand(index, toEditItem, toEdit, tagStringSet);
            setData(command);
            executeCommand(command);
        } catch (IllegalValueException e) {
        }
        removePreviousCommand(undoIndex);

    }

    private void prepareUndoAdd() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        Command command = new DeleteCommand(lastShownList.size());
        setData(command);
        executeCommand(command);
        removePreviousCommand();

    }

    private void prepareUndoDelete(String[] previousCommandDetails) {

        int index = Integer.parseInt(previousCommandDetails[1]) - 1;
        int size = lastIndexOfUndoList();

        HashSet<String> tagStringSet = getTags(size);

        try {
            Command command = addCommand(size, tagStringSet);
            setData(command);
            command.execute(index);

        } catch (IllegalValueException e) {
        }
        removePreviousCommand(size);
    }

    private void executeCommand(Command command) {
        command.execute(true);
    }

    private void setData(Command command) {
        command.setData(model);
    }

    private void removePreviousCommand() {
        getUndoList().remove(getUndoList().size() - 1);
    }

    

    private int decrement(int index) {
        index--;
        return index;
    }

    private void removePreviousCommand(int index) {
        getUndoList().remove(index);
    }

    private HashSet<String> getTags(int index) {
        HashSet<Tag> tagSet = new HashSet<>(getUndoList().get(index).getNewTask().getTags().toSet());
        HashSet<String> getTags = new HashSet<>(tagSet.size());
        for (Tag tags : tagSet) {
            getTags.add(tags.tagName);
        }
        return getTags;
    }

    private Command addCommand(int index, HashSet<String> tagStringSet) throws IllegalValueException {
        Command command = new AddCommand(EMPTY_STRING + getUndoList().get(index).getNewTask().getName(),
                EMPTY_STRING + getUndoList().get(index).getNewTask().getStartTime(),
                EMPTY_STRING + getUndoList().get(index).getNewTask().getEndTime(),
                EMPTY_STRING + getUndoList().get(index).getNewTask().getDeadline(), tagStringSet);
        return command;
    }

    private boolean isPreviousCommandListEmpty() {
        return getPreviousCommandList().isEmpty();
    }

    @Override
    public CommandResult execute(int index) {
        return null;
    }

}