package seedu.taskmanager.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.events.ui.ShortItemPanelSelectionChangedEvent;
import seedu.taskmanager.model.item.ReadOnlyItem;
import seedu.taskmanager.logic.Logic;

import java.util.logging.Logger;

//@@author A0065571A
/**
 * Panel containing the list of items.
 */
public class ShortItemListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(ShortItemListPanel.class);
    private static final String FXML = "ShortItemListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;
    private Logic logic;

    @FXML
    private ListView<ReadOnlyItem> shortItemListView;

    public ShortItemListPanel() {
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
    
    @FXML 
    public void handleShortItemClick() {
        ReadOnlyItem newItem = shortItemListView.getSelectionModel().getSelectedItem();
        raise(new ShortItemPanelSelectionChangedEvent(newItem, shortItemListView.getItems().indexOf(newItem)));
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static ShortItemListPanel load(Stage primaryStage, AnchorPane shortItemListPlaceholder,
                                       ObservableList<ReadOnlyItem> itemList, Logic logic) {
        ShortItemListPanel itemListPanel =
                UiPartLoader.loadUiPart(primaryStage, shortItemListPlaceholder, new ShortItemListPanel());
        itemListPanel.configure(itemList);
        itemListPanel.logic = logic;
        return itemListPanel;
    }

    private void configure(ObservableList<ReadOnlyItem> itemList) {
        setConnections(itemList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyItem> itemList) {
        shortItemListView.setItems(itemList);
        shortItemListView.setCellFactory(listView -> new ShortItemListViewCell());
        // setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        shortItemListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in item list panel changed to : '" + newValue + "'");
                raise(new ShortItemPanelSelectionChangedEvent(newValue, shortItemListView.getItems().indexOf(newValue)));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            shortItemListView.scrollTo(index);
            shortItemListView.getSelectionModel().clearAndSelect(index);
            handleShortItemClick();
        });
    }
    
    public void updateIndex() {
        shortItemListView.setCellFactory(listView -> new ShortItemListViewCell());
    }

    class ShortItemListViewCell extends ListCell<ReadOnlyItem> {

        public ShortItemListViewCell() {

        }

        @Override
        protected void updateItem(ReadOnlyItem item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(ShortItemCard.load(item, getIndex() + 1, logic).getLayout());
            }
        }
    }

}
