package guitests.guihandles;


import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.testutil.TestUtil;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.Activity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Provides a handle for the panel containing the person list.
 */
public class ActivityListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";
    private static final String PERSON_LIST_VIEW_ID = "#taskListView";

    public ActivityListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyActivity> getSelectedPersons() {
        ListView<ReadOnlyActivity> personList = getListView();
        return personList.getSelectionModel().getSelectedItems();
    }

    public ListView<ReadOnlyActivity> getListView() {
        return (ListView<ReadOnlyActivity>) getNode(PERSON_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the person details correctly and in correct order.
     * @param activities A list of person in the correct order.
     */
    public boolean isListMatching(ReadOnlyActivity... activities) {
        return this.isListMatching(0, activities);
    }
    
    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point= TestUtil.getScreenMidPoint(getListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code activities} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyActivity... activities) {
        List<ReadOnlyActivity> activitiesInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + activities.length > activitiesInList.size()){
            return false;
        }

        // Return false if any of the activities doesn't match
        for (int i = 0; i < activities.length; i++) {
            if (!activitiesInList.get(startPosition + i).getActivityName().fullName.equals(activities[i].getActivityName().fullName)){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if the list is showing the person details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param activities A list of person in the correct order.
     */
    public boolean isListMatching(int startPosition, ReadOnlyActivity... activities) throws IllegalArgumentException {
        if (activities.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size() - 1) + " activities");
        }
        assertTrue(this.containsInOrder(startPosition, activities));

        for (int i = 0; i < activities.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndPerson(getPersonCardHandle(startPosition + i), activities[i])) {
                return false;
            }
        }
        return true;
    }


    public ActivityCardHandle navigateToPerson(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyActivity> activity = getListView().getItems().stream().filter(p -> p.getActivityName().fullName.equals(name)).findAny();
        if (!activity.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }
        return navigateToPerson(activity.get());
    }

    /**
     * Navigates the listview to display and select the person.
     */
    public ActivityCardHandle navigateToPerson(ReadOnlyActivity activity) {
        int index = getPersonIndex(activity);
        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getPersonCardHandle(activity);
    }


    /**
     * Returns the position of the person given, {@code NOT_FOUND} if not found in the list.
     */
    public int getPersonIndex(ReadOnlyActivity targetPerson) {
        List<ReadOnlyActivity> activitiesInList = getListView().getItems();
        for (int i = 0; i < activitiesInList.size(); i++) {
            if(activitiesInList.get(i).getActivityName().equals(targetPerson.getActivityName())){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a person from the list by index
     */
    public ReadOnlyActivity getPerson(int index) {
        return getListView().getItems().get(index);
    }

    public ActivityCardHandle getPersonCardHandle(int index) {
        return getPersonCardHandle(new Activity(getListView().getItems().get(index)));
    }

    public ActivityCardHandle getPersonCardHandle(ReadOnlyActivity person) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> activityCardNode = nodes.stream()
                .filter(n -> new ActivityCardHandle(guiRobot, primaryStage, n).isSameActivity(person))
                .findFirst();
        if (activityCardNode.isPresent()) {
            return new ActivityCardHandle(guiRobot, primaryStage, activityCardNode.get());
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