package seedu.dailyplanner.ui;

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
import seedu.dailyplanner.commons.core.LogsCenter;
import seedu.dailyplanner.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.dailyplanner.model.task.ReadOnlyTask;

import java.util.logging.Logger;

/**
 * Panel containing the list of persons.
 */
//@@author A0140124B
public class PinnedTaskPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(PinnedTaskPanel.class);
    private static final String FXML = "PinnedTaskPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> pinListView;

    public PinnedTaskPanel() {
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

    public static PinnedTaskPanel load(Stage primaryStage, AnchorPane PinnedTaskPlaceholder,
                                       ObservableList<ReadOnlyTask> pinnedTaskList) {
        PinnedTaskPanel pinnedTaskPanel =
                UiPartLoader.loadUiPart(primaryStage, PinnedTaskPlaceholder, new PinnedTaskPanel());
        pinnedTaskPanel.configure(pinnedTaskList);
        return pinnedTaskPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> personList) {
        setConnections(personList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> personList) {
        pinListView.setItems(personList);
        pinListView.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        pinListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            pinListView.scrollTo(index);
            pinListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class PersonListViewCell extends ListCell<ReadOnlyTask> {

        public PersonListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyTask person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(TaskCard.load(person, getIndex() + 1).getLayout());
            }
        }
    }

}
