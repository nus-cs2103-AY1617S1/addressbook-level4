package seedu.unburden.logic.commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import seedu.unburden.commons.core.Messages;
import seedu.unburden.commons.core.UnmodifiableObservableList;
import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.model.tag.UniqueTagList.DuplicateTagException;
import seedu.unburden.model.task.ReadOnlyTask;
import seedu.unburden.model.task.Task;

/**
 * Lists all persons in the address book to the user.
 * 
 * @@author A0139678J
 */

// @@Nathanael Chan A0139678J
public class ListCommand extends Command {

	public static final String COMMAND_WORD = "list";
	public static final String MESSAGE_SUCCESS = "Listed all tasks";
	public static final String MESSAGE_NO_MATCHES_DONE = "There are currently no tasks that are marked as done.\nDo try again after marking some task.";
	public static final String MESSAGE_NO_MATCHES_UNDONE = "There are currently no tasks that are marked as undone.\nDo try again after adding more tasks.";
	public static final String MESSAGE_NO_MATCHES_OVERDUE = "There are currently no tasks that are marked as overdue.\nKeep it up!";
	public static final String MESSAGE_NO_MATCHES_DATE = "There are currently no tasks found within the dates you specified";

	public static final String MESSAGE_USAGE = "Type : \"" + COMMAND_WORD + "\" or type : \"" + COMMAND_WORD
			+ "\" your specified date ";

	private final Date endDate;

	private final Date startDate;

	private final String mode;

	public ListCommand() {
		this.endDate = null;
		this.startDate = null;
		this.mode = "undone";
	}

	/**
	 * This constructor is used when listing done, undone or overdue
	 * 
	 * @param args
	 */
	public ListCommand(String args) {
		this.endDate = null;
		this.startDate = null;
		this.mode = args;
	}

	/**
	 * This constructor is used when the listing all tasks within a start time
	 * and end time
	 * 
	 * @param startTime
	 * @param endTime
	 * @param mode
	 * @throws ParseException
	 */
	public ListCommand(String startTime, String endTime, String mode) throws ParseException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		this.endDate = dateFormatter.parse(endTime.trim());
		this.startDate = dateFormatter.parse(startTime.trim());
		this.mode = mode;
	}

	/**
	 * Returns true if the task's deadline falls within the start date and end
	 * date and is undone
	 * 
	 * @param startDate
	 * @param endDate
	 * @return true if the task meets the requirements specified, false
	 *         otherwise
	 */
	private java.util.function.Predicate<? super Task> getAllDatesBetween(Date startDate, Date endDate) {
		return t -> {
			try {
				return (t.getDate().toDate().before(endDate) && t.getDate().toDate().after(startDate) && !t.getDone())
						|| (t.getDate().toDate().equals(startDate) && !t.getDone())
						|| (t.getDate().toDate().equals(endDate) && !t.getDone());
			} catch (ParseException e) {
				return false;
			}
		};
	}

	/**
	 * Returns true if the task is done
	 * 
	 * @return true if the task is done, false otherwise
	 */
	private java.util.function.Predicate<? super Task> getAllDone() {
		return t -> {
			return t.getDone();
		};
	}

	/**
	 * Returns true if the task is undone
	 * 
	 * @return true if the task is undone, false otherwise
	 */
	private java.util.function.Predicate<? super Task> getAllUndone() {
		return t -> {
			return !t.getDone() && !t.getOverdue();
		};
	}

	/**
	 * Returns true if the task is overdue
	 * 
	 * @return true if the task is overdue, false otherwise
	 */
	private java.util.function.Predicate<? super Task> getAllOverdue() {
		return t -> {
			return t.getOverdue();
		};
	}

	@Override
	public CommandResult execute() {
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
		switch (mode) {
		case "undone":
			model.updateFilteredTaskList(getAllUndone());
			break;
		case "overdue":
			model.updateFilteredTaskList(getAllOverdue());
			break;
		case "done":
			model.updateFilteredTaskList(getAllDone());
			break;
		case "date":
			model.updateFilteredTaskList(getAllDatesBetween(startDate, endDate));
			break;
		default:
			model.updateFilteredListToShowAll();
		}
		if (lastShownList.size() == 0) {
			switch (mode) {
			case "undone":
				return new CommandResult(MESSAGE_NO_MATCHES_UNDONE);
			case "done":
				return new CommandResult(MESSAGE_NO_MATCHES_DONE);
			case "overdue":
				return new CommandResult(MESSAGE_NO_MATCHES_OVERDUE);
			case "date":
				return new CommandResult(MESSAGE_NO_MATCHES_DATE);
			}
			return new CommandResult(String.format(Messages.MESSAGE_NO_TASKS_FOUND, ListCommand.MESSAGE_USAGE));
		} else {
			if (mode.equals("all")) {
				return new CommandResult(MESSAGE_SUCCESS);

			} else {
				return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
			}
		}
	}

}
