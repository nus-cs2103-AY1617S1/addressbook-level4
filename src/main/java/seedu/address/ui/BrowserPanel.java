package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.AppointmentImplLocal;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.commands.BlockCommand;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskComponent;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskType;
import seedu.address.commons.core.LogsCenter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart{

    private static Logger logger = LogsCenter.getLogger(BrowserPanel.class);
    private static final String FXML = "BrowserPanel.fxml";
    
    @FXML
    private MyAgenda agenda;

    /**
     * Constructor is kept private as {@link #load(AnchorPane)} is the only way to create a BrowserPanel.
     */
    private BrowserPanel() {
    	super();
    }

    @Override
    public void setNode(Node node) {
    }

    @Override
    public String getFxmlPath() {
        return FXML;//not applicable
    }
    
    @Override
    public void setPlaceholder(AnchorPane pane) {
    }

    /**
     * Factory method for creating a Browser Panel.
     * This method should be called after the FX runtime is initialized and in FX application thread.
     * @param placeholder The AnchorPane where the BrowserPanel must be inserted
     */
    public static BrowserPanel load(AnchorPane placeholder, ObservableList<TaskComponent> taskList){
        logger.info("Initializing Calendar");
        BrowserPanel browserPanel = new BrowserPanel();
        //browserPanel.browser = new WebView();
        browserPanel.agenda = new MyAgenda();
        browserPanel.initialize(taskList);
        placeholder.setOnKeyPressed(Event::consume); // To prevent triggering events for typing inside the loaded Web page.
        FxViewUtil.applyAnchorBoundaryParameters(browserPanel.agenda, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(browserPanel.agenda);
        return browserPanel;
    }
    
    
    private void initialize(ObservableList<TaskComponent> taskList){
    	loadTaskList(taskList);
    }

    public void loadTaskPage(ReadOnlyTask task) {
        //loadPage("https://www.google.com.sg/#safe=off&q=" + task.getName().fullName.replaceAll(" ", "+"));
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        agenda = null;
    }
    
    public void loadTaskList(ObservableList<TaskComponent> taskList){
    	agenda.addAllToAgenda(taskList);    		
    }
    
}
