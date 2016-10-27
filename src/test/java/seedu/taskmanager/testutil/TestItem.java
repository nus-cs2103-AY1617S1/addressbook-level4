package seedu.taskmanager.testutil;

import seedu.taskmanager.logic.commands.AddCommand;
import seedu.taskmanager.model.item.ItemDate;
import seedu.taskmanager.model.item.ItemType;
import seedu.taskmanager.model.item.Name;
import seedu.taskmanager.model.item.ReadOnlyItem;
import seedu.taskmanager.model.item.ItemTime;
import seedu.taskmanager.model.tag.UniqueTagList;

/**
 * A mutable item object. For testing only.
 */
public class TestItem implements ReadOnlyItem {

    private ItemType itemType;
    private ItemDate startDate;
    private ItemTime startTime;
    private ItemDate endDate;
    private ItemTime endTime;
    private Name name;
    private boolean done;
    private UniqueTagList tags;

    public TestItem() {
        tags = new UniqueTagList();
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public void setStartTime(ItemTime startTime) {
        this.startTime = startTime;
    }

    public void setStartDate(ItemDate startDate) {
        this.startDate = startDate;
    }    
    
    public void setEndTime(ItemTime endTime) {
        this.endTime = endTime;
    }

    public void setEndDate(ItemDate endDate) {
        this.endDate = endDate;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
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
    public ItemDate getStartDate() {
        return startDate;
    }

    @Override
    public ItemTime getStartTime() {
        return startTime;
    }
    
    @Override
    public ItemDate getEndDate() {
        return endDate;
    }

    @Override
    public ItemTime getEndTime() {
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
    
    @Override
    public boolean getDone() {
        return done;
    }
    
    @Override
    public void setDone() {
        done = true;
    }
    
    public void setUndone() {
        done = false;
    }

    public String getAddCommand(boolean shortCommand, boolean shortItemType, boolean noNamePrefix, boolean noStartTime, boolean noEndTime) {
        StringBuilder sb = new StringBuilder();
        String addCommand;
        if (shortCommand) {
            addCommand = AddCommand.SHORT_COMMAND_WORD;
        } else {
            addCommand = AddCommand.COMMAND_WORD;
        }
        String itemType;
        if (shortItemType) {
            itemType = this.getItemType().value.substring(0, 1);
        } else {
        	itemType = this.getItemType().value;
        }
        String namePrefix = "n/";
        if (noNamePrefix) {
            namePrefix = "";
        }
        // Can be add or a
        if (this.getItemType().value.equals(ItemType.TASK_WORD)) {
            sb.append(addCommand + " " + itemType + " ");
            sb.append(namePrefix + this.getName().value + " ");
        } else if (this.getItemType().value.equals(ItemType.DEADLINE_WORD)) {
            sb.append(addCommand + " " + itemType + " ");
            sb.append(namePrefix + this.getName().value + " ");
            sb.append("ed/" + this.getEndDate().value + " ");
            if (!noEndTime) {
                sb.append("et/" + this.getEndTime().value + " ");
            }
        } else if (this.getItemType().value.equals(ItemType.EVENT_WORD)) {
            sb.append(addCommand + " " + itemType + " ");
            sb.append(namePrefix + this.getName().value + " ");
            sb.append("sd/" + this.getStartDate().value + " ");
            if (!noStartTime) {
                sb.append("st/" + this.getStartTime().value + " ");
            }
            sb.append("ed/" + this.getEndDate().value + " ");
            if (!noEndTime) {
                sb.append("et/" + this.getEndTime().value + " ");
            }
        }
        this.getTags().getInternalList().stream().forEach(s -> sb.append("#" + s.tagName + " "));
        return sb.toString();
    }
}
