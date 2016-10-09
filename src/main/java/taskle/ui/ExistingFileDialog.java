package taskle.ui;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import taskle.commons.core.Config;
import taskle.commons.util.ConfigUtil;
import taskle.commons.util.StorageDirectoryUtil;
import taskle.logic.Logic;

/**
 * 
 * Confirmation dialog to replace existing file
 *
 */
public class ExistingFileDialog {

    private static final String DIALOG_HEADER = "A Taskle data file currently exists in the specified folder.";
    private static final String DIALOG_CONTENT = "Replace existing file?";
    
    public static void load(ResultDisplay resultDisplay, Stage stage, Config config, Logic logic, File selectedDirectory) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(config.getAppTitle());
        alert.setHeaderText(DIALOG_HEADER);
        alert.setContentText(DIALOG_CONTENT);
        alert.initOwner(stage);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            StorageDirectoryUtil.updateDirectory(config, logic, selectedDirectory);
            resultDisplay.postMessage("Directory changed to: " + config.getTaskManagerFileDirectory());
        } 
    }
}
