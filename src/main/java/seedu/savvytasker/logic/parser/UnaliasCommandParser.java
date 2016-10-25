package seedu.savvytasker.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.logic.commands.UnaliasCommand;

public class UnaliasCommandParser implements CommandParser<UnaliasCommand> {
    private static final String HEADER = "unalias";
    private static final String READABLE_FORMAT = HEADER+" KEYWORD";
    
    private static final String REGEX_REF_KEYWORD = "Keyword";
    
    private static final Pattern REGEX_PATTERN = Pattern.compile(
            HEADER+"\\s+(?<"+REGEX_REF_KEYWORD+">[^/]+)"
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
    public UnaliasCommand parse(String commandText) throws ParseException {
        Matcher matcher = REGEX_PATTERN.matcher(commandText);
        if (matcher.matches()) {
            String keyword = parseKeyword(matcher.group(REGEX_REF_KEYWORD));
            return new UnaliasCommand(keyword);
        }

        throw new ParseException(commandText, String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, getRequiredFormat()));
    }

    private String parseKeyword(String keywordText) throws ParseException {
        String trimmedKeywordText = keywordText.trim();
        if (trimmedKeywordText.isEmpty()) {
            throw new ParseException(trimmedKeywordText, "KEYWORD: Cannot be empty.");
        }
        return trimmedKeywordText;
    }

}
