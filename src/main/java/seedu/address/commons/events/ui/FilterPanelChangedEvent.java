package seedu.address.commons.events.ui;

import java.util.Map;
import java.util.Set;

import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.util.Types;

//@@author A0146123R
/**
 * Represents a change in the Filter Panel.
 */
public class FilterPanelChangedEvent extends BaseEvent {

    private final Set<Types> types;
    private final Map<Types, String> qualifications;
    private final Set<String> tags;

    public FilterPanelChangedEvent(Set<Types> types, Map<Types, String> qualifications, Set<String> tags) {
        this.types = types;
        this.qualifications = qualifications;
        this.tags = tags;
    }

    public Set<Types> getTypes() {
        return types;
    }

    public Map<Types, String> getQualifications() {
        return qualifications;
    }

    public Set<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
