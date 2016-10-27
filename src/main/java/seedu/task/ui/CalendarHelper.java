package seedu.task.ui;

import jfxtras.scene.control.agenda.Agenda.AppointmentImplBase;
import seedu.task.model.item.ReadOnlyEvent;

import java.util.HashMap;
import java.util.Map;

import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.control.agenda.Agenda.AppointmentGroup;
import jfxtras.scene.control.agenda.Agenda.AppointmentImplLocal;

public class CalendarHelper extends AppointmentImplBase implements Appointment {
	private static final String DEFAULT_GROUP = "group1";
	private static Map<String, AppointmentGroup> groupMap;
	private static CalendarHelper instance;
	
	
	private CalendarHelper() {
		setGroups();
	}
	
	private static void setGroups() {
		groupMap = new HashMap<>();
		for (AppointmentGroup group : new Agenda().appointmentGroups()) {
			groupMap.put(group.getDescription(), group);
		}
	}
	
	public static Appointment convertFromEvent(ReadOnlyEvent event) {
		Appointment item = new AppointmentImplLocal();
		item.setSummary(event.getEvent().fullName);
		item.setStartLocalDateTime(event.getDuration().getStartTime());
		item.setEndLocalDateTime(event.getDuration().getEndTime());
		item.setDescription(event.getDescriptionValue());
		item.setAppointmentGroup(groupMap.get(DEFAULT_GROUP));
		
		return item;
	}

	public static CalendarHelper getInstance() {
		if (instance == null) {
			instance = new CalendarHelper();
		}
		return instance;
	}
}
