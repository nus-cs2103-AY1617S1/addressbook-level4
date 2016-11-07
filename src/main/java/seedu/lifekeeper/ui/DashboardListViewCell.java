package seedu.lifekeeper.ui;

import seedu.lifekeeper.model.activity.ReadOnlyActivity;
import seedu.lifekeeper.ui.DashboardCard;
import seedu.lifekeeper.ui.ActivityListViewCell;
//@@author A0125097A
/**
 * Class that represents a cell containing DashboardCard.
 * 
 * Extension of ListCell<ReadOnlyActivity>
 */
public class DashboardListViewCell extends ActivityListViewCell {

	public DashboardListViewCell() {
	}
	
    @Override
    protected void updateItem(ReadOnlyActivity activity, boolean empty) {
        super.updateItem(activity, empty);

        if (empty || activity == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(DashboardCard.load(activity).getLayout());
        }
    }
	
}
