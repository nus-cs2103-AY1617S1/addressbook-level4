package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add an event with a starting and ending date or a task (with or without deadline) to the task manager.\n"
            + "Parameters: EVENT_NAME s/START_DATE e/END_DATE [t/TAG]... or TASK_NAME [d/DEADLINE] [t/TAG]...\n"// [p/PRIORITY_LEVEL]
            + "Example: " + COMMAND_WORD
            + " Lecture s/7.10.2016-14 e/7.10.2016-16 t/CS2103, add Project Deadline d/14.10.2016 t/CS2103"; // p/3

    public static final String MESSAGE_EVENT_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_TASK_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "It's already exists in the task manager";
    public static final String DATE_VALIDATION_REGEX = "^[0-3]?[0-9].[0-1]?[0-9].([0-9]{4})(-[0-2]?[0-9]?)?";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, String deadline, Set<String> tags, String freq) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        if (!deadline.matches(DATE_VALIDATION_REGEX) && !deadline.equals("")) {
            List<Date> date = new com.joestelmach.natty.Parser().parse(deadline).get(0).getDates();
            deadline = dateFormat.format(date.get(0)).toString() + "-" + date.get(0).toString().substring(11, 13);
        }
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        if (deadline != "" && freq != "") {
            this.toAdd = new Task(new Name(name), new Deadline(deadline), new UniqueTagList(tagSet),
                    new Recurring(freq));
        } else if (deadline != "") {
            this.toAdd = new Task(new Name(name), new Deadline(deadline), new UniqueTagList(tagSet));
        } else {
            this.toAdd = new Task(new Name(name), new UniqueTagList(tagSet));
        }
    }

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, String startDate, String endDate, Set<String> tags, String freq)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        List<Date> date;
        if (!startDate.matches(DATE_VALIDATION_REGEX) && !startDate.equals("")) {
            date = new com.joestelmach.natty.Parser().parse(startDate).get(0).getDates();
            startDate = dateFormat.format(date.get(0)).toString() + "-" + date.get(0).toString().substring(11, 13);
        }
        if (!endDate.matches(DATE_VALIDATION_REGEX) && !endDate.equals("")) {
            date = new com.joestelmach.natty.Parser().parse(endDate).get(0).getDates();
            endDate = dateFormat.format(date.get(0)).toString() + "-" + date.get(0).toString().substring(11, 13);
        }
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        if (freq != "" && startDate != "") {
            this.toAdd = new Task(new Name(name), new EventDate(startDate, endDate), new UniqueTagList(tagSet),
                    new Recurring(freq));
        } else if (startDate != "") {
            this.toAdd = new Task(new Name(name), new EventDate(startDate, endDate), new UniqueTagList(tagSet));
        } else {
            this.toAdd = new Task(new Name(name), new UniqueTagList(tagSet));
        }
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            String message = String.format(getSuccessMessage(toAdd), toAdd);
            model.saveState(message);
            return new CommandResult(message);
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }

    public static String getSuccessMessage(Task toAdd) {
        if (toAdd.isEvent()) {
            return MESSAGE_EVENT_SUCCESS;
        } else {
            return MESSAGE_TASK_SUCCESS;
        }
    }

}