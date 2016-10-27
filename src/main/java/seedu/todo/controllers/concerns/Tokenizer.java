package seedu.todo.controllers.concerns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import seedu.todo.commons.exceptions.UnmatchedQuotesException;

/**
 * Class to store the static method <code>tokenizer</code>.
 * 
 * @@author A0093907W
 */
public class Tokenizer {

    private static final String UNMATCHED_QUOTES_MESSAGE = "Unmatched double-quotes detected.";
    private final static String QUOTE = "\"";

    /**
     * A private class to tag a string as a token or a quote.
     * @author louietyj
     *
     */
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

    /**
     * Tokenizer method to parse a user-input string into a mapping of tokenType -> tokenField.
     * <ul>
     *   <li>Quoted chunks are kept as a whole and never matched to a token.</li>
     *   <li>If there are multiple token matches, only the first one will be registered.</li>
     *   <li>If there are multiple tokenType matches, only one match will be returned.</li>
     * </ul>
     * @param tokenDefinitions  Mapping of tokenType -> list of token strings to match
     * @param inputCommand      User input to tokenize
     * @return                  Mapping of tokenType -> { matchedToken, tokenField }
     * @throws UnmatchedQuotesException If there is an odd number of quotes
     */
    public static Map<String, String[]> tokenize(Map<String, String[]> tokenDefinitions, String inputCommand)
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

        if (inputCommand.length() == 0) {
            return null;
        }

        // Split inputCommand into arraylist of chunks

        if (StringUtils.countMatches(inputCommand, QUOTE) % 2 == 1) {
            throw new UnmatchedQuotesException(UNMATCHED_QUOTES_MESSAGE);
        }

        // --- Split by quotes
        String[] splitString = inputCommand.split(QUOTE);

        // If first char is QUOTE, then first element is a quoted string.
        List<TokenizedString> tokenizedSplitString = new ArrayList<TokenizedString>();
        for (int i = 0; i < splitString.length; i++) {
            tokenizedSplitString.add(new TokenizedString(splitString[i].trim(), false, (i % 2 == 1)));
        }
        
        // --- Split by tokens
        Map<String, Integer> tokenIndices = splitByTokens(tokens, getTokenType, tokenizedSplitString);
        
        // Get arraylist of indices
        // Get dictionary of tokenType -> index
        // Return dictionary of tokenType -> {token, tokenField}
        return constructParsedResult(tokenizedSplitString, tokenIndices);
    }

    /**
     * Constructs the parsedResult from user input that has been delimited and tokenized.
     * @param tokenizedSplitString
     * @param tokenIndices
     * @return parsedResult
     */
    private static Map<String, String[]> constructParsedResult(List<TokenizedString> tokenizedSplitString,
            Map<String, Integer> tokenIndices) {
        Map<String, String[]> parsedResult = new HashMap<String, String[]>();
        for (Map.Entry<String, Integer> tokenIndex : tokenIndices.entrySet()) {
            String tokenType = tokenIndex.getKey();
            String token = tokenizedSplitString.get(tokenIndex.getValue()).string;
            String tokenField = null;
            // Should just EAFP instead of LBYL, but oh well.
            if (tokenIndex.getValue() + 1 < tokenizedSplitString.size() && !tokenizedSplitString.get(tokenIndex.getValue() + 1).isToken) {
                tokenField = tokenizedSplitString.get(tokenIndex.getValue() + 1).string;
            }
            parsedResult.put(tokenType, new String[] { token, tokenField });
        }
        return parsedResult;
    }

    /**
     * Re-implementation from scratch of
     * <code>tokens.split("token1|token2|token3|...")</code> with the constraint
     * that quoted strings are kept intact and unmatched.
     * 
     * @param tokens
     *            List of tokens to match
     * @param getTokenType
     *            Mapping of token -> tokenType
     * @param tokenizedSplitString
     *            User input with quoted strings tagged. This method will modify
     *            tokenizedSplitString in-place.
     * @return Indexes of matched tokens
     */
    private static Map<String, Integer> splitByTokens(List<String> tokens, HashMap<String, String> getTokenType,
            List<TokenizedString> tokenizedSplitString) {
        Map<String, Integer> tokenIndices = new HashMap<String, Integer>();
        for (int i = 0; i < tokenizedSplitString.size(); i++) { // Java doesn't eager-evaluate the terminating condition
            TokenizedString currString = tokenizedSplitString.get(i);
            if (currString.isQuote) {
                continue;
            }
            
            // Record token.
            if (currString.isToken) {
                tokenIndices.put(getTokenType.get(currString.string), i);
                tokens.remove(currString.string);
                continue;
            }
            
            // Try to match all the tokens
            for (String token : tokens) {
                Matcher m = Pattern.compile(String.format("\\b%s\\b", token), Pattern.CASE_INSENSITIVE)
                        .matcher(currString.string);
                if (!m.find()) {
                    continue;
                }
                
                // Found. Replace current element with split elements.
                String preString = currString.string.substring(0, m.start()).trim();
                String postString = currString.string.substring(m.end(), currString.string.length()).trim();
                
                tokenizedSplitString.remove(i);
                List<TokenizedString> replacedSplitStrings = new ArrayList<TokenizedString>();
                if (!preString.isEmpty()) {
                    replacedSplitStrings.add(new TokenizedString(preString, false, false));
                }
                replacedSplitStrings.add(new TokenizedString(token, true, false));
                if (!postString.isEmpty()) {
                    replacedSplitStrings.add(new TokenizedString(postString, false, false));
                }
                tokenizedSplitString.addAll(i, replacedSplitStrings);
                
                // Restart outer loop at current index.
                i--;
                break;
            }   
        }
        return tokenIndices;
    }

}

