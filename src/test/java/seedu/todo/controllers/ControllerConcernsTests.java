package seedu.todo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.junit.*;
import org.junit.Assert.*;

import seedu.todo.commons.exceptions.UnmatchedQuotesException;
import seedu.todo.controllers.concerns.Tokenizer;

public class ControllerConcernsTests {
    
    private static Map<String, String[]> getTokenDefinitions() {
        Map<String, String[]> tokenDefinitions = new HashMap<String, String[]>();
        tokenDefinitions.put("tokenType1", new String[] {"token11"});
        tokenDefinitions.put("tokenType2", new String[] {"token21", "token22"});
        tokenDefinitions.put("tokenType3", new String[] {"token31", "token32", "token33"});
        return tokenDefinitions;
    }
    
}
