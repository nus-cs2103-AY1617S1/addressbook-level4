package seedu.agendum.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.reflections.Reflections;
import seedu.agendum.logic.commands.Command;
import seedu.agendum.commons.core.LogsCenter;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

//@@author A0148031R
/**
 * Controller for help anchorpane
 */
public class HelpWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";
    private static final int PADDING = 11;
    private static final double COMMAND_COLUMN_WIDTH = 0.2;
    private static final double DESCRIPTION_COLUMN_WIDTH = 0.4;
    private static final double FORMAT_COLUMN_WIDTH = 0.4;
    private ObservableList<Map<CommandColumns, String>> commandList = FXCollections.observableArrayList();

    private enum CommandColumns {
        COMMAND, DESCRIPTION, FORMAT
    }
    
    @FXML
    private AnchorPane helpWindowRoot;
    
    @FXML
    private TableView<Map<CommandColumns, String>> commandTable;
    
    @FXML
    private TableColumn<Map<CommandColumns, String>, String> commandColumn;
    
    @FXML
    private TableColumn<Map<CommandColumns, String>, String> descriptionColumn;
    
    @FXML
    private TableColumn<Map<CommandColumns, String>, String> formatColumn;
    
    private StackPane messagePlaceHolder;
    private AnchorPane mainPane;
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        
        commandColumn.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().get(CommandColumns.COMMAND)));
        descriptionColumn.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().get(CommandColumns.DESCRIPTION)));
        formatColumn.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().get(CommandColumns.FORMAT)));
        commandTable.setItems(commandList);
        commandTable.setEditable(false);
    }
    
    public static HelpWindow load(Stage primaryStage, StackPane messagePlaceHolder) {
        logger.fine("Showing help page about the application.");
        HelpWindow helpWindow = UiPartLoader.loadUiPart(primaryStage, new HelpWindow());
        helpWindow.configure(messagePlaceHolder);
        return helpWindow;
    }
    
    @Override
    public void setNode(Node node) {
        this.mainPane = (AnchorPane)node;
    }
    
    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    public AnchorPane getMainPane() {
        return this.mainPane;
    }

    private void configure(StackPane messagePlaceHolder){
        this.messagePlaceHolder = messagePlaceHolder;
        commandColumn.prefWidthProperty().bind(commandTable.widthProperty().multiply(COMMAND_COLUMN_WIDTH));
        descriptionColumn.prefWidthProperty().bind(commandTable.widthProperty().multiply(DESCRIPTION_COLUMN_WIDTH));
        formatColumn.prefWidthProperty().bind(commandTable.widthProperty().multiply(FORMAT_COLUMN_WIDTH));
        loadHelpList();
    }
    
    public void show(double height) {
        this.messagePlaceHolder.setPadding(new Insets(PADDING));
        this.helpWindowRoot.setMinHeight(height);
        this.messagePlaceHolder.setPrefSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
        this.messagePlaceHolder.getChildren().add(helpWindowRoot);
    }

    //@@author A0003878Y

    /**
     * Uses Java reflection followed by Java stream.map() to retrieve all commands for listing on the Help
     * window dynamically
     */
    private void loadHelpList() {
        new Reflections("seedu.agendum").getSubTypesOf(Command.class)
                .stream()
                .map(s -> {
                    try {
                        Map<CommandColumns, String> map = new HashMap<>();
                        map.put(CommandColumns.COMMAND, s.getMethod("getName").invoke(null).toString());
                        map.put(CommandColumns.FORMAT, s.getMethod("getFormat").invoke(null).toString());
                        map.put(CommandColumns.DESCRIPTION, s.getMethod("getDescription").invoke(null).toString());
                        return map;
                    } catch (NullPointerException e) {
                        return null; // Suppress this exception are we expect some Commands to not conform to these methods
                    } catch (Exception e) {
                        logger.severe("Java reflection for Command class failed");
                        throw new RuntimeException();
                    }
                })
                .filter(p -> p != null) // remove nulls
                .sorted((lhs, rhs) -> lhs.get(CommandColumns.COMMAND).compareTo(rhs.get(CommandColumns.COMMAND)))
                .forEach(m -> commandList.add(m));
    }
}
