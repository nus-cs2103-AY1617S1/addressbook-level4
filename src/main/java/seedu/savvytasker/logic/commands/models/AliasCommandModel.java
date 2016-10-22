package seedu.savvytasker.logic.commands.models;

/**
 * Represents a model for use with the alias command.
 */
public class AliasCommandModel extends CommandModel {
    
    private String keyword;
    private String representingText;
    
    /**
     * Creates the model to be used with the alias command.
     * @param keyword The keyword.
     * @param representingText The text that the keyword represents.
     */
    public AliasCommandModel(String keyword, String representingText) {
        this.keyword = keyword;
        this.representingText = representingText;
    }
    
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    public String getRepresentingText() {
        return representingText;
    }

    public void setRepresentingText(String shortKeyword) {
        this.representingText = shortKeyword;
    }
}
