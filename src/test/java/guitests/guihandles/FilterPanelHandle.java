package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.address.TestApp;

/**
 * A handle to the Filter Panel in the GUI.
 */
public class FilterPanelHandle extends GuiHandle{

    private static final String EVENT_INPUT_FIELD_ID = "#eventsToggleButton";
    private static final String TASKS_INPUT_FIELD_ID = "#tasksToggleButton";
    private static final String DONE_INPUT_FIELD_ID = "#doneToggleButton";
    private static final String UNDONE_INPUT_FIELD_ID = "#undoneToggleButton";
    private static final String DEADLINE_INPUT_FIELD_ID = "#deadlineTextField";
    private static final String START_DATE_INPUT_FIELD_ID = "#startDateTextField";
    private static final String END_DATE_INPUT_FIELD_ID = "#endDateTextField";
    private static final String RECURRING_INPUT_FIELD_ID = "#recurringTextField";
    private static final String TAG_INPUT_FIELD_ID = "#tagsTextField";
    private static final String PRIORITY_INPUT_FIELD_ID = "#priorityChoiceBox";

    public FilterPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }
    
    /**
     * Get the given inputs in the Filter Panel
     */
    public boolean getEventInput() {
        return getToggleButtonInput(EVENT_INPUT_FIELD_ID);
    }
    
    public String getDeadlineInput() {
        return getTextFieldText(DEADLINE_INPUT_FIELD_ID);
    }
    
    public String getPriorityInput() {
        return getPriorityInput(PRIORITY_INPUT_FIELD_ID);
    }
    
    /**
     * Enter the given commands in the Filter Panel
     */
    public void runCommandForEvent() {
        enterCommandForEvents();
        guiRobot.sleep(200); //Give time for the command to take effect
    }
    
    public void runCommandForTask() {
        enterCommandForTasks();
        guiRobot.sleep(200);
    }
    
    public void runCommandForDone() {
        enterCommandForDone();
        guiRobot.sleep(200);
    }
    
    public void runCommandForUndone() {
        enterCommandForUndone();
        guiRobot.sleep(200);
    }
    
    public void runCommandForDeadline(String command) {
        enterCommandForDeadline(command);
        pressEnter();
        guiRobot.sleep(200);
    }
    
    public void runCommandForStartDate(String command) {
        enterCommandForStartDate(command);
        pressEnter();
        guiRobot.sleep(200); 
    }
    
    public void runCommandForEndDate(String command) {
        enterCommandForEndDate(command);
        pressEnter();
        guiRobot.sleep(200); 
    }
    
    public void runCommandForRecurring(String command) {
        enterCommandForRecurring(command);
        pressEnter();
        guiRobot.sleep(200); 
    }
    
    public void runCommandForTag(String command) {
        enterCommandForTag(command);
        pressEnter();
        guiRobot.sleep(200); 
    }
    
    public void runCommandForPriority(String command) {
        enterCommandForPriority(command);
        guiRobot.sleep(200); 
    }
    
    /**
     * Check the given fields in the Filter Panel are focused
     */
    public boolean deadlineIsFocused() {
        return isFocused(DEADLINE_INPUT_FIELD_ID);
    }
    
    public boolean startDateIsFocused() {
        return isFocused(START_DATE_INPUT_FIELD_ID);
    }
    
    public boolean endDateIsFocused() {
        return isFocused(END_DATE_INPUT_FIELD_ID);
    }
    
    public boolean recurringIsFocused() {
        return isFocused(RECURRING_INPUT_FIELD_ID);
    }
    
    public boolean tagIsFocused() {
        return isFocused(TAG_INPUT_FIELD_ID);
    }
    
    public boolean priorityIsFocused() {
        return choiceBoxIsFocused(PRIORITY_INPUT_FIELD_ID);
    }
    
    /**
     * For toggle buttons, enter command by click button
     */
    private void enterCommandForEvents() {
        clickToggleButton(EVENT_INPUT_FIELD_ID);
    }
    
    private void enterCommandForTasks() {
        clickToggleButton(TASKS_INPUT_FIELD_ID);
    }
    
    private void enterCommandForDone() {
        clickToggleButton(DONE_INPUT_FIELD_ID);
    }
    
    private void enterCommandForUndone() {
        clickToggleButton(UNDONE_INPUT_FIELD_ID);
    }

    /**
     * For text fields, enter command by set text field
     */
    private void enterCommandForDeadline(String command) {
        setTextField(DEADLINE_INPUT_FIELD_ID, command);
    }
    
    private void enterCommandForStartDate(String command) {
        setTextField(START_DATE_INPUT_FIELD_ID, command);
    }
    
    private void enterCommandForEndDate(String command) {
        setTextField(END_DATE_INPUT_FIELD_ID, command);
    }
    
    private void enterCommandForRecurring(String command) {
        setTextField(RECURRING_INPUT_FIELD_ID, command);
    }
    
    private void enterCommandForTag(String command) {
        setTextField(TAG_INPUT_FIELD_ID, command);
    }
    
    private void enterCommandForPriority(String command) {
        chooseChoiceBox(PRIORITY_INPUT_FIELD_ID, command);
    }
    
}
