//@@author A0140156R

package seedu.oneline.logic.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.TagColor;
import seedu.oneline.model.tag.TagField;
import seedu.oneline.model.task.*;
import seedu.oneline.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task to the task book.
 */
public class EditTagCommand extends EditCommand {

    public final String name;
    public final Map<TagField, String> fields;
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = EditCommand.MESSAGE_USAGE;
    
    public static final String MESSAGE_SUCCESS = "Category updated: %1$s";

    
    public EditTagCommand(String name, Map<TagField, String> fields) throws IllegalValueException, IllegalCmdArgsException {
        if (!Tag.isValidTagName(name)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS + " : " + name);
        }
        if (fields.containsKey(TagField.NAME) && !Tag.isValidTagName(fields.get(TagField.NAME))) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS + " : " + fields.get(TagField.NAME));
        }
        if (fields.containsKey(TagField.COLOR) && !TagColor.isValidColor(fields.get(TagField.COLOR))) {
            throw new IllegalValueException(TagColor.MESSAGE_COLOR_CONSTRAINTS + " : " + fields.get(TagField.COLOR));
        }
        if (fields.size() == 0) {
            throw new IllegalCmdArgsException(EditCommand.MESSAGE_USAGE);
        }
        this.name = name;
        this.fields = fields;
    }
    
    public static EditTagCommand createFromArgs(String args) throws IllegalCmdArgsException, IllegalValueException {
        Entry<String, Map<TagField, String>> parsed = Parser.getTagAndTagFieldsFromArgs(args);
        return new EditTagCommand(parsed.getKey(), parsed.getValue());
    }

    @Override
    public CommandResult execute() {
        String name = this.name; // Mutability
        Tag curTag = null;
        try {
            curTag = Tag.getTag(name);
        } catch (IllegalValueException e1) {
            assert false;
        }
        if (!model.getTaskBook().getTagList().contains(curTag)) {
            return new CommandResult(Messages.MESSAGE_EDIT_TAG_TAG_NOT_FOUND);
        }
        
        List<String> results = new ArrayList<String>();

        if (fields.containsKey(TagField.NAME)) {
            String newName = fields.get(TagField.NAME);
            Tag newTag = null;
            try {
                newTag = Tag.getTag(newName);
            } catch (IllegalValueException e) {
                assert false;
            }
            if (model.getTaskBook().getTagList().contains(newTag)) {
                return new CommandResult(String.format(Tag.MESSAGE_DUPLICATE_TAG, newName));
            }
            TagColor color = model.getTagColor(curTag);
            model.setTagColor(curTag, TagColor.getDefault());
            model.setTagColor(newTag, color);
            List<ReadOnlyTask> taskList = new ArrayList<ReadOnlyTask>(model.getTaskBook().getTaskList());
            Map<TaskField, String> fields = new HashMap<TaskField, String>();
            fields.put(TaskField.TAG, newTag.getTagName());
            for (ReadOnlyTask t : taskList) {
                if (t.getTag().equals(curTag)) {
                    try {
                        Task newTask = t.update(fields);
                        model.replaceTask(t, newTask);
                    } catch (TaskNotFoundException | IllegalValueException e) {
                        assert false : e.getMessage();
                    }
                }
            }
            name = newName;
            curTag = newTag;
            results.add("renamed to " + newTag.getTagName());
        }
        
        if (fields.containsKey(TagField.COLOR)) {
            TagColor color = null;
            try {
                color = new TagColor(fields.get(TagField.COLOR));
            } catch (IllegalValueException e) {
                assert false;
            }
            model.setTagColor(curTag, color);
            results.add(String.format("color updated to " + color.toString()));
        }
          
        String resultStr = updatesToString(results);
        return new CommandResult(resultStr);
    }

    /**
     * @param results
     * @return
     */
    private String updatesToString(List<String> results) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(MESSAGE_SUCCESS, this.name));
        sb.append(" ");
        for (int i = 0; i < results.size(); i++) {
            sb.append(results.get(i));
            if (i < results.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean canUndo() {
        return true;
    }

}