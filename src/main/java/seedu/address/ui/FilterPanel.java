package seedu.address.ui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.FilterPanelChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.EventDate;

public class FilterPanel extends UiPart {
    
    private static final Logger logger = LogsCenter.getLogger(FilterPanel.class);
    private static final String FXML = "FilterPanel.fxml";
    
    private static final String EVENTS = "events";
    private static final String TASKS = "tasks";
    private static final String DONE = "done";
    private static final String UNDONE = "undone";
    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";
    private static final String DEADLINE = "deadline";
    private static final String RECURRING = "recurring";
    private static final String EMPTY = "";
    private static final String INVALID_FILTER = "Invalid filter: ";
    private static final String SUCCESS_FILTER = "Filter the todoList";
    private static final String SPACE = "\\s+";
    private static final String ONE = "1";
    private static final String TWO = "2";
    private static final String THREE = "3";
    
    private GridPane mainPane;
    private AnchorPane placeHolder;
    private ResultDisplay resultDisplay;
    
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
    private TextField recurringTextField;
    
    @FXML
    private TextField startDateTextField;
    
    @FXML
    private TextField endDateTextField;
    
    @FXML
    private TextField tagsTextField;
    
    @FXML
    private ChoiceBox<String> priorityChoiceBox;
    
    public static FilterPanel load(Stage stage, AnchorPane placeHolder, ResultDisplay resultDisplay) {
        FilterPanel filterPanel = UiPartLoader.loadUiPart(stage, placeHolder, new FilterPanel());
        filterPanel.configure(resultDisplay);
        return filterPanel;
    }
    
    public void configure(ResultDisplay resultDisplay) {
        this.resultDisplay = resultDisplay;
        addMainPane();
        initialPriority();
    }

    private void addMainPane() {
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(mainPane);
    }
    
    private void initialPriority() {
        priorityChoiceBox.setItems(FXCollections.observableArrayList(ONE, TWO, THREE));
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
    
    @FXML
    private void handleFilterChanged() {
        Set<String> types = handleTypesChanged();
        Map<String, String> qualifications;
        try {
            qualifications = handleQualificationsChanged();
        } catch (IllegalValueException e) {
            logger.info(INVALID_FILTER + e.getMessage());
            resultDisplay.setStyleToIndicateIncorrectCommand();
            resultDisplay.postMessage(INVALID_FILTER + e.getMessage());
            return;
        }
        Set<String> tagSet = handleTagsChanged();
        logger.info("Input in filter panel changed");
        resultDisplay.setStyleToIndicateCorrectCommand();
        resultDisplay.postMessage(SUCCESS_FILTER);
        raise(new FilterPanelChangedEvent(types, qualifications, tagSet));
    }
    
    private Set<String> handleTypesChanged() {
        Set<String> types = new HashSet<>();
        if (eventsCheckBox.isSelected()) {
            types.add(EVENTS);
        }
        if (tasksCheckBox.isSelected()) {
            types.add(TASKS);
        }
        if (doneCheckBox.isSelected()) {
            types.add(DONE);
        }
        if (undoneCheckBox.isSelected()) {
            types.add(UNDONE);
        }
        return types;
    }
    
    private Map<String, String> handleQualificationsChanged() throws IllegalValueException {
        HashMap<String, String> qualifications = new HashMap<>();
        String deadline = deadlineTextField.getText().trim();
        if (!deadline.equals(EMPTY)) {
            deadline = Deadline.validateDate(deadline);
            qualifications.put(DEADLINE, deadline);
        }
        String startDate = startDateTextField.getText().trim();
        if (!startDate.equals(EMPTY)) {
            startDate = EventDate.validateDate(startDate);
            qualifications.put(START_DATE, startDate);
        }
        String endDate = endDateTextField.getText().trim();
        if (!endDate.equals(EMPTY)) {
            endDate = EventDate.validateDate(endDate);
            qualifications.put(END_DATE, endDate);
        }
        String recurring = recurringTextField.getText().trim();
        if (!recurring.equals(EMPTY)) {
            qualifications.put(RECURRING, recurring);
        }
        return qualifications;
    }
    
    private Set<String> handleTagsChanged() {
        String tags = tagsTextField.getText().trim();
        Set<String> tagSet;
        if (tags.equals(EMPTY)) {
            tagSet = new HashSet<>();
        } else {
            tagSet = new HashSet<>(Arrays.asList(tags.split(SPACE)));
        }
        return tagSet;
    }

}
