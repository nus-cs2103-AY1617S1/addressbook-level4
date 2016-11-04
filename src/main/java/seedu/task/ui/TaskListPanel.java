package seedu.task.ui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.Name;
import seedu.task.model.task.StartTime;
import seedu.task.model.task.EndTime;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Deadline;
import seedu.task.model.task.Status;
import seedu.task.model.task.Name;



import java.util.logging.Logger;

/**
 * Panel containing the list of tasks.
 */
//@@author A0133369B
public class TaskListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> taskListView;
    @FXML
    private TableView<ReadOnlyTask> taskTable;
    @FXML
    private TableColumn<ReadOnlyTask, Number> idColumn;
    @FXML
    private TableColumn<ReadOnlyTask, Name> taskNameColumn;
    @FXML
    private TableColumn<ReadOnlyTask, StartTime> startTimeColumn;
    @FXML
    private TableColumn<ReadOnlyTask, EndTime> endTimeColumn;
    @FXML
    private TableColumn<ReadOnlyTask, Deadline> dueTimeColumn;
    @FXML
    private TableColumn<ReadOnlyTask, String> tagColumn;
    @FXML
    private TableColumn<ReadOnlyTask, Status> statusColumn;

    public TaskListPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static TaskListPanel load(Stage primaryStage, AnchorPane taskListPlaceholder,
                                       ObservableList<ReadOnlyTask> taskList) {
        TaskListPanel taskListPanel =
                UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new TaskListPanel());
        taskListPanel.configure(taskList);
        
        
        return taskListPanel;
    }
    
 
    //table initialization
    private void initialize(){
    	    	
    	idColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Number>(taskTable.getItems().indexOf(column.getValue()) + 1));
  	  	
    	
    	taskNameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getName()));
    	
    	
    	startTimeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getStartTime()));
    	startTimeColumn.setCellFactory(column -> {
    	    return new TableCell<ReadOnlyTask, StartTime>() {
    	        @Override
    	        protected void updateItem(StartTime item, boolean empty) {
    	            super.updateItem(item, empty);

    	            if (item == null || empty) {
    	                setText(null);
    	                setStyle("");
    	            } else if(item.toString().equals("now")) {
    	            	setText("-");
    	            	
    	            }
    	            else {
    	                setText(item.toString());
    	                setStyle("");

    	            }
    	        }
    	    };
    	});
    	
    	
    	endTimeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getEndTime()));
    	endTimeColumn.setCellFactory(column -> {
    	    return new TableCell<ReadOnlyTask, EndTime>() {
    	        @Override
    	        protected void updateItem(EndTime item, boolean empty) {
    	            super.updateItem(item, empty);

    	            if (item == null || empty) {
    	                setText(null);
    	                setStyle("");
    	            } else if(item.toString().equals("no endtime")) {
    	            	setText("-");
    	            	
    	            }else {
    	                // Format date.
    	                setText(item.toString());
    	                setStyle("");

     	            }
    	        }
    	    };
    	});
    	
    	
    	dueTimeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getDeadline()));
    	dueTimeColumn.setCellFactory(column -> {
    	    return new TableCell<ReadOnlyTask, Deadline>() {
    	        @Override
    	        protected void updateItem(Deadline item, boolean empty) {
    	            super.updateItem(item, empty);

    	            if (item == null || empty) {
    	                setText(null);
    	                setStyle("");
    	            }  else if(item.toString().equals("no deadline")) {
    	            	setText("-");
    	            	
    	            }  else {
    	                // Format date.
    	                setText(item.toString());
    	                setStyle("");

    	            }
    	        }
    	    };
    	});
    	
    	
    	tagColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().tagsString()));
    	tagColumn.setCellFactory(column -> {
    	    return new TableCell<ReadOnlyTask, String>() {
    	        @Override
    	        protected void updateItem(String item, boolean empty) {
    	            super.updateItem(item, empty);

    	            if (item == null || empty) {
    	                setText(null);
    	                setStyle("-");
    	            }  else {
    	                // Format date.
    	                setText(item.toString());
    	                setStyle("");

    	            }
    	        }
    	    };
    	});
    	
    		
    }
    
//    private void setStatus(){
//    	statusColumn.setCellFactory(column -> {
//    	    return new TableCell<ReadOnlyTask, Status>() {
//    	    	@Override
//    	    	protected void updateItem(Status item, boolean empty) {
//    	            super.updateItem(item, empty);
//    	            
//    	            Task task = (Task) getTableRow().getItem();
//
//    	            if(task.getStatus().getDoneStatus()==true) {
//    	            	setText("1");
//        	    		setStyle("-fx-background-color: yello");
//        	    		System.out.print("0");
//        	    	} else if (task.getStatus().getNewlyAddedStatus()==true){
//    	                //setText(item.toString());
//    	                setStyle("-fx-background-color: yello");
//    	                System.out.print("1");
//    	            } else if (task.getStatus().getOverdueStatus()==true){
//    	            	setStyle("-fx-background-color: red");
//    	            	System.out.print("2");
//    	            } else{
//    	            	setStyle("-fx-background-color: white");
//    	            	System.out.print(taskTable.getItems().get(0).getStatus().getNewlyAddedStatus());
//    	            	System.out.print("3");
//    	            }
//    	        }
//    	    	
//    	    }; 	    
//    	});
//    }
//    
//    
    
    
   // prenvent columns reordering
    private void disableTableColumnReordering() {
        TableColumn[] columns = { statusColumn,idColumn, taskNameColumn, startTimeColumn, endTimeColumn, dueTimeColumn, tagColumn };
        taskTable.getColumns().clear();
    	
        taskTable.getColumns().addListener(new ListChangeListener<TableColumn>() {
            public boolean reordered = false;

            @Override
            public void onChanged(Change change) {
                change.next();
                if (change.wasReplaced() && !reordered) {
                    reordered = true;
                    taskTable.getColumns().setAll(columns);
                    reordered = false;
                }
            }
        });
        taskTable.getColumns().addAll(columns);
    }
    
    // autoscroll when we add new events
    private void autoScroll(){
    	taskTable.getItems().addListener(
                new ListChangeListener<ReadOnlyTask>() {
                    @Override
                    public void onChanged(
                            javafx.collections.ListChangeListener.Change<? extends ReadOnlyTask> arg0) {
                    	taskTable.scrollTo(taskTable.getItems().size());
                    }

                });
    }
  

    private void configure(ObservableList<ReadOnlyTask> taskList) {
        setConnections(taskList);
        addToPlaceholder();
        
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
    	taskTable.setPlaceholder(new Label("Come on and add something!"));
    	
    	
    	taskTable.setItems(taskList);
    	    	
    	initialize();
    	
    	disableTableColumnReordering();    	
     	
    	autoScroll();
    	
    	setEventHandlerForSelectionChangeEvent();
    	
    	
        
        
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
       
        placeHolderPane.getChildren().add(panel);
        
    }

    private void setEventHandlerForSelectionChangeEvent() {
    	taskTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            	TableViewSelectionModel selectionModel = taskTable.getSelectionModel();
                ObservableList selectedCells = selectionModel.getSelectedCells();
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                Object val = tablePosition.getTableColumn().getCellData(newValue);
                System.out.println("Selected Value" + val);

            }
        });
        
    }
   

}
