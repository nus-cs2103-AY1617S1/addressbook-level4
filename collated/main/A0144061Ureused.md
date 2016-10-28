# A0144061Ureused
###### /java/seedu/todoList/MainApp.java
``` java
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyTaskList> TodoListOptional;
        ReadOnlyTaskList initialTodoListData;
        Optional<ReadOnlyTaskList> EventListOptional;
        ReadOnlyTaskList initialEventListData;
        Optional<ReadOnlyTaskList> DeadlineListOptional;
        ReadOnlyTaskList initialDeadlineListData;
        
        try {
            TodoListOptional = storage.readTodoList();
            if(!TodoListOptional.isPresent()){
                logger.info("Data file not found. Will be starting with an empty TodoList");
            }
            initialTodoListData = TodoListOptional.orElse(new TaskList());
            
            EventListOptional = storage.readEventList();
            if(!EventListOptional.isPresent()){
                logger.info("Data file not found. Will be starting with an empty EventList");
            }
            initialEventListData = EventListOptional.orElse(new TaskList());
            
            DeadlineListOptional = storage.readDeadlineList();
            if(!DeadlineListOptional.isPresent()){
                logger.info("Data file not found. Will be starting with an empty DeadlineList");
            }
            initialDeadlineListData = DeadlineListOptional.orElse(new TaskList());
            
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty TodoList");
            initialTodoListData = new TaskList();
            initialEventListData = new TaskList();
            initialDeadlineListData = new TaskList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty TodoList");
            initialTodoListData = new TaskList();
            initialEventListData = new TaskList();
            initialDeadlineListData = new TaskList();
        }

        return new ModelManager(initialTodoListData, initialEventListData, initialDeadlineListData, userPrefs);
    }
```
###### /java/seedu/todoList/storage/XmlAdaptedDeadline.java
``` java
/**
 * JAXB-friendly version of the task.
 */
public class XmlAdaptedDeadline implements XmlAdaptedTask {
    
	@XmlElement(required = true)
	private String name;
	@XmlElement(required = true)
	private String startDate;
	@XmlElement(required = true)
    private String endTime;
	@XmlElement(required = true)
    private String isDone;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedDeadline () {}


    /**
     * Converts a given task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedtask
     */
    public XmlAdaptedDeadline(Deadline source) {
    	name = source.getName().name;
    	startDate = source.getDate().saveDate;
        endTime = source.getEndTime().saveEndTime;
        isDone = source.getDone();
    }
    
    public XmlAdaptedDeadline(ReadOnlyTask source) {
    	this((Deadline) source);
    }

    public Task toModelType() throws IllegalValueException {
        final Name name = new Name(this.name);
        final StartDate date = new StartDate(this.startDate);
        final EndTime endTime = new EndTime(this.endTime);
        final String isDone = new String(this.isDone);
        return new Deadline(name, date, endTime, isDone);
    }
}

```
###### /java/seedu/todoList/storage/XmlAdaptedEvent.java
``` java
/**
 * JAXB-friendly version of the task.
 */
public class XmlAdaptedEvent implements XmlAdaptedTask {
    
	@XmlElement(required = true)
	private String name;
	@XmlElement(required = true)
	private String startDate;
	@XmlElement(required = true)
	private String endDate;
	@XmlElement(required = true)
    private String startTime;
	@XmlElement(required = true)
    private String endTime;
	@XmlElement(required = true)
    private String isDone;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedEvent () {}


    /**
     * Converts a given task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedtask
     */
    public XmlAdaptedEvent(Event source) {
    	name = source.getName().name;
    	startDate = source.getStartDate().saveDate;
    	endDate = source.getEndDate().saveEndDate;
        startTime = source.getStartTime().saveStartTime;
        endTime = source.getEndTime().saveEndTime;
        isDone = source.getDone();
    }
    
    public XmlAdaptedEvent(ReadOnlyTask source) {
    	this((Event) source);
    }
    
    public Task toModelType() throws IllegalValueException {
        final Name name = new Name(this.name);
        final StartDate date = new StartDate(this.startDate);
        final EndDate endDate = new EndDate(this.endDate);
        final StartTime startTime = new StartTime(this.startTime);
        final EndTime endTime = new EndTime(this.endTime);
        final String isDone = new String(this.isDone);
        return new Event(name, date, endDate, startTime, endTime, isDone);
    }
}

```
###### /java/seedu/todoList/storage/XmlAdaptedTask.java
``` java
/**
 * JAXB-friendly version of the task.
 */
public interface XmlAdaptedTask {

    /**
     * Converts this jaxb-friendly adapted task object into the model's task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException;
}
```
###### /java/seedu/todoList/storage/XmlAdaptedTodo.java
``` java
/**
 * JAXB-friendly version of the task.
 */
public class XmlAdaptedTodo implements XmlAdaptedTask {
    
	@XmlElement(required = true)
	private String name;
	@XmlElement(required = true)
	private String startDate;
	@XmlElement(required = true)
	private String endDate;
	@XmlElement(required = true)
	private String priority;
	@XmlElement(required = true)
	private String isDone;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTodo () {}


    /**
     * Converts a given task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedtask
     */
    public XmlAdaptedTodo(Todo source) {
    	this.name = source.getName().name;
    	this.startDate = source.getStartDate().saveDate;
    	this.endDate = source.getEndDate().saveEndDate;
    	this.priority = source.getPriority().savePriority;
    	this.isDone = source.getDone();
    }
    
    public XmlAdaptedTodo(ReadOnlyTask source) {
    	this((Todo) source);
    }

    public Todo toModelType() throws IllegalValueException {
        final Name name = new Name(this.name);
        final StartDate date = new StartDate(this.startDate);
        final EndDate endDate = new EndDate(this.endDate);
        final Priority priority = new Priority(this.priority);
        final String isDone = new String(this.isDone);
        return new Todo(name, date, endDate, priority, isDone);
    }
}

```
###### /java/seedu/todoList/storage/XmlDeadlineListStorage.java
``` java
/**
 * A class to access TodoList data stored as an xml file on the hard disk.
 */
public class XmlDeadlineListStorage implements TaskListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlDeadlineListStorage.class);

    private String filePath;

    public XmlDeadlineListStorage(String filePath){
        this.filePath = filePath;
    }

    public String getTaskListFilePath(){
        return filePath;
    }
    
    public void setTaskListFilePath(String filePath){
        this.filePath = filePath;
    }

    /**
     * Similar to {@link #readTodoList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskList> readTaskList(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File TaskListFile = new File(filePath);

        if (!TaskListFile.exists()) {
            logger.info("TaskList file "  + TaskListFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskList TaskListOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(TaskListOptional);
    }

    /**
     * Similar to {@link #saveTodoList(ReadOnlyTodoList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskList(ReadOnlyTaskList taskList, String filePath) throws IOException {
        assert taskList != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
       	XmlFileStorage.saveDataToFile(file, new XmlSerializableDeadlineList(taskList));
    }

    @Override
    public Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException {
        return readTaskList(filePath);
    }

    @Override
    public void saveTaskList(ReadOnlyTaskList taskList) throws IOException {
        saveTaskList(taskList, filePath);
    }
}
```
###### /java/seedu/todoList/storage/XmlEventListStorage.java
``` java
/**
 * A class to access TodoList data stored as an xml file on the hard disk.
 */
public class XmlEventListStorage implements TaskListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlEventListStorage.class);

    private String filePath;

    public XmlEventListStorage(String filePath){
        this.filePath = filePath;
    }

    public String getTaskListFilePath(){
        return filePath;
    }
    
    public void setTaskListFilePath(String filePath){
        this.filePath = filePath;
    }

    /**
     * Similar to {@link #readTodoList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskList> readTaskList(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File TaskListFile = new File(filePath);

        if (!TaskListFile.exists()) {
            logger.info("TaskList file "  + TaskListFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskList TaskListOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(TaskListOptional);
    }

    /**
     * Similar to {@link #saveTodoList(ReadOnlyTodoList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskList(ReadOnlyTaskList taskList, String filePath) throws IOException {
        assert taskList != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableEventList(taskList));
    }

    @Override
    public Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException {
        return readTaskList(filePath);
    }

    @Override
    public void saveTaskList(ReadOnlyTaskList taskList) throws IOException {
        saveTaskList(taskList, filePath);
    }
}
```
###### /java/seedu/todoList/storage/XmlFileStorage.java
``` java
/**
 * Stores TaskList data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given TodoList  data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableTaskList TaskList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, TaskList);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns TodoList in the file or an empty TodoList
     */
    public static XmlSerializableTaskList loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
        	if(file.getPath().contains("TodoList.xml")) {
        		return XmlUtil.getDataFromFile(file, XmlSerializableTodoList.class);
        	}
        	else if(file.getPath().contains("EventList.xml")) {
        		return XmlUtil.getDataFromFile(file, XmlSerializableEventList.class);
        	}
        	else if(file.getPath().contains("DeadlineList.xml")) {
        		return XmlUtil.getDataFromFile(file, XmlSerializableDeadlineList.class);
        	}
        	else {
        		return XmlUtil.getDataFromFile(file, XmlSerializableTaskList.class);
        	}
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
```
###### /java/seedu/todoList/storage/XmlSerializableDeadlineList.java
``` java
/**
 * An Immutable TaskList that is serializable to XML format
 */
@XmlRootElement(name = "DeadlineList")
public class XmlSerializableDeadlineList implements XmlSerializableTaskList {

    @XmlElement
    private List<XmlAdaptedDeadline> tasks;

    {
        tasks = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableDeadlineList() {}

    /**
     * Conversion
     */
    public XmlSerializableDeadlineList(ReadOnlyTaskList src) {
    	tasks.addAll(src.getTaskList().stream().map(XmlAdaptedDeadline::new).collect(Collectors.toList()));
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedDeadline p : tasks) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}
```
###### /java/seedu/todoList/storage/XmlSerializableEventList.java
``` java
/**
 * An Immutable TaskList that is serializable to XML format
 */
@XmlRootElement(name = "EventList")
public class XmlSerializableEventList implements XmlSerializableTaskList {

    @XmlElement
    private List<XmlAdaptedEvent> tasks;

    {
        tasks = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableEventList() {}

    /**
     * Conversion
     */
    public XmlSerializableEventList(ReadOnlyTaskList src) {
    	tasks.addAll(src.getTaskList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedEvent p : tasks) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}
```
###### /java/seedu/todoList/storage/XmlSerializableTodoList.java
``` java
/**
 * An Immutable TaskList that is serializable to XML format
 */
@XmlRootElement(name = "TodoList")
public class XmlSerializableTodoList implements XmlSerializableTaskList {

    @XmlElement
    private List<XmlAdaptedTodo> tasks;

    {
        tasks = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableTodoList() {}

    /**
     * Conversion
     */
    public XmlSerializableTodoList(ReadOnlyTaskList src) {
    	tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTodo::new).collect(Collectors.toList()));
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedTodo p : tasks) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}
```
###### /java/seedu/todoList/storage/XmlTodoListStorage.java
``` java
/**
 * A class to access TodoList data stored as an xml file on the hard disk.
 */
public class XmlTodoListStorage implements TaskListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTodoListStorage.class);

    private String filePath;

    public XmlTodoListStorage(String filePath){
        this.filePath = filePath;
    }

    public String getTaskListFilePath(){
        return filePath;
    }
    
    public void setTaskListFilePath(String filePath){
        this.filePath = filePath;
    }

    /**
     * Similar to {@link #readTodoList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskList> readTaskList(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File TaskListFile = new File(filePath);

        if (!TaskListFile.exists()) {
            logger.info("TaskList file "  + TaskListFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskList TaskListOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(TaskListOptional);
    }

    /**
     * Similar to {@link #saveTodoList(ReadOnlyTodoList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskList(ReadOnlyTaskList taskList, String filePath) throws IOException {
        assert taskList != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTodoList(taskList));
    }

    @Override
    public Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException {
        return readTaskList(filePath);
    }

    @Override
    public void saveTaskList(ReadOnlyTaskList taskList) throws IOException {
        saveTaskList(taskList, filePath);
    }
}
```
###### /java/seedu/todoList/ui/DeadlineCard.java
``` java
public class DeadlineCard extends UiPart{

    private static final String FXML = "DeadlineCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label endTime;
    @FXML
    private Label done;

    private Deadline task;
    private int displayedIndex;

    public DeadlineCard(){

    }

    public static DeadlineCard load(ReadOnlyTask task, int displayedIndex){
        DeadlineCard card = new DeadlineCard();
        card.task = (Deadline) task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().name);
        id.setText(displayedIndex + ". ");
        date.setText("Date: " + task.getDate().date);
        endTime.setText("End Time: " + task.getEndTime().endTime);
        done.setText("Completed: " + task.getDone().toString());
    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
```
###### /java/seedu/todoList/ui/DeadlineListPanel.java
``` java
/**
 * Panel containing the list of tasks.
 */
public class DeadlineListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(DeadlineListPanel.class);
    private static final String FXML = "DeadlineListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> deadlineListView;

    public DeadlineListPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static DeadlineListPanel load(Stage primaryStage, AnchorPane deadlineListPlaceholder,
                                       ObservableList<ReadOnlyTask> deadlineList) {
        DeadlineListPanel deadlineListPanel =
                UiPartLoader.loadUiPart(primaryStage, deadlineListPlaceholder, new DeadlineListPanel());
        deadlineListPanel.configure(deadlineList);
        return deadlineListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> deadlineList) {
        setConnections(deadlineList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> deadlineList) {
        deadlineListView.setItems(deadlineList);
        deadlineListView.setCellFactory(listView -> new DeadlineListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        deadlineListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            deadlineListView.scrollTo(index);
            deadlineListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class DeadlineListViewCell extends ListCell<ReadOnlyTask> {

        public DeadlineListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(DeadlineCard.load(task, getIndex() + 1).getLayout());
            }
        }
    }

}
```
###### /java/seedu/todoList/ui/EventCard.java
``` java
public class EventCard extends UiPart{

    private static final String FXML = "EventCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label endDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label done;

    private Event task;
    private int displayedIndex;

    public EventCard(){

    }

    public static EventCard load(ReadOnlyTask task, int displayedIndex){
        EventCard card = new EventCard();
        card.task = (Event) task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().name);
        id.setText(displayedIndex + ". ");
        date.setText("Start Date: "+ task.getStartDate().date);
        endDate.setText("End Date: " + task.getEndDate().endDate);
        startTime.setText("Start Time: " + task.getStartTime().startTime);
        endTime.setText("End Time: " + task.getEndTime().endTime);
        done.setText("Completed: " + task.getDone().toString());
    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
```
###### /java/seedu/todoList/ui/EventListPanel.java
``` java
/**
 * Panel containing the list of tasks.
 */
public class EventListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);
    private static final String FXML = "EventListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> eventListView;

    public EventListPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static EventListPanel load(Stage primaryStage, AnchorPane eventListPlaceholder,
                                       ObservableList<ReadOnlyTask> eventList) {
        EventListPanel eventListPanel =
                UiPartLoader.loadUiPart(primaryStage, eventListPlaceholder, new EventListPanel());
        eventListPanel.configure(eventList);
        return eventListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> eventList) {
        setConnections(eventList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> eventList) {
        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new EventListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        eventListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            eventListView.scrollTo(index);
            eventListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class EventListViewCell extends ListCell<ReadOnlyTask> {

        public EventListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(EventCard.load(task, getIndex() + 1).getLayout());
            }
        }
    }

}
```
###### /java/seedu/todoList/ui/TodoCard.java
``` java
public class TodoCard extends UiPart{

    private static final String FXML = "TodoCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label endDate;
    @FXML
    private Label priority;
    @FXML
    private Label done;

    private Todo task;	
    private int displayedIndex;

    public TodoCard(){

    }

    public static TodoCard load(ReadOnlyTask task, int displayedIndex){
        TodoCard card = new TodoCard();
        card.task = (Todo) task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().name);
        id.setText(displayedIndex + ". ");
        date.setText("Start Date: " + task.getStartDate().date);
        endDate.setText("End Date: " + task.getEndDate().endDate);
        priority.setText("Priority: " + task.getPriority().toString());
        done.setText("Completed: " + task.getDone().toString());
    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
```
###### /java/seedu/todoList/ui/TodoListPanel.java
``` java
/**
 * Panel containing the list of tasks.
 */
public class TodoListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TodoListPanel.class);
    private static final String FXML = "TodoListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    ListView<ReadOnlyTask> todoListView;

    public TodoListPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static TodoListPanel load(Stage primaryStage, AnchorPane todoListPlaceholder,
                                       ObservableList<ReadOnlyTask> todoList) {
        TodoListPanel todoListPanel =
                UiPartLoader.loadUiPart(primaryStage, todoListPlaceholder, new TodoListPanel());
        todoListPanel.configure(todoList);
        return todoListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> todokList) {
        setConnections(todokList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> todoList) {
        todoListView.setItems(todoList);
        todoListView.setCellFactory(listView -> new TodoListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        todoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            todoListView.scrollTo(index);
            todoListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class TodoListViewCell extends ListCell<ReadOnlyTask> {

        public TodoListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(TodoCard.load(task, getIndex() + 1).getLayout());
            }
        }
    }

}
```
