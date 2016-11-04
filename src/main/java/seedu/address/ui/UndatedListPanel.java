package seedu.address.ui;

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
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.UndatedPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.UpdateListCountEvent;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Panel containing the list of persons.
 */
public class UndatedListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(UndatedListPanel.class);
    private static final String FXML = "UndatedListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;
    private String indexAlphabet = "A";
    
    private String label;

    @FXML
    private Label label_count;
    
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

    public static UndatedListPanel load(Stage primaryStage, AnchorPane undatedTaskListPlaceholder,
                                       ObservableList<ReadOnlyTask> personList) {
        UndatedListPanel personListPanel =
                UiPartLoader.loadUiPart(primaryStage, undatedTaskListPlaceholder, new UndatedListPanel());
        personListPanel.configure(personList); 
        return personListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> undatedTaskList) {
        setConnections(undatedTaskList);
        addToPlaceholder();
        registerAsAnEventHandler(this);
        initializeLabelCount(undatedTaskList);
    }
    
    private void initializeLabelCount(ObservableList<ReadOnlyTask> undatedTaskList) {
    	label = "To Do : ";
    	assert undatedTaskList != null;
    	label_count.setText(label + undatedTaskList.size());
	}

    private void setConnections(ObservableList<ReadOnlyTask> undatedTaskList) {
        undatedListView.setItems(undatedTaskList);
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
    
    public void clearSelection(){
    	undatedListView.getSelectionModel().clearSelection();
    }

    class UndatedListViewCell extends ListCell<ReadOnlyTask> {

        public UndatedListViewCell() {
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
    	int listSize = e.model.getFilteredUndatedTaskList().size();
    	label_count.setText(label + listSize);
    }

}
