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

public class HistoryManager extends Command {
	
	
	private static Stack<Instruction> recordCommand = new Stack<Instruction>();
	
	
	
	public AddCommand undoLastCommand (){
		
		Instruction redo = recordCommand.pop();
		try {
			return new AddCommand(redo.getTaskName(), redo.getTaskDate(), redo.getTaskStart(), redo.getTaskEnd(), redo.getTag());
		} catch (IllegalValueException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public void stackCommand (DeleteCommand latestCommand){
		/*int targetIndex;
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();
		ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);*/
		//this.recordCommand.push(latestCommand);
		
	}
	public void stackDelCommand (int targInd){
		
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();
		ReadOnlyTask toPush = lastShownList.get(targInd - 1);
		String pushName = toPush.getName().toString();
		String pushDate = toPush.getPhone().toString();
		String pushStart = toPush.getEmail().toString();
		String pushEnd = toPush.getAddress().toString();
		Set<String> pushTag = new HashSet<>();
		for (Tag tagName : toPush.getTags().toSet()) {
		      pushTag.add(tagName.toString());
		 }				
		
		this.recordCommand.push(new Instruction("A", pushName, pushDate, pushStart, pushEnd, pushTag));
		
	}

	@Override
	public CommandResult execute() {
		// TODO Auto-generated method stub
		return null;
	}
}
