package guitests.guihandles;


import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.lifekeeper.TestApp;
import seedu.lifekeeper.model.activity.Activity;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;
import seedu.lifekeeper.testutil.TestUtil;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Provides a handle for the panel containing the activity list.
 */
public class ActivityListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String ACTIVITY_LIST_VIEW_ID = "#activityListView";

    public ActivityListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyActivity> getSelectedActivities() {
        ListView<ReadOnlyActivity> activityList = getListView();
        return activityList.getSelectionModel().getSelectedItems();
    }
    //my debugging gets stuck here. possibly timeout happening here.
    public ListView<ReadOnlyActivity> getListView() {
        return (ListView<ReadOnlyActivity>) getNode(ACTIVITY_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the activity details correctly and in correct order.
     * @param activities A list of activity in the correct order.
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
            if (!activitiesInList.get(startPosition + i).getName().fullName.equals(activities[i].getName().fullName)){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if the list is showing the activity details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param activities A list of activity in the correct order.
     */
    public boolean isListMatching(int startPosition, ReadOnlyActivity... activities) throws IllegalArgumentException {
        if(startPosition!= 0 && activities.length!=0){
        if (activities.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size()) + " activities");
        }
        assertTrue(this.containsInOrder(startPosition, activities));
        for (int i = 0; i < activities.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndActivity(getActivityCardHandle(startPosition + i), activities[i])) {
                return false;
            }
        }}
        return true;
    }


    public ActivityCardHandle navigateToActivity(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyActivity> activity = getListView().getItems().stream().filter(p -> p.getName().fullName.equals(name)).findAny();
        
        if (!activity.isPresent()) {
            throw new IllegalStateException("Activity Name not found: " + name);
        }

        return navigateToActivity(activity.get());
    }
    
    /**
     * Navigates the listview to display and select the activity.
     */
    public ActivityCardHandle navigateToActivity(ReadOnlyActivity activity) {
        int index = getActivityIndex(activity);

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(500);
            getListView().getSelectionModel().select(index); 
        });
        guiRobot.sleep(100);
        return getActivityCardHandle(activity);
    }


    /**
     * Returns the position of the activity given, {@code NOT_FOUND} if not found in the list.
     */
    public int getActivityIndex(ReadOnlyActivity targetActivity) {
        List<ReadOnlyActivity> activitiesInList = getListView().getItems();
        for (int i = 0; i < activitiesInList.size(); i++) {
            if(activitiesInList.get(i).getName().equals(targetActivity.getName())){
                return i;
            }
        }

        return NOT_FOUND;
    }

    /**
     * Gets a activity from the list by index
     */
    public ReadOnlyActivity getActivity(int index) {
        return getListView().getItems().get(index);
    }

    public ActivityCardHandle getActivityCardHandle(int index) {
        return getActivityCardHandle(new Activity(getListView().getItems().get(index)));
    }

    public ActivityCardHandle getActivityCardHandle(ReadOnlyActivity activity) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> activityCardNode = nodes.stream()
                .filter(n -> new ActivityCardHandle(guiRobot, primaryStage, n).isSameActivity(activity))
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
