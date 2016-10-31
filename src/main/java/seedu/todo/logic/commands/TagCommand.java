package seedu.todo.logic.commands;

import com.google.common.collect.ImmutableList;
import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.events.ui.ExpandCollapseTaskEvent;
import seedu.todo.commons.events.ui.HighlightTaskEvent;
import seedu.todo.commons.events.ui.ShowTagsEvent;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.IntArgument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.arguments.StringArgument;

import java.util.List;

//@@author A0135805H
/**
 * This class handles all tagging command
 */
public class TagCommand extends BaseCommand {

    /* Constants */
    private static final String ERROR_INCOMPLETE_PARAMETERS
            = "The tag command is unable to recognise your commands.";
    private static final String ERROR_TOO_MANY_PARAMETERS
            = "You have supplied too many parameters.";
    private static final String ERROR_INPUT_INDEX_REQUIRED
            = "A task index is required.";
    private static final String ERROR_INPUT_ADD_TAGS_REQUIRED
            = "A list of tags \"tag1, tag2, ...\" to add is required.";
    private static final String ERROR_INPUT_DELETE_TAGS_REQUIRED
            = "A list of tags \"tag1, tag2, ...\" to delete is required.";
    private static final String ERROR_INPUT_RENAME_TAGS_REQUIRED
            = "Please provide an existing tag name and a new tag name.";
    private static final String ERROR_TWO_PARAMS
            = "You may only provide an existing tag name, and a new tag name for renaming.";

    private static final String SUCCESS_ADD_TAGS = " - tagged successfully";
    private static final String SUCCESS_DELETE_TAGS = " - removed successfully";
    private static final String SUCCESS_RENAME_TAGS = " renamed to ";

    private static final String DESCRIPTION_SHOW_TAGS = "Shows a global list of tags";
    private static final String DESCRIPTION_ADD_TAGS = "Add tags to a task";
    private static final String DESCRIPTION_DELETE_TAGS_TASK = "Delete tags from tasks";
    private static final String DESCRIPTION_DELETE_TAGS = "Delete tags from all tasks";
    private static final String DESCRIPTION_RENAME_TAGS = "Rename a tag";

    private static final String ARGUMENTS_SHOW_TAGS = "";
    private static final String ARGUMENTS_ADD_TAGS = "index tag1 [, tag2, ...]";
    private static final String ARGUMENTS_DELETE_TAGS_TASK = "index /d tag1 [, tag2, ...]";
    private static final String ARGUMENTS_DELETE_TAGS = "/d tag1 [, tag2, ...]";
    private static final String ARGUMENTS_RENAME_TAGS = "/r old_tag_name new_tag_name";

    /* Variables */
    private Argument<Integer> index = new IntArgument("index");

    private Argument<String> addTags = new StringArgument("add");

    private Argument<String> deleteTags = new StringArgument("delete")
            .flag("d");

    private Argument<String> renameTag = new StringArgument("rename")
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
            new CommandSummary(DESCRIPTION_DELETE_TAGS_TASK, getCommandName(), ARGUMENTS_DELETE_TAGS_TASK),
            new CommandSummary(DESCRIPTION_DELETE_TAGS, getCommandName(), ARGUMENTS_DELETE_TAGS),
            new CommandSummary(DESCRIPTION_RENAME_TAGS, getCommandName(), ARGUMENTS_RENAME_TAGS)
        );
    }

    @Override
    protected void validateArguments() {

        if (!isInputParameterSufficient()) {
            handleUnavailableInputParameters();
        }

        if (isRenamingTag()) {
            //Check arguments for rename tags case
            checkForTwoParams(renameTag.getName(), renameTag.getValue());
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
        CommandResult result = new CommandResult();

        //Performs the actual execution with the data
        if (isShowTags()) {
            ShowTagsEvent tagsEvent = new ShowTagsEvent(model.getGlobalTagsList());
            EventsCenter.getInstance().post(tagsEvent);

        } else if (isAddTagsToTask()) {
            model.addTagsToTask(displayedIndex, tagsToAdd);
            result = new CommandResult(StringUtil.convertListToString(tagsToAdd) + SUCCESS_ADD_TAGS);

        } else if (isDeleteTagsFromTask()) {
            model.deleteTagsFromTask(displayedIndex, tagsToDelete);
            result = new CommandResult(StringUtil.convertListToString(tagsToDelete) + SUCCESS_DELETE_TAGS);

        } else if (isDeleteTagsFromAllTasks()) {
            model.deleteTags(tagsToDelete);
            result = new CommandResult(StringUtil.convertListToString(tagsToDelete) + SUCCESS_DELETE_TAGS);

        } else if (isRenamingTag()) {
            model.renameTag(renameTagsParam[0], renameTagsParam[1]);
            result = new CommandResult(renameTagsParam[0] + SUCCESS_RENAME_TAGS + renameTagsParam[1]);

        } else {
            //Invalid case, should not happen, as we have checked it validateArguments.
            //However, for completeness, a command result is returned.
            throw new ValidationException(ERROR_INCOMPLETE_PARAMETERS);
        }

        if (displayedIndex != null) {
            eventBus.post(new HighlightTaskEvent(model.getTask(displayedIndex)));
            eventBus.post(new ExpandCollapseTaskEvent(model.getTask(displayedIndex)));
        }
        return result;
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
     * Checks if the input parameters are sufficient, by checking if it matches any of the input case.
     */
    private boolean isInputParameterSufficient() {
        return getNumberOfTruth(isShowTags(), isAddTagsToTask(), isDeleteTagsFromTask(),
                isDeleteTagsFromAllTasks(), isRenamingTag()) == 1;
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
            errors.put(ERROR_TOO_MANY_PARAMETERS);

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
     * Check if the command parameters contain exactly 2 items
     */
    private void checkForTwoParams(String argumentName, String paramString) {
        String[] params = StringUtil.splitString(paramString);
        if (params != null && params.length != 2) {
            errors.put(argumentName, ERROR_TWO_PARAMS);
        }
    }

    /* Helper Methods */
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