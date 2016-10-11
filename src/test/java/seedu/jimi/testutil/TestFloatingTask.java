package seedu.jimi.testutil;

import seedu.jimi.model.tag.UniqueTagList;
import seedu.jimi.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestFloatingTask implements ReadOnlyTask {

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
    public UniqueTagList getTags() {
        return tags;
    }
    
    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public boolean isCompleted() {
        return false;
    }
}
