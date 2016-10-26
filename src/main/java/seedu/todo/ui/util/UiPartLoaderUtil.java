package seedu.todo.ui.util;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.todo.MainApp;
import seedu.todo.ui.UiPart;

//@@author reused
/**
 * A utility class to load UiParts from FXML files.
 */
public class UiPartLoaderUtil {
    private final static String FXML_FILE_FOLDER = "/view/";

    public static <T extends UiPart> T loadUiPart(Stage primaryStage, T controllerSeed) {
        return loadUiPart(primaryStage, null, controllerSeed);
    }

    /**
     * Returns the ui class for a specific UI Part.
     * If a placeholder is supplied, the sampleUiPart will be attached to the placeholder automatically.
     *
     * @param primaryStage The primary stage for the view.
     * @param placeholder The placeholder where the loaded Ui Part is added.
     * @param sampleUiPart The sample of the expected UiPart class.
     * @param <T> The type of the UiPart
     */
    public static <T extends UiPart> T loadUiPart(Stage primaryStage, AnchorPane placeholder, T sampleUiPart) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(FXML_FILE_FOLDER + sampleUiPart.getFxmlPath()));
        Node mainNode = loadLoader(loader, sampleUiPart.getFxmlPath());
        UiPart controller = loader.getController();
        controller.setStage(primaryStage);
        controller.setPlaceholder(placeholder);
        controller.setNode(mainNode);
        attachToPlaceholder(placeholder, mainNode);
        return (T) controller;
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

    //@@author A0135805H
    /**
     * Attaches only one children view element to a specified placeholder.
     * However, if either one of the params is null, it will result in no-op.
     * Also, if there are any other children in the placeholder, they will be cleared first.
     *
     * @param placeholder to add the childrenView to, no-op if null
     * @param childrenView to be attached to the placeholder, no-op if null
     */
    private static void attachToPlaceholder(AnchorPane placeholder, Node childrenView) {
        if (placeholder != null && childrenView != null) {
            ObservableList<Node> placeholderChildren = placeholder.getChildren();
            placeholderChildren.clear();
            placeholderChildren.add(childrenView);
        }
    }
}
