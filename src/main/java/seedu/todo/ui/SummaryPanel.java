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
import seedu.todo.model.task.ReadOnlyTask;

import java.time.LocalDate;

/**
 * Panel containing the list of tasks.
 */
public class SummaryPanel extends UiPart {
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
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
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
                LocalDate taskByDate = task.getByDate().getDate();
                LocalDate taskOnDate = task.getOnDate().getDate();
                LocalDate todayDate = LocalDate.now();
                dateCheck = todayDate.isAfter(taskByDate) || todayDate.isBefore(taskOnDate);
                }
            catch(Exception e) {
                dateCheck = false;
            }
            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else if(!dateCheck) {
                setGraphic(SummaryCard.load(task).getLayout());
            } else {
                setGraphic(null);
            }
        }
    }

}
