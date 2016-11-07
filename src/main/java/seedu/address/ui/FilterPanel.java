package seedu.address.ui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.FilterPanelChangedEvent;
import seedu.address.commons.events.ui.JumpToFilterPanelEvent;
import seedu.address.commons.events.ui.UpdateFilterPanelEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.commons.util.Types;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.EventDate;
import seedu.address.model.task.Recurring;

//@@author A0146123R
/**
 * Controller for the filter panel that is displayed at the left of the application.
 */
public class FilterPanel extends UiPart {

    public static final String SUCCESS_FILTER = "Filter the todoList";
    public static final String INVALID_FILTER = "Invalid filter: ";

    private static final Logger logger = LogsCenter.getLogger(FilterPanel.class);
    private static final String FXML = "FilterPanel.fxml";

    private static final String EMPTY = "";
    private static final String NIL = "nil";
    private static final String SPACE = "\\s+";
    private static final String ONE = "1";
    private static final String TWO = "2";
    private static final String THREE = "3";
    
    private static final String DEFAULT_BACKGROUND = "-fx-background-color: white";
    private static final String ERROR_BACKGROUND = "-fx-background-color: #d9534f";

    private GridPane mainPane;
    private AnchorPane placeHolder;
    private ResultDisplay resultDisplay;

    @FXML
    private ToggleButton eventsToggleButton;

    @FXML
    private ToggleButton tasksToggleButton;

    @FXML
    private ToggleButton doneToggleButton;

    @FXML
    private ToggleButton undoneToggleButton;

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
        registerAsAnEventHandler(this);
    }

    private void addMainPane() {
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(mainPane);
    }

    private void initialPriority() {
        priorityChoiceBox.setItems(FXCollections.observableArrayList(EMPTY, ONE, TWO, THREE));
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

    /**
     * Sets the corresponding text filed or choice box to be focused.
     */
    @Subscribe
    private void handleJumpFilterPanelEvent(JumpToFilterPanelEvent event) {
        Types qualification = event.getAttribute();
        switch (qualification) {
        case DEADLINE:
            deadlineTextField.requestFocus();
            return;
        case START_DATE:
            startDateTextField.requestFocus();
            return;
        case END_DATE:
            endDateTextField.requestFocus();
            return;
        case RECURRING:
            recurringTextField.requestFocus();
            return;
        case PRIORITY_LEVEL:
            priorityChoiceBox.requestFocus();
            priorityChoiceBox.show();
            return;
        case TAG:
            tagsTextField.requestFocus();
            return;
        default:
            assert false;
        }
    }

    /**
     * Handles changes in the filter panel.
     */
    @FXML
    private void handleFilterChanged() {
        Set<Types> types = handleTypesChanged();
        Map<Types, String> qualifications;
        try {
            qualifications = handleQualificationsChanged();
        } catch (IllegalValueException e) {
            indicateInvalidFilter(e);
            return;
        }
        Set<String> tagSet = handleTagsChanged();
        
        raise(new FilterPanelChangedEvent(types, qualifications, tagSet));
        indicateFilterSuccess();
    }

    /**
     * Indicates an invalid filter command caused by a parameter with illegal
     * value.
     * 
     * @param illegalValueException
     */
    private void indicateInvalidFilter(IllegalValueException illegalValueException) {
        logger.info(INVALID_FILTER + illegalValueException.getMessage());
        resultDisplay.setStyleToIndicateIncorrectCommand();
        resultDisplay.postMessage(INVALID_FILTER + illegalValueException.getMessage());
    }

    /**
     * Indicates filter successfully.
     */
    private void indicateFilterSuccess() {
        logger.info("Input in filter panel changed");
        resultDisplay.setStyleToIndicateCorrectCommand();
        resultDisplay.postMessage(SUCCESS_FILTER);
    }

    /**
     * Handles changes in the selection of types.
     * 
     * @return a set of selected types.
     */
    private Set<Types> handleTypesChanged() {
        Set<Types> types = new HashSet<>();
        if (eventsToggleButton.isSelected()) {
            types.add(Types.EVENTS);
        }
        if (tasksToggleButton.isSelected()) {
            types.add(Types.TASKS);
        }
        if (doneToggleButton.isSelected()) {
            types.add(Types.DONE);
        }
        if (undoneToggleButton.isSelected()) {
            types.add(Types.UNDONE);
        }
        return types;
    }

    /**
     * Handles changes in the inputs for qualification
     * 
     * @return a map of qualifications
     * @throws IllegalValueException
     *             if any input is invalid
     */
    private Map<Types, String> handleQualificationsChanged() throws IllegalValueException {
        Map<Types, String> qualifications = new HashMap<>();
        handleDeadlineChanged(qualifications);
        handleStartDateChanged(qualifications);
        handleEndDateChanged(qualifications);
        handleRecurringChanged(qualifications);
        handlePriorityChanged(qualifications);
        return qualifications;
    }

    /*
     * For the following inputs, we assume that they are correct. If the input
     * is invalid, the corresponding text field will be changed accordingly to
     * indicate the error.
     */

    /**
     * Handles changes in the deadline text field.
     * 
     * @throws IllegalValueException
     *             if the given deadline is invalid.
     */
    private void handleDeadlineChanged(Map<Types, String> qualifications) throws IllegalValueException {
        String deadline = parseTextFieldInput(deadlineTextField);
        if (deadline.isEmpty()) {
            return;
        }
        if (deadline.equals(NIL)) {
            qualifications.put(Types.DEADLINE, EMPTY);
        } else {
            try {
                deadline = Deadline.getValidDate(deadline);
                qualifications.put(Types.DEADLINE, deadline);
            } catch (IllegalValueException e) {
                indicateInvalidTextFieldInput(deadlineTextField);
                throw e;
            }
        }
    }

    /**
     * Handles changes in the start date text field.
     * 
     * @throws IllegalValueException
     *             if the given start date is invalid.
     */
    private void handleStartDateChanged(Map<Types, String> qualifications) throws IllegalValueException {
        String startDate = parseTextFieldInput(startDateTextField);
        if (startDate.isEmpty()) {
            return;
        }
        try {
            startDate = EventDate.getValidDate(startDate);
            qualifications.put(Types.START_DATE, startDate);
        } catch (IllegalValueException e) {
            indicateInvalidTextFieldInput(startDateTextField);
            throw e;
        }
    }

    /**
     * Handles changes in the end date text filed.
     * 
     * @throws IllegalValueException
     *             if the given end date is invalid.
     */
    private void handleEndDateChanged(Map<Types, String> qualifications) throws IllegalValueException {
        String endDate = parseTextFieldInput(endDateTextField);
        if (endDate.isEmpty()) {
            return;
        }
        try {
            endDate = EventDate.getValidDate(endDate);
            qualifications.put(Types.END_DATE, endDate);
        } catch (IllegalValueException e) {
            indicateInvalidTextFieldInput(endDateTextField);
            throw e;
        }
    }

    /**
     * Handles changes in the recurring text field.
     * 
     * @throws IllegalValueException
     *             if the given recurring frequency is invalid.
     */
    private void handleRecurringChanged(Map<Types, String> qualifications) throws IllegalValueException {
        String recurring = parseTextFieldInput(recurringTextField);
        if (recurring.isEmpty()) {
            return;
        }
        if (Recurring.isValidFrequency(recurring)) {
            qualifications.put(Types.RECURRING, recurring);
        } else {
            indicateInvalidTextFieldInput(recurringTextField);
            throw new IllegalValueException(Recurring.MESSAGE_RECURRING_CONSTRAINTS);
        }
    }

    /**
     * Parses the input in the given text field and reset its background color
     * to default.
     */
    private String parseTextFieldInput(TextField textField) {
        textField.setStyle(DEFAULT_BACKGROUND);
        return textField.getText().trim();
    }

    /**
     * Indicates the input in the given text field is invalid.
     */
    private void indicateInvalidTextFieldInput(TextField textField) {
        textField.requestFocus();
        textField.setStyle(ERROR_BACKGROUND);
    }

    /**
     * Handles changes in the priority level choice box.
     */
    private void handlePriorityChanged(Map<Types, String> qualifications) {
        String priority = priorityChoiceBox.getSelectionModel().getSelectedItem().toString();
        if (!priority.isEmpty()) {
            qualifications.put(Types.PRIORITY_LEVEL, priority);
        }
    }

    /**
     * Handles changes in the tags text field.
     * 
     * @return a set of tags
     */
    private Set<String> handleTagsChanged() {
        String tags = tagsTextField.getText().trim();
        Set<String> tagSet;
        if (tags.isEmpty()) {
            tagSet = new HashSet<>();
        } else {
            tagSet = new HashSet<>(Arrays.asList(tags.split(SPACE)));
        }
        return tagSet;
    }
    
    /**
     * Updates filter panel so it will correspond to changes in the filtered
     * task list.
     */
    @Subscribe
    private void handleUpdateFilterPanelEvent(UpdateFilterPanelEvent event) {
        reset();
        
        event.getTypes().forEach(type -> updateType(type));
        event.getQualifications().forEach((attribute, keyword) -> updateQualification(attribute, keyword));
        updateTags(event.getTags());
    }

    /**
     * Resets the filter panel.
     */
    private void reset() {
        resetToggleButtons();
        resetTextFields();
        resetChoiceBox();
    }

    /**
     * Resets toggle buttons.
     */
    private void resetToggleButtons() {
        eventsToggleButton.setSelected(false);
        tasksToggleButton.setSelected(false);
        doneToggleButton.setSelected(false);
        undoneToggleButton.setSelected(false);
    }

    /**
     * Resets text fields.
     */
    private void resetTextFields() {
        resetTextFields(deadlineTextField);
        resetTextFields(startDateTextField);
        resetTextFields(endDateTextField);
        resetTextFields(recurringTextField);
        resetTextFields(tagsTextField);
    }

    private void resetTextFields(TextField textField) {
        textField.setStyle(DEFAULT_BACKGROUND);
        textField.setText("");
    }

    /**
     * Resets choice box.
     */
    private void resetChoiceBox() {
        priorityChoiceBox.getSelectionModel().selectFirst();
    }

    /**
     * Updates selected types in the filter panel.
     */
    private void updateType(Types type) {
        switch (type) {
        case EVENTS:
            eventsToggleButton.setSelected(true);
            return;
        case TASKS:
            tasksToggleButton.setSelected(true);
            return;
        case DONE:
            doneToggleButton.setSelected(true);
            return;
        case UNDONE:
            undoneToggleButton.setSelected(true);
            return;
        default:
            assert false;
        }
    }

    /**
     * Updates qualifications inputs in the filter panel.
     */
    private void updateQualification(Types attribute, String keyword) {
        switch (attribute) {
        case DEADLINE:
            deadlineTextField.setText(keyword);
            return;
        case START_DATE:
            startDateTextField.setText(keyword);
            return;
        case END_DATE:
            endDateTextField.setText(keyword);
            return;
        case RECURRING:
            recurringTextField.setText(keyword);
            return;
        case PRIORITY_LEVEL:
            priorityChoiceBox.getSelectionModel().select(Integer.parseInt(keyword));
            return;
        default:
            assert false;
        }
    }

    /**
     * Updates tags inputs in the filter panel.
     */
    private void updateTags(Set<String> tags) {
        String tagString = "";
        for (String tag : tags) {
            tagString += tag + " ";
        }
        tagsTextField.setText(tagString);
    }

}