package seedu.lifekeeper.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import seedu.lifekeeper.commons.core.LogsCenter;
import seedu.lifekeeper.logic.Logic;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;

/**
 * Panel containing the list of activities that are set as High Priority.
 * 
 * @@author A0125284H
 */
public class OverdueListPanel extends ListPanel {
	/**
	 * Panel containing the list of persons.
	 */
	private final Logger logger = LogsCenter.getLogger(ActivityListPanel.class);
	private static final String FXML = "ActivityListPanel.fxml";
	private VBox panel;
	private AnchorPane placeHolderPane;

	@FXML
	private ListView<ReadOnlyActivity> personListView;

	public OverdueListPanel() {
	        super();
	    }

	// Function specific to OverdueListPanel
	@Override
	public String getFxmlPath() {
		return FXML;
	}
	
	public void refresh(AnchorPane pane, Logic logic) {
	    OverdueTaskListPanel.load(primaryStage, pane, logic.getFilteredOverdueTaskList());
	}
}
