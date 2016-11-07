package seedu.lifekeeper.model.activity;

import seedu.lifekeeper.commons.exceptions.IllegalValueException;
import seedu.lifekeeper.model.activity.event.EndTime;
import seedu.lifekeeper.model.activity.event.Event;
import seedu.lifekeeper.model.activity.event.StartTime;
import seedu.lifekeeper.model.activity.task.DueDate;
import seedu.lifekeeper.model.activity.task.Priority;
import seedu.lifekeeper.model.activity.task.Task;
import seedu.lifekeeper.model.tag.Tag;
import seedu.lifekeeper.model.tag.UniqueTagList;
import seedu.lifekeeper.model.tag.UniqueTagList.DuplicateTagException;

//@@author A0125680H
public class ActivityManager {
    private static final String COMMAND_TYPE_UNDO = "undo";
    private static final String COMMAND_TYPE_EDIT = "edit";

    private static final String ENTRY_TYPE_EVENT = "event";
    private static final String ENTRY_TYPE_TASK = "task";
    private static final String ENTRY_TYPE_ACTIVITY = "activity";

    private static final String NULL_ENTRY = "";

    /**
     * Replaces the parameters in the old task to the new parameters specified.
     * 
     * @param oldTask
     *            the old task.
     * @param newParams
     *            the new parameters.
     * @param type
     *            "edit" to edit the old task, or "undo" to revert an edit.
     * @return the updated entry.
     */
    public static Activity editUnaffectedParams(Activity oldTask, Activity newParams, String type) {
        Activity newActivity = null;
        String oldTaskType = oldTask.getClass().getSimpleName().toLowerCase();
        String newParamsType = newParams.getClass().getSimpleName().toLowerCase();

        try {
            switch (oldTaskType) {
            case ENTRY_TYPE_ACTIVITY:
                if (newParamsType.equals(ENTRY_TYPE_TASK)) { // change from activity to task
                    newActivity = updateTask(oldTask, newParams, type, true);
                } else if (newParamsType.equals(ENTRY_TYPE_EVENT)) { // change from activity to event
                    newActivity = updateEvent(oldTask, newParams, type, true);
                } else { // remain as activity
                    newActivity = updateActivity(oldTask, newParams, type);
                }
                
                newActivity.setCompletionStatus(oldTask.getCompletionStatus());
                break;
            case ENTRY_TYPE_TASK:
                if (type.equals(COMMAND_TYPE_EDIT)) {
                    newActivity = updateTask((Task) oldTask, newParams, type, newParamsType.equals(ENTRY_TYPE_TASK));
                } else if (type.equals(COMMAND_TYPE_UNDO)) {
                    newActivity = updateActivity(oldTask, newParams, type);
                }

                newActivity.setCompletionStatus(oldTask.getCompletionStatus());
                break;
            case ENTRY_TYPE_EVENT:
                if (type.equals(COMMAND_TYPE_EDIT)) {
                    newActivity = updateEvent((Event) oldTask, newParams, type, newParamsType.equals(ENTRY_TYPE_EVENT));
                } else if (type.equals(COMMAND_TYPE_UNDO)) {
                    newActivity = updateActivity(oldTask, newParams, type);
                }
                
                break;
            default:
                assert false : "Invalid class type";
            }
        } catch (IllegalValueException ive) {
            assert false : "There should not be any illegal value at this point";
        }

        return newActivity;
    }

    /**
     * Returns an activity overwriting data from the old entry with the new parameters.
     * @param oldTask the old entry.
     * @param newParams the new parameters.
     * @param type "edit" to edit the old task, or "undo" to revert an edit.
     * @return the Activity containing the new parameters.
     * @throws IllegalValueException
     */
    private static Activity updateActivity(Activity oldTask, Activity newParams, String type)
            throws IllegalValueException {
        return new Activity(
                updateTaskName(oldTask, newParams, type), 
                updateReminder(oldTask, newParams, type),
                updateTags(oldTask, newParams, type));
    }

    /**
     * Returns a task overwriting data from the old entry with the new parameters.
     * @param oldTask the old entry.
     * @param newParams the new parameters.
     * @param type "edit" to edit the old task, or "undo" to revert an edit.
     * @param isNewParamsATask true if the new parameters is of type Task (has a due date and/or priority). 
     * @return the Task containing the new parameters.
     * @throws IllegalValueException
     */
    private static Task updateTask(Activity oldTask, Activity newParams, String type, boolean isNewParamsATask)
            throws IllegalValueException {
        if (isNewParamsATask) {
            return new Task(
                    updateTaskName(oldTask, newParams, type), 
                    updateDueDate(oldTask, newParams, type),
                    updatePriority(oldTask, newParams, type), 
                    updateReminder(oldTask, newParams, type),
                    updateTags(oldTask, newParams, type));
        } else {
            return new Task(
                    updateTaskName(oldTask, newParams, type),
                    new DueDate(((Task) oldTask).getDueDate().getCalendarValue()),
                    new Priority(((Task) oldTask).getPriority().toString()), 
                    updateReminder(oldTask, newParams, type),
                    updateTags(oldTask, newParams, type));
        }
    }

    /**
     * Returns an event overwriting data from the old entry with the new parameters.
     * @param oldTask the old entry.
     * @param newParams the new parameters.
     * @param type "edit" to edit the old task, or "undo" to revert an edit.
     * @param isNewParamsATask true if the new parameters is of type Event (has start and end time). 
     * @return the Event containing the new parameters.
     * @throws IllegalValueException
     */
    private static Event updateEvent(Activity oldTask, Activity newParams, String type, boolean isNewParamsAnEvent)
            throws IllegalValueException {
        if (isNewParamsAnEvent) {
            return new Event(
                    updateTaskName(oldTask, newParams, type), 
                    updateStartTime(oldTask, newParams, type),
                    updateEndTime(oldTask, newParams, type), 
                    updateReminder(oldTask, newParams, type),
                    updateTags(oldTask, newParams, type));
        } else {
            return new Event(
                    updateTaskName(oldTask, newParams, type),
                    new StartTime(((Event) oldTask).getStartTime().getCalendarValue(),
                            ((Event) oldTask).getStartTime().recurring,
                            ((Event) oldTask).getStartTime().RecurringMessage),
                    new EndTime(((Event) oldTask).getEndTime().getCalendarValue(),
                            ((Event) oldTask).getStartTime().recurring,
                            ((Event) oldTask).getStartTime().RecurringMessage),
                    updateReminder(oldTask, newParams, type), 
                    updateTags(oldTask, newParams, type));
        }
    }

    /**
     * Marks the specified task to the specified completion status.
     * @param task the task to be marked.
     * @param isComplete the completion status.
     */
    public static void markTask(Activity task, boolean isComplete) {
        task.setCompletionStatus(isComplete);
    }

    private static Name updateTaskName(Activity oldTask, Activity newParams, String type) throws IllegalValueException {
        if (newParams.getName().toString().equals(NULL_ENTRY) && type.equals(COMMAND_TYPE_EDIT)) {
            return new Name(oldTask.getName().toString());
        } else {
            return new Name(newParams.getName().toString());
        }
    }
    
    private static Reminder updateReminder(Activity oldTask, Activity newParams, String type)
            throws IllegalValueException {
        Reminder newReminder;

        if (newParams.getReminder().toString().equals(NULL_ENTRY) && type.equals(COMMAND_TYPE_EDIT)) {
            newReminder = new Reminder(oldTask.getReminder().getCalendarValue());
            newReminder.recurring = oldTask.getReminder().recurring;
            newReminder.recurringMessage = oldTask.getReminder().recurringMessage;
        } else {
            newReminder = new Reminder(newParams.getReminder().getCalendarValue());
            newReminder.recurring = newParams.getReminder().recurring;
            newReminder.recurringMessage = newParams.getReminder().recurringMessage;
        }

        return newReminder;
    }

    private static DueDate updateDueDate(Activity oldTask, Activity newParams, String type)
            throws IllegalValueException {
        if (!newParams.getClass().getSimpleName().equalsIgnoreCase(ENTRY_TYPE_TASK)
                || (((Task) newParams).getDueDate().toString().equals(NULL_ENTRY) 
                        && type.equals(COMMAND_TYPE_EDIT)
                        && oldTask.getClass().getSimpleName().equalsIgnoreCase(ENTRY_TYPE_TASK))) {
            return new DueDate(((Task) oldTask).getDueDate().getCalendarValue());
        } else if (((Task) newParams).getDueDate().toString().equals(NULL_ENTRY) 
                && type.equals(COMMAND_TYPE_EDIT)
                && oldTask.getClass().getSimpleName().equalsIgnoreCase(ENTRY_TYPE_ACTIVITY)) {
            return new DueDate("");
        } else {
            return new DueDate(((Task) newParams).getDueDate().getCalendarValue());
        }
    }

    private static Priority updatePriority(Activity oldTask, Activity newParams, String type)
            throws IllegalValueException {
        Priority newPriority;

        if (!newParams.getClass().getSimpleName().equalsIgnoreCase(ENTRY_TYPE_TASK)) {
            return new Priority(((Task) oldTask).getPriority().toString());
        }

        if (((Task) newParams).getPriority().toString().equals(NULL_ENTRY) && type.equals(COMMAND_TYPE_EDIT)) {
            if (oldTask.getClass().getSimpleName().equalsIgnoreCase(ENTRY_TYPE_ACTIVITY)) {
                newPriority = new Priority("0");
            } else {
                newPriority = new Priority(((Task) oldTask).getPriority().toString());
            }
        } else {
            newPriority = new Priority(((Task) newParams).getPriority().toString());
        }

        return newPriority;
    }

    private static StartTime updateStartTime(Activity oldTask, Activity newParams, String type)
            throws IllegalValueException {
        StartTime newStartTime;

        if (!newParams.getClass().getSimpleName().equalsIgnoreCase(ENTRY_TYPE_EVENT)) {
            return new StartTime(((Event) oldTask).getStartTime().getCalendarValue());
        }

        if (((Event) newParams).getStartTime().toString().equals(NULL_ENTRY) && type.equals(COMMAND_TYPE_EDIT)) {
            if (oldTask.getClass().getSimpleName().equalsIgnoreCase(ENTRY_TYPE_ACTIVITY)) {
                newStartTime = new StartTime("");
            } else {
                newStartTime = new StartTime(((Event) oldTask).getStartTime().getCalendarValue());
                newStartTime.recurring = ((Event) oldTask).getStartTime().recurring;
                newStartTime.RecurringMessage = ((Event) oldTask).getStartTime().RecurringMessage;

            }
        } else {
            newStartTime = new StartTime(((Event) newParams).getStartTime().getCalendarValue());
            newStartTime.recurring = ((Event) newParams).getStartTime().recurring;
            newStartTime.RecurringMessage = ((Event) newParams).getStartTime().RecurringMessage;
        }

        return newStartTime;
    }

    private static EndTime updateEndTime(Activity oldTask, Activity newParams, String type)
            throws IllegalValueException {
        EndTime newEndTime;

        if (!newParams.getClass().getSimpleName().equalsIgnoreCase(ENTRY_TYPE_EVENT)) {
            return new EndTime(((Event) oldTask).getEndTime().getCalendarValue());
        }

        if (((Event) newParams).getEndTime().toString().equals(NULL_ENTRY) && type.equals(COMMAND_TYPE_EDIT)) {
            if (oldTask.getClass().getSimpleName().equalsIgnoreCase(ENTRY_TYPE_ACTIVITY)) {
                newEndTime = new EndTime(((Event) oldTask).getStartTime(), "");
            } else {
                newEndTime = new EndTime(((Event) oldTask).getEndTime().getCalendarValue());
                newEndTime.recurring = ((Event) oldTask).getEndTime().recurring;
                newEndTime.RecurringMessage = ((Event) oldTask).getEndTime().RecurringMessage;
            }
        } else {
            newEndTime = new EndTime(((Event) newParams).getEndTime().getCalendarValue());
            newEndTime.recurring = ((Event) newParams).getEndTime().recurring;
            newEndTime.RecurringMessage = ((Event) newParams).getEndTime().RecurringMessage;
        }

        return newEndTime;
    }

    private static UniqueTagList updateTags(Activity oldTask, Activity newParams, String type) {
        UniqueTagList newTags;
        if (type.equals(COMMAND_TYPE_EDIT)) {
            newTags = new UniqueTagList(oldTask.getTags());
        } else {
            newTags = new UniqueTagList();
        }

        for (Tag toAdd : newParams.getTags()) {
            try {
                newTags.add(toAdd);
            } catch (DuplicateTagException e) {
                continue; //ignore dupliate tags
            }
        }

        return newTags;
    }

}
