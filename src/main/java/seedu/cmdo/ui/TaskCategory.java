package seedu.cmdo.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import seedu.cmdo.commons.core.LogsCenter;
import seedu.cmdo.commons.events.model.ToDoListChangedEvent;
import seedu.cmdo.model.task.DueByDate;
import seedu.cmdo.model.task.ReadOnlyTask;
import javafx.scene.Node;

//@@author A0141006B

public class TaskCategory extends UiPart {
	private static final Logger logger = LogsCenter.getLogger(WelcomeMessage.class);
	private static final String FXML = "TaskCategory.fxml";	
	private static final String ICON1 = "/images/overdue.png";
	private static final String ICON2 = "/images/today.png";
	private static final String ICON3 = "/images/thisweek.png";
	private static final String ICON4 = "/images/thismonth.png";
	private static final String ICON5 = "/images/someday.png";
	private static final String ICON6 = "/images/totaltask.png";
	private static final Integer LABEL_WIDTH = 110;
	private static final Integer LABEL_HEIGHT = 30;
	private ObservableList<ReadOnlyTask> allTasksList;
	
	public TaskCategory(ObservableList<ReadOnlyTask> allTasksList) {
		this.allTasksList = allTasksList;
        registerAsAnEventHandler(this);
	}
	
	//======= FXML =======
	@FXML
	private GridPane taskCategoryPane;
	@FXML
	private ImageView overDueImg; 
	@FXML
	private ImageView todayImg; 
	@FXML
	private ImageView thisWeekImg;
	@FXML
	private ImageView thisMonthImg; 
	@FXML
	private ImageView somedayImg; 
	@FXML
	private ImageView totalTaskImg;
	@FXML
	private static Label overDue;
	@FXML
	private Label today;
	@FXML
	private Label thisWeek;
	@FXML
	private Label thisMonth;
	@FXML
	private Label someday;
	@FXML
	private Label totalTask;
	@FXML
	private Label totalDone;
	@FXML
	private Label overDueNo;
	@FXML
	private Label todayNo;
	@FXML
	private Label thisWeekNo;
	@FXML
	private Label thisMonthNo;
	@FXML
	private Label somedayNo;
	@FXML
	private Label totalTaskNo;
	@FXML
	private Label totalDoneNo;
	@FXML
	private Region emptySpace;
	
	@Override
	public void setNode(Node node) {
		taskCategoryPane = (GridPane) node;
	}

	@Override
	public String getFxmlPath() {
		return FXML;
	}

	public GridPane getTaskCategoryPane() {
		logger.info("Starting task category...");
		GridPane taskCategoryPane = new GridPane();
		//setGridPictures();
		setGridLabels();
		setGridNumbers();
		taskCategoryPane.getChildren().addAll(
				//overDueImg, todayImg, thisWeekImg,
				//thisMonthImg, somedayImg, totalTaskImg,
				overDue, today, thisWeek, thisMonth, someday, totalTask, totalDone, emptySpace,
				overDueNo, todayNo, thisWeekNo, thisMonthNo, somedayNo, totalTaskNo, totalDoneNo
				);		
		updateTasksOverviewPanel(allTasksList);
		logger.info("" + allTasksList.size());
		return taskCategoryPane;
	}
	
	public void setGridNumbers() {
		overDueNo = new Label();
		GridPane.setConstraints(overDueNo, 2, 0);	
		
		todayNo = new Label();
		GridPane.setConstraints(todayNo, 2, 1);		
		
		thisWeekNo = new Label();
		GridPane.setConstraints(thisWeekNo, 2, 2);		
		
		thisMonthNo = new Label();
		GridPane.setConstraints(thisMonthNo, 2, 3);		
		
		somedayNo = new Label();
		GridPane.setConstraints(somedayNo, 2, 4);		
		
		totalTaskNo = new Label();
		GridPane.setConstraints(totalTaskNo, 2, 5);		
		
		totalDoneNo = new Label();
		GridPane.setConstraints(totalDoneNo, 2, 7);
		
	}
	
	public void setGridPictures() {
		overDueImg = new ImageView(ICON1);
		GridPane.setConstraints(overDueImg, 0, 0);
		
		todayImg = new ImageView(ICON2);
		GridPane.setConstraints(todayImg, 0, 1);
		
		thisWeekImg = new ImageView(ICON3);
		GridPane.setConstraints(thisWeekImg, 0, 2);
		
		thisMonthImg = new ImageView(ICON4);
		GridPane.setConstraints(thisMonthImg, 0, 3);
		
		somedayImg = new ImageView(ICON5);
		GridPane.setConstraints(somedayImg, 0, 4);
		
		totalTaskImg = new ImageView(ICON6);
		GridPane.setConstraints(totalTaskImg, 0, 5);
	}
	
	public void setGridLabels() {
		overDue = new Label();
		overDue.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
		GridPane.setConstraints(overDue, 1, 0);
		overDue.setText("Overdue");	
		overDue.setFont(new Font("Gothic", 18));
		
		today = new Label();
		today.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
		GridPane.setConstraints(today, 1, 1);		
		today.setText("Today");
		today.setFont(new Font("Gothic", 18));
		
		thisWeek = new Label();
		thisWeek.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
		GridPane.setConstraints(thisWeek, 1, 2);		
		thisWeek.setText("This Week");
		thisWeek.setFont(new Font("Gothic", 18));
		
		thisMonth = new Label();
		thisMonth.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
		GridPane.setConstraints(thisMonth, 1, 3);		
		thisMonth.setText("This Month");	
		thisMonth.setFont(new Font("Gothic", 18));
		
		
		someday = new Label();
		someday.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
		GridPane.setConstraints(someday, 1, 4);		
		someday.setText("Someday");	
		someday.setFont(new Font("Gothic", 18));
		
		totalTask = new Label();
		totalTask.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
		GridPane.setConstraints(totalTask, 1, 5);		
		totalTask.setText("Total Tasks");
		totalTask.setFont(new Font("Gothic", 18));
		
		emptySpace = new Region();
		emptySpace.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
		GridPane.setConstraints(emptySpace, 1, 6);
		
		totalDone = new Label();
		totalDone.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
		GridPane.setConstraints(totalDone, 1, 7);		
		totalDone.setText("Total Done");
		totalDone.setFont(new Font("Gothic", 18));
		
		
	}
	
	//@@author A0139661Y
    public void updateTasksOverviewPanel(ObservableList<ReadOnlyTask> taskObservableList) {
        List<Integer> countMap = getTaskTimeStateCount(taskObservableList);
        
    	//Integer tasksInInbox = 0;
        Integer overdueNumber = 0;
        Integer todayNumber = 0;
        Integer thisWeekNumber = 0;
        Integer thisMonthNumber = 0;
        Integer somedayNumber = 0;
        Integer doneNumber = 0;
        Integer totalTasksNumber = 0;
        
        for (Integer i:countMap) {
        	switch (i) {
        	case 666:
        		doneNumber++;
        		break;
        	case -1:
        		overdueNumber++;
        		break;
        	case 0:
        		somedayNumber++;
        		break;
        	case 1:
        		todayNumber++;
        	case 2:
        		thisWeekNumber++;
        	case 3:
        		thisMonthNumber++;
        	}
        }
        overDueNo.setText("[" + Integer.toString(overdueNumber) + "]");
        todayNo.setText("[" + Integer.toString(todayNumber) + "]");
        thisWeekNo.setText("[" + Integer.toString(thisWeekNumber) + "]");
        thisMonthNo.setText("[" + Integer.toString(thisMonthNumber) + "]");
        somedayNo.setText("[" + Integer.toString(somedayNumber) + "]");
        totalTasksNumber = taskObservableList.size() - doneNumber;
        totalTaskNo.setText("[" + Integer.toString(totalTasksNumber) + "]");
        totalDoneNo.setText("[" + Integer.toString(doneNumber) + "]"); 
    }

     /**
     * Determines the time-state of the task in question
     * 
     * =======TIME-STATE TABLE=======
     * |    state   |   due         |
     * |------------|---------------|
     * |    404     |   error       |
     * |    666     |   done        |
     * |    -1      |   overdue     |
     * |    0       |   no due date |
     * |    1       |   today       |
     * |    2       |   this week   |
     * |    3       |   this month  |
     * ==============================
     * 
     * @param task (undone) in question
     * @return Integer based on the time-state
     * 
     * @@author A0139661Y
     */
    public Integer getTaskTimeState(ReadOnlyTask task) {
    	assert task != null;
    	
    	if (task.checkDone().value) {
    		return 666;
    	}
    	
        DueByDate dbd = task.getDueByDate();
        
        LocalDate nowDate = LocalDate.now();
        LocalDate weekDate = nowDate.plusWeeks(1);
        LocalDate monthDate = nowDate.plusMonths(1);
        LocalDate dueDate = dbd.start;
        
        // Floating tasks.
        if (dueDate.equals(LocalDate.MIN)) {
            return 0;
        }
        
        if (dueDate.isBefore(nowDate)) return -1;
        if (dueDate.isEqual(nowDate)) return 1;
        if (dueDate.isBefore(weekDate)) return 2;
        if (dueDate.isBefore(monthDate)) return 3;
        
        return 404;
    }
    
    //@@author A0139661Y
    public List<Integer> getTaskTimeStateCount(ObservableList<ReadOnlyTask> taskObservableList) {
        assert taskObservableList != null;
    	List<Integer> countMap = new ArrayList<Integer>();
        for (ReadOnlyTask t:taskObservableList) {
        	logger.info("Cycle 1");
            countMap.add(getTaskTimeState(t));        
        }
        return countMap;
    }
    
    //@@author A0141006B
    @Subscribe
    public void handleToDoListChangedEvent(ToDoListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        try {
        	updateTasksOverviewPanel(FXCollections.observableList(event.data.getTaskList()));
        } catch (Exception e) {
        	logger.severe("Failed to update task category panel.");
        }
    }
	
}
