package harmony.mastermind.logic.commands;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import com.google.common.base.Strings;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.tag.UniqueTagList;
import harmony.mastermind.model.task.*;

/**
 * Adds a task to the task manager.
 * 
 */
// @@author A0138862W
public class AddCommand extends Command implements Undoable {

    // http://stackoverflow.com/questions/24472120/match-optional-components-in-any-order
    // the Add command can have multiple aliases, the naming convention is
    // COMMAND_KEYWORD_<command_keyword>
    public static final String COMMAND_KEYWORD_ADD = "add";
    public static final String COMMAND_KEYWORD_DO = "do";

    // @@author A0138862W
    public static final String COMMAND_ARGUMENTS_REGEX = "(?=(?:.*?name\\/\"(?<name>.+?)\"))"
                                                         + "(?=(?:.*?startDate\\/\"(?<startDate>.+?)\")?)"
                                                         + "(?=(?:.*?endDate\\/\"(?<endDate>.+?)\")?)"
                                                         + "(?=(?:.*tags\\/(?<tags>\\w+(?:,\\w+)*)?)?)"
                                                         + ".*";

    public static final Pattern COMMAND_ARGUMENTS_PATTERN = Pattern.compile(COMMAND_ARGUMENTS_REGEX);

    public static final String COMMAND_SUMMARY = "Adding a task:"
                                                 + "\n"
                                                 + "("
                                                 + COMMAND_KEYWORD_ADD
                                                 + " | "
                                                 + COMMAND_KEYWORD_DO
                                                 + ") "
                                                 + " name/\"<taskName>\" [startDate/\"<start_date\">] [endDate/\"<end_date\">] [tags/<comma_spearated_tags>]";

    public static final String MESSAGE_USAGE = COMMAND_SUMMARY
                                               + "\n"
                                               + "Example: "
                                               + COMMAND_KEYWORD_ADD
                                               + " name/\"do laundry\" startDate/\"today\" endDate/\"next friday 6pm\"";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_UNDO_SUCCESS = "[Undo Add Command] Task deleted: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in Mastermind";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    // event
    // @@author A0138862W
    public AddCommand(String name, String startDate, String endDate, Set<String> tags) throws IllegalValueException, ParseException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        this.toAdd = new Task(name, prettyTimeParser.parse(startDate).get(0), prettyTimeParser.parse(endDate).get(0), new UniqueTagList(tagSet), false);

    }

    // deadline
    // @@author A0138862W
    public AddCommand(String name, String endDate, Set<String> tags) throws IllegalValueException, ParseException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        this.toAdd = new Task(name, prettyTimeParser.parse(endDate).get(0), new UniqueTagList(tagSet), false);
    }

    // floating
    // @@author A0138862W
    public AddCommand(String name, Set<String> tags) throws IllegalValueException, ParseException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        this.toAdd = new Task(name, new UniqueTagList(tagSet), false);
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            model.getCommandHistory().push(this);

            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

    @Override
    // @@author A0138862W
    public CommandResult undo() {
        try {
            // remove the task that's previously added.
            model.deleteTask(toAdd);

            return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, toAdd));
        } catch (UniqueTaskList.TaskNotFoundException pne) {
            return new CommandResult(Messages.MESSAGE_TASK_NOT_IN_MASTERMIND);
        }
    }

}
