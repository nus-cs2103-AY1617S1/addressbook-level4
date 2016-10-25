package seedu.todo.logic.commands;

import com.google.common.collect.ImmutableList;
import org.ocpsoft.prettytime.shade.org.apache.commons.lang.BooleanUtils;
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
    private static final String VERB = "tagged";

    private static final String ERROR_INCOMPLETE_PARAMETERS = "You have not supplied sufficient parameters to run a Tag command.";
    private static final String ERROR_INPUT_INDEX_REQUIRED = "A task index is required.";
    private static final String ERROR_INPUT_ADD_TAGS_REQUIRED = "A list of tags \"tag1, tag2, ...\" to add is required.";
    private static final String ERROR_INPUT_DELETE_TAGS_REQUIRED = "A list of tags \"tag1, tag2, ...\" to delete is required.";

    private static final String SUCCESS_ADD_TAGS = " tags have been added successfully.";
    private static final String SUCCESS_DELETE_TAGS = " tags have been removed successfully.";

    /* Variables */
    private Argument<Integer> index = new IntArgument("index");

    private Argument<String> addTags = new StringArgument("/a")
            .flag("a");

    private Argument<String> deleteTags = new StringArgument("/d")
            .flag("d");

    /* Constructor */
    /**
     * Empty constructor
     */
    public TagCommand() {}

    @Override
    public Parameter[] getArguments() {
        return new Parameter[] {
            index, deleteTags, addTags
        };
    }

    @Override
    protected void setPositionalArgument(String argument) {
        String[] tokens = argument.trim().split(" ", 2);
        boolean isFirstArgNumber = StringUtil.isUnsignedInteger(tokens[0]);

        if (isFirstArgNumber) {
            try {
                index.setValue(tokens[0]);
            } catch (IllegalValueException e) {
                errors.put(index.getName(), e.getMessage());
            }
        }
    }

    @Override
    public String getCommandName() {
        return "tag";
    }

    @Override
    public List<CommandSummary> getCommandSummary() {
        String addNewTagsFromTaskArgument = index.getName() + " /a tag1 [, tag2, ...]";
        String deleteTagsFromTaskArgument = "[" + index.getName() + "] /d tag1 [, tag2, ...]";

        return ImmutableList.of(
            new CommandSummary("Add tags to a task", getCommandName(), addNewTagsFromTaskArgument),
            new CommandSummary("Delete tags from tasks", getCommandName(), deleteTagsFromTaskArgument)
        );
    }

    @Override
    protected void validateArguments() {
        //Check if required combination of inputs are available.
        if (!isInputParametersAvailable()) {
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

        if (isAddTagsToTask()) {
            model.addTagsToTask(displayedIndex, tagsToAdd);
            return new CommandResult(StringUtil.convertListToString(tagsToAdd) + SUCCESS_ADD_TAGS);
        } else if (isDeleteTagsFromTask()) {
            model.deleteTagsFromTask(displayedIndex, tagsToDelete);
            return new CommandResult(StringUtil.convertListToString(tagsToDelete) + SUCCESS_DELETE_TAGS);
        } else {
            //Invalid case, should not happen, as we have checked it validateArguments.
            //However, for completeness, a command result is returned.
            errors.put(ERROR_INCOMPLETE_PARAMETERS);
            return new CommandResult("", errors);
        }
    }

    /* Input Parameters Validation */
    /**
     * Returns true if the command matches the action of adding tag(s) to a task.
     */
    private boolean isAddTagsToTask() {
        boolean isIndexAvailable = index.getValue() != null;
        boolean isTagsToAddAvailable = addTags.getValue() != null;
        return isIndexAvailable && isTagsToAddAvailable;
    }

    /**
     * Returns true if the command matches the action of deleting tag(s) from a task.
     */
    private boolean isDeleteTagsFromTask() {
        boolean isIndexAvailable = index.getValue() != null;
        boolean isTagsToDeleteAvailable = deleteTags.getValue() != null;
        return isIndexAvailable && isTagsToDeleteAvailable;
    }

    /**
     * Returns true if the command matches the action of deleting tag(s) from all tasks.
     */
    private boolean isDeleteTagsFromAllTasks() {
        boolean isIndexAvailable = index.getValue() != null;
        boolean isTagsToDeleteAvailable = deleteTags.getValue() != null;
        return !isIndexAvailable && isTagsToDeleteAvailable;
    }

    /**
     * Returns true if the correct input parameters are available.
     * This method do not check validity of each input.
     */
    private boolean isInputParametersAvailable() {
        boolean isAddTagsToTask = isAddTagsToTask();
        boolean isDeleteTagsFromTask = isDeleteTagsFromTask();
        boolean isDeleteTagsFromAll = isDeleteTagsFromAllTasks();
        return BooleanUtils.xor(new boolean[] {isAddTagsToTask, isDeleteTagsFromTask, isDeleteTagsFromAll});
    }

    /**
     * Sets error messages for insufficient input parameters, dependent on input parameters supplied.
     */
    private void handleUnavailableInputParameters() {
        boolean hasIndex = index.getValue() != null;
        boolean hasAddTags = addTags.getValue() != null;
        boolean hasDeleteTags = deleteTags.getValue() != null;

        //Validation for all inputs.
        if (!hasIndex && !hasAddTags && !hasDeleteTags) {
            errors.put(index.getName(), ERROR_INPUT_INDEX_REQUIRED);
            errors.put(addTags.getName(), ERROR_INPUT_ADD_TAGS_REQUIRED);
            errors.put(deleteTags.getName(), ERROR_INPUT_DELETE_TAGS_REQUIRED);

        } else if (!hasIndex && hasAddTags) {
            errors.put(index.getName(), ERROR_INPUT_INDEX_REQUIRED);

        } else if (hasIndex && !hasAddTags && !hasDeleteTags) {
            errors.put(addTags.getName(), ERROR_INPUT_ADD_TAGS_REQUIRED);
            errors.put(deleteTags.getName(), ERROR_INPUT_DELETE_TAGS_REQUIRED);
        }
    }
}
