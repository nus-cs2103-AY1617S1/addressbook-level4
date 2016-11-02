package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.activity.ReadOnlyActivity;

/**
 * Panel containing the list of activities that are set as High Priority.
 * 
 * @author A0125284H
 */
public class OverdueListPanel extends ListPanel {
	/**
	 * Panel containing the list of persons.
	 */
	private final Logger logger = LogsCenter.getLogger(ActivityListPanel.class);
	private static final String FXML = "PersonListPanel.fxml";
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
}
