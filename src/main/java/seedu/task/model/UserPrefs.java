package seedu.task.model;

import java.util.Objects;

import java.util.HashMap;
import seedu.task.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {
    //@@author A0144939R
    private HashMap<String, String> aliases = new HashMap<String, String>();
    //@@author
    public GuiSettings guiSettings;

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public UserPrefs(){
        this.setGuiSettings(500, 500, 0, 0);
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this){
            return true;
        }
        if (!(other instanceof UserPrefs)){ //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs)other;

        return Objects.equals(guiSettings, o.guiSettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings);
    }

    @Override
    public String toString(){
        return guiSettings.toString();
    }
    
    //@@author A0144939R
    /**
     * Gets mapping for a given alias.
     * @param alias: a user defined string
     * @return The command represented by the alias, or null if no mapping exists
     */
    public String getAliasMapping(String alias) {
        if(aliases.containsKey(alias)) {
            return aliases.get(alias);
        } else {
            return null;
        }
    }
    
    /**
     * Sets mapping for given alias
     * @param command
     * @param alias
     */
    public void setMapping(String command, String alias) {
        aliases.put(command, alias);
    }
}
