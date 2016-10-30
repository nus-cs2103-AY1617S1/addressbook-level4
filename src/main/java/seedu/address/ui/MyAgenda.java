package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javafx.util.Callback;
import jfxtras.scene.control.agenda.Agenda;
import seedu.address.logic.commands.BlockCommand;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskOccurrence;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskType;

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
        setPrefSize(550, 700);

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
        displayedLocalDateTime().set(getConvertedTime(inputDate).truncatedTo(ChronoUnit.DAYS));
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
            AppointmentImplLocal appointment = getAppointment(taskOccurrence);
            appointments().add(appointment);
        }
        if (taskOccurrence.equals(taskOccurrence.getTaskReference().getLastAppendedComponent())) {
            AppointmentImplLocal appointment = getAppointment(taskOccurrence);
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
            addYearlyOccurrences(appointment);
            break;
        case MONTHLY:
            addMonthlyOccurrences(appointment);
            break;
        case WEEKLY:
            addWeeklyOccurrences(appointment);
            break;
        case DAILY:
            addDailyOccurrences(appointment);
            break;
        default:
            break;
        }
    }

    /** Returns an AppointmentImplLocal object from a task component */
    private AppointmentImplLocal getAppointment(TaskOccurrence taskOccurrence) {

        AppointmentImplLocal appointment = new AppointmentImplLocal();
        appointment.setSummary(taskOccurrence.getTaskReference().getName().fullName);
        appointment.setDescription(taskOccurrence.getTaskReference().tagsString());
        appointment.setStartLocalDateTime(getConvertedTime(taskOccurrence.getStartDate()));
        appointment.setEndLocalDateTime(getConvertedTime(taskOccurrence.getEndDate()));
        if (taskOccurrence.isArchived()) {
            appointment.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("archive"));
        } else if (taskOccurrence.getTaskReference().getName().fullName.equals(BlockCommand.DUMMY_NAME)) {
            appointment.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("block"));
        } else {
            appointment.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("normal"));
        }
        return appointment;

    }

    /** Computes and adds all occurrences of this daily task to agenda. */
    private void addDailyOccurrences(AppointmentImplLocal appointment) {
        int dayOfWeek = appointment.getStartLocalDateTime().getDayOfWeek().getValue() % 7;
        if (appointment.getEndLocalDateTime().truncatedTo(ChronoUnit.DAYS).isBefore(agendaStartTime)) {
            dayOfWeek = 0;
        }
        for (int i = dayOfWeek; i <= 6; i++) {
            LocalDateTime start = getAppointmentStartTime(agendaStartTime.truncatedTo(ChronoUnit.DAYS), i, appointment);
            LocalDateTime end = getAppointmentEndTime(agendaStartTime.truncatedTo(ChronoUnit.DAYS), i, appointment);
            addToAgenda(appointment, start, end);
        }
    }

    /** Computes and adds all occurrences of this weekly task to agenda. */
    private void addWeeklyOccurrences(AppointmentImplLocal appointment) {
        int dayOfWeek = appointment.getStartLocalDateTime().getDayOfWeek().getValue() % 7;
        LocalDateTime start = getAppointmentStartTime(agendaStartTime, dayOfWeek, appointment);
        LocalDateTime end = getAppointmentEndTime(agendaStartTime, dayOfWeek, appointment);
        addToAgenda(appointment, start, end);
    }

    /** Computes and adds all occurrences of this monthly task to agenda. */
    private void addMonthlyOccurrences(AppointmentImplLocal appointment) {
        int dayOffset = appointment.getStartLocalDateTime().getDayOfMonth() - agendaStartTime.getDayOfMonth();
        if (dayOffset < 0) {
            dayOffset = 6 - (agendaEndTime.getDayOfMonth() - appointment.getStartLocalDateTime().getDayOfMonth());
        }
        LocalDateTime start = getAppointmentStartTime(agendaStartTime.truncatedTo(ChronoUnit.DAYS), dayOffset,
                appointment);
        LocalDateTime end = getAppointmentEndTime(agendaStartTime.truncatedTo(ChronoUnit.DAYS), dayOffset, appointment);
        addToAgenda(appointment, start, end);
    }

    /** Computes and adds all occurrences of this yearly task to agenda. */
    private void addYearlyOccurrences(AppointmentImplLocal appointment) {
        if (appointment.getStartLocalDateTime().getDayOfYear() > agendaStartTime.getDayOfYear() + 6
                || appointment.getStartLocalDateTime().getDayOfYear() < agendaStartTime.getDayOfYear()) {
            return;
        }
        int dayOffset = appointment.getStartLocalDateTime().getDayOfYear() - agendaStartTime.getDayOfYear();
        LocalDateTime start = getAppointmentStartTime(agendaStartTime, dayOffset, appointment);
        LocalDateTime end = getAppointmentEndTime(agendaStartTime, dayOffset, appointment);
        addToAgenda(appointment, start, end);
    }

    // ================Utility
    // methods================================================

    /** Returns a LocalDateTime object converted from TaskDate. */
    private LocalDateTime getConvertedTime(TaskDate t) {
        return LocalDateTime.ofInstant(new Date(t.getDateInLong()).toInstant(), ZoneId.systemDefault());
    }

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

    /** Returns the startTime of the appointment. */
    private LocalDateTime getAppointmentStartTime(LocalDateTime startPoint, int dayOffset,
            AppointmentImplLocal source) {
        return startPoint.plusDays(dayOffset).plusHours(source.getStartLocalDateTime().getHour())
                .plusMinutes(source.getStartLocalDateTime().getMinute());
    }

    /** Returns the endTime of the appointment. */
    private LocalDateTime getAppointmentEndTime(LocalDateTime startPoint, int dayOffset, AppointmentImplLocal source) {
        return startPoint.plusDays(dayOffset).plusHours(source.getEndLocalDateTime().getHour())
                .plusMinutes(source.getEndLocalDateTime().getMinute());
    }

    /**
     * Returns a new appointment with start and end time specified and contains
     * same data with source.
     */
    private AppointmentImplLocal copyAppointment(AppointmentImplLocal src, LocalDateTime start, LocalDateTime end) {
        AppointmentImplLocal newOne = new AppointmentImplLocal().withAppointmentGroup(src.getAppointmentGroup())
                .withDescription(src.getDescription()).withSummary(src.getSummary());

        newOne.setStartLocalDateTime(start);
        newOne.setEndLocalDateTime(end);
        if (start.isAfter(src.getStartLocalDateTime()))
            newOne.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("normal"));
        return newOne;
    }

    /** Adds a new appointment to agenda. */
    private void addToAgenda(AppointmentImplLocal appointment, LocalDateTime start, LocalDateTime end) {
        AppointmentImplLocal newAppointment = copyAppointment(appointment, start, end);
        if (!appointments().contains(newAppointment) && !onSameDay(newAppointment, appointment))
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
        return getConvertedTime(taskOccurrence.getStartDate()).truncatedTo(ChronoUnit.DAYS).isAfter(agendaEndTime)
                || getConvertedTime(taskOccurrence.getEndDate()).truncatedTo(ChronoUnit.DAYS).isBefore(agendaStartTime);
    }

    /** Returns true if the appointments are on the same day. */
    private boolean onSameDay(AppointmentImplLocal src, AppointmentImplLocal toCheck) {
        return src.getStartLocalDateTime().truncatedTo(ChronoUnit.DAYS)
                .equals(toCheck.getStartLocalDateTime().truncatedTo(ChronoUnit.DAYS));
    }

}
