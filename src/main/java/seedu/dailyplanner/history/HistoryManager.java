package seedu.dailyplanner.history;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import seedu.dailyplanner.model.*;
import seedu.dailyplanner.model.tag.*;
import seedu.dailyplanner.model.tag.UniqueTagList;
import seedu.dailyplanner.model.task.*;
import seedu.dailyplanner.commons.core.UnmodifiableObservableList;
import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.logic.commands.*;
import seedu.dailyplanner.logic.parser.*;

public class HistoryManager {

	private Stack<Instruction> recordCommand = new Stack<Instruction>();

	public Instruction getLastInstruction() {

		return recordCommand.pop();
		
	}



	public void stackAddInstruction(ReadOnlyTask toPush) {

		String pushName = toPush.getName();
		Date pushStart = toPush.getStart();
		Date pushEnd = toPush.getEnd();
		boolean isComplete = toPush.isComplete();
		boolean isPinned = toPush.isPinned();
		UniqueTagList pushTag = toPush.getTags();
		
		recordCommand.push(new Instruction("A", pushName, pushStart, pushEnd, isComplete, isPinned, pushTag));
	}
	
	public void stackDeleteInstruction(ReadOnlyTask toPush) {

		String pushName = toPush.getName();
		Date pushStart = toPush.getStart();
		Date pushEnd = toPush.getEnd();
		boolean isComplete = toPush.isComplete();
		boolean isPinned = toPush.isPinned();
		UniqueTagList pushTag = toPush.getTags();
		
		recordCommand.push(new Instruction("A", pushName, pushStart, pushEnd, isComplete, isPinned, pushTag));
	}
	
	public void stackEditInstruction(ReadOnlyTask originalTask, ReadOnlyTask editedTask) {

		String pushAddName = originalTask.getName();
		Date pushAddStart = originalTask.getStart();
		Date pushAddEnd = originalTask.getEnd();
		boolean pushAddComplete = originalTask.isComplete();
		boolean pushAddPinned = originalTask.isPinned();
		UniqueTagList pushAddTag = originalTask.getTags();
		
		recordCommand.push(new Instruction("EA", pushAddName, pushAddStart, pushAddEnd, pushAddComplete, pushAddPinned, pushAddTag));
        
		String pushDeleteName = originalTask.getName();
		Date pushDeleteStart = originalTask.getStart();
		Date pushDeleteEnd = originalTask.getEnd();
		boolean pushDeleteComplete = originalTask.isComplete();
		boolean pushDeletePinned = originalTask.isPinned();
		UniqueTagList pushDeleteTag = originalTask.getTags();
		
		recordCommand.push(new Instruction("ED", pushDeleteName, pushDeleteStart, pushDeleteEnd, pushDeleteComplete, pushDeletePinned, pushDeleteTag));
    }
	
	

}
