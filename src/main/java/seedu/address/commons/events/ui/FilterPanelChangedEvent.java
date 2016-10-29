package seedu.address.commons.events.ui;

import java.util.Map;
import java.util.Set;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Represents a change in the Filter Panel
 */
public class FilterPanelChangedEvent extends BaseEvent {

    private final Set<String> types;
    private final Map<String, String> qualifications;
    private final Set<String> tags;

    public FilterPanelChangedEvent(Set<String> types, Map<String, String> qualifications, Set<String> tags){
        this.types = types;
        this.qualifications = qualifications;
        this.tags = tags;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Set<String> getTypes() {
        return types;
    }
    
    public Map<String, String> getQualifications() {
        return qualifications;
    }
    
    public Set<String> getTags() {
        return tags;
    }
}
