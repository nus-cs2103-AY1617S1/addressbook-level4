package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javafx.util.Callback;
import jfxtras.scene.control.agenda.Agenda;
import seedu.address.logic.commands.BlockCommand;
import seedu.address.model.task.TaskOccurrence;
import seedu.address.ui.util.MyAgendaUtil;
import seedu.address.model.task.TaskDate;

//@@author A0147967J
/**
 * This class is modified from jfxtras agenda for Happy Jim Task Master Use.
 */
public class MyAgenda extends Agenda {

    /** Keeps track of the start and end time of agenda. */
    private LocalDateTime agendaStartTime;
    private LocalDateTime agendaEndTime;

    /** Constructor */
    public MyAgenda() {

        super();

        /** Sets preferred size */
        setPrefSize(760, 570);

        /** Sets css class. */
        this.getStyleClass().add(MyAgenda.class.getSimpleName());

        /**
         * Disables dragging and resizing appointments. The agenda is only used
         * as a visualization.
         */
        allowDraggingProperty().set(false);
        allowResizeProperty().set(false);

        /** Disables editing via agenda */
        setEditAppointmentCallback((Callback<Appointment, Void>) (appointment) -> {
            return null;
        });

        /** Sets the locale used by calendar. */
        setLocale(Locale.ENGLISH);

        /** Computes and sets value for variables */
        agendaStartTime = getAgendaStartDateTime();
        agendaEndTime = getAgendaEndDateTime();

    }

    // =================For change agenda
    // range================================================

    /** Sets the displayed date time of the agenda to specified one. */
    public void setDisplayedDateTime(TaskDate inputDate) {
        displayedLocalDateTime().set(MyAgendaUtil.getConvertedTime(inputDate).truncatedTo(ChronoUnit.DAYS));
        agendaStartTime = getAgendaStartDateTime();
        agendaEndTime = getAgendaEndDateTime();
    }

    /** Loads the task component to be displayed on the agenda. */
    public void addAllToAgenda(List<TaskOccurrence> taskList) {
        appointments().clear();
        for (TaskOccurrence t : taskList){
            addAllOccurrencesInWeek(t);
        }
    }

    /**
     * Adds all occurrence of a particular task in current week into agenda.
     * Typically used for daily tasks. Under the assumption that the task
     * component list will not keep anything earlier than today.
     */
    private void addAllOccurrencesInWeek(TaskOccurrence taskOccurrence) {

        //Only cares time slot
        if(!taskOccurrence.isSlot()) {
            return;
        }

        if (!isOutsideAgenda(taskOccurrence)) {
            AppointmentImplLocal appointment = MyAgendaUtil.getAppointment(taskOccurrence);
            appointments().add(appointment);
        }
        if (taskOccurrence.equals(taskOccurrence.getTaskReference().getLastAppendedComponent())) {
            AppointmentImplLocal appointment = MyAgendaUtil.getAppointment(taskOccurrence);
            addCopiesToAgenda(taskOccurrence, appointment);
        }

    }

    /**
     * Adds copies of future appointments to the future agenda based on
     * recurring type.
     */
    private void addCopiesToAgenda(TaskOccurrence taskOccurrence, AppointmentImplLocal appointment) {
        if (isOutsideAgenda(appointment)) {
            return;
        }
        switch (taskOccurrence.getTaskReference().getRecurringType()) {
        case YEARLY:
            addYearlyOccurrences(appointment, getEndBoundary(taskOccurrence, appointment));
            break;
        case MONTHLY:
            addMonthlyOccurrences(appointment, getEndBoundary(taskOccurrence, appointment));
            break;
        case WEEKLY:
            addWeeklyOccurrences(appointment, getEndBoundary(taskOccurrence, appointment));
            break;
        case DAILY:
            addDailyOccurrences(appointment, getEndBoundary(taskOccurrence, appointment));
            break;
        default:
            break;
        }
    }
    
    /**
     * Adds copies of future appointments to the future agenda based on
     * recurring type.
     */
    private LocalDateTime getEndBoundary(TaskOccurrence taskOccurrence, AppointmentImplLocal appointment) {
        LocalDateTime endBoundary = null;
        int recurringCount = taskOccurrence.getTaskReference().getRecurringPeriod();
        if(recurringCount >= 0){
            switch (taskOccurrence.getTaskReference().getRecurringType()) {
            case YEARLY:
                endBoundary = appointment.getStartLocalDateTime().plusYears(recurringCount);
                break;
            case MONTHLY:
                endBoundary = appointment.getStartLocalDateTime().plusMonths(recurringCount);
                break;
            case WEEKLY:
                endBoundary = appointment.getStartLocalDateTime().plusWeeks(recurringCount);
                break;
            case DAILY:
                endBoundary = appointment.getStartLocalDateTime().plusDays(recurringCount);
                break;
            default:
                break;
            }
        }
        return endBoundary;
    }

    /** Computes and adds all occurrences of this daily task to agenda. */
    private void addDailyOccurrences(AppointmentImplLocal appointment, LocalDateTime endBoundary) {
        int dayOfWeek = appointment.getStartLocalDateTime().getDayOfWeek().getValue() % 7;
        if (appointment.getEndLocalDateTime().truncatedTo(ChronoUnit.DAYS).isBefore(agendaStartTime)) {
            dayOfWeek = 0;
        }
        for (int i = dayOfWeek; i <= 6; i++) {
            addToAgenda(appointment, agendaStartTime.truncatedTo(ChronoUnit.DAYS), i, endBoundary);
        }
    }

    /** Computes and adds all occurrences of this weekly task to agenda. */
    private void addWeeklyOccurrences(AppointmentImplLocal appointment ,LocalDateTime endBoundary) {
        int dayOfWeek = appointment.getStartLocalDateTime().getDayOfWeek().getValue() % 7;
        addToAgenda(appointment, agendaStartTime, dayOfWeek, endBoundary);
    }

    /** Computes and adds all occurrences of this monthly task to agenda. */
    private void addMonthlyOccurrences(AppointmentImplLocal appointment, LocalDateTime endBoundary) {
        int dayOffset = appointment.getStartLocalDateTime().getDayOfMonth() - agendaStartTime.getDayOfMonth();
        if (dayOffset < 0) {
            dayOffset = 6 - (agendaEndTime.getDayOfMonth() - appointment.getStartLocalDateTime().getDayOfMonth());
        }
        addToAgenda(appointment, agendaStartTime.truncatedTo(ChronoUnit.DAYS), dayOffset, endBoundary);
    }

    /** Computes and adds all occurrences of this yearly task to agenda. */
    private void addYearlyOccurrences(AppointmentImplLocal appointment, LocalDateTime endBoundary) {
        if (appointment.getStartLocalDateTime().getDayOfYear() > agendaStartTime.getDayOfYear() + 6
                || appointment.getStartLocalDateTime().getDayOfYear() < agendaStartTime.getDayOfYear()) {
            return;
        }
        int dayOffset = appointment.getStartLocalDateTime().getDayOfYear() - agendaStartTime.getDayOfYear();
        addToAgenda(appointment, agendaStartTime, dayOffset, endBoundary);
    }

    // ================Utility methods===========================================

    /** Returns the startTime of the agenda. */
    private LocalDateTime getAgendaStartDateTime() {
        LocalDateTime displayedDateTime = getDisplayedLocalDateTime().truncatedTo(ChronoUnit.DAYS);
        int dayOfWeek = displayedDateTime.getDayOfWeek().getValue() % 7;
        return displayedDateTime.minusDays(dayOfWeek);
    }

    /** Returns the endTime of the agenda. */
    private LocalDateTime getAgendaEndDateTime() {
        LocalDateTime displayedDateTime = getDisplayedLocalDateTime().truncatedTo(ChronoUnit.DAYS);
        int dayOfWeek = displayedDateTime.getDayOfWeek().getValue() % 7;
        return displayedDateTime.plusDays(6 - dayOfWeek);
    }

    /** Adds a new appointment to agenda. */
    private void addToAgenda(AppointmentImplLocal appointment, LocalDateTime startPoint, int dayOffset, LocalDateTime endBoundary) {
        LocalDateTime start = MyAgendaUtil.getAppointmentStartTime(startPoint, dayOffset, appointment);
        LocalDateTime end = MyAgendaUtil.getAppointmentEndTime(startPoint, dayOffset, appointment);
        AppointmentImplLocal newAppointment = MyAgendaUtil.copyAppointment(appointment, start, end);
        if (!MyAgendaUtil.isSameAppointment(newAppointment, appointment) 
                && (endBoundary == null || !endBoundary.isBefore(newAppointment.getStartLocalDateTime())))
            appointments().add(newAppointment);
    }

    /**
     * Returns true if it is a future task that is not needed to add to agenda.
     */
    private boolean isOutsideAgenda(AppointmentImplLocal appointment) {
        return appointment.getStartLocalDateTime().truncatedTo(ChronoUnit.DAYS).isAfter(agendaEndTime);
    }

    /**
     * Returns true if it is a future task that is not needed to add to agenda.
     */
    private boolean isOutsideAgenda(TaskOccurrence taskOccurrence) {
        return MyAgendaUtil.getConvertedTime(taskOccurrence.getStartDate()).truncatedTo(ChronoUnit.DAYS).isAfter(agendaEndTime)
                || MyAgendaUtil.getConvertedTime(taskOccurrence.getEndDate()).truncatedTo(ChronoUnit.DAYS).isBefore(agendaStartTime);
    }

}
