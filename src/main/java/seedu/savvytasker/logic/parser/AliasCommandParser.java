package seedu.savvytasker.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.logic.commands.AliasCommand;
import seedu.savvytasker.logic.commands.models.AliasCommandModel;

public class AliasCommandParser implements CommandParser<AliasCommand> {
    private static final String HEADER = "alias";
    private static final String READABLE_FORMAT = HEADER+" t/TEXT k/KEYWORD";
    
    private static final String REGEX_REF_ORIGINAL = "Text";
    private static final String REGEX_REF_REPLACEMENT = "Keyword";
    
    private static final Pattern REGEX_PATTERN = Pattern.compile(
            HEADER+"\\s+((?<=\\s)(" +
                    "(t/(?<"+REGEX_REF_ORIGINAL+">[^/]+)(?!.*\\st/))|" +
                    "(k/(?<"+REGEX_REF_REPLACEMENT+">[^/]+)(?!.*\\sk/))" +
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
    public AliasCommand parse(String commandText) throws ParseException {
        Matcher matcher = REGEX_PATTERN.matcher(commandText);
        if (matcher.matches()) {
            String original = parseOriginal(matcher.group(REGEX_REF_ORIGINAL));
            String replacement = parseReplacement(matcher.group(REGEX_REF_REPLACEMENT));
            return new AliasCommand(new AliasCommandModel(original, replacement));
        }

        throw new ParseException(commandText, String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, getRequiredFormat()));
    }
    
    private static String parseOriginal(String originalText) throws ParseException {
        String trimmedText = originalText.trim();
        
        if (trimmedText.isEmpty())
            throw new ParseException(trimmedText, "TEXT: Needs to be at least one character!");
        
        return trimmedText;
    }

    private static String parseReplacement(String keywordText) throws ParseException {
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
