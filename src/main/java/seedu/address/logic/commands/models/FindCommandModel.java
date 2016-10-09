package seedu.address.logic.commands.models;

import java.util.Set;

/**
 * Represents a model for use with the find command.
 */
public class FindCommandModel extends CommandModel {
    
    private Set<String> keywords;
    
    public FindCommandModel(Set<String> keywords) {
        this.keywords = keywords;
    }
    
    public Set<String> getTargetIndex() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }
}
