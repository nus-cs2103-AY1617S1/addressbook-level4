package seedu.malitio.model.history;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.DateTime;
import seedu.malitio.model.task.Deadline;
import seedu.malitio.model.task.Event;
import seedu.malitio.model.task.FloatingTask;
import seedu.malitio.model.task.Name;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;

public class InputEditHistory extends InputHistory {

    private ReadOnlyFloatingTask taskToEdit;
    private ReadOnlyDeadline deadlineToEdit;
    private ReadOnlyEvent eventToEdit;
    private FloatingTask editedTask;
    private Deadline editedDeadline;
    private Event editedEvent;
    private String type;
    
    public InputEditHistory(FloatingTask editedTask, ReadOnlyFloatingTask taskToEdit) {
        this.type = "floating task";
        this.commandForUndo = "edit";
        this.taskToEdit = editedTask;
        String name = taskToEdit.getName().fullName;
        UniqueTagList tags = taskToEdit.getTags();
        try {
            this.editedTask = new FloatingTask(new Name(name), tags);
        } catch (IllegalValueException e) {
            assert false: "not possible";
        }
    }
    
    public InputEditHistory(Deadline editedDeadline, ReadOnlyDeadline deadlineToEdit) {
        this.type = "deadline";
        this.commandForUndo = "edit";
        this.deadlineToEdit = editedDeadline;
        String name = deadlineToEdit.getName().fullName;
        String due = deadlineToEdit.getDue().toString();
        UniqueTagList tags = deadlineToEdit.getTags();
        try {
            this.editedDeadline = new Deadline(new Name(name), new DateTime(due), tags);
        } catch (IllegalValueException e) {
            assert false: "not possible";
        }
    }
    
    public InputEditHistory(Event editedEvent, ReadOnlyEvent eventToEdit) {
        this.type = "event";
        this.commandForUndo = "edit";
        this.eventToEdit = editedEvent;
        String name = eventToEdit.getName().fullName;
        String start = eventToEdit.getStart().toString();
        String end = eventToEdit.getEnd().toString();
        UniqueTagList tags = eventToEdit.getTags();
        try {
            this.editedEvent = new Event(new Name(name), new DateTime(start), new DateTime(end), tags);
        } catch (IllegalValueException e) {
            assert false: "not possible";
        }
    }
    
    public String getType() {
        return type;
    }
    
    public ReadOnlyFloatingTask getTaskToEdit() {
        return taskToEdit;
    }
    
    public ReadOnlyDeadline getDeadlineToEdit() {
        return deadlineToEdit;
    }
    
    public ReadOnlyEvent getEventToEdit() {
        return eventToEdit;
    }
    
    public FloatingTask getEditedTask() {
        return editedTask;
    }
    
    public Deadline getEditedDeadline() {
        return editedDeadline;
    }
    
    public Event getEditedEvent() {
        return editedEvent;
    }
    
}
