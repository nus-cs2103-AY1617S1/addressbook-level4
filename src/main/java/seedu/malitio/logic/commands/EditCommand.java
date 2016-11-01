package seedu.malitio.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.malitio.commons.core.Messages;
import seedu.malitio.commons.core.UnmodifiableObservableList;
import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.DateTime;
import seedu.malitio.model.task.Deadline;
import seedu.malitio.model.task.Event;
import seedu.malitio.model.task.FloatingTask;
import seedu.malitio.model.task.Name;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyFloatingTask;
import seedu.malitio.model.task.UniqueDeadlineList;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineNotFoundException;
import seedu.malitio.model.task.UniqueEventList.DuplicateEventException;
import seedu.malitio.model.task.UniqueEventList.EventNotFoundException;
import seedu.malitio.model.task.UniqueFloatingTaskList;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskNotFoundException;
//@@author A0129595N
/**
 * Edits a floating task/ deadline/ event identified using it's last displayed index from Malitio.
 * Only the attribute(s) that require changes is(are) entered. 
 */
public class EditCommand extends Command{

    public static final String COMMAND_WORD = "edit";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ":Edits the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be either 'f'/'d'/'e' and a positive integer) [NAME] [by NEWDATE] [START NEWDATE] [END NEWDATE]\n"
            + "Example: " + COMMAND_WORD + " f1 New Name";
    
    public static final String MESSAGE_DUPLICATE_TASK = "The intended edit correspond to a pre-existing floating task in Malitio";
 
    public static final String MESSAGE_DUPLICATE_DEADLINE = "The intended edit correspond to a pre-existing deadline in Malitio";

    public static final String MESSAGE_DUPLICATE_EVENT = "The intended edit correspond to a pre-existing event in Malitio";

    public static final String MESSAGE_INVALID_EVENT = "Event must start before it ends!";
    
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Successfully edited task.\nOld: %1$s\nNew: %2$s";

    private final char taskType;
    
    private final int targetIndex;
    
    private Object editedTask;
    
    private Object taskToEdit;
    
    private Name name;
    
    private DateTime due;
    
    private DateTime start;
    
    private DateTime end;
    
    private UniqueTagList tags;
    
    public EditCommand(char taskType, int targetIndex, String name, String due, String start, String end, Set<String> newTags) 
            throws IllegalValueException {
        assert validArgTask(taskType, name, due, start, end, newTags) ;
        this.taskType = taskType;
        this.targetIndex = targetIndex;
        if (notEmptyString(name)) {
            this.name = new Name(name);
        }
        if (notEmptyString(due)) {
            this.due = new DateTime(due);
        }
        if (notEmptyString(start)) {
            this.start = new DateTime(start);
        }
        if (notEmptyString(end)) {
            this.end = new DateTime(end);
        }
        this.tags = processTags(newTags);
    }
    
    @Override
    public CommandResult execute() {
        UnmodifiableObservableList lastShownList;
        lastShownList = getCorrectList();        
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToEdit = lastShownList.get(targetIndex - 1);
                
        try {
            assert model != null;
            fillInTheGaps(taskToEdit);
            constructEditedTask();
            model.editTask(editedTask, taskToEdit);
        } catch (FloatingTaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (UniqueFloatingTaskList.DuplicateFloatingTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (DeadlineNotFoundException pnfe) {
            assert false : "The target deadline cannot be missing";
        } catch (UniqueDeadlineList.DuplicateDeadlineException e) {
            return new CommandResult(MESSAGE_DUPLICATE_DEADLINE);
        } catch (EventNotFoundException pnfe) {
            assert false : "The target event cannot be missing";
        } catch (DuplicateEventException e) {
            return new CommandResult(MESSAGE_DUPLICATE_EVENT);
        } catch (IllegalValueException e) {
            return new CommandResult(MESSAGE_INVALID_EVENT);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit, editedTask));
    }

    /**
     * Creates the correct edited task object
     * @throws IllegalValueException
     */
    private void constructEditedTask() throws IllegalValueException {
        if (taskType == 'f') {
            editedTask = new FloatingTask(name, tags);
        } else if (taskType == 'd') {
            editedTask = new Deadline(name, due, tags);
        } else {
            editedTask = new Event(name, start, end, tags);
        }
    }

    /**
     * @return UnmodifiableObservableList of the correct task type
     */
    private UnmodifiableObservableList getCorrectList() {
        UnmodifiableObservableList lastShownList;
        if (taskType == 'f') {
        lastShownList = model.getFilteredFloatingTaskList();
        } else if (taskType == 'd') {
        lastShownList = model.getFilteredDeadlineList(); 
        } else {
        lastShownList = model.getFilteredEventList();
        }
        return lastShownList;
    }
    
    
    /**
     * fillInTheGaps will replace the task's attributes not entered by the user by extracting from the task to be edited .
     * @param ReadOnly<TaskType>
     */
    private void fillInTheGaps(Object taskToEdit) {
        if (isFloatingTask(taskToEdit)) {
            getFloatingTaskDetails(taskToEdit);
        } else if (isDeadline(taskToEdit)) {
            getDeadlineDetails(taskToEdit);
        } else {
            getEventDetails(taskToEdit);
        }
    }
    
    /**
     * Method to check for valid (at least one non-empty) arguments for the corresponding task type
     */
    private boolean validArgTask(char taskType, String name, String due, String start, String end,
            Set<String> newTags) {
        if (taskType == 'f') {
            return (notEmptyString(name) || !newTags.isEmpty()) && start.equals("") && end.equals("") && due.equals("");
        } else if (taskType == 'd') {
            return (notEmptyString(name) || notEmptyString(due) || !newTags.isEmpty()) && start.equals("") && end.equals("");
        } else {
            return (notEmptyString(name) || notEmptyString(start) || notEmptyString(end) || !newTags.isEmpty()) && due.equals("");
        }
    }
    
    private boolean isDeadline(Object taskToEdit) {
        return taskToEdit instanceof ReadOnlyDeadline;
    }

    private boolean isFloatingTask(Object taskToEdit) {
        return taskToEdit instanceof ReadOnlyFloatingTask;
    }
    
    private boolean notEmptyString(String name) {
        return !name.equals("");
    }
        
    /**
     * processTags return a UniqueTagList of tags or returns null if no tags were entered.
     * @param newTags
     * @return UniqueTagList or Null
     * @throws IllegalValueException
     */
    private UniqueTagList processTags(Set<String> newTags) throws IllegalValueException {
        if (!newTags.isEmpty() && newTags.toArray()[0].equals("null") && newTags.size()==1) {
            return new UniqueTagList();
        } 
        else if (!newTags.isEmpty()){
            final Set<Tag> tagSet = new HashSet<>();
            for (String tagName : newTags) {
            tagSet.add(new Tag(tagName));
            }
            return new UniqueTagList(tagSet);
        }       
        else {
            return null;
        }
    }

    /**
     * Replace the editedTask, type casted to Event, details if they are empty 
     * @param taskToEdit
     */
    private void getEventDetails(Object taskToEdit) {
        if (this.name == null) {
            this.name = ((Event) taskToEdit).getName();
        }
        if (this.start == null) {
            this.start = ((Event) taskToEdit).getStart();
        }
        if (this.end == null) {
            this.end = ((Event) taskToEdit).getEnd();
        }
        if (this.tags == null) {
            this.tags = ((Event) taskToEdit).getTags();
        }
    }

    /**
     * Replace the editedTask, type casted to Deadline, details if they are empty
     * @param taskToEdit
     */
    private void getDeadlineDetails(Object taskToEdit) {
        if (this.name == null) {
            this.name = ((ReadOnlyDeadline) taskToEdit).getName();
        }
        if (this.due == null) {
            this.due = ((ReadOnlyDeadline) taskToEdit).getDue();
        }
        if (this.tags == null) {
            this.tags = ((ReadOnlyDeadline) taskToEdit).getTags();
        }
    }

    /**
     * Replace the editedTask, type casted to Floating Task, details if they are empty
     * @param taskToEdit
     */
    private void getFloatingTaskDetails(Object taskToEdit) {
        if (this.name == null) {
            this.name = ((ReadOnlyFloatingTask) taskToEdit).getName();
        }
        if (this.tags == null) {
            this.tags = ((ReadOnlyFloatingTask) taskToEdit).getTags();
        }
    }
    
}
