package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.EventDate;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Recurring;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

public class EditCommand extends Command {

	public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + "Edit a field of a event/task in the task manager.\n"
            + "Parameters: EVENT_NAME [s/START_DATE] [e/END_DATE] [r/RECURRING_EVENT] or TASK_NAME [d/DEADLINE] [r/RECURRING_EVENT] \n"
            + "Examples: " + COMMAND_WORD
            + " CS3230 Lecture s/14.10.2016-10 \n"
            + COMMAND_WORD
            + "CS3230 Lecture e/14.10.2016-12 \n";

//    public static final String MESSAGE_SUCCESS = "This event/task has been edited: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This event/task already exists in the task manager";
    public static final String MESSAGE_TASK_NOT_IN_LIST = "This event/task is not found in the task manager";
   //
    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    public static final String MESSAGE_DELETE_SAME_NAME="Please select the Task identified "
    		+ "by the index number.\n"+"Parameters: EVENT_NAME [s/START_DATE] [e/END_DATE] [r/RECURRING_EVENT] [i/INDEX(must be a positive integer)]\n"
    		+"Example: "+COMMAND_WORD+"CS3230 Lecture e/14.10.2016-12 i/1";
    public static final String MESSAGE_EVENT_SUCCESS = "This event has been edited: %1$s";
    public static final String MESSAGE_TASK_SUCCESS = "This task has been edited: %1$s";

//    private final Task toEdit;
    private String name;
    private String type;
    private String details;
    private int targetIndex;

    public EditCommand(String name, String type, String details) {
    	this.name = name;
    	this.type = type;
    	this.details = details;
    	this.targetIndex = -1;
    }

    public EditCommand(String name, String type, String details, int index) {
    	this.name = name;
    	this.type = type;
    	this.details = details;
    	this.targetIndex = index;
    }

	@Override
	public CommandResult execute() {
			ReadOnlyTask target = null;

			UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
	        if (targetIndex != -1) {
	            if (lastShownList.size() < targetIndex) {
	                indicateAttemptToExecuteIncorrectCommand();
	                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
	            }

	            target = lastShownList.get(targetIndex - 1);
	        } else {
	            assert this.name != null;
	            ArrayList<ReadOnlyTask> shownList=new ArrayList<ReadOnlyTask>();
	            for (ReadOnlyTask e : lastShownList) {
	                if (name.trim().equals(e.getName().taskName)) {
	                    shownList.add(e);
	                }
	            }
	            if(shownList.size()>1){
	            	final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(name.trim());
	            	 if (!matcher.matches()) {
	                     return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
	                             EditCommand.MESSAGE_USAGE));
	                 }
	                 // keywords delimited by whitespace
	                 final String[] keywords = matcher.group("keywords").split("\\s+");
	                 final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
	            	model.updateFilteredTaskList(keywordSet);
	            	return new CommandResult(MESSAGE_DELETE_SAME_NAME);
	            }else if(shownList.size()==1){
	            	target = shownList.get(0);
	            }else{
	            	return new CommandResult(Messages.MESSAGE_INVALID_TASK_NAME);
	            }
	        } //end if statment to find the target task

	        try {
				model.editTask(target, type, details);
				String message = String.format(getSuccessMessage(target), target);
				model.updateFilteredListToShowAll();
				model.saveState(message);
				return new CommandResult(message);
			} catch (IllegalValueException e) {
				return new CommandResult(MESSAGE_TASK_NOT_IN_LIST);
			}

		}

	public static String getSuccessMessage(ReadOnlyTask toEdit) {
        if (toEdit.isEvent()) {
            return MESSAGE_EVENT_SUCCESS;
        } else {
            return MESSAGE_TASK_SUCCESS;
        }
    }

//    public EditCommand(String name,
//    		String startDate, String endDate,Set<String> tags, String freq) throws IllegalValueException {
//		final Set<Tag> tagSet = new HashSet<>();
//		for (String tagName : tags) {
//		  tagSet.add(new Tag(tagName));
//		}
////		this.toEdit= new Task(
////                new Name(name),
////                new Deadline(deadline),
////                new UniqueTagList(tagSet)
////		);
//		if(freq!=""&&startDate!=""){
//	        this.toEdit = new Task(
//	                new Name(name),
//	                new EventDate(startDate, endDate),
//	                new UniqueTagList(tagSet),
//	                new Recurring(freq)
//	        );
//	        }else if(startDate!=""){
//	            this.toEdit=new Task(new Name(name),new EventDate(startDate,endDate),new UniqueTagList(tagSet));
//	        }else{
//	            this.toEdit=new Task(new Name(name),new UniqueTagList(tagSet));
//	        }
//	}
//
//    public EditCommand(String name, String deadline, Set<String> tags,String freq)
//            throws IllegalValueException {
//        final Set<Tag> tagSet = new HashSet<>();
//        for (String tagName : tags) {
//            tagSet.add(new Tag(tagName));
//        }
//        if(deadline!=""&&freq!=""){
//        this.toEdit = new Task(
//                new Name(name),
//                new Deadline(deadline),
//                new UniqueTagList(tagSet),
//                new Recurring(freq)
//        );
//        }else if(deadline!=""){
//            this.toEdit=new Task(new Name(name),new Deadline(deadline),new UniqueTagList(tagSet));
//        }else{
//            this.toEdit=new Task(new Name(name),new UniqueTagList(tagSet));
//        }
//    }

//	@Override
//	public CommandResult execute() {
////		try{
////			model.editTask(toEdit);
////			ReadOnlyTask toRemove = null;
////			for (ReadOnlyTask task: model.getFilteredTaskList()) {
////				if(task.getName().equals(toEdit.getName()))
////					toRemove = task;
////			}
////			model.deleteTask(toRemove);
////			String message = String.format(MESSAGE_SUCCESS, toEdit);
////			model.saveState(message);
////			return new CommandResult(message);
////		} catch (TaskNotFoundException e) {
////			// TODO Auto-generated catch block
////        	return new CommandResult(MESSAGE_TASK_NOT_IN_LIST);
////		}
//
//		try{
//			model.editTask(toEdit);
//			ReadOnlyTask toRemove = null;
//
//			int targetIndex;
//			//delete
//	        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
//	        if (targetIndex != -1) {
//	            if (lastShownList.size() < targetIndex) {
//	                indicateAttemptToExecuteIncorrectCommand();
//	                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
//	            }
//
//	            TaskToDelete = lastShownList.get(targetIndex - 1);
//	        } else {
//	            assert this.name != null;
//	            ArrayList<ReadOnlyTask> shownList=new ArrayList<ReadOnlyTask>();
//	            for (ReadOnlyTask e : lastShownList) {
//	                if (name.trim().equals(e.getName().taskName)) {
//	                    shownList.add(e);
//	                }
//	            }
//	            if(shownList.size()>1){
//	            	final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(name.trim());
//	            	 if (!matcher.matches()) {
//	                     return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
//	                             DeleteCommand.MESSAGE_USAGE));
//	                 }
//	                 // keywords delimited by whitespace
//	                 final String[] keywords = matcher.group("keywords").split("\\s+");
//	                 final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
//	            	model.updateFilteredTaskList(keywordSet);
//	            	return new CommandResult(MESSAGE_DELETE_SAME_NAME);
//	            }else if(shownList.size()==1){
//	            	TaskToDelete=shownList.get(0);
//	            }else{
//	            	return new CommandResult(Messages.MESSAGE_INVALID_TASK_NAME);
//	            }
//	        }
//
//		}
//	}

}
