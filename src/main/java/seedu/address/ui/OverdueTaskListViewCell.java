package seedu.address.ui;

import seedu.address.model.activity.ReadOnlyActivity;

/**
 * Class that represents a cell containing Overdue Tasks.
 * 
 * Extension of ListCell<ReadOnlyActivity>
 */


public class OverdueTaskListViewCell extends ActivityListViewCell {

	public OverdueTaskListViewCell() {
	}
	
    @Override
    protected void updateItem(ReadOnlyActivity person, boolean empty) {
        super.updateItem(person, empty);

        if (empty || person == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(ActivityCard.load(person, getIndex() + 1).getLayout());
        }
    }
	
}
