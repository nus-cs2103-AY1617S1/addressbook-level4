package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.LogicManager;
import seedu.address.model.task.*;
import seedu.address.model.tag.UniqueTagList;

/**
 * update the details of a task.
 */
public class UpdateCommand extends Command{

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": update the details of an existing task.\n"
            + "Parameters: INDEX (must be a positive integer) PROPERTY INFOMATION\n"
            + "Example: " + COMMAND_WORD + " 1" + " priority"+" high";

    public static final String MESSAGE_EDIT_SUCCESS = "Edit successfully: %1$s";
    public static final String MESSAGE_EDIT_FAIL = "Editing failed";
    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Time should either be in 24H format or given as a Day of the Week\n"
          + "Eg. 9:11, 09:11, thursday, Thursday, THURSDAY, thu, Thur, THURS";
    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Priority should be high, normal or low";

    public final int targetIndex;
    public final String property;
    public final String info;

    public UpdateCommand(int targetIndex, String property, String info) throws IllegalValueException {
        this.targetIndex = targetIndex;;
        this.property = property;
        this.info = info;
    }

	@Override
    public CommandResult execute() throws IllegalValueException {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUpdate = lastShownList.get(targetIndex - 1);

        Description description = taskToUpdate.getDescription();
        Priority priority = taskToUpdate.getPriority();
        Time timeStart = taskToUpdate.getTimeStart();
        Time timeEnd = taskToUpdate.getTimeEnd();
        UniqueTagList tags = taskToUpdate.getTags();
        boolean completeStatus = taskToUpdate.getCompleteStatus();

        LogicManager.tasks.push(new Task(description, priority, timeStart, timeEnd, tags, completeStatus));
        LogicManager.indexes.push(targetIndex);

			switch(property){
				case "description":
					description = new Description(info);
					timeStart = taskToUpdate.getTimeStart();
					timeEnd = taskToUpdate.getTimeEnd();
					priority = taskToUpdate.getPriority();
					tags =taskToUpdate.getTags();
					completeStatus = taskToUpdate.getCompleteStatus();
					break;

				case "start":
					try {
						timeStart = new Time(info);
					} catch (IllegalValueException e1) {
						LogicManager.tasks.pop();
				        LogicManager.indexes.pop();
						return new CommandResult(MESSAGE_TIME_CONSTRAINTS);
					}
					description = taskToUpdate.getDescription();
					timeEnd = taskToUpdate.getTimeEnd();
					priority = taskToUpdate.getPriority();
					tags =taskToUpdate.getTags();
					completeStatus = taskToUpdate.getCompleteStatus();

					if(timeEnd.isEndBeforeStart(timeStart)){
						LogicManager.tasks.pop();
			        	LogicManager.indexes.pop();
						return new CommandResult(MESSAGE_EDIT_FAIL + ": End is before start.");
					}
					break;

				case "priority":
					try{
						priority = new Priority(info);
					} catch (IllegalValueException e1) {
						LogicManager.tasks.pop();
				        LogicManager.indexes.pop();
						return new CommandResult(MESSAGE_PRIORITY_CONSTRAINTS);
					}
					description = taskToUpdate.getDescription();
					timeEnd = taskToUpdate.getTimeEnd();
					timeStart = taskToUpdate.getTimeStart();
					tags =taskToUpdate.getTags();
					completeStatus = taskToUpdate.getCompleteStatus();
					break;

				case "end":
					try {
						timeEnd = new Time(info);
					} catch (IllegalValueException e1) {
						LogicManager.tasks.pop();
				        LogicManager.indexes.pop();
						return new CommandResult(MESSAGE_TIME_CONSTRAINTS);
					}

					description = taskToUpdate.getDescription();
					priority = taskToUpdate.getPriority();
					timeStart = taskToUpdate.getTimeStart();
					tags =taskToUpdate.getTags();
					completeStatus = taskToUpdate.getCompleteStatus();
					if(timeEnd.isEndBeforeStart(timeStart)){
						LogicManager.tasks.pop();
				        LogicManager.indexes.pop();
						return new CommandResult(MESSAGE_EDIT_FAIL + ": End is before start.");
					}
					break;

				default: return new CommandResult(MESSAGE_EDIT_FAIL);
		}

		DeleteCommand delete = new DeleteCommand(targetIndex);
        delete.model = model;
		delete.execute();
		AddCommand add;
		try {
			add = new AddCommand(description.toString(), priority.toString(), timeStart, timeEnd, tags, completeStatus,targetIndex-1);
			add.model = model;
			add.insert();
			undo = true;
		} catch (IllegalValueException e) {
			LogicManager.tasks.pop();
	        LogicManager.indexes.pop();
			return new CommandResult(String.format(MESSAGE_EDIT_FAIL));
		}
		SelectCommand select = new SelectCommand(targetIndex);
		select.model = model;
		select.execute();
        return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, lastShownList.get(targetIndex - 1)));
	}

	@Override
	 public CommandResult undo() throws IllegalValueException{
		Task task = LogicManager.tasks.pop();
		 int index = LogicManager.indexes.pop();

		 DeleteCommand delete = new DeleteCommand(index);
		 delete.model = model;
		 delete.execute();

		 AddCommand add = new AddCommand(task,index-1);
		 add.model = model;
		 add.insert();

		 LogicManager.tasks.pop();
		 LogicManager.indexes.pop();

		 return new CommandResult("Undo complete!");
	 }
}
