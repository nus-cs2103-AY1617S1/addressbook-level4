package tars.ui;

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
import tars.model.task.rsv.RsvTask;


/**
 * Panel containing the list of reserved tasks.
 */
public class RsvTaskListPanel extends UiPart {
    private static final String FXML = "RsvTaskListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<RsvTask> rsvTaskListView;

    public RsvTaskListPanel() {
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

    public static RsvTaskListPanel load(Stage primaryStage, AnchorPane rsvTaskListPlaceholder,
                                       ObservableList<RsvTask> rsvTaskList) {
        RsvTaskListPanel rsvTaskListPanel =
                UiPartLoader.loadUiPart(primaryStage, rsvTaskListPlaceholder, new RsvTaskListPanel());
        rsvTaskListPanel.configure(rsvTaskList);
        return rsvTaskListPanel;
    }

    private void configure(ObservableList<RsvTask> rsvTaskList) {
        setConnections(rsvTaskList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<RsvTask> rsvTaskList) {
        rsvTaskListView.setItems(rsvTaskList);
        rsvTaskListView.setCellFactory(listView -> new RsvTaskListViewCell());
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            rsvTaskListView.scrollTo(index);
            rsvTaskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class RsvTaskListViewCell extends ListCell<RsvTask> {

        public RsvTaskListViewCell() {
        }

        @Override
        protected void updateItem(RsvTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(RsvTaskCard.load(task, getIndex() + 1).getLayout());
            }
        }
    }

}
