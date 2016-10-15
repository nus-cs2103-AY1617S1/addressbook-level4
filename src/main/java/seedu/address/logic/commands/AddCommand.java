package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: DESCRIPTION [pr/PRIORITY] [start/TIME] [end/TIME] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Lose Sleep pr/high time/23:35 d/12.10.2016 t/CS2103 t/WhatIsSleep";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;
    private int index;
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String description, String priority, String timeStart, String timeEnd, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Description(description),
                new Priority(priority),
                new Time(timeStart),
                new Time(timeEnd),
                new UniqueTagList(tagSet)
        );
    }

    public AddCommand(String description, String priority, String timeStart, String timeEnd, UniqueTagList tags, int index)
            throws IllegalValueException {
        this.toAdd = new Task(
                new Description(description),
                new Priority(priority),
                new Time(timeStart),
                new Time(timeEnd),
                tags
        );
        this.index = index;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }

    public CommandResult insert() {
        assert model != null;
        try {
			model.insertTask(index, toAdd);
		} catch (DuplicateTaskException e) {
			return new CommandResult(MESSAGE_DUPLICATE_TASK);
		}
		return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

}
