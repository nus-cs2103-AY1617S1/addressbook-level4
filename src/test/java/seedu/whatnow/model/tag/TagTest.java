package seedu.whatnow.model.tag;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.model.tag.UniqueTagList.DuplicateTagException;

public class TagTest {
    //@@author A0139772U
    @Test
    public void createUniqueTagList_duplicatedTagCollections_exceptionThrown() throws Exception {
        try {
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag1");
            ArrayList<Tag> tagList = new ArrayList<Tag>();
            
            tagList.add(tag1);
            tagList.add(tag2);
            
            new UniqueTagList(tagList);
            
        } catch (DuplicateTagException e) {
            assertEquals(e.getMessage(), "Operation would result in duplicate tags");
        }
    }
    
    //@@author A0139772U
    @Test
    public void createUniqueTagList_noDuplicatedTagCollections_UniqueTagListCreated() throws Exception {
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            ArrayList<Tag> tagList = new ArrayList<Tag>();
            
            tagList.add(tag1);
            tagList.add(tag2);
            
            new UniqueTagList(tagList);
    }
    //@@author A0139772U
    @Test
    public void createUniqueTagList_noDuplicatedTagSet_UniqueTagListCreated() throws IllegalValueException {
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        Set<Tag> tagSet = new HashSet<Tag>();
        
        tagSet.add(tag1);
        tagSet.add(tag2);
        
        new UniqueTagList(tagSet);
    }

    //@@author A0139772U
    @Test
    public void createUniqueTagList_noDuplicatedTagInUniqueTagList_UniqueTagListCreated() throws DuplicateTagException, IllegalValueException {
        UniqueTagList feed = new UniqueTagList(new Tag("tag1"), new Tag("tag2"));
        UniqueTagList newList = new UniqueTagList(feed);
        
        assertTrue(feed.size() == newList.size());
    }
    
    @Test
    public void mergeTagList_bothTagListValid_firstListContainAllTag() throws DuplicateTagException, IllegalValueException {
        UniqueTagList list1 = new UniqueTagList(new Tag("tag1"), new Tag("tag2"));
        UniqueTagList list2 = new UniqueTagList(new Tag("tag3"), new Tag("tag4"));
        int list1size = list1.size();
        int list2size = list2.size();
        
        list1.mergeFrom(list2);

        assertTrue(list1.size() == list1size + list2size);
    }
    
    @Test
    public void setTag_validTagList_replaceOriginalTags() throws DuplicateTagException, IllegalValueException {
        UniqueTagList list1 = new UniqueTagList(new Tag("tag1"), new Tag("tag2"));
        UniqueTagList list2 = new UniqueTagList(new Tag("tag3"), new Tag("tag4"));
        
        list1.setTags(list2);
        
        assertEquals(list1, list2);
    }
    
    @Test
    public void containsTag_validTag_listContainsTag() throws DuplicateTagException, IllegalValueException {
        UniqueTagList list1 = new UniqueTagList(new Tag("tag1"), new Tag("tag2"));
        assertTrue(list1.contains(new Tag("tag1")));
    }
    
    @Test
    public void addTagToTagList_validTag_tagAddedToTagList() throws DuplicateTagException, IllegalValueException {
        UniqueTagList list1 = new UniqueTagList(new Tag("tag1"), new Tag("tag2"));
        
        list1.add(new Tag("tag3"));
        
        assertTrue(list1.contains(new Tag("tag3")));
    }
    
    @Test
    public void addTagToTagList_duplicatedTag_duplicatedTagExceptionThrown() throws IllegalValueException {
        UniqueTagList list1 = new UniqueTagList(new Tag("tag1"), new Tag("tag2"));
        try {
            list1.add(new Tag("tag2"));
        } catch (DuplicateTagException e) {
            assertEquals(e.getMessage(), "Operation would result in duplicate tags");
        }
    }
    
    
    
    @Test
    public void equalTagList_tagListNotEqual_listNotEqual() throws DuplicateTagException, IllegalValueException {
        UniqueTagList list1 = new UniqueTagList(new Tag("tag1"), new Tag("tag2"));
        UniqueTagList list2 = new UniqueTagList(new Tag("tag3"), new Tag("tag4"));
        
        assertFalse(list1.equals(list2));
    }
    
    @Test
    public void equalTagList_comparedWithNonTagList_listNotEqual() throws DuplicateTagException, IllegalValueException {
        UniqueTagList list1 = new UniqueTagList(new Tag("tag1"), new Tag("tag2"));
        ArrayList<Tag> list2 = new ArrayList<Tag>();
        
        list2.add(new Tag("tag1"));
        list2.add(new Tag("tag2"));
        
        assertFalse(list1.equals(list2));
    }
    
    @Test
    public void equalTagList_comparedWithTagListOfDifferentSize_listNotEqual() throws DuplicateTagException, IllegalValueException {
        UniqueTagList list1 = new UniqueTagList(new Tag("tag1"), new Tag("tag2"));
        UniqueTagList list2 = new UniqueTagList(new Tag("tag3"), new Tag("tag4"), new Tag("tag5"));
        
        assertFalse(list1.equals(list2));
    }
    
    @Test
    public void hashCode_differentTagList_differentHashCode() throws DuplicateTagException, IllegalValueException {
        UniqueTagList list1 = new UniqueTagList(new Tag("tag1"), new Tag("tag2"));
        UniqueTagList list2 = new UniqueTagList(new Tag("tag3"), new Tag("tag4"), new Tag("tag5"));
        assertFalse(list1.hashCode() == list2.hashCode());
    }
    
}
