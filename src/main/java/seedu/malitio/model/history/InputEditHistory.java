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

//@@author A0129595N
public class InputEditHistory extends InputHistory {

    private Object taskToEdit;

    private Object editedTask;
    
    public InputEditHistory(Object edited, Object beforeEdit) {
        this.commandForUndo = "edit";
        this.taskToEdit = edited;
        if (isFloatingTask(edited)) {
            createEditedFloatingTask(beforeEdit);
        } else if (isDeadline(edited)) {
            createEditedDeadline(beforeEdit);
        } else {
            createEditedEvent(beforeEdit);
        }
    }


    private void createEditedEvent(Object beforeEdit) {
        String name = ((Event)beforeEdit).getName().fullName;
        String start = ((Event)beforeEdit).getStart().toString();
        String end = ((Event)beforeEdit).getEnd().toString();
        UniqueTagList tags = ((Event)beforeEdit).getTags();
        try {
            this.editedTask = new Event(new Name(name), new DateTime(start), new DateTime(end), tags);
        } catch (IllegalValueException e) {
            assert false: "not possible";
        }
    }


    private void createEditedDeadline(Object beforeEdit) {
        String name = ((ReadOnlyDeadline) beforeEdit).getName().fullName;
        String due = ((ReadOnlyDeadline) beforeEdit).getDue().toString();
        UniqueTagList tags = ((ReadOnlyDeadline) beforeEdit).getTags();
        try {
            this.editedTask = new Deadline(new Name(name), new DateTime(due), tags);
        } catch (IllegalValueException e) {
            assert false : "not possible";
        }
    }


    private void createEditedFloatingTask(Object beforeEdit) {
        String name = ((ReadOnlyFloatingTask) beforeEdit).getName().fullName;
        UniqueTagList tags = ((ReadOnlyFloatingTask) beforeEdit).getTags();
        this.editedTask = new FloatingTask(new Name(name), tags);
    }
        
    
    private boolean isDeadline(Object edited) {
        return edited instanceof Deadline;
    }


    private boolean isFloatingTask(Object edited) {
        return edited instanceof FloatingTask;
    }
    
    public Object getTaskToEdit() {
        return taskToEdit;
    }
    
    public Object getEditedTask() {
        return editedTask;
    }    
}
