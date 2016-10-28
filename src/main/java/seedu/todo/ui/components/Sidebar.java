package seedu.todo.ui.components;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.todo.models.TodoListDB;

/**
 * @@author A0139812A
 */
public class Sidebar extends Component {

    private static final String FXML_PATH = "components/Sidebar.fxml";

    // Links
    private static final String TASKS_LABEL = "Tasks";
    private static final String TASKS_ICON_PATH = "/images/icon-pin.png";
    private static final String OVERDUE_LABEL = "Overdue Tasks";
    private static final String OVERDUE_ICON_PATH = "/images/icon-siren.png";
    private static final String EVENTS_LABEL = "Events";
    private static final String EVENTS_ICON_PATH = "/images/icon-calendar.png";

    private static final String TAG_LABEL = "Tags";
    
    // Props
    public List<String> tags = new ArrayList<>();

    // FXML
    @FXML
    private Text titleText;
    @FXML
    private VBox sidebarCountersPlaceholder;
    @FXML
    private VBox sidebarTagsPlaceholder;

   @Override
    public String getFxmlPath() {
        return FXML_PATH;
    }

    @Override
    public void componentDidMount() {
        titleText.setText(formatTagSize(tags.size()));

        // Load Counters
        loadCounters();

        // Load Tags
        loadTags();
    }
    
    private String formatTagSize(int size) {
        return String.format("%s (%s)",TAG_LABEL, size);
    }
    
    private void loadCounters() {
        TodoListDB db = TodoListDB.getInstance();
        
        // Clear items.
        SidebarCounter.reset(sidebarCountersPlaceholder);

        String[] linkLabels = { formatLink(TASKS_LABEL, db.countIncompleteTasks()), 
                                formatLink(OVERDUE_LABEL, db.countOverdueTasks()), 
                                formatLink(EVENTS_LABEL , db.countFutureEvents()) };
        String[] linkIconPaths = { TASKS_ICON_PATH, OVERDUE_ICON_PATH, EVENTS_ICON_PATH };

        for (int i = 0; i < linkLabels.length; i++) {
            SidebarCounter counter = load(primaryStage, sidebarCountersPlaceholder, SidebarCounter.class);
            counter.label = linkLabels[i];
            counter.iconPath = linkIconPaths[i];
            counter.render();
        }
    }

    private void loadTags() {
        TagListItem.reset(sidebarTagsPlaceholder);

        for (String tag : tags) {
            TagListItem item = load(primaryStage, sidebarTagsPlaceholder, TagListItem.class);
            item.tag = tag;
            item.render();
        }
    }
    
    private String formatLink(String label, int total) {
        return String.format("%s (%d)", label, total);
    }

}
