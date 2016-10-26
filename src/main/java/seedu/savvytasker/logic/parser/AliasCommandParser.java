//@@author A0139916U
package seedu.savvytasker.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.logic.commands.AliasCommand;

public class AliasCommandParser implements CommandParser<AliasCommand> {
    private static final String HEADER = "alias";
    private static final String READABLE_FORMAT = HEADER+" k/KEYWORD r/REPRESENTATION";
    
    private static final String REGEX_REF_REPRESENTATION = "Text";
    private static final String REGEX_REF_KEYWORD = "Keyword";
    
    private static final Pattern REGEX_PATTERN = Pattern.compile(
            HEADER+"\\s+((?<=\\s)(" +
                    "(r/(?<"+REGEX_REF_REPRESENTATION+">[^/]+)(?!.*\\sr/))|" +
                    "(k/(?<"+REGEX_REF_KEYWORD+">[^/]+)(?!.*\\sk/))" +
                    ")(\\s|$)){2}"
    );
    
    @Override
    public String getHeader() {
        return HEADER;
    }

    @Override
    public String getRequiredFormat() {
        return READABLE_FORMAT;
    }

    @Override
    public boolean shouldPreprocess() {
        return false;
    }
    
    @Override
    public AliasCommand parse(String commandText) throws ParseException {
        Matcher matcher = REGEX_PATTERN.matcher(commandText);
        if (matcher.matches()) {
            String representation = parseRepresentation(matcher.group(REGEX_REF_REPRESENTATION));
            String keyword = parseKeyword(matcher.group(REGEX_REF_KEYWORD));
            return new AliasCommand(keyword, representation);
        }

        throw new ParseException(commandText, String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, getRequiredFormat()));
    }
    
    private String parseRepresentation(String originalText) throws ParseException {
        String trimmedText = originalText.trim();
        
        if (trimmedText.isEmpty())
            throw new ParseException(trimmedText, "REPRESENTATION: Needs to be at least one character!");
        
        return trimmedText;
    }

    private String parseKeyword(String keywordText) throws ParseException {
        String trimmedKeywordText = keywordText.trim();
        if (trimmedKeywordText.length() < 2) {
            throw new ParseException(trimmedKeywordText, "KEYWORD: Needs to consist at least 2 character.");
        }
        if (trimmedKeywordText.matches(".*\\s+.*")) {
            throw new ParseException(trimmedKeywordText, "KEYWORD: Needs to be a single word without spaces.");
        }
        return trimmedKeywordText;
    }
}
