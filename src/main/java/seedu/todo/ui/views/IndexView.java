package seedu.todo.ui.views;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class IndexView extends View {

	private static final String FXML_PATH = "views/IndexView.fxml";
	
	public String indexTextValue;
	
	// FXML
	@FXML
	private Text indexText;
	
	// Props
	public ArrayList<Object> tasks = new ArrayList<Object>(); // stub
	
	// Components



	@Override
	public String getFxmlPath() {
		return FXML_PATH;
	}
	
	@Override
	public void componentDidMount() {
		System.out.println(indexTextValue);
		indexText.setText(indexTextValue);
	}
	
	
}
