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
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private static final String FXML = "PersonListPanel.fxml";
    public static final int DATED_DISPLAY_INDEX_OFFSET = 10;
    public static final int UNDATED_DISPLAY_INDEX_OFFSET = 0;
    private VBox panel;
    private AnchorPane placeHolderPane;
    private int indexOffset;

    @FXML
    private Label label_count;
    
    @FXML
    private ListView<ReadOnlyTask> personListView;

    public PersonListPanel() {
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

    public static PersonListPanel load(Stage primaryStage, AnchorPane personListPlaceholder,
                                       ObservableList<ReadOnlyTask> personList, int indexStart) {
        PersonListPanel personListPanel =
                UiPartLoader.loadUiPart(primaryStage, personListPlaceholder, new PersonListPanel());
        personListPanel.configure(personList, indexStart); 
        return personListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> personList, int indexStart) {
        this.indexOffset = indexStart;
        setConnections(personList);
        addToPlaceholder();
        registerAsAnEventHandler(this);
        initializeLabelCount(personList);
    }

    private void initializeLabelCount(ObservableList<ReadOnlyTask> personList) {
    	String label = "Events / Deadlines : ";
    	label_count.setText(label + personList.size());
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
                raise(new PersonPanelSelectionChangedEvent(newValue));
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
                setGraphic(PersonCard.load(person, getIndex() + 1 + indexOffset).getLayout());
            }
        }
    }
    
    @Subscribe 
    private void updateLabelCount(TaskBookChangedEvent e){
    	int listSize = e.data.getDatedTaskList().size();
    	String label = "Events / Deadlines : ";
    	label_count.setText(label + listSize);
    }

}
