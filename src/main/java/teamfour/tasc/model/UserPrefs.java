package teamfour.tasc.model;

import java.util.Objects;

import teamfour.tasc.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    public GuiSettings guiSettings;
    private String calendarView;

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public UserPrefs(){
        this.setGuiSettings(500, 500, 0, 0);
        this.calendarView = "week";
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }
    
    public String getCalendarView() {
        return calendarView;
    }
    
    public void setCalendarView(String calendarView) {
        this.calendarView = calendarView;
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

        return Objects.equals(guiSettings, o.guiSettings) &&
                calendarView.equals(o.calendarView);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, calendarView);
    }

    @Override
    public String toString(){
        return guiSettings.toString() + "\nCalendar View : " + calendarView;
    }

}
