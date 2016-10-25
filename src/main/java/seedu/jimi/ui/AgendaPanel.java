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
import javafx.scene.control.TableCell;
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
        tasksTableColumnId.setCellValueFactory(cellData -> new SimpleStringProperty("t" + (cellData.getTableView().getItems().indexOf(cellData.getValue()) + 1) + "."));
        tasksTableColumnId.setCellFactory(getCustomPriorityCellFactory());
        tasksTableColumnTags.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().tagsString()));
        tasksTableColumnTags.setCellFactory(getCustomPriorityCellFactory());
        tasksTableColumnDetails.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName().toString()));
        tasksTableColumnDetails.setCellFactory(getCustomPriorityCellFactory());
        tasksTableColumnEndDate.setCellValueFactory(cellData -> new SimpleStringProperty(((DeadlineTask) cellData.getValue()).getDeadline().toString()));  
        tasksTableColumnEndDate.setCellFactory(getCustomPriorityCellFactory());
    }
    
    /**
     * Sets up the cellValueFactories for all events TableColumn views.
     * Formatting of data shown to user is done here.
     */
    private void configureEventsColumnsCellFactories() {
        eventsTableColumnId.setCellValueFactory(cellData -> new SimpleStringProperty("e" + (cellData.getTableView().getItems().indexOf(cellData.getValue()) + 1) + "."));
        eventsTableColumnId.setCellFactory(getCustomPriorityCellFactory());
        eventsTableColumnTags.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().tagsString()));
        eventsTableColumnTags.setCellFactory(getCustomPriorityCellFactory());
        eventsTableColumnDetails.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName().toString()));
        eventsTableColumnDetails.setCellFactory(getCustomPriorityCellFactory());
        eventsTableColumnStartDate.setCellValueFactory(cellData -> new SimpleStringProperty(((Event) cellData.getValue()).getStart().toString()));
        eventsTableColumnStartDate.setCellFactory(getCustomPriorityCellFactory());
        eventsTableColumnEndDate.setCellValueFactory(cellData -> new SimpleStringProperty(((Event) cellData.getValue()).getEnd().toString()));
        eventsTableColumnEndDate.setCellFactory(getCustomPriorityCellFactory());
    }
    
    private Callback<TableColumn<ReadOnlyTask, String>, TableCell<ReadOnlyTask, String>> getCustomPriorityCellFactory() {
        return new Callback<TableColumn<ReadOnlyTask, String>, TableCell<ReadOnlyTask, String>>() {

            @Override
            public TableCell<ReadOnlyTask, String> call(TableColumn<ReadOnlyTask, String> param) {    
                TableCell<ReadOnlyTask, String> cell = new TableCell<ReadOnlyTask, String>() {
                    
                    @Override
                    public void updateItem(final String item, boolean empty) {
                        
                        // CSS Styles
                        String low_priority = "low-priority";
                        String med_priority = "medium-priority";
                        String high_priority = "high-priority";
                        String cssStyle = "";

                        ReadOnlyTask rowTask = null;
                        if( getTableRow() != null ) {
                            rowTask = (ReadOnlyTask) getTableRow().getItem();
                        }

                        //Remove all previously assigned CSS styles from the cell.
                        getStyleClass().remove(low_priority);
                        getStyleClass().remove(med_priority);
                        getStyleClass().remove(high_priority);

                        super.updateItem((String) item, empty);

                        //Determine how to format the cell based on the status of the container.
                        if( rowTask == null ) {
                            cssStyle = low_priority;
                        } else if( rowTask.getPriority().toString().toLowerCase().contains("med") ) {
                            cssStyle = med_priority;
                        } else if( rowTask.getPriority().toString().toLowerCase().contains("high") ) {
                            cssStyle = high_priority;
                        } else {
                            cssStyle = low_priority;
                        }

                        //Set the CSS style on the cell and set the cell's text.
                        getStyleClass().add(cssStyle);
                        if( item != null ) {
                            setText( item.toString()  );
                        } else {
                            setText( "" );
                        }                       
                    }
                };
                return cell;
            }
        };
    }
}
   
