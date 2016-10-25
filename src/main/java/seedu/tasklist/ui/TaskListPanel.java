package seedu.tasklist.ui;

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
import seedu.tasklist.commons.core.EventsCenter;
import seedu.tasklist.commons.core.LogsCenter;
import seedu.tasklist.commons.events.TickEvent;
import seedu.tasklist.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.tasklist.model.task.ReadOnlyTask;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

/**
 * Panel containing the list of persons.
 */
public class TaskListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> personListView;

    public TaskListPanel() {
        super();
    }
    
    @Subscribe
    public void tickEventHandler(TickEvent te){
    	personListView.refresh();
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

    public static TaskListPanel load(Stage primaryStage, AnchorPane personListPlaceholder,
                                       ObservableList<ReadOnlyTask> personList) {
        TaskListPanel taskListPanel =
                UiPartLoader.loadUiPart(primaryStage, personListPlaceholder, new TaskListPanel());
        taskListPanel.configure(personList);
        EventsCenter.getInstance().registerHandler(taskListPanel);
        return taskListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> personList) {
        setConnections(personList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> personList) {
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            personListView.scrollTo(index);
            personListView.getSelectionModel().clearAndSelect(index);
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
