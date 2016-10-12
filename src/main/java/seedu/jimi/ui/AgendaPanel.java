package seedu.jimi.ui;

import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.jimi.commons.core.LogsCenter;
import seedu.jimi.commons.events.model.AddressBookChangedEvent;
import seedu.jimi.commons.events.ui.TaskPanelSelectionChangedEvent;
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
    private TreeTableView<ReadOnlyTask> tasksTreeTableView;
    @FXML
    private TreeTableColumn<ReadOnlyTask, String> tasksTableColumnLeft;
    @FXML
    private TreeTableColumn<ReadOnlyTask, String> tasksTableColumnRight;
    @FXML
    private TreeTableView<ReadOnlyTask> eventsTreeTableView;
    @FXML
    private TreeTableColumn<ReadOnlyTask, String> eventsTableColumnLeft;
    @FXML
    private TreeTableColumn<ReadOnlyTask, String> eventsTableColumnRight;
    
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
    
    /**
     * Initializes the agenda view with relevant tasks and events.
     */
    @FXML
    private void initialize() {
        // Initialize the tasks table with the two columns.
        this.tasksTreeTableView = new TreeTableView<>();
        this.tasksTableColumnLeft = new TreeTableColumn("Name");
        this.tasksTableColumnRight = new TreeTableColumn("Details");
        
        tasksTableColumnLeft.setCellValueFactory((TreeTableColumn.CellDataFeatures<ReadOnlyTask, String> p) -> new ReadOnlyStringWrapper(
                p.getValue().getValue().getName().toString()));
        
        tasksTableColumnRight.setCellValueFactory((TreeTableColumn.CellDataFeatures<ReadOnlyTask, String> p) -> new ReadOnlyStringWrapper(
                p.getValue().getValue().getTags().toString()));
        
        tasksTreeTableView.getColumns().setAll(tasksTableColumnLeft, tasksTableColumnRight);
    }
    
    public static AgendaPanel load(Stage primaryStage, AnchorPane agendaPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        AgendaPanel agendaPanel = 
                UiPartLoader.loadUiPart(primaryStage, agendaPlaceholder, new AgendaPanel());
        agendaPanel.configure(taskList);
        return agendaPanel;
    }
    
    private void configure(ObservableList<ReadOnlyTask> taskList) {
        setConnections(taskList);
        addToPlaceholder();
        registerAsAnEventHandler(this); //to update labels
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        this.tasksList = FXCollections.observableArrayList();
        this.eventsList = FXCollections.observableArrayList();
        
        updateTasksList(taskList);
    }
    
    private void updateTasksList(ObservableList<ReadOnlyTask> taskList) {
        for(ReadOnlyTask t : taskList){
            if(!t.isCompleted()) {//TODO: add checks for due dates
                this.tasksList.add(t);
            }
        }
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, true);
        placeHolderPane.getChildren().add(panel);
    }
}
   
