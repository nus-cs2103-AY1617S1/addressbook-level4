package seedu.todo.commons.events.ui;

import seedu.todo.commons.events.BaseEvent;
import seedu.todo.model.tag.Tag;

import java.util.List;

//@@author A0135805H
/**
 * An event requesting to view the global tags page.
 */
public class ShowTagsEvent extends BaseEvent {

    private List<Tag> listOfTags;

    public ShowTagsEvent(List<Tag> listOfTags) {
        this.listOfTags = listOfTags;
    }

    /**
     * Retrieves the global list of tags sent by the event bus.
     */
    public List<Tag> getListOfTags() {
        return listOfTags;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
