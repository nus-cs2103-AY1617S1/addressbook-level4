package seedu.cmdo.model;

import seedu.cmdo.commons.core.GuiSettings;
import seedu.cmdo.commons.core.StorageSettings;

import java.util.Objects;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    public GuiSettings guiSettings;
    
    public StorageSettings storageSettings;

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }
    
    public StorageSettings getStorageSettings() {
    	return storageSettings == null? new StorageSettings() : storageSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }
    
    public void updateLastUsedStorageSetting(StorageSettings storageSettings) {
    	this.storageSettings = storageSettings;
    }

    public UserPrefs(){
        this.setGuiSettings(500, 500, 0, 0);
        this.setStorageSettings("data/cmdo.xml");
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }
    
    public void setStorageSettings(String filePath) {
    	storageSettings = new StorageSettings(filePath);
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

}
