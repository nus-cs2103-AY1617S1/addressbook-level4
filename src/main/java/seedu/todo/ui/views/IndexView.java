package seedu.todo.ui.views;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seedu.todo.commons.util.FxViewUtil;
import seedu.todo.models.Event;
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
    public List<Event> events = new ArrayList<Event>();
    public List<Task> tasks = new ArrayList<Task>();
    public List<String> tags = new ArrayList<String>();
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
        tagList.tags = tags;
        tagList.render();

        // Render TaskList
        TaskList taskList = TaskList.load(primaryStage, tasksPane);
        taskList.tasks = tasks;
        taskList.events = events;
        taskList.render();
    }

}
