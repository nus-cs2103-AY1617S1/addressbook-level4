package seedu.savvytasker.logic.commands.models;

import seedu.savvytasker.model.task.FindType;

/**
 * Represents a model for use with the find command.
 */
public class FindCommandModel extends CommandModel {
    
    private FindType findType;
    private String[] keywords;
    
    /**
     * Creates a model to be used with the find command.
     * @param findType Type of match
     * @param keywords Keywords to match, must not be null
     */
    public FindCommandModel(FindType findType, String[] keywords) {
        assert (keywords != null);
        this.keywords = keywords;
        this.findType = findType;
    }
    
    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        assert (keywords != null);
        this.keywords = keywords;
    }
    
    public FindType getFindType() {
        return findType;
    }

    public void setFindType(FindType findType) {
        this.findType = findType;
    }
}
