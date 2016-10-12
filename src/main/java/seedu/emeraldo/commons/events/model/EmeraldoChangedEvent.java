package seedu.emeraldo.commons.events.model;

import seedu.emeraldo.commons.events.BaseEvent;
import seedu.emeraldo.model.ReadOnlyEmeraldo;

/** Indicates the Emeraldo in the model has changed*/
public class EmeraldoChangedEvent extends BaseEvent {

    public final ReadOnlyEmeraldo data;

    public EmeraldoChangedEvent(ReadOnlyEmeraldo data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
