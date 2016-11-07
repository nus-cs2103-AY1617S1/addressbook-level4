package seedu.dailyplanner.ui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.dailyplanner.commons.core.LogsCenter;
import seedu.dailyplanner.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.dailyplanner.logic.Logic;
import seedu.dailyplanner.model.task.ReadOnlyTask;

import java.util.logging.Logger;

import com.sun.javafx.tk.Toolkit.Task;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private static final String FXML = "PersonListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;
    private Logic logic;

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
                                       ObservableList<ReadOnlyTask> personList, Logic logic) {
        
        PersonListPanel personListPanel =
                UiPartLoader.loadUiPart(primaryStage, personListPlaceholder, new PersonListPanel());
        personListPanel.configure(personList, logic);
        return personListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> personList, Logic logic) {
        this.logic = logic;
        setConnections(personList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> personList) {
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
        setEventHandlerForAdditionToListEvent(personList);
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
    
    private void setEventHandlerForAdditionToListEvent(ObservableList<ReadOnlyTask> personList) {
       logic.getLastTaskAddedIndexProperty().addListener(new ChangeListener<Object>() {
      @Override
      public void changed(ObservableValue observableValue, Object oldValue,
          Object newValue) {
        scrollTo((int) newValue);
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
                setGraphic(PersonCard.load(person, getIndex() + 1).getLayout());
            }
        }
    }

}
