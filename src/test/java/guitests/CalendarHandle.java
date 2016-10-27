package guitests;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import guitests.guihandles.GuiHandle;
import javafx.stage.Stage;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaysFromDisplayedSkin;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import seedu.task.TestApp;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.testutil.TestUtil;
import seedu.task.ui.CalendarHelper;

//@@author A0144702N
public class CalendarHandle extends GuiHandle {
	
	private static final String PANE_ID = "#calendar";

	public CalendarHandle(GuiRobot guiRobot, Stage primaryStage) {
		super(guiRobot, primaryStage, TestApp.APP_TITLE);
	}
	
	public List<Appointment> getAppoinments() {
		Agenda agenda = getAgenda();
		return agenda.appointments();
	}

	public Agenda getAgenda() {
		return (Agenda) getNode(PANE_ID);
	}
	
	public boolean isCalendarMatching(ReadOnlyEvent... events) {
		return this.isCalendarMatching(0, events);
	}

	private boolean isCalendarMatching(int startPosition, ReadOnlyEvent[] events) {
		if(events.length + startPosition != getAppoinments().size()) {
			throw new IllegalArgumentException("Calendar size mismatched\n" + "Expected" 
		+ (getAppoinments().size()-1) + "events\n" + "But was : " + events.length);
		}
		
		return (this.containsAll(startPosition, events));
	}

	private boolean containsAll(int startPosition, ReadOnlyEvent[] events) {
		List<Appointment> eventsInCal = getAppoinments();
		
		//check on the length 
		if(eventsInCal.size() < startPosition+ events.length) {
			return false;
		}
		
		//check each event in the list
		for(int i = 0; i<events.length; i++) {
			if(!isSameEvent(eventsInCal.get(i), events[i])) {
				throw new IllegalArgumentException("was: " + eventsInCal.get(i).toString()+ " expected: "+ events[i].toString());
			}
		}
		return true;
	}

	public boolean isSameEvent(Appointment appointment, ReadOnlyEvent event) {
		return appointment.getSummary().equals(event.getEvent().fullName)
				&& appointment.getDescription().equals(event.getDescriptionValue())
				&& appointment.getStartLocalDateTime().format(StringUtil.DATE_FORMATTER)
				.equals(event.getDuration().getStartTime().format(StringUtil.DATE_FORMATTER))
				&& appointment.getEndLocalDateTime().format(StringUtil.DATE_FORMATTER)
				.equals(event.getDuration().getEndTime().format(StringUtil.DATE_FORMATTER));
		
	}

	public Agenda getAgendaOfDay() throws Exception {
		TestUtil.initRuntime();
		Agenda agenda = new Agenda();
		agenda.setSkin(new AgendaDaySkin(agenda));
		TestUtil.tearDownRuntime();
		
		return agenda;
	}
	
	public Agenda getAgendaOfWeek() throws Exception {
		TestUtil.initRuntime();
		Agenda agenda = new Agenda();
		agenda.setSkin(new AgendaDaysFromDisplayedSkin(agenda));
		TestUtil.tearDownRuntime();
		
		return agenda;
	}

	public Agenda getAgendaOfDateTime(LocalDateTime time) throws Exception {
		TestUtil.initRuntime();
		Agenda agenda = new Agenda();
		agenda.setDisplayedLocalDateTime(time);
		TestUtil.tearDownRuntime();
		
		return agenda;
	}
}
