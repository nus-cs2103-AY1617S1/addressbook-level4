package seedu.todo.guitests.guihandles;

import java.util.Optional;

import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todo.guitests.GuiRobot;

//@@author A0139812A
public class TagListHandle extends GuiHandle {

    private static final String TAGLIST_ID = "#tagListItem";

    public TagListHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        super(guiRobot, primaryStage, stageTitle);
    }

    public TagListItemHandle getTagListDateItem(String tag) {
        Optional<Node> itemNode = guiRobot.lookup(TAGLIST_ID).queryAll().stream()
                .filter(dateItem -> new TagListItemHandle(guiRobot, primaryStage, dateItem).isEqualsTo(tag))
                .findFirst();
        
        if (itemNode.isPresent()) {
            return new TagListItemHandle(guiRobot, primaryStage, itemNode.get());
        } else {
            return null;
        }
    }
}
