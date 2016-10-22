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
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;
import seedu.malitio.model.task.UniqueDeadlineList;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineNotFoundException;
import seedu.malitio.model.task.UniqueEventList.DuplicateEventException;
import seedu.malitio.model.task.UniqueEventList.EventNotFoundException;
import seedu.malitio.model.task.UniqueFloatingTaskList;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskNotFoundException;

/**
 * Edits a floating task/ deadline/ event identified using it's last displayed index from Malitio.
 * Only the attribute(s) that require changes is(are) entered.
 * @author Annabel Eng A0129595N
 * 
 */
public class EditCommand extends Command{

    public static final String COMMAND_WORD = "edit";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ":Edits the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be either 'f'/'d'/'e' and a positive integer) [NAME] [by NEWDATE] [START NEWDATE] [END NEWDATE]\n"
            + "Example: " + COMMAND_WORD + " f1 New Name";
    
    public static final String MESSAGE_DUPLICATE_TASK = "The intended edit correspond to a pre-existing floating task in Malitio";
 
    private static final String MESSAGE_DUPLICATE_DEADLINE = "The intended edit correspond to a pre-existing deadline in Malitio";

    private static final String MESSAGE_DUPLICATE_EVENT = "The intended edit correspond to a pre-existing event in Malitio";

    private static final String MESSAGE_INVALID_EVENT = "Event must start before it ends!";
    
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Successfully edited floating task.\nOld: %1$s\nNew: %2$s";

    private static final String MESSAGE_EDIT_DEADLINE_SUCCESS = "Successfully edited deadline.\nOld: %1$s\nNew: %2$s";

    private static final String MESSAGE_EDIT_EVENT_SUCCESS = "Successfully edited event.\nOld: %1$s\nNew: %2$s";
    
    private final char taskType;
    
    private final int targetIndex;
    
    private FloatingTask editedTask;
     
    private Deadline editedDeadline;
    
    private Event editedEvent;
    
    private Name name;
    
    private DateTime due;
    
    private DateTime start;
    
    private DateTime end;
    
    private UniqueTagList tags;
    
    //@@author A0129595N  
    public EditCommand(char taskType, int targetIndex, String name, Set<String> newTags) 
            throws IllegalValueException {
        assert taskType == 'd';
        assert !name.equals("") || !newTags.isEmpty() ;
        this.taskType = taskType;
        this.targetIndex = targetIndex;
        if (!name.equals("")) {
            this.name = new Name(name);
        }
        this.tags = processTags(newTags);
    }
    
    public EditCommand(char taskType, int targetIndex, String name, String due, Set<String> newTags)
            throws IllegalValueException {
        assert taskType == 'd';
        assert !name.equals("") || !due.equals("") || !newTags.isEmpty();
        this.taskType = taskType;
        this.targetIndex = targetIndex;
        if (!name.equals("")) {
            this.name = new Name(name);
        }
        if (!due.equals("")) {
            this.due = new DateTime(due);
        }
        this.tags = processTags(newTags);
    }
    
    public EditCommand(char taskType, int targetIndex, String name, String start, String end, Set<String> newTags)
            throws IllegalValueException {
        assert taskType == 'e';
        assert !name.equals("") || !start.equals("") || !end.equals("") || !newTags.isEmpty();
        this.taskType = taskType;
        this.targetIndex = targetIndex;
        if (!name.equals("")) {
            this.name = new Name(name);
        }
        if (!start.equals("")) {
            this.start = new DateTime(start);
        }
        if (!end.equals("")) {
            this.end = new DateTime(end);
        }
        this.tags = processTags(newTags);
    }
    
    
    /**
     * processTags return a UniqueTagList of tags but returns null if no tags were entered.
     * @param newTags
     * @return UniqueTagList or Null
     * @throws IllegalValueException
     */
    private UniqueTagList processTags(Set<String> newTags) throws IllegalValueException {
        if (!newTags.isEmpty()){
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
     * fillInTheGaps will replace the task's attributes not entered by the user by extracting from the task to be edited .
     * @param ReadOnly<TaskType>
     */
    private void fillInTheGaps(ReadOnlyFloatingTask taskToEdit) {
        if (this.name==null) {
            this.name = taskToEdit.getName();
        }
        if (this.tags==null) {
            this.tags = taskToEdit.getTags();
        }
    }
    
    private void fillInTheGaps(ReadOnlyDeadline deadlineToEdit) {
        if (this.name==null) {
            this.name = deadlineToEdit.getName();
        }
        if (this.due==null) {
            this.due = deadlineToEdit.getDue();
        }
        if (this.tags==null) {
            this.tags = deadlineToEdit.getTags();
        }
    }

    private void fillInTheGaps(ReadOnlyEvent eventToEdit) {
        if (this.name==null) {
            this.name = eventToEdit.getName();
        }
        if (this.start==null) {
            this.start = eventToEdit.getStart();
        }
        if (this.end==null) {
            this.end = eventToEdit.getEnd();
        }
        if (this.tags==null) {
            this.tags = eventToEdit.getTags();
        }
    }
    @Override
    public CommandResult execute() {
        CommandResult result;
        if (taskType=='f') {
            result = executeEditFloatingTask();
            model.getFuture().clear();
            return result;
        }
        else if (taskType=='d') {
            result = executeEditDeadline();
            model.getFuture().clear();
            return result;
        }
        else {
            result = executeEditEvent();
            model.getFuture().clear();
            return result;
        }
        
    }
    
    public CommandResult executeEditFloatingTask() {
        UnmodifiableObservableList<ReadOnlyFloatingTask> lastShownList = model.getFilteredFloatingTaskList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyFloatingTask taskToEdit = lastShownList.get(targetIndex - 1);
                
        try {
            assert model != null;
            fillInTheGaps(taskToEdit);
            editedTask = new FloatingTask(name,tags);
            model.editFloatingTask(editedTask, taskToEdit);
        } catch (FloatingTaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (UniqueFloatingTaskList.DuplicateFloatingTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit, editedTask));
    }
    
    public CommandResult executeEditDeadline() {
        UnmodifiableObservableList<ReadOnlyDeadline> lastShownList = model.getFilteredDeadlineList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_DEADLINE_DISPLAYED_INDEX);
        }

        ReadOnlyDeadline deadlineToEdit = lastShownList.get(targetIndex - 1);
                
        try {
            assert model != null;
            fillInTheGaps(deadlineToEdit);
            editedDeadline = new Deadline(name,due,tags);
            model.editDeadline(editedDeadline, deadlineToEdit);
        } catch (DeadlineNotFoundException pnfe) {
            assert false : "The target deadline cannot be missing";
        } catch (UniqueDeadlineList.DuplicateDeadlineException e) {
            return new CommandResult(MESSAGE_DUPLICATE_DEADLINE);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_DEADLINE_SUCCESS, deadlineToEdit, editedDeadline));
    }
    
    public CommandResult executeEditEvent() {
        UnmodifiableObservableList<ReadOnlyEvent> lastShownList = model.getFilteredEventList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        ReadOnlyEvent eventToEdit = lastShownList.get(targetIndex - 1);
                
        try {
            assert model != null;
            fillInTheGaps(eventToEdit);
            editedEvent = new Event(name, start, end, tags);
            model.editEvent(editedEvent, eventToEdit);
        } catch (EventNotFoundException pnfe) {
            assert false : "The target event cannot be missing";
        } catch (DuplicateEventException e) {
            return new CommandResult(MESSAGE_DUPLICATE_EVENT);
        } catch (IllegalValueException e) {
            return new CommandResult(MESSAGE_INVALID_EVENT);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, eventToEdit, editedEvent));
    }
    
}
