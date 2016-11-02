package tars.logic.commands;

import java.time.DateTimeException;
import java.util.HashSet;
import java.util.Set;

import tars.commons.core.Messages;
import tars.commons.core.UnmodifiableObservableList;
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.util.DateTimeUtil;
import tars.commons.util.StringUtil;
import tars.logic.parser.ArgumentTokenizer;
import tars.logic.parser.Prefix;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList;
import tars.model.tag.UniqueTagList.DuplicateTagException;
import tars.model.tag.UniqueTagList.TagNotFoundException;
import tars.model.task.DateTime;
import tars.model.task.DateTime.IllegalDateException;
import tars.model.task.Name;
import tars.model.task.Priority;
import tars.model.task.ReadOnlyTask;
import tars.model.task.Task;

/**
 * Edits a task identified using it's last displayed index from tars.
 * 
 * @@author A0121533W
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits any component of a particular task.\n"
            + "Parameters: <INDEX> [/n TASK_NAME] [/dt DATETIME] [/p PRIORITY] "
            + "[/ta TAG_TO_ADD ...] [/tr TAG_TO_REMOVE ...]\n" + "Example: " + COMMAND_WORD
            + " 1 /n Lunch with John /dt 10/09/2016 1200 to 10/09/2016 1300 /p l /ta lunch /tr dinner";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s";

    public static final String MESSAGE_UNDO = "Edited to %1$s to %1$s";
    public static final String MESSAGE_REDO = "Edited to %1$s to %1$s";
    
    private static final int DATETIME_INDEX_OF_ENDDATE = 1;
    private static final int DATETIME_INDEX_OF_STARTDATE = 0;

    public final int targetIndex;
    private ReadOnlyTask toBeReplacedTask;
    private Task editedTask;
    private ArgumentTokenizer argsTokenizer;

    private static final Prefix NAME_PREFIX = new Prefix("/n");
    private static final Prefix DATETIME_PREFIX = new Prefix("/dt");
    private static final Prefix PRIORITY_PREFIX = new Prefix("/p");
    private static final Prefix ADD_TAG_PREFIX = new Prefix("/ta");
    private static final Prefix REMOVE_TAG_PREFIX = new Prefix("/tr");

    /**
     * Convenience constructor using raw values.
     */
    public EditCommand(int targetIndex, ArgumentTokenizer argsTokenizer) {
        this.targetIndex = targetIndex;
        this.argsTokenizer = argsTokenizer;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList =
                model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(
                    Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        toBeReplacedTask = lastShownList.get(targetIndex - 1);
        editedTask = new Task(toBeReplacedTask);

        try {
            updateTask();
            model.replaceTask(toBeReplacedTask, editedTask);
            model.getUndoableCmdHist().push(this);
            return new CommandResult(
                    String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
        } catch (DateTimeException dte) {
            return new CommandResult(Messages.MESSAGE_INVALID_DATE);
        } catch (IllegalValueException | TagNotFoundException e) {
            return new CommandResult(e.getMessage());
        }
    }
    
    /**
     * Update task if there is a change
     * 
     * @@author A0139924W
     * @throws IllegalValueException
     * @throws TagNotFoundException
     */
    private void updateTask() throws IllegalValueException, TagNotFoundException {
        updateNameIfChanged();
        updatePriorityIfChanged();
        updateDateTimeIfChanged();
        addTagsIfFound();
        deleteTagsIfFound();
    }

    /**
     * Update the name field if there is a change
     * 
     * @throws IllegalValueException
     */
    private void updateNameIfChanged() throws IllegalValueException {
        if (isFieldChanged(NAME_PREFIX)) {
            Name editedName =
                    new Name(argsTokenizer.getValue(NAME_PREFIX).get());
            editedTask.setName(editedName);
        }
    }

    /**
     * Update the priority if there is a change
     * 
     * @throws IllegalValueException
     */
    private void updatePriorityIfChanged() throws IllegalValueException {
        if (isFieldChanged(PRIORITY_PREFIX)) {
            Priority editedPriority =
                    new Priority(argsTokenizer.getValue(PRIORITY_PREFIX).get());
            editedTask.setPriority(editedPriority);
        }
    }

    /**
     * Update the date time if there is a change
     * 
     * @throws IllegalDateException
     */
    private void updateDateTimeIfChanged() throws IllegalDateException {
        if (isFieldChanged(DATETIME_PREFIX)) {
            String[] dateTimeArray = DateTimeUtil.parseStringToDateTime(
                    argsTokenizer.getValue(DATETIME_PREFIX).get());
            DateTime editedDateTime =
                    new DateTime(dateTimeArray[DATETIME_INDEX_OF_STARTDATE],
                            dateTimeArray[DATETIME_INDEX_OF_ENDDATE]);
            editedTask.setDateTime(editedDateTime);
        }
    }
    
    /**
     * Add tag if there is a change
     * 
     * @throws IllegalValueException
     * @throws DuplicateTagException
     * @throws TagNotFoundException
     */
    private void addTagsIfFound() throws IllegalValueException, DuplicateTagException,
            TagNotFoundException {
        Set<String> tagsToAdd = argsTokenizer.getMultipleValues(ADD_TAG_PREFIX)
                .orElse(new HashSet<>());
        updateTagList(ADD_TAG_PREFIX, tagsToAdd);
    }
    
    /**
     * Remove tag if there is a change
     * 
     * @throws IllegalValueException
     * @throws DuplicateTagException
     * @throws TagNotFoundException
     */
    private void deleteTagsIfFound() throws IllegalValueException, DuplicateTagException,
            TagNotFoundException {
        Set<String> tagsToAdd = argsTokenizer.getMultipleValues(REMOVE_TAG_PREFIX)
                .orElse(new HashSet<>());
        updateTagList(REMOVE_TAG_PREFIX, tagsToAdd);
    }
    
    /**
     * Update tag list
     * 
     * @throws IllegalValueException
     * @throws TagNotFoundException
     */
    private void updateTagList(Prefix mutatorPrefix, Set<String> mutateTagNames)
            throws IllegalValueException, TagNotFoundException {
        UniqueTagList replacement = editedTask.getTags();

        for (String mutateTagName : mutateTagNames) {
            Tag mutateTag = new Tag(mutateTagName);
            
            if (ADD_TAG_PREFIX.equals(mutatorPrefix)) {
                replacement.add(mutateTag);
            }
            
            if (REMOVE_TAG_PREFIX.equals(mutatorPrefix)) {
                replacement.remove(mutateTag);
            }
        }

        editedTask.setTags(replacement);
    }

    /**
     * Checks if the field need to be updated
     * 
     * @@author A0139924W
     * @return true if the field need update
     */
    private boolean isFieldChanged(Prefix prefix) {
        return !argsTokenizer.getValue(prefix).orElse(StringUtil.EMPTY_STRING)
                .equals(StringUtil.EMPTY_STRING);
    }

    // @@author A0139924W
    @Override
    public CommandResult undo() {
        assert model != null;
        try {
            model.replaceTask(editedTask, new Task(toBeReplacedTask));
            return new CommandResult(String.format(UndoCommand.MESSAGE_SUCCESS,
                    String.format(MESSAGE_UNDO, toBeReplacedTask)));
        } catch (DuplicateTaskException e) {
            return new CommandResult(
                    String.format(UndoCommand.MESSAGE_UNSUCCESS,
                            Messages.MESSAGE_DUPLICATE_TASK));
        }
    }

    // @@author A0139924W
    @Override
    public CommandResult redo() {
        assert model != null;
        try {
            model.replaceTask(toBeReplacedTask, editedTask);
            return new CommandResult(String.format(RedoCommand.MESSAGE_SUCCESS,
                    String.format(MESSAGE_REDO, toBeReplacedTask)));
        } catch (DuplicateTaskException e) {
            return new CommandResult(String
                    .format(RedoCommand.MESSAGE_UNSUCCESS, e.getMessage()));
        }
    }
}
