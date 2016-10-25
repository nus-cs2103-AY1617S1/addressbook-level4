package tars.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tars.commons.core.LogsCenter;
import tars.commons.events.model.TarsChangedEvent;
import tars.commons.util.DateTimeUtil;
import tars.model.task.ReadOnlyTask;

/**
 * Controller for overview panel
 */
public class OverviewPanel extends UiPart {
    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);
    private static final String FXML = "OverviewPanel.fxml";
    private static final String OVERVIEW_PANEL_STYLE_SHEET = "overview-panel";
    private static final String STATUS_UNDONE = "Undone";

    private static ObservableList<ReadOnlyTask> list;
    private AnchorPane placeHolderPane;

    @FXML
    private VBox panel;
    @FXML
    private Label numUpcoming;
    @FXML
    private Label numOverdue;

    public static OverviewPanel load(Stage primaryStage, AnchorPane overviewPanelPlaceHolder
            , ObservableList<ReadOnlyTask> taskList) {
        OverviewPanel overviewPanel = UiPartLoader.loadUiPart(primaryStage, overviewPanelPlaceHolder, new OverviewPanel());
        list = taskList;
        overviewPanel.configure();
        return overviewPanel;
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
        panel.getStyleClass().add(OVERVIEW_PANEL_STYLE_SHEET);
        setUpcoming();
        setOverdue();
        addToPlaceholder();
        registerAsAnEventHandler(this);
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
}
