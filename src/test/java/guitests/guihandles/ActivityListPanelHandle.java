package guitests.guihandles;


import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.testutil.TestActivity;
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
        return (ListView<ReadOnlyActivity>) getNode(EVENT_LIST_VIEW_ID);
    }


    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param tasks A list of tasks in the correct order.
     */
    public boolean isTaskListMatching(ReadOnlyActivity... tasks) {
        return this.isTaskListMatching(0, tasks);
    }
    
    /**
     * Returns true if the list is showing the floating task details correctly and in correct order.
     * @param floatingTask A list of tasks in the correct order.
     */
    public boolean isFloatingTaskListMatching(ReadOnlyActivity... floatingTask) {
        return this.isFloatingTaskListMatching(0, floatingTask);
    }
    
    /**
     * Returns true if the list is showing the event details correctly and in correct order.
     * @param events A list of tasks in the correct order.
     */
    
    
    public boolean isEventListMatching(ReadOnlyActivity... events) {
        return this.isEventListMatching(0, events);
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
    public boolean containsTasksInOrder(int startPosition, ReadOnlyActivity... activities) {
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
     * Returns true if the {@code activities} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsFloatingTasksInOrder(int startPosition, ReadOnlyActivity... activities) {
        List<ReadOnlyActivity> activitiesInList = getFloatingTaskListView().getItems();

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
     * Returns true if the {@code activities} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsEventsInOrder(int startPosition, ReadOnlyActivity... activities) {
        List<ReadOnlyActivity> activitiesInList = getEventListView().getItems();

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
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param tasks A list of task in the correct order.
     */
    public boolean isTaskListMatching(int startPosition, ReadOnlyActivity... tasks) throws IllegalArgumentException {
        if (tasks.length + startPosition != getTaskListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getTaskListView().getItems().size() - 1) + " activities");
        }
        assertTrue(this.containsTasksInOrder(startPosition, tasks));

        for (int i = 0; i < tasks.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getTaskListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndTask(getTaskCardHandle(startPosition + i), tasks[i])) {
                return false;
            }
        }
        return true;
    }
    
    //@@author: A0139164A
    public TestActivity returnsUpdatedEvent(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyActivity> activity = getEventListView().getItems().stream().filter(p -> p.getActivityName().fullName.equals(name)).findAny();
        if (!activity.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }
        TestActivity dub = new TestActivity(activity.get());
        return dub;
    }
    public TestActivity returnsUpdatedTask(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyActivity> activity = getTaskListView().getItems().stream().filter(p -> p.getActivityName().fullName.equals(name)).findAny();
        if (!activity.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }
        TestActivity dub = new TestActivity(activity.get());
        return dub;
    }
    
    public TestActivity returnsUpdatedFloatingTask(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyActivity> activity = getFloatingTaskListView().getItems().stream().filter(p -> p.getActivityName().fullName.equals(name)).findAny();
        if (!activity.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }
        TestActivity dub = new TestActivity(activity.get());
        return dub;
    }

    
    /**
     * Returns true if the list is showing the floating task details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param floating tasks A list of floating task in the correct order.
     */
    public boolean isFloatingTaskListMatching(int startPosition, ReadOnlyActivity... floatingTasks) throws IllegalArgumentException {
        if (floatingTasks.length + startPosition != getFloatingTaskListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getFloatingTaskListView().getItems().size() - 1) + " activities");
        }
        assertTrue(this.containsFloatingTasksInOrder(startPosition, floatingTasks));

        for (int i = 0; i < floatingTasks.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getFloatingTaskListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndFloatingTask(getFloatingTaskCardHandle(startPosition + i), floatingTasks[i])) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Returns true if the list is showing the event details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param events A list of events in the correct order.
     */
    public boolean isEventListMatching(int startPosition, ReadOnlyActivity... events) throws IllegalArgumentException {
        if (events.length + startPosition != getEventListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getEventListView().getItems().size() - 1) + " activities");
        }
        assertTrue(this.containsEventsInOrder(startPosition, events));

        for (int i = 0; i < events.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getEventListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndEvent(getEventCardHandle(startPosition + i), events[i])) {
                return false;
            }
        }
        return true;
    }


    public TaskCardHandle navigateToTask(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyActivity> activity = getTaskListView().getItems().stream().filter(p -> p.getActivityName().fullName.equals(name)).findAny();
        if (!activity.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }
        return navigateToTask(activity.get());
    }
    
    public FloatingTaskCardHandle navigateToFloatingTask(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyActivity> activity = getFloatingTaskListView().getItems().stream().filter(p -> p.getActivityName().fullName.equals(name)).findAny();
        if (!activity.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }
        return navigateToFloatingTask(activity.get());
    }
    
    public EventCardHandle navigateToEvent(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyActivity> activity = getEventListView().getItems().stream().filter(p -> p.getActivityName().fullName.equals(name)).findAny();
        if (!activity.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }
        return navigateToEvent(activity.get());
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public TaskCardHandle navigateToTask(ReadOnlyActivity task) {
        int index = getTaskIndex(task);
        guiRobot.interact(() -> {
            getTaskListView().scrollTo(index);
            guiRobot.sleep(150);
            getTaskListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getTaskCardHandle(task);
    }

    /**
     * @author BrehmerChan (A0146752B)
     * Navigates the listview to display and select the floating task.
     */
    public FloatingTaskCardHandle navigateToFloatingTask(ReadOnlyActivity floatingTask) {
        int index = getFloatingTaskIndex(floatingTask);
        guiRobot.interact(() -> {
            getFloatingTaskListView().scrollTo(index);
            guiRobot.sleep(150);
            getFloatingTaskListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getFloatingTaskCardHandle(floatingTask);
    }
    
    /**
     * @author BrehmerChan (A0146752B)
     * Navigates the listview to display and select the event.
     */
    public EventCardHandle navigateToEvent(ReadOnlyActivity event) {
        int index = getEventIndex(event);
        guiRobot.interact(() -> {
            getEventListView().scrollTo(index);
            guiRobot.sleep(150);
            getEventListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getEventCardHandle(event);
    }


    /**
     * Returns the position of the task given, {@code NOT_FOUND} if not found in the list.
     */
    public int getTaskIndex(ReadOnlyActivity targetTask) {
        List<ReadOnlyActivity> activitiesInList = getTaskListView().getItems();
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
        for (int i = 0; i < activitiesInList.size(); i++) {
            if(activitiesInList.get(i).getActivityName().equals(targetEvent.getActivityName())){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
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
    

    public EventCardHandle getEventCardHandle(int index) {
        return getEventCardHandle(new Activity(getEventListView().getItems().get(index)));
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
            return new FloatingTaskCardHandle(guiRobot, primaryStage, activityCardNode.get());
        } else {
            return null;
        }
    }
    
    public EventCardHandle getEventCardHandle(ReadOnlyActivity event) {
        Set<Node> nodes = getAllEventCardNodes();
        Optional<Node> activityCardNode = nodes.stream()
                .filter(n -> new EventCardHandle(guiRobot, primaryStage, n).isSameActivity(event))
                .findFirst();
        if (activityCardNode.isPresent()) {
            return new EventCardHandle(guiRobot, primaryStage, activityCardNode.get());
        } else {
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