package guitests.guihandles;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.task.EndTime;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.StartTime;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends GuiHandle {
	private static final String NAME_FIELD_ID = "#name";
	private static final String ADDRESS_FIELD_ID = "#priority";
	private static final String PHONE_FIELD_ID = "#startTime";
	private static final String EMAIL_FIELD_ID = "#endTime";

	private Node node;

	public PersonCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
		super(guiRobot, primaryStage, null);
		this.node = node;
	}

	protected String getTextFromLabel(String fieldId) {
		return getTextFromLabel(fieldId, node);
	}

	public String getFullName() {
		return getTextFromLabel(NAME_FIELD_ID);
	}

	public String getPriority() {
		Pattern pattern = Pattern.compile(".*(?<priority>(low|med|high)).*");
		Matcher matcher = pattern.matcher(getTextFromLabel(ADDRESS_FIELD_ID));
		if(!matcher.matches())
			assert false;
		return matcher.group("priority");
	}

	public String getStartTime() {
		return getTextFromLabel(PHONE_FIELD_ID);
	}

	public String getEndTime() {
		return getTextFromLabel(EMAIL_FIELD_ID);
	}
	//@@author A0146107M
	public boolean isSameTask(ReadOnlyTask task){
		String start = getStartTime().equals("-")?"":getStartTime();
		String end = getEndTime().equals("-")?"":getEndTime();
		try {
			return getFullName().equals(task.getTaskDetails().toString()) && task.getStartTime().equals(new StartTime(start))
					&& task.getEndTime().equals(new EndTime(end)) && task.getPriority().equals(new Priority(getPriority()));
		} catch (IllegalValueException e) {
			assert false;
			return false;
		}
	}
	//@@author
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PersonCardHandle) {
			PersonCardHandle handle = (PersonCardHandle) obj;
			return getFullName().equals(handle.getFullName())
					&& getPriority().equals(handle.getPriority()); //TODO: compare the rest
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(getFullName()+ "\n")
		.append(getStartTime() + "\t")
		.append(getEndTime()+ "\n")
		.append(getPriority()+ "\n");
		//        getTags().forEach(builder::append);
		return builder.toString();

	}
}
