//@@author A0127014W
package teamfour.tasc.commons.events.ui;

import teamfour.tasc.commons.events.BaseEvent;

/**
 *Indicates the collapse status of the view has changed (from collapsed to expanded, or vice versa)
 */

public class CollapseChangeEvent extends BaseEvent {

    // Static variable used by all instances of this event
    private static boolean isCollapsed = false;

    public CollapseChangeEvent(boolean toCollapse){
        setCollapsed(toCollapse);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }


    public static boolean getCollapsed() {
        return isCollapsed;
    }

    public static void setCollapsed(boolean isCollapsed) {
        CollapseChangeEvent.isCollapsed = isCollapsed;
    }

}
