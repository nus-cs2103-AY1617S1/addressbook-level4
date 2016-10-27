# A0144702Nreused
###### /java/guitests/guihandles/EventCardHandle.java
``` java
/**
 * Provides a handle to an event card in the event list panel.
 * (Morphed from TaskCardHandle) 
 * @author 
 */
public class EventCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String DURATION_FIELD_ID = "#duration";
    

    private Node node;

    public EventCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullEventName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public String getEventDuration() {
        return getTextFromLabel(DURATION_FIELD_ID);
    }

    public boolean isSameEvent(ReadOnlyEvent event){

    	return getFullEventName().equals(event.getEvent().fullName) 
                && getEventDuration().equals(event.getDuration().toString())
                && getDescription().equals(event.getDescriptionValue());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EventCardHandle) {
            EventCardHandle handle = (EventCardHandle) obj;
            return getFullEventName().equals(handle.getFullEventName())
                    && getDescription().equals(handle.getDescription())
                    && getEventDuration().equals(handle.getEventDuration());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullEventName() + " " + getDescription();
    }
}
```
###### /java/guitests/guihandles/EventListPanelHandle.java
``` java
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
```
###### /java/seedu/task/logic/LogicBasicTest.java
``` java
public class LogicBasicTest {
	 /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    protected Model model;
    protected Logic logic;

    //These are for checking the correctness of the events raised
    protected ReadOnlyTaskBook latestSavedTaskBook;
    protected boolean helpShown;
    protected int targetedJumpIndex;

    /******************************Event Subscription********************/
    
    @Subscribe
    private void handleLocalModelChangedEvent(TaskBookChangedEvent abce) {
        latestSavedTaskBook = new TaskBook(abce.data);
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpEvent she) {
        helpShown = true;
    }

    @Subscribe
    private void handleJumpToTaskListRequestEvent(JumpToTaskListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }
    
    @Subscribe
    private void handleJumpToEventListRequestEvent(JumpToTaskListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }
    
    
    /******************************Pre and Post set up*****************************/
    @Before
    public void setup() {
        model = new ModelManager();
        String tempTaskBookFile = saveFolder.getRoot().getPath() + "TempTaskBook.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTaskBookFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTaskBook = new TaskBook(model.getTaskBook()); // last saved assumed to be up to date before.
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }

    @After
    public void teardown() {
        EventsCenter.clearSubscribers();
    }

}
```
