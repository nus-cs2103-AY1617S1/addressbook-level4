package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.tasklist.model.task.ReadOnlyTask;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#priority";
    private static final String PHONE_FIELD_ID = "#startTime";
    private static final String EMAIL_FIELD_ID = "#endTime";

    private Node node;

    public PersonCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getAddress() {
        return getTextFromLabel(ADDRESS_FIELD_ID);
    }

    public String getPhone() {
        return getTextFromLabel(PHONE_FIELD_ID);
    }

    public String getEmail() {
        return getTextFromLabel(EMAIL_FIELD_ID);
    }

    public boolean isSamePerson(ReadOnlyTask person){
        return getFullName().equals(person.getTaskDetails().toString()) && getPhone().equals("Starts:   "+person.getStartTime().toCardString())
                && getEmail().equals("Ends:     "+person.getEndTime().toCardString()) && getAddress().equals("Priority: "+person.getPriority().toString());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PersonCardHandle) {
            PersonCardHandle handle = (PersonCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getAddress().equals(handle.getAddress()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getFullName()+ "\n")
                .append("Start time: ")
                .append(getPhone() + "\t")
                .append("End time:")
                .append(getEmail() + "\n")
                .append("Priority: ")
                .append(getAddress()+ "\n")
                .append("Tags: ");
        return builder.toString();

        //return getFullName() + " " + getAddress();
    }
}
