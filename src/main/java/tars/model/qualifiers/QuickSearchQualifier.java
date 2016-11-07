package tars.model.qualifiers;

//@@author A0124333U

import java.util.ArrayList;

import tars.commons.util.StringUtil;
import tars.model.task.ReadOnlyTask;

public class QuickSearchQualifier implements Qualifier {

    private static final String LABEL_TAGS = "Tags: ";
    private static final String LABEL_STATUS = "Status: ";
    private static final String LABEL_PRIORITY = "Priority: ";
    private static final String LABEL_DATETIME = "DateTime: ";
    private final ArrayList<String> quickSearchKeywords;

    public QuickSearchQualifier(ArrayList<String> quickSearchKeywords) {
        this.quickSearchKeywords = quickSearchKeywords;
    }

    private String removeLabels(String taskAsString) {
        String editedString = taskAsString
                .replace(StringUtil.STRING_SQUARE_BRACKET_OPEN,
                        StringUtil.EMPTY_STRING)
                .replace(StringUtil.STRING_SQUARE_BRACKET_CLOSE,
                        StringUtil.STRING_WHITESPACE)
                .replace(LABEL_DATETIME, StringUtil.EMPTY_STRING)
                .replace(LABEL_PRIORITY, StringUtil.EMPTY_STRING)
                .replace(LABEL_STATUS, StringUtil.EMPTY_STRING)
                .replace(LABEL_TAGS, StringUtil.EMPTY_STRING);
        return editedString;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        String taskAsString = removeLabels(task.getAsText());
        return quickSearchKeywords.stream().filter(
                keyword -> StringUtil.containsIgnoreCase(taskAsString, keyword))
                .count() == quickSearchKeywords.size();
    }

}
