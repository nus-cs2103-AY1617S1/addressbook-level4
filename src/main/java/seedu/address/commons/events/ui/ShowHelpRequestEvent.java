package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class ShowHelpRequestEvent extends BaseEvent {

    private final String commandWord;

    public ShowHelpRequestEvent(String commandWord) {
        this.commandWord = commandWord;
    }

    public String getCommandWord() {
        return commandWord;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " with command word: " + commandWord;
    }
}
