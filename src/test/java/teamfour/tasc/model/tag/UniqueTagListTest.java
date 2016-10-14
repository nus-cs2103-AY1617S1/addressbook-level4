package teamfour.tasc.model.tag;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.model.tag.Tag;
import teamfour.tasc.model.tag.UniqueTagList;
import teamfour.tasc.model.tag.UniqueTagList.DuplicateTagException;
import teamfour.tasc.model.tag.exceptions.TagNotFoundException;

public class UniqueTagListTest {
    
    private Tag tagImportant;
    private Tag tagMeeting;
    
    @Before
    public void setUp() throws Exception {
        tagImportant = new Tag("Important");
        tagMeeting = new Tag("Meeting");
    }

    @Test (expected=TagNotFoundException.class)
    public void remove_tagNotInList_throwsException()
            throws TagNotFoundException, DuplicateTagException, IllegalValueException {
        
        UniqueTagList list = new UniqueTagList(tagImportant);

        list.remove(tagMeeting);
    }
    
    @Test
    public void remove_tagInList_tagIsRemoved()
            throws TagNotFoundException, DuplicateTagException, IllegalValueException {
        UniqueTagList list = new UniqueTagList(tagImportant);
        list.remove(tagImportant);
        
        assertFalse(list.contains(tagImportant));
    }
}
