package seedu.address.ui;

import javafx.scene.control.ListCell;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;
import seedu.lifekeeper.ui.ActivityCard;

public class ActivityListViewCell extends ListCell<ReadOnlyActivity>{

	public ActivityListViewCell() {
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
