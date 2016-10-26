//@@author A0142421X
package seedu.todo.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.todo.model.tag.Tag;

public class TagListPanel extends UiPart{
	private static final String FXML = "TagListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<Tag> tagListView;
    
    public TagListPanel() {
    	super();
    }
    
    @Override
    public String getFxmlPath() {
    	return FXML;
    }

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }
    
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static TagListPanel load(Stage primaryStage, AnchorPane tagListPanelPlaceholder,
    								ObservableList<Tag> tagList){
    	TagListPanel tagListPanel =
        UiPartLoader.loadUiPart(primaryStage, tagListPanelPlaceholder, new TagListPanel());
        tagListPanel.configure(tagList);
        return tagListPanel;
    }

    private void configure(ObservableList<Tag> tagList) {
        setConnections(tagList);
        addToPlaceholder(); 
    }
    
    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }
    
    private void setConnections(ObservableList<Tag> tagList) {
        tagListView.setItems(tagList);
        tagListView.setCellFactory(listView -> new TagListViewCell());
        //setEventHandlerForSelectionChangeEvent();
    }
    
    public void scrollTo(int index) {
        Platform.runLater(() -> {
            tagListView.scrollTo(index);
            tagListView.getSelectionModel().clearAndSelect(index);
        });
    }
    
    class TagListViewCell extends ListCell<Tag> {

        public TagListViewCell() {
        }

        @Override
        protected void updateItem(Tag tag, boolean empty) {
            super.updateItem(tag, empty);

            if (empty || tag == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(TagCard.load(tag).getLayout());
            }
        }
    }
}