package w15c2.tusk.ui;

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
import w15c2.tusk.model.Alias;

//@@author A0139708W
/*
 * Panel for displaying aliases
*/
public class AliasListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(AliasListPanel.class);
    private static final String FXML = "AliasListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<Alias> aliasListView;

    /**
     * Constructor for AliasListPanel,
     * uses UiPart constructor
     */
    public AliasListPanel() {
        super();
    }
    
    /**
     * Sets the node for the panel,
     * used in UiPart load
     */
    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }
    /**
     * Returns the name of the AliasListPanel
     * FXML file.
     * 
     * @return  String of FXML file name.
     */
    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    /**
     * Assign placeHolderPane to  
     */
    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }
    
    /**
     * Loads Alias List Panel with information from an alias list.
     * 
     * @param primaryStage          Stage containing panel.
     * @param aliasListPlaceholder  Placeholder which alias list will use to display.
     * @param aliasList             List of aliases which have been added.
     * @return                      Filled alias list panel.
     */
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
    
    /**
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
    public void handleAliasChangedEvent(AliasChangedEvent event) {
        UniqueItemCollection<Alias> newAliases = event.data;
        setConnections(newAliases.getInternalList());
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Refreshed alias list"));
    }

    //@@author A0139708W-reused
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
