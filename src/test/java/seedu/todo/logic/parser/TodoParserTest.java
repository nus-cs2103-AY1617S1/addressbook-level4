package seedu.todo.logic.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.todo.logic.parser.ParseResult;
import seedu.todo.logic.parser.Parser;
import seedu.todo.logic.parser.TodoParser;

//@@author A0135817B
public class TodoParserTest {
    private Parser parser = new TodoParser();
    
    @Test
    public void testParse() {
        ParseResult p;
        
        p = parser.parse("hello");
        assertEquals("hello", p.getCommand());
        
        p = parser.parse("HeLLo");
        assertEquals("hello", p.getCommand());
        
        p = parser.parse("HELLO");
        assertEquals("hello", p.getCommand());
        
        p = parser.parse("hello world");
        assertEquals("hello", p.getCommand());
        
        p = parser.parse("  hello ");
        assertEquals("hello", p.getCommand());
    }
    
    @Test
    public void testPositionalArgument() {
        ParseResult p;
        
        p = parser.parse("hello world");
        assertEquals("world", p.getPositionalArgument().get());
        
        p = parser.parse("hello one two three");
        assertEquals("one two three", p.getPositionalArgument().get());
        
        p = parser.parse("hello   one   two  three  ");
        assertEquals("one two three", p.getPositionalArgument().get());
    }
    
    @Test
    public void testNamedArguments() {
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
    public void testInvalidFlags() {
        ParseResult p;
        
        p = parser.parse("hello /");
        assertTrue(p.getPositionalArgument().isPresent());
        assertEquals(0, p.getNamedArguments().size());
    }

}
