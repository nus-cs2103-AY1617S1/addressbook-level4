package seedu.todo.logic.commands;

import com.google.common.collect.ImmutableList;
import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.events.ui.ShowTagsEvent;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.commons.util.CollectionUtil;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.IntArgument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.arguments.StringArgument;
import seedu.todo.model.tag.Tag;

import java.util.List;
import java.util.Arrays;
import java.util.regex.Pattern;

//@@author A0135805H
/**
 * This class handles all tagging command
 */
public class TagCommand extends BaseCommand {
    /* Constants */
    private static final String ERROR_INCOMPLETE_PARAMETERS
            = "The tag command is unable to recognise your commands.";
    private static final String ERROR_INPUT_INDEX_REQUIRED
            = "A task index is required.";
    private static final String ERROR_INPUT_ADD_TAGS_REQUIRED
            = "A list of tags \"tag1, tag2, ...\" to add is required.";
    private static final String ERROR_INPUT_DELETE_TAGS_REQUIRED
            = "A list of tags \"tag1, tag2, ...\" to delete is required.";
    private static final String ERROR_INPUT_RENAME_TAGS_REQUIRED
            = "An existing tag name, and a new tag name is required.";
    private static final String ERROR_TAGS_DUPLICATED
            = "You might have keyed in duplicated tag names.";
    private static final String ERROR_TAGS_ILLEGAL_CHAR
            = "Tags may only include alphanumeric characters, including dashes and underscores.";
    private static final String ERROR_TWO_PARAMS
            = "You may only provide two tag names.";

    private static final String SUCCESS_ADD_TAGS = " - tagged successfully";
    private static final String SUCCESS_DELETE_TAGS = " - removed successfully";
    private static final String SUCCESS_RENAME_TAGS = " renamed to  ";

    private static final String DESCRIPTION_SHOW_TAGS = "Shows a global list of tags";
    private static final String DESCRIPTION_ADD_TAGS = "Add tags to a task";
    private static final String DESCRIPTION_DELETE_TAGS = "Delete tags from tasks";
    private static final String DESCRIPTION_RENAME_TAGS = "Rename a tag";

    private static final String ARGUMENTS_SHOW_TAGS = "";
    private static final String ARGUMENTS_ADD_TAGS = "index tag1 [, tag2, ...]";
    private static final String ARGUMENTS_DELETE_TAGS = "[index] /d tag1 [, tag2, ...]";
    private static final String ARGUMENTS_RENAME_TAGS = "/r old_tag_name new_tag_name";

    private static final Pattern TAG_VALIDATION_REGEX = Pattern.compile("^[\\w\\d_-]+$");

    /* Variables */
    private Argument<Integer> index = new IntArgument("index");

    private Argument<String> addTags = new StringArgument("");

    private Argument<String> deleteTags = new StringArgument("/d")
            .flag("d");

    private Argument<String> renameTag = new StringArgument("/r")
            .flag("r");

    @Override
    public Parameter[] getArguments() {
        return new Parameter[] {
            index, deleteTags, addTags, renameTag
        };
    }

    /**
     * Partitions two flag-less parameters index and list of tags by overriding this method.
     */
    @Override
    protected void setPositionalArgument(String argument) {
        String[] tokens = argument.trim().split(" ", 2);
        boolean isFirstArgNumber = StringUtil.isUnsignedInteger(tokens[0]);
        boolean isSecondArgAvailable = tokens.length == 2;

        if (isFirstArgNumber) {
            try {
                index.setValue(tokens[0]);
            } catch (IllegalValueException e) {
                errors.put(index.getName(), e.getMessage());
            }
        }

        if (isSecondArgAvailable) {
            try {
                addTags.setValue(tokens[1]);
            } catch (IllegalValueException e) {
                errors.put(addTags.getName(), e.getMessage());
            }
        }
    }

    @Override
    public String getCommandName() {
        return "tag";
    }

    @Override
    public List<CommandSummary> getCommandSummary() {
        return ImmutableList.of(
            new CommandSummary(DESCRIPTION_SHOW_TAGS, getCommandName(), ARGUMENTS_SHOW_TAGS),
            new CommandSummary(DESCRIPTION_ADD_TAGS, getCommandName(), ARGUMENTS_ADD_TAGS),
            new CommandSummary(DESCRIPTION_DELETE_TAGS, getCommandName(), ARGUMENTS_DELETE_TAGS),
            new CommandSummary(DESCRIPTION_RENAME_TAGS, getCommandName(), ARGUMENTS_RENAME_TAGS)
        );
    }

    @Override
    protected void validateArguments() {
        if (isShowTags()) {
            // No arguments to check.

        } else if (isAddTagsToTask()) {
            //Check arguments for add tags case
            String[] tagsToAdd = StringUtil.splitString(addTags.getValue());
            checkForIllegalCharInTagNames(addTags.getName(), tagsToAdd);
            checkForDuplicatedTagNames(addTags.getName(), tagsToAdd);

        } else if (isDeleteTagsFromTask()) {
            //Check arguments for delete tags case
            String[] tagsToDelete = StringUtil.splitString(deleteTags.getValue());
            checkForDuplicatedTagNames(deleteTags.getName(), tagsToDelete);

        } else if (isDeleteTagsFromAllTasks()) {
            //Check arguments for delete tags case
            String[] tagsToDelete = StringUtil.splitString(deleteTags.getValue());
            checkForDuplicatedTagNames(deleteTags.getName(), tagsToDelete);

        } else if (isRenamingTag()) {
            //Check arguments for rename tags case
            String[] renameTagsParam = StringUtil.splitString(deleteTags.getValue());
            checkForDuplicatedTagNames(renameTag.getName(), renameTagsParam);
            checkForTwoParams(renameTag.getName(), renameTagsParam);

        } else {
            //We do not have sufficient inputs.
            handleUnavailableInputParameters();
        }

        super.validateArguments();
    }

    @Override
    public CommandResult execute() throws ValidationException {
        //Obtain values for manipulation
        Integer displayedIndex = index.getValue();
        String[] tagsToAdd = StringUtil.splitString(addTags.getValue());
        String[] tagsToDelete = StringUtil.splitString(deleteTags.getValue());
        String[] renameTagsParam = StringUtil.splitString(renameTag.getValue());

        //Performs the actual execution with the data
        if (isShowTags()) {
            ShowTagsEvent tagsEvent = new ShowTagsEvent(model.getGlobalTagsList());
            EventsCenter.getInstance().post(tagsEvent);
            return new CommandResult();

        } else if (isAddTagsToTask()) {
            model.addTagsToTask(displayedIndex, tagsToAdd);
            return new CommandResult(StringUtil.convertListToString(tagsToAdd) + SUCCESS_ADD_TAGS);

        } else if (isDeleteTagsFromTask()) {
            model.deleteTagsFromTask(displayedIndex, tagsToDelete);
            return new CommandResult(StringUtil.convertListToString(tagsToDelete) + SUCCESS_DELETE_TAGS);

        } else if (isDeleteTagsFromAllTasks()) {
            model.deleteTags(tagsToDelete);
            return new CommandResult(StringUtil.convertListToString(tagsToDelete) + SUCCESS_DELETE_TAGS);

        } else if (isRenamingTag()) {
            model.renameTag(renameTagsParam[0], renameTagsParam[1]);
            return new CommandResult(renameTagsParam[0] + SUCCESS_DELETE_TAGS + renameTagsParam[1]);

        } else {
            //Invalid case, should not happen, as we have checked it validateArguments.
            //However, for completeness, a command result is returned.
            throw new ValidationException(ERROR_INCOMPLETE_PARAMETERS);
        }
    }

    /* Input Parameters Validation */
    /**
     * Returns true if the command parameters matches the action of show global tags
     */
    private boolean isShowTags() {
        return !index.hasBoundValue() && !addTags.hasBoundValue() && !deleteTags.hasBoundValue()
                && !renameTag.hasBoundValue();
    }

    /**
     * Returns true if the command parameters matches the action of adding tag(s) to a task.
     */
    private boolean isAddTagsToTask() {
        return index.hasBoundValue() && addTags.hasBoundValue() && !deleteTags.hasBoundValue()
                && !renameTag.hasBoundValue();
    }

    /**
     * Returns true if the command parameters matches the action of deleting tag(s) from a task.
     */
    private boolean isDeleteTagsFromTask() {
        return index.hasBoundValue() && !addTags.hasBoundValue() && deleteTags.hasBoundValue()
                && !renameTag.hasBoundValue();
    }

    /**
     * Returns true if the command parameters matches the action of deleting tag(s) from all tasks.
     */
    private boolean isDeleteTagsFromAllTasks() {
        return !index.hasBoundValue() && !addTags.hasBoundValue() && deleteTags.hasBoundValue()
                && !renameTag.hasBoundValue();
    }

    /**
     * Returns true if the command parameters matches the action of renaming tags.
     */
    private boolean isRenamingTag() {
        return !index.hasBoundValue() && !addTags.hasBoundValue() && !deleteTags.hasBoundValue()
                && renameTag.hasBoundValue();
    }

    /**
     * Sets error messages for insufficient input parameters, dependent on input parameters supplied.
     */
    private void handleUnavailableInputParameters() {
        boolean hasIndex = index.hasBoundValue();
        boolean hasAdd = addTags.hasBoundValue();
        boolean hasDelete = deleteTags.hasBoundValue();
        boolean hasRename = renameTag.hasBoundValue();

        if (getNumberOfTruth(hasAdd, hasDelete, hasRename) > 1) {
            //Only one set of tags can be available.
            errors.put("You have supplied too many parameters.");

        } else if (hasAdd && !hasIndex) {
            //Add requires an index
            errors.put(index.getName(), ERROR_INPUT_INDEX_REQUIRED);

        } else if (hasRename && !hasIndex) {
            //Rename requires an index
            errors.put(index.getName(), ERROR_INPUT_INDEX_REQUIRED);

        } else if (hasIndex && !hasAdd && !hasDelete && !hasRename) {
            //Has an index and nothing else
            errors.put(addTags.getName(), ERROR_INPUT_ADD_TAGS_REQUIRED);
            errors.put(deleteTags.getName(), ERROR_INPUT_DELETE_TAGS_REQUIRED);
            errors.put(renameTag.getName(), ERROR_INPUT_RENAME_TAGS_REQUIRED);

        } else {
            //Falls into none of the cases above. Throw a generic error.
            errors.put(ERROR_INCOMPLETE_PARAMETERS);
        }
    }

    /**
     * Checks if the given tag names have duplicated entries.
     */
    private void checkForDuplicatedTagNames(String argumentName, String[] tagNames) {
        if (!CollectionUtil.elementsAreUnique(Arrays.asList(tagNames))) {
            errors.put(argumentName, ERROR_TAGS_DUPLICATED);
        }
    }

    /**
     * Check if the given tag names are alphanumeric, which also can contain dashes and underscores.
     */
    private void checkForIllegalCharInTagNames(String argumentName, String[] tagNames) {
        for (String tagName : tagNames) {
            if (!isValidTagName(tagName)) {
                errors.put(argumentName, ERROR_TAGS_ILLEGAL_CHAR);
            }
        }
    }

    private void checkForTwoParams(String argumentName, String[] params) {
        if (params != null && params.length != 2) {
            errors.put(argumentName, ERROR_TWO_PARAMS);
        }
    }

    /* Helper Methods */
    /**
     * Returns true if a given string is a valid tag name (alphanumeric, can contain dashes and underscores)
     * Originated from {@link Tag}
     */
    private static boolean isValidTagName(String test) {
        return TAG_VALIDATION_REGEX.matcher(test).matches();
    }

    /**
     * Returns number of true booleans.
     */
    private static int getNumberOfTruth(boolean... booleans) {
        int count = 0;
        for (boolean bool : booleans) {
            if (bool) {
                count += 1;
            }
        }
        return count;
    }
}