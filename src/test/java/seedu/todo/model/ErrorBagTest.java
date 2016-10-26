package seedu.todo.model;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.todo.commons.exceptions.ValidationException;

//@@author A0135817B
public class ErrorBagTest {
    
    private ErrorBag errorBag = new ErrorBag();

    @Test
    public void testPutString() {
        errorBag.put("Hello World");
        errorBag.put("Another error");
        
        assertEquals(0, errorBag.getNonFieldErrors().indexOf("Hello World"));
        assertEquals(1, errorBag.getNonFieldErrors().indexOf("Another error"));
        
        assertEquals(2, errorBag.size());
        assertEquals(0, errorBag.getFieldErrors().size());
    }

    @Test
    public void testPutStringString() {
        errorBag.put("a", "Hello World");
        errorBag.put("b", "Another error");
        
        assertEquals("Hello World", errorBag.getFieldErrors().get("a"));
        assertEquals("Another error", errorBag.getFieldErrors().get("b"));
        
        assertEquals(2, errorBag.size());
        assertEquals(0, errorBag.getNonFieldErrors().size());
    }

    @Test
    public void testSize() {
        errorBag.put("Hello World");
        errorBag.put("a", "Hello World");
        
        assertEquals(2, errorBag.size());
    }

    @Test
    public void testValidateNoErrors() throws ValidationException {
        assertEquals(0, errorBag.size());
        errorBag.validate("Validation message");
    }
    

    @Test(expected=ValidationException.class)
    public void testValidateWithErrors() throws ValidationException {
        errorBag.put("Hello World");
        errorBag.put("a", "Hello World");
        
        errorBag.validate("Validation message");
    }
    
    @Test
    public void testValidationException() {
        errorBag.put("Hello World");
        errorBag.put("a", "Hello World");
        
        try {
            errorBag.validate("Validation message");
        } catch (ValidationException e) {
            assertEquals(errorBag, e.getErrors());
            assertEquals("Validation message", e.getMessage());
        }
        
    }

}
