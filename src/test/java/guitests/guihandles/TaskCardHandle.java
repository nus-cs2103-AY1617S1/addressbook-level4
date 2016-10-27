package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.taskmanager.model.item.ReadOnlyItem;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String ITEMTYPE_FIELD_ID = "#itemType";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ENDDATE_FIELD_ID = "#endDate";
    private static final String ENDTIME_FIELD_ID = "#endTime";
    private static final String STARTDATE_FIELD_ID = "#startDate";
    private static final String STARTTIME_FIELD_ID = "#startTime";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getItemType() {
        return getTextFromLabel(ITEMTYPE_FIELD_ID);
    }

    public String getEndTime() {
        return getTextFromLabel(ENDTIME_FIELD_ID);
    }

    public String getName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getEndDate() {
        return getTextFromLabel(ENDDATE_FIELD_ID);
    }
    
    public String getStartDate() {
        return getTextFromLabel(STARTDATE_FIELD_ID);
    }
    
    public String getStartTime() {
        return getTextFromLabel(STARTTIME_FIELD_ID);
    }

    public boolean isSameItem(ReadOnlyItem person){
        return getName().equals(person.getName().value);
//        return getItemType().equals(person.getItemType().value) && getName().equals(person.getName().value)
//                && getEndDate().equals(person.getEndDate().value) && getEndTime().equals(person.getEndTime().value)
//                && getStartDate().equals(person.getStartDate().value) && getStartTime().equals(person.getStartTime().value);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getItemType().equals(handle.getItemType())
                    && getEndTime().equals(handle.getEndTime()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getItemType() + " " + getEndTime();
    }
}
