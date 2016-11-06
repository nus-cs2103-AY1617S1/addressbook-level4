package seedu.address.ui;

import javafx.fxml.FXML;
import seedu.address.model.activity.task.ReadOnlyTask;
import seedu.address.model.activity.event.ReadOnlyEvent;

public class UpcomingCard extends DashboardCard {

	 private static final String FXML = "UpcomingCard.fxml";
	
	public UpcomingCard() {
	}

	@FXML
	public void initialize() {
		String type = activity.getClass().getSimpleName();
		
		switch (type) {
			
			case "Task":
				name.setText(activity.getName().toString());
				datetime.setText(((ReadOnlyTask) activity).getDueDate().forDashboardDisplay());
				break;
				
			case "Event":
				name.setText(activity.getName().toString());
				datetime.setText(((ReadOnlyEvent) activity).getStartTime().forDisplay());
				break;
				
			default:
				break;
		}
	}
	
    public String getFxmlPath() {
        return FXML;
    }
	

}
