
	package seedu.dailyplanner.logic.commands;

	import java.util.HashSet;
import java.util.Set;

import seedu.dailyplanner.commons.core.Messages;
	import seedu.dailyplanner.commons.core.UnmodifiableObservableList;
import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.model.tag.Tag;
import seedu.dailyplanner.model.tag.UniqueTagList;
import seedu.dailyplanner.model.task.Date;
import seedu.dailyplanner.model.task.EndTime;
import seedu.dailyplanner.model.task.Name;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.StartTime;
import seedu.dailyplanner.model.task.Task;
import seedu.dailyplanner.model.task.UniqueTaskList;
import seedu.dailyplanner.model.task.UniqueTaskList.PersonNotFoundException;

	/**
	 * Deletes a task identified using it's last displayed index from the address book.
	 * Adds the task back with new updated information
	 */
	//@@author A0139102U
	public class EditCommand extends Command {

	    public static final String COMMAND_WORD = "edit";

	    public static final String MESSAGE_USAGE = COMMAND_WORD
	            + ": Edits the task identified by the index number used in the last task listing.\n"
	            + "Parameters: INDEX (must be a positive integer) NAME d/DATE s/STARTTIME e/ENDTIME\n"
	            + "Example: " + COMMAND_WORD + " 2";
	    
	    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the daily planner";
	    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Task: %1$s";
	    
	    public final int targetIndex;
	    private final Task toAdd;

	    public EditCommand(int targetIndex, String taskName, String date, String startTime, String endTime, Set<String> tags) throws IllegalValueException {
	        this.targetIndex = targetIndex;
	        final Set<Tag> tagSet = new HashSet<>();
	        for (String tagName : tags) {
	            tagSet.add(new Tag(tagName));
	        }
	        this.toAdd = new Task(
	                new Name(taskName),
	                new Date(date),
	                new StartTime(startTime),
	                new EndTime(endTime),
	                new UniqueTagList(tagSet),
	                "NOT COMPLETE"
	                );
	    }


	    @Override
	    public CommandResult execute() {

	        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();

	        if (lastShownList.size() < targetIndex) {
	            indicateAttemptToExecuteIncorrectCommand();
	            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
	        }

	        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
	        
	        Name taskToEditName = taskToEdit.getName();
	        Date taskToEditDate = taskToEdit.getPhone();
	        StartTime taskToEditStartTime = taskToEdit.getEmail();
	        EndTime taskToEditEndTime = taskToEdit.getAddress();

	        
	        Name toAddName = toAdd.getName();
            Date toAddDate = toAdd.getPhone();
            StartTime toAddStartTime = toAdd.getEmail();
            EndTime toAddEndTime = toAdd.getAddress();

            
            if (toAddName.toString().equals("")) {
                toAdd.setName(taskToEditName);
            }
            
            if (toAddDate.toString().equals("")) {
                toAdd.setDate(taskToEditDate);
            }
            if (toAddStartTime.toString().equals("")) {
                toAdd.setStartTime(taskToEditStartTime);
            }
            if (toAddEndTime.toString().equals("")) {
                toAdd.setEndTime(taskToEditEndTime);
            }
            

	        try {
	            model.getHistory().stackEditInstruction(taskToEdit,toAdd);
	            model.deletePerson(taskToEdit);
	            model.addPerson(toAdd);
	            
	        } catch (PersonNotFoundException pnfe) {
	            assert false : "The target task cannot be missing";
	        } catch (UniqueTaskList.DuplicatePersonException e) {
	            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
	        }

	        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, taskToEdit));
	    }

	}


