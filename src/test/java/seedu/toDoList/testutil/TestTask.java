package seedu.toDoList.testutil;

import seedu.toDoList.commons.exceptions.IllegalValueException;
import seedu.toDoList.model.tag.UniqueTagList;
import seedu.toDoList.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private static int DEFAULT_PRIORITY_LEVEL = 0;

    private Name name;
    private Date date;
    private Recurring recurring;
    private Priority priorityLevel;
    private boolean isEvent;
    private boolean isDone;
    private boolean isRecurring;
    private boolean isCustomizedPriority;

    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
        isDone = false;
        try {
            priorityLevel = new Priority(DEFAULT_PRIORITY_LEVEL);
            isCustomizedPriority = false;
        } catch (IllegalValueException e) {
            assert false;
        }
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
        if (date instanceof EventDate) {
            isEvent = true;
        } else {
            isEvent = false;
        }
    }

    public void setRecurringFrequency(String freq) throws IllegalValueException {
        this.isRecurring = true;
        this.recurring = new Recurring(freq);
    }

    public void setPriorityLevel(Priority priorityLevel) {
        this.isCustomizedPriority = true;
        this.priorityLevel = priorityLevel;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public boolean isEvent() {
        return isEvent;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public Priority getPriorityLevel() {
        return priorityLevel;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + "n/" + this.getName().taskName + " ");
        if (isEvent) {
            assert date instanceof EventDate;
            EventDate eventDate = (EventDate) this.getDate();
            sb.append("s/" + eventDate.getStartDate() + " ");
            sb.append("e/" + eventDate.getEndDate() + " ");
        } else {
            assert date instanceof Deadline;
            String deadline = this.getDate().getValue();
            if (!deadline.equals("")) {
                sb.append("d/" + deadline + " ");
            }
        }
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        if (isRecurring) {
            sb.append("r/" + recurring.recurringFrequency);
        }
        if (isCustomizedPriority) {
            sb.append("p/" + priorityLevel.priorityLevel);
        }
        return sb.toString();
    }

    @Override
    public void markAsDone() {
        isDone = true;

    }

    // @@author A0142325R
    public String getFlexiAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add ");
        if (isCustomizedPriority) {
            sb.append("p/" + priorityLevel.priorityLevel);
        }
        if (isEvent) {
            assert date instanceof EventDate;
            EventDate eventDate = (EventDate) this.getDate();
            sb.append("e/" + eventDate.getEndDate() + " ");
            sb.append("s/" + eventDate.getStartDate() + " ");
        } else {
            assert date instanceof Deadline;
            String deadline = this.getDate().getValue();
            if (!deadline.equals("")) {
                sb.append("d/" + deadline + " ");
            }
        }
        if (isRecurring) {
            sb.append("r/" + recurring.recurringFrequency);
        }
        sb.append("n/" + this.getName().taskName + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public Recurring getRecurring() {
        return recurring;
    }

    @Override
    public boolean isRecurring() {
        return isRecurring;
    }

}
