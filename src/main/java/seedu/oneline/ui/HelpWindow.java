package seedu.oneline.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML; 
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import seedu.oneline.commons.core.LogsCenter;
import seedu.oneline.commons.util.FxViewUtil;
import seedu.oneline.logic.commands.*; 

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

//@@author A0142605N
/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";
    
    private static final Map<String, String> helpMap = new HashMap<String, String>(); 
    static {
        helpMap.put(HelpCommand.COMMAND_WORD, HelpCommand.MESSAGE_USAGE);
        helpMap.put(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE);
        helpMap.put(EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE);
        helpMap.put(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_USAGE);
        helpMap.put(DoneCommand.COMMAND_WORD, DoneCommand.MESSAGE_USAGE);
        helpMap.put(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_USAGE);
        helpMap.put(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE);
        helpMap.put(UndoCommand.COMMAND_WORD, UndoCommand.MESSAGE_USAGE);
        helpMap.put(LocationCommand.COMMAND_WORD, LocationCommand.MESSAGE_USAGE);
    }
    private static final ObservableList<Map.Entry<String, String>> helpList = 
            FXCollections.observableArrayList(helpMap.entrySet());
    
    
    
    private AnchorPane mainPane;
    private Stage dialogStage; 
    
    @FXML 
    private TableView<Map.Entry<String,String>> helpTable;
    @FXML
    TableColumn<Map.Entry<String, String>, String> commandCol;
    @FXML
    private TableColumn<Map.Entry<String, String>, String> formatCol;
    

    public static HelpWindow load(Stage primaryStage) {
        logger.fine("Showing help page about the application.");
        HelpWindow helpWindow = UiPartLoader.loadUiPart(primaryStage, new HelpWindow());
        helpWindow.configure();
        return helpWindow;
    }

    @Override
    public void setNode(Node node) {
        mainPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    private void configure(){
        Scene scene = new Scene(mainPane);
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        setIcon(dialogStage, ICON);
        buildTable();
    }
    
    //builds helpTable
    private void buildTable() {
        commandCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, 
                ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                return new SimpleStringProperty(p.getValue().getKey());
            }
        });

        formatCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, 
                ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                return new SimpleStringProperty(p.getValue().getValue());
            }
        });

        helpTable.setItems(helpList);
        helpTable.getColumns().setAll(commandCol, formatCol);
    }
    
    public void show() {
        dialogStage.showAndWait();
    }
}
