package seedu.taskitty.commons.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class UiUtil {
    
    /**
     * Creates an alert asking user whether to overwrite an existing file
     * @return false if NO is selected and true otherwise
     */
    public static boolean createAlertToOverwriteExistingFile() {
        Alert alert = new Alert(AlertType.CONFIRMATION,
                "Overwrite existing file?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        return alert.getResult() != ButtonType.NO;
    }
}
