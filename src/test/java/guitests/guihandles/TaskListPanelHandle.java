package guitests.guihandles;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.model.task.Task;
import seedu.address.testutil.TestUtil;

/**
 * Provides a handle for the panel containing the person list.
 */
public class TaskListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String PERSON_LIST_VIEW_ID = "#personListView";

    public TaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<Task> getSelectedPersons() {
        ListView<Task> personList = getListView();
        return personList.getSelectionModel().getSelectedItems();
    }

    public ListView<Task> getListView() {
        return (ListView<Task>) getNode(PERSON_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the person details correctly and in correct order.
     * @param persons A list of person in the correct order.
     */
    public boolean isListMatching(Task... persons) {
        return this.isListMatching(0, persons);
    }

    /**
     * Returns true if the list is showing the person details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param persons A list of person in the correct order.
     */
    public boolean isListMatching(int startPosition, Task... persons) throws IllegalArgumentException {
        if (persons.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n"
                    + "Expected " + (getListView().getItems().size() - 1) + " persons");
        }
        assertTrue(this.containsInOrder(startPosition, persons));
        for (int i = 0; i < persons.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndTask(getPersonCardHandle(startPosition + i), persons[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point = TestUtil.getScreenMidPoint(getListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code persons} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, Task... persons) {
        List<Task> personsInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + persons.length > personsInList.size()) {
            return false;
        }

        // Return false if any of the persons doesn't match
        for (int i = 0; i < persons.length; i++) {
            if (!personsInList.get(startPosition + i).name.fullName.equals(persons[i].name.fullName)) {
                return false;
            }
        }

        return true;
    }

    public TaskListCardHandle navigateToPerson(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<Task> person = getListView().getItems().stream().filter(p -> p.name.fullName.equals(name)).findAny();
        if (!person.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToPerson(person.get());
    }

    /**
     * Navigates the listview to display and select the person.
     */
    public TaskListCardHandle navigateToPerson(Task person) {
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
    public int getPersonIndex(Task targetPerson) {
        List<Task> personsInList = getListView().getItems();
        for (int i = 0; i < personsInList.size(); i++) {
            if (personsInList.get(i).name.equals(targetPerson.name)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a person from the list by index
     */
    public Task getPerson(int index) {
        return getListView().getItems().get(index);
    }

    public TaskListCardHandle getPersonCardHandle(int index) {
        return getPersonCardHandle(new Task(getListView().getItems().get(index)));
    }

    public TaskListCardHandle getPersonCardHandle(Task person) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> personCardNode = nodes.stream()
                .filter(n -> new TaskListCardHandle(guiRobot, primaryStage, n).isSamePerson(person))
                .findFirst();
        if (personCardNode.isPresent()) {
            return new TaskListCardHandle(guiRobot, primaryStage, personCardNode.get());
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
