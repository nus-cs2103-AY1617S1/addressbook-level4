package seedu.todo.ui;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seedu.todo.MainApp;

/**
 * A utility class to load UiParts from FXML files.
 * 
 * @@author A0139812A
 */
public class UiPartLoader {
    private final static String FXML_FILE_FOLDER = "/ui/";
    private final static String FXML_ERROR_MESSAGE = "FXML Load Error for ";
    private final static String INSTANTION_EXCEPTION_ERROR_MESSAGE = "Could not instantiate ";
    
    /**
     * Loads the UiPart and returns the view controller.
     *
     * @param primaryStage The primary stage for the view.
     * @param placeholder  The placeholder where the loaded Ui Part is added.
     * @param uiPartClass  The UiPart class to load.
     * @param <T>          The type of the UiPart
     */
    public static <T extends UiPart> T loadUiPart(Stage primaryStage, Pane placeholder, Class<T> uiPartClass) {
        FXMLLoader loader = new FXMLLoader();
        
        // Get FXML path
        T instance = null;
        try {
            instance = uiPartClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            String errorMessage = INSTANTION_EXCEPTION_ERROR_MESSAGE + uiPartClass.getName();
            throw new RuntimeException(errorMessage, e);
        }
        String fxmlPath = instance.getFxmlPath();
        
        // Continue with loading
        loader.setLocation(getFXMLResource(fxmlPath));
        Node mainNode = loadLoader(loader, fxmlPath);

        T controller = loader.getController();
        controller.setStage(primaryStage);
        controller.setPlaceholder(placeholder);
        controller.setNode(mainNode);
        return controller;
    }


    private static Node loadLoader(FXMLLoader loader, String fxmlFileName) {
        try {
            return loader.load();
        } catch (Exception e) {
            String errorMessage = FXML_ERROR_MESSAGE + fxmlFileName;
            throw new RuntimeException(errorMessage, e);
        }
    }

    private static URL getFXMLResource(String fxmlPath) {
        return MainApp.class.getResource(FXML_FILE_FOLDER + fxmlPath);
    }

}
