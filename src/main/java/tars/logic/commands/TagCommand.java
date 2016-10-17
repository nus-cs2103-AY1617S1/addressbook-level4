package tars.logic.commands;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import javafx.collections.ObservableList;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.flags.Flag;
import tars.model.tag.ReadOnlyTag;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList.TagNotFoundException;
import tars.ui.Formatter;

/**
 * Tag a task identified using it's last displayed index from tars.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": [-ls] [-e <INDEX> <TAG>]";
    
    public static final String MESSAGE_RENAME_TAG_SUCCESS = "%1$s renamed to [%2$s]";

    private Flag flag;
    private String[] args;
    
    public TagCommand(Flag flag) {
        this.flag = flag;
    }
    
    public TagCommand(Flag flag, String[] args) {
        this.flag = flag;
        this.args = args;
    }

    @Override
    public CommandResult execute() {
        if (flag.prefix.equals(Flag.LIST)) {
            ObservableList<? extends ReadOnlyTag> allTags = model.getUniqueTagList();
            return new CommandResult(new Formatter().formatTags(allTags));
        } else if (flag.prefix.equals(Flag.EDIT)) {
            try {
                int index = Integer.parseInt(args[0]);
                String newTagName = args[1];

                ReadOnlyTag toBeRename = model.getUniqueTagList().get(index - 1);
                model.renameTag(toBeRename, newTagName);
                return new CommandResult(String.format(String.format(MESSAGE_RENAME_TAG_SUCCESS,
                        toBeRename.getAsText(), newTagName)));
            } catch (IllegalValueException e) {
                return new CommandResult(Tag.MESSAGE_TAG_CONSTRAINTS);
            } catch (TagNotFoundException e) {
                return new CommandResult(e.getMessage());
            }
        } else {
            return new CommandResult(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }
    }

}
