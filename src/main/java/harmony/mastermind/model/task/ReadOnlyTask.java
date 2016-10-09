package harmony.mastermind.model.task;

import java.util.Date;

import harmony.mastermind.model.tag.UniqueTagList;

//@@author A0138862W
public interface ReadOnlyTask {
    //provide safe read, unmodifiable task object

    public String getName();
    public Date getStartDate();
    public Date getEndDate();
    public UniqueTagList getTags();
    
    public boolean isArchived();
    
    public boolean isFloating();
    public boolean isDeadline();
    public boolean isEvent();
}
