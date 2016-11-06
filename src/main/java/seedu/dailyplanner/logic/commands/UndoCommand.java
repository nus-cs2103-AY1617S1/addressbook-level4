package seedu.dailyplanner.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.history.Instruction;
import seedu.dailyplanner.model.tag.Tag;
import seedu.dailyplanner.model.tag.UniqueTagList;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.Task;
import seedu.dailyplanner.model.task.UniqueTaskList.DuplicatePersonException;
import seedu.dailyplanner.model.task.UniqueTaskList.PersonNotFoundException;

public class UndoCommand extends Command {

	public static final String COMMAND_WORD = "undo";
	public static final String MESSAGE_SUCCESS = "Undo success!";

	@Override
	public CommandResult execute() {
		Instruction undoInstruction = model.getHistory().getLastInstruction();
		ReadOnlyTask taskToUndo = null;

		taskToUndo = undoInstruction.getTask();

		if (undoInstruction.getReverse().equals("A")) {
			try {
				model.addPerson((Task) taskToUndo);
			} catch (IllegalValueException e) {
				e.printStackTrace();
			}
		}

		if (undoInstruction.getReverse().equals("D")) {
			try {
				model.deletePerson(taskToUndo);
			} catch (PersonNotFoundException e) {
				e.printStackTrace();
			}
		}

		if (undoInstruction.getReverse().equals("ED")) {
			try {
				model.deletePerson(taskToUndo);

			} catch (PersonNotFoundException e) {
				e.printStackTrace();
			}

			// Get next instruction from stack to generate and add the old task
			// back
			// The instruction is guaranteed to be an "EA" instruction
			undoInstruction = model.getHistory().getLastInstruction();
			taskToUndo = null;

			taskToUndo = undoInstruction.getTask();

			try {
				model.addPerson((Task) taskToUndo);
			} catch (DuplicatePersonException e) {
				e.printStackTrace();
			}
		}

		if (undoInstruction.getReverse().equals("UP")) {
			int indexInPinBoard = model.getPinnedTaskList().indexOf(taskToUndo);
			try {
				model.unpinTask(indexInPinBoard);
			} catch (PersonNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if (undoInstruction.getReverse().equals("P")) {
			try {
				model.pinTask(taskToUndo);
			} catch (PersonNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if (undoInstruction.getReverse().equals("UC")) {
            int indexInTaskList = model.getFilteredPersonList().indexOf(taskToUndo);
            model.uncompleteTask(indexInTaskList);
        }
		
		model.updatePinBoard();

		return new CommandResult(String.format(MESSAGE_SUCCESS));

	}

}
