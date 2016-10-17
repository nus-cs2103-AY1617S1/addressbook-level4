package tars.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tars.commons.util.FxViewUtil;

/**
 * A ui for the information panel that is at the header of the application
 */
public class InformationHeader extends UiPart {
    public static final String INFORMATION_HEADER_ID = "informationHeader";
    private static final String INFO_HEADER_STYLE_SHEET = "info-header";
    private static final String FXML = "InformationHeader.fxml";
    private static DateFormat df = new SimpleDateFormat("E d, MMM");

    private AnchorPane placeHolder;

    private AnchorPane mainPane;

    @FXML
    private HBox header;
    @FXML
    private Label date;
    @FXML
    private Label numUpcoming;
    @FXML
    private Label numOverdue;


    public static InformationHeader load(Stage primaryStage, AnchorPane placeHolder) {
        InformationHeader infoHeader = UiPartLoader.loadUiPart(primaryStage, placeHolder, new InformationHeader());
        infoHeader.configure();
        return infoHeader;
    }

    public void configure() {
        header.getStyleClass().add(INFO_HEADER_STYLE_SHEET);
        Date today = new Date();
        date.setText(df.format(today));
        numUpcoming.setText("0");
        numOverdue.setText("0");

        FxViewUtil.applyAnchorBoundaryParameters(header, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(mainPane);
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
