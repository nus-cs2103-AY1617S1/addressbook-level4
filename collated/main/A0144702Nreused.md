# A0144702Nreused
###### /java/seedu/task/ui/EventCard.java
``` java
public class EventCard extends UiPart {
    private static final String FXML = "EventListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label index;
    @FXML
    private Label description;
    @FXML
    private Label duration;
    

    private ReadOnlyEvent event;
    private int displayedIndex;


    public static EventCard load(ReadOnlyEvent event, int displayedIndex){
        EventCard card = new EventCard();
        card.event = event;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(event.getEvent().fullName);
        index.setText(displayedIndex + ". ");
        description.setText(event.getDescriptionValue());
        duration.setText(event.getDuration().toString());
        
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
###### /java/seedu/task/ui/EventListPanel.java
``` java
public class EventListPanel extends UiPart{
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);
    private static final String FXML = "EventListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyEvent> eventListView;

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

    public static EventListPanel load(Stage primaryStage, AnchorPane eventListPlaceHolder,
                                       ObservableList<ReadOnlyEvent> eventList) {
        EventListPanel eventListPanel =
                UiPartLoader.loadUiPart(primaryStage, eventListPlaceHolder, new EventListPanel());
        eventListPanel.configure(eventList);
        return eventListPanel;
    }

    private void configure(ObservableList<ReadOnlyEvent> eventList) {
        setConnections(eventList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyEvent> eventList) {
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
                logger.fine("Selection in event list panel changed to : '" + newValue + "'");
                raise(new EventPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            eventListView.scrollTo(index);
            eventListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class EventListViewCell extends ListCell<ReadOnlyEvent> {

        public EventListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyEvent event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(EventCard.load(event, getIndex() + 1).getLayout());
            }
        }
    }

}
```
###### /resources/view/EventListPanel.fxml
``` fxml
<?import java.lang.*?>
<?import java.net.*?>
<?import javafx.scene.control.*?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.*?>
<?import java.net.URL?>

<VBox AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.task.ui.EventListPanel">
    <stylesheets>
        <URL value="@DarkTheme.css" />
        <URL value="@Extensions.css" />
    </stylesheets>
    <children>
        <ListView fx:id="eventListView" VBox.vgrow="ALWAYS" />
    </children>
</VBox>
```
