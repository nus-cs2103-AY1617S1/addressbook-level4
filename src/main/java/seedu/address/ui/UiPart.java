package seedu.address.ui;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.util.AppUtil;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Base class for UI parts.
 * A 'UI part' represents a distinct part of the UI. e.g. Windows, dialogs, panels, status bars, etc.
 */
public abstract class UiPart {

    /**
     * The primary stage for the UI Part.
     */
    Stage primaryStage;

    public UiPart(){

    }

    /**
     * Raises the event via {@link EventsCenter#post(BaseEvent)}
     * @param event
     */
    protected void raise(BaseEvent event){
        EventsCenter.getInstance().post(event);
    }

    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }
    
    //@@author A0147890U
    /**
     * changes color of the card border depending on task overdue status
     */
    protected int overdueChangeBorderColor(ReadOnlyTask task, HBox cardPane) {
        if (task.getOverdue() == 2) {
            cardPane.setStyle("-fx-border-color: #02f21e");
            return 2;
        }
        
        else if (task.getOverdue() == 1) {
            cardPane.setStyle("-fx-border-color: red");
            return 1;
        }
        
        else {
            cardPane.setStyle(null);
            return 0;
        }
    }
    
    //@@author A0147890U
    /**
     * @param 24 hour string value
     * @return 12 hour string value
     * this method converts 24 hour format to 12 hour format for display in event and deadline cards
     */
    protected String twelveHourConvertor(String value) {
        int toBeConverted = Integer.parseInt(value);
        int firstTwoDigits = toBeConverted / 100;
        int twelveHourFormat = 0;
        String twelveHourClock;
        if (firstTwoDigits == 12) {
            twelveHourFormat = toBeConverted;
        } 
        else {
            twelveHourFormat = toBeConverted % 1200;
        }
        
        twelveHourClock = Integer.toString(twelveHourFormat);
        
        twelveHourClock = new StringBuilder(twelveHourClock).insert(twelveHourClock.length() - 2, '.').toString();
        
        if (firstTwoDigits == 12) {
            twelveHourClock = twelveHourClock + "pm";
        } else if (firstTwoDigits > 12) {
            twelveHourClock = twelveHourClock + "pm";
        } else {
            twelveHourClock = twelveHourClock + "am";
        }
            
        return twelveHourClock;
    } 
    
    //@@author
    /**
     * Override this method to receive the main Node generated while loading the view from the .fxml file.
     * @param node
     */
    public abstract void setNode(Node node);

    /**
     * Override this method to return the name of the fxml file. e.g. {@code "MainWindow.fxml"}
     * @return
     */
    public abstract String getFxmlPath();

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    /**
     * Creates a modal dialog.
     * @param title Title of the dialog.
     * @param parentStage The owner stage of the dialog.
     * @param scene The scene that will contain the dialog.
     * @return the created dialog, not yet made visible.
     */
    protected Stage createDialogStage(String title, Stage parentStage, Scene scene) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(parentStage);
        dialogStage.setScene(scene);
        return dialogStage;
    }

    /**
     * Sets the given image as the icon for the primary stage of this UI Part.
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    protected void setIcon(String iconSource) {
        primaryStage.getIcons().add(AppUtil.getImage(iconSource));
    }

    /**
     * Sets the given image as the icon for the given stage.
     * @param stage
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    protected void setIcon(Stage stage, String iconSource) {
        stage.getIcons().add(AppUtil.getImage(iconSource));
    }

    /**
     * Sets the placeholder for UI parts that reside inside another UI part.
     * @param placeholder
     */
    public void setPlaceholder(AnchorPane placeholder) {
        //Do nothing by default.
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
