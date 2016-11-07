package seedu.lifekeeper.ui;

import javafx.scene.control.ListCell;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;
import seedu.lifekeeper.ui.ActivityCard;

public class ActivityListViewCell extends ListCell<ReadOnlyActivity>{

	public ActivityListViewCell() {
	}
	
    @Override
    protected void updateItem(ReadOnlyActivity activity, boolean empty) {
        super.updateItem(activity, empty);

        if (empty || activity == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(ActivityCard.load(activity, getIndex() + 1).getLayout());
        }
    }

}
