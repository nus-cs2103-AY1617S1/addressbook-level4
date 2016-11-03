package tars.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tars.commons.core.LogsCenter;
import tars.commons.events.model.TarsChangedEvent;
import tars.commons.events.storage.TarsStorageDirectoryChangedEvent;
import tars.commons.util.DateTimeUtil;
import tars.commons.util.StringUtil;
import tars.model.task.ReadOnlyTask;

/**
 * UI Controller for this week panel
 * 
 * @@author A0121533W
 */
public class ThisWeekPanel extends UiPart {
    private static final String TasksListEllipsis = "\n...\n";
    private static final Logger logger =
            LogsCenter.getLogger(ThisWeekPanel.class);
    private static final String FXML = "ThisWeekPanel.fxml";
    private static final String THISWEEK_PANEL_STYLE_SHEET = "thisWeek-panel";
    private static final String STATUS_UNDONE = "Undone";
    private static final DateFormat df = new SimpleDateFormat("E d, MMM");

    private static List<ReadOnlyTask> list;
    private static List<ReadOnlyTask> upcomingTasks =
            new ArrayList<ReadOnlyTask>();
    private static List<ReadOnlyTask> overduedTasks =
            new ArrayList<ReadOnlyTask>();

    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private Label date;
    @FXML
    private Label numUpcoming;
    @FXML
    private Label numOverdue;
    @FXML
    private Label overduedTasksList;
    @FXML
    private Label upcomingTasksList;

    public static ThisWeekPanel load(Stage primaryStage,
            AnchorPane thisWeekPanelPlaceHolder, List<ReadOnlyTask> taskList) {
        ThisWeekPanel thisWeekPanel = UiPartLoader.loadUiPart(primaryStage,
                thisWeekPanelPlaceHolder, new ThisWeekPanel());
        list = taskList;
        thisWeekPanel.configure();
        return thisWeekPanel;
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
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void configure() {
        panel.getStyleClass().add(THISWEEK_PANEL_STYLE_SHEET);
        setDate();
        handleUpcomingTasks();
        handleOverdueTasks();
        addToPlaceholder();
        registerAsAnEventHandler(this);
    }

    private void setDate() {
        Date today = new Date();
        date.setText(df.format(today));
    }
    
    private void handleUpcomingTasks() {
        int count = 0;
        for (ReadOnlyTask t : list) {
            if (DateTimeUtil.isWithinWeek(t.getDateTime().getEndDate())
                    && t.getStatus().toString().equals(STATUS_UNDONE)) {
                count++;
                upcomingTasks.add(t);
            }
        }
        numUpcoming.setText(String.valueOf(count));
        if (count == 0) {
            upcomingTasksList.setText(StringUtil.EMPTY_STRING);
        } else {
            setThisWeekPanelTaskList(count, upcomingTasks, upcomingTasksList);
        }
    }

    private void handleOverdueTasks() {
        int count = 0;
        for (ReadOnlyTask t : list) {
            if (DateTimeUtil.isOverDue(t.getDateTime().getEndDate())
                    && t.getStatus().toString().equals(STATUS_UNDONE)) {
                count++;
                overduedTasks.add(t);
            }
        }
        numOverdue.setText(String.valueOf(count));
        if (count == 0) {
            overduedTasksList.setText(StringUtil.EMPTY_STRING);
        } else {
            setThisWeekPanelTaskList(count, overduedTasks, overduedTasksList);
        }
    }
    
    /**
     * Set text for tasksLists to display top three tasks
     * 
     */
    private void setThisWeekPanelTaskList(int count, List<ReadOnlyTask> tasksList, Label taskListLabel) {
            List<ReadOnlyTask> topFiveTasks = tasksList.subList(
                    StringUtil.START_INDEX, Math.min(tasksList.size(), 3));
            String list = Formatter
                    .formatThisWeekPanelTasksList(topFiveTasks);
            if (tasksList.size() > 3) {
                list = list + TasksListEllipsis;
            }
            taskListLabel.setText(list);
    }
    
    /**
     * Updates panel with latest data
     */
    private void updateThisWeekPanel() {
        upcomingTasks.clear();
        handleUpcomingTasks();
        overduedTasks.clear();
        handleOverdueTasks();
    }

    @Subscribe
    private void handleTarsChangedEvent(TarsChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Update this week panel"));
        updateThisWeekPanel();
    }
    
    @Subscribe
    private void handleTarsStorageChangeDirectoryEvent(
            TarsStorageDirectoryChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Update this week panel"));
        updateThisWeekPanel();
    }
}
