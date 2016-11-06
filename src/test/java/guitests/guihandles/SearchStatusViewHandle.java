package guitests.guihandles;

//@@author A0135805H

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.todo.TestApp;
import seedu.todo.testutil.UiTestUtil;

/**
 * A handler for retrieving the status of the search result via {@link seedu.todo.ui.view.SearchStatusView}
 */
public class SearchStatusViewHandle extends GuiHandle {
    /* Constants */
    private static final String SEARCH_STATUS_VIEW_ID = "#searchStatusView";
    private static final String SEARCH_TERM_LABEL_ID = "#searchTerm";
    private static final String SEARCH_COUNT_LABEL_ID = "#searchCount";

    /**
     * Constructs a handle to the {@link seedu.todo.ui.view.SearchStatusView}
     *
     * @param guiRobot {@link GuiRobot} for the current GUI test.
     * @param primaryStage The stage where the views for this handle is located.
     */
    public SearchStatusViewHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    /* Interfacing Methods */
    /**
     * Returns true if the view is visible.
     */
    public boolean isVisible() {
        Node view = getNode(SEARCH_STATUS_VIEW_ID);
        return UiTestUtil.isDisplayed(view);
    }

    /**
     * Returns the search terms displayed in the view, separated by comma.
     */
    public String getSearchTermText() {
        Text text = (Text) getNode(SEARCH_TERM_LABEL_ID);
        return text.getText();
    }

    /**
     * Returns true if the number of items found matches the count displayed in this view.
     */
    public boolean doesSearchCountMatch(int count) {
        Text text = (Text) getNode(SEARCH_COUNT_LABEL_ID);
        String actual = text.getText();
        String expected;
        if (count == 1) {
            expected = "1 task found";
        } else {
            expected = count + " tasks found";
        }
        return expected.equals(actual);
    }
}
