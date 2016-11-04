package tars.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tars.commons.util.FxViewUtil;

/**
 * UI Controller for the header of the application
 * 
 * @@author A0121533W
 */
public class Header extends UiPart {
    private static final String HEADER_STYLE_SHEET = "header";
    private static final String FXML = "Header.fxml";

    private AnchorPane placeHolder;
    private AnchorPane mainPane;

    @FXML
    private HBox header;

    public static Header load(Stage primaryStage, AnchorPane placeHolder) {
        Header infoHeader = UiPartLoader.loadUiPart(primaryStage, placeHolder,
                new Header());
        infoHeader.configure();
        return infoHeader;
    }

    public void configure() {
        header.getStyleClass().add(HEADER_STYLE_SHEET);
        FxViewUtil.applyAnchorBoundaryParameters(header, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(mainPane);
        registerAsAnEventHandler(this);
    }

    @Override
    public void setNode(Node node) {
        mainPane = (AnchorPane) node;
    }

    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeHolder = placeholder;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

}
