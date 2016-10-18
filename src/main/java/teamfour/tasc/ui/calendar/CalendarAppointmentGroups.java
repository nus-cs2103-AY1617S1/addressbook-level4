package teamfour.tasc.ui.calendar;

import jfxtras.scene.control.agenda.Agenda.AppointmentGroupImpl;

public class CalendarAppointmentGroups {
    public static final AppointmentGroupImpl COMPLETED = new
            AppointmentGroupImpl().withStyleClass("completed");
    public static final AppointmentGroupImpl OVERDUE = new
            AppointmentGroupImpl().withStyleClass("overdue");
    
    public static final AppointmentGroupImpl DEADLINE = new
            AppointmentGroupImpl().withStyleClass("deadline");
    public static final AppointmentGroupImpl PERIOD = new
            AppointmentGroupImpl().withStyleClass("period");
    public static final AppointmentGroupImpl RECURRING = new
            AppointmentGroupImpl().withStyleClass("recurring");
}
