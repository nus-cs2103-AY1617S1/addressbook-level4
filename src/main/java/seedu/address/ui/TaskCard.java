package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;
import seedu.address.model.task.TaskDateTimeFormatter;
import seedu.address.model.task.TaskType;

/**
 * A ui component that displays the details of a task
 */
//@@author A0142184L
public class TaskCard extends UiPart {

    private static final String FXML = "TaskCard.fxml";

    @FXML
    private VBox cardPane;
    @FXML
    private Label taskName;
    @FXML
    private Label id;
    @FXML
    private Label taskType;
    @FXML
    private Label taskStatus;
    @FXML
    private Label startDateAndTime;
    @FXML
    private Label connector;
    @FXML
    private Label endDateAndTime;
    @FXML
    private Label tagHeader;
    @FXML
    private Label tags;

    private ReadOnlyTask task;
    
    private int displayedIndex;

    public TaskCard() {
    	
    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex) {
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        setTaskName();
        setId();
        setTaskType();
        setTaskDateTime();
        setTaskStatus();
        setTags();
    }

	private void setTaskName() {
        taskName.setText(task.getName().value);
	}

	private void setTaskType() {
	    taskType.setText(task.getTaskType().toString());
		if (task.getTaskType().value.equals(TaskType.Type.EVENT)) {
			setEventTaskTypeStyle();
		} else if (task.getTaskType().value.equals(TaskType.Type.DEADLINE)) {
			setDeadlineTaskTypeStyle();
		} else {
			setSomedayTaskTypeStyle();
		}
	}

	private void setEventTaskTypeStyle() {
		taskType.setStyle("-fx-background-color: #38d0ff");
	}
	
	private void setDeadlineTaskTypeStyle() {
		taskType.setStyle("-fx-background-color: #ffd13d");
	}
	
	private void setSomedayTaskTypeStyle() {
		taskType.setStyle("-fx-background-color: #63ff75");		
	}
	
	private void setId() {
		id.setText(displayedIndex + ". ");
	}

	private void setTaskDateTime() {
		if (task.getTaskType().value.equals(TaskType.Type.EVENT)) {
		    setEventTaskDateTime();
		} else if (task.getTaskType().value.equals(TaskType.Type.DEADLINE)) {
			setDeadlineTaskDateTime();
		} else {
			setSomedayTaskDateTime();
		}
	}

	private void setEventTaskDateTime() {
		if (task.getStartDate().get().toLocalDate().equals(task.getEndDate().get().toLocalDate())) {
<<<<<<< HEAD
		    startDateAndTime.setText(TaskDateTimeFormatter.formatToShowDateAndTime(task.getStartDate().get()));
		    connector.setText(" � ");
		    endDateAndTime.setText(TaskDateTimeFormatter.formatToShowTimeOnly(task.getEndDate().get().toLocalTime()));
		} else {
		    startDateAndTime.setText(TaskDateTimeFormatter.formatToShowDateAndTime(task.getStartDate().get()));
		    connector.setText(" � ");
		    endDateAndTime.setText(TaskDateTimeFormatter.formatToShowDateAndTime(task.getEndDate().get()));
=======
		    setStartDateTime(TaskDateTimeFormatter.formatToShowDateAndTime(task.getStartDate().get()));
		    setConnector(" - ");
		    setEndDateTime(TaskDateTimeFormatter.formatToShowTimeOnly(task.getEndDate().get().toLocalTime()));
		} else {
		    setStartDateTime(TaskDateTimeFormatter.formatToShowDateAndTime(task.getStartDate().get()));
		    setConnector(" - ");
		    setEndDateTime(TaskDateTimeFormatter.formatToShowDateAndTime(task.getEndDate().get()));
>>>>>>> master
		}
	}
	
	private void setDeadlineTaskDateTime() {
		setStartDateTime("");
	    setConnector("");
	    setEndDateTime(TaskDateTimeFormatter.formatToShowDateAndTime(task.getEndDate().get()));		
	}
	
	private void setSomedayTaskDateTime() {
		setStartDateTime("");
	    setConnector("");
	    setEndDateTime("");
	}
	
	private void setStartDateTime(String startDateTime) {
		startDateAndTime.setText(startDateTime);
	}
	
	private void setEndDateTime(String endDateTime) {
		endDateAndTime.setText(endDateTime);
	}
	
	private void setConnector(String connector) {
		this.connector.setText(connector);
	}

    private void setTaskStatus() {
		if (task.getStatus().isDone()) {
			setTaskStatusText();
			setDoneTaskStatusStyle();
		} else if (task.getStatus().isOverdue()) {
			setTaskStatusText();
			setOverdueTaskStatusStyle();
		}
	}
    
	private void setTaskStatusText() {
		taskStatus.setText(task.getStatus().value.toString().toUpperCase());
	}

	private void setDoneTaskStatusStyle() {
		taskStatus.setStyle("-fx-font-size: 14");
		taskStatus.setStyle("-fx-text-fill: green");
	}
	
	private void setOverdueTaskStatusStyle() {
		taskStatus.setStyle("-fx-font-size: 14");
		taskStatus.setStyle("-fx-text-fill: red");
	}
    
	private void setTags() {
		tags.setText(task.tagsString());
		if (task.getTaskType().value.equals(TaskType.Type.SOMEDAY)) {
		    adjustTagLayoutForSomedayTask();
		}
	}

	private void adjustTagLayoutForSomedayTask() {
	    tagHeader.setTranslateY(-10.0);
	    tags.setTranslateY(-10.0);
	}

	public VBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (VBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}