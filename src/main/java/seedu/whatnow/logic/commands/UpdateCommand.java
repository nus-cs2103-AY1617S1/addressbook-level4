package seedu.whatnow.logic.commands;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.whatnow.commons.core.Messages;
import seedu.whatnow.commons.core.UnmodifiableObservableList;
import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.model.tag.Tag;
import seedu.whatnow.model.tag.UniqueTagList;
import seedu.whatnow.model.task.Name;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.Task;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Update a task with new description/date/time/tag using it's last displayed index from WhatNow.
 */


public class UpdateCommand extends Command {
    
    public static final String COMMAND_WORD = "update";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD 
            + ": Updates the description/date/time/tag of the task identified by the index number used in the last task listing.\n"
            + "Parameters: todo/schedule INDEX (must be a positive integer) description/date/time/tag DESCRIPTION/DATE/TIME/TAG\n"
            + "Example: " + COMMAND_WORD + " todo 1 tag priority low";
    
    public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Updated Task: %1$s";
    
    public final int targetIndex;
    public final String type;
    public final String arg_type;
    public final String arg;
    private Task toUpdate;
    
    public UpdateCommand(String type, int targetIndex, String arg_type, String arg) throws IllegalValueException {
        this.type = type;
        this.targetIndex = targetIndex;
        this.arg_type = arg_type;
        this.arg = arg;
        processArg();
    }
    
    private void processArg() throws IllegalValueException {
        String newName = "a";
        final Set<Tag> tagSet = new HashSet<>();
        if (arg_type.toUpperCase().compareToIgnoreCase("tag") == 0) {
            Set<String> tags = processTag();
            for (String tagName : tags) {
                tagSet.add(new Tag(tagName));
            }
        }
        if (arg_type.toUpperCase().compareToIgnoreCase("description") == 0) {
            newName = arg;
        }
        toUpdate = new Task(
                new Name(newName),
                new UniqueTagList(tagSet),
                null);   
    }
    
    private Set<String> processTag() {
        if (arg.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(arg.split(" "));
        return new HashSet<>(tagStrings);
    }
    
    private void updateTheCorrectField(ReadOnlyTask taskToUpdate) {
        if (arg_type.toUpperCase().compareToIgnoreCase("tag") == 0) {
            toUpdate.setName(taskToUpdate.getName());
        }
        if (arg_type.toUpperCase().compareToIgnoreCase("description") == 0) {
            toUpdate.setTags(taskToUpdate.getTags());
        }
        toUpdate.setStatus(taskToUpdate.getStatus());
    }
    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getCurrentFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUpdate = lastShownList.get(targetIndex - 1);
        updateTheCorrectField(taskToUpdate);
        
        try {
            model.updateTask(taskToUpdate, toUpdate);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, "\nBefore update: " + taskToUpdate + " \nAfter update: " + toUpdate));
    }
}
