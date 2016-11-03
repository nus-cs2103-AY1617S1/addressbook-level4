package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import javafx.scene.control.Skin;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaysFromDisplayedSkin;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import seedu.task.commons.util.StringUtil;
import seedu.task.logic.commands.CalendarCommand;
import seedu.task.testutil.TestEvent;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;
import seedu.task.testutil.TypicalTestEvents;
import seedu.task.testutil.TypicalTestTasks;
import seedu.task.ui.CalendarHelper;

//@@author A0144702N
public class CalendarTest extends TaskBookGuiTest {
	
	/*
	 * Use cases need to be covered:
	 * 	1. Update event addition/deletion/edition
	 * 	2. Response to select command
	 * 	3. Response to undo command.
	 * 	4. Response to show command
	 */
	
	
	@Test
	public void initAsWeekView() {
		Agenda agenda = calendar.getAgenda();
		TestEvent[] currentEventList = te.getTypicalAllEvents();
		TestTask[] currentTaskList = td.getTypicalTasks();
		calendarViewMatch(agenda, new AgendaDaysFromDisplayedSkin(new Agenda()));
		calendarListMatch(currentEventList, currentTaskList);
	}
	

	@Test 
	public void switch_viewType() throws Exception {
		Agenda agendaDayView = calendar.getAgendaOfDay();
		Agenda agendaWeekView = calendar.getAgendaOfWeek();
		
		//restore to main app
		FxToolkit.setupApplication(testApp.getClass(), getDataFileLocation());
		
		assertTrue(calendarViewMatch("show now /day", agendaDayView));
		assertTrue(calendarViewMatch("show now /wk", agendaWeekView));
		//default is week view
		assertTrue(calendarViewMatch("show now", agendaWeekView));
		
		//mismached of views.
		assertFalse(calendarViewMatch("show now /day", agendaWeekView));
		assertFalse(calendarViewMatch("show now /wk", agendaDayView));
	}
	
	@Test
	public void show_invalidArgs() {
		//empty arg not allowed
		commandBox.runCommand("show");
		assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarCommand.MESSAGE_USAGE));
		
		commandBox.runCommand("show haha /day");
		assertResultMessage(StringUtil.TIME_CONSTRAINTS);
	}
	
	@Test
	public void switch_displayedDateTime() throws Exception {
		LocalDateTime time1 = LocalDateTime.now();
		LocalDateTime time2 = LocalDateTime.now().plusWeeks(1);
		Agenda target1 = calendar.getAgendaOfDateTime(time1); 
		Agenda target2 = calendar.getAgendaOfDateTime(time2);
		
		//restore to main app
		FxToolkit.setupApplication(testApp.getClass(), getDataFileLocation());
		
		assertTrue(calendarDisplayedDateTimeMatch("show now /wk", target1));
		assertTrue(calendarDisplayedDateTimeMatch("show next week /day", target2));
		
		assertFalse(calendarDisplayedDateTimeMatch("show now /day", target2));
		assertFalse(calendarDisplayedDateTimeMatch("show next week /wk", target1));
	}
	
	@Test
	public void modifyEventsList_shouldSync() {
		//set up 
		TestEvent[] currentEventList = te.getTypicalAllEvents();
		TestTask[] currentTaskList = td.getTypicalTasks();
		Agenda agenda = calendar.getAgenda();
		calendarListMatch(currentEventList, currentTaskList);
		
		
		//add an event
		currentEventList = TestUtil.addEventsToList(currentEventList, TypicalTestEvents.addedEvent); /* Add to end since it is not sorted in the end */
		commandBox.runCommand(TypicalTestEvents.addedEvent.getAddCommand());
		calendarListMatch(currentEventList, currentTaskList);
		
		//delete an event
		currentEventList= TestUtil.removeEventFromList(currentEventList, 4);
		commandBox.runCommand("delete /e 1");
		calendarListMatch(currentEventList, currentTaskList);
		
		//edit an event
		currentEventList = TestUtil.editEventsToList(currentEventList, 1, TypicalTestEvents.addedEvent);
		commandBox.runCommand(TypicalTestEvents.addedEvent.getEditCommand(1));
		calendarListMatch(currentEventList, currentTaskList);
		
		//undo
		currentEventList = TestUtil.editEventsToList(currentEventList, 1, TypicalTestEvents.meeting2);
		commandBox.runCommand("undo");
		calendarListMatch(currentEventList, currentTaskList);
	}
	
	@Test
	public void select_shouldSync() {
		TestEvent[] currentEventList = te.getTypicalAllEvents();
		TestTask[] currentTaskList = td.getTypicalTasks();
		calendarListMatch(currentEventList, currentTaskList);
		
		//select a event
		commandBox.runCommand("select /e 1");
		
		//calendar size should not change
		calendarListMatch(currentEventList, currentTaskList);
		calendarSelectedCorrectTask(TypicalTestEvents.meeting2);
	}
	

	/****************************** Helper Methods ***************************/




	private boolean calendarViewMatch(String command, Agenda expectedAgenda) {
		commandBox.runCommand(command);
		
		return calendarViewMatch(calendar.getAgenda(),expectedAgenda.getSkin());
	}

	private boolean calendarDisplayedDateTimeMatch(String command, Agenda expectedAgenda) {
		commandBox.runCommand(command);
		
		return calendarDisplayedDateTimeMatch(calendar.getAgenda().getDisplayedLocalDateTime(),
				expectedAgenda.getDisplayedLocalDateTime());
	}
	
	/**
	 * Compare two LocalDateTime with 2 minutes allowed as buffer.
	 * @param testTime
	 * @param expectedTime
	 * @return
	 */
	private boolean calendarDisplayedDateTimeMatch(LocalDateTime testTime, LocalDateTime expectedTime) {
		return testTime.isAfter(expectedTime.minusMinutes(1)) && testTime.isBefore(expectedTime.plusMinutes(1));
	}
	
	private boolean calendarViewMatch(Agenda agenda, Skin skin) {
		return (agenda.getSkin().getClass().getName().equals(skin.getClass().getName()));
	}
	
	private void calendarListMatch(TestEvent[] eventList, TestTask[] taskList) {
		assertTrue(calendar.isCalendarEventsMatching(eventList) && calendar.isCalendarTaskMatching(taskList)) ;
	}

	
	private void calendarSelectedCorrectTask(TestEvent event) {
		assertTrue(calendarDisplayedDateTimeMatch(calendar.getAgenda().getDisplayedLocalDateTime(), event.getDuration().getStartTime()));
		assertTrue(calendarHighlightedEvent(calendar.getAgenda().selectedAppointments().get(0), event));
	}
	
	private boolean calendarHighlightedEvent(Appointment appointment, TestEvent event) {
		return calendar.isSameEvent(appointment, event);
	}

	
}
