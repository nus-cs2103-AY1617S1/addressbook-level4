package seedu.address.model.task;

public interface ModifyTask {
    
    void setName(Name name);
    void setDate(Date date );
    void setStart(Start start);
    void setEnd(End end);
    void setTaskCategory(int taskCat);
    void setCompleted(boolean completed);
    void setOverdue(int overdue);

}
