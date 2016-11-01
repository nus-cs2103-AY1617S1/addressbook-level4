package seedu.oneline.logic.commands;

import javafx.collections.transformation.FilteredList;
import seedu.oneline.commons.core.UnmodifiableObservableList;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.task.ReadOnlyTask;

public class DeleteTagCommand extends DeleteCommand {

    private final String tagName;
    
    public DeleteTagCommand(String tagName) {
        this.tagName = tagName;
    }
    
    public static DeleteTagCommand createFromArgs(String args) throws IllegalValueException {
        String tagName = args.substring(1);
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS + " : " + args);
        } else {
            return new DeleteTagCommand(tagName);
        }
    }
    
    @Override
    public CommandResult execute() {
        FilteredList<Tag> tagList = model.getTagList().filtered(tag -> tag.getTagName().equals(tagName));
        if (tagList.isEmpty()){
            return new CommandResult(String.format(Tag.MESSAGE_INVALID_TAG, tagName));
        } else {
            // TODO
            return new CommandResult(MESSAGE_DELETE_CAT_SUCCESS + " : " + tagName);
        }
    }
    
    @Override
    public boolean canUndo() {
        return true;
    }
    
}
