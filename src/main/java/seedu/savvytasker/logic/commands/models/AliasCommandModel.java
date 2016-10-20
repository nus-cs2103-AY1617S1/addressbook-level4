package seedu.savvytasker.logic.commands.models;

/**
 * Represents a model for use with the alias command.
 */
public class AliasCommandModel extends CommandModel {
    
    private String keyword;
    private String shortKeyword;
    
    /**
     * Creates the model to be used with the alias command.
     * @param keyword The keyword to replace.
     * @param shortKeyword The keyword to replace with.
     */
    public AliasCommandModel(String keyword, String shortKeyword) {
        this.keyword = keyword;
        this.shortKeyword = shortKeyword;
    }
    
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    public String getShortKeyword() {
        return shortKeyword;
    }

    public void setShortKeyword(String shortKeyword) {
        this.shortKeyword = shortKeyword;
    }
}
