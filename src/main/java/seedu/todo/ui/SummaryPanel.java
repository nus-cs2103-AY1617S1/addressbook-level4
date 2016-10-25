//@@author A0138967J
package seedu.todo.ui;

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
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.todo.model.task.ReadOnlyTask;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * Panel containing the list of tasks.
 */
public class SummaryPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(SummaryPanel.class);
    private static final String FXML = "SummaryPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> summaryListView;

    public SummaryPanel() {
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

    public static SummaryPanel load(Stage primaryStage, AnchorPane summaryPlaceholder,
                                       ObservableList<ReadOnlyTask> taskList) {
        SummaryPanel summaryPanel =
                UiPartLoader.loadUiPart(primaryStage, summaryPlaceholder, new SummaryPanel());
        summaryPanel.configure(taskList);
        return summaryPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> taskList) {
        setConnections(taskList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        summaryListView.setItems(taskList);
        summaryListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }
    private void setEventHandlerForSelectionChangeEvent() {
        summaryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in summary panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            summaryListView.scrollTo(index);
            summaryListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class TaskListViewCell extends ListCell<ReadOnlyTask> {

        public TaskListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);
            boolean dateCheck = false;
            try{
                dateCheck = LocalDate.now().isAfter(task.getByDate().getDate()) || LocalDate.now().isBefore(task.getOnDate().getDate());
                }
            catch(Exception e){
                dateCheck = false;
            }
            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else if(!dateCheck){
                setGraphic(SummaryCard.load(task).getLayout());
            } else {
                setGraphic(null);
            }
        }
    }

}
