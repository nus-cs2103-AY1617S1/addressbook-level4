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
    private static final String SUCCESS_ADD_TAGS = " tags have been added successfully.";

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
        CommandResult result = null;

        if (isAddTagsToTask()) {
            model.addTagsToTask(displayedIndex, tagsToAdd);
            result = new CommandResult(StringUtil.convertListToString(tagsToAdd) + SUCCESS_ADD_TAGS);

        } else if (isDeleteTagsFromTask()) {
            model.deleteTagsFromTask(displayedIndex, tagsToDelete);

        } else {
            //Invalid case, should not happen, as we have checked it validateArguments.
            //However, for completeness, a command result is returned.
            errors.put(ERROR_INCOMPLETE_PARAMETERS);
            result = new CommandResult("", errors);
        }

        return result;
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
        boolean isIndexAvailable = index.getValue() != null;
        boolean isTagsToAddAvailable = addTags.getValue() != null;
        boolean isTagsToDeleteAvailable = deleteTags.getValue() != null;

        String indexRequired = "A task index is required.";
        String addTagsRequired = "A list of tags \"tag1, tag2, ...\" to add is required.";
        String deleteTagsRequired = "A list of tags \"tag1, tag2, ...\" to delete is required.";

        //Validation for all inputs.
        if (!isIndexAvailable && !isTagsToAddAvailable && !isTagsToDeleteAvailable) {
            errors.put(index.getName(), indexRequired);
            errors.put(addTags.getName(), addTagsRequired);
            errors.put(deleteTags.getName(), deleteTagsRequired);
        } else if (!isIndexAvailable && isTagsToAddAvailable) {
            errors.put(index.getName(), indexRequired);
        } else if (isIndexAvailable && !isTagsToAddAvailable && !isTagsToDeleteAvailable) {
            errors.put(addTags.getName(), addTagsRequired);
            errors.put(deleteTags.getName(), deleteTagsRequired);
        }
    }
}
