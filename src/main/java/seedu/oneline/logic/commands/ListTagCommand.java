//@@author A0138848M
package seedu.oneline.logic.commands;

import javafx.collections.transformation.FilteredList;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.tag.Tag;

public class ListTagCommand extends ListCommand {
    public final String name;
    
    public static final String COMMAND_WORD = "edit";
    
    public static final String MESSAGE_SUCCESS = "Listed all tasks of category: %1$s";
    public static final String MESSAGE_INVALID_TAG = "The category %1$s is invalid";
    
    public ListTagCommand(String tagName) throws IllegalValueException {
        name = tagName;
    }

    /**
     * Constructs a ListTagCommand given a tag
     * @param tag the tag in the format #<tag name>
     * @throws IllegalValueException if tag is invalid
     */
    public static ListTagCommand createFromArgs(String tag) throws IllegalValueException {
        String tagName = tag.substring(1);
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS + " : " + tag);
        } else {
            return new ListTagCommand(tagName);
        }
    }
    
    @Override
    public CommandResult execute(){
        FilteredList<Tag> tagList = model.getTagList().filtered(tag -> tag.getTagName().equals(name));
        if (tagList.isEmpty()){
            return new CommandResult(String.format(MESSAGE_INVALID_TAG, name));
        } else {
            model.updateFilteredTaskListToShowTag(name);
            return new CommandResult(String.format(MESSAGE_SUCCESS, name));
        }
    }
}
