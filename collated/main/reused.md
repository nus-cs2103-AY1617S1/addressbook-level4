# reused
###### \java\seedu\todo\commons\util\CollectionUtil.java
``` java
/**
 * Utility methods related to Collections
 */
public class CollectionUtil {
    /**
     * Returns true if every element in a collection are unique by {@link Object#equals(Object)}.
     */
    public static boolean elementsAreUnique(Collection<?> items) {
        final Set<Object> testSet = new HashSet<>();
        for (Object item : items) {
            final boolean itemAlreadyExists = !testSet.add(item); // see Set documentation
            if (itemAlreadyExists) {
                return false;
            }
        }
        return true;
    }
}
```
###### \java\seedu\todo\ui\UiManager.java
``` java
    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showAlertDialogAndWait(AlertType.ERROR, "File Operation Error", 
            "There was a problem when saving/loading your data", event.message);
    }
}
```
###### \java\seedu\todo\ui\util\FxViewUtil.java
``` java
    /**
     * Applies an anchor to the left, right, top and bottom boundary of {@code node}.
     */
    public static void applyAnchorBoundaryParameters(Node node, double left, double right, double top, double bottom) {
        AnchorPane.setBottomAnchor(node, bottom);
        AnchorPane.setLeftAnchor(node, left);
        AnchorPane.setRightAnchor(node, right);
        AnchorPane.setTopAnchor(node, top);
    }

```
###### \java\seedu\todo\ui\util\UiPartLoaderUtil.java
``` java
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

```
###### \resources\style\ModernUi.css
``` css
/**Modern UI***/
/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #1d1d1d;
    -fx-font-family: "Roboto";
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
    -fx-background-color: white;
    -fx-text-fill: #1d1d1d;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #1d1d1d;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #fff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#1d1d1d, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar .thumb {
    -fx-background-color: derive(#1d1d1d, 50%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}
```
