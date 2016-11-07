package seedu.todo.model;

import seedu.todo.commons.core.Config;
import seedu.todo.commons.core.GuiSettings;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.storage.FixedStorage;
import seedu.todo.storage.UserPrefsStorage;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

//@@author A0139021U-reused
/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private static final Logger logger = LogsCenter.getLogger(UserPrefs.class);
    private FixedStorage<UserPrefs> storage;

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public UserPrefs(){
        this.setDefaultGuiSettings();
    }

    public UserPrefs(Config config) {
        assert config != null;

        String prefsFilePath = config.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        this.storage = new UserPrefsStorage(prefsFilePath);
        try {
            this.updateLastUsedGuiSetting(storage.read().getGuiSettings());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. " +
                    "Using default user prefs");
            this.setDefaultGuiSettings();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Default settings will be used.");
            this.setDefaultGuiSettings();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        this.save();
    }

    private void setDefaultGuiSettings() {
        this.setGuiSettings(680, 780, 0, 0);
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public void save() {
        try {
            storage.save(this);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
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
