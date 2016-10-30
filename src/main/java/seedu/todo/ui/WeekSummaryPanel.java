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
import java.util.logging.Logger;

/**
 * Panel containing the list of tasks.
 */
public class WeekSummaryPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(WeekSummaryPanel.class);
    private static final String FXML = "WeekSummaryPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> weekSummaryListView;

    public WeekSummaryPanel() {
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

    public static WeekSummaryPanel load(Stage primaryStage, AnchorPane weekSummaryPlaceholder,
                                       ObservableList<ReadOnlyTask> taskList) {
        WeekSummaryPanel weekSummaryPanel =
                UiPartLoader.loadUiPart(primaryStage, weekSummaryPlaceholder, new WeekSummaryPanel());
        weekSummaryPanel.configure(taskList);
        return weekSummaryPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> taskList) {
        setConnections(taskList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        weekSummaryListView.setItems(taskList);
        weekSummaryListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }
    private void setEventHandlerForSelectionChangeEvent() {
        weekSummaryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in week summary panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            weekSummaryListView.scrollTo(index);
            weekSummaryListView.getSelectionModel().clearAndSelect(index);
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
                dateCheck = LocalDate.now().isAfter(task.getByDate().getDate()) || LocalDate.now().plusWeeks(1).isBefore(task.getOnDate().getDate());
                }
            catch(Exception e){
                dateCheck = false;
            }
            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else if(!dateCheck){
                setGraphic(WeekSummaryCard.load(task).getLayout());
            } else {
                setGraphic(null);
            }
        }
    }

}
