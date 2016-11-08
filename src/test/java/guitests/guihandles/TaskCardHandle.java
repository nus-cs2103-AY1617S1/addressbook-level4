package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.dailyplanner.model.task.ReadOnlyTask;
//@@author A0140124B
/**
 * Provides a handle to a person card in the person list panel.
 */
public class TaskCardHandle extends GuiHandle {
	private static final String NAME_FIELD_ID = "#name";
	private static final String START_DATE_FIELD_ID = "#startDate";
	private static final String START_TIME_FIELD_ID = "#startTime";
	private static final String END_DATE_FIELD_ID = "#endDate";
	private static final String END_TIME_FIELD_ID = "#endTime";
	private static final String COMPLETED_FIELD_ID = "#isComplete";

	private Node node;

	public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
		super(guiRobot, primaryStage, null);
		this.node = node;
	}

	protected String getTextFromLabel(String fieldId) {
		return getTextFromLabel(fieldId, node);
	}

	public String getTaskName() {
		return getTextFromLabel(NAME_FIELD_ID);
	}

	public String getStartDate() {
		return getTextFromLabel(START_DATE_FIELD_ID);
	}

	public String getStartTime() {
		return getTextFromLabel(START_TIME_FIELD_ID);
	}

	public String getEndDate() {
		return getTextFromLabel(END_DATE_FIELD_ID);
	}

	public String getEndTime() {
		return getTextFromLabel(END_TIME_FIELD_ID);
	}

	public String getCompletion() {
		return getTextFromLabel(COMPLETED_FIELD_ID);
	}

	public boolean isSamePerson(ReadOnlyTask person) {

		return getTaskName().equals(person.getName());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TaskCardHandle) {
			TaskCardHandle handle = (TaskCardHandle) obj;
			return getTaskName().equals(handle.getTaskName()) && getStartDate().equals(handle.getStartDate()); // TODO:
																												// compare
																												// the
																												// rest
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return getTaskName() + " start: " + getStartDate() + " " + getStartTime() + " end: " + getEndDate() + " "
				+ getEndTime();
	}
}
