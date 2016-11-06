package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import seedu.todo.TestApp;
import seedu.todo.testutil.UiTestUtil;

import java.util.List;
import java.util.stream.Collectors;

//@@author A0135805H
/**
 * A handler for retrieving the global list of tags via {@link seedu.todo.ui.view.GlobalTagView}
 */
public class GlobalTagViewHandle extends GuiHandle {
    /* Constant */
    private static final String TAG_VIEW_PANEL_ID = "#globalTagViewPanel";
    private static final String TAGS_CONTAINER_ID = "#tagFlowPane";

    /**
     * Constructs a handle to the {@link seedu.todo.ui.view.GlobalTagView}
     *
     * @param guiRobot {@link GuiRobot} for the current GUI test.
     * @param primaryStage The stage where the views for this handle is located.
     */
    public GlobalTagViewHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    /* Interfacing Methods */
    /**
     * Returns true if the placeholder is visible.
     */
    public boolean isVisible() {
        Node view = getNode(TAG_VIEW_PANEL_ID);
        return UiTestUtil.isDisplayed(view);
    }

    /**
     * Gets a list of tag names that are displayed in this view.
     */
    public List<String> getDisplayedTags() {
        FlowPane flowPane = (FlowPane) getNode(TAGS_CONTAINER_ID);
        return flowPane.getChildren()
                .stream()
                .map(node -> ((Label) node).getText())
                .collect(Collectors.toList());
    }
}
