package seedu.todo.logic.parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class TodoParserTest {

    @Test
    public void testParse() {
        TodoParser p;
        
        p = new TodoParser("hello");
        assertEquals("hello", p.getComand());
        
        p = new TodoParser("HeLLo");
        assertEquals("hello", p.getComand());
        
        p = new TodoParser("HELLO");
        assertEquals("hello", p.getComand());
        
        p = new TodoParser("hello world");
        assertEquals("hello", p.getComand());
    }
    
    @Test
    public void testPositionalArgument() {
        TodoParser p;
        
        p = new TodoParser("hello world");
        assertEquals("world", p.getPositionalArgument().get());
        
        p = new TodoParser("hello one two three");
        assertEquals("one two three", p.getPositionalArgument().get());
    }
    
    @Test
    public void testNamedArguments() {
        TodoParser p;
        
        p = new TodoParser("hello -f");
        assertEquals(1, p.getNamedArguments().size());
        assertTrue(p.getNamedArguments().containsKey("f"));
        
        p = new TodoParser("hello -f Hello");
        assertEquals(1, p.getNamedArguments().size());
        assertEquals("Hello", p.getNamedArguments().get("f"));
        
        p = new TodoParser("hello --all Hello");
        assertEquals(1, p.getNamedArguments().size());
        assertEquals("Hello", p.getNamedArguments().get("all"));
        
        p = new TodoParser("hello -f Hello -p --all");
        assertEquals(3, p.getNamedArguments().size());
        assertEquals("Hello", p.getNamedArguments().get("f"));
        assertTrue(p.getNamedArguments().containsKey("p"));
        assertTrue(p.getNamedArguments().containsKey("all"));
    }
    
    @Test
    public void testInvalidFlags() {
        TodoParser p;
        
        p = new TodoParser("hello");
        assertFalse(p.getPositionalArgument().isPresent());
        assertEquals(0, p.getNamedArguments().size());
        
        p = new TodoParser("hello --");
        assertTrue(p.getPositionalArgument().isPresent());
        assertEquals(0, p.getNamedArguments().size());
        
        p = new TodoParser("hello --a");
        assertEquals(0, p.getNamedArguments().size());
        
        p = new TodoParser("hello -all");
        assertEquals(0, p.getNamedArguments().size());
    }

}
