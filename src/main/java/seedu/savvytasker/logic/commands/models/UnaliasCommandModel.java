package seedu.savvytasker.logic.commands.models;

/**
 * Represents a model for use with the unalias command.
 */
public class UnaliasCommandModel extends CommandModel {
    
    private String shortKeyword;
    
    /**
     * Creates the model to be used with the unalias command.
     * @param shortKeyword The keyword to replace with.
     */
    public UnaliasCommandModel(String shortKeyword) {
        assert(shortKeyword != null && !shortKeyword.isEmpty());
        this.shortKeyword = shortKeyword;
    }
    
    public String getShortKeyword() {
        return shortKeyword;
    }

    public void setShortKeyword(String shortKeyword) {
        assert(shortKeyword != null && !shortKeyword.isEmpty());
        this.shortKeyword = shortKeyword;
    }
}
