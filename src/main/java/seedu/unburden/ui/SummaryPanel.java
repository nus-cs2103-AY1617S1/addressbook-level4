package seedu.unburden.ui;

import java.text.ParseException;

import com.google.common.eventbus.Subscribe;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import seedu.unburden.commons.core.LogsCenter;
import seedu.unburden.commons.events.model.ListOfTaskChangedEvent;
import seedu.unburden.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.unburden.commons.util.FxViewUtil;
import seedu.unburden.logic.Logic;
import seedu.unburden.logic.commands.CommandResult;
import seedu.unburden.model.task.ReadOnlyTask;
import seedu.unburden.ui.TaskListPanel;
import seedu.unburden.model.ListOfTask;
import seedu.unburden.model.ModelManager;

/**
 * The Summary panel
 * which will show the number of different types of tasks
 * @@author A0147986H
 */
public class SummaryPanel extends UiPart{

    private static final String FXML = "SummaryPanel.fxml";

    @FXML
    private AnchorPane summary;
    @FXML
    private GridPane mainContainer;
    @FXML
    private Label today;
    @FXML
    private Label tomorrow;
    @FXML
    private Label nextWeek;    
    @FXML
    private Label done;
    @FXML
    private Label undone;

    private AnchorPane placeHolderPane;    


    public SummaryPanel(){

    }

    public static SummaryPanel load(){
        SummaryPanel summaryPanel = new SummaryPanel();
        return summaryPanel;           
    }

    public static SummaryPanel load(Stage primaryStage, AnchorPane summaryPanelPlaceholder,
    		ObservableList<ReadOnlyTask> taskList) {
        SummaryPanel summaryPanel = UiPartLoader.loadUiPart(primaryStage, summaryPanelPlaceholder, new SummaryPanel());
        summaryPanel.configure(taskList);
        
        return summaryPanel;
    }

    public  void configure(ObservableList<ReadOnlyTask> taskList) {
    	addToPlaceholder();
    }

    //@@author A0143095H
    @FXML
    public void initialize() {
    	
   	    today.setText(Integer.toString(ListOfTask.todayCounter));
   	    tomorrow.setText(Integer.toString(ListOfTask.tomorrowCounter));
   	    nextWeek.setText(Integer.toString(ListOfTask.nextWeekCounter));
        done.setText(Integer.toString(ListOfTask.doneCounter));
        undone.setText(Integer.toString(ListOfTask.undoneCounter));
       
    }
             
  //@@author A0143095H
    @Subscribe
    private void modelChangedEvent(ListOfTaskChangedEvent change) {
    	
   	    today.setText(Integer.toString(ListOfTask.todayCounter));
   	    tomorrow.setText(Integer.toString(ListOfTask.tomorrowCounter));
   	    nextWeek.setText(Integer.toString(ListOfTask.nextWeekCounter));
        done.setText(Integer.toString(ListOfTask.doneCounter));
        undone.setText(Integer.toString(ListOfTask.undoneCounter));
       
    }    
    
    private  void addToPlaceholder() {
        
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(summary, 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(summary);
    
    }

    @Override
    public void setNode(Node node) {
       summary = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }
    
}