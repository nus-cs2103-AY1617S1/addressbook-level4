package seedu.todo.model;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.tag.UniqueTagList;

public class UniqueTagListTest {


    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void setTags_test() throws IllegalValueException {
        UniqueTagList list1 = new UniqueTagList();
        list1.add(new Tag("HELLO"));
        list1.add(new Tag("BYE BYE"));
        
        UniqueTagList list2 = new UniqueTagList();
        list2.setTags(list1);
        assertEquals(list1, list2);
    }
    
}
