package seedu.taskmaster.model.task.stub;

import java.util.Set;

import seedu.taskmaster.model.tag.Tag;
import seedu.taskmaster.model.tag.UniqueTagList;

//@@author A0135782Y
public class UniqueTagListStub extends UniqueTagList {
    /**
     * All tags in this list as a Set. This set is mutable and change-insulated against the internal list.
     */
    public Set<Tag> toSet() {
        return null;
    }

    /**
     * Replaces the Tags in this list with those in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
    }

    /**
     * Adds every tag from the argument list that does not yet exist in this list.
     */
    public void mergeFrom(UniqueTagList tags) {
    }

    /**
     * Returns true if the list contains an equivalent Tag as the given argument.
     */
    public boolean contains(Tag toCheck) {
        return true;
    }

    /**
     * Adds a Tag to the list.
     *
     * @throws DuplicateTagException if the Tag to add is a duplicate of an existing Tag in the list.
     */
    @Override
    public void add(Tag toAdd) throws DuplicateTagException {
    }


    @Override
    public boolean equals(Object other) {
        return true;
    }

}
