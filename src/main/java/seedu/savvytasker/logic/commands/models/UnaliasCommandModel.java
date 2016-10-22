package seedu.savvytasker.logic.commands.models;

/**
 * Represents a model for use with the unalias command.
 */
public class UnaliasCommandModel extends CommandModel {
    
    private String keyword;
    
    /**
     * Creates the model to be used with the unalias command.
     * @param shortKeyword The keyword to replace with.
     */
    public UnaliasCommandModel(String keyword) {
        assert(keyword != null && !keyword.isEmpty());
        this.keyword = keyword;
    }
    
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        assert(keyword != null && !keyword.isEmpty());
        this.keyword = keyword;
    }
}
