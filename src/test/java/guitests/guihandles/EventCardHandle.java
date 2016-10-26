package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;

/**
 * Provides a handle to a Event card in the task list panel.
 */
public class EventCardHandle extends GuiHandle {

    private static final String NAME_FIELD_ID = "#name";
    private static final String START_FIELD_ID = "#start";
    private static final String END_FIELD_ID = "#end";

    private Node node;

    public EventCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }
    
    public String getStart() {
        return getTextFromLabel(START_FIELD_ID);
    }
    
    public String getEnd() {
        return getTextFromLabel(END_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyEvent event){
        return getFullName().equals(event.getName().fullName) 
               && getStart().substring(7).equals(event.getStart().toString())
               && getEnd().substring(5).equals(event.getEnd().toString());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EventCardHandle) {
            EventCardHandle handle = (EventCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                   && getStart().equals(handle.getStart())
                   && getEnd().equals(handle.getEnd());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName();
    }
}


