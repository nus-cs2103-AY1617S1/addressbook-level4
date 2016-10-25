package teamfour.tasc.ui.calendar;

import java.time.LocalDateTime;

import jfxtras.scene.control.agenda.Agenda.AppointmentGroup;
import teamfour.tasc.commons.util.DateUtil;
import teamfour.tasc.model.task.Deadline;
import teamfour.tasc.model.task.Period;
import teamfour.tasc.model.task.ReadOnlyTask;

public class CalendarReadOnlyRecurredAppointment extends CalendarReadOnlyAppointment {

    private final Deadline deadlineForOccurrence;
    private final Period periodForOccurence;
    
    public CalendarReadOnlyRecurredAppointment(ReadOnlyTask associatedTask,
            int associatedIndex,
            Deadline deadlineForOccurrence, Period periodForOccurence) {
        super(associatedTask, associatedIndex);

        this.deadlineForOccurrence = deadlineForOccurrence;
        this.periodForOccurence = periodForOccurence;
    }
    
    @Override
    public AppointmentGroup getAppointmentGroup() {
        if (associatedTask.getDeadline().hasDeadline()) {
            if (associatedTask.isOverdue(DateUtil.getCurrentTime())) {
                return CalendarAppointmentGroups.OVERDUE;
            }
        }
        
        return CalendarAppointmentGroups.RECURRING;
    }

    @Override
    public LocalDateTime getStartLocalDateTime() {
        if (deadlineForOccurrence.hasDeadline()) {
            return convertToLocalDateTime(deadlineForOccurrence.getDeadline());
        }
        
        if (periodForOccurence.hasPeriod()) {
            return convertToLocalDateTime(periodForOccurence.getStartTime());
        }

        return null;
    }

    @Override
    public LocalDateTime getEndLocalDateTime() {
        if (deadlineForOccurrence.hasDeadline()) {
            return convertToLocalDateTime(deadlineForOccurrence.getDeadline()).plusHours(1);
        }
        
        if (periodForOccurence.hasPeriod()) {
            return convertToLocalDateTime(periodForOccurence.getEndTime());
        }

        return null;
    }
}
