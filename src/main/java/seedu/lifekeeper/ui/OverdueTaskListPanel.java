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

/**
 * Panel containing the list of activities that are set as High Priority.
 * 
 * @@author A0125284H
 */
public class OverdueTaskListPanel extends ListPanel {
	/**
	 * Panel containing the list of persons.
	 */
	private final Logger logger = LogsCenter.getLogger(ActivityListPanel.class);
	private static final String FXML = "OverdueTaskListPanel.fxml";
	private VBox panel;
	private AnchorPane placeHolderPane;

	@FXML
	private ListView<ReadOnlyActivity> personListView;

	public OverdueTaskListPanel() {
	        super();
	    }

	// Function specific to OverdueListPanel
	
	public static OverdueTaskListPanel load(Stage primaryStage, AnchorPane personListPlaceholder,
			ObservableList<ReadOnlyActivity> activityList) {
		OverdueTaskListPanel overdueListPanel = UiPartLoader.loadUiPart(primaryStage, personListPlaceholder,
				new OverdueTaskListPanel());
		overdueListPanel.configure(activityList);
		return overdueListPanel;
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
