package teamfour.tasc.commons.events.ui;

import teamfour.tasc.commons.events.BaseEvent;

public class CollapseChangeEvent extends BaseEvent {

    public boolean isCollapsed = false;

    public CollapseChangeEvent(boolean toCollapse){
        this.isCollapsed = toCollapse;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
