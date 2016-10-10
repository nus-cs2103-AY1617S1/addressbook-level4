package seedu.todo.ui;

import java.util.function.Function;

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

    public static <T extends UiPart> T loadUiPart(Stage primaryStage, T controllerSeed) {
        return loadUiPart(primaryStage, null, controllerSeed);
    }

    /**
     * Returns the ui class for a specific UI Part.
     *
     * @param primaryStage The primary stage for the view.
     * @param placeholder The placeholder where the loaded Ui Part is added.
     * @param sampleUiPart The sample of the expected UiPart class.
     * @param <T> The type of the UiPart
     */
    public static <T extends UiPart> T loadUiPart(Stage primaryStage, Pane placeholder, T sampleUiPart, Function<T,T> callback) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(FXML_FILE_FOLDER + sampleUiPart.getFxmlPath()));
        Node mainNode = loadLoader(loader, sampleUiPart.getFxmlPath());
        
        T controller = loader.getController();
        controller = callback.apply(controller);
        
        controller.setStage(primaryStage);
        controller.setPlaceholder(placeholder);
        controller.setNode(mainNode);
        return (T)controller;
    }
    
    // Overloaded with optional argument.
    public static <T extends UiPart> T loadUiPart(Stage primaryStage, Pane placeholder, T sampleUiPart) {
    	return loadUiPart(primaryStage, placeholder, sampleUiPart, (T controller) -> controller);
    }

    /**
     * Returns the ui class for a specific UI Part.
     *
     * @param seedUiPart The UiPart object to be used as the ui.
     * @param <T> The type of the UiPart
     */

    public static <T extends UiPart> T loadUiPart(T seedUiPart) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(FXML_FILE_FOLDER + seedUiPart.getFxmlPath()));
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

}
