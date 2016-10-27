//@@author A0144939R
package seedu.task.testutil;

import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private DateTime openTime;
    private DateTime closeTime;
    private UniqueTagList tags;
    private boolean isImportant;
    private boolean isCompleted;
    private int recurrentWeek;

    public TestTask() {
        tags = new UniqueTagList();
    }
    
    public void setName(Name name) {
        this.name = name;
    }

    public void setIsImportant(boolean isImportant){
    	this.isImportant=isImportant;
    }
    
    public void setOpenTime(DateTime openTime) {
        this.openTime = openTime;
    }
    
    public void setCloseTime(DateTime closeTime) {
        this.closeTime = closeTime;
    }

    public void setIsCompleted(boolean isCompleted){
        this.isCompleted = isCompleted;
    }
    
    public void setRecurrentWeek(int recurrentWeek){
        this.recurrentWeek=recurrentWeek;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public boolean getComplete() {
        return isCompleted;
    }
    
    @Override
    public DateTime getOpenTime() {
        return openTime;
    }

    @Override
    public DateTime getCloseTime() {
        return closeTime;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public int getRecurrentWeek() {
        return recurrentWeek;
    }
    
    @Override
    public boolean getImportance() {
        return isImportant;
    }
    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().taskName + " ");
        sb.append("s/" + this.getOpenTime().toPrettyString() + " ");
        sb.append("c/" + this.getCloseTime().toPrettyString() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        sb.append("r/" + this.getRecurrentWeek()+ " ");
        return sb.toString();
    }
    
    public String getArgs() {
        StringBuilder sb = new StringBuilder();
        sb.append(" "+this.getName().taskName + " ");
        sb.append("s/" + this.getOpenTime().toPrettyString() + " ");
        sb.append("c/" + this.getCloseTime().toPrettyString() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
