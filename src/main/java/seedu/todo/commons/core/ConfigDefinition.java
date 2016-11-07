package seedu.todo.commons.core;

// @@author A0139812A
/**
 * Container class to store and retrieve config value properties.
 * Each ConfigDefinition refers to a single config value (e.g. {@code appTitle})
 * and stores the name, description and the current value.
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
