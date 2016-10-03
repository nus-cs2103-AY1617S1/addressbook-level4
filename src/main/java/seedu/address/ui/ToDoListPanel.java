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
import seedu.address.commons.events.ui.ToDoListPanelSelectionChangedEvent;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.todo.ReadOnlyToDo;

import java.util.logging.Logger;

/**
 * Panel containing the list of to-dos
 */
public class ToDoListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(ToDoListPanel.class);
    private static final String FXML = "ToDoListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyToDo> toDoListView;

    public ToDoListPanel() {
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

    public static ToDoListPanel load(Stage primaryStage, AnchorPane toDoListPlaceholder,
                                     ObservableList<ReadOnlyToDo> toDoList) {
        ToDoListPanel toDoListPanel =
                UiPartLoader.loadUiPart(primaryStage, toDoListPlaceholder, new ToDoListPanel());
        toDoListPanel.configure(toDoList);
        return toDoListPanel;
    }

    private void configure(ObservableList<ReadOnlyToDo> toDos) {
        setConnections(toDos);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyToDo> toDoList) {
        toDoListView.setItems(toDoList);
        toDoListView.setCellFactory(listView -> new ToDoListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        toDoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in to-do list panel changed to : '" + newValue + "'");
                raise(new ToDoListPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            toDoListView.scrollTo(index);
            toDoListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class ToDoListViewCell extends ListCell<ReadOnlyToDo> {

        public ToDoListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyToDo toDo, boolean empty) {
            super.updateItem(toDo, empty);

            if (empty || toDo == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(ToDoCard.load(toDo, getIndex() + 1).getLayout());
            }
        }
    }

}
