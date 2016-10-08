package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.ReadOnlyFloatingTask;

/**
 * A mutable person object. For testing only.
 */
public class TestFloatingTask implements ReadOnlyFloatingTask {

    private Name name;
    private UniqueTagList tags;

    public TestFloatingTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public Priority getPriorityValue() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("a/" + this.getAddress().value + " ");
        return sb.toString();
    }
}
