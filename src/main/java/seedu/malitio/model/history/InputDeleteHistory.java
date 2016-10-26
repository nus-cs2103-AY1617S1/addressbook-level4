package seedu.malitio.model.history;
import javafx.collections.ObservableList;
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
public class InputDeleteHistory extends InputHistory {
    private String name;
    private String due;
    private String start;
    private String end;
    private UniqueTagList tags;
    private String type;
    private int initialPositionOfFloatingTask;
    
    public InputDeleteHistory(ReadOnlyFloatingTask target, ObservableList<FloatingTask> observableList) {
        this.commandForUndo = "add";
        this.name = target.getName().fullName;
        this.tags = target.getTags();
        this.type = "floating task";
        this.initialPositionOfFloatingTask = observableList.indexOf(target);
    }
    
    public InputDeleteHistory(ReadOnlyDeadline target) {
        this.commandForUndo = "add";
        this.name = target.getName().fullName;
        this.due = target.getDue().toString();
        this.tags = target.getTags();
        this.type = "deadline";
    }
    
    public InputDeleteHistory(ReadOnlyEvent target) {
        this.commandForUndo = "add";
        this.name = target.getName().fullName;
        this.start = target.getStart().toString();
        this.end = target.getEnd().toString();
        this.tags = target.getTags();
        this.type = "event";        
    }
    
    public String getType() {
        return type;
    }
    
    public FloatingTask getFloatingTask() {
        return new FloatingTask(new Name(name), new UniqueTagList(tags));
    }
    
    public int getPositionOfFloatingTask() {
        return initialPositionOfFloatingTask;
    }
    
    public Deadline getDeadline() {
        try {
            return new Deadline(new Name(name), new DateTime(due), new UniqueTagList(tags));
        } catch (IllegalValueException e) {
            assert false: "not possible";
        }
        return null;
    }
    
    public Event getEvent() {
        try {
            return new Event(new Name(name), new DateTime(start), new DateTime(end), new UniqueTagList(tags));
        } catch (IllegalValueException e) {
            assert false: "not possible";
        }
        return null;
    }
}
