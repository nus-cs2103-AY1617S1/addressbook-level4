package guitests.guihandles;

import java.util.Optional;
import java.util.Set;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import w15c2.tusk.model.Alias;
import w15c2.tusk.model.task.Task;

//@@author A0138978E
/**
 * Provides a handle to the alias window of the app.
 */
public class AliasWindowHandle extends GuiHandle {

    private static final String ALIAS_WINDOW_TITLE = "Alias List";
    private static final String ALIAS_LIST_VIEW_ID = "#aliasListView";
    private static final String ALIAS_CARD_PANE_ID = "#cardPane";

    public AliasWindowHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, ALIAS_WINDOW_TITLE);
        guiRobot.sleep(1000);
    }
    
    public ListView<Alias> getListView() {
        return (ListView<Alias>) getNode(ALIAS_LIST_VIEW_ID);
    }
    
    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(ALIAS_CARD_PANE_ID).queryAll();
    }

    
    public AliasCardHandle getAliasCardHandle(Alias alias) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> aliasCardNode = nodes.stream()
                .filter(n -> new AliasCardHandle(guiRobot, primaryStage, n).isSameAlias(alias))
                .findFirst();
        if (aliasCardNode.isPresent()) {
            return new AliasCardHandle(guiRobot, primaryStage, aliasCardNode.get());
        } else {
            return null;
        }
    }
    
    public AliasCardHandle getTaskCardHandle(int index) {
        return getAliasCardHandle(getListView().getItems().get(index));
    }

    public boolean isWindowOpen() {
        return getNode(ALIAS_LIST_VIEW_ID) != null;
    }

    public void closeWindow() {
        super.closeWindow();
        guiRobot.sleep(500);
    }

}