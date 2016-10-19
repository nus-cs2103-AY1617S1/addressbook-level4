package seedu.address.model.activity;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.model.task.DueDate;
import seedu.address.model.task.Priority;

public class ActivityManager {
    private static final String NULL_ENTRY = "";
    
    public static Activity editUnaffectedParams(Activity oldTask, Activity newParams, String type) {
        Activity newTask = null;
        try {
            newTask = new Activity(
                    updateTaskName(oldTask, newParams, type),
                    updateDueDate(oldTask, newParams, type),
                    updatePriority(oldTask, newParams, type),
                    updateReminder(oldTask, newParams, type),
                    updateTags(oldTask, newParams)
                    );
            newTask.setCompletionStatus(oldTask.getCompletionStatus());
        } catch (IllegalValueException ive) {
            assert false : "There should not be any illegal value at this point";
        }
        
        return newTask;
    }

	public static void marksTask(Activity task, boolean isComplete) {
		task.setCompletionStatus(isComplete);
	}

	private static Name updateTaskName(Activity oldTask, Activity newParams, String type) throws IllegalValueException {
        Name newTaskName;

        if (newParams.getName().toString().equals(NULL_ENTRY)&& type == "edit") {
            newTaskName = new Name(oldTask.getName().toString());
        } else {
            newTaskName = new Name(newParams.getName().toString());
        }

        return newTaskName;
    }

    private static DueDate updateDueDate(Activity oldTask, Activity newParams, String type) throws IllegalValueException {
        DueDate newDueDate;

        if (newParams.getDueDate().toString().equals(NULL_ENTRY)&& type == "edit") {
            newDueDate = new DueDate(oldTask.getDueDate().getCalendarValue());
        } else {
            newDueDate = new DueDate(newParams.getDueDate().getCalendarValue());
        }

        return newDueDate;
    }

    private static Priority updatePriority(Activity oldTask, Activity newParams, String type) throws IllegalValueException {
        Priority newPriority;

        if (newParams.getPriority().toString().equals(NULL_ENTRY)&& type == "edit") {
            newPriority = new Priority(oldTask.getPriority().toString());
        } else {
            newPriority = new Priority(newParams.getPriority().toString());
        }

        return newPriority;
    }

    private static Reminder updateReminder(Activity oldTask, Activity newParams, String type) throws IllegalValueException {
        Reminder newReminder;

        if (newParams.getReminder().toString().equals(NULL_ENTRY)&& type == "edit") {
            newReminder = new Reminder(oldTask.getReminder().getCalendarValue());
        } else {
            newReminder = new Reminder(newParams.getReminder().getCalendarValue());
        }

        return newReminder;
    }
   
    private static UniqueTagList updateTags(Activity oldTask, Activity newParams) {
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
