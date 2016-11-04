package seedu.task.logic.commands;

import java.util.HashSet;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.ReadOnlyTask;

/**
 * Undo previous commands that was input by the user.
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
    
    public final int numOfTimes;
    
    public final boolean isMultiUndo;
    
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public UndoCommand() {
        numOfTimes = 1;
        isMultiUndo = false;
    }
    
    public UndoCommand(int numOfTimes) {
        this.numOfTimes = numOfTimes;
        isMultiUndo = true;
        
    }

    @Override
    public CommandResult execute(boolean isUndo) {
        if (history.getUndoList().size() == 0) {
            return new CommandResult(MESSAGE_FAIL);
        }
        String outputUndoList = "";
        for (int i = 0; i < numOfTimes; i++) {
        
        if (history.getUndoList().size() != 0) {
            outputUndoList +=  " " + MESSAGE_SUCCESS + history.getPreviousCommandList().get(history.getPreviousCommandList().size()-1) + "\n" ;
            String[] getIndex = history.getPreviousCommandList().get(history.getPreviousCommandList().size()-1).split(" ",4);
            String previousCommand = getIndex[0];
            String[] previousCommandDetails = getIndex;

            switch (previousCommand) {

            case AddCommand.COMMAND_WORD:
                prepareUndoAdd();
                break;

            case DeleteCommand.COMMAND_WORD:
                prepareUndoDelete(previousCommandDetails);
                break;

            case EditCommand.COMMAND_WORD:
                prepareUndoEdit(previousCommandDetails);
                break;

            case ClearCommand.COMMAND_WORD:
                prepareUndoClear(previousCommandDetails);
                break;

            case DoneCommand.COMMAND_WORD:
                prepareUndoDone(previousCommandDetails);
                break;
                
            case UndoneCommand.COMMAND_WORD:
                prepareUndoUndone(previousCommandDetails);
                break;
                
            case FavoriteCommand.COMMAND_WORD:
                prepareUndoFavorite(previousCommandDetails);
                break;
                
            case UnfavoriteCommand.COMMAND_WORD:
                prepareUndoUnfavorite(previousCommandDetails);
                break;

            case RefreshCommand.COMMAND_WORD:
                prepareUndoRefreshCommand();
                break;
            default:
                break;
            }

            checkCommandListSize();
        }
        else {
            if (!isMultiUndo) {
                outputUndoList = MESSAGE_FAIL;
            }
        }
        }
        return new CommandResult(outputUndoList);
    }

    private void prepareUndoRefreshCommand() {
        

        Command command = new RefreshCommand();     
        command.setData(model);
        command.execute(true);

        history.getUndoList().remove(history.getUndoList().size() - 1);
    }
    
    private void prepareUndoDone(String[] previousCommandDetails) {
        int index = Integer.parseInt(previousCommandDetails[1]);

        Command command = new UndoneCommand(index);     
        command.setData(model);
        command.execute(true);

        history.getUndoList().remove(history.getUndoList().size() - 1);
    }

    private void prepareUndoUndone(String[] previousCommandDetails) {
        int index = Integer.parseInt(previousCommandDetails[1]);

        Command command = new DoneCommand(index);     
        command.setData(model);
        command.execute(true);

        history.getUndoList().remove(history.getUndoList().size() - 1);
    }

    private void prepareUndoFavorite(String[] previousCommandDetails) {
        int index = Integer.parseInt(previousCommandDetails[1]);

        Command command = new UnfavoriteCommand(index);     
        command.setData(model);
        command.execute(true);

        history.getUndoList().remove(history.getUndoList().size() - 1);
    }

    private void prepareUndoUnfavorite(String[] previousCommandDetails) {
        int index = Integer.parseInt(previousCommandDetails[1]);

        Command command = new FavoriteCommand(index);     
        command.setData(model);
        command.execute(true);

        history.getUndoList().remove(history.getUndoList().size() - 1);
    }
    
    private void prepareUndoClear(String[] previousCommandDetails) {

        int size = history.getUndoList().size() - 1; 

        while (history.getUndoList().get(size).getCommandWord().equals("clear")) {
            HashSet<Tag> tagSet = new HashSet<>(history.getUndoList().get(size).getNewTask().getTags().toSet());
            HashSet<String> tagStringSet = new HashSet<>(tagSet.size());
            for (Tag tags: tagSet) {
                tagStringSet.add(tags.tagName);
            }

            try {
                Command command = new AddCommand(
                        "" + history.getUndoList().get(size).getNewTask().getName(),
                        "" + history.getUndoList().get(size).getNewTask().getStartTime(),
                        "" + history.getUndoList().get(size).getNewTask().getEndTime(),
                        "" + history.getUndoList().get(size).getNewTask().getDeadline(),
                        tagStringSet);
                command.setData(model);
                command.execute(0);

            } catch (IllegalValueException e) {

            }
            history.getUndoList().remove(size);
            size--;
            if (size == -1) {
                break;
            }
            if (!(history.getUndoList().get(size).getCommandWord().equals("clear"))) {
                break;
            }
        }

    }

    private void checkCommandListSize() {
        if (history.getPreviousCommandList().size() != 0) {
            history.getPreviousCommandList().remove(history.getPreviousCommandList().size() - 1);
        }
    }

    private void prepareUndoEdit(String[] previousCommandDetails) {
        int index = Integer.parseInt(previousCommandDetails[1]);
        String toEditItem = previousCommandDetails[2].replace(",", "");
        String toEdit = "";
        
        
        HashSet<String> tagStringSet = null;
        
        int size = history.getUndoList().size() - 1; 
        
        switch (toEditItem) {
        
        case UNDO_EDIT_NAME:
            toEdit = history.getUndoList().get(size).getOldTask().getName().toString();
            break;
        case UNDO_EDIT_START_TIME:
            toEdit = history.getUndoList().get(size).getOldTask().getStartTime().toString();
            break;
        case UNDO_EDIT_END_TIME:
            toEdit = history.getUndoList().get(size).getOldTask().getEndTime().toString();
            break;
        case UNDO_EDIT_DEADLINE:
            toEdit = history.getUndoList().get(size).getOldTask().getDeadline().toString();
            break;
        case UNDO_EDIT_TAG:
            HashSet<Tag> tagSet = new HashSet<>(history.getUndoList().get(size).getNewTask().getTags().toSet());
            tagStringSet = new HashSet<>(tagSet.size());
            break;
        }
        

        

       try {
            Command command = new EditCommand(index, toEditItem, toEdit, tagStringSet);
            command.setData(model);
            command.execute(true);
        } catch (IllegalValueException e) {
        }
        history.getUndoList().remove(size);
        
    }

    private void prepareUndoAdd() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        Command command = new DeleteCommand(lastShownList.size());     
        command.setData(model);
        command.execute(true);
        
        //history.getRedoList().add(history.getUndoList().get(history.getUndoList().size() - 1));
        history.getUndoList().remove(history.getUndoList().size() - 1);

    }

    private void prepareUndoDelete(String[] previousCommandDetails) {

        int index = Integer.parseInt(previousCommandDetails[1]) - 1;
        int size = history.getUndoList().size() - 1; 

        HashSet<Tag> tagSet = new HashSet<>(history.getUndoList().get(size).getNewTask().getTags().toSet());
        HashSet<String> tagStringSet = new HashSet<>(tagSet.size());
        for (Tag tags: tagSet) {
            tagStringSet.add(tags.tagName);
        }

        try {
            Command command = new AddCommand(
                    "" + history.getUndoList().get(size).getNewTask().getName(),
                    "" + history.getUndoList().get(size).getNewTask().getStartTime(),
                    "" + history.getUndoList().get(size).getNewTask().getEndTime(),
                    "" + history.getUndoList().get(size).getNewTask().getDeadline(),
                    tagStringSet);
            command.setData(model);
            command.execute(index);

        } catch (IllegalValueException e) {
        }
        history.getUndoList().remove(size);
    }

    @Override
    public CommandResult execute(int index) {
        return null;
    }

}