package seedu.todo.ui.views;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
	public ArrayList<TaskStub> tasks = new ArrayList<TaskStub>(); // stub
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
		tagList.passInProps(v -> {
			TagList view = (TagList) v;
			
			// Temp
			for (int i = 1; i <= 20; i++) 
				tags.add("Tag " + i);
			
			view.tags = tags;
			return view;
		});
		tagList.render(primaryStage, tagsPane);
		
		// Render TaskList
		TaskList taskList = new TaskList();
		taskList.passInProps(v -> {
			TaskList view = (TaskList) v;
			
			// Temp
			LocalDateTime date = LocalDateTime.now().minus(3, ChronoUnit.DAYS);
			for (int i = 1; i <= 10; i++) {
				tasks.add(new TaskStub(date, "Task " + i));
				date = date.plus(1, ChronoUnit.DAYS);
			}
			
			view.tasks = tasks;
			return view;
		});
		taskList.render(primaryStage, tasksPane);
	}
	
	public static class TaskStub {
		public LocalDateTime dateTime;
		public String name;
		
		public TaskStub(LocalDateTime dateTime, String name) {
			this.dateTime = dateTime;
			this.name = name;
		}
	}
	
	
}
