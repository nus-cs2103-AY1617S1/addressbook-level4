package seedu.unburden.ui;

import java.text.ParseException;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import seedu.unburden.commons.core.LogsCenter;
import seedu.unburden.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.unburden.commons.util.FxViewUtil;
import seedu.unburden.logic.Logic;
import seedu.unburden.logic.commands.CommandResult;
import seedu.unburden.model.task.ReadOnlyTask;

/**
 * The Summary panel
 * which will show different types of tasks
 * @@author A0147986H
 */
public class SummaryPanel extends UiPart{

    private static final String FXML = "summaryPanel.fxml";

    private AnchorPane summary;
    @FXML
    private TitledPane today;
    @FXML
    private TitledPane tomorrow;
    @FXML
    private TitledPane importance;
    @FXML
    private TitledPane completeness;
    @FXML
    private Label urgent;
    @FXML
    private Label normal;
    @FXML
    private Label nonurgent;
    @FXML
    private Label done;
    @FXML
    private Label undone;

    private Logic logic;

    private AnchorPane placeHolderPane;    


    public SummaryPanel(){

    }

    public static SummaryPanel load(){
        SummaryPanel summaryPanel = new SummaryPanel();
        return summaryPanel;           
    }

    public static SummaryPanel load(Stage primaryStage, AnchorPane summaryPanelPlaceholder,
            Logic logic) {
        SummaryPanel summaryPanel = UiPartLoader.loadUiPart(primaryStage, summaryPanelPlaceholder, new SummaryPanel());
        summaryPanel.configure( logic);
        summaryPanel.addToPlaceholder();
        return summaryPanel;
    }

    public  void configure(Logic logic) {
        this.logic = logic;
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


 

  

