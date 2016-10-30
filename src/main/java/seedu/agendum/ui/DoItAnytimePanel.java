package seedu.agendum.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.agendum.model.task.ReadOnlyTask;

//@@author A0148031R
/**
 * Panel contains the list of uncompleted floating tasks
 */
public class DoItAnytimePanel extends UiPart {
    private static final String FXML = "DoItAnytimePanel.fxml";
    private AnchorPane panel;
    private AnchorPane placeHolderPane;
    private static ObservableList<ReadOnlyTask> mainTaskList;

    @FXML
    private ListView<ReadOnlyTask> anytimeTasksListView;

    @Override
    public void setNode(Node node) {
        panel = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static DoItAnytimePanel load(Stage primaryStage, AnchorPane OtherTasksPlaceholder,
            ObservableList<ReadOnlyTask> taskList) {
        mainTaskList = taskList;
        DoItAnytimePanel otherTasksPanel = UiPartLoader.loadUiPart(primaryStage, OtherTasksPlaceholder, new DoItAnytimePanel());
        otherTasksPanel.configure(taskList);
        return otherTasksPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> taskList) {
        setConnections(taskList.filtered(task -> !task.isCompleted() && !task.hasTime()));
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> otherTasks) {
        anytimeTasksListView.setItems(otherTasks);
        anytimeTasksListView.setCellFactory(listView -> new otherTasksListViewCell());
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            anytimeTasksListView.scrollTo(index);
            anytimeTasksListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class otherTasksListViewCell extends ListCell<ReadOnlyTask> {

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(TaskCard.load(task, mainTaskList.indexOf(task) + 1).getLayout());
            }
        }
    }

}
