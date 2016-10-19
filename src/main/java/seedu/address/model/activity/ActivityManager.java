package seedu.address.model.activity;

import java.util.Calendar;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.task.DueDate;
import seedu.address.model.activity.task.Priority;
import seedu.address.model.activity.task.Task;
import seedu.address.model.activity.event.EndTime;
import seedu.address.model.activity.event.Event;
import seedu.address.model.activity.event.StartTime;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;

public class ActivityManager {
    private static final String NULL_ENTRY = "";
    
    public static Activity editUnaffectedParams(Activity oldTask, Activity newParams, String type) {
        Activity newTask = null;
        String oldTaskType = oldTask.getClass().getSimpleName().toLowerCase();
        String newParamsType = newParams.getClass().getSimpleName().toLowerCase();
        try {
            switch (oldTaskType) {
            case "activity":
                if (newParamsType.equals("task")) {
                    newTask = new Task(
                            updateTaskName(oldTask, newParams, type),
                            updateDueDate(oldTask, newParams, type),
                            updatePriority(oldTask, newParams, type),
                            updateReminder(oldTask, newParams, type),
                            updateTags(oldTask, newParams)
                            );
                } else if (newParamsType.equals("event")) {
                    newTask = new Event(
                            updateTaskName(oldTask, newParams, type),
                            updateStartTime(oldTask, newParams, type),
                            updateEndTime(oldTask, newParams, type),
                            updateReminder(oldTask, newParams, type),
                            updateTags(oldTask, newParams)
                            );
                } else {
                    newTask = new Activity(
                            updateTaskName(oldTask, newParams, type),
                            updateReminder(oldTask, newParams, type),
                            updateTags(oldTask, newParams)
                            );
                }
                
                newTask.setCompletionStatus(oldTask.getCompletionStatus());
                break;
            case "task":
                newTask = new Task(
                        updateTaskName(oldTask, newParams, type),
                        updateDueDate((Task) oldTask, newParams, type),
                        updatePriority((Task) oldTask, newParams, type),
                        updateReminder(oldTask, newParams, type),
                        updateTags(oldTask, newParams)
                        );
                newTask.setCompletionStatus(oldTask.getCompletionStatus());
                break;
            case "event":
                break;
            default:
                assert false : "Invalid class type";
            }
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
        
        if (!newParams.getClass().getSimpleName().equalsIgnoreCase("task")) {
            return new DueDate(((Task) oldTask).getDueDate().getCalendarValue());
        }

        if (((Task) newParams).getDueDate().toString().equals(NULL_ENTRY)&& type == "edit") {
            if (oldTask.getClass().getSimpleName().equalsIgnoreCase("activity")) {
                newDueDate = new DueDate("");
            } else {
                newDueDate = new DueDate(((Task) oldTask).getDueDate().getCalendarValue());
            }
        } else {
            newDueDate = new DueDate(((Task) newParams).getDueDate().getCalendarValue());
        }

        return newDueDate;
    }

    private static Priority updatePriority(Activity oldTask, Activity newParams, String type) throws IllegalValueException {
        Priority newPriority;
        
        if (!newParams.getClass().getSimpleName().equalsIgnoreCase("task")) {
            return new Priority(((Task) oldTask).getPriority().toString());
        }

        if (((Task) newParams).getPriority().toString().equals(NULL_ENTRY)&& type == "edit") {
            if (oldTask.getClass().getSimpleName().equalsIgnoreCase("activity")) {
                newPriority = new Priority("0");
            } else {
                newPriority = new Priority(((Task) oldTask).getPriority().toString());
            }
        } else {
            newPriority = new Priority(((Task) newParams).getPriority().toString());
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
    
    //Handle wrong time format in event instead of here?

    private static StartTime updateStartTime(Activity oldTask, Activity newParams, String type) throws IllegalValueException {
        StartTime newStartTime;
        
        if (!newParams.getClass().getSimpleName().equalsIgnoreCase("event")) {
            return new StartTime(((Event) oldTask).getStartTime().getCalendarValue());
        }

        if (((Event) newParams).getStartTime().toString().equals(NULL_ENTRY)&& type == "edit") {
            if (oldTask.getClass().getSimpleName().equalsIgnoreCase("activity")) {
                newStartTime = new StartTime("");
            } else {
                newStartTime = new StartTime(((Event) oldTask).getStartTime().getCalendarValue());
            }
        } else {
            newStartTime = new StartTime(((Event) newParams).getStartTime().getCalendarValue());
        }

        return newStartTime;
    }

    private static EndTime updateEndTime(Activity oldTask, Activity newParams, String type) throws IllegalValueException {
        EndTime newEndTime;
        
        if (!newParams.getClass().getSimpleName().equalsIgnoreCase("event")) {
            return new EndTime(((Event) oldTask).getEndTime().getCalendarValue());
        }

        if (((Event) newParams).getEndTime().toString().equals(NULL_ENTRY)&& type == "edit") {
            if (oldTask.getClass().getSimpleName().equalsIgnoreCase("activity")) {
                newEndTime = new EndTime("20-10-2016 1200", ""); //what to put as starttime?
            } else {
                newEndTime = new EndTime(((Event) oldTask).getEndTime().getCalendarValue());
            }
        } else {
            newEndTime = new EndTime(((Event) newParams).getEndTime().getCalendarValue());
        }

        return newEndTime;
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
