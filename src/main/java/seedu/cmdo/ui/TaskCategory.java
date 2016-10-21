package seedu.cmdo.ui;

import java.awt.Font;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import seedu.cmdo.commons.core.LogsCenter;
import seedu.cmdo.model.task.DueByDate;
import seedu.cmdo.model.task.ReadOnlyTask;
import seedu.cmdo.model.task.Task;
import javafx.scene.Node;
//import javafx.scene.layout.GridPane;


public class TaskCategory extends UiPart {
	private static final Logger logger = LogsCenter.getLogger(WelcomeMessage.class);
	private static final String FXML = "TaskCategory.fxml";	
	private static final String ICON1 = "/images/overdue.png";
	private static final String ICON2 = "/images/today.png";
	private static final String ICON3 = "/images/thisweek.png";
	private static final String ICON4 = "/images/thismonth.png";
	private static final String ICON5 = "/images/someday.png";
	private static final String ICON6 = "/images/totaltask.png";
	
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
	
	//@FXML
	//private Label test;
	
	//@FXML
	//private AnchorPane placeHolder;

	@Override
	public void setNode(Node node) {
		taskCategoryPane = (GridPane) node;
	}

	@Override
	public String getFxmlPath() {
		return FXML;
	}
	/*
	public AnchorPane getTaskCategoryPane() {
		placeHolder = new AnchorPane();
		Label test = new Label();
		test.setText("Loser look here please!");
		placeHolder.getChildren().add(test);
		return placeHolder;	
	}
	*/
	public GridPane getTaskCategoryPane() {
		logger.info("Starting task category...");
		GridPane taskCategoryPane = new GridPane();
		setGridPictures();
		setGridLabels();
		taskCategoryPane.getChildren().addAll(overDueImg, todayImg, thisWeekImg,
				thisMonthImg, somedayImg, totalTaskImg,
				overDue, today, thisWeek, thisMonth, someday, totalTask
				);		
		
		return taskCategoryPane;
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
		GridPane.setConstraints(overDue, 1, 0);
		overDue.setText("Overdue");
		//overDue.setTextFill(Color.RED);		
		
		today = new Label();
		GridPane.setConstraints(today, 1, 1);		
		today.setText("Today");	
		//today.setTextFill(Color.DARKORANGE);	
		
		thisWeek = new Label();
		GridPane.setConstraints(thisWeek, 1, 2);		
		thisWeek.setText("This Week");	
		//thisWeek.setTextFill(Color.GOLD);	
		
		thisMonth = new Label();
		GridPane.setConstraints(thisMonth, 1, 3);		
		thisMonth.setText("This Month");	
		//thisMonth.setTextFill(Color.GREEN);	
		
		someday = new Label();
		GridPane.setConstraints(someday, 1, 4);		
		someday.setText("Someday");	
		//someday.setTextFill(Color.ROYALBLUE);	
		
		totalTask = new Label();
		GridPane.setConstraints(totalTask, 1, 5);		
		totalTask.setText("Total Tasks");
		//totalTask.setTextFill(Color.VIOLET);	
	}
	
	//@@author A0139661Y
    public void updateTasksOverviewPanel(ObservableList<Task> taskObservableList) {
        List<Integer> countMap = getTaskTimeStateCount(taskObservableList);
        
    	//Integer tasksInInbox = 0;
        Integer overdueNumber = 0;
        Integer todayNumber = 0;
        Integer thisWeekNumber = 0;
        Integer thisMonthNumber = 0;
        Integer somedayNumber = 0;
        Integer totalTasksNumber = taskObservableList.size();
        
        for (Integer i:countMap) {
        	switch (i) {
        	case -1:
        		overdueNumber++;
        		break;
        	case 0:
        		somedayNumber++;
        		break;
        	case 3:
        		todayNumber++;
        	case 2:
        		thisWeekNumber++;
        	case 1:
        		thisMonthNumber++;
        	}
        }
     }

     /**
     * Determines the time-state of the task in question
     * 
     * =======TIME-STATE TABLE=======
     * |    state   |   due         |
     * |------------|---------------|
     * |    666     |   error       |
     * |    -1      |   overdue     |
     * |    0       |   no due date |
     * |    1       |   today       |
     * |    2       |   this week   |
     * |    3       |   this month  |
     * ==============================
     * 
     * @param task in question
     * @return int based on the time-state
     * 
     * @@author A0139661Y
     */
    public int getTaskTimeState(ReadOnlyTask task) {
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
        
        return 666;
    }
    
    //@@author A0139661Y
    public List<Integer> getTaskTimeStateCount(ObservableList<Task> taskObservableList) {
        List<Integer> countMap = Collections.EMPTY_LIST;
        for (Task t:taskObservableList) {
            countMap.add(getTaskTimeState(t));          
        }
        return countMap;
    }
	
}
