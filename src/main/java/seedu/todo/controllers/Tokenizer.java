package seedu.todo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.todo.commons.exceptions.UnmatchedQuotesException;

public class Tokenizer {

    private final static String QUOTE = "\"";

    private static class TokenizedString {
        public String string;
        public boolean isToken;
        public boolean isQuote;

        TokenizedString(String string, boolean isToken, boolean isQuote) {
            this.string = string;
            this.isToken = isToken;
            this.isQuote = isQuote;
        }

        @Override
        public String toString() {
            return String.format("TokenizedString(%s, %s, %s)", this.string, isToken, isQuote);
        }
    }

    public static HashMap<String, String[]> tokenize(HashMap<String, String[]> tokenDefinitions, String inputCommand)
            throws UnmatchedQuotesException {
        
        // Generate token -> tokenType mapping and list of tokens
        List<String> tokens = new ArrayList<String>();
        HashMap<String, String> getTokenType = new HashMap<String, String>();
        for (Map.Entry<String, String[]> tokenDefinition : tokenDefinitions.entrySet()) {
            String tokenType = tokenDefinition.getKey();
            for (String token : tokenDefinition.getValue()) {
                tokens.add(token);
                getTokenType.put(token, tokenType);
            }
        }

        if (inputCommand.length() == 0)
            return null;

        // Split inputCommand into arraylist of chunks

        if (StringUtils.countMatches(inputCommand, QUOTE) % 2 == 1)
            throw new UnmatchedQuotesException("Unmatched double-quotes detected.");

        // --- Split by quotes
        String[] splitString = inputCommand.split(QUOTE);

        // If first char is QUOTE, then first element is a quoted string.
        List<TokenizedString> tokenizedSplitString = new ArrayList<TokenizedString>();
        for (int i = 0; i < splitString.length; i++) {
            tokenizedSplitString.add(new TokenizedString(splitString[i].trim(), false, (i % 2 == 1)));
        }
        
        // --- Split by tokens
        for (int i = 0; i < tokenizedSplitString.size(); i++) { // Java doesn't eager-evaluate the terminating condition
            TokenizedString currString = tokenizedSplitString.get(i);
            if (currString.isQuote || currString.isToken)
                continue;
            
            // Try to match all the tokens
            for (String token : tokens) {
                Matcher m = Pattern.compile(String.format("\\b%s\\b", token)).matcher(currString.string);
                if (!m.find())
                    continue;
                
                // Found. Replace current element with split elements.
                String preString = currString.string.substring(0, m.start()).trim();
                String postString = currString.string.substring(m.end(), currString.string.length()).trim();
                
                tokenizedSplitString.remove(i);
                List<TokenizedString> replacedSplitStrings = new ArrayList<TokenizedString>();
                if (!preString.isEmpty())
                    replacedSplitStrings.add(new TokenizedString(preString, false, false));
                replacedSplitStrings.add(new TokenizedString(token, true, false));
                if (!postString.isEmpty())
                    replacedSplitStrings.add(new TokenizedString(postString, false, false));
                tokenizedSplitString.addAll(i, replacedSplitStrings);
                
                // Update currString and resume.
                currString = tokenizedSplitString.get(i);
                if (currString.isQuote || currString.isToken)
                    break;
            }   
        }
        
        // Get arraylist of indices
        // Get dictionary of tokenType -> index
        
        // Return dictionary of tokenType -> {token, tokenField}
        
        
        return null;
    }

}

