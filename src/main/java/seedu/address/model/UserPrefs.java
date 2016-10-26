package seedu.address.model;

import seedu.address.commons.core.GuiSettings;
import seedu.address.storage.XmlAddressBookStorage;

import java.util.Objects;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    public GuiSettings guiSettings;
    public static String savePath;

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public UserPrefs(){
        XmlAddressBookStorage xml = new XmlAddressBookStorage();
        this.setGuiSettings(500, 500, 0, 0);
        this.setDataFilePath(xml.getAddressBookFilePath());
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }
    
    public static void setDataFilePath(String filePath) {
        savePath = filePath;
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
        StringBuilder sb = new StringBuilder();
        sb.append(guiSettings.toString() + "\n");
        sb.append("Save Path: " + savePath);
        return sb.toString();
    }

}
