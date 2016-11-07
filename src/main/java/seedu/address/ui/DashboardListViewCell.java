package seedu.address.ui;

import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.ui.ActivityListViewCell;

/**
 * Class that represents a cell containing either Overdue Tasks, Upcoming Tasks, or Upcoming Events.
 * 
 * Extension of ListCell<ReadOnlyActivity>
 */


public class DashboardListViewCell extends ActivityListViewCell {

	public DashboardListViewCell() {
	}
	
    @Override
    protected void updateItem(ReadOnlyActivity person, boolean empty) {
        super.updateItem(person, empty);

        if (empty || person == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(DashboardCard.load(person).getLayout());
        }
    }
	
}
