package tars.ui;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import tars.commons.util.FxViewUtil;

/**
 * UI Controller for a help page
 * 
 * @@author A0121533W
 */
public class HelpPanel extends UiPart {
    private static final String FXML = "HelpPanel.fxml";
    private static final String USERGUIDE_URL = "/html/UserGuide.md.html";

    private VBox panel;
    private AnchorPane placeHolderPane;
    private WebView browser;

    public static HelpPanel load(Stage primaryStage,
            AnchorPane helpPanelPlaceHolder) {
        HelpPanel helpPanel = UiPartLoader.loadUiPart(primaryStage,
                helpPanelPlaceHolder, new HelpPanel());
        helpPanel.configure();
        return helpPanel;
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

    private void addToPlaceholder() {
        placeHolderPane.getChildren().add(panel);
    }

    private void configure() {
        browser = new WebView();
        browser.getEngine().load(configureURL(UserGuide.DEFAULT));
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        panel.getChildren().add(browser);
        addToPlaceholder();
    }

    /**
     * @@author A0140022H
     */
    private String configureURL(String args) {
        String url =
                HelpPanel.class.getResource(USERGUIDE_URL).toExternalForm();

        switch (args) {
            case UserGuide.ADD:
                url = url.concat(UserGuide.ADD_ID);
                break;
            case UserGuide.CD:
                url = url.concat(UserGuide.CD_ID);
                break;
            case UserGuide.CLEAR:
                url = url.concat(UserGuide.CLEAR_ID);
                break;
            case UserGuide.CONFIRM:
                url = url.concat(UserGuide.CONFIRM_ID);
                break;
            case UserGuide.DELETE:
                url = url.concat(UserGuide.DELETE_ID);
                break;
            case UserGuide.DONE:
                url = url.concat(UserGuide.DONE_ID);
                break;
            case UserGuide.EDIT:
                url = url.concat(UserGuide.EDIT_ID);
                break;
            case UserGuide.EXIT:
                url = url.concat(UserGuide.EXIT_ID);
                break;
            case UserGuide.FIND:
                url = url.concat(UserGuide.FIND_ID);
                break;
            case UserGuide.FREE:
                url = url.concat(UserGuide.FREE_ID);
                break;
            case UserGuide.HELP:
                url = url.concat(UserGuide.HELP_ID);
                break;
            case UserGuide.LIST:
                url = url.concat(UserGuide.LIST_ID);
                break;
            case UserGuide.REDO:
                url = url.concat(UserGuide.REDO_ID);
                break;
            case UserGuide.RSV:
                url = url.concat(UserGuide.RSV_ID);
                break;
            case UserGuide.RSV_DELETE:
                url = url.concat(UserGuide.RSV_DELETE_ID);
                break;
            case UserGuide.TAG_EDIT:
                url = url.concat(UserGuide.TAG_EDIT_ID);
                break;
            case UserGuide.TAG_DELETE:
                url = url.concat(UserGuide.TAG_DELETE_ID);
                break;
            case UserGuide.TAG_LIST:
                url = url.concat(UserGuide.TAG_LIST_ID);
                break;
            case UserGuide.UNDONE:
                url = url.concat(UserGuide.UNDONE_ID);
                break;
            case UserGuide.UNDO:
                url = url.concat(UserGuide.UNDO_ID);
                break;
            case UserGuide.SUMMARY:
                url = url.concat(UserGuide.SUMMARY_ID);
                break;
            default:
                break;
        }

        return url;
    }

    /**
     * @@author A0140022H
     */
    public void loadUserGuide(String args) {
        browser.getEngine().load(configureURL(args));
    }
}
