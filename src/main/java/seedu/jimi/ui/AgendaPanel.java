package seedu.jimi.ui;

import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import seedu.jimi.commons.core.LogsCenter;
import seedu.jimi.commons.events.model.AddressBookChangedEvent;
import seedu.jimi.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.jimi.commons.util.FxViewUtil;
import seedu.jimi.model.datetime.DateTime;
import seedu.jimi.model.task.DeadlineTask;
import seedu.jimi.model.event.Event;
import seedu.jimi.model.task.FloatingTask;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.ui.TaskListPanel.TaskListViewCell;

/**
 * Agenda window of Jimi, displays most relevant tasks and events to the user when first starting up app.
 * @author zexuan
 *
 */
public class AgendaPanel extends UiPart{
    private final Logger logger = LogsCenter.getLogger(AgendaPanel.class);
    private static final String FXML = "AgendaPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;
    
    //list of tasks and events to display on agenda
    private ObservableList<ReadOnlyTask> tasksList;
    private ObservableList<ReadOnlyTask> eventsList;
    
    //main agenda views
    @FXML
    private TableView<ReadOnlyTask> tasksTableView;
    @FXML private TableColumn<ReadOnlyTask, String> tasksTableColumnId;
    @FXML private TableColumn<ReadOnlyTask, String> tasksTableColumnTags;
    @FXML private TableColumn<ReadOnlyTask, String> tasksTableColumnDetails;
    @FXML private TableColumn<ReadOnlyTask, String> tasksTableColumnEndDate;
    
    @FXML
    private TableView<ReadOnlyTask> eventsTableView;
    @FXML private TableColumn<ReadOnlyTask, String> eventsTableColumnId;
    @FXML private TableColumn<ReadOnlyTask, String> eventsTableColumnTags;
    @FXML private TableColumn<ReadOnlyTask, String> eventsTableColumnDetails;
    @FXML private TableColumn<ReadOnlyTask, String> eventsTableColumnStartDate;
    @FXML private TableColumn<ReadOnlyTask, String> eventsTableColumnEndDate;
    
    
    public AgendaPanel(){
        super();
    }
    
    @Override
    public void setNode(Node node) {
        this.panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }
    
    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(placeHolderPane, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(panel, 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(panel);
    }
    
    
    public static AgendaPanel load(Stage primaryStage, AnchorPane agendaPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        AgendaPanel agendaPanel = 
                UiPartLoader.loadUiPart(primaryStage, agendaPlaceholder, new AgendaPanel());
        agendaPanel.configure(taskList);
        return agendaPanel;
    }
    
    private void configure(ObservableList<ReadOnlyTask> taskList) {
        instantiateObjectLists();
        updateTasksList(taskList);
        
        configureTaskColumnsCellFactories();
        configureEventsColumnsCellFactories();
        setConnections(taskList);
        addToPlaceholder();
        registerAsAnEventHandler(this); //to update labels
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        tasksTableView.setItems(this.tasksList);
        eventsTableView.setItems(this.eventsList);
        
        tasksTableView.getColumns().setAll(tasksTableColumnId, 
                tasksTableColumnTags,
                tasksTableColumnDetails,
                tasksTableColumnEndDate);
        
        eventsTableView.getColumns().setAll(eventsTableColumnId,
                eventsTableColumnTags,
                eventsTableColumnDetails,
                eventsTableColumnStartDate,
                eventsTableColumnEndDate);
    }

    /**
     * Instantiates the tasks and events lists.
     */
    private void instantiateObjectLists() {
        this.tasksList = FXCollections.observableArrayList();
        this.eventsList = FXCollections.observableArrayList();
    }

    /**
     * Sets up the cellValueFactories for all tasks TableColumn views.
     * Formatting of data shown to user is done here.
     */
    private void configureTaskColumnsCellFactories() {
        tasksTableColumnId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReadOnlyTask, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ReadOnlyTask, String> p) {  
                
                return new SimpleStringProperty("t" + (p.getTableView().getItems().indexOf(p.getValue()) + 1 ) + ".");
            }
         });
        
        tasksTableColumnTags.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReadOnlyTask, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ReadOnlyTask, String> cd) {  
                ReadOnlyTask a  = cd.getValue();

                return Bindings.createStringBinding(() -> a.tagsString());
            }
         });
        
        tasksTableColumnDetails.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReadOnlyTask, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ReadOnlyTask, String> cd) {
                ReadOnlyTask a  = cd.getValue();

                return Bindings.createStringBinding(() -> a.getName().toString());
            }
        });
        
        tasksTableColumnEndDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReadOnlyTask, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ReadOnlyTask, String> cd) {  
                if(cd.getValue() instanceof DeadlineTask){
                    DeadlineTask a = (DeadlineTask) cd.getValue();
                    return Bindings.createStringBinding(() -> a.getDeadline().toString());
                }
               return new SimpleStringProperty();
            }
        });
        
        
    }
    
    /**
     * Sets up the cellValueFactories for all events TableColumn views.
     * Formatting of data shown to user is done here.
     */
    private void configureEventsColumnsCellFactories() {
        eventsTableColumnId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReadOnlyTask, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ReadOnlyTask, String> p) {  
                
                return new SimpleStringProperty("e" + (p.getTableView().getItems().indexOf(p.getValue()) + 1 ) + ".");
            }
         });
        
        eventsTableColumnTags.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReadOnlyTask, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ReadOnlyTask, String> cd) {  
                ReadOnlyTask a  = cd.getValue();

                return Bindings.createStringBinding(() -> a.tagsString());
            }
         });
        
        eventsTableColumnDetails.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReadOnlyTask, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ReadOnlyTask, String> cd) {
                ReadOnlyTask a  = cd.getValue();

                return Bindings.createStringBinding(() -> a.getName().toString());
            }
        });
        
        eventsTableColumnStartDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReadOnlyTask, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ReadOnlyTask, String> cd) {  
                if(cd.getValue() instanceof Event){
                    Event a = (Event) cd.getValue();
                    return Bindings.createStringBinding(() -> a.getStart().toString());
                }
               return new SimpleStringProperty();
            }
        });
        
        eventsTableColumnEndDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReadOnlyTask, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ReadOnlyTask, String> cd) {  
                if(cd.getValue() instanceof Event){
                    Event a = (Event) cd.getValue();
                    return Bindings.createStringBinding(() -> a.getEnd().toString());
                }
               return new SimpleStringProperty();
            }
        });
    }
    
    /**
     * Runs every time the task list need to be re-populated.
     * @param taskList
     */
    private void updateTasksList(List<ReadOnlyTask> taskList) {
        ObservableList<ReadOnlyTask> newTasksList = FXCollections.observableArrayList();
        
        for(ReadOnlyTask t : taskList){
            if(t instanceof FloatingTask
                    && !t.isCompleted()) {//TODO: add checks for due dates
                newTasksList.add(t);
            }
        }
        
        this.tasksList.setAll(newTasksList);
    }
    
    /**
     * Runs every time the events list need to be re-populated.
     * @param taskList
     */
    private void updateEventsList(List<ReadOnlyTask> eventList) {
        ObservableList<ReadOnlyTask> newEventsList = FXCollections.observableArrayList();
        
        for(ReadOnlyTask t : eventList){
            if(t instanceof Event) { //checks if startDate is ahead of current system time
                newEventsList.add(t);
            }
        }
        
        this.eventsList.setAll(newEventsList);
    }

    /**
     * Updates all the titles when taskBook is changed. Updates remaining tasks for each title.
     * @param abce
     */
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        updateTasksList(abce.data.getTaskList());
        updateEventsList(abce.data.getTaskList());
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Reloading lists : " + ""+abce.data.getTaskList().size()));
    }
}
   
