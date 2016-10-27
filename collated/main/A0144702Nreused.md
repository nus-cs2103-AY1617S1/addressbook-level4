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
###### /resources/view/EventListCard.fxml
``` fxml
<?import javafx.scene.text.*?>
<?import java.lang.*?>
<?import java.net.*?>
<?import javafx.geometry.*?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>
<?import java.net.URL?>

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <children>
        <GridPane HBox.hgrow="ALWAYS">
            <columnConstraints>
                <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="150.0" />
                <ColumnConstraints hgrow="SOMETIMES" maxWidth="100.0" minWidth="10.0" prefWidth="100.0" />
            </columnConstraints>
            <children>
                <VBox alignment="CENTER_LEFT" maxHeight="150.0" minHeight="105.0" prefHeight="115.0" GridPane.columnIndex="0">
                    <stylesheets>
                        <URL value="@DarkTheme.css" />
                        <URL value="@Extensions.css" />
                    </stylesheets>
                    <padding>
                        <Insets bottom="5" left="15" right="5" top="5" />
                    </padding>

                    <children>
                        <HBox alignment="CENTER_LEFT" spacing="5">
                            <children>
                                <HBox>
                                   <children>
                                       <Label fx:id="index" prefHeight="20.0" prefWidth="15.0" styleClass="cell_small_label" text="\$index">
                                 <font>
                                    <Font size="5.0" />
                                 </font>
                              </Label>
                                       <Label fx:id="name" styleClass="cell_big_label" text="\$first" />
                                   </children>
                                </HBox>
                            </children>
                        </HBox>
                        <Label fx:id="description" styleClass="cell_small_label" text="\\$desc" />
                        <Label fx:id="duration" styleClass="cell_small_label" text="\\$duration" />
                    </children>
                </VBox>
            </children>
         <rowConstraints>
            <RowConstraints />
         </rowConstraints>
        </GridPane>
    </children>
</HBox>
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
