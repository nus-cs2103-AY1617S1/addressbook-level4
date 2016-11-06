package seedu.todo.model.tag;

import org.junit.Test;

import static org.junit.Assert.*;

//@@author A0135817B
public class TagTest {
    @Test
    public void testSimpleEquality() {
        Tag t = new Tag("world");
        assertEquals(t, t);
        assertEquals(new Tag("hello"), new Tag("hello"));
        assertEquals(new Tag("world"), t);
    }
    
    @Test
    public void testLowerCaseEquality() {
        assertEquals(new Tag("HELLO"), new Tag("hello"));
        assertEquals(new Tag("heLlo"), new Tag("HelLo"));
        assertEquals(new Tag("HELLO"), new Tag("hello"));
    }
    
    @Test
    public void testHashCode() {
        assertEquals(new Tag("hello").hashCode(), new Tag("hello").hashCode());
        assertEquals(new Tag("HEllO").hashCode(), new Tag("hello").hashCode());
        assertEquals(new Tag("heLlo").hashCode(), new Tag("hello").hashCode());
    }
}
