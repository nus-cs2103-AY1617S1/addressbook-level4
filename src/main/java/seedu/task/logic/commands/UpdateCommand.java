package seedu.task.logic.commands;

import java.util.Set;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.LogicManager;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.*;

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
    public CommandResult execute() throws IllegalValueException {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

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

        if(toUpdate.getTimeEnd().isEndBeforeStart(toUpdate.getTimeStart()))
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
