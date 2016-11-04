package guitests.guihandles;


import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.simply.TestApp;
import seedu.simply.model.task.ReadOnlyTask;
import seedu.simply.model.task.Task;
import seedu.simply.testutil.TestUtil;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * @@author A0138993L
 * Provides a handle for the panel containing the deadline list.
 */
public class DeadlineListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String DEADLINE_LIST_VIEW_ID = "#deadlineListView";

    public DeadlineListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyTask> getSelectedDeadlines() {
        ListView<ReadOnlyTask> deadlineList = getListView();
        return deadlineList.getSelectionModel().getSelectedItems();
    }

    public ListView<ReadOnlyTask> getListView() {
        return (ListView<ReadOnlyTask>) getNode(DEADLINE_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the person details correctly and in correct order.
     * @param deadline A list of person in the correct order.
     */
    public boolean isListMatching(ReadOnlyTask... deadline) {
        return this.isListMatching(0, deadline);
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
    public boolean containsInOrder(int startPosition, ReadOnlyTask... persons) {
        List<ReadOnlyTask> deadlinesInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + persons.length > deadlinesInList.size()){
            return false;
        }

        // Return false if any of the persons doesn't match
        for (int i = 0; i < persons.length; i++) {
            if (!deadlinesInList.get(startPosition + i).getName().taskDetails.equals(persons[i].getName().taskDetails)){
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
    public boolean isListMatching(int startPosition, ReadOnlyTask... persons) throws IllegalArgumentException {
        if (persons.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size() - 1) + " persons");
        }
        assertTrue(this.containsInOrder(startPosition, persons));
        for (int i = 0; i < persons.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndDeadline(getDeadlineCardHandle(startPosition + i), persons[i])) {
                return false;
            }
        }
        return true;
    }


    public DeadlineCardHandle navigateToPerson(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyTask> person = getListView().getItems().stream().filter(p -> p.getName().toString().equals(name)).findAny();
        if (!person.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToPerson(person.get());
    }

    /**
     * Navigates the listview to display and select the person.
     */
    public DeadlineCardHandle navigateToPerson(ReadOnlyTask person) {
        int index = getPersonIndex(person);

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getDeadlineCardHandle(person);
    }


    /**
     * Returns the position of the person given, {@code NOT_FOUND} if not found in the list.
     */
    public int getPersonIndex(ReadOnlyTask targetPerson) {
        List<ReadOnlyTask> personsInList = getListView().getItems();
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
    public ReadOnlyTask getPerson(int index) {
        return getListView().getItems().get(index);
    }

    public DeadlineCardHandle getDeadlineCardHandle(int index) {
        return getDeadlineCardHandle(new Task(getListView().getItems().get(index)));
    }

    public DeadlineCardHandle getDeadlineCardHandle(ReadOnlyTask person) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> personCardNode = nodes.stream()
                .filter(n -> new DeadlineCardHandle(guiRobot, primaryStage, n).isSamePerson(person))
                .findFirst();
        if (personCardNode.isPresent()) {
            return new DeadlineCardHandle(guiRobot, primaryStage, personCardNode.get());
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
