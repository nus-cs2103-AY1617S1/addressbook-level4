package seedu.dailyplanner.commons.events.model;

import seedu.dailyplanner.commons.events.BaseEvent;
import seedu.dailyplanner.model.ReadOnlyDailyPlanner;

/** Indicates the DailyPlanner in the model has changed*/
public class DailyPlannerChangedEvent extends BaseEvent {

    public final ReadOnlyDailyPlanner data;

    public DailyPlannerChangedEvent(ReadOnlyDailyPlanner data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of categories " + data.getCategoryList().size();
    }
}
