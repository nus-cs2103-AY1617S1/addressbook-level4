package harmony.mastermind.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import harmony.mastermind.commons.core.LogsCenter;
import harmony.mastermind.logic.HelpPopupEntry;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.Popup;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

//@@author A0139194X
/**
 * Class to initialise the Help Popup containing a table of information.
 */
public class HelpPopup extends UiPart {

    private static final String FXML = "HelpPopup.fxml";
    private static final Logger logger = LogsCenter.getLogger(HelpPopup.class);

    private final String COMMAND_COL_HEADER = "Command";
    private final String FORMAT_COL_HEADER = "Format";
    private final String DESCRIPTION_COL_HEADER = "Description";
    
    private final int COMMAND_COL_MIN_WIDTH = 150;
    private final int FORMAT_COL_MIN_WIDTH = 300;
    private final int DESCRIPTION_COL_MIN_WIDTH = 400;
    
    private final int DEFAULT_X_POS = 200;
    private final int DEFAULT_Y_POS = 100;

    private Popup popup;
    private boolean isFirstKey;
    private TableView<HelpPopupEntry> table;
    
    TableColumn<HelpPopupEntry, String> commandCol;
    TableColumn<HelpPopupEntry, String> formatCol;
    TableColumn<HelpPopupEntry, String> descriptionCol;
    
    ObservableList<HelpPopupEntry> entries;
    
    //@@author A0139194X
    public HelpPopup() {
        initTable();
        initPopup();
        isFirstKey = true;
    }

    //@@author A0139194X
    public void show(Node node) {
        assert node != null;
        table.setItems(entries);
        logger.fine("Displaying help Popup");
        popup.show(node, DEFAULT_X_POS, DEFAULT_Y_POS);
        popup.centerOnScreen();
    }

    //@@author
    @Override
    public void setNode(Node node) {
    }

    //@@author A0139194X
    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    //@@author A0139194X
    @FXML
    private void initPopup() {
        popup = new Popup();

        popup.getContent().add(table);

        popup.addEventHandler(KeyEvent.KEY_RELEASED, keyEventHandler);
    }
    
    /**@@author A0139194X
     * Initialise the table
     */
    @FXML
    private void initTable() {
        logger.info("Initialising help popup's table");
        table = new TableView<HelpPopupEntry>();
        table.setEditable(false);
        
        initCommandCol();
        initFormatCol();
        initDescriptionCol();
        
        table.getColumns().addAll(commandCol, formatCol, descriptionCol);
    }
    
    /**@@author A0139194X
     * Initialise the Command word Column
     */
    private void initCommandCol() {
        commandCol = new TableColumn<HelpPopupEntry, String>(COMMAND_COL_HEADER);
        commandCol.setMinWidth(COMMAND_COL_MIN_WIDTH);
        commandCol.setCellValueFactory(entry -> new ReadOnlyStringWrapper(entry.getValue().getCommandWord()));
    }

    /**@@author A0139194X
     * Initialise the format Column
     */
    private void initFormatCol() {
        formatCol = new TableColumn<HelpPopupEntry, String>(FORMAT_COL_HEADER);
        formatCol.setMinWidth(FORMAT_COL_MIN_WIDTH);
        formatCol.setCellValueFactory(entry -> new ReadOnlyStringWrapper(entry.getValue().getFormat()));
    }
    
    /**@@author A0139194X
     * Initialise the description Column
     */
    private void initDescriptionCol() {
        descriptionCol = new TableColumn<HelpPopupEntry, String>(DESCRIPTION_COL_HEADER);
        descriptionCol.setMinWidth(DESCRIPTION_COL_MIN_WIDTH);
        descriptionCol.setCellValueFactory(entry -> new ReadOnlyStringWrapper(entry.getValue().getDescription()));
    }
    
    //@@author A0139194X
    //Handles the closing of the popup
    @FXML
    EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
        public void handle(KeyEvent event) {
            if (!isFirstKey && event.getCode() != null) {
                popup.hide();
            }
            isFirstKey = !isFirstKey;
        }
    };
    

    /**
     * @@author A0143378Y-unused
     * Styling for TextArea. Unused because we initially use a TextArea contained in a popup.
     * But we changed it to a TableView instead.
     */
    /*
    public void properties() { 
        //Setting up the width and height
        content.setPrefHeight(DEFAULT_HEIGHT);
        content.setPrefWidth(DEFAULT_WIDTH);
        
        //Setting up wrapping of text in the content box 
        content.setWrapText(true);
        
        //Setting up the background, font and borders
        content.setStyle("-fx-background-color: #00BFFF;"
                + "-fx-padding:10px;"
                + "-fx-text-fill: #000080;"
                + "-fx-font-family: Fantasy;"
                + "-fx-alignment: center"
                + "-fx-font-size: 20px"
                );
    }
    */

    //@@author A0139194X
    //Sets the data to display
    public void injectData(ArrayList<HelpPopupEntry> helpEntries) {
        entries = FXCollections.observableArrayList();
        for (int i = 0; i < helpEntries.size(); i++) {
            entries.add(helpEntries.get(i));
        }
        logger.fine("Help Popup table entries injected and initialised succesfully");
    }
}
