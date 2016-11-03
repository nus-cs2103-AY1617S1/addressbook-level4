package seedu.jimi.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.jimi.commons.core.Messages;
import seedu.jimi.commons.core.UnmodifiableObservableList;
import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.datetime.DateTime;
import seedu.jimi.model.event.Event;
import seedu.jimi.model.tag.Priority;
import seedu.jimi.model.tag.Tag;
import seedu.jimi.model.tag.UniqueTagList;
import seedu.jimi.model.task.DeadlineTask;
import seedu.jimi.model.task.FloatingTask;
import seedu.jimi.model.task.Name;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.model.task.UniqueTaskList.DuplicateTaskException;

// @@author A0140133B
/**
 * Edits an existing task/event in Jimi.
 */
public class EditCommand extends Command implements TaskBookEditor {
    
    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_REMOVE_DATES = "dateless";
    public static final String COMMAND_REMOVE_TAGS = "tagless";
    
    public static final String INDEX_TASK_PREFIX = "t";
    public static final String INDEX_EVENT_PREFIX = "e";
    
    public static final String MESSAGE_EDIT_EVENT_CONSTRAINTS = 
            "You tried to convert a task to an event but failed to specify an event start date.\n"
            + "Remember, edits must follow adding contraints! \n"
            + "\n"
            + AddCommand.MESSAGE_USAGE;
    
    public static final String MESSAGE_USAGE = 
            COMMAND_WORD + ": Edits an existing task/event in Jimi following add constraints. \n"
            + "Parameters: INDEX(must be t<positive integer> or e<positive integer>) EDITS_TO_MAKE\n" 
            + "You can edit everything from the task name to its tags. \n"
            + "You can leave out fields that you do not wish to edit too. \n"
            + "\n"
            + "For instance, if t2. already has a deadline but you only wish to edit its name: \n"
            + "Example: " + COMMAND_WORD + " t2 \"clear trash\"\n"
            + "\n"
            + "If you wish to remove all dates/tags from an existing task: \n"
            + "Example: " + COMMAND_WORD + " e1 dateless or " + COMMAND_WORD + " e1 tagless\n"
            + "\n"
            + "> Tip: Typing 'e', 'ed', 'edi' instead of 'edit' works too.";
    
    public static final String MESSAGE_EDIT_SUCCESS = "Updated details: %1$s";
    private static final String MESSAGE_DUPLICATE_TASK = 
            "Your new edits seem to overlap with an already existing task. Please check and try again!";
    
    private final String taskIndex; //index of task/event to be edited
    private UniqueTagList newTagList;
    private Name newName;
    private Priority newPriority;
    
    private DateTime deadline;
    private DateTime eventStart;
    private DateTime eventEnd;
    
    public enum EditType {
        REMOVE_DATES,
        REMOVE_TAGS,
        TO_SAME_TYPE,
        TO_EVENT,
        TO_DEADLINE
    }
    
    private EditType editType;
    
    /** Empty constructor for stub usage */
    public EditCommand() {
        this.taskIndex = null;
    }
    
    /** Constructor for removal of tags/dates at {@code taskIndex}. */
    public EditCommand(String taskIndex, EditType removeType) {
        assert removeType == EditType.REMOVE_DATES || removeType == EditType.REMOVE_TAGS;
        this.taskIndex = taskIndex;
        if (removeType == EditType.REMOVE_TAGS) {
            newTagList = new UniqueTagList();
        }
        editType = removeType;
    }
    
    public EditCommand(String name, Set<String> tags, List<Date> deadline, List<Date> eventStart, List<Date> eventEnd,
            String taskIndex, String priority) throws IllegalValueException {
        
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        
        this.taskIndex = taskIndex;
        
        if (name != null) {
            this.newName = new Name(name);
        }
        
        if (!tagSet.isEmpty()) {
            this.newTagList = new UniqueTagList(tagSet);
        }
        
        if (priority != null && !priority.isEmpty())   {
            this.newPriority = new Priority(priority);
        }
        
        this.deadline = (deadline.size() != 0) ? new DateTime(deadline.get(0)) : null;
        this.eventStart = (eventStart.size() != 0) ? new DateTime(eventStart.get(0)) : null;
        this.eventEnd = (eventEnd.size() != 0) ? new DateTime(eventEnd.get(0)) : null;
        
        determineEditType();
    }
    
    @Override
    public CommandResult execute() {
        Optional<UnmodifiableObservableList<ReadOnlyTask>> optionalList = 
                determineListFromIndexPrefix(taskIndex);
        
        // actual index is everything after the 1 character prefix.
        int actualIdx = Integer.parseInt(taskIndex.substring(1).trim());
        if (!optionalList.isPresent() || optionalList.get().size() < actualIdx) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = optionalList.get();
        
        ReadOnlyTask oldTask = lastShownList.get(actualIdx - 1);        
        Optional<ReadOnlyTask> newTask;
        try {
            newTask = determineNewTask(oldTask);
        } catch (IllegalValueException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(e.getMessage());
        }
        if (!newTask.isPresent()) {
            new CommandResult(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        
        try {
            model.replaceTask(oldTask, newTask.get());
            return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, newTask.get()));
        } catch (DuplicateTaskException dte) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }
    
    @Override
    public boolean isValidCommandWord(String commandWord) {
        for (int i = 1; i <= COMMAND_WORD.length(); i++) {
            if (commandWord.toLowerCase().equals(COMMAND_WORD.substring(0, i))) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getMessageUsage() {
        return MESSAGE_USAGE;
    }
    
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
    
    /*
     * ====================================================================
     *                          Helper Methods
     * ====================================================================
     */
    
    /** Determines the type of edit based on user input. */
    private void determineEditType() {
        if ((newName != null || newTagList != null || newPriority != null) && deadline == null && eventStart == null
                && eventEnd == null) {
            this.editType = EditType.TO_SAME_TYPE;
        } else if (eventStart == null && eventEnd == null && deadline != null) {
            this.editType = EditType.TO_DEADLINE;
        } else if (deadline == null && (eventStart != null || eventEnd != null)) {
            this.editType = EditType.TO_EVENT;
        } else {
            this.editType = EditType.REMOVE_DATES;
        }
    }
    
    /** Generates the new task to replace the current task 
     * @throws IllegalValueException */
    private Optional<ReadOnlyTask> determineNewTask(ReadOnlyTask oldTask) throws IllegalValueException {
        switch (editType) {
        case REMOVE_DATES :
            return Optional.of(toFloatingTypeWithChanges(oldTask));
        case REMOVE_TAGS :
        case TO_SAME_TYPE :
            return Optional.of(toSameTaskTypeWithChanges(oldTask));
        case TO_EVENT :
            return Optional.of(toEventTypeWithChanges(oldTask));
        case TO_DEADLINE :
            return Optional.of(toDeadlineTaskTypeWithChanges(oldTask));
        default :
            return Optional.empty();
        }
    }

    /** Converts ReadOnlyTask {@code t} to a floating task with changes */
    private ReadOnlyTask toFloatingTypeWithChanges(ReadOnlyTask t) {
        return generateFloatingTaskWithChanges(t);
    }

    /** Converts ReadOnlyTask {@code t} to a deadline task with changes. */
    private ReadOnlyTask toDeadlineTaskTypeWithChanges(ReadOnlyTask t) {
        return generateDeadlineTaskWithChanges(t);
    }

    /** Converts ReadOnlyTask {@code t} to an Event with changes. 
     * @throws IllegalValueException */
    private ReadOnlyTask toEventTypeWithChanges(ReadOnlyTask t) throws IllegalValueException {
        DateTime newStart = null;
        DateTime newEnd = null;
        
        if (!(t instanceof Event)) {
            if (eventStart == null) {
                throw new IllegalValueException(MESSAGE_EDIT_EVENT_CONSTRAINTS);
            }
            newStart = eventStart;
            newEnd = eventEnd == null ? new DateTime(eventStart.getDate().concat(" 23:59")) : eventEnd;
        } else { 
            newStart = determineNewEventStartForExistingEvent(t);
            newEnd = determineNewEventEndForExistingEvent(t);
        }
        
        if (newStart.compareTo(newEnd) <= 0) {
            return generateEventWithChanges(t, newStart, newEnd);
        } else {
            throw new IllegalValueException(Messages.MESSAGE_START_END_CONSTRAINT);
        }
    }

    /** Converts ReadOnlyTask {@code t} to its same task type, but with changes. 
     * @throws IllegalValueException */
    private ReadOnlyTask toSameTaskTypeWithChanges(ReadOnlyTask t) throws IllegalValueException {
        if (t instanceof Event) {
            DateTime newStart = determineNewEventStartForExistingEvent(t);
            DateTime newEnd = determineNewEventEndForExistingEvent(t);
            if (newStart.compareTo(newEnd) <= 0) {
                return generateEventWithChanges(t, newStart, newEnd);
            } else {
                throw new IllegalValueException(Messages.MESSAGE_START_END_CONSTRAINT);
            }
        } else if (t instanceof DeadlineTask) {
            return generateDeadlineTaskWithChanges(t);
        } else { // floating task
            return generateFloatingTaskWithChanges(t);
        }
    }

    /*
     * ==================================================================
     *                  Instantiation/Generation Methods
     * ==================================================================
     */
    
    /** Generates an event with changes */
    private Event generateEventWithChanges(ReadOnlyTask t, DateTime newStart, DateTime newEnd) {
        return new Event(newName == null ? t.getName() : newName, newStart, newEnd,
                newTagList == null ? t.getTags() : newTagList, t.isCompleted(), 
        		newPriority == null ? t.getPriority() : newPriority);
    }
    
    /** Generates a deadline task with changes */
    private DeadlineTask generateDeadlineTaskWithChanges(ReadOnlyTask t) {
        return new DeadlineTask(
                newName == null ? t.getName() : newName, 
                t instanceof DeadlineTask && deadline == null ? ((DeadlineTask) t).getDeadline() : deadline, 
                newTagList == null ? t.getTags() : newTagList, 
                newPriority == null ? t.getPriority() : newPriority);
    }
    
    /** Generates a floating task with changes */
    private FloatingTask generateFloatingTaskWithChanges(ReadOnlyTask t) {
        return new FloatingTask(
                newName == null ? t.getName() : newName, 
                newTagList == null ? t.getTags() : newTagList, 
                t.isCompleted(),
                newPriority == null ? t.getPriority() : newPriority);
    }
    
    private DateTime determineNewEventEndForExistingEvent(ReadOnlyTask t) {
        return eventEnd == null ? ((Event) t).getEnd() : eventEnd; // Checking for changes to be made
    }

    private DateTime determineNewEventStartForExistingEvent(ReadOnlyTask t) {
        return eventStart == null ? ((Event) t).getStart() : eventStart; // Checking for changes to be made
    }
}
