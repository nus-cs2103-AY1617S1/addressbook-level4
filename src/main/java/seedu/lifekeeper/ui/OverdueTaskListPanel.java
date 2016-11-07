package seedu.lifekeeper.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.lifekeeper.commons.core.LogsCenter;
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
	 * Panel containing the list of persons.
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
     * @param personListPlaceholder
     * @param taskList - the function calling should make sure that an ActivityList consisting of only tasks are passed.
     * @return
     */

	public static OverdueTaskListPanel load(Stage primaryStage, AnchorPane personListPlaceholder,
		                            	ObservableList<ReadOnlyActivity> taskList) {
		OverdueTaskListPanel overdueActivitiesListPanel = 
				UiPartLoader.loadUiPart(primaryStage, personListPlaceholder, new OverdueTaskListPanel());
		overdueActivitiesListPanel.configure(taskList);
		return overdueActivitiesListPanel;
	}
	
	/**
	 * To configure listView in activityListView to accept overdue Tasks
	 * 
	 * @preconditions: personList only contains (overdue) Tasks.
	 * 
	 */
	
	//@@author A0125284H
    protected void setConnections(ObservableList<ReadOnlyActivity> personList) {
        activityListView.setItems(personList);
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
}