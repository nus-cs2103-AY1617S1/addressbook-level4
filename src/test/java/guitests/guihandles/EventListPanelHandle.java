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
import seedu.task.TestApp;
import seedu.task.model.item.Event;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.testutil.TestUtil;

/**
 * Provides a handle for the panel containing the event list
 * @author xuchen
 *
 */
public class EventListPanelHandle extends GuiHandle {

	private static final String EVENT_LIST_VIEW_ID = "#eventListView";
	private static final String CARD_PANE_ID = "#cardPane";
	public static final int NOT_FOUND = -1;

	public EventListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
		super(guiRobot, primaryStage, TestApp.APP_TITLE);
	}
	
	public List<ReadOnlyEvent> getSelectedEvents() {
		ListView<ReadOnlyEvent> eventList = getListView();
		return eventList.getSelectionModel().getSelectedItems();
		
	}

	private ListView<ReadOnlyEvent> getListView() {
		return (ListView<ReadOnlyEvent>) getNode(EVENT_LIST_VIEW_ID);
	}
	
	/**
     * Returns true if the list is showing the event details correctly and in correct order.
     * @param events A list of event in the correct order.
     */
    public boolean isListMatching(ReadOnlyEvent... events) {
        return this.isListMatching(0, events);
    }
    
    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point= TestUtil.getScreenMidPoint(getListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    
    /**
     * Returns true if the list is showing the event details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param events A list of event in the correct order.
     */
	private boolean isListMatching(int startPosition, ReadOnlyEvent[] events) {
		if (events.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size() - 1) + " events");
        }
        assertTrue(this.containsInOrder(startPosition, events));
        for (int i = 0; i < events.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndEvent(getEventCardHandle(startPosition + i), events[i])) {
                return false;
            }
            
        }
        return true;
	}

	private boolean containsInOrder(int startPosition, ReadOnlyEvent[] events) {
		List<ReadOnlyEvent> eventInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + events.length > eventInList.size()){
            return false;
        }

        // Return false if any of the events doesn't match
        for (int i = 0; i < events.length; i++) {
            if (!eventInList.get(startPosition + i).equals(events[i])){
                return false;
            }
        }

        return true;
	}
	
    public EventCardHandle navigateToEvent(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyEvent> event = getListView().getItems().stream().filter(p -> p.getEvent().fullName.equals(name)).findAny();
        if (!event.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToEvent(event.get());
    }
    
    /**
     * Navigates the list view to display and select the event.
     */
    public EventCardHandle navigateToEvent(ReadOnlyEvent event) {
        int index = getEventIndex(event);

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getEventCardHandle(event);
    }
    

    /**
     * Returns the position of the event given, {@code NOT_FOUND} if not found in the list.
     */
    public int getEventIndex(ReadOnlyEvent targetEvent) {
        List<ReadOnlyEvent> eventsInList = getListView().getItems();
        for (int i = 0; i < eventsInList.size(); i++) {
            if(eventsInList.get(i).getEvent().equals(targetEvent.getEvent())){
                return i;
            }
        }
        return NOT_FOUND;
    }

	
	 /**
     * Gets an event from the list by index
     */
    public ReadOnlyEvent getEvent(int index) {
        return getListView().getItems().get(index);
    }

    public EventCardHandle getEventCardHandle(int index) {
        return getEventCardHandle(new Event(getListView().getItems().get(index)));
    }

    public EventCardHandle getEventCardHandle(ReadOnlyEvent event) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> eventCardNode = nodes.stream()
                .filter(n -> new EventCardHandle(guiRobot, primaryStage, n).isSameEvent(event))
                .findFirst();
        if (eventCardNode.isPresent()) {
            return new EventCardHandle(guiRobot, primaryStage, eventCardNode.get());
        } else {
            return null;
        }
    }
    
    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfEvents() {
        return getListView().getItems().size();
    }

	
	
}
