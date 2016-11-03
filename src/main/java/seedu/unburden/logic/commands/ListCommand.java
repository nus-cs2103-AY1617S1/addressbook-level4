package seedu.unburden.logic.commands;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.common.base.Predicate;

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

	public ListCommand(String doneOrUndone) {
		this.endDate = null;
		this.startDate = null;
		this.mode = doneOrUndone;
	}

	public ListCommand(String args, String mode) throws ParseException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		this.endDate = dateFormatter.parse(args.trim());
		this.startDate = null;
		this.mode = mode;
	}

	public ListCommand(String[] args, String mode) throws ParseException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		this.endDate = dateFormatter.parse(args[1].trim());
		this.startDate = dateFormatter.parse(args[0].trim());
		this.mode = mode;
	}

	private java.util.function.Predicate<? super Task> getAllDatesBefore(Date date) {
		return t -> {
			try {
				return t.getDate().toDate().before(date) || t.getDate().toDate().equals(date);
			} catch (ParseException e) {
				return false;
			}
		};
	}

	private java.util.function.Predicate<? super Task> getAllDatesBetween(Date startDate, Date endDate) {
		return t -> {
			try {
				return (t.getDate().toDate().before(endDate) && t.getDate().toDate().after(startDate))
						|| t.getDate().toDate().equals(startDate) || t.getDate().toDate().equals(endDate);
			} catch (ParseException e) {
				return false;
			}
		};
	}

	private java.util.function.Predicate<? super Task> getAllDone() {
		return t -> {
			return t.getDone();
		};
	}

	private java.util.function.Predicate<? super Task> getAllUndone() {
		return t -> {
			return !t.getDone() && !t.getOverdue();
		};
	}

	private java.util.function.Predicate<? super Task> getAllOverdue() {
		return t -> {
			return t.getOverdue();
		};
	}

	@Override
	public CommandResult execute() throws DuplicateTagException, IllegalValueException {
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
		case "period":
			model.updateFilteredTaskList(getAllDatesBetween(startDate, endDate));
			break;
		case "all":
			model.updateFilteredListToShowAll();
			break;
		default:
			model.updateFilteredTaskList(getAllDatesBefore(endDate));
			break;
		}
		/*
		 * if (mode.equals("undone")) {
		 * model.updateFilteredTaskList(getAllUndone()); //
		 * model.updateFilteredListToShowAll(); } else if (mode.equals("done"))
		 * { model.updateFilteredTaskList(getAllDone()); } else if
		 * (mode.equals("all")) { //
		 * model.updateFilteredTaskList(getAllUndone());
		 * model.updateFilteredListToShowAll(); } else {
		 * model.updateFilteredTaskList(getAllDatesBefore(date)); }
		 */
		return new CommandResult(MESSAGE_SUCCESS);
	}

}
