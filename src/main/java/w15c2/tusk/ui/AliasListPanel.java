package w15c2.tusk.ui;

import java.util.Date;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollToEvent;
import javafx.scene.control.SplitPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import w15c2.tusk.commons.collections.UniqueItemCollection;
import w15c2.tusk.commons.core.LogsCenter;
import w15c2.tusk.commons.events.model.AliasChangedEvent;
import w15c2.tusk.commons.events.model.TaskManagerChangedEvent;
import w15c2.tusk.model.Alias;

//@@author A0139708W
public class AliasListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(AliasListPanel.class);
    private static final String FXML = "AliasListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<Alias> aliasListView;

    public AliasListPanel() {
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

    public static AliasListPanel load(Stage primaryStage, AnchorPane aliasListPlaceholder,
                                       ObservableList<Alias> aliasList) {
        AliasListPanel aliasListPanel =
                UiPartLoader.loadUiPart(primaryStage, aliasListPlaceholder, new AliasListPanel());
        aliasListPanel.configure(aliasList);
        return aliasListPanel;
    }
    
    private void configure(ObservableList<Alias> aliasList) {
        setConnections(aliasList);
        setSelectableCharacteristics();
        addToPlaceholder();
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Alias> aliasList) {
        aliasListView.setItems(aliasList);
        aliasListView.setCellFactory(listView -> new AliasListViewCell());
    }
    
    /*
     * Consume all events except for scrolling and scrollevents from control up/down
     */
    private void setSelectableCharacteristics() {
        aliasListView.addEventFilter(Event.ANY, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
               EventType<?> type = event.getEventType().getSuperType();
               if (type != ScrollEvent.ANY && type != ScrollToEvent.ANY) {
                   event.consume();
               } 
            }
        });
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            aliasListView.scrollTo(index);
            aliasListView.getSelectionModel().clearAndSelect(index);
        });
    }
    
    @Subscribe
    public void handleAliasChangedEvent(AliasChangedEvent abce) {
        UniqueItemCollection<Alias> newAliases = abce.data;
        setConnections(newAliases.getInternalList());
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Refreshed alias list"));
    }

    class AliasListViewCell extends ListCell<Alias> {

        public AliasListViewCell() {
        }

        @Override
        protected void updateItem(Alias alias, boolean empty) {
            super.updateItem(alias, empty);

            if (empty || alias == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(AliasCard.load(alias, getIndex() + 1).getLayout());
            }
        }
    }


}
