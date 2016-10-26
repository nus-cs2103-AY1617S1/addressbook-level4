package seedu.oneline.ui;

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
import seedu.oneline.commons.core.LogsCenter;
import seedu.oneline.commons.events.ui.TagPanelSelectionChangedEvent;
import seedu.oneline.model.tag.Tag;

import java.util.logging.Logger;

//@@author A0142605N

/**
 * Panel containing the list of tags.
 */
public class TagListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TagListPanel.class);
    private static final String FXML = "TagListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<Tag> tagListView;

    public TagListPanel() {
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

    public static TagListPanel load(Stage primaryStage, AnchorPane tagListPlaceholder,
                                       ObservableList<Tag> tagList) {
        TagListPanel tagListPanel =
                UiPartLoader.loadUiPart(primaryStage, tagListPlaceholder, new TagListPanel());
        tagListPanel.configure(tagList);
        return tagListPanel;
    }

    private void configure(ObservableList<Tag> tagList) {
        setConnections(tagList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<Tag> tagList) {
        tagListView.setItems(tagList);
        tagListView.setCellFactory(listView -> new TagListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        tagListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in tag list panel changed to : '" + newValue + "'");
                raise(new TagPanelSelectionChangedEvent(newValue));
            }
        });
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
