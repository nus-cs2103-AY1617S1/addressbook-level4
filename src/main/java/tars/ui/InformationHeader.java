package tars.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tars.commons.util.FxViewUtil;

/**
 * A Ui for the information header of the application
 */
public class InformationHeader extends UiPart {
    private static final String INFO_HEADER_STYLE_SHEET = "info-header";
    private static final String FXML = "InformationHeader.fxml";
        
    private AnchorPane placeHolder;
    private AnchorPane mainPane;

    @FXML
    private HBox header;
    
    public static InformationHeader load(Stage primaryStage, AnchorPane placeHolder) {
        InformationHeader infoHeader = UiPartLoader.loadUiPart(primaryStage, placeHolder, new InformationHeader());
        infoHeader.configure();
        return infoHeader;
    }

    public void configure() {
        header.getStyleClass().add(INFO_HEADER_STYLE_SHEET);
        
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
