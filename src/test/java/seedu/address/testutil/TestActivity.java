package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.activity.*;

/**
 * A mutable person object. For testing only.
 */
public class TestActivity implements ReadOnlyActivity {

    private String name;
    private UniqueTagList tags;

    public TestActivity() {
        tags = new UniqueTagList();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
