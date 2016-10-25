package seedu.jimi.ui;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import seedu.jimi.commons.core.LogsCenter;
import seedu.jimi.commons.util.FxViewUtil;
import seedu.jimi.model.event.Event;
import seedu.jimi.model.task.DeadlineTask;
import seedu.jimi.model.task.ReadOnlyTask;

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
        SplitPane.setResizableWithParent(placeHolderPane, true);
        FxViewUtil.applyAnchorBoundaryParameters(placeHolderPane, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(panel, 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(panel);
    }
    
    
    public static AgendaPanel load(Stage primaryStage, AnchorPane agendaPlaceholder, ObservableList<ReadOnlyTask> taskList, ObservableList<ReadOnlyTask> eventList) {
        AgendaPanel agendaPanel = 
                UiPartLoader.loadUiPart(primaryStage, agendaPlaceholder, new AgendaPanel());
        agendaPanel.configure(taskList, eventList);
        return agendaPanel;
    }
    
    private void configure(ObservableList<ReadOnlyTask> taskList, ObservableList<ReadOnlyTask> eventList) {
        instantiateObjectLists(taskList, eventList);
        configureTaskColumnsCellFactories();
        configureEventsColumnsCellFactories();
        setConnections();
        addToPlaceholder();
        registerAsAnEventHandler(this); //to update labels
    }

    private void setConnections() {
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
    private void instantiateObjectLists(ObservableList<ReadOnlyTask> taskList, ObservableList<ReadOnlyTask> eventList) {
        this.tasksList = taskList;
        this.eventsList = eventList;
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
                    if (a.getEnd() == null) {
                        return Bindings.createStringBinding(() -> "");
                    }                        
                    return Bindings.createStringBinding(() -> a.getEnd().toString());
                }
               return new SimpleStringProperty();
            }
        });
    }
}
   
