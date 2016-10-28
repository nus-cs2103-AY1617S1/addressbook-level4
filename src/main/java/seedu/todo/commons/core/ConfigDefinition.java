package seedu.todo.commons.core;

/**
 * @@author A0139812A
 */
public class ConfigDefinition {
    private String configName;
    private String configDescription;
    private String configValue;
    
    public ConfigDefinition(String configName, String configDescription, String configValue) {
        this.configName = configName;
        this.configDescription = configDescription;
        this.configValue = configValue;
    }
    
    public String getConfigName() {
        return configName;
    }
    
    public String getConfigDescription() {
        return configDescription;
    }
    
    public String getConfigValue() {
        return configValue;
    }
}
