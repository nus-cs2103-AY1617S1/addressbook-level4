package seedu.malitio.model.history;

import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.UniqueDeadlineList;
import seedu.malitio.model.task.UniqueEventList;
import seedu.malitio.model.task.UniqueFloatingTaskList;
//@@author A0129595N
public class InputClearHistory extends InputHistory{
    
    UniqueFloatingTaskList originalFloatingTaskList = new UniqueFloatingTaskList();
    UniqueDeadlineList originalDeadlineList = new UniqueDeadlineList();
    UniqueEventList originalEventList = new UniqueEventList();
    UniqueTagList originalTagList = new UniqueTagList();

    public InputClearHistory(UniqueFloatingTaskList task, UniqueDeadlineList deadline,
            UniqueEventList event, UniqueTagList tag) {
        this.originalFloatingTaskList.getInternalList().addAll(task.getInternalList());
        this.originalDeadlineList.getInternalList().addAll(deadline.getInternalList());
        this.originalEventList.getInternalList().addAll(event.getInternalList());
        this.originalTagList.getInternalList().addAll(tag.getInternalList());
        this.commandForUndo = "clear";
    }
    
    public UniqueFloatingTaskList getFloatingTask() {
        return originalFloatingTaskList;
    }

    public UniqueDeadlineList getDeadline() {
        return originalDeadlineList;
    }
    
    public UniqueEventList getEvent() {
        return originalEventList;
    }
    
    public UniqueTagList getTag() {
        return originalTagList;
    }
}
