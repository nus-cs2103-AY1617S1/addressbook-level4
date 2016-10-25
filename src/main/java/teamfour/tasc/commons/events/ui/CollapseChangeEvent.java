package teamfour.tasc.commons.events.ui;

import teamfour.tasc.commons.events.BaseEvent;

public class CollapseChangeEvent extends BaseEvent {

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
