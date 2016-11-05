package seedu.lifekeeper.ui;

import javafx.fxml.FXML;
import seedu.lifekeeper.model.activity.event.ReadOnlyEvent;
import seedu.lifekeeper.model.activity.task.ReadOnlyTask;

public class OverdueTaskCard extends DashboardCard{

	 private static final String FXML = "overdueTaskCard.fxml";
 
		@FXML
		public void initialize() {

			name.setText(activity.getName().fullName);
<<<<<<< HEAD:src/main/java/seedu/lifekeeper/ui/OverdueTaskCard.java
			datetime.setText(((ReadOnlyTask) activity).getDueDate().forDisplay());
=======
			datetime.setText(((ReadOnlyTask) activity).getDueDate().forDashboardDisplay());
>>>>>>> 937da6a... Added DueDate output for OverdueTaskCard:src/main/java/seedu/address/ui/OverdueTaskCard.java

		}
	 
	    public String getFxmlPath() {
	        return FXML;
	    }
	
}
