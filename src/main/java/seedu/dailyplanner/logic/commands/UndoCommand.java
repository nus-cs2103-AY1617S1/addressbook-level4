package seedu.dailyplanner.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.history.HistoryManager;
import seedu.dailyplanner.history.Instruction;
import seedu.dailyplanner.model.tag.Tag;
import seedu.dailyplanner.model.tag.UniqueTagList;
import seedu.dailyplanner.model.task.Date;
import seedu.dailyplanner.model.task.EndTime;
import seedu.dailyplanner.model.task.Name;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.StartTime;
import seedu.dailyplanner.model.task.Task;
import seedu.dailyplanner.model.task.UniqueTaskList.DuplicatePersonException;
import seedu.dailyplanner.model.task.UniqueTaskList.PersonNotFoundException;

public class UndoCommand extends Command {

	public static final String COMMAND_WORD = "undo";
	public static final String MESSAGE_SUCCESS = "Undone";
	
	@Override
	public CommandResult execute() {
		Command undoCommand;
		Instruction undoInstruction = model.getHistory().getLastInstruction();
		ReadOnlyTask taskToUndo = null;
		
		try {
			final Set<Tag> tagSet = new HashSet<>();
	        for (String tagName : undoInstruction.getTag()) {
	            tagSet.add(new Tag(tagName));
	        }
	        
			 taskToUndo = new Task(new Name(undoInstruction.getTaskName()), new Date(undoInstruction.getTaskDate()), new StartTime(undoInstruction.getTaskStart()), new EndTime(undoInstruction.getTaskEnd()),
					new UniqueTagList(tagSet));
			
		} catch (IllegalValueException e) {
			e.printStackTrace();
		}
		
		
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

            try {
                final Set<Tag> tagSet = new HashSet<>();
                for (String tagName : undoInstruction.getTag()) {
                    tagSet.add(new Tag(tagName));
                }

                taskToUndo = new Task(new Name(undoInstruction.getTaskName()), new Date(undoInstruction.getTaskDate()),
                        new StartTime(undoInstruction.getTaskStart()), new EndTime(undoInstruction.getTaskEnd()),
                        new UniqueTagList(tagSet));

            } catch (IllegalValueException e) {
                e.printStackTrace();
            }

            try {
                model.addPerson((Task) taskToUndo);
            } catch (DuplicatePersonException e) {
                e.printStackTrace();
            }

        }
		
		return new CommandResult(String.format(MESSAGE_SUCCESS));
		
		
	}

}
