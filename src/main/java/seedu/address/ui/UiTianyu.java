package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
//from w ww .j a v a 2  s .  c o m
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class UiTianyu extends Application {

	public static void main(String[] args)
	{
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage){
/////////creates input text field//////////////////////////////////
		TextField inputTextField = new TextField();
/////////dummy text fields/////////////////////////////////////////
		Text centerText = new Text("Center");
		Text rightText = new Text("right_test");
		Text bottomText = new Text("Bottom");
/////////creates the arraylist of tasks////////////////////////////
		ArrayList<UiTaskTest> taskList = new ArrayList<UiTaskTest>();	
////////enter key from keyboard////////////////////////////////////////////		
		inputTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
		    public void handle(KeyEvent keyEvent) {
		        String text;
		    	if (keyEvent.getCode() == KeyCode.ENTER)  {
//		            rightText.setText(inputTextField.getText());		
		    		taskList.add(new UiTaskTest(inputTextField.getText()));
		            inputTextField.setText("");
		        }
		    }
		});

		taskList.add(new UiTaskTest("bb"));
		taskList.add(new UiTaskTest("bb"));
		taskList.add(new UiTaskTest("bb"));

////////feedback text display/////////////////////////////////////////////////
		String numberOfTasks = String.valueOf(taskList.size());
		bottomText.setText("total number of tasks" + " = " + numberOfTasks);

////////reads the array of tasks description into an array of String		
		ArrayList<String> taskDescriptionString =  new ArrayList<String>();
		
		for (int i = 0; i < taskList.size(); i++){
		taskDescriptionString.add(taskList.get(i).getDescription());
		}

/////////creates the listView//////////////////////////////////////	
		ObservableList<String> taskObservableList = FXCollections.<String>observableArrayList(taskDescriptionString);

		ListView<String> actualListView = new ListView<>(taskObservableList);
		// Set the Orientation of the ListView
		actualListView.setOrientation(Orientation.VERTICAL);
		// Set the Size of the ListView
		actualListView.setPrefSize(1000, 700);
		
//////////////creates borderPane///////////////////////////////////
		BorderPane borderPane = new BorderPane(centerText, inputTextField,rightText , bottomText, actualListView);

/////////////populates the scene//////////////////////		
		Scene scene = new Scene(borderPane);
		// Add the scene to the Stages
		stage.setScene(scene);
		// Set the title of the Stage
		stage.setTitle("A simple BorderPane Example");
		// Display the Stage
		stage.show();
	}
	
}

