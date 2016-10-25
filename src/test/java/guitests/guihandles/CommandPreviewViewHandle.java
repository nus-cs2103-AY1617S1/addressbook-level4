package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seedu.todo.TestApp;

/**
 * Created by Kai on 26/10/2016.
 */
public class CommandPreviewViewHandle extends GuiHandle {
    /* Constants */
    public static final String PREVIEW_VIEW_GRID_ID = "#previewGrid";

    /**
     * Constructs a handle to the {@link CommandPreviewViewHandle}
     *
     * @param guiRobot {@link GuiRobot} for the current GUI test.
     * @param primaryStage The stage where the views for this handle is located.
     */
    public CommandPreviewViewHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    /**
     * Get the preview {@link GridPane} object.
     */
    private GridPane getPreviewGrid() {
        return (GridPane) getNode(PREVIEW_VIEW_GRID_ID);
    }

    /**
     * Get the number of rows that is displayed on this {@link #getPreviewGrid()} object.
     */
    public int getRowsDisplayed() {
        return getPreviewGrid().getChildren().size() / 2;
    }
}
