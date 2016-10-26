package seedu.malitio.testutil;

import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.*;

/**
 * A mutable deadline object. For testing only.
 */
public class TestDeadline implements ReadOnlyDeadline {

    private Name name;
    private DateTime due;
    private boolean completed = false;
    private UniqueTagList tags;

    public TestDeadline() {
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

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " " + "by " + this.getDue().toString());
        this.getTags().getInternalList().stream().forEach(s -> sb.append(" t/" + s.tagName + " "));
        return sb.toString();
    }


    public DateTime getDue() {
        return due;
    }


    public void setDue(DateTime due) {
        this.due = due;
    }

    @Override
    public String tagsString() {
        return ReadOnlyDeadline.super.tagsString();
    }

    @Override
    public String getAsText() {
        return ReadOnlyDeadline.super.getAsText();
    }

	@Override
	public boolean getCompleted() {
		return completed;
	}

	@Override
	public void setCompleted() {
		this.completed = true;
	}

}
