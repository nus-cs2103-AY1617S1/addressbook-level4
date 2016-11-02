package seedu.address.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.commons.core.LogsCenter;

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
                logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                raise(new PersonPanelSelectionChangedEvent(newValue));
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
