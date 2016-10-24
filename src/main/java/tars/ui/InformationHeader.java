package tars.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tars.commons.core.LogsCenter;
import tars.commons.events.model.TarsChangedEvent;
import tars.commons.util.DateTimeUtil;
import tars.commons.util.FxViewUtil;
import tars.model.task.ReadOnlyTask;

/**
 * A Ui for the information header of the application
 */
public class InformationHeader extends UiPart {
    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);
    public static final String INFORMATION_HEADER_ID = "informationHeader";
    private static final String INFO_HEADER_STYLE_SHEET = "info-header";
    private static final String FXML = "InformationHeader.fxml";
    private static final DateFormat df = new SimpleDateFormat("E d, MMM");
    private static final String STATUS_UNDONE = "Undone";
    
    private static ObservableList<ReadOnlyTask> list;

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
    @FXML
    private Tooltip upcomingTip;
    @FXML
    private Tooltip overdueTip;


    public static InformationHeader load(Stage primaryStage, AnchorPane placeHolder
            , ObservableList<ReadOnlyTask> taskList) {
        InformationHeader infoHeader = UiPartLoader.loadUiPart(primaryStage, placeHolder, new InformationHeader());
        list = taskList;
        infoHeader.configure();
        return infoHeader;
    }

    public void configure() {
        header.getStyleClass().add(INFO_HEADER_STYLE_SHEET);
        setDate();
        setUpcoming();
        setOverdue();

        FxViewUtil.applyAnchorBoundaryParameters(header, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(mainPane);
        registerAsAnEventHandler(this);
    }

    private void setDate() {
        Date today = new Date();
        date.setText(df.format(today));        
    }
    
    private void setUpcoming() {
        int count = 0;
        for (ReadOnlyTask t : list) {
            if (DateTimeUtil.isWithinWeek(t.getDateTime().getEndDate())
                    && t.getStatus().toString().equals(STATUS_UNDONE)) {
                count++;
            }
        }
        numUpcoming.setText(String.valueOf(count));
    }
    
    private void setOverdue() {
        int count = 0;
        for (ReadOnlyTask t : list) {
            if (DateTimeUtil.isOverDue(t.getDateTime().getEndDate())
                    && t.getStatus().toString().equals(STATUS_UNDONE)) {
                count++;
            }
        }
        numOverdue.setText(String.valueOf(count));
    }
    
    @Subscribe
    public void handleTarsChangedEvent(TarsChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Update information header"));
        setUpcoming();
        setOverdue();
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
