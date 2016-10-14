package seedu.todo.ui.components;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.todo.commons.util.DateUtil;
import seedu.todo.models.Task;
import seedu.todo.ui.UiPartLoader;

public class TaskListTaskItem extends MultiComponent {

    private static final String COMPLETED_ICON_PATH = "/images/icon-tick.png";
    private static final String FXML_PATH = "components/TaskListTaskItem.fxml";
    
    // Props
    public Task task;
    public Integer displayIndex;

    // FXML
    @FXML
    private Text taskText;
    @FXML
    private Text taskTime;
    @FXML
    private Text rowIndex;
    @FXML
    private Circle taskCheckMarkCircle;
    @FXML
    private ImageView taskCheckMarkImage;

    public static TaskListTaskItem load(Stage primaryStage, Pane placeholderPane) {
        return UiPartLoader.loadUiPart(primaryStage, placeholderPane, new TaskListTaskItem());
    }

    @Override
    public String getFxmlPath() {
        return FXML_PATH;
    }

    @Override
    public void componentDidMount() {
        taskText.setText(task.getName());
        taskTime.setText(DateUtil.formatTime(task.getCalendarDT()));
        rowIndex.setText(displayIndex.toString());
        
        if (task.isCompleted()) {
            showCompleted();
        } else {
            showIncomplete();
        }
    }
    
    private void showCompleted() {
        taskCheckMarkImage.setImage(new Image(COMPLETED_ICON_PATH));
        taskCheckMarkCircle.setRadius(0);
        taskText.getStyleClass().add("completed");
    }
    
    private void showIncomplete() {
        taskCheckMarkImage.setFitWidth(0);
    }

}
