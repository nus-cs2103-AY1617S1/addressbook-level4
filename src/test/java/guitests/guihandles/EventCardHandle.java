package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.Tdoo.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
// @@author A0132157M
public class EventCardHandle extends GuiHandle {
	private static final String NAME_FIELD_ID = "#name";
	private static final String STARTDATE_FIELD_ID = "#startDate";
	private static final String ENDDATE_FIELD_ID = "#endDate";
	private static final String ST_FIELD_ID = "#startTime";
	private static final String ENDTIME_FIELD_ID = "#endTime";
	private static final String DONE_FIELD_ID = "#isDone";

	private Node node;

	public EventCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
		super(guiRobot, primaryStage, null);
		this.node = node;
	}

	protected String getTextFromLabel(String fieldId) {
		return getTextFromLabel(fieldId, node);
	}

	public String getName() {
		return getTextFromLabel(NAME_FIELD_ID);
	}

	public String getStartTime() {
		return getTextFromLabel(ST_FIELD_ID);
	}

	public String getStartDate() {
		return getTextFromLabel(STARTDATE_FIELD_ID);
	}

	public String getEndDate() {
		return getTextFromLabel(ENDDATE_FIELD_ID);
	}

	public String getEndTime() {
		return getTextFromLabel(ENDTIME_FIELD_ID);
	}

	public String getDone() {
		return getTextFromLabel(DONE_FIELD_ID);
	}

	public boolean isSameEvent(ReadOnlyTask task) {
		return getName().equals(task.getName().name);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EventCardHandle) {
			EventCardHandle handle = (EventCardHandle) obj;
			return getName().equals(handle.getName()) && getStartDate().equals(handle.getStartDate())
					&& getEndDate().equals(handle.getEndDate()) && getStartTime().equals(handle.getStartTime())
					&& getEndTime().equals(handle.getEndTime()) && getDone().equals(handle.getDone());
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return getName() + " " + getStartDate() + " " + getEndDate() + " " + getStartTime() + " " + getEndTime() + " "
				+ getDone();
	}
}
