package seedu.lifekeeper.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.lifekeeper.commons.core.LogsCenter;
import seedu.lifekeeper.logic.Logic;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;
import seedu.lifekeeper.model.activity.task.ReadOnlyTask;
import seedu.lifekeeper.ui.ListPanel;
import seedu.lifekeeper.ui.UiPartLoader;

/**
 * Panel containing the list of tasks that are already Overdue.
 */
//@@author A0125284H
public class OverdueTaskListPanel extends ListPanel {
	/**
	 * Panel containing the list of activities.
	 */
	private final Logger logger = LogsCenter.getLogger(OverdueTaskListPanel.class);
	private static final String FXML = "OverdueListPanel.fxml";

/*
	@FXML
	private static ListView<ReadOnlyActivity> overdueActivityListView;
*/
	public OverdueTaskListPanel() {
	        super();
	    }

	// Functions specific to OverdueListPanel ---------------------------------------------
    
    /**
     * 
     * @param primaryStage
     * @param activityListPlaceholder
     * @param taskList - the function calling should make sure that an ActivityList consisting of only tasks are passed.
     * @return
     */
	//@@author A0125284H
	public static OverdueTaskListPanel load(Stage primaryStage, AnchorPane activityListPlaceholder,
		                            	ObservableList<ReadOnlyActivity> taskList) {
		OverdueTaskListPanel overdueActivitiesListPanel = 
				UiPartLoader.loadUiPart(primaryStage, activityListPlaceholder, new OverdueTaskListPanel());
		overdueActivitiesListPanel.configure(taskList);
		return overdueActivitiesListPanel;
	}
	
	/**
	 * To configure listView in activityListView to accept overdue Tasks
	 * 
	 * @preconditions: activityList only contains (overdue) Tasks.
	 * 
	 */
	
	//@@author A0125284H
    protected void setConnections(ObservableList<ReadOnlyActivity> activityList) {
        activityListView.setItems(activityList);
        activityListView.setCellFactory(listView -> new DashboardListViewCell());
    }
	
	@Override
	public String getFxmlPath() {
		return FXML;
	}
	
	@Override
	public void setPlaceholder(AnchorPane pane) {
		this.placeHolderPane = pane;
	}
	
	public void refresh(Logic logic) {
	    Platform.runLater(new Runnable() {
            public void run() {
                activityListView.setItems(logic.getFilteredOverdueTaskList());
            }
        });
    }
}