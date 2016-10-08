package taskle.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import taskle.commons.core.LogsCenter;
import taskle.model.help.CommandGuide;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";
    private static final List<CommandGuide> LIST_COMMAND_GUIDES = new ArrayList<>(
            Arrays.asList(new CommandGuide("Adding", "add", "task_name [Date & Time]"), 
                    new CommandGuide("Editing", "task_number", "new_task_name"),
                    new CommandGuide("", "reschedule", "task_number", "Date [Time]"),
                    new CommandGuide("", "remind", "task_number", "Date [Time]"),
                    new CommandGuide("", "remind", "task_number", "null"),
                    new CommandGuide("Removing", "remove", "task_number"),
                    new CommandGuide("Finding", "find")
                    ));

    private AnchorPane mainPane;    
    private Stage dialogStage;

    @FXML
    private TableView<CommandGuide> tableView;


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

    private void configure() {
        Scene scene = new Scene(mainPane);
        // Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(false);
        setIcon(dialogStage, ICON);
        ObservableList<CommandGuide> observableGuides = 
                FXCollections.observableArrayList(LIST_COMMAND_GUIDES);
        tableView.setItems(observableGuides);
    }

    public void fillInnerPart() {

    }

    public void show() {
        dialogStage.showAndWait();
    }
}
