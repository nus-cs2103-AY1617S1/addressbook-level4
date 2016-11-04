package harmony.mastermind.commons.events.ui;

import java.util.ArrayList;

import harmony.mastermind.commons.events.BaseEvent;

/**@@author A0139194X
 * An event requesting to view the help page.
 */
public class ShowHelpRequestEvent extends BaseEvent {

    private final ArrayList<String> commandList;
    private final ArrayList<String> formatList;
    private final ArrayList<String> descriptionList;

    
    public ShowHelpRequestEvent(ArrayList<String> commandList,
            ArrayList<String> formatList,
            ArrayList<String> descriptionList) {
        this.commandList = commandList;
        this.formatList = formatList;
        this.descriptionList = descriptionList;
    }
    
    public ArrayList<String> getCommandList() {
        return commandList;
    }
    
    public ArrayList<String> getFormatList() {
        return formatList;
    }

    public ArrayList<String> getDescriptionList() {
        return descriptionList;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
