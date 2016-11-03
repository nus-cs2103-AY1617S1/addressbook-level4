package harmony.mastermind.ui;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Popup;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

//@@author A0139194X
public class HelpPopup extends UiPart {

    private static final String FXML = "HelpPopup.fxml";
    private final String COMMAND_COL_HEADER = "Command";
    private final String FORMAT_COL_HEADER = "Format";
    private final String USAGE_COL_HEADER = "Usage";
    
    private final int COMMAND_COL_MIN_WIDTH = 100;
    private final int FORMAT_COL_MIN_WIDTH = 200;
    private final int USAGE_COL_MIN_WIDTH = 200;
    
    private final int DEFAULT_X_POS = 200;
    private final int DEFAULT_Y_POS = 100;
    private final int DEFAULT_HEIGHT = 800;
    private final int DEFAULT_WIDTH = 1000;
    private Popup popup;
    private TextArea content;
    private boolean isFirstKey;
    private TableView<HelpPopupEntry> table;
    
    TableColumn<HelpPopupEntry, String> commandCol;
    TableColumn<HelpPopupEntry, String> formatCol;
    TableColumn<HelpPopupEntry, String> usageCol;
    
    //@@author A0139194X
    public HelpPopup() {
        initTable();
        initPopup();
        isFirstKey = true;
    }

    //@@author A0139194X
    public void show(Node node) {
        
        popup.show(node, DEFAULT_X_POS, DEFAULT_Y_POS);
        popup.centerOnScreen();
    }

    //@@author A0139194X
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
        content = new TextArea();
        //properties();

        //popup.getContent().add(content);
        popup.getContent().add(table);
        popup.addEventHandler(KeyEvent.KEY_RELEASED, keyEventHandler);

        //content.setEditable(false);
    }
    
    @FXML
    private void initTable() {
        
        table = new TableView<HelpPopupEntry>();
        table.setEditable(false);
        
        initCommandCol();
        initFormatCol();
        initUsageCol();
        
        table.setItems(getList());
        table.getColumns().setAll(commandCol, formatCol, usageCol);
    }
    
    private void initCommandCol() {
        commandCol = new TableColumn<HelpPopupEntry, String>(COMMAND_COL_HEADER);
        commandCol.setMinWidth(COMMAND_COL_MIN_WIDTH);
        commandCol.setCellValueFactory(new PropertyValueFactory<>("commandWord"));
    }
    
    private void initFormatCol() {
        formatCol = new TableColumn<HelpPopupEntry, String>(FORMAT_COL_HEADER);
        formatCol.setMinWidth(FORMAT_COL_MIN_WIDTH);
        formatCol.setCellValueFactory(new PropertyValueFactory<>("format"));
    }
    
    private void initUsageCol() {
        usageCol = new TableColumn<HelpPopupEntry, String>(USAGE_COL_HEADER);
        usageCol.setMinWidth(USAGE_COL_MIN_WIDTH);
        usageCol.setCellValueFactory(new PropertyValueFactory<>("usage"));
    }
    
    private ObservableList<HelpPopupEntry> getList() {
        ObservableList<HelpPopupEntry> entries = FXCollections.observableArrayList();
        entries.add(new HelpPopupEntry("help", "help", "help"));
        entries.add(new HelpPopupEntry("exit", "exit", "exit"));
        
        return entries;
    }
    

    //@@author A0139194X
    @FXML
    EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
        public void handle(KeyEvent event) {
            if (!isFirstKey && event.getCode() != null) {
                popup.hide();
            }
            isFirstKey = !isFirstKey;
        }
    };

    //@@author A0139194X
    public void setContent(String text) {
        content.setText(text);
    }
    
    //@@author A0143378Y
    public void properties() { 
        //Setting up the width and height
        content.setPrefHeight(DEFAULT_HEIGHT);
        content.setPrefWidth(DEFAULT_WIDTH);
        
        //Setting up wrapping of text in the content box 
        content.setWrapText(true);
        
        //Setting up the background, font and borders
        content.setStyle("-fx-background-color: #00BFFF;-fx-padding:10px;"
                + "-fx-text-fill: #000080;"+ "-fx-font-family: Consolas;"
                + "-fx-alignment: center"
                );

//        content.setStyle("-fx-font-family: sample; -fx-font-size: 20;");
    }
    
    //@@author A0139194X
    class HelpPopupEntry {
        private String commandWord;
        private String format;
        private String usage;
        
        public HelpPopupEntry(String commandWord, String format, String usage) {
            this.setCommandWord(commandWord);
            this.setFormat(format);
            this.setUsage(usage);
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getCommandWord() {
            return commandWord;
        }

        public void setCommandWord(String commandWord) {
            this.commandWord = commandWord;
        }

        public String getUsage() {
            return usage;
        }

        public void setUsage(String usage) {
            this.usage = usage;
        }
    }
}
