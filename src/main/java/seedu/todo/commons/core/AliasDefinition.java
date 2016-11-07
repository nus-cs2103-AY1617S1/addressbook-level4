package seedu.todo.commons.core;

// @@author A0093907W
/**
 * Container class to store and retrieve a pair of values for an alias key -> value pair.
 */
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
