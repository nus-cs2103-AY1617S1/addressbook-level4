package harmony.mastermind.model.task;

import java.util.Date;

import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.tag.UniqueTagList;

public class Task implements ReadOnlyTask {

    private String name;
    private Date startDate;
    private Date endDate;
    private UniqueTagList tags;
    private boolean marked;

    // event
    // @@author A0138862W
    public Task(String name, Date startDate, Date endDate, UniqueTagList tags) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags = tags;
        this.marked = false;
    }

    // deadline
    // @@author A0138862W
    public Task(String name, Date endDate, UniqueTagList tags) {
        this(name, null, endDate, tags);
    }

    // floating
    // @@author A0138862W
    public Task(String name, UniqueTagList tags) {
        this(name, null, null, tags);
    }

    // @@author A0138862W
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartDate(), source.getEndDate(), source.getTags());
        this.marked = source.isMarked();
    }

    @Override
    // @@author generated
    public String getName() {
        return name;
    }

    // @@author generated
    public void setName(String name) {
        this.name = name;
    }

    @Override
    // @@author generated
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    // @@author generated
    public Date getStartDate() {
        return startDate;
    }

    // @@author generated
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    // @@author generated
    public Date getEndDate() {
        return endDate;
    }

    // @@author generated
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    // @@author generated
    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    // @@author A0138862W
    public boolean isFloating() {
        return startDate == null && endDate == null;
    }

    @Override
    // @@author A0138862W
    public boolean isDeadline() {
        return startDate == null && endDate != null;
    }

    @Override
    // @@author A0138862W
    public boolean isEvent() {
        return startDate != null && endDate != null;
    }

    //@@author A0124797R
    @Override 
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Task // instanceof handles nulls
                && this.getName().equals(((Task) other).getName())); // state check
        
    }
        
    @Override
    //@@author A0124797R
    public boolean isMarked() {
        return this.marked;
    }

    //@@author A0124797R
    public Task mark() {
        this.marked = true;
        return this;
    }
    
    //@@author A0124797R
    public Task unmark() {
        this.marked = false;
        return this;
    }
    
    //@@author A0124797R
    @Override
    public String toString() {
        return getAsText();
    }
}
