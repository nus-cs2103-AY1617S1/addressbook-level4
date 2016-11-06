package tars.model;

import java.util.Objects;

import tars.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private static final int GUI_SETTINGS_DEFAULT_Y_POSITION = 0;
    private static final int GUI_SETTINGS_DEFAULT_X_POSITION = 0;
    private static final int GUI_SETTINGS_DEFAULT_HEIGHT = 500;
    private static final int GUI_SETTINGS_DEFAULT_WIDTH = 500;
    public GuiSettings guiSettings;

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public UserPrefs(){
        this.setGuiSettings(GUI_SETTINGS_DEFAULT_WIDTH,
                GUI_SETTINGS_DEFAULT_HEIGHT, GUI_SETTINGS_DEFAULT_X_POSITION,
                GUI_SETTINGS_DEFAULT_Y_POSITION);
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

}
