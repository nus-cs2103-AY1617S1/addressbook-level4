package harmony.mastermind.commons.events.ui;

import java.util.ArrayList;

import harmony.mastermind.commons.events.BaseEvent;
import harmony.mastermind.logic.HelpPopupEntry;

/**@@author A0139194X
 * An event requesting to view the help page.
 */
public class ShowHelpRequestEvent extends BaseEvent {

    private final ArrayList<HelpPopupEntry> helpEntries;
    
    public ShowHelpRequestEvent(ArrayList<HelpPopupEntry> helpEntries) {
        this.helpEntries = helpEntries;
    }
    
    public ArrayList<HelpPopupEntry> getHelpEntries() {
        return this.helpEntries;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
