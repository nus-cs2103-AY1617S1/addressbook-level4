package seedu.address.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.events.ui.UndatedPanelSelectionChangedEvent;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * Panel containing the list of persons.
 */
public class UndatedListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(UndatedListPanel.class);
    private static final String FXML = "UndatedListPanel.fxml";
    public static final int DATED_DISPLAY_INDEX_OFFSET = 10;
    public static final int UNDATED_DISPLAY_INDEX_OFFSET = 0;
    private VBox panel;
    private AnchorPane placeHolderPane;
    private int indexOffset;

    @FXML
    private ListView<ReadOnlyTask> undatedListView;

    public UndatedListPanel() {
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

    public static UndatedListPanel load(Stage primaryStage, AnchorPane personListPlaceholder,
                                       ObservableList<ReadOnlyTask> personList, int indexStart) {
        UndatedListPanel personListPanel =
                UiPartLoader.loadUiPart(primaryStage, personListPlaceholder, new UndatedListPanel());
        personListPanel.configure(personList, indexStart); 
        return personListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> personList, int indexStart) {
        this.indexOffset = indexStart;
        setConnections(personList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> personList) {
        undatedListView.setItems(personList);
        undatedListView.setCellFactory(listView -> new UndatedListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        undatedListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                raise(new UndatedPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            undatedListView.scrollTo(index);
            undatedListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class UndatedListViewCell extends ListCell<ReadOnlyTask> {

        public UndatedListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyTask person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(PersonCard.load(person, getIndex() + 1 + indexOffset).getLayout());
            }
        }
    }

}
