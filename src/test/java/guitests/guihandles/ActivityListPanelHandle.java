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
    public static final String TASK_CARD_PANE_ID = "#taskCardPane";
    public static final String FLOATING_CARD_PANE_ID = "#floatingTaskCardPane";
    public static final String EVENT_CARD_PANE_ID = "#eventCardPane";
    private static final String TASK_LIST_VIEW_ID = "#taskListView";
    private static final String FLOATING_LIST_VIEW_ID = "#floatingTaskListView";
    private static final String EVENT_LIST_VIEW_ID = "#eventListView";

    public ActivityListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyActivity> getSelectedPersons() {
        ListView<ReadOnlyActivity> personList = getTaskListView();
        return personList.getSelectionModel().getSelectedItems();
    }

    public ListView<ReadOnlyActivity> getTaskListView() {
        return (ListView<ReadOnlyActivity>) getNode(TASK_LIST_VIEW_ID);
    }
    
    /**
     * @author BrehmerChan (A046752B)
     */
    public ListView<ReadOnlyActivity> getFloatingTaskListView() {
        return (ListView<ReadOnlyActivity>) getNode(FLOATING_LIST_VIEW_ID);
    }

    /**
     * @author BrehmerChan (A046752B)
     */
    public ListView<ReadOnlyActivity> getEventListView() {
        return (ListView<ReadOnlyActivity>) getNode(TASK_LIST_VIEW_ID);
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
        Point2D point= TestUtil.getScreenMidPoint(getTaskListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code activities} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyActivity... activities) {
        List<ReadOnlyActivity> activitiesInList = getTaskListView().getItems();

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
        if (activities.length + startPosition != getTaskListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getTaskListView().getItems().size() - 1) + " activities");
        }
        assertTrue(this.containsInOrder(startPosition, activities));

        for (int i = 0; i < activities.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getTaskListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndTask(getTaskCardHandle(startPosition + i), activities[i])) {
                return false;
            }
        }
        return true;
    }


    public TaskCardHandle navigateToActivity(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyActivity> activity = getTaskListView().getItems().stream().filter(p -> p.getActivityName().fullName.equals(name)).findAny();
        if (!activity.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }
        return navigateToTask(activity.get());
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public TaskCardHandle navigateToTask(ReadOnlyActivity activity) {
        int index = getTaskIndex(activity);
        System.out.println("index = " + index);
        guiRobot.interact(() -> {
            getTaskListView().scrollTo(index);
            guiRobot.sleep(150);
            getTaskListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getTaskCardHandle(activity);
    }
    
    /**
     * @author BrehmerChan (A0146752B)
     * Navigates the listview to display and select the floating task.
     */
    public FloatingTaskCardHandle navigateToFloatingTask(ReadOnlyActivity activity) {
        int index = getFloatingTaskIndex(activity);
        System.out.println("index = " + index);
        guiRobot.interact(() -> {
            getFloatingTaskListView().scrollTo(index);
            guiRobot.sleep(150);
            getFloatingTaskListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getFloatingTaskCardHandle(activity);
    }
    
    /**
     * @author BrehmerChan (A0146752B)
     * Navigates the listview to display and select the event.
     */
    public TaskCardHandle navigateToEvent(ReadOnlyActivity activity) {
        int index = getEventIndex(activity);
        guiRobot.interact(() -> {
            getEventListView().scrollTo(index);
            guiRobot.sleep(150);
            getEventListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getTaskCardHandle(activity);
    }


    /**
     * Returns the position of the task given, {@code NOT_FOUND} if not found in the list.
     */
    public int getTaskIndex(ReadOnlyActivity targetTask) {
        List<ReadOnlyActivity> activitiesInList = getTaskListView().getItems();
        System.out.println("activities in list: " + activitiesInList.get(0).toString());
        for (int i = 0; i < activitiesInList.size(); i++) {
            if(activitiesInList.get(i).getActivityName().equals(targetTask.getActivityName())){
                return i;
            }
        }
        return NOT_FOUND;
    }
    
    /**
     * Returns the position of the floating task given, {@code NOT_FOUND} if not found in the list.
     */
    public int getFloatingTaskIndex(ReadOnlyActivity targetFloatingTask) {
        List<ReadOnlyActivity> activitiesInList = getFloatingTaskListView().getItems();
        for (int i = 0; i < activitiesInList.size(); i++) {
            if(activitiesInList.get(i).getActivityName().equals(targetFloatingTask.getActivityName())){
                return i;
            }
        }
        return NOT_FOUND;
    }
    
    /**
     * Returns the position of the event given, {@code NOT_FOUND} if not found in the list.
     */
    public int getEventIndex(ReadOnlyActivity targetEvent) {
        List<ReadOnlyActivity> activitiesInList = getEventListView().getItems();
        System.out.println("activities in list: " + activitiesInList.get(0).toString());
        for (int i = 0; i < activitiesInList.size(); i++) {
            if(activitiesInList.get(i).getActivityName().equals(targetEvent.getActivityName())){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**s
     * Gets a person from the list by index
     */
    public ReadOnlyActivity getPerson(int index) {
        return getTaskListView().getItems().get(index);
    }

    public TaskCardHandle getTaskCardHandle(int index) {
        return getTaskCardHandle(new Activity(getTaskListView().getItems().get(index)));
    }
    
    public FloatingTaskCardHandle getFloatingTaskCardHandle(int index) {
        return getFloatingTaskCardHandle(new Activity(getFloatingTaskListView().getItems().get(index)));
    }

    public TaskCardHandle getTaskCardHandle(ReadOnlyActivity person) {
        Set<Node> nodes = getAllTaskCardNodes();
        Optional<Node> activityCardNode = nodes.stream()
                .filter(n -> new TaskCardHandle(guiRobot, primaryStage, n).isSameActivity(person))
                .findFirst();
        if (activityCardNode.isPresent()) {
            return new TaskCardHandle(guiRobot, primaryStage, activityCardNode.get());
        } else {
            return null;
        }
    }
    
    public FloatingTaskCardHandle getFloatingTaskCardHandle(ReadOnlyActivity person) {
        Set<Node> nodes = getAllFloatingTaskCardNodes();
        Optional<Node> activityCardNode = nodes.stream()
                .filter(n -> new FloatingTaskCardHandle(guiRobot, primaryStage, n).isSameActivity(person))
                .findFirst();
        if (activityCardNode.isPresent()) {
            System.out.println("returns properly");
            return new FloatingTaskCardHandle(guiRobot, primaryStage, activityCardNode.get());
        } else {
            System.out.println("returns null");
            return null;
        }
    }

    protected Set<Node> getAllTaskCardNodes() {
        return guiRobot.lookup(TASK_CARD_PANE_ID).queryAll();
    }
    
    protected Set<Node> getAllFloatingTaskCardNodes() {
        return guiRobot.lookup(FLOATING_CARD_PANE_ID).queryAll();
    }
    
    protected Set<Node> getAllEventCardNodes() {
        return guiRobot.lookup(EVENT_CARD_PANE_ID).queryAll();
    }

    public int getNumberOfPeople() {
        return getTaskListView().getItems().size();
    }
}