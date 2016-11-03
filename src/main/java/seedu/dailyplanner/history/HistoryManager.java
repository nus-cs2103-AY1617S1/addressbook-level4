package seedu.dailyplanner.history;

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

		String pushName = toPush.getName().toString();
		String pushDate = toPush.getPhone().toString();
		String pushEndDate = toPush.getPhone().getEndDate();
		String pushStart = toPush.getEmail().toString();
		String pushEnd = toPush.getAddress().toString();
		UniqueTagList pushTag = toPush.getTags();
		String isComplete = toPush.getCompletion();

		recordCommand.push(new Instruction("A", pushName, pushDate, pushEndDate, pushStart, pushEnd, pushTag, isComplete));

	}
	
	public void stackDeleteInstruction(ReadOnlyTask toPush) {

		String pushName = toPush.getName().toString();
		String pushDate = toPush.getPhone().toString();
		String pushEndDate = toPush.getPhone().getEndDate();
		String pushStart = toPush.getEmail().toString();
		String pushEnd = toPush.getAddress().toString();
		UniqueTagList pushTag = toPush.getTags();
		String isComplete = toPush.getCompletion();

		recordCommand.push(new Instruction("D", pushName, pushDate, pushEndDate, pushStart, pushEnd, pushTag, isComplete));

	}
	
	public void stackEditInstruction(ReadOnlyTask taskToEdit, ReadOnlyTask toAdd) {

        String taskToEditName = taskToEdit.getName().toString();
        String taskToEditDate = taskToEdit.getPhone().toString();
        String taskToEditEndDate = taskToEdit.getPhone().getEndDate();
        String taskToEditStart = taskToEdit.getEmail().toString();
        String taskToEditEnd = taskToEdit.getAddress().toString();
        UniqueTagList taskToEditTag = taskToEdit.getTags();
        String taskToEditIsComplete = taskToEdit.getCompletion();

        recordCommand.push(new Instruction("EA", taskToEditName, taskToEditDate, taskToEditEndDate, taskToEditStart, taskToEditEnd, taskToEditTag,taskToEditIsComplete));
        
        String toAddName = toAdd.getName().toString();
        String toAddDate = toAdd.getPhone().toString();
        String toAddEndDate = taskToEdit.getEmail().toString();
        String toAddStart = toAdd.getEmail().toString();
        String toAddEnd = toAdd.getAddress().toString();
        UniqueTagList toAddTag = toAdd.getTags();
        String toAddIsComplete = toAdd.getCompletion();

        recordCommand.push(new Instruction("ED", toAddName, toAddDate, toAddEndDate, toAddStart, toAddEnd, toAddTag, toAddIsComplete));

    }
	
	

}
