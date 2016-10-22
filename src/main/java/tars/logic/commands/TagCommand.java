package tars.logic.commands;

import javafx.collections.ObservableList;
import tars.commons.core.Messages;
import tars.commons.exceptions.IllegalValueException;
import tars.logic.parser.Prefix;
import tars.model.tag.ReadOnlyTag;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList.DuplicateTagException;
import tars.model.tag.UniqueTagList.TagNotFoundException;
import tars.ui.Formatter;

/**
 * List and rename tags
 * 
 * @@author A0139924W
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": [/ls] [/e <INDEX> <TAG>]";
    
    public static final String MESSAGE_RENAME_TAG_SUCCESS = "%1$s renamed to [%2$s]";
    
    /** Offset required to convert between 1-indexing and 0-indexing. */
    private static final int DISPLAYED_INDEX_OFFSET = 1;

    private Prefix prefix;
    private String[] args;
    
    public TagCommand(Prefix prefix) {
        this.prefix = prefix;
    }
    
    public TagCommand(Prefix prefix, String[] args) {
        this.prefix = prefix;
        this.args = args;
    }

    @Override
    public CommandResult execute() {
        if (prefix.prefix.equals("/ls")) {
            ObservableList<? extends ReadOnlyTag> allTags = model.getUniqueTagList();
            return new CommandResult(new Formatter().formatTags(allTags));
        } else if (prefix.prefix.equals("/e")) {
            try {
                int targetedIndex = Integer.parseInt(args[0]);
                String newTagName = args[1];
                
                if (model.getUniqueTagList().size() < targetedIndex || targetedIndex == 0) {
                    return new CommandResult(Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX);
                }
                
                ReadOnlyTag toBeRename = model.getUniqueTagList().get(targetedIndex - DISPLAYED_INDEX_OFFSET);
                model.renameTag(toBeRename, newTagName);
                return new CommandResult(String.format(String.format(MESSAGE_RENAME_TAG_SUCCESS,
                        toBeRename.getAsText(), newTagName)));
                
            } catch (DuplicateTagException e) {
                return new CommandResult(e.getMessage());
            } catch (IllegalValueException e) {
                return new CommandResult(Tag.MESSAGE_TAG_CONSTRAINTS);
            } catch (TagNotFoundException e) {
                return new CommandResult(e.getMessage());
            }
        } else {
            return new CommandResult(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    TagCommand.MESSAGE_USAGE));
        }
    }

}
