package seedu.dailyplanner.history;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import seedu.dailyplanner.model.*;
import seedu.dailyplanner.model.category.*;
import seedu.dailyplanner.model.task.*;
import seedu.dailyplanner.commons.core.UnmodifiableObservableList;
import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.logic.commands.*;
import seedu.dailyplanner.logic.parser.*;
//@@author A0139102U
public class HistoryManager {

	private Stack<Instruction> recordCommand = new Stack<Instruction>();

	public Instruction getLastInstruction() {
		return recordCommand.pop();
	}

	public void stackAddInstruction(ReadOnlyTask toPush) {
		recordCommand.push(new Instruction("A", toPush));
	}
	
	public void stackDeleteInstruction(ReadOnlyTask toPush) {
		recordCommand.push(new Instruction("D", toPush));
	}
	
	public void stackEditInstruction(ReadOnlyTask originalTask, ReadOnlyTask editedTask) {
		recordCommand.push(new Instruction("EA", originalTask));
		recordCommand.push(new Instruction("ED", editedTask));
    }

	public void stackUnpinInstruction(ReadOnlyTask taskToUnpin) {
		recordCommand.push(new Instruction("UP", taskToUnpin));
	}

	public void stackPinInstruction(ReadOnlyTask taskToUnpin) {
		recordCommand.push(new Instruction("P", taskToUnpin));
	}
	
	public void stackUncompleteInstruction(ReadOnlyTask taskToUncomplete) {
        recordCommand.push(new Instruction("UC", taskToUncomplete));
    }

    public void stackCompleteInstruction(ReadOnlyTask taskToComplete) {
        recordCommand.push(new Instruction("C", taskToComplete)); 
    }
}
