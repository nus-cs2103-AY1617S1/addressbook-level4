package seedu.taskitty.commons.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

//@@author A0135793W
/**
 * A class for handling Ui related commands
 *
 */
public class UiUtil {
    public static ButtonType load = new ButtonType("Load");
    public static ButtonType save = new ButtonType("Save");
    
    /**
     * Creates an alert asking user whether to load or overwrite an existing file
     * @return selected button
     */
    public static ButtonType createAlertToOverwriteExistingFile(String filename) {
        
        Alert alert = new Alert(AlertType.CONFIRMATION,
                "File/Directory " + filename + " exists. Would you like to overwrite existing file or load existing file?", 
                load, save, ButtonType.CANCEL);
        alert.showAndWait();

        return alert.getResult();
    }
}
