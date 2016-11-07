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
/**
 * Help Overlay Display
*/
public class HelpPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(HelpPanel.class);
    private static final String FXML = "HelpListPanel.fxml";
    private static final String COMMAND_COL_TITLE = "Command";
    private static final String FORMAT_COL_TITLE = "Format";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private TableView<HelpGuide> helpListView;

    @FXML
    private Label helpLabel;

    /**
     * Empty Constructor for HelpPanel,
     * to be used during load() method
     */
    public HelpPanel() {
        super();
    }
    
    /**
     * Sets pane containing HelpPanel
     * to appropriate node, used in 
     * UiPart.
     */
    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }
    
    /**
     * Return FXML file name for UiPart loading.
     * 
     * @return  String representation of FXML file name.
     */
    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    /**
     * Set placeHolderPane to correct pane, 
     * used in UiPart.
     */
    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }
    
    /**
     * Loads Help Panel with information from a help list.
     * 
     * @param primaryStage          Stage containing panel.
     * @param helpListPlaceholder   Placeholder which help list will use to display.
     * @param helpList              List of help information which have been added.
     * @return                      Filled help panel.
     */
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
    
    /**
     * Set items to help table with helpList and create command and
     * format columns.
     * 
     * @param helpList  list of HelpGuide to be parsed into columns.
     */
    private void setConnections(ObservableList<HelpGuide> helpList) {
        helpListView.setItems(helpList);
        TableColumn<HelpGuide,String> commandCol = createCommandCol();
        TableColumn<HelpGuide,String> formatCol = createFormatCol();
        helpListView.getColumns().add(commandCol);
        helpListView.getColumns().add(formatCol);
        
        //Prevent selection of cells
        helpListView.setSelectionModel(null);
        helpLabel.setText("Help");
    }
    
    /**
     * Creates the command column, disabling sorting.
     * 
     * @return  Command Column with relevant command names.
     */
    private TableColumn<HelpGuide,String> createCommandCol() {
        TableColumn<HelpGuide,String> commandCol = new TableColumn<HelpGuide,String>(COMMAND_COL_TITLE);
        commandCol.setCellValueFactory(new PropertyValueFactory<HelpGuide, String>("name"));
        commandCol.setSortable(false);
        return commandCol;
    }
    
    /**
     * Creates the format column, disabling sorting.
     * 
     * @return  Format Column with relevant command input format information.
     */
    private TableColumn<HelpGuide,String> createFormatCol() {
        TableColumn<HelpGuide,String> formatCol = new TableColumn<HelpGuide,String>(FORMAT_COL_TITLE);
        formatCol.setCellValueFactory(new PropertyValueFactory<HelpGuide, String>("format"));
        formatCol.setSortable(false);
        return formatCol;
    }
    
    private void addToPlaceholder() {
        placeHolderPane.getChildren().add(panel);

    }

}
