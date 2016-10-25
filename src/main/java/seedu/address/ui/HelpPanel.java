package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

//@@author A0139708W
public class HelpPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(HelpPanel.class);
    private static final String FXML = "HelpListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;
    
    @FXML
    private ListView<String> helpListView;

    @FXML
    private Label helpLabel;

    public HelpPanel() {
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

    public static HelpPanel load(Stage primaryStage, AnchorPane helpListPlaceholder,
                                       ObservableList<String> helpList) {
        HelpPanel helpListPanel =
                UiPartLoader.loadUiPart(primaryStage, helpListPlaceholder, new HelpPanel());
        helpListPanel.configure(helpList);
        return helpListPanel;
    }
    
    private void configure(ObservableList<String> helpList) {
        setConnections(helpList);
        helpLabel.setText("Help");
        addToPlaceholder();
    }

    private void setConnections(ObservableList<String> helpList) {
        helpListView.setItems(helpList);
    }
    private void addToPlaceholder() {
        placeHolderPane.getChildren().add(panel);
        
    }



}
