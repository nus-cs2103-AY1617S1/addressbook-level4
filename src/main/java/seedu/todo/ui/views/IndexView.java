package seedu.todo.ui.views;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import seedu.todo.commons.util.FxViewUtil;
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
	public ArrayList<Object> tasks = new ArrayList<Object>(); // stub
	public ArrayList<String> tags = new ArrayList<String>(); // stub
	public String indexTextValue;


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
		TagList tagList = new TagList();
		tagList.setHookModifyView(v -> {
			TagList view = (TagList) v;
			
			// Temp
			tags.add("tag1");
			tags.add("tag2");
			tags.add("tag3");
			tags.add("tag4");
			tags.add("tag5");
			tags.add("tag6");
			tags.add("tag7");
			tags.add("tag1");
			tags.add("tag2");
			tags.add("tag3");
			tags.add("tag4");
			tags.add("tag5");
			tags.add("tag6");
			tags.add("tag7");
			tags.add("tag1");
			tags.add("tag2");
			tags.add("tag3");
			tags.add("tag4");
			tags.add("tag5");
			tags.add("tag6");
			tags.add("tag7");
			
			view.tags = tags;
			return view;
		});
		tagList.render(primaryStage, tagsPane);
		
		// Render TaskList
		TaskList taskList = new TaskList();
		taskList.setHookModifyView(v -> {
			TaskList view = (TaskList) v;
			view.tasks = tasks;
			return view;
		});
		taskList.render(primaryStage, tasksPane);
	}
	
	
}
