package seedu.task.testutil;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.task.model.item.Description;
import seedu.task.model.item.EventDuration;
import seedu.task.model.item.Name;
import seedu.task.model.item.ReadOnlyEvent;

//@@author A0144702N

public class TestEvent implements ReadOnlyEvent {
	private Name name;
	private Optional <Description> description;
	private EventDuration eventDuration;
	
	public void setName(Name name) {
		this.name = name;
	}
	
	public void setDescription(Description desc) {
		this.description = Optional.of(desc);
	}
	
	public void setEventDuration(EventDuration dur) {
		this.eventDuration = dur;
	}
	
	@Override
	public Name getEvent() {
		return this.name;
	}

	@Override
	public EventDuration getDuration() {
		return this.eventDuration;
	}

	@Override
	public boolean isEventCompleted() {
		return !(this.eventDuration.getEndTime().isAfter(LocalDateTime.now()));
	}	
	
	@Override
	public String toString() {
		return getAsText();
	}
	
	public String getListEventCommand() {
		return "list -e";
	}
	
	public String getListAllEventCommand() {
		return "list -e -a";
	}
	
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getEvent().fullName + " ");
        sb.append("/desc " + this.getDescriptionValue() + " ");
        sb.append("/from " + this.getDuration().getStartTimeAsText());
        sb.append(" /to " + this.getDuration().getEndTimeAsText());
        return sb.toString();
    }

	public String getEditCommand(int i) {
		StringBuilder sb = new StringBuilder();
		sb.append("edit /e " + i + " ");
		sb.append("/name " + this.getEvent().fullName+ " ");
		sb.append("/desc "+ this.getDescriptionValue() +  " ");
		sb.append("/from " + this.getDuration().getStartTimeAsText());
		sb.append(" /to " + this.getDuration().getEndTimeAsText());
		return sb.toString();
	}

    @Override
    public Optional<Description> getDescription() {
        return this.description;
    }	

}
