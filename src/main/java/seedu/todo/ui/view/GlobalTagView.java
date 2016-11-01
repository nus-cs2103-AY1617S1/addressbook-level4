package seedu.todo.ui.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.ui.util.FxViewUtil;
import seedu.todo.model.tag.Tag;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.util.UiPartLoaderUtil;
import seedu.todo.ui.util.ViewGeneratorUtil;
import seedu.todo.ui.util.ViewStyleUtil;

import java.util.Collection;
import java.util.logging.Logger;

//@@author A0092382A
/**
 * A view that displays all the help commands in a single view.
 */
public class GlobalTagView extends UiPart {

    /* Constants */
    private static final String FXML = "GlobalTagView.fxml";
    private static final int PREF_WIDTH = 340;

    /* Variables */
    private final Logger logger = LogsCenter.getLogger(GlobalTagView.class);

    /*Layouts*/
    private VBox globalTagViewPanel;

    @FXML private FlowPane tagFlowPane;

    /**
     * Loads and initialise the feedback view element to the placeHolder
     * @param primaryStage of the application
     * @param placeholder where the view element {@link #globalTagViewPanel} should be placed
     * @return an instance of this class
     */
    public static GlobalTagView load(Stage primaryStage, AnchorPane placeholder) {
        GlobalTagView globalTagView = UiPartLoaderUtil.loadUiPart(primaryStage, placeholder, new GlobalTagView());
        globalTagView.configureLayout();
        globalTagView.hideGlobalTagViewPanel();
        return globalTagView;
    }

    /**
     * Configure the UI layout of {@link globalTagView}
     */
    private void configureLayout() {
        FxViewUtil.applyAnchorBoundaryParameters(globalTagViewPanel, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(tagFlowPane, 0.0, 0.0, 0.0, 0.0);
    }

    private void appendTag(Tag tag) {
        Label tagLabel = constructTagLabel(tag);
        tagFlowPane.getChildren().add(tagLabel);
    }

    private Label constructTagLabel(Tag tag) {
        String tagName = tag.getTagName();
        Label tagLabel = ViewGeneratorUtil.constructRoundedText(tagName);
        ViewStyleUtil.addClassStyles(tagLabel, "white");
        return tagLabel;
    }

    /**
     * Displays a list of tags into the globalTagPanelView
     */
    public void displayGlobalTags(Collection<Tag> globalTags) {
        this.showGlobalTagViewPanel();
        tagFlowPane.getChildren().clear();
        for (Tag tag : globalTags){
            appendTag(tag);
        }
    }

    /* Ui Methods */
    public void hideGlobalTagViewPanel() {
        FxViewUtil.setCollapsed(globalTagViewPanel, true);
    }

    private void showGlobalTagViewPanel() {
        FxViewUtil.setCollapsed(globalTagViewPanel, false);
    }

    /* Override Methods */
    @Override
    public void setNode(Node node) {
        this.globalTagViewPanel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
