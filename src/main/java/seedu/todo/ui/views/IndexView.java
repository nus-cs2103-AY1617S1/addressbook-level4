package seedu.todo.ui.views;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seedu.todo.commons.util.FxViewUtil;
import seedu.todo.models.Task;
import seedu.todo.ui.UiPartLoader;
import seedu.todo.ui.components.TagList;
import seedu.todo.ui.components.TaskList;

public class IndexView extends View {

    private static final String FXML_PATH = "views/IndexView.fxml";

    // FXML
    @FXML
    private Pane tagsPane;
    @FXML
    private Pane tasksPane;

    // Props
    public List<Task> tasks = new ArrayList<Task>(); // stub
    public List<String> tags = new ArrayList<String>(); // stub
    public String indexTextValue;


    public static IndexView load(Stage primaryStage, Pane placeholderPane) {
        return UiPartLoader.loadUiPart(primaryStage, placeholderPane, new IndexView());
    }

    @Override
    public String getFxmlPath() {
        return FXML_PATH;
    }

    @Override
    public void componentDidMount() {
        // Makes full width wrt parent container.
        FxViewUtil.makeFullWidth(this.mainNode);

        // Load sub components
        loadComponents();
    }

    private void loadComponents() {
        // Render TagList
        TagList tagList = TagList.load(primaryStage, tagsPane);
        for (int i = 1; i <= 20; i++)  // Temp
            tags.add("Tag " + i);
        tagList.tags = tags;
        tagList.render();

        // Render TaskList
        TaskList taskList = TaskList.load(primaryStage, tasksPane);

        // Temp
        LocalDateTime date = LocalDateTime.now().minus(3, ChronoUnit.DAYS);
        for (int i = 1; i <= 10; i++) {
            Task newTask = new Task();
            newTask.setCalendarDT(date);
            newTask.setName("Task " + i);
            tasks.add(newTask);
            date = date.plus(2, ChronoUnit.DAYS);
        }
        taskList.tasks = tasks;
        taskList.render();
    }

}
