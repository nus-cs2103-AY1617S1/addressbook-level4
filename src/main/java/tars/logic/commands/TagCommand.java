package tars.logic.commands;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import tars.commons.core.Messages;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.util.StringUtil;
import tars.logic.parser.Prefix;
import tars.model.tag.ReadOnlyTag;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList.DuplicateTagException;
import tars.model.tag.UniqueTagList.TagNotFoundException;
import tars.model.task.ReadOnlyTask;
import tars.ui.formatter.Formatter;

/**
 * Rename and delete tag from a list of tags in TARS
 * 
 * @@author A0139924W
 */
public class TagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": [/ls] [/e <INDEX> <TAG_NAME>] [/del <INDEX>]";
    public static final String MESSAGE_RENAME_TAG_SUCCESS =
            "%1$s renamed to [%2$s]";
    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";

    private final Prefix prefix;
    private final String[] args;

    private static final Prefix listPrefix = new Prefix("/ls");
    private static final Prefix editPrefix = new Prefix("/e");
    private static final Prefix deletePrefix = new Prefix("/del");

    private ReadOnlyTag toBeRenamed;
    private ReadOnlyTag toBeDeleted;
    private Tag newTag;
    private ArrayList<ReadOnlyTask> editedTaskList;

    public TagCommand(Prefix prefix, String... args) {
        this.prefix = prefix;
        this.args = args;
    }

    @Override
    public CommandResult execute() {
        CommandResult result = null;

        try {
            if (listPrefix.equals(prefix)) {
                result = executeListTag();
            } else if (editPrefix.equals(prefix)) {
                result = executeEditTag();
            } else if (deletePrefix.equals(prefix)) {
                result = executeDeleteTag();
            }
        } catch (DuplicateTagException e) {
            return new CommandResult(e.getMessage());
        } catch (TagNotFoundException e) {
            return new CommandResult(e.getMessage());
        } catch (IllegalValueException e) {
            return new CommandResult(Tag.MESSAGE_TAG_CONSTRAINTS);
        } catch (NumberFormatException e) {
            return new CommandResult(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                            TagCommand.MESSAGE_USAGE));
        }

        return result;
    }

    private CommandResult executeListTag() {
        ObservableList<? extends ReadOnlyTag> allTags =
                model.getUniqueTagList();
        return new CommandResult(new Formatter().formatTags(allTags));
    }

    private CommandResult executeEditTag() throws DuplicateTagException,
            IllegalValueException, TagNotFoundException {
        int targetedIndex = Integer.parseInt(args[0]);
        String newTagName = args[1];

        if (isInValidIndex(targetedIndex)) {
            return new CommandResult(
                    Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX);
        }

        toBeRenamed = model.getUniqueTagList()
                .get(targetedIndex - Formatter.DISPLAYED_INDEX_OFFSET);
        newTag = new Tag(newTagName);
        model.renameTasksWithNewTag(toBeRenamed, newTag);

        model.getUndoableCmdHist().push(this);
        return new CommandResult(
                String.format(String.format(MESSAGE_RENAME_TAG_SUCCESS,
                        toBeRenamed.getAsText(), newTagName)));
    }

    private CommandResult executeDeleteTag() throws DuplicateTagException,
            IllegalValueException, TagNotFoundException {
        int targetedIndex = Integer.parseInt(args[0]);

        if (isInValidIndex(targetedIndex)) {
            return new CommandResult(
                    Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX);
        }

        toBeDeleted = model.getUniqueTagList()
                .get(targetedIndex - Formatter.DISPLAYED_INDEX_OFFSET);
        editedTaskList = model.removeTagFromAllTasks(toBeDeleted);

        model.getUndoableCmdHist().push(this);
        return new CommandResult(
                String.format(MESSAGE_DELETE_TAG_SUCCESS, toBeDeleted));
    }

    /**
     * Checks if the targetedIndex is a valid index
     * 
     * @param targetedIndex
     * @return true if targetedIndex is an invalid index
     */
    private boolean isInValidIndex(int targetedIndex) {
        return targetedIndex < 1
                || model.getUniqueTagList().size() < targetedIndex;
    }

    @Override
    public CommandResult undo() {
        try {
            if (editPrefix.equals(prefix)) {
                model.renameTasksWithNewTag(newTag, new Tag(toBeRenamed));

            } else if (deletePrefix.equals(prefix)) {
                model.addTagToAllTasks(toBeDeleted, editedTaskList);
            }
        } catch (Exception e) {
            return new CommandResult(UndoCommand.MESSAGE_UNSUCCESS);
        }

        return new CommandResult(String.format(UndoCommand.MESSAGE_SUCCESS,
                StringUtil.EMPTY_STRING));
    }

    @Override
    public CommandResult redo() {
        try {
            if (editPrefix.equals(prefix)) {
                model.renameTasksWithNewTag(toBeRenamed, newTag);
            } else if (deletePrefix.equals(prefix)) {
                editedTaskList = model.removeTagFromAllTasks(toBeDeleted);
            }
        } catch (Exception e) {
            return new CommandResult(RedoCommand.MESSAGE_UNSUCCESS);
        }

        return new CommandResult(String.format(RedoCommand.MESSAGE_SUCCESS,
                StringUtil.EMPTY_STRING));
    }

}
