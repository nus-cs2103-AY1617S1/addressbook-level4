package seedu.todo.commons.core;

// @@author A0093907W
public class AliasDefinition {
    private String aliasKey;
    private String aliasValue;
    
    public AliasDefinition(String aliasKey, String aliasValue) {
        this.aliasKey = aliasKey;
        this.aliasValue = aliasValue;
    }
    
    public String getAliasKey() {
        return aliasKey;
    }

    public String getAliasValue() {
        return aliasValue;
    }

}
