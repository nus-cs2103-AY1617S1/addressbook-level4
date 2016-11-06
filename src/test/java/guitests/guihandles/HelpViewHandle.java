package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import seedu.todo.TestApp;
import seedu.todo.testutil.UiTestUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@@author A0135805H
/**
 * A handler for retrieving help view details to user via {@link seedu.todo.ui.view.HelpView}
 */
public class HelpViewHandle extends GuiHandle {
    /* Constants */
    private static final String HELP_GRID_ID = "#helpGrid";
    private static final String HELP_VIEW_ID = "#helpPanelView";

    /**
     * Constructs a handle to the {@link seedu.todo.ui.view.HelpView}
     *
     * @param guiRobot {@link GuiRobot} for the current GUI test.
     * @param primaryStage The stage where the views for this handle is located.
     */
    public HelpViewHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    /**
     * Returns true if the placeholder is visible.
     */
    public boolean isVisible() {
        Node view = getNode(HELP_VIEW_ID);
        return UiTestUtil.isDisplayed(view);
    }

    /**
     * Returns the number of help items displayed.
     */
    public int numberOfItemsDisplayed() {
        GridPane helpGrid = (GridPane) getNode(HELP_GRID_ID);
        return helpGrid.getChildren().size() / 2;
    }

    /**
     * Returns a set of displayed help items, where each item represents an array of:
     *      [description, command keyword, command arguments]
     */
    private List<String[]> getDisplayedHelpItems() {
        List<String[]> displayedHelpItems = new ArrayList<>();
        GridPane helpGrid = (GridPane) getNode(HELP_GRID_ID);
        List<Node> helpGridNodes = helpGrid.getChildren();

        for (int i = 0; i < helpGridNodes.size(); i += 2) {
            String[] helpItem = new String[3];
            List<Node> commandDetails = ((TextFlow) helpGridNodes.get(i+1)).getChildren();
            helpItem[0] = ((Text) helpGridNodes.get(i)).getText();
            helpItem[1] = ((Text) commandDetails.get(0)).getText();
            helpItem[2] = ((Text) commandDetails.get(1)).getText();

            //Because there is a space between command keyword and argument in the argument text,
            //we need to remove it.
            helpItem[2] = helpItem[2].substring(1, helpItem[2].length());
            displayedHelpItems.add(helpItem);
        }
        return displayedHelpItems;
    }

    /**
     * Returns true if this help view displays all the help items correctly, by checking if the data provided
     * by {@code commandSummaries} matches to what is displayed.
     */
    public boolean isHelpItemsDisplayedCorrectly(List<String[]> commandSummaries) {
        List<String[]> displayedCommandSummaries = getDisplayedHelpItems();

        for (int i = 0; i < commandSummaries.size(); i++) {
            String[] expectedCommandSummary = commandSummaries.get(i);
            String[] displayedCommandSummary = displayedCommandSummaries.get(i);
            if (!Arrays.equals(expectedCommandSummary, displayedCommandSummary)) {
                return false;
            }
        }
        return true;
    }
}