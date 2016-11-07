package seedu.savvytasker.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.savvytasker.commons.core.LogsCenter;
import seedu.savvytasker.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.savvytasker.commons.util.FxViewUtil;
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
	private static String DATE_PATTERN = "dd MMM yy";
	private static String DAY_DATE_FORMAT = "%1$s, %2$s";

	private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
	private static final String FXML = "DailyList.fxml";
	private VBox panel;
	private AnchorPane placeHolderPane;


	@FXML 
	private TextField header;

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
		Date today = new Date();
		if(date == today) {
			placeHolderPane.setStyle("-fx-background-color:#FF0000");
			header.setStyle("-fx-text-fill:#FF0000");
		}
		setConnections(taskList, dateHeader);
		addToPlaceholder();
		
	}

	private void setConnections(ObservableList<ReadOnlyTask> taskList, String dateHeader) {
		header.clear();
		header.setText(dateHeader);
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

		Date today = new Date();
		Date tomorrow = new Date();
		tomorrow = addDay(1, tomorrow);
		
		String day;

		if(DateUtils.isSameDay(date, today)) {

			day = TODAY_TITLE; 
		
		} else if (DateUtils.isSameDay(date, tomorrow)) {

			day = TOMORROW_TITLE;
		
		} else {

			day = dayFormatter.format(date);

		}
		String header = String.format(DAY_DATE_FORMAT, day, dateFormatter.format(date));

		return header;
	}
	
	private Date addDay(int i, Date date) {
		
        //convert date object to calendar object and add 1 days
        Calendar calendarExpectedDate = Calendar.getInstance();
        calendarExpectedDate.setTime(date);
        
        calendarExpectedDate.add(Calendar.DATE, i);
			
        //convert calendar object back to date object
        date = calendarExpectedDate.getTime();
			
        return date;
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
				setGraphic(TaskCard.load(task, 0, false).getLayout());
			}
		}
	}

}
