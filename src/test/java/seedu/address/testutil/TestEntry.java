package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.person.*;

/**
 * A mutable person object. For testing only.
 */
public class TestEntry implements Entry {

    private Title title;
    private UniqueTagList tags;

    public TestEntry() {
        tags = new UniqueTagList();
    }

    public void setName(Title name) {
        this.title = name;
    }


    @Override
    public Title getTitle() {
        return title;
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
        sb.append("add " + this.getTitle().fullTitle + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public boolean isSameStateAs(Entry other) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getAsText() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setTags(UniqueTagList uniqueTagList) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setTitle(Title newTitle) {
        // TODO Auto-generated method stub

    }
}
