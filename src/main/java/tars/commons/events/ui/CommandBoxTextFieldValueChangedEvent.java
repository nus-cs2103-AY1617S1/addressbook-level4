package tars.commons.events.ui;

import tars.commons.events.BaseEvent;

/**
 * Indicates a change of value in the command box
 */
public class CommandBoxTextFieldValueChangedEvent extends BaseEvent {

    private String value;

    public CommandBoxTextFieldValueChangedEvent(String value) {
        this.value = value;
    }

    public String getTextFieldValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
