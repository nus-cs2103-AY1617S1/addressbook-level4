package seedu.oneline.logic.commands;

import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.tag.Tag;

public class ListTagCommand extends ListCommand {
    public final String name;
    
    public static final String COMMAND_WORD = "edit";
    
    public static final String MESSAGE_SUCCESS = "Category updated: %1$s";
    public static final String MESSAGE_INVALID_TAG = "The category %1$s is invalid";
    
    /**
     * Constructs a ListTagCommand given a tag
     * @param tag the tag in the format #<tag name>
     * @throws IllegalValueException
     */
    public ListTagCommand(String tag) throws IllegalValueException {
        if (!Tag.isValidTagName(tag)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS + " : " + tag);
        } else {
            name = tag.substring(1);
        }
    }
}
