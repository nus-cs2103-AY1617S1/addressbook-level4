package seedu.oneline.logic.commands;

import static seedu.oneline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.core.UnmodifiableObservableList;
import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.task.*;
import seedu.oneline.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task to the task book.
 */
public class EditTagCommand extends EditCommand {

    public final String oldName;
    public final String newName;
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a task to the task book. "
            + "Parameters: NAME p/PHONE e/EMAIL a/ADDRESS  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " John Doe p/98765432 e/johnd@gmail.com a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "Task updated: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task book";


    private static final Pattern EDIT_TAGS_ARGS_FORMAT =
            Pattern.compile("#(?<oldName>\\p{Alnum}+)" // index
                    + " #(?<newName>\\p{Alnum}+)"); // the other arguments
    
    public EditTagCommand(String oldName, String newName) throws IllegalCmdArgsException {
        assert oldName != null;
        assert newName != null;
        if (!Tag.isValidTagName(oldName) || !Tag.isValidTagName(newName)) {
            throw new IllegalCmdArgsException("Tag " + 
                        ((!Tag.isValidTagName(oldName)) ? oldName : newName) + 
                                " is not a valid tag."
                    );
        }
        this.oldName = oldName;
        this.newName = newName;
    }
    
    public static EditTagCommand createFromArgs(String args) throws IllegalCmdArgsException {
        final Matcher matcher = EDIT_TAGS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
           throw new IllegalCmdArgsException("Format: edit #oldtag #newtag");
        }
        String oldName = matcher.group("oldName");
        String newName = matcher.group("newName");
        return new EditTagCommand(oldName, newName);
    }

    @Override
    public CommandResult execute() {
        Tag oldTag = null;
        Tag newTag = null;
        try {
            oldTag = Tag.getTag(oldName);
            newTag = Tag.getTag(newName);
        } catch (IllegalValueException e) {
        }
        if (model.getTaskBook().getTagList().contains(newTag)) {
            return new CommandResult("Tag " + newName + " already exists.");
        }
        List<ReadOnlyTask> taskList = new ArrayList<ReadOnlyTask>(model.getTaskBook().getTaskList());
        Map<TaskField, String> fields = new HashMap<TaskField, String>();
        fields.put(TaskField.TAG, newTag.getTagName());
        int taskCount = 0;
        for (ReadOnlyTask t : taskList) {
            if (t.getTag().equals(oldTag)) {
                try {
                    Task newTask = t.update(fields);
                    model.replaceTask(t, newTask);
                    taskCount++;
                } catch (TaskNotFoundException | IllegalValueException e) {
                    assert false : e.getMessage();
                }
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskCount));
    }
    
    @Override
    public boolean canUndo() {
        return true;
    }

}