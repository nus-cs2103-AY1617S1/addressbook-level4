package seedu.address.ui;

import java.util.Date;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

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
import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AliasChangedEvent;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.model.Alias;

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
        addToPlaceholder();
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Alias> aliasList) {
        aliasListView.setItems(aliasList);
        aliasListView.setCellFactory(listView -> new AliasListViewCell());
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
