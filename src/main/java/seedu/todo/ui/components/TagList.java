package seedu.todo.ui.components;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TagList extends Component {
	
	private static final String FXML_PATH = "components/TagList.fxml";
	
	// Links
	private static final String TASKS_LABEL = "Tasks";
	private static final String TASKS_ICON_PATH = "/images/icon-pin.png";
	private static final String OVERDUE_LABEL = "Overdue Tasks";
	private static final String OVERDUE_ICON_PATH = "/images/icon-siren.png";
	private static final String EVENTS_LABEL = "Events";
	private static final String EVENTS_ICON_PATH = "/images/icon-calendar.png";
	private static final String COMPLETED_LABEL = "Completed Tasks";
	private static final String COMPLETED_ICON_PATH = "/images/icon-tick.png";
	
	// Props
	public ArrayList<Object> tags; // stub
	
	// FXML
	@FXML
	private Text titleText;
	@FXML
	private VBox tagListLinksPlaceholder;

	@Override
	public String getFxmlPath() {
		return FXML_PATH;
	}
	
	@Override
	public void componentDidMount() {
		titleText.setText("Tags (" + tags.size() + ")");
		
		// Load TagListLinks
		String[] linkLabels = { TASKS_LABEL, OVERDUE_LABEL, EVENTS_LABEL, COMPLETED_LABEL };
		String[] linkIconPaths = { TASKS_ICON_PATH, OVERDUE_ICON_PATH, EVENTS_ICON_PATH, COMPLETED_ICON_PATH };
		
		for (int i = 0; i < linkLabels.length; i++) {
			TagListLink link = new TagListLink();
			String label = linkLabels[i];
			String iconPath = linkIconPaths[i];
			
			link.setHookModifyView(c -> {
				TagListLink comp = (TagListLink) c;
				comp.linkLabel = label;
				comp.iconPath = iconPath;
				return comp;
			});
			
			link.render(primaryStage, tagListLinksPlaceholder);
		}
	}

}
