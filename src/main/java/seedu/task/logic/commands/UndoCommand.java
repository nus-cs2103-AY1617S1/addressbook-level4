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

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public UndoCommand() {

    }

    @Override
    public CommandResult execute(boolean isUndo) {
        String outputUndoList = "";
        if (history.getUndoList().size() != 0) {
            outputUndoList = MESSAGE_SUCCESS + history.getPreviousCommandList().get(history.getPreviousCommandList().size()-1);
            String[] getIndex = history.getPreviousCommandList().get(history.getPreviousCommandList().size()-1).split(" ");
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

            case UndoneCommand.COMMAND_WORD:
                prepareUndoUndone(previousCommandDetails);
                break;

            case DoneCommand.COMMAND_WORD:
                prepareUndoDone(previousCommandDetails);
                break;

            default:

            }

            checkCommandListSize();
        }
        else {
            outputUndoList = MESSAGE_FAIL;
        }

        return new CommandResult(outputUndoList);
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

    private void prepareUndoClear(String[] previousCommandDetails) {

        int size = history.getUndoList().size() - 1; 

        while(history.getUndoList().get(size).getCommandWord().equals("clear")){
            HashSet<Tag> tagSet = new HashSet<>(history.getUndoList().get(size).getNewTask().getTags().toSet());
            HashSet<String> tagStringSet = new HashSet<>(tagSet.size());
            for(Tag tags: tagSet){
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
            if(size == -1){
                break;
            }
            if(!(history.getUndoList().get(size).getCommandWord().equals("clear"))){
                break;
            }
        }

    }

    private void checkCommandListSize() {
        if(history.getPreviousCommandList().size() != 0){
            history.getPreviousCommandList().remove(history.getPreviousCommandList().size()-1);
        }
    }

    private void prepareUndoEdit(String[] previousCommandDetails) {
        int index = Integer.parseInt(previousCommandDetails[1]);
        int size = history.getUndoList().size() - 1; 


        String name = history.getUndoList().get(size).getOldTask().getName().toString();
        String startTime = history.getUndoList().get(size).getOldTask().getStartTime().toString();
        String endTime = history.getUndoList().get(size).getOldTask().getEndTime().toString();
        String deadline = history.getUndoList().get(size).getOldTask().getDeadline().toString();

        HashSet<Tag> tagSet = new HashSet<>(history.getUndoList().get(size).getNewTask().getTags().toSet());
        HashSet<String> tagStringSet = new HashSet<>(tagSet.size());

        try {
            Command command = new EditCommand(index, name, startTime,  endTime,  deadline, tagStringSet);
            command.setData(model);
            command.execute(true);
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        history.getUndoList().remove(size);

    }

    private void prepareUndoAdd() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        Command command = new DeleteCommand(1);     
        command.setData(model);
        command.execute(true);

        history.getUndoList().remove(history.getUndoList().size() - 1);


    }

    private void prepareUndoDelete(String[] previousCommandDetails) {

        int index = Integer.parseInt(previousCommandDetails[1]) - 1;


        int size = history.getUndoList().size() - 1; 

        HashSet<Tag> tagSet = new HashSet<>(history.getUndoList().get(size).getNewTask().getTags().toSet());
        HashSet<String> tagStringSet = new HashSet<>(tagSet.size());
        for(Tag tags: tagSet){
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
        // TODO Auto-generated method stub
        return null;
    }

}