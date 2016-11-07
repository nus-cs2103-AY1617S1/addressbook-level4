package seedu.lifekeeper.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;
import seedu.lifekeeper.model.activity.task.Task;


public abstract class DashboardCard extends UiPart {

	@FXML
	protected HBox displayCardPanel;
	@FXML
	protected Label name;
	@FXML
	protected Label datetime;

	protected ReadOnlyActivity activity;

	public DashboardCard() {

	}
	
	/**
	 * Function to load activity attributes onto a UI Dashboard card.
	 * @param event or tasks only.
	 * @return UI DashboardCard containing details of activity.
	 */

	public static DashboardCard load(ReadOnlyActivity activity) {
		
		DashboardCard card = new OverdueTaskCard();
		String type = activity.getClass().getSimpleName().toLowerCase();
		
		card.activity = activity;
		return UiPartLoader.loadUiPart(card);
	}

	@FXML
	public abstract void initialize(); 

	public HBox getLayout() {
		return displayCardPanel;
	}

	@Override
	public void setNode(Node node) {
		displayCardPanel = (HBox) node;
	}

	public abstract String getFxmlPath();
}
