package seedu.lifekeeper.ui;

import javafx.scene.layout.AnchorPane;
import seedu.lifekeeper.commons.core.LogsCenter;
import java.util.logging.Logger;

/**
 * Panel containing the list of persons.
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
    
	public String getFxmlPath() {
		return FXML;
	}
	
	@Override
	public void setPlaceholder(AnchorPane pane) {
		this.placeHolderPane = pane;
	}


}
