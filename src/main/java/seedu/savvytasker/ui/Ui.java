package seedu.savvytasker.ui;

import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Stops the UI. */
    void stop();
    
    /** Shows/Hides the task list */
    void showTaskList(boolean isShown);

}
