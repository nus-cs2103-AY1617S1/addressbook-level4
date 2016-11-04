package seedu.stask.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.stask.commons.core.LogsCenter;
import seedu.stask.commons.events.ui.DatedPanelSelectionChangedEvent;
import seedu.stask.commons.events.ui.UpdateListCountEvent;
import seedu.stask.model.task.ReadOnlyTask;

/**
 * Panel containing the list of persons.
 */
public class DatedListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(DatedListPanel.class);
    private static final String FXML = "DatedListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;
    private String indexAlphabet = "B";

    @FXML
    private Label label_count;
    
    @FXML
    private ListView<ReadOnlyTask> datedTaskListView;

    public DatedListPanel() {
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

    public static DatedListPanel load(Stage primaryStage, AnchorPane datedTaskListPlaceholder,
                                       ObservableList<ReadOnlyTask> datedTaskList) {
        DatedListPanel datedTaskListPanel =
                UiPartLoader.loadUiPart(primaryStage, datedTaskListPlaceholder, new DatedListPanel());
        datedTaskListPanel.configure(datedTaskList); 
        return datedTaskListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> datedTaskList) {
        setConnections(datedTaskList);
        addToPlaceholder();
        registerAsAnEventHandler(this);
        initializeLabelCount(datedTaskList);
    }

    private void initializeLabelCount(ObservableList<ReadOnlyTask> datedTaskList) {
    	String label = "Events / Deadlines : ";
    	label_count.setText(label + datedTaskList.size());
	}

	private void setConnections(ObservableList<ReadOnlyTask> datedTaskList) {
        datedTaskListView.setItems(datedTaskList);
        datedTaskListView.setCellFactory(listView -> new DatedTaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        datedTaskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                raise(new DatedPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            datedTaskListView.scrollTo(index);
            datedTaskListView.getSelectionModel().clearAndSelect(index);
        });
    }
    
    public void clearSelection(){
    	datedTaskListView.getSelectionModel().clearSelection();
    }

    class DatedTaskListViewCell extends ListCell<ReadOnlyTask> {

        public DatedTaskListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(TaskCard.load(task, getIndex() + 1, indexAlphabet).getLayout());
            }
        }
    }
    
    @Subscribe 
    private void updateLabelCount(UpdateListCountEvent e){
    	int listSize = e.model.getFilteredDatedTaskList().size();
    	String label = "Events / Deadlines : ";
    	label_count.setText(label + listSize);
    }

}
