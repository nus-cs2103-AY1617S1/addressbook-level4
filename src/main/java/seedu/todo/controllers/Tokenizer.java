package seedu.todo.controllers;

import java.util.HashMap;

public class Tokenizer {
    
    private class TokenizedString {
        public String string;
        public boolean isToken;
        public boolean isQuote;
        
        TokenizedString(String string, boolean isToken, boolean isQuote) {
            this.string = string;
            this.isToken = isToken;
            this.isQuote = isQuote;
        }
        
    }
    
    public static HashMap<String, String[]> tokenize(HashMap<String, String[]> tokenDefinitions, String inputCommand) {
        // Split inputCommand into arraylist of chunks
        
        // --- Split by quotes
        // --- Split by tokens
        
        // Get arraylist of indices
        // Get dictionary of tokenType -> index
        
        // Return dictionary of tokenType -> {token, tokenField}
        
        
        return null;
    }

}

