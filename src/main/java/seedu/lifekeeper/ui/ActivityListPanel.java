package seedu.lifekeeper.ui;

import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.lifekeeper.commons.core.LogsCenter;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;

import java.util.logging.Logger;

/**
 * Panel containing the list of activities.
 */
public class ActivityListPanel extends ListPanel {
    private final Logger logger = LogsCenter.getLogger(ActivityListPanel.class);
    private static final String FXML = "ActivityListPanel.fxml";


    public ActivityListPanel() {
        super();
    }

    //Function specific to ActivityListPanel
    private void setEventHandlerForSelectionChangeEvent() {
        this.activityListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in activity list panel changed to : '" + newValue + "'");
                }
        });
    }

	public static ActivityListPanel load(Stage primaryStage, AnchorPane personListPlaceholder,
		                            	ObservableList<ReadOnlyActivity> activityList) {
		ActivityListPanel personListPanel = 
				UiPartLoader.loadUiPart(primaryStage, personListPlaceholder, new ActivityListPanel());
		personListPanel.configure(activityList);
		return personListPanel;
	}
    
    
	public String getFxmlPath() {
		return FXML;
	}
	
	@Override
	public void setPlaceholder(AnchorPane pane) {
		this.placeHolderPane = pane;
	}


}
