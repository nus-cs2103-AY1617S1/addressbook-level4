package seedu.savvytasker.ui;

import java.util.logging.Logger;

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
import seedu.savvytasker.commons.core.LogsCenter;
import seedu.savvytasker.model.alias.AliasSymbol;

/**
 * Panel containing the list of persons.
 */
public class AliasSymbolListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(AliasSymbolListPanel.class);
    private static final String FXML = "AliasSymbolListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<AliasSymbol> aliasSymbolListView;

    public AliasSymbolListPanel() {
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

    public static AliasSymbolListPanel load(Stage primaryStage, AnchorPane aliasSymbolListPlaceholder,
                                       ObservableList<AliasSymbol> aliasList) {
        AliasSymbolListPanel aliasListPanel =
                UiPartLoader.loadUiPart(primaryStage, aliasSymbolListPlaceholder, new AliasSymbolListPanel());
        aliasListPanel.configure(aliasList);
        return aliasListPanel;
    }

    private void configure(ObservableList<AliasSymbol> aliasList) {
        setConnections(aliasList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<AliasSymbol> aliasList) {
        aliasSymbolListView.setItems(aliasList);
        aliasSymbolListView.setCellFactory(listView -> new AliasSymbolListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        aliasSymbolListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in alias list panel changed to : '" + newValue + "'");
                //raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            aliasSymbolListView.scrollTo(index);
            aliasSymbolListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class AliasSymbolListViewCell extends ListCell<AliasSymbol> {

        public AliasSymbolListViewCell() {
        }

        @Override
        protected void updateItem(AliasSymbol symbol, boolean empty) {
            super.updateItem(symbol, empty);

            if (empty || symbol == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(AliasSymbolCard.load(symbol, getIndex() + 1).getLayout());
            }
        }
    }

}
