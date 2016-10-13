package seedu.savvytasker.logic.commands.models;

import java.util.HashSet;
import java.util.Set;

import seedu.savvytasker.model.task.FindType;

/**
 * Represents a model for use with the find command.
 */
public class FindCommandModel extends CommandModel {
    
    private FindType findType;
    private Set<String> keywords;
    
    /**
     * Creates a model to be used with the find command.
     * @param findType Type of match
     * @param keywords Keywords to match, must not be null
     */
    public FindCommandModel(FindType findType, String[] keywords) {
        assert (keywords != null);
        this.keywords = createSet(keywords);
        this.findType = findType;
    }
    
    /**
     * Helper method to build Set<String> from String[]
     * @param keywords list of keywords
     */
    private static Set<String> createSet(String[] keywords) {
        HashSet<String> _keywords = new HashSet<String>();
        for (String keyword : keywords) {
            _keywords.add(keyword);
        }
        return _keywords;
    }
    
    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        assert (keywords != null);
        this.keywords = createSet(keywords);
    }
    
    public FindType getFindType() {
        return findType;
    }

    public void setFindType(FindType findType) {
        this.findType = findType;
    }
}
