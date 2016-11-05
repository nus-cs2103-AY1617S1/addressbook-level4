package w15c2.tusk.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import w15c2.tusk.commons.core.LogsCenter;
import w15c2.tusk.model.HelpGuide;

//@@author A0139708W
public class HelpPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(HelpPanel.class);
    private static final String FXML = "HelpListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private TableView<HelpGuide> helpListView;

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
            ObservableList<HelpGuide> helpList) {
        HelpPanel helpListPanel =
                UiPartLoader.loadUiPart(primaryStage, helpListPlaceholder, new HelpPanel());
        helpListPanel.configure(helpList);
        return helpListPanel;
    }

    private void configure(ObservableList<HelpGuide> helpList) {
        setConnections(helpList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<HelpGuide> helpList) {
        helpListView.setItems(helpList);
        TableColumn commandCol = new TableColumn("Command");
        TableColumn formatCol = new TableColumn("Format");
        commandCol.setCellValueFactory(new PropertyValueFactory<HelpGuide, String>("name"));
        formatCol.setCellValueFactory(new PropertyValueFactory<HelpGuide, String>("args"));
        commandCol.setSortable(false);
        formatCol.setSortable(false);
        helpListView.getColumns().addAll(commandCol, formatCol);
        helpListView.setSelectionModel(null);
        helpLabel.setText("Help");
    }
    private void addToPlaceholder() {
        placeHolderPane.getChildren().add(panel);

    }



}
