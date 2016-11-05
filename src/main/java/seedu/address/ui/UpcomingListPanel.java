package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.activity.ReadOnlyActivity;

/**
 * Panel containing the list of tasks and events that are upcoming.
 */

//@@ Author A0125284H
public class UpcomingListPanel extends ListPanel {
	
	private final Logger logger = LogsCenter.getLogger(UpcomingListPanel.class);
	private static final String FXML = "UpcomingListPanel.fxml";
	
	public UpcomingListPanel() {
		super();
	}

	// Functions specific to UpcomingListPanel ---------------------------------------------
	
	@Override
	public String getFxmlPath() {
		return FXML;
	}
	
    /**
     * 
     * @param primaryStage
     * @param personListPlaceholder
     * @param upcomingActivitiesList - Logic will ensure only Upcoming Events, and Tasks with Upcoming Duedate is passed in.
     * @return
     */

	public static UpcomingListPanel load(Stage primaryStage, AnchorPane personListPlaceholder,
		                            	ObservableList<ReadOnlyActivity> upcomingActivitiesList) {
		UpcomingListPanel upcomingActivitiesListPanel = 
				UiPartLoader.loadUiPart(primaryStage, personListPlaceholder, new UpcomingListPanel());
		upcomingActivitiesListPanel.configure(upcomingActivitiesList);
		return upcomingActivitiesListPanel;
	}

}
