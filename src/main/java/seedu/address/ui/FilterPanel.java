package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;

public class FilterPanel extends UiPart {
    
    private static final Logger logger = LogsCenter.getLogger(FilterPanel.class);
    private static final String FXML = "FilterPanel.fxml";
    private GridPane mainPane;
    private AnchorPane placeHolder;
    
    @FXML
    private CheckBox eventsCheckBox;
    
    @FXML
    private CheckBox tasksCheckBox;
    
    @FXML
    private CheckBox doneCheckBox;
    
    @FXML
    private CheckBox undoneCheckBox;
    
    @FXML
    private TextField deadlineTextField;
    
    @FXML
    private TextField startDateTextField;
    
    @FXML
    private TextField endDateTextField;
    
    @FXML
    private TextField tagsTextField;
    
    @FXML
    private ChoiceBox<String> priorityChoiceBox;
    
    public static FilterPanel load(Stage stage, AnchorPane placeHolder) {
        FilterPanel filterPanel = UiPartLoader.loadUiPart(stage, placeHolder, new FilterPanel());
        filterPanel.configure();
        return filterPanel;
    }
    
    public void configure() {
        addMainPane();
    }

    private void addMainPane() {
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(mainPane);
    }

    @Override
    public void setNode(Node node) {
        mainPane = (GridPane) node;
    }
    
    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeHolder = placeholder;
    }
    
    @Override
    public String getFxmlPath() {
        return FXML;
    }

}
