package seedu.ggist.commons.events.ui;

import seedu.ggist.commons.events.BaseEvent;
import java.util.Date;
//@@author A0144727B
/**
 * An event indicating the new listing on tasklistview to be reflected in UI
 */
public class ChangeListingEvent extends BaseEvent {

    public final String listing;
    
    public ChangeListingEvent(String listing) {
        this.listing = listing;
    }

    @Override
    public String toString() {
        if (listing == null || listing.equals("")) {
            return (new Date()).toString();
        } else {
            return listing;
        }
    }
}
