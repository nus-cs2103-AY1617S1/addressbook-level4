package guitests.guihandles;

import java.util.List;

import guitests.GuiRobot;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.todo.TestApp;
import seedu.todo.model.tag.Tag;
//@@author A0142421X-unused
/**
 * Provides a handle for the panel containing tag List
 */
public class TagListPanelHandle extends GuiHandle{
	
	public static final int NOT_FOUND = -1;
	public static final String CARD_PANE_ID = "#cardPane";

    private static final String TAG_LIST_VIEW_ID = "#tagListView";

    public TagListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<Tag> getSelectedTasks() {
        ListView<Tag> tagList = getListView();
        return tagList.getSelectionModel().getSelectedItems();
    }

    public ListView<Tag> getListView() {
        return (ListView<Tag>) getNode(TAG_LIST_VIEW_ID);
    }
}
