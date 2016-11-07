package w15c2.tusk.commons.events.ui;

import w15c2.tusk.commons.events.BaseEvent;
//@@author A0139708W
/**
 * An event requesting to view the alias list.
 */
public class ShowAliasListEvent extends BaseEvent {

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
