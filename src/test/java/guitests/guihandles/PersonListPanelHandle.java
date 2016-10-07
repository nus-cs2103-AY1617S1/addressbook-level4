package guitests.guihandles;


import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.model.item.FloatingTask;
import seedu.address.model.item.ReadOnlyFloatingTask;
import seedu.address.testutil.TestUtil;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Provides a handle for the panel containing the person list.
 */
public class PersonListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String PERSON_LIST_VIEW_ID = "#personListView";

    public PersonListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyFloatingTask> getSelectedPersons() {
        ListView<ReadOnlyFloatingTask> personList = getListView();
        return personList.getSelectionModel().getSelectedItems();
    }

    public ListView<ReadOnlyFloatingTask> getListView() {
        return (ListView<ReadOnlyFloatingTask>) getNode(PERSON_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the person details correctly and in correct order.
     * @param persons A list of person in the correct order.
     */
    public boolean isListMatching(ReadOnlyFloatingTask... persons) {
        return this.isListMatching(0, persons);
    }
    
    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point= TestUtil.getScreenMidPoint(getListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code persons} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyFloatingTask... persons) {
        List<ReadOnlyFloatingTask> personsInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + persons.length > personsInList.size()){
            return false;
        }

        // Return false if any of the persons doesn't match
        for (int i = 0; i < persons.length; i++) {
            if (!personsInList.get(startPosition + i).getName().equals(persons[i].getName())){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if the list is showing the person details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param persons A list of person in the correct order.
     */
    public boolean isListMatching(int startPosition, ReadOnlyFloatingTask... persons) throws IllegalArgumentException {
        if (persons.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size() - 1) + " persons");
        }
        assertTrue(this.containsInOrder(startPosition, persons));
        for (int i = 0; i < persons.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndPerson(getFloatingTaskCardHandle(startPosition + i), persons[i])) {
                return false;
            }
        }
        return true;
    }


    public FloatingTaskCardHandle navigateToFloatingTask(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyFloatingTask> person = getListView().getItems().stream().filter(p -> p.getName().equals(name)).findAny();
        if (!person.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToFloatingTask(person.get().getName().name);
    }

    /**
     * Navigates the listview to display and select the person.
     */
    public FloatingTaskCardHandle navigateToFloatingTask(ReadOnlyFloatingTask person) {
        int index = getPersonIndex(person);

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getPersonCardHandle(person);
    }


    /**
     * Returns the position of the person given, {@code NOT_FOUND} if not found in the list.
     */
    public int getPersonIndex(ReadOnlyFloatingTask targetPerson) {
        List<ReadOnlyFloatingTask> personsInList = getListView().getItems();
        for (int i = 0; i < personsInList.size(); i++) {
            if(personsInList.get(i).getName().equals(targetPerson.getName())){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a person from the list by index
     */
    public ReadOnlyFloatingTask getPerson(int index) {
        return getListView().getItems().get(index);
    }

    public FloatingTaskCardHandle getFloatingTaskCardHandle(int index) {
        return getPersonCardHandle(new FloatingTask(getListView().getItems().get(index)));
    }

    public FloatingTaskCardHandle getPersonCardHandle(ReadOnlyFloatingTask person) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> personCardNode = nodes.stream()
                .filter(n -> new FloatingTaskCardHandle(guiRobot, primaryStage, n).isSameFloatingTask(person))
                .findFirst();
        if (personCardNode.isPresent()) {
            return new FloatingTaskCardHandle(guiRobot, primaryStage, personCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfPeople() {
        return getListView().getItems().size();
    }
}
