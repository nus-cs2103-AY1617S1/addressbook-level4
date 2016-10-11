package seedu.todo.ui;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seedu.todo.MainApp;

/**
 * A utility class to load UiParts from FXML files.
 */
public class UiPartLoader {
    private final static String FXML_FILE_FOLDER = "/ui/";

    /**
     * Loads the UiPart and returns the view controller.
     *
     * @param primaryStage The primary stage for the view.
     * @param placeholder The placeholder where the loaded Ui Part is added.
     * @param sampleUiPart The sample instance of the expected UiPart class.
     * @param <T> The type of the UiPart
     */
    public static <T extends UiPart> T loadUiPart(Stage primaryStage, Pane placeholder, T sampleUiPart) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getFXMLResource(sampleUiPart.getFxmlPath()));
        Node mainNode = loadLoader(loader, sampleUiPart.getFxmlPath());
        
        T controller = loader.getController();
        controller.setStage(primaryStage);
        controller.setPlaceholder(placeholder);
        controller.setNode(mainNode);
        return controller;
    }
    
    public static <T extends UiPart> T loadUiPart(Stage primaryStage, T sampleUiPart) {
    	return loadUiPart(primaryStage, null, sampleUiPart);
    }
    
    /**
     * Returns the ui class for a specific UI Part.
     *
     * @param seedUiPart The UiPart object to be used as the ui.
     * @param <T> The type of the UiPart
     */

    public static <T extends UiPart> T loadUiPart(T seedUiPart) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getFXMLResource(seedUiPart.getFxmlPath()));
        loader.setController(seedUiPart);
        loadLoader(loader, seedUiPart.getFxmlPath());
        return seedUiPart;
    }


    private static Node loadLoader(FXMLLoader loader, String fxmlFileName) {
        try {
            return loader.load();
        } catch (Exception e) {
            String errorMessage = "FXML Load Error for " + fxmlFileName;
            throw new RuntimeException(errorMessage, e);
        }
    }
    
    private static URL getFXMLResource(String fxmlPath) {
    	return MainApp.class.getResource(FXML_FILE_FOLDER + fxmlPath);
    }

}
