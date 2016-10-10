package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.model.item.ReadOnlyPerson;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends GuiHandle {
    private static final String ITEMTYPE_FIELD_ID = "#name";
    private static final String TIME_FIELD_ID = "#address";
    private static final String NAME_FIELD_ID = "#phone";
    private static final String DATE_FIELD_ID = "#email";

    private Node node;

    public PersonCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getItemType() {
        return getTextFromLabel(ITEMTYPE_FIELD_ID);
    }

    public String getTime() {
        return getTextFromLabel(TIME_FIELD_ID);
    }

    public String getName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getDate() {
        return getTextFromLabel(DATE_FIELD_ID);
    }

    public boolean isSamePerson(ReadOnlyPerson person){
    	System.out.println(getItemType());
    	System.out.println(getName());
    	System.out.println(getDate());
    	System.out.println(getTime());
    	System.out.println(person.getItemType().value);
    	System.out.println(person.getName().value);
    	System.out.println(person.getEndDate().value);
    	System.out.println(person.getEndTime().value);
        return getItemType().equals(person.getItemType().value) && getName().equals(person.getName().value)
                && getDate().equals(person.getEndDate().value) && getTime().equals(person.getEndTime().value);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PersonCardHandle) {
            PersonCardHandle handle = (PersonCardHandle) obj;
            return getItemType().equals(handle.getItemType())
                    && getTime().equals(handle.getTime()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getItemType() + " " + getTime();
    }
}
