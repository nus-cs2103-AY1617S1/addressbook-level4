package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.task.model.item.ReadOnlyEvent;
//@@author A0144702N-reused
/**
 * Provides a handle to an event card in the event list panel.
 * (Morphed from TaskCardHandle) 
 * @author 
 */
public class EventCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String DURATION_FIELD_ID = "#duration";
    

    private Node node;

    public EventCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullEventName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public String getEventDuration() {
        return getTextFromLabel(DURATION_FIELD_ID);
    }

    public boolean isSameEvent(ReadOnlyEvent event){
        return getFullEventName().equals(event.getNameWithStatus()) 
                && getEventDuration().equals(event.getDuration().toString())
                && getDescription().equals(event.getDescriptionValue());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EventCardHandle) {
            EventCardHandle handle = (EventCardHandle) obj;
            return getFullEventName().equals(handle.getFullEventName())
                    && getDescription().equals(handle.getDescription())
                    && getEventDuration().equals(handle.getEventDuration());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullEventName() + " " + getDescription();
    }
}
