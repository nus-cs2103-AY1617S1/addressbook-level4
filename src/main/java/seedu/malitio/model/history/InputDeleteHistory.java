package seedu.malitio.model.history;
import javafx.collections.ObservableList;
import seedu.malitio.logic.commands.AddCommand;
import seedu.malitio.logic.commands.DeleteCommand;
import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.DateTime;
import seedu.malitio.model.task.Deadline;
import seedu.malitio.model.task.Event;
import seedu.malitio.model.task.FloatingTask;
import seedu.malitio.model.task.Name;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyFloatingTask;
//@@author A0129595N
public class InputDeleteHistory extends InputHistory {
    private Object task;
    private int initialPositionOfFloatingTask = -1;
    
    public InputDeleteHistory(Object target, ObservableList<FloatingTask> observableList) {
        this.commandForUndo = AddCommand.COMMAND_WORD;
        this.commandForRedo = DeleteCommand.COMMAND_WORD;
        String name = ((ReadOnlyFloatingTask)target).getName().fullName;
        UniqueTagList tags = ((ReadOnlyFloatingTask)target).getTags();
        this.initialPositionOfFloatingTask = observableList.indexOf(target);
        this.task = new FloatingTask(new Name(name), new UniqueTagList(tags));
    }
    
    public InputDeleteHistory(Object target) {
        String name, due, start, end;
        UniqueTagList tags;
        this.commandForUndo = "add";
        try {
            if (isDeadline(target)) {
                name = ((Deadline) target).getName().fullName;
                due = ((Deadline) target).getDue().toString();
                tags = ((Deadline) target).getTags();
                this.task = new Deadline(new Name(name), new DateTime(due), new UniqueTagList(tags));
            } else {
                name = ((Event) target).getName().fullName;
                start = ((Event) target).getStart().toString();
                end = ((Event) target).getEnd().toString();
                tags = ((Event) target).getTags();
                this.task = new Event(new Name(name), new DateTime(start), new DateTime(end), new UniqueTagList(tags));
            }
        } catch (Exception e) {
            assert false : "Not possible";
        }
    }
    
    private boolean isDeadline(Object target) {
        return target instanceof ReadOnlyDeadline;
    }
   
    public Object getTask() {
        return task;
    }
    
    public int getPositionOfFloatingTask() {
        return initialPositionOfFloatingTask;
    }
}
