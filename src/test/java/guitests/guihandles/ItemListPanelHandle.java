package guitests.guihandles;


import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.model.item.Item;
import seedu.address.model.item.ReadOnlyItem;
import seedu.address.testutil.TestUtil;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Provides a handle for the panel containing the item list.
 */
public class ItemListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String PERSON_LIST_VIEW_ID = "#itemListView";

    public ItemListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyItem> getSelectedItems() {
        ListView<ReadOnlyItem> itemList = getListView();
        return itemList.getSelectionModel().getSelectedItems();
    }

    public ListView<ReadOnlyItem> getListView() {
        return (ListView<ReadOnlyItem>) getNode(PERSON_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the item details correctly and in correct order.
     * @param items A list of item in the correct order.
     */
    public boolean isListMatching(ReadOnlyItem... items) {
        return this.isListMatching(0, items);
    }
    
    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point= TestUtil.getScreenMidPoint(getListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code items} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyItem... items) {
        List<ReadOnlyItem> itemsInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + items.length > itemsInList.size()){
            return false;
        }

        // Return false if any of the items doesn't match
        for (int i = 0; i < items.length; i++) {
            if (!itemsInList.get(startPosition + i).getDescription().getFullDescription().equals(items[i].getDescription().getFullDescription())){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if the list is showing the item details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param items A list of item in the correct order.
     */
    public boolean isListMatching(int startPosition, ReadOnlyItem... items) throws IllegalArgumentException {
        if (items.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size() - 1) + " items");
        }
        assertTrue(this.containsInOrder(startPosition, items));
        for (int i = 0; i < items.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndItem(getItemCardHandle(startPosition + i), items[i])) {
                return false;
            }
        }
        return true;
    }


    public ItemCardHandle navigateToItem(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyItem> item = getListView().getItems().stream().filter(p -> p.getDescription().getFullDescription().equals(name)).findAny();
        if (!item.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToItem(item.get());
    }

    /**
     * Navigates the listview to display and select the item.
     */
    public ItemCardHandle navigateToItem(ReadOnlyItem item) {
        int index = getItemIndex(item);

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getItemCardHandle(item);
    }


    /**
     * Returns the position of the item given, {@code NOT_FOUND} if not found in the list.
     */
    public int getItemIndex(ReadOnlyItem targetItem) {
        List<ReadOnlyItem> itemsInList = getListView().getItems();
        for (int i = 0; i < itemsInList.size(); i++) {
            if(itemsInList.get(i).getDescription().equals(targetItem.getDescription())){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a item from the list by index
     */
    public ReadOnlyItem getItem(int index) {
        return getListView().getItems().get(index);
    }

    public ItemCardHandle getItemCardHandle(int index) {
        return getItemCardHandle(new Item(getListView().getItems().get(index)));
    }

    public ItemCardHandle getItemCardHandle(ReadOnlyItem item) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> itemCardNode = nodes.stream()
                .filter(n -> new ItemCardHandle(guiRobot, primaryStage, n).isSameItem(item))
                .findFirst();
        if (itemCardNode.isPresent()) {
            return new ItemCardHandle(guiRobot, primaryStage, itemCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfPeople() {
        return getListView().getItems().size();
    }
}
