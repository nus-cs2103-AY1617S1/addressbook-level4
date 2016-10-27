//@@ author A0147969E
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
 * Updates the details of a task.
 */
public class UpdateCommand extends Command{

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": update the details of an existing task.\n"
    		+ "Parameters: INDEX [des/DESCRIPTION] [pr/PRIORITY] [st/TIME] [ed/TIME]\n"
            + "Example: " + COMMAND_WORD
            + " des/Go to Tutorial pr/normal st/12:00 ed/14:00";

    public static final String MESSAGE_EDIT_SUCCESS = "Edit successfully: %1$s";
    public static final String MESSAGE_EDIT_FAIL = "Editing failed";

    private final Task toUpdate;
    private int index;

    private Time start;
    private Time end;
    private Description description;
    private Priority priority;
	public UpdateCommand(String index, String description, String priority, String start,
			String end) throws IllegalValueException {

		if(description.equals(""))
			description = "NODESCRIPTION";
		this.description = new Description(description);
		if(priority.equals(""))
			priority = "NOUPDATE";
		this.priority = new Priority(priority);
		this.start = new Time(start);
        this.end = new Time(end);

        this.toUpdate = new Task(
                this.description,
                this.priority,
                this.start,
                this.end,
                new UniqueTagList()
         );

        this.index = Integer.parseInt(index);
	}

	@Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getSortedFilteredTaskList();

        if (lastShownList.size() < index) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUpdate = lastShownList.get(index - 1);

        if(toUpdate.getDescription().toString().equals("NODESCRIPTION"))
        	toUpdate.setDescription(taskToUpdate.getDescription());
        if(toUpdate.getTimeStart().toString().equals(""))
        	toUpdate.setTimeStart(taskToUpdate.getTimeStart());
        if(toUpdate.getTimeEnd().toString().equals(""))
        	toUpdate.setTimeEnd(taskToUpdate.getTimeEnd());
        if(toUpdate.getPriority().toString().equals("NOUPDATE"))
        	toUpdate.setPriority(taskToUpdate.getPriority());
        toUpdate.setTags(taskToUpdate.getTags());
        toUpdate.setCompleteStatus(taskToUpdate.getCompleteStatus());

        if(toUpdate.getTimeEnd().isBefore(toUpdate.getTimeStart()))
			return new CommandResult(MESSAGE_EDIT_FAIL + ": End is before start.");

        LogicManager.tasks.push(new Task(taskToUpdate.getDescription(),
        								 taskToUpdate.getPriority(),
        								 taskToUpdate.getTimeStart(),
        								 taskToUpdate.getTimeEnd(),
        								 taskToUpdate.getTags(),
        								 taskToUpdate.getCompleteStatus()));
        LogicManager.indexes.push(index);

		DeleteCommand delete = new DeleteCommand(index);
        delete.model = model;
		delete.execute();
		AddCommand add;
		try {
			add = new AddCommand(toUpdate,index-1);
			add.model = model;
			add.insert();
			undo = true;
		} catch (IllegalValueException e) {
			LogicManager.tasks.pop();
	        LogicManager.indexes.pop();
			return new CommandResult(String.format(MESSAGE_EDIT_FAIL));
		}
		SelectCommand select = new SelectCommand(index);
		select.model = model;
		select.execute();
        return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, lastShownList.get(index - 1)));
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
