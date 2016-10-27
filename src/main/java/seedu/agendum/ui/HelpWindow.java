package seedu.agendum.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.reflections.Reflections;
import seedu.agendum.logic.commands.Command;
import seedu.agendum.commons.core.LogsCenter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.sun.javafx.stage.StageHelper;

//@@author A0148031R
/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 650;
    private ObservableList<Map<CommandColumns, String>> commandList = FXCollections.observableArrayList();

    private AnchorPane mainPane;

    private static Stage dialogStage;

    private enum CommandColumns {
        COMMAND, DESCRIPTION, FORMAT
    }
    
    @FXML
    private TableView<Map<CommandColumns, String>> commandTable;
    
    @FXML
    private TableColumn<Map<CommandColumns, String>, String> commandColumn;
    
    @FXML
    private TableColumn<Map<CommandColumns, String>, String> descriptionColumn;
    
    @FXML
    private TableColumn<Map<CommandColumns, String>, String> formatColumn;
    
    @FXML
    private Button backButton;
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        
        backButton.setOnAction((event) -> {
            dialogStage.close();
        });
        
        commandColumn.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().get(CommandColumns.COMMAND)));
        descriptionColumn.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().get(CommandColumns.DESCRIPTION)));
        formatColumn.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().get(CommandColumns.FORMAT)));
        
        commandTable.setItems(commandList);
    }
    
    public static HelpWindow load(Stage primaryStage) {
        if (!StageHelper.getStages().contains(dialogStage)) {
            logger.fine("Showing help page about the application.");
            HelpWindow helpWindow = UiPartLoader.loadUiPart(primaryStage, new HelpWindow());
            helpWindow.configure();
            return helpWindow;
        } else {
            dialogStage.requestFocus();
            return null;
        }
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
        dialogStage.setWidth(WIDTH);
        dialogStage.setHeight(HEIGHT);
        dialogStage.setResizable(false);
        
        scene.setFill(Color.TRANSPARENT);
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        
        setIcon(dialogStage, ICON);
        loadHelpList();
        
        handleEscape(scene);
    }

    private void handleEscape(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt) {
                if (evt.getCode().equals(KeyCode.ESCAPE)) {
                    dialogStage.close();
                }
            }
        });
    }

    //@@author A0003878Y
    private void loadHelpList() {
        Reflections reflections = new Reflections("seedu.agendum");
        Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);

        for (Class<? extends Command> c :classes) {
            try {
                Map<CommandColumns, String> map = new HashMap<CommandColumns, String>();
                map.put(CommandColumns.COMMAND, c.getMethod("getName").invoke(null).toString());
                map.put(CommandColumns.FORMAT, c.getMethod("getFormat").invoke(null).toString());
                map.put(CommandColumns.DESCRIPTION, c.getMethod("getDescription").invoke(null).toString());
                commandList.add(map);
            } catch (NullPointerException e) {
                    continue;
            } catch (Exception e) {
                logger.severe("Java reflection for Command class failed");
            }
        }
    }

    public void show() {
        dialogStage.show();
    }
}
