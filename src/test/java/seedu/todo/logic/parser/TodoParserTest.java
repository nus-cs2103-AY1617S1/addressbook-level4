package seedu.todo.logic.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;

public class TodoParserTest {
    private Parser parser = new TodoParser();
    
    @Test
    public void testParse() throws IllegalValueException {
        ParseResult p;
        
        p = parser.parse("hello");
        assertEquals("hello", p.getComand());
        
        p = parser.parse("HeLLo");
        assertEquals("hello", p.getComand());
        
        p = parser.parse("HELLO");
        assertEquals("hello", p.getComand());
        
        p = parser.parse("hello world");
        assertEquals("hello", p.getComand());
        
        p = parser.parse("  hello ");
        assertEquals("hello", p.getComand());
    }
    
    @Test
    public void testPositionalArgument() throws IllegalValueException {
        ParseResult p;
        
        p = parser.parse("hello world");
        assertEquals("world", p.getPositionalArgument().get());
        
        p = parser.parse("hello one two three");
        assertEquals("one two three", p.getPositionalArgument().get());
        
        p = parser.parse("hello   one   two  three  ");
        assertEquals("one two three", p.getPositionalArgument().get());
    }
    
    @Test
    public void testNamedArguments() throws IllegalValueException {
        ParseResult p;
        
        p = parser.parse("hello /f");
        assertEquals(1, p.getNamedArguments().size());
        assertTrue(p.getNamedArguments().containsKey("f"));
        
        p = parser.parse("hello /f Hello");
        assertEquals(1, p.getNamedArguments().size());
        assertEquals("Hello", p.getNamedArguments().get("f"));
        
        p = parser.parse("hello  /f   Hello ");
        assertEquals(1, p.getNamedArguments().size());
        assertEquals("Hello", p.getNamedArguments().get("f"));
        
        p = parser.parse("hello /all Hello");
        assertEquals(1, p.getNamedArguments().size());
        assertEquals("Hello", p.getNamedArguments().get("all"));
        
        p = parser.parse("hello /f Hello /p /all");
        assertEquals(3, p.getNamedArguments().size());
        assertEquals("Hello", p.getNamedArguments().get("f"));
        assertTrue(p.getNamedArguments().containsKey("p"));
        assertTrue(p.getNamedArguments().containsKey("all"));
    }
    
    @Test
    public void testInvalidFlags() throws IllegalValueException {
        ParseResult p;
        
        p = parser.parse("hello /");
        assertTrue(p.getPositionalArgument().isPresent());
        assertEquals(0, p.getNamedArguments().size());
    }

}
