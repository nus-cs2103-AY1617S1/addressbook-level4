package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.model.task.Task;

/**
 * Panel containing the list of persons.
 */
public class TaskListPanel extends UiPart<VBox> {

    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    private static final String FXML = "/view/TaskListPanel.fxml";

    @FXML
    private ListView<Task> personListView;

    public TaskListPanel(ObservableList<Task> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
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

    class PersonListViewCell extends ListCell<Task> {

        PersonListViewCell() {
        }

        @Override
        protected void updateItem(Task person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                final TaskListCard personCard = new TaskListCard(person, getIndex() + 1);
                setGraphic(personCard.getRoot());
            }
        }
    }

}
