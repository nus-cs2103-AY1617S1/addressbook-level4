package seedu.todo.ui.views;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import seedu.todo.commons.util.FxViewUtil;
import seedu.todo.models.Event;
import seedu.todo.models.Task;
import seedu.todo.ui.components.Sidebar;
import seedu.todo.ui.components.TaskList;

/**
 * Index View, which shows the list of tasks and tags in a two-column format.
 * 
 * @@author A0139812A
 */
public class IndexView extends View {

    private static final String FXML_PATH = "views/IndexView.fxml";

    // FXML
    @FXML
    private Pane tagsPane;
    @FXML
    private Pane tasksPane;

    // Props
    public List<Event> events = new ArrayList<>();
    public List<Task> tasks = new ArrayList<>();
    public List<String> tags = new ArrayList<>();

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
        Sidebar tagList = load(primaryStage, tagsPane, Sidebar.class);
        tagList.tags = tags;
        tagList.render();

        // Render TaskList
        TaskList taskList = load(primaryStage, tasksPane, TaskList.class);
        taskList.tasks = tasks;
        taskList.events = events;
        taskList.render();
    }

}
