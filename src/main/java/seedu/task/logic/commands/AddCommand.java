package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.*;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Adds a task to the address book.
 */
public class AddCommand extends Command {

	public static final String COMMAND_WORD = "add";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
			+ "Parameters: TITLE [d/DESCRIPTION] [sd/START_DATE] [dd/DUE_DATE] [i/INTERVAL] [ti/TIMEINTERVAL] [t/TAG]...\n"
			+ "Example: " + COMMAND_WORD + " HOMEWORK d/Math homework. dd/01-01-2012 i/2 ti/7";
	public static final String MESSAGE_EVENT_USAGE = "To add an event, DUE_DATE is required\n" + "Example: "
			+ COMMAND_WORD + " HOMEWORK d/Math homework. dd/01-01-2012 i/2 ti/7";

	public static final String MESSAGE_SUCCESS = "New task added: %1$s";
	public static final String MESSAGE_SUCCESS_MANY_TASKS = "%1$s tasks added: %2$s";
	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

	private List<Task> tasksToAdd;

	/**
	 * Convenience constructor using raw values.
	 *
	 * @throws IllegalValueException
	 *             if any of the raw values are invalid
	 * @throws ParseException
	 */
	public AddCommand(String title, String description, String startDate, String dueDate, String interval,
			String timeInterval, Set<String> tags) throws IllegalValueException, ParseException {
		final Set<Tag> tagSet = new HashSet<>();
		for (String tagName : tags) {
			tagSet.add(new Tag(tagName));
		}
		Task mainTask = new Task(new Title(title), new Description(description), new StartDate(startDate),
				new DueDate(dueDate), new Interval(interval), new TimeInterval(timeInterval), new Status("Ongoing"),
				new UniqueTagList(tagSet));
		addTasksToList(mainTask);
	}

	private void addTasksToList(Task mainTask) {
		int timeInterval = mainTask.getTimeInterval().value;
		tasksToAdd = new ArrayList<Task>();
		tasksToAdd.add(mainTask);
		for (int i = 1; i < mainTask.getInterval().value; i++) {
			tasksToAdd.add( new Task(mainTask.getTitle(), mainTask.getDescription(), mainTask.getStartDateWithInterval(timeInterval*i),
					mainTask.getDueDateWithInterval(timeInterval*i), mainTask.getInterval(), mainTask.getTimeInterval(), new Status("Ongoing"),
					mainTask.getTags()));
		}
	}

	@Override
	public CommandResult execute() {
		assert model != null;
		try {
			for (Task task : tasksToAdd) {
				model.addTask(task);
			}
			EventsCenter.getInstance().post(new JumpToListRequestEvent(model.getFilteredTaskList().size()-1));
			if(tasksToAdd.size()==1)
				return new CommandResult(String.format(MESSAGE_SUCCESS,tasksToAdd.get(0).getTitle()
				        + " Description: " + tasksToAdd.get(0).getDescription()
				        + " Start Date: " + tasksToAdd.get(0).getStartDate()
				        + " Due Date: " + tasksToAdd.get(0).getDueDate()
				        + " Status: " + tasksToAdd.get(0).getStatus()));
			else
				return new CommandResult(String.format(MESSAGE_SUCCESS_MANY_TASKS,tasksToAdd.get(0).getInterval(),tasksToAdd.get(0).getTitle()));
		} catch (UniqueTaskList.DuplicateTaskException e) {
			return new CommandResult(MESSAGE_DUPLICATE_TASK);
		}

	}

}
