package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAliasManager;

/** Indicates that the AliasManager in the model has changed*/
public class AliasManagerChangedEvent extends BaseEvent {

    public final ReadOnlyAliasManager data;

    public AliasManagerChangedEvent(ReadOnlyAliasManager data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getAliasList().size();
    }
}
