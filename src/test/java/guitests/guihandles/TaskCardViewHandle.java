package guitests.guihandles;

import com.google.common.base.Optional;
import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.todo.commons.util.TimeUtil;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.testutil.UiTestUtil;
import seedu.todo.ui.view.TaskCardView;
import seedu.todo.ui.view.TodoListView;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

//@@author A0135805H
/**
 * Provides a handle to a {@link TaskCardView}
 * that exists in {@link TodoListView}
 */
public class TaskCardViewHandle extends GuiHandle {

    /* Constants */
    private static final String TITLE_LABEL_ID = "#titleLabel";
    private static final String DESCRIPTION_LABEL_ID = "#descriptionLabel";
    private static final String DATE_LABEL_ID = "#dateLabel";
    private static final String LOCATION_LABEL_ID = "#locationLabel";

    private static final String DESCRIPTION_BOX_ID = "#descriptionBox";
    private static final String DATE_BOX_ID = "#dateBox";
    private static final String LOCATION_BOX_ID = "#locationBox";

    private static final String PIN_IMAGE_ID = "#pinImage";

    private static final String TYPE_LABEL_ID = "#typeLabel";
    private static final String MOREINFO_LABEL_ID = "#moreInfoLabel";

    /* Variables */
    private Node rootNode;

    /**
     * Constructs a handle for {@link TaskCardView}.
     *
     * @param guiRobot The GUI test robot.
     * @param primaryStage The main stage that is executed from the application's UI.
     * @param rootNode Node that houses the contents of this Task Card.
     */
    public TaskCardViewHandle(GuiRobot guiRobot, Stage primaryStage, Node rootNode){
        super(guiRobot, primaryStage, null);
        this.rootNode = rootNode;
    }

    /* Task Property Getters */
    public String getDisplayedTitle() {
        return getTextFromLabel(TITLE_LABEL_ID);
    }

    public String getDisplayedDescription() {
        return getTextFromLabel(DESCRIPTION_LABEL_ID);
    }

    public String getDisplayedDateText() {
        return getTextFromLabel(DATE_LABEL_ID);
    }

    public String getDisplayedLocation() {
        return getTextFromLabel(LOCATION_LABEL_ID);
    }

    public String getDisplayedTypeLabel() {
        return getTextFromLabel(TYPE_LABEL_ID);
    }

    public boolean getMoreInfoLabelVisibility() {
        Node moreInfoLabel = getNode(MOREINFO_LABEL_ID);
        return UiTestUtil.isDisplayed(moreInfoLabel);
    }

    public boolean getDescriptionBoxVisibility() {
        Node descriptionBox = getNode(DESCRIPTION_BOX_ID);
        return UiTestUtil.isDisplayed(descriptionBox);
    }

    public boolean getDateBoxVisibility() {
        Node dateBox = getNode(DATE_BOX_ID);
        return UiTestUtil.isDisplayed(dateBox);
    }

    public boolean getLocationBoxVisibility() {
        Node locationBox = getNode(LOCATION_BOX_ID);
        return UiTestUtil.isDisplayed(locationBox);
    }

    public boolean getPinImageVisibility() {
        Node pinImage = getNode(PIN_IMAGE_ID);
        return UiTestUtil.isDisplayed(pinImage);
    }

    public boolean isSelectedStyleApplied() {
        return UiTestUtil.containsStyleClass(rootNode, "selected");
    }

    public boolean isCompletedStyleApplied() {
        return UiTestUtil.containsStyleClass(rootNode, "completed");
    }

    public boolean isOverdueStyleApplied() {
        return UiTestUtil.containsStyleClass(rootNode, "overdue");
    }

    public boolean isTaskCardCollapsed() {
        return UiTestUtil.containsStyleClass(rootNode, "collapsed");
    }

    public boolean isTaskCollapsible() {
        return !getDisplayedDescription().isEmpty();
    }

    /* General Methods */
    /**
     * Checks if the supplied index matches to what this node displays.
     * To be used for nodes filtering.
     */
    public boolean matchesTask(int displayedIndex) {
        return getDisplayedTitle().startsWith(displayedIndex + ". ");
    }

    /**
     * Given an {@link ImmutableTask} and a displayed index, check if this view is displayed correctly.
     * @param displayedIndex Index displayed in the view.
     * @param task Task displayed in the view.
     * @return Returns true only if and only if the elements in this view is displayed correctly.
     * TODO: Maybe we do not need this at all.
     */
    public boolean isDisplayedCorrectly(int displayedIndex, ImmutableTask task) {
        //JUnit Assertion Test: To know which test are failing in detail
        assertTrue(isTitleCorrect(displayedIndex, task));
        assertTrue(isDescriptionCorrect(task));
        assertTrue(isTaskCardCollapsedStateCorrect());
        assertTrue(isCompletedDisplayCorrect(task));
        assertTrue(isDateTextCorrect(task));
        assertTrue(isLocationCorrect(task));
        assertTrue(isTypeDisplayCorrect(task));
        assertTrue(isPinDisplayCorrect(task));
        assertTrue(isOverdueDisplayCorrect(task));
        return true;
    }

    private boolean isTitleCorrect(int displayedIndex, ImmutableTask task) {
        String expected = convertToDisplayedTitle(displayedIndex, task.getTitle());
        String actual = getDisplayedTitle();
        return expected.equals(actual);
    }

    private boolean isDescriptionCorrect(ImmutableTask task) {
        java.util.Optional<String> description = task.getDescription();

        if (description.isPresent()) {
            //If there is a task description, it should match with the displayed description.
            String expected = description.get();
            String actual = getDisplayedDescription();
            return expected.equals(actual);
        } else {
            //Description should be hidden when there is no description.
            boolean expected = false;
            boolean actual = getDescriptionBoxVisibility();
            return expected == actual;
        }
    }

    private boolean isDateTextCorrect(ImmutableTask task) {
        java.util.Optional<LocalDateTime> startTime = task.getStartTime();
        java.util.Optional<LocalDateTime> endTime = task.getEndTime();

        TimeUtil timeUtil = new TimeUtil();

        String displayedDateText = getDisplayedDateText();
        String expectedDateText;

        if (!startTime.isPresent() && !endTime.isPresent()) {
            //Date box should be hidden when there is no start and end time.
            return !getDateBoxVisibility();
        } else if (startTime.isPresent() && endTime.isPresent()) {
            //When start and end date are available, expect event format
            expectedDateText = timeUtil.getEventTimeText(startTime.get(), endTime.get());
        } else if (endTime.isPresent()) {
            //When only end time is present, expect deadline format
            expectedDateText = timeUtil.getTaskDeadlineText(endTime.get());
        } else {
            //Otherwise, illegal date state.
            throw new IllegalStateException("Start time is present, but end time is not.");
        }
        return expectedDateText.equals(displayedDateText);
    }

    private boolean isLocationCorrect(ImmutableTask task) {
        java.util.Optional<String> location = task.getLocation();

        if (location.isPresent()) {
            //If there is a location, it should match with the displayed location.
            String expected = location.get();
            String actual = getDisplayedLocation();
            return expected.equals(actual);
        } else {
            //Description should be hidden when there is no description.
            boolean expected = false;
            boolean actual = getLocationBoxVisibility();
            return expected == actual;
        }
    }

    private boolean isPinDisplayCorrect(ImmutableTask task) {
        boolean expected = task.isPinned();
        boolean actual = getPinImageVisibility();
        return expected == actual;
    }

    private boolean isCompletedDisplayCorrect(ImmutableTask task) {
        boolean expected = task.isCompleted();
        boolean actual = isCompletedStyleApplied();
        return expected == actual;
    }

    private boolean isOverdueDisplayCorrect(ImmutableTask task) {
        java.util.Optional<LocalDateTime> endTime = task.getEndTime();
        boolean actual = isOverdueStyleApplied();
        boolean expected;

        if (endTime.isPresent() && !task.isEvent()) {
            expected = seedu.todo.testutil.TimeUtil.isOverdue(endTime.get());
        } else {
            expected = false;
        }
        return expected == actual;
    }

    private boolean isTypeDisplayCorrect(ImmutableTask task) {
        String actual = getDisplayedTypeLabel();
        String expected;

        if (task.isEvent()) {
            expected = "Event";
        } else {
            expected = "Task";
        }

        return expected.equals(actual);
    }

    public boolean isTaskCardCollapsedStateCorrect() {
        boolean collapsedStyleApplied = UiTestUtil.containsStyleClass(rootNode, "collapsed");
        boolean moreInfoLabelDisplayed = UiTestUtil.isDisplayed(getNode(MOREINFO_LABEL_ID));

        if (isTaskCollapsible()) {
            return collapsedStyleApplied == moreInfoLabelDisplayed;
        } else {
            return !moreInfoLabelDisplayed;
        }
    }

    /* View Elements Helper Methods */
    //@@author A0135805H-reused
    /**
     * Search and returns exactly one matching node.
     *
     * @param fieldId Field ID to search inside the parent node.
     * @return Returns one appropriate node that matches the {@code fieldId}.
     * @throws NullPointerException when no node with {@code fieldId} can be found, intentionally breaking
     *         the tests.
     */
    @Override
    protected Node getNode(String fieldId) throws NullPointerException {
        Optional<Node> node = guiRobot.from(rootNode).lookup(fieldId).tryQuery();
        if (node.isPresent()) {
            return node.get();
        } else {
            throw new NullPointerException("Node " + fieldId + " is not found.");
        }
    }

    //@@author A0135805H
    /**
     * Gets a text from the node with {@code fieldId}.
     *
     * @param fieldId To get the node's text from.
     * @return Returns the text presented in the node.
     */
    private String getTextFromLabel(String fieldId) {
        return ((Label) getNode(fieldId)).getText();
    }

    /**
     * Converts {@code actualTitle} to a displayed title with the relevant {@code displayedIndex}.
     * @param displayedIndex The index that is shown on the title displayed to the user.
     * @param actualTitle The actual title of the task.
     * @return Returns a title text that is actually displayed to the user.
     */
    private String convertToDisplayedTitle(int displayedIndex, String actualTitle) {
        return displayedIndex + ". " + actualTitle;
    }

    //@@author A0135805H=reused
    /* Override Methods */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardViewHandle) {
            TaskCardViewHandle handle = (TaskCardViewHandle) obj;

            boolean hasEqualTitle = this.getDisplayedTitle()
                    .equals(handle.getDisplayedTitle());
            boolean hasEqualDescription = this.getDisplayedDescription()
                    .equals(handle.getDisplayedDescription());
            boolean hasEqualDateText = this.getDisplayedDateText()
                    .equals(handle.getDisplayedDateText());
            boolean hasEqualLocation = this.getDisplayedLocation()
                    .equals(handle.getDisplayedLocation());
            boolean hasEqualType = this.getDisplayedTypeLabel()
                    .equals(handle.getDisplayedTypeLabel());
            boolean hasEqualMoreInfoVisibility = this.getMoreInfoLabelVisibility()
                    == handle.getMoreInfoLabelVisibility();
            boolean hasEqualDescriptionBoxVisibility = this.getDescriptionBoxVisibility()
                    == handle.getDescriptionBoxVisibility();
            boolean hasEqualDateBoxVisibility = this.getDateBoxVisibility()
                    == handle.getDateBoxVisibility();
            boolean hasEqualLocationBoxVisibility = this.getLocationBoxVisibility()
                    == handle.getLocationBoxVisibility();
            boolean hasEqualPinImageVisibility = this.getPinImageVisibility()
                    == handle.getPinImageVisibility();
            boolean hasEqualSelectedStyleApplied = this.isSelectedStyleApplied()
                    == handle.isSelectedStyleApplied();
            boolean hasEqualCompletedStyleApplied = this.isCompletedStyleApplied()
                    == handle.isCompletedStyleApplied();
            boolean hasEqualOverdueStyleApplied = this.isOverdueStyleApplied()
                    == handle.isOverdueStyleApplied();

            return hasEqualTitle && hasEqualDescription && hasEqualDateText
                    && hasEqualLocation && hasEqualType && hasEqualMoreInfoVisibility
                    && hasEqualDescriptionBoxVisibility && hasEqualDateBoxVisibility
                    && hasEqualLocationBoxVisibility && hasEqualPinImageVisibility
                    && hasEqualSelectedStyleApplied && hasEqualCompletedStyleApplied
                    && hasEqualOverdueStyleApplied;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getDisplayedTitle() + " " + getDisplayedDescription();
    }
}
