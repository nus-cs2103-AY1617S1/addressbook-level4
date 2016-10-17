package seedu.todo.ui.components;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.todo.models.TodoListDB;
import seedu.todo.ui.UiPartLoader;

public class TagList extends Component {

    private static final String FXML_PATH = "components/TagList.fxml";

    // Links
    private static final String TASKS_LABEL = "Tasks";
    private static final String TASKS_ICON_PATH = "/images/icon-pin.png";
    private static final String OVERDUE_LABEL = "Overdue Tasks";
    private static final String OVERDUE_ICON_PATH = "/images/icon-siren.png";
    private static final String EVENTS_LABEL = "Events";
    private static final String EVENTS_ICON_PATH = "/images/icon-calendar.png";

    private static final String TAG_LABEL = "Tags";
    
    // Props
    public List<String> tags; // stub

    // FXML
    @FXML
    private Text titleText;
    @FXML
    private VBox tagListLinksPlaceholder;
    @FXML
    private VBox tagListTagsPlaceholder;

    public static TagList load(Stage primaryStage, Pane placeholderPane) {
        return UiPartLoader.loadUiPart(primaryStage, placeholderPane, new TagList());
    }

    @Override
    public String getFxmlPath() {
        return FXML_PATH;
    }

    @Override
    public void componentDidMount() {
        titleText.setText(formatTagSize(tags.size()));

        // Load TagListLinks
        loadLinks();

        // Load TagListItems
        loadTags();
    }
    
    private String formatTagSize(int size) {
        return String.format("%s (%s)",TAG_LABEL, size);
    }
    
    private void loadLinks() {
        TodoListDB db = TodoListDB.getInstance();
        
        TagListLink.reset(tagListLinksPlaceholder);

        String[] linkLabels = { formatLink(TASKS_LABEL, db.countIncompleteTasks()), 
                                formatLink(OVERDUE_LABEL, db.countOverdueTasks()), 
                                formatLink(EVENTS_LABEL , db.countFutureEvents()) };
        String[] linkIconPaths = { TASKS_ICON_PATH, OVERDUE_ICON_PATH, EVENTS_ICON_PATH };

        for (int i = 0; i < linkLabels.length; i++) {
            TagListLink link = TagListLink.load(primaryStage, tagListLinksPlaceholder);
            link.linkLabel = linkLabels[i];
            link.iconPath = linkIconPaths[i];
            link.render();
        }
    }

    private void loadTags() {
        TagListItem.reset(tagListTagsPlaceholder);

        for (String tag : tags) {
            TagListItem item = TagListItem.load(primaryStage, tagListTagsPlaceholder);
            item.tag = tag;
            item.render();
        }
    }
    
    private String formatLink(String label, int total) {
        return String.format("%s (%d)", label, total);
    }

}
