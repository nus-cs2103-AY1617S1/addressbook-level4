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
 * Rename and delete tag from a list of tags in TARS
 * 
 * @@author A0139924W
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": [/ls] [/e <INDEX> <TAG>] [/del <INDEX>]";
    public static final String MESSAGE_RENAME_TAG_SUCCESS = "%1$s renamed to [%2$s]";
    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";

    /** Offset required to convert between 1-indexing and 0-indexing. */
    private static final int DISPLAYED_INDEX_OFFSET = 1;

    private final Prefix prefix;
    private final String[] args;

    private static final Prefix listPrefix = new Prefix("/ls");
    private static final Prefix editPrefix = new Prefix("/e");
    private static final Prefix deletePrefix = new Prefix("/del");

    public TagCommand(Prefix prefix, String... args) {
        this.prefix = prefix;
        this.args = args;
    }

    @Override
    public CommandResult execute() {
        try {
            if (listPrefix.equals(prefix)) {
                return executeListTag();
            } else if (editPrefix.equals(prefix)) {
                return executeEditTag();
            } else if (deletePrefix.equals(prefix)) {
                return executeDeleteTag();
            }
        } catch (DuplicateTagException e) {
            return new CommandResult(e.getMessage());
        } catch (TagNotFoundException e) {
            return new CommandResult(e.getMessage());
        } catch (IllegalValueException e) {
            return new CommandResult(Tag.MESSAGE_TAG_CONSTRAINTS);
        } catch (NumberFormatException e) {
            return new CommandResult(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    TagCommand.MESSAGE_USAGE));
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
    }

    private CommandResult executeListTag() {
        ObservableList<? extends ReadOnlyTag> allTags = model.getUniqueTagList();
        return new CommandResult(new Formatter().formatTags(allTags));
    }

    private CommandResult executeEditTag()
            throws DuplicateTagException, IllegalValueException, TagNotFoundException {
        int targetedIndex = Integer.parseInt(args[0]);
        String newTagName = args[1];

        if (isInValidIndex(targetedIndex)) {
            return new CommandResult(Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX);
        }

        ReadOnlyTag toBeRename =
                model.getUniqueTagList().get(targetedIndex - DISPLAYED_INDEX_OFFSET);
        model.renameTag(toBeRename, newTagName);
        return new CommandResult(String.format(
                String.format(MESSAGE_RENAME_TAG_SUCCESS, toBeRename.getAsText(), newTagName)));
    }

    private CommandResult executeDeleteTag()
            throws DuplicateTagException, IllegalValueException, TagNotFoundException {
        int targetedIndex = Integer.parseInt(args[0]);

        if (isInValidIndex(targetedIndex)) {
            return new CommandResult(Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX);
        }

        ReadOnlyTag toBeDeleted =
                model.getUniqueTagList().get(targetedIndex - DISPLAYED_INDEX_OFFSET);
        model.deleteTag(toBeDeleted);

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, toBeDeleted));
    }

    /**
     * Checks if the targetedIndex is a valid index
     * 
     * @param targetedIndex
     * @return true if targetedIndex is an invalid index
     */
    private boolean isInValidIndex(int targetedIndex) {
        return targetedIndex < 1 || model.getUniqueTagList().size() < targetedIndex;
    }

}
