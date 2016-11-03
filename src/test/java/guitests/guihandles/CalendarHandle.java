package guitests.guihandles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaysFromDisplayedSkin;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import seedu.task.TestApp;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.ReadOnlyTask;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;
import seedu.task.ui.CalendarHelper;

//@@author A0144702N
public class CalendarHandle extends GuiHandle {
	
	private static final String PANE_ID = "#calendar";
	private final CalendarHelper calHelper;

	public CalendarHandle(GuiRobot guiRobot, Stage primaryStage) {
		super(guiRobot, primaryStage, TestApp.APP_TITLE);
		calHelper = CalendarHelper.getInstance();
	}
	
	public List<Appointment> getAppoinments() {
		Agenda agenda = getAgenda();
		return agenda.appointments();
	}

	public Agenda getAgenda() {
		return (Agenda) getNode(PANE_ID);
	}
	
	public boolean isCalendarTaskMatching(ReadOnlyTask... tasks) {
		return this.isCalendarTaskMatching(0, tasks);
	}

	public boolean isCalendarEventsMatching(ReadOnlyEvent... events) {
		return this.isCalendarEventMatching(0, events);
	}
	
	private boolean isCalendarTaskMatching(int startPosition, ReadOnlyTask[] tasks) {
		if(tasks.length + startPosition != getAppoinmentsTask().size()) {
			throw new IllegalArgumentException("Calendar size mismatched\n" + "Expected" 
		+ (getAppoinmentsTask().size()-1) + "events\n" + "But was : " + tasks.length);
		}
		
		return (this.containsAllTask(startPosition, tasks));
	}

	

	private List<Appointment> getAppoinmentsTask() {
		Agenda agenda = getAgenda();
		return agenda.appointments().stream()
		.filter((Predicate<? super Agenda.Appointment>) appointment -> calHelper.isTask(appointment))
		.collect(Collectors.toList());
	}
	
	
	private List<Appointment> getAppoinmentsEvent() {
		Agenda agenda = getAgenda();
		return agenda.appointments().stream()
		.filter((Predicate<? super Agenda.Appointment>) appointment -> calHelper.isEvent(appointment))
		.collect(Collectors.toList());
	}

	private boolean isCalendarEventMatching(int startPosition, ReadOnlyEvent[] events) {
		if(events.length + startPosition != getAppoinmentsEvent().size()) {
			throw new IllegalArgumentException("Calendar size mismatched\n" + "Expected" 
		+ (getAppoinmentsEvent().size()-1) + "events\n" + "But was : " + events.length);
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
	
	private boolean containsAllTask(int startPosition, ReadOnlyTask[] tasks) {
		List<Appointment> tasksInCal = getAppoinmentsTask();
		
		if(tasksInCal.size() < startPosition+tasks.length) {
			return false;
		}
		
		//check each event in the list
		for(int i = 0; i<tasks.length; i++) {
			if(!isSameTask(tasksInCal.get(i), tasks[i])) {
				throw new IllegalArgumentException("was: " + tasksInCal.get(i).toString()+ " expected: "+ tasks[i].toString());
				}
			}
		return true;
	}

	private boolean isSameTask(Appointment appointment, ReadOnlyTask task) {
		return appointment.getSummary().equals(task.getTask().fullName)
				&& appointment.getStartLocalDateTime().format(StringUtil.DATE_FORMATTER)
				.equals(task.getDeadline().get().getTime().format(StringUtil.DATE_FORMATTER))
				&& appointment.getDescription().equals(task.getDescriptionValue());
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
		Slider slider = (Slider)agenda.lookup("#daysAfterSlider");
		slider.setValue(3.0);
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
