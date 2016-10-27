package tars.ui;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import tars.commons.util.FxViewUtil;

/**
 * Controller for a help page
 */
public class HelpPanel extends UiPart {
    private static final String FXML = "HelpPanel.fxml";
    private static final String USERGUIDE_URL = "/html/UserGuide.md.html";
    
    private VBox panel;
    private AnchorPane placeHolderPane;
    
    public static HelpPanel load(Stage primaryStage, AnchorPane helpPanelPlaceHolder) {
        HelpPanel helpPanel = UiPartLoader.loadUiPart(primaryStage, helpPanelPlaceHolder, new HelpPanel());
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

    private void configure(){
        WebView browser = new WebView();
        browser.getEngine().load(HelpPanel.class.getResource(USERGUIDE_URL).toExternalForm());
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        panel.getChildren().add(browser);
        addToPlaceholder();
    }
}
