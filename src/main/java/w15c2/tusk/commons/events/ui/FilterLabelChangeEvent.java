package w15c2.tusk.commons.events.ui;

import w15c2.tusk.commons.events.BaseEvent;

//@@author A0139708W
/**
 * An event requesting to change the filter labels
 */
public class FilterLabelChangeEvent extends BaseEvent {
    
    private COMMANDTYPE commandType;
    /**
     * Enum of various command types
     * that raise event.
     */
    public enum COMMANDTYPE {
        List, ListComplete, Find
    }
    
    /**
     * Constructor for event, assigns event type to private 
     * commandType enum.
     * 
     * @param commandInput  COMMANDTYPE enum of command raising event.
     */
    public FilterLabelChangeEvent(COMMANDTYPE commandInput) {
        this.commandType = commandInput;
    }
    
    /**
     * Returns COMMANDTYPE enum that raised event instance.
     * 
     * @return  COMMANDTYPE enum that raised instance of event.
     */
    public COMMANDTYPE getCommandType() {
        return commandType;
    }

    /**
     * Returns String of event name for 
     * logging purposes.
     * 
     * @return  String of event name
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
