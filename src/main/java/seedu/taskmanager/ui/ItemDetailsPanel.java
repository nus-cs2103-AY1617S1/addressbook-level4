package seedu.taskmanager.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.core.UnmodifiableObservableList;
import seedu.taskmanager.commons.events.ui.ShortItemPanelSelectionChangedEvent;
import seedu.taskmanager.model.item.Item;
import seedu.taskmanager.model.item.ReadOnlyItem;
import seedu.taskmanager.logic.Logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

//@@author A0065571A
/**
 * Panel containing the list of items.
 */
public class ItemDetailsPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(ItemDetailsPanel.class);
    private static final String FXML = "ItemDetailsPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;
    private Logic logic;
    private ObservableList<ReadOnlyItem> itemList;
    private ArrayList<Integer> itemIndex;

    @FXML
    private ListView<ReadOnlyItem> itemListView;

    public ItemDetailsPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static ItemDetailsPanel load(Stage primaryStage, AnchorPane itemListPlaceholder,
                                       Logic logic) {
        ItemDetailsPanel itemListPanel =
                UiPartLoader.loadUiPart(primaryStage, itemListPlaceholder, new ItemDetailsPanel());
        itemListPanel.configure();
        itemListPanel.logic = logic;
        itemListPanel.itemList = FXCollections.observableArrayList();
        itemListPanel.itemIndex = new ArrayList<Integer>();
        return itemListPanel;
    }

    private void configure() {
        addToPlaceholder();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    class ItemListViewCell extends ListCell<ReadOnlyItem> {

        private ArrayList<Integer> allIndexes;
    	
        public ItemListViewCell(ArrayList<Integer> allIndexes) {
            this.allIndexes = allIndexes;
        }

        @Override
        protected void updateItem(ReadOnlyItem item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                if (allIndexes.size() > getIndex()) {
                    setGraphic(ItemCard.load(item, allIndexes.get(getIndex()), logic).getLayout());
                }
            }
        }
    }
    
	public void loadItem(ReadOnlyItem item, int newIdx) {
	    // Uncomment to add more than one item
		//if (itemList.contains(item)) {
	    //    int requiredIdx = itemList.indexOf(item);
	    //    itemList.remove(requiredIdx);
	    //    itemIndex.remove(requiredIdx);
	    //} else {
	    //    itemList.add(item);
	    //    itemIndex.add(newIdx+1);
	    //}
		itemList.clear();
		itemIndex.clear();
		itemList.add(item);
		itemIndex.add(newIdx+1);
	    itemListView.setItems(itemList);
        itemListView.setCellFactory(listView -> new ItemListViewCell(itemIndex));
	}

	public void freeResources() {
        itemList.clear();
        itemIndex.clear();
        itemListView.setItems(itemList);
        itemListView.setCellFactory(listView -> new ItemListViewCell(itemIndex));	    
	}
	
	public void filterItems(FilteredList<Item> filteredItems) {
		ArrayList<Integer> detailedItemsToDelete = new ArrayList<Integer>();
	    for (int i=0; i<itemList.size(); ++i) {
	    	ReadOnlyItem detailedItem = itemList.get(i);
	        if (filteredItems.contains(detailedItem)) {
	            int idx = filteredItems.indexOf(detailedItem);
	            itemIndex.set(i, idx+1);
	        } else {
	            detailedItemsToDelete.add(i);
	        }
	    }
	    Collections.sort(detailedItemsToDelete, Collections.reverseOrder());
		for (int i=0; i<detailedItemsToDelete.size(); ++i) {
		    int deleteIdx = detailedItemsToDelete.get(i);
		    itemIndex.remove(deleteIdx);
		    itemList.remove(deleteIdx);
		}
		itemListView.setItems(itemList);
        itemListView.setCellFactory(listView -> new ItemListViewCell(itemIndex));
	}

}
