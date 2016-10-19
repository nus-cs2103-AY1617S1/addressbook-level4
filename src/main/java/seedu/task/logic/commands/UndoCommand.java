package seedu.task.logic.commands;


import java.util.Collections;

import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.HistoryList;
import seedu.task.model.task.ReadOnlyTask;


public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo a command which was previously input by user. ";

    public static final String MESSAGE_SUCCESS = "Undo the following command: ";

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public UndoCommand() {

    }

    @Override
    public CommandResult execute(boolean isUndo) {
        // TODO Auto-generated method stub
        String taskToUndo = "";
        String outputUndoList = "";
        if(HistoryList.getUndoList().size() != 0) {
            String[] getIndex = HistoryList.getPreviousCommand().get(HistoryList.getPreviousCommand().size()-1).split(" ");
            String previousCommand = getIndex[0];
            
            outputUndoList = prepareUndoDelete(outputUndoList, getIndex, previousCommand);
            outputUndoList = prepareUndoAdd(outputUndoList, previousCommand);

            checkCommandListSize();
        }
        else {
            outputUndoList = "Cannot undo anymore!";
        }

        return new CommandResult(outputUndoList);
    }

    private void checkCommandListSize() {
        if(HistoryList.getPreviousCommand().size() != 0){
            HistoryList.getPreviousCommand().remove(HistoryList.getPreviousCommand().size()-1);
        }
    }

    private String prepareUndoAdd(String outputUndoList, String previousCommand) {
        if(previousCommand.equals("add")) {

            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
            outputUndoList = "deleting: " + (lastShownList.size());
            Command command = new DeleteCommand(lastShownList.size());     
            command.setData(model);
            command.execute(true);

            HistoryList.getUndoList().remove(HistoryList.getUndoList().size() - 1);



        }
        return outputUndoList;
    }

    private String prepareUndoDelete(String outputUndoList, String[] getIndex, String previousCommand) {
        if(previousCommand.equals("delete")){
            int index = Integer.parseInt(getIndex[1]) - 1;
            final StringBuilder builder = new StringBuilder();
            builder.append(HistoryList.getUndoList().get(HistoryList.getUndoList().size()-1).getNewTask().getName())
            .append(" from ")
            .append(HistoryList.getUndoList().get(HistoryList.getUndoList().size()-1).getNewTask().getStartTime())
            .append(" to ")
            .append(HistoryList.getUndoList().get(HistoryList.getUndoList().size()-1).getNewTask().getEndTime())
            .append(" by ")
            .append(HistoryList.getUndoList().get(HistoryList.getUndoList().size()-1).getNewTask().getDeadline());
            int size = HistoryList.getUndoList().size() - 1; 
            outputUndoList = "added "  + " " + builder.toString();

            try {
                Command command = new AddCommand(
                        "" + HistoryList.getUndoList().get(size).getNewTask().getName(),
                        "" + HistoryList.getUndoList().get(size).getNewTask().getStartTime(),
                        "" + HistoryList.getUndoList().get(size).getNewTask().getEndTime(),
                        "" + HistoryList.getUndoList().get(size).getNewTask().getDeadline(),
                        Collections.emptySet());
                command.setData(model);
                command.execute(index);
            } catch (IllegalValueException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            HistoryList.getUndoList().remove(size);
        }
        return outputUndoList;
    }

    @Override
    public CommandResult execute(int index) {
        // TODO Auto-generated method stub
        return null;
    }

}
