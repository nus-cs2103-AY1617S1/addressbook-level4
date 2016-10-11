package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.emeraldo.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class PersonCardHandle extends GuiHandle {
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String DATETIME_FIELD_ID = "#dateTime";
    private static final String PHONE_FIELD_ID = "#phone";

    private Node node;

    public PersonCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public String getDateTime() {
        return getTextFromLabel(DATETIME_FIELD_ID);
    }

    public String getPhone() {
        return getTextFromLabel(PHONE_FIELD_ID);
    }


    public boolean isSameTask(ReadOnlyTask task){
        return getDescription().equals(task.getDescription().fullName) 
                 && getPhone().equals(task.getPhone().value)
                 && getDateTime().equals(task.getDateTime().value);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PersonCardHandle) {
            PersonCardHandle handle = (PersonCardHandle) obj;
            return getDescription().equals(handle.getDescription())
                    && getDateTime().equals(handle.getDateTime()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getDescription() + " " + getDateTime();
    }
}
