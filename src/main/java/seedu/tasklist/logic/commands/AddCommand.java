package seedu.tasklist.logic.commands;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.*;

import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

/**
 * Adds a task to the task list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. "
            + "Parameters: TITLE [d/DESCRIPTION]  [s/START DATE TIME] [e/END DATE TIME] [t/TAG]...\n"
            + "Example: \n"
            + COMMAND_WORD + " CS2103 d/Pre tutorial 1 s/15102016 2100 e/15112016 2300 t/urgent\n"
            + COMMAND_WORD + " CS1020 Tutorial d/many questions e/05102016 1200 t/needhelp\n"
            + COMMAND_WORD + " Meeting";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private Task toAdd;

    public AddCommand() {};
    
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String title, String startDateTime, String description, String endDateTime, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Title(title),
                new DateTime(startDateTime),
                new Description(description),
                new DateTime(endDateTime),
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command prepare(String args) {
        final Matcher matcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());

        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
       
        try {
            return new AddCommand(
                    matcher.group("title"),
                    getDetailsFromArgs(matcher.group("startDateTime")),
                    getDetailsFromArgs(matcher.group("description")),
                    getDetailsFromArgs(matcher.group("endDateTime")),
                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
