package seedu.savvytasker.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.savvytasker.commons.core.LogsCenter;
import seedu.savvytasker.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.savvytasker.model.task.ReadOnlyTask;

//@@author A0138431L

/**
 * Panel containing the list overdue task.
 * @author A0138431L
 * 
 */
public class DailyPanel extends UiPart {

	private static String TODAY_TITLE = "Today";
	private static String TOMORROW_TITLE = "Tomorrow";
	private static String DAY_PATTERN = "EEEE";
	private static String DATE_PATTERN = "dd MMMM yy";
	private static String DAY_DATE_FORMAT = "%1$s, %2$s";

	private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
	private static final String FXML = "dailyList.fxml";
	private VBox panel;
	private AnchorPane placeHolderPane;

	@FXML 
	private Label dayHeader;

	@FXML
	private ListView<ReadOnlyTask> taskListView;

	public DailyPanel() {
		super();
	}

	@Override
	public void setNode(Node node) {
		panel = (VBox) node;
	}

	@Override
	public String getFxmlPath() {
		return FXML;
	}

	@Override
	public void setPlaceholder(AnchorPane pane) {
		this.placeHolderPane = pane;
	}

	public static DailyPanel load(Stage primaryStage, AnchorPane DailyListPlaceholder,
			ObservableList<ReadOnlyTask> taskList, int dayOfTheWeek, Date date) {
		DailyPanel dailyPanel =
				UiPartLoader.loadUiPart(primaryStage, DailyListPlaceholder, new DailyPanel());
		dailyPanel.configure(taskList, dayOfTheWeek, date);
		return dailyPanel;
	}

	private void configure(ObservableList<ReadOnlyTask> taskList, int dayOfTheWeek, Date date) {

		String dateHeader = generateHeader(dayOfTheWeek, date);
		setConnections(taskList, dateHeader);
		addToPlaceholder();
	}

	private void setConnections(ObservableList<ReadOnlyTask> taskList, String dateHeader) {
		dayHeader.setText(dateHeader);
		taskListView.setItems(taskList);
		taskListView.setCellFactory(listView -> new TaskListViewCell());
		setEventHandlerForSelectionChangeEvent();
	}

	private void addToPlaceholder() {
		SplitPane.setResizableWithParent(placeHolderPane, false);
		placeHolderPane.getChildren().add(panel);
	}

	private void setEventHandlerForSelectionChangeEvent() {
		taskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				logger.fine("Selection in daily task list panel changed to : '" + newValue + "'");
				raise(new TaskPanelSelectionChangedEvent(newValue));
			}
		});
	}

	public void scrollTo(int index) {
		Platform.runLater(() -> {
			taskListView.scrollTo(index);
			taskListView.getSelectionModel().clearAndSelect(index);
		});
	}

	private String generateHeader(int dayOfTheWeek, Date date) {

		SimpleDateFormat dayFormatter = new SimpleDateFormat(DAY_PATTERN);
		SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN);

		String day;

		switch (dayOfTheWeek) {

		case 0:

			day = TODAY_TITLE; 
			break;

		case 1:

			day = TOMORROW_TITLE;
			break;

		default:

			day = dayFormatter.format(date);
			break;

		}
		String header = String.format(DAY_DATE_FORMAT, day, dateFormatter.format(date));

		return header;
	}

	class TaskListViewCell extends ListCell<ReadOnlyTask> {

		public TaskListViewCell() {
		}

		@Override
		protected void updateItem(ReadOnlyTask task, boolean empty) {
			super.updateItem(task, empty);

			if (empty || task == null) {
				setGraphic(null);
				setText(null);
			} else {
				setGraphic(TaskCard.load(task, getIndex() + 1).getLayout());
			}
		}
	}

}
