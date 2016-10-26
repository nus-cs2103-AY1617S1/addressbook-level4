package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.LogicManager;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Description;
import seedu.task.model.task.Time;
import seedu.task.model.task.Priority;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;

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
    
    public final int targetIndex;
    public final String property;
    public final String info;

    public UpdateCommand(int targetIndex, String property, String info) throws IllegalValueException {
        this.targetIndex = targetIndex;;
        this.property = property;
        this.info = info;
    }

	@Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getSortedFilteredTaskList();

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
                    try {
                        description = new Description(info);
                    } catch (IllegalValueException e2) {
                        LogicManager.tasks.pop();
                        LogicManager.indexes.pop();
                        return new CommandResult(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
                    }
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
						return new CommandResult(Time.MESSAGE_TIME_CONSTRAINTS);
					}
					description = taskToUpdate.getDescription();
					timeEnd = taskToUpdate.getTimeEnd();
					priority = taskToUpdate.getPriority();
					tags =taskToUpdate.getTags();
					completeStatus = taskToUpdate.getCompleteStatus();

					if(timeEnd.isBefore(timeStart)){
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
						return new CommandResult(Priority.MESSAGE_PRIORITY_CONSTRAINTS);
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
						return new CommandResult(Time.MESSAGE_TIME_CONSTRAINTS);
					}

					description = taskToUpdate.getDescription();
					priority = taskToUpdate.getPriority();
					timeStart = taskToUpdate.getTimeStart();
					tags =taskToUpdate.getTags();
					completeStatus = taskToUpdate.getCompleteStatus();
					if(timeEnd.isBefore(timeStart)){
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
