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
		String pushStart = toPush.getEmail().toString();
		String pushEnd = toPush.getAddress().toString();
		Set<String> pushTag = new HashSet<>();
		for (Tag tagName : toPush.getTags().toSet()) {
			pushTag.add(tagName.toString());
		}

		recordCommand.push(new Instruction("A", pushName, pushDate, pushStart, pushEnd, pushTag));

	}
	
	public void stackDeleteInstruction(ReadOnlyTask toPush) {

		String pushName = toPush.getName().toString();
		String pushDate = toPush.getPhone().toString();
		String pushStart = toPush.getEmail().toString();
		String pushEnd = toPush.getAddress().toString();
		Set<String> pushTag = new HashSet<>();
		for (Tag tagName : toPush.getTags().toSet()) {
			pushTag.add(tagName.toString());
		}

		recordCommand.push(new Instruction("D", pushName, pushDate, pushStart, pushEnd, pushTag));

	}

}
