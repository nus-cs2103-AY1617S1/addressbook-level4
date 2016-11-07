package seedu.task.logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import seedu.task.commons.core.Config;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.FileUtil;
import seedu.task.logic.RollBackCommand;
import seedu.task.model.Model;
import seedu.task.model.ModelManager;
import seedu.task.model.tag.Tag;

// @@author A0147335E
/**
 * Undo previous commands that was input by the user.
 * 
 * 
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo a command which was previously input by user. ";
    public static final String MESSAGE_SUCCESS = "Undo: ";
    public static final String MESSAGE_FAIL = "Cannot undo anymore!";



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
        String displayResult = EMPTY_STRING;
        displayResult = executeUndoCommand(displayResult);
        return new CommandResult(displayResult);
    }

    private String executeUndoCommand(String displayResult) {
        String result = displayResult;
        for (int i = 0; i < numOfTimes; i++) {
            if (!isUndoListEmpty()) {
                result += DELIMITER + MESSAGE_SUCCESS + getPreviousCommandText() + NEW_LINE;
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

                case EditCommand.COMMAND_WORD_ALT:      
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

                case FindCommand.COMMAND_WORD:
                    result = prepareUndoFindCommand(result);
                    break;

                case SortCommand.COMMAND_WORD:
                    prepareUndoSortCommand(result);
                    break;

                default:
                    break;
                }

                if (!isPreviousCommandListEmpty()) {
                    removePreviousCommandText();
                }

            } else {
                if (!isMultiUndo) {
                    result = MESSAGE_FAIL;
                }
            }
        }
        return result;
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
        runCommand(command);
    }

    private String prepareUndoFindCommand(String result) {
        return result;


    }

    private void prepareUndoSortCommand(String result) {
        int undoIndex = lastIndexOfUndoList();
        String currentSort = EMPTY_STRING;
        String currentSortPreference = model.getCurrentSortPreference();
        Command command = null;
        boolean isSort = false;
        
        for(int i = undoIndex - 1; i >= 0; i--){
            if(getUndoList().get(i).getCommandWord().equals(SortCommand.COMMAND_WORD)){
                currentSort = getUndoList().get(i).getCurrentSort();
                isSort = true;
                break;
            }
         }
        if(isSort) {
            command = new SortCommand(currentSort.replace(DELIMITER, EMPTY_STRING).toLowerCase());
        }
        else {
            command = new SortCommand(currentSortPreference.replace(DELIMITER, EMPTY_STRING).toLowerCase());
        }
        
        runCommand(command);
        
    }
    private void prepareUndoDone(String[] commandParts) {
        int undoIndex = lastIndexOfUndoList();
        int currentIndex = getUndoList().get(undoIndex).getCurrentIndex() + 1;
        int index = Integer.parseInt(commandParts[COMMAND_INDEX]);

        Command command = new UndoneCommand(index, currentIndex);
        runCommand(command);
    }

    private void prepareUndoUndone(String[] commandParts) {
        int undoIndex = lastIndexOfUndoList();
        int currentIndex = getUndoList().get(undoIndex).getCurrentIndex() + 1;
        int index = Integer.parseInt(commandParts[COMMAND_INDEX]);
        
        Command command = new DoneCommand(index, currentIndex);
        runCommand(command);
    }

    private void prepareUndoFavorite(String[] commandParts) {
        int undoIndex = lastIndexOfUndoList();
        int currentIndex = getUndoList().get(undoIndex).getCurrentIndex() + 1;
        int index = Integer.parseInt(commandParts[COMMAND_INDEX]);
        
        Command command = new UnfavoriteCommand(index, currentIndex);
        runCommand(command);
    }

    private void prepareUndoUnfavorite(String[] commandParts) {
        int undoIndex = lastIndexOfUndoList();
        int currentIndex = getUndoList().get(undoIndex).getCurrentIndex() + 1;
        int index = Integer.parseInt(commandParts[COMMAND_INDEX]);
        
        Command command = new FavoriteCommand(index, currentIndex);
        runCommand(command);
    }

    private void prepareUndoClear() {
        int undoIndex = lastIndexOfUndoList();
        while (isLastIndexClearCommand(undoIndex)) {
            HashSet<String> getTags = getTags(undoIndex);
            try {
                AddCommand command = addCommand(undoIndex, getTags);
                setData(command);
                command.execute(FIRST_INDEX_OF_LIST);
            } catch (IllegalValueException e) {
            }
            removePreviousCommand(undoIndex);
            undoIndex--;
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
        String toEditItemParsed = "";
        String toEdit = EMPTY_STRING;
        HashSet<String> tagStringSet = null;
        int undoIndex = lastIndexOfUndoList();
        // @@author A0147944U
        switch (toEditItem) {

        case "name":
        case "n":
            toEdit = getUndoList().get(undoIndex).getOldTask().getName().toString();
            toEditItemParsed = "name";
            break;
        case "starttime":
        case "start":
        case "s":
            toEdit = getUndoList().get(undoIndex).getOldTask().getStartTime().toString();
            toEditItemParsed = "starttime";
            break;
        case "endtime":
        case "end":
        case "e":
            toEdit = getUndoList().get(undoIndex).getOldTask().getEndTime().toString();
            toEditItemParsed = "endtime";
            break;
        case "deadline":
        case "due":
        case "d":
            toEdit = getUndoList().get(undoIndex).getOldTask().getDeadline().toString();
            toEditItemParsed = "deadline";
            break;
        case "tag":
        case "t":
            HashSet<Tag> tagSet = new HashSet<>(getUndoList().get(undoIndex).getNewTask().getTags().toSet());
            tagStringSet = new HashSet<>(tagSet.size());
            toEditItemParsed = "tag";
            break;

        default:
            break;
        }
        // @@author A0147335E
        try {
            int currentIndex = getUndoList().get(undoIndex).getCurrentIndex() + 1;
            Command command = new EditCommand(index, currentIndex, toEditItemParsed, toEdit, tagStringSet);
            setData(command);
            executeCommand(command);
        } catch (IllegalValueException e) {
        }
        removePreviousCommand(undoIndex);

    }

    private void prepareUndoAdd() {
        int undoIndex = lastIndexOfUndoList();
        Command command = new DeleteCommand(getUndoList().get(undoIndex).getCurrentIndex() + 1);
        runCommand(command);
    }

    private void prepareUndoDelete(String[] previousCommandDetails) {

        int index = Integer.parseInt(previousCommandDetails[1]) - 1;
        int size = lastIndexOfUndoList();

        HashSet<String> tagStringSet = getTags(size);

        try {
            AddCommand command = addCommand(size, tagStringSet);
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

    private AddCommand addCommand(int index, HashSet<String> tagStringSet) throws IllegalValueException {
        AddCommand command = new AddCommand(EMPTY_STRING + getUndoList().get(index).getNewTask().getName(),
                EMPTY_STRING + getUndoList().get(index).getNewTask().getStartTime(),
                EMPTY_STRING + getUndoList().get(index).getNewTask().getEndTime(),
                EMPTY_STRING + getUndoList().get(index).getNewTask().getDeadline(), tagStringSet, getUndoList().get(index).getNewTask().getStatus());
        return command;
    }

    private boolean isPreviousCommandListEmpty() {
        return getPreviousCommandList().isEmpty();
    }

    private void runCommand(Command command) {
        setData(command);
        executeCommand(command);
        removePreviousCommand();
    }

    public CommandResult execute(int index) {
        return null;
    }

}