package seedu.lifekeeper.ui;

import seedu.lifekeeper.model.activity.ReadOnlyActivity;
import seedu.lifekeeper.ui.DashboardCard;
import seedu.lifekeeper.ui.ActivityListViewCell;

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
