package teamfour.tasc.commons.events.ui;

import teamfour.tasc.commons.events.BaseEvent;

public class CollapseChangeEvent extends BaseEvent {

    public static boolean isCollapsed = false;

    public CollapseChangeEvent(boolean toCollapse){
        isCollapsed = toCollapse;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
