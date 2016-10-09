package seedu.todo.ui.views;

import java.util.ArrayList;

public class IndexView extends View {

	private String FXML_PATH = "views/IndexView.fxml";
	
	// Props
	public ArrayList<Object> tasks = new ArrayList<Object>(); // stub
	
	// Components



	@Override
	public String getFxmlPath() {
		return FXML_PATH;
	}
	
	
}
