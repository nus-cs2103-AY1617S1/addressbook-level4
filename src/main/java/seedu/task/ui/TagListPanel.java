package seedu.task.ui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import seedu.task.commons.core.LogsCenter;
import seedu.task.model.tag.Tag;

import java.util.logging.Logger;

/**
 * Panel for the tag list.
 */
public class TagListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TagListPanel.class);
    private static final String FXML = "TagListPanel.fxml";
    private FlowPane panel;
    private AnchorPane placeHolderPane;

    public TagListPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (FlowPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static TagListPanel load(Stage primaryStage, AnchorPane taskListPlaceholder,
                                       ObservableList<Tag> tagList) {
        TagListPanel tagListPanel =
                UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new TagListPanel());
        tagListPanel.configure(tagList);
        return tagListPanel;
    }

    private void configure(ObservableList<Tag> tagList) {
        setConnections(tagList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<Tag> taskList) {
        for (Tag tg : taskList) {
            panel.getChildren().add(TagCard.load(tg).getLayout());
        }
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }
}
