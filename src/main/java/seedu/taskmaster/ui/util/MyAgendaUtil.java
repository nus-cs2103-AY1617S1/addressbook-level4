package seedu.taskmaster.ui.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.control.agenda.Agenda.AppointmentImplLocal;
import seedu.taskmaster.model.task.TaskDate;
import seedu.taskmaster.model.task.TaskOccurrence;
//@@author A0147967J
/**
 * A utility class to handle MyAgenda 
 * LocalDateTime and Appointment manipulation.
 */
public class MyAgendaUtil {

    /** Returns a LocalDateTime object converted from TaskDate. */
    public static LocalDateTime getConvertedTime(TaskDate t){
        return LocalDateTime.ofInstant(new Date(t.getDateInLong()).toInstant(), ZoneId.systemDefault());        
    }
    
    /** Returns the startTime of the appointment. */
    public static LocalDateTime getAppointmentStartTime(LocalDateTime startPoint, int dayOffset,
            AppointmentImplLocal source) {
        return startPoint.plusDays(dayOffset).plusHours(source.getStartLocalDateTime().getHour())
                .plusMinutes(source.getStartLocalDateTime().getMinute());
    }

    /** Returns the endTime of the appointment. */
    public static LocalDateTime getAppointmentEndTime(LocalDateTime startPoint, int dayOffset, AppointmentImplLocal source) {
        return startPoint.plusDays(dayOffset).plusHours(source.getEndLocalDateTime().getHour())
                .plusMinutes(source.getEndLocalDateTime().getMinute());
    }

    /**
     * Returns a new appointment with start and end time specified and contains
     * same data with source.
     */
    public static AppointmentImplLocal copyAppointment(AppointmentImplLocal src, LocalDateTime start, LocalDateTime end) {
        AppointmentImplLocal newOne = new AppointmentImplLocal().withAppointmentGroup(src.getAppointmentGroup())
                .withDescription(src.getDescription()).withSummary(src.getSummary());

        newOne.setStartLocalDateTime(start);
        newOne.setEndLocalDateTime(end);
        if (start.isAfter(src.getStartLocalDateTime()))
            newOne.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("normal"));
        return newOne;
    }
    
    /** Returns an AppointmentImplLocal object from a task component */
    public static AppointmentImplLocal getAppointment(TaskOccurrence taskComponent){
        
        AppointmentImplLocal appointment = new AppointmentImplLocal();
        appointment.setSummary(taskComponent.getTaskReference().getName().fullName);
        appointment.setDescription(taskComponent.getTaskReference().tagsString());
        appointment.setStartLocalDateTime(getConvertedTime(taskComponent.getStartDate()));          
        appointment.setEndLocalDateTime(getConvertedTime(taskComponent.getEndDate()));
        if(taskComponent.isArchived()){
            appointment.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("archive"));           
        }else if(taskComponent.isBlockedSlot()){
            appointment.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("block"));
        }else{
            appointment.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("normal"));
        }   
        return appointment;
            
    }
    
    /** Returns true if the appointments are the same. */
    public static boolean isSameAppointment(Appointment a, AppointmentImplLocal a2){
        return a.getAppointmentGroup().getStyleClass().equals(a2.getAppointmentGroup().getStyleClass()) 
                && a.getStartLocalDateTime().equals(a2.getStartLocalDateTime())
                && a.getEndLocalDateTime().equals(a2.getEndLocalDateTime())
                && a.getDescription().equals(a2.getDescription())
                && a.getSummary().equals(a2.getSummary());
    }
}
