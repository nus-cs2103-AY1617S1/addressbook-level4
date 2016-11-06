package seedu.task.logic.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.RollBackCommand;
import seedu.task.logic.parser.*;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.EndTime;
import seedu.task.model.task.Deadline;
import seedu.task.model.task.Name;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.StartTime;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task from the task manager.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_WORD_ALT = "e";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX TASKNAME at START_TIME to END_TIME [by DEADLINE] [#TAG...]\n" + "Example: "
            + COMMAND_WORD + " 4 tag, school";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edit Task: %1$s";

    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
    public static final String EDIT_NAME = "name";
    public static final String EDIT_START_TIME = "starttime";
    public static final String EDIT_END_TIME = "endtime";
    public static final String EDIT_DEADLINE = "deadline";
    public static final String EDIT_TAG = "tag";
    public final int targetIndex;
    private final String toEdit;
    private final String toEditItem;
    private final Set<String> toEditTags;

    // @@author A0152958R
    public EditCommand(int targetIndex, String item, String editResult, Set<String> tags) throws IllegalValueException {
        this.targetIndex = targetIndex;
        this.toEdit = editResult;
        this.toEditItem = item;
        this.toEditTags = tags;

    }

    // @@author A0152958R
    @Override
    public CommandResult execute(boolean isUndo) {
        assert model != null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask currentTask = lastShownList.get(targetIndex - 1);
        ReadOnlyTask editedTask = null;
        Task toAdd = null;
        final Set<Tag> tagSet = new HashSet<>();
        switch (this.toEditItem) {
        case EDIT_NAME:
            try {
                toAdd = new Task(new Name(this.toEdit), currentTask.getStartTime(), currentTask.getEndTime(),
                        currentTask.getDeadline(), currentTask.getTags(), currentTask.getStatus(),
                        currentTask.getRecurring());
            } catch (IllegalValueException e) {
                return new CommandResult(Name.MESSAGE_NAME_CONSTRAINTS);
            }
            break;
        case EDIT_START_TIME:
            try {
                toAdd = new Task(currentTask.getName(), new StartTime(this.toEdit), currentTask.getEndTime(),
                        currentTask.getDeadline(), currentTask.getTags(), currentTask.getStatus(),
                        currentTask.getRecurring());
            } catch (IllegalValueException e) {
                return new CommandResult(StartTime.MESSAGE_STARTTIME_CONSTRAINTS);
            }
            break;
        case EDIT_END_TIME:
            TimeParser endTime = new TimeParser();
            String parserString = "from " + currentTask.getStartTime().toString() + " to " + this.toEdit;
            TimeParserResult end = endTime.parseTime(parserString);
            if (end == null) {
                return new CommandResult(Messages.MESSAGE_INVALID_TIME_INTERVAL);
            } else if (end != null) {
                try {
                    toAdd = new Task(currentTask.getName(), currentTask.getStartTime(), new EndTime(this.toEdit),
                            currentTask.getDeadline(), currentTask.getTags(), currentTask.getStatus(),
                            currentTask.getRecurring());
                } catch (IllegalValueException e) {
                    return new CommandResult(EndTime.MESSAGE_ENDTIME_CONSTRAINTS);
                }
            }
            break;
        case EDIT_DEADLINE:
            try {
                toAdd = new Task(currentTask.getName(), currentTask.getStartTime(), currentTask.getEndTime(),
                        new Deadline(this.toEdit), currentTask.getTags(), currentTask.getStatus(),
                        currentTask.getRecurring());
            } catch (IllegalValueException e) {
                return new CommandResult(Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
            }
            break;
        case EDIT_TAG:
            try {
                for (String tagName : this.toEditTags) {
                    tagSet.add(new Tag(tagName));
                }
                toAdd = new Task(currentTask.getName(), currentTask.getStartTime(), currentTask.getEndTime(),
                        currentTask.getDeadline(), new UniqueTagList(tagSet), currentTask.getStatus(),
                        currentTask.getRecurring());
            } catch (IllegalValueException e) {
                return new CommandResult(Tag.MESSAGE_TAG_CONSTRAINTS);
            }
            break;
        default:
            break;
        }

        try {
            model.addTask(targetIndex, toAdd);
            editedTask = lastShownList.get(targetIndex);
        } catch (UniqueTaskList.DuplicateTaskException e) {
            try {
                model.deleteTask(editedTask);
            } catch (TaskNotFoundException tnfe) {
                assert false : "The target task cannot be missing";
            }
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

        try {
            model.deleteTask(currentTask);
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        }

        if (!isUndo) {
            getUndoList().add(new RollBackCommand(COMMAND_WORD, toAdd, (Task) currentTask));
        }
        // @@author A0147944U
        // Sorts updated list of tasks
        model.autoSortBasedOnCurrentSortPreference();
        // @@author A0152958R
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, toEdit));
    }

    // @@author A0147335E
    private ArrayList<RollBackCommand> getUndoList() {
        return history.getUndoList();
    }

}
