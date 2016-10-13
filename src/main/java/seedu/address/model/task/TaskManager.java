package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;

public class TaskManager {
    private static final String NULL_ENTRY = "";
    
    public TaskManager() {
    }

    public static Task mapUnaffectedParams(ReadOnlyTask oldTask, Task newParams) {
        Task newTask = null;
        try {
            newTask = new Task(
                    updateTaskName(oldTask, newParams),
                    updateDueDate(oldTask, newParams),
                    updatePriority(oldTask, newParams),
                    updateReminder(oldTask, newParams),
                    updateTags(oldTask, newParams)
                    );
        } catch (IllegalValueException ive) {
            assert false : "There should not be any illegal value at this point";
        }
        
        return newTask;
    }

    private static TaskName updateTaskName(ReadOnlyTask oldTask, Task newParams) throws IllegalValueException {
        TaskName newTaskName;

        if (newParams.getName().toString().equals(NULL_ENTRY)) {
            newTaskName = new TaskName(oldTask.getName().toString());
        } else {
            newTaskName = new TaskName(newParams.getName().toString());
        }

        return newTaskName;
    }

    private static DueDate updateDueDate(ReadOnlyTask oldTask, Task newParams) throws IllegalValueException {
        DueDate newDueDate;

        if (newParams.getDueDate().toString().equals(NULL_ENTRY)) {
            newDueDate = new DueDate(oldTask.getDueDate().toString());
        } else {
            newDueDate = new DueDate(newParams.getDueDate().toString());
        }

        return newDueDate;
    }

    private static Priority updatePriority(ReadOnlyTask oldTask, Task newParams) throws IllegalValueException {
        Priority newPriority;

        if (newParams.getPriority().toString().equals(NULL_ENTRY)) {
            newPriority = new Priority(oldTask.getPriority().toString());
        } else {
            newPriority = new Priority(newParams.getPriority().toString());
        }

        return newPriority;
    }

    private static Reminder updateReminder(ReadOnlyTask oldTask, Task newParams) throws IllegalValueException {
        Reminder newReminder;

        if (newParams.getReminder().toString().equals(NULL_ENTRY)) {
            newReminder = new Reminder(oldTask.getReminder().toString());
        } else {
            newReminder = new Reminder(newParams.getReminder().toString());
        }

        return newReminder;
    }

    private static UniqueTagList updateTags(ReadOnlyTask oldTask, Task newParams) {
        UniqueTagList newTags = new UniqueTagList(oldTask.getTags());

        for (Tag toAdd : newParams.getTags()) {
            try {
                newTags.add(toAdd);
            } catch (DuplicateTagException e) {
                continue;
            }
        }

        return newTags;
    }
}
