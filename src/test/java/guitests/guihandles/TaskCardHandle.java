package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.model.item.ReadOnlyTask;

/**
 * Provides a handle to a floating task card in the item list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String PRIORITY_FIELD_ID = "#priority";


    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }


    public boolean isSameFloatingTask(ReadOnlyTask task){
        System.out.println("===================================================================");
        System.out.println("this.name = " + this.getName() + ", task.name = " + task.getName());
        System.out.println("this.name.compareTo(task.name) = " + this.getName().compareTo(task.getName().toString()));
        System.out.println("this.name = task.name? " + (this.getName().equals(task.getName())));
        System.out.println("this.name.hashCode() = " + this.getName().hashCode() + ", task.name.hashCode() = " + task.getName().hashCode());
        System.out.println("-------------------------------------------------------------------");
        System.out.println("this.priority = " + this.getPriority() + ", task.priority = " + task.getPriorityValue());
        System.out.println("this.priority = task.priority? " + (this.getPriority().equals(task.getPriorityValue())));
        System.out.println("this.priority.hashCode() = " + this.getPriority().hashCode() + ", task.priority.hashCode() = " + task.getPriorityValue().hashCode());
        System.out.println("===================================================================");

        return getName().equals(task.getName().name) && getPriority().equals(task.getPriorityValue());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getName().equals(handle.getName())
                    && getPriority().equals(handle.getPriority()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName() + " " + getPriority();
    }
}
