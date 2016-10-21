package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import seedu.taskscheduler.commons.util.DateFormatter;
import seedu.taskscheduler.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the person list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String CARDPANE_FIELD_ID = "#cardPane";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String START_DATE_FIELD_ID = "#phone";
    private static final String END_DATE_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getStyleFromHBox(String fieldId) {
        return getStyleFromHBox(fieldId, node);
    }
    
    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }


    public String getHBoxStyle() {
        return getStyleFromHBox(CARDPANE_FIELD_ID);
    }
    
    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getLocation() {
        return getTextFromLabel(ADDRESS_FIELD_ID);
    }

    public String getStartDate() {
        return getTextFromLabel(START_DATE_FIELD_ID).replace("Start Date: ", "");
    }

    public String getEndDate() {
        return getTextFromLabel(END_DATE_FIELD_ID).replace("Due Date: ", "");
    }
    
    public String getTags() {
        return getTextFromLabel(TAGS_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getFullName().equals(task.getName().fullName) && getStartDate().equals(task.getStartDate().getDisplayString())
                && getEndDate().equals(task.getEndDate().getDisplayString()) && getLocation().equals(task.getLocation().value);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getLocation().equals(handle.getLocation()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getLocation();
    }
}
