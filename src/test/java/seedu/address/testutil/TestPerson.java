package seedu.address.testutil;

import seedu.address.model.item.*;
import seedu.address.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyPerson {

    private ItemType itemType;
    private Date startDate;
    private Time startTime;
    private Date endDate;
    private Time endTime;
    private Name name;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }    
    
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public ItemType getItemType() {
        return itemType;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public Time getStartTime() {
        return startTime;
    }
    
    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public Time getEndTime() {
        return endTime;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        if (this.getItemType().value.equals(ItemType.TASK_WORD)) {
            sb.append("add " + this.getItemType().value + " ");
            sb.append("n/" + this.getName().value + " ");
        } else if (this.getItemType().value.equals(ItemType.DEADLINE_WORD)) {
            sb.append("add " + this.getItemType().value + " ");
            sb.append("n/" + this.getName().value + " ");
            sb.append("ed/" + this.getEndDate().value + " ");
            sb.append("et/" + this.getEndTime().value + " ");
        } else if (this.getItemType().value.equals(ItemType.EVENT_WORD)) {
            sb.append("add " + this.getItemType().value + " ");
            sb.append("n/" + this.getName().value + " ");
            sb.append("sd/" + this.getStartDate().value + " ");
            sb.append("st/" + this.getStartTime().value + " ");
            sb.append("ed/" + this.getEndDate().value + " ");
            sb.append("et/" + this.getEndTime().value + " ");
        }
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
