package seedu.todo.logic.commands;

import com.google.common.collect.ImmutableList;

import seedu.todo.commons.events.ui.HighlightTaskEvent;
import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.events.ui.ExpandCollapseTaskEvent;
import seedu.todo.commons.events.ui.ShowTagsEvent;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.IntArgument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.arguments.StringArgument;
import seedu.todo.model.task.ImmutableTask;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

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

    private static final String DESCRIPTION_SHOW_TAGS = "Shows all tags";
    private static final String DESCRIPTION_ADD_TAGS = "Add tags to a task";
    private static final String DESCRIPTION_DELETE_TAGS_TASK = "Remove tags from a task";
    private static final String DESCRIPTION_DELETE_TAGS = "Remove tags from all tasks";
    private static final String DESCRIPTION_RENAME_TAGS = "Rename a tag";

    private static final int INDEX_OFFSET = 1;
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

    /* Methods to get command argument for add tags */
    /**
     * Partitions two flag-less parameters index and list of tags by overriding this method.
     */
    @Override
    protected void setPositionalArgument(String argument) {
        String[] tokens = argument.trim().split(" ", 2);
        getPossibleIndexForAddCommand(tokens[0]);
        if (tokens.length == 2) {
            getPossibleTagsForAddCommand(tokens[1]);
        }
    }

    /**
     * If the input parameter resembles the add command with an index, extracts the index of the add command
     * input.
     */
    private void getPossibleIndexForAddCommand(String argument) {
        boolean isFirstArgNumber = StringUtil.isUnsignedInteger(argument);
        if (isFirstArgNumber) {
            try {
                index.setValue(argument);
            } catch (IllegalValueException e) {
                errors.put(index.getName(), e.getMessage());
            }
        }
    }

    /**
     * If the input parameter resembles the add command with tag names, extracts the tag names of the add
     * command input.
     */
    private void getPossibleTagsForAddCommand(String argument) {
        try {
            addTags.setValue(argument);
        } catch (IllegalValueException e) {
            errors.put(addTags.getName(), e.getMessage());
        }
    }

    /* Remaining Override Methods */
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
        validateRenameParams();
        super.validateArguments();
    }

    @Override
    public CommandResult execute() throws ValidationException {
        //Performs the actual execution with the data. Guarantees that only one action gets to run.
        guaranteeOneActionExecutes();

        CommandResult result = chooseFirstAvailable(
                performShowTagsWhenApplicable(),
                performAddTagToTaskWhenApplicable(),
                performDeleteTagsFromTaskWhenApplicable(),
                performDeleteTagsGloballyWhenApplicable(),
                performRenameTagWhenApplicable());

        guaranteeCommandResultExists(result); //Just a sanity check.

        highlightTaskWhenComplete();
        return result;
    }

    /* User Interface Helper Methods */
    /**
     * Highlights the task when a task index is available.
     * @throws ValidationException when the index is invalid.
     */
    private void highlightTaskWhenComplete() throws ValidationException {
        if (index.hasBoundValue()) {
            int displayedIndex = index.getValue();
            eventBus.post(new HighlightTaskEvent(model.getTask(displayedIndex)));
            eventBus.post(new ExpandCollapseTaskEvent(model.getTask(displayedIndex)));
        }
    }

    /* Show Tags Methods */
    /**
     * Returns true if the command parameters matches the action of show global tags
     */
    private boolean isShowTags() {
        return !index.hasBoundValue() && !addTags.hasBoundValue() && !deleteTags.hasBoundValue()
                && !renameTag.hasBoundValue();
    }

    /**
     * Performs the following execution if the command indicates so:
     *      Show a global list of tags
     */
    private CommandResult performShowTagsWhenApplicable() {
        if (isShowTags()){
            ShowTagsEvent tagsEvent = new ShowTagsEvent(model.getGlobalTagsList());
            EventsCenter.getInstance().post(tagsEvent);
            return new CommandResult("Type [Enter] to dismiss.");
        }
        return null;
    }

    /* Add Tags to Task Method */
    /**
     * Returns true if the command parameters matches the action of adding tag(s) to a task.
     */
    private boolean isAddTagsToTask() {
        return index.hasBoundValue() && addTags.hasBoundValue() && !deleteTags.hasBoundValue()
                && !renameTag.hasBoundValue();
    }

    /**
     * Performs the following execution if the command indicates so:
     *      Adds Tags to a particular Task
     */
    private CommandResult performAddTagToTaskWhenApplicable() throws ValidationException {
        Integer displayedIndex = index.getValue();
        String[] tagsToAdd = StringUtil.splitString(addTags.getValue());

        if (isAddTagsToTask()) {
            model.addTagsToTask(displayedIndex, tagsToAdd);
            ImmutableTask task = model.getObservableList().get(displayedIndex - INDEX_OFFSET);
            eventBus.post(new HighlightTaskEvent(task));
            return new CommandResult(StringUtil.convertListToString(tagsToAdd) + SUCCESS_ADD_TAGS);
        }
        return null;
    }

    /* Delete Tags from Task */
    /**
     * Returns true if the command parameters matches the action of deleting tag(s) from a task.
     */
    private boolean isDeleteTagsFromTask() {
        return index.hasBoundValue() && !addTags.hasBoundValue() && deleteTags.hasBoundValue()
                && !renameTag.hasBoundValue();
    }

    /**
     * Performs the following execution if the command indicates so:
     *      Deletes tags from a task.
     */
    private CommandResult performDeleteTagsFromTaskWhenApplicable() throws ValidationException {
        Integer displayedIndex = index.getValue();
        String[] tagsToDelete = StringUtil.splitString(deleteTags.getValue());

        if (isDeleteTagsFromTask()) {
            model.deleteTagsFromTask(displayedIndex, tagsToDelete);
            return new CommandResult(StringUtil.convertListToString(tagsToDelete) + SUCCESS_DELETE_TAGS);
        }
        return null;
    }

    /* Delete Tags from All Tasks */
    /**
     * Returns true if the command parameters matches the action of deleting tag(s) from all tasks.
     */
    private boolean isDeleteTagsFromAllTasks() {
        return !index.hasBoundValue() && !addTags.hasBoundValue() && deleteTags.hasBoundValue()
                && !renameTag.hasBoundValue();
    }

    /**
     * Performs the following execution if the command indicates so:
     *      Deletes tags from all tasks.
     */
    private CommandResult performDeleteTagsGloballyWhenApplicable() throws ValidationException {
        String[] tagsToDelete = StringUtil.splitString(deleteTags.getValue());

        if (isDeleteTagsFromAllTasks()) {
            model.deleteTags(tagsToDelete);
            return new CommandResult(StringUtil.convertListToString(tagsToDelete) + SUCCESS_DELETE_TAGS);
        }
        return null;
    }

    /* Renaming Tags */
    /**
     * Returns true if the command parameters matches the action of renaming tags.
     */
    private boolean isRenamingTag() {
        return !index.hasBoundValue() && !addTags.hasBoundValue() && !deleteTags.hasBoundValue()
                && renameTag.hasBoundValue();
    }

    /**
     * Performs validation to rename params (check for sufficient parameters to rename).
     */
    private void validateRenameParams() {
        if (isRenamingTag()) {
            String[] params = StringUtil.splitString(renameTag.getValue());
            if (params != null && params.length != 2) {
                errors.put(renameTag.getName(), ERROR_TWO_PARAMS);
            }
        }
    }

    /**
     * Performs the following execution if the command indicates so:
     *      Rename a specific tag.
     */
    private CommandResult performRenameTagWhenApplicable() throws ValidationException {
        String[] renameTagsParam = StringUtil.splitString(renameTag.getValue());

        if (isRenamingTag()) {
            String oldName = renameTagsParam[0];
            String newName = renameTagsParam[1];
            model.renameTag(oldName, newName);
            return new CommandResult(oldName + SUCCESS_RENAME_TAGS + newName);
        }
        return null;
    }

    /* Command Validation Methods */
    /**
     * Guarantees that only one action gets to run, otherwise throws {@link ValidationException}.
     */
    private void guaranteeOneActionExecutes() throws ValidationException {
        if (!isInputParameterSufficient()) {
            throw new ValidationException(ERROR_INCOMPLETE_PARAMETERS);
        }
    }

    /**
     * Guarantees that result is not null. Otherwise, throw an error {@link ValidationException}.
     */
    private void guaranteeCommandResultExists(CommandResult result) throws ValidationException {
        if (result == null) {
            throw new ValidationException(ERROR_INCOMPLETE_PARAMETERS);
        }
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

        Boolean errorPlaced = chooseFirstTrue(
                handleTooManyParameters(hasAdd, hasDelete, hasRename),
                handleMissingIndex(hasIndex, hasAdd, hasRename),
                handleMissingTagParams(hasIndex, hasAdd, hasDelete, hasRename));

        handleGenericIncompleteParams(errorPlaced);
    }

    /**
     * Sets a generic error for the error bag for {@link #handleUnavailableInputParameters()} if specific
     * error checks did not spot any param errors.
     * @param errorPlaced a boolean value indicated whether an error has been placed already.
     */
    private void handleGenericIncompleteParams(Boolean errorPlaced) {
        if (errorPlaced != null && !errorPlaced) {
            errors.put(ERROR_INCOMPLETE_PARAMETERS);
        }
    }

    /**
     * Sets the error bag {@link #errors} if the parameters expects tags names as parameters but is missing.
     * @return True when error is set.
     */
    private boolean handleMissingTagParams(boolean hasIndex, boolean hasAdd, boolean hasDelete, boolean hasRename) {
        if (hasIndex && !hasAdd && !hasDelete && !hasRename) {
            errors.put(addTags.getName(), ERROR_INPUT_ADD_TAGS_REQUIRED);
            errors.put(deleteTags.getName(), ERROR_INPUT_DELETE_TAGS_REQUIRED);
            errors.put(renameTag.getName(), ERROR_INPUT_RENAME_TAGS_REQUIRED);
            return true;
        }
        return false;
    }

    /**
     * Sets the error bag {@link #errors} if the parameters expects an index but is missing.
     * @return True when error is set.
     */
    private boolean handleMissingIndex(boolean hasIndex, boolean hasAdd, boolean hasRename) {
        if ((hasAdd && !hasIndex) || (hasRename && !hasIndex)) {
            errors.put(index.getName(), ERROR_INPUT_INDEX_REQUIRED);
            return true;
        }
        return false;
    }

    /**
     * Sets the error bag {@link #errors} if the parameters have too many errors.
     * @return True when error is set.
     */
    private boolean handleTooManyParameters(boolean hasAdd, boolean hasDelete, boolean hasRename) {
        if (getNumberOfTruth(hasAdd, hasDelete, hasRename) > 1) {
            errors.put(ERROR_TOO_MANY_PARAMETERS);
            return true;
        }
        return false;
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

    /**
     * Returns the first object that cause the {@code predicate} to be true. Otherwise, return null.
     */
    private static <T> T chooseFirst(Predicate<T> predicate, T... objects) {
        Optional<T> optionalObject = Arrays.stream(objects).filter(predicate).findFirst();
        return optionalObject.isPresent() ? optionalObject.get() : null;
    }

    /**
     * Returns the first object that is available. Otherwise, return null.
     */
    private static <T> T chooseFirstAvailable(T... objects) {
        return chooseFirst(t -> t != null, objects);
    }

    /**
     * Returns the first object that is true. Otherwise, return null.
     */
    private static Boolean chooseFirstTrue(Boolean ... objects) {
        return chooseFirst(aBoolean -> aBoolean != null && aBoolean, objects);
    }
}
