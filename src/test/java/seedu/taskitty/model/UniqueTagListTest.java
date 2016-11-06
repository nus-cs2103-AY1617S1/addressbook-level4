package seedu.taskitty.model;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.model.tag.Tag;
import seedu.taskitty.model.tag.UniqueTagList;
import seedu.taskitty.model.tag.UniqueTagList.DuplicateTagException;

//@@author A0130853L
public class UniqueTagListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void individualTags_hasDuplicates_exceptionThrown() throws DuplicateTagException, IllegalValueException {
        thrown.expect(DuplicateTagException.class);
        UniqueTagList list = new UniqueTagList(new Tag("hi"), new Tag("hi"));
    }
    
    @Test
    public void createTagCollection_hasDuplicates_exceptionThrown() throws DuplicateTagException, IllegalValueException {
        thrown.expect(DuplicateTagException.class);
        Collection<Tag> tags = new ArrayList<Tag>();
        tags.add(new Tag("hi"));
        tags.add(new Tag("hi"));
        UniqueTagList list = new UniqueTagList(tags);
    }
    
    @Test
    public void addTagToUniqueTagList_hasDuplicate_exceptionThrown() throws DuplicateTagException, IllegalValueException {
        thrown.expect(DuplicateTagException.class);
        UniqueTagList list = new UniqueTagList();
        list.add(new Tag("hi"));
        list.add(new Tag("hi"));
    }
}
