package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import jfxtras.scene.control.agenda.Agenda;
import seedu.address.logic.commands.BlockCommand;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskComponent;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskType;

/**
 * This class is modified from jfxtras agenda for 
 * Happy Jim Task Master Use.
 */
public class MyAgenda extends Agenda{
	
	/** Keeps track of the start and end time of agenda.*/
	private LocalDateTime agendaStartTime;
	private LocalDateTime agendaEndTime;
	
	private HashSet<ReadOnlyTask> taskSet;
	
	/** Constructor */
	public MyAgenda(){
		
		super();
		
		taskSet = new HashSet<ReadOnlyTask>();
		
		/** Sets preferred size */
		setPrefSize(550, 700);	
		
		/** Disables dragging and resizing appointments. The agenda is only used as a visualization. */
		allowDraggingProperty().set(false);
		allowResizeProperty().set(false);
		
		/** Disables editing via agenda */
		setEditAppointmentCallback((Callback<Appointment, Void>)(appointment)->{
			return null;			
		});
		
		/** Sets the locale used by calendar. */
		setLocale(Locale.ENGLISH);				
		
		/** Computes and sets value for variables */
		agendaStartTime = getAgendaStartDateTime();
		agendaEndTime = getAgendaEndDateTime();
		
	}
	
	/** Returns an AppointmentImplLocal object from a task component */
	private AppointmentImplLocal getAppointment(TaskComponent taskComponent){
		if(taskComponent.getTaskReference().getTaskType()!=TaskType.FLOATING && !taskComponent.hasOnlyEndDate()){
			AppointmentImplLocal appointment = new AppointmentImplLocal();
			appointment.setSummary(taskComponent.getTaskReference().getName().fullName);
			appointment.setDescription(taskComponent.getTaskReference().tagsString());
			appointment.setStartLocalDateTime(getConvertedTime(taskComponent.getStartDate()));
			appointment.setEndLocalDateTime(getConvertedTime(taskComponent.getEndDate()));
			if(taskComponent.isArchived()){
				appointment.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group1"));
			}else if(taskComponent.getTaskReference().getName().fullName.equals(BlockCommand.DUMMY_NAME)){
				appointment.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group6"));
			}else{
				appointment.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group9"));
			}	
			return appointment;
		}
		return null;		
	}
	
	/** Loads the task component to be displayed on the agenda. */
	public void addAllToAgenda(ObservableList<TaskComponent> taskList){
    	appointments().clear();
    	taskSet.clear();
    	for(TaskComponent t:taskList){
    		addAllOccurenceInWeek(t);
    	}
    }
	
	/** 
	 * Adds all occurrence of a particular task in current week into agenda.
	 * Typically used for daily tasks.
	 * Under the assumption that the task component list will not keep anything earlier than today.
	 */
	private void addAllOccurenceInWeek(TaskComponent taskComponent){
		
		if(taskSet.contains(taskComponent.getTaskReference())) return;
		taskSet.add(taskComponent.getTaskReference());		
		AppointmentImplLocal appointment = getAppointment(taskComponent);
		if(appointment!=null && !appointments().contains(appointment)){
			switch(taskComponent.getTaskReference().getRecurringType()){				
				case DAILY:
					addDailyOccurrences(appointment);
					break;
				case WEEKLY:
					addWeeklyOccurrences(appointment);
					break;
				case MONTHLY:
					addMonthlyOccurrences(appointment);
					break;
				case YEARLY:
					addYearlyOccurrences(appointment);
					break;
				case NONE:
					appointments().add(appointment);
				break;
				default:
					break;			
			}
		}
	}
	
	/** Computes and adds all occurrences of this daily task to agenda.*/
	private void addDailyOccurrences(AppointmentImplLocal appointment) {
		if(isOutsideAgenda(appointment)) return;
		int dayOfWeek = appointment.getStartLocalDateTime().getDayOfWeek().getValue() % 7;
		if(appointment.getEndLocalDateTime().truncatedTo(ChronoUnit.DAYS).isBefore(agendaStartTime))
			dayOfWeek = 0;
		for(int i = dayOfWeek; i <= 6; i++){
			LocalDateTime start = getAppointmentStartTime(agendaStartTime.truncatedTo(ChronoUnit.DAYS),i,appointment);
			LocalDateTime end = getAppointmentEndTime(agendaStartTime.truncatedTo(ChronoUnit.DAYS),i,appointment);
			addToAgenda(appointment, start, end);
		}		
	}
	
	//==Prepared for view command (If want to view other week's agenda)==
	public void setDisplayedDateTime(TaskDate inputDate){
		displayedLocalDateTime().set(getConvertedTime(inputDate).truncatedTo(ChronoUnit.DAYS));
		agendaStartTime = getAgendaStartDateTime();
		agendaEndTime = getAgendaEndDateTime();
	}
	
	private void addMonthlyOccurrences(AppointmentImplLocal appointment) {
		if(isOutsideAgenda(appointment)) return;
		int dayOffset = appointment.getStartLocalDateTime().getDayOfMonth() - agendaStartTime.getDayOfMonth();
		LocalDateTime start = getAppointmentStartTime(agendaStartTime.truncatedTo(ChronoUnit.DAYS),dayOffset,appointment);
		LocalDateTime end = getAppointmentEndTime(agendaStartTime.truncatedTo(ChronoUnit.DAYS),dayOffset,appointment);
		addToAgenda(appointment, start, end);
	}

	private void addWeeklyOccurrences(AppointmentImplLocal appointment) {
		if(isOutsideAgenda(appointment)) return;
		int dayOfWeek = appointment.getStartLocalDateTime().getDayOfWeek().getValue() % 7;
		LocalDateTime start = getAppointmentStartTime(agendaStartTime,dayOfWeek,appointment);
		LocalDateTime end = getAppointmentEndTime(agendaStartTime,dayOfWeek,appointment);
		addToAgenda(appointment, start, end);
	}

	private void addYearlyOccurrences(AppointmentImplLocal appointment) {
		if(isOutsideAgenda(appointment)) return;
		if(appointment.getStartLocalDateTime().getDayOfYear() > agendaStartTime.getDayOfYear()+6
				||appointment.getStartLocalDateTime().getDayOfYear() < agendaStartTime.getDayOfYear()) return;
		int dayOffset  = appointment.getStartLocalDateTime().getDayOfYear() - agendaStartTime.getDayOfYear();
		LocalDateTime start = getAppointmentStartTime(agendaStartTime,dayOffset,appointment);
		LocalDateTime end = getAppointmentEndTime(agendaStartTime,dayOffset,appointment);
		addToAgenda(appointment, start, end);		
	}
	
	//=====Prepared for modified select method======================
	public void highLightSelected(TaskComponent taskComponent){
		AppointmentImplLocal appointment;
		if(appointments().contains((appointment = getAppointment(taskComponent))))
			appointments().get(appointments().indexOf(appointment))
			.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group5"));
	}
	//==============================================================
	private LocalDateTime getAppointmentStartTime(LocalDateTime startPoint, int dayOffset, AppointmentImplLocal source){
		return startPoint.plusDays(dayOffset)
				.plusHours(source.getStartLocalDateTime().getHour())
				.plusMinutes(source.getStartLocalDateTime().getMinute());
	}
	
	private LocalDateTime getAppointmentEndTime(LocalDateTime startPoint, int dayOffset, AppointmentImplLocal source){
		return startPoint.plusDays(dayOffset)
				.plusHours(source.getEndLocalDateTime().getHour())
				.plusMinutes(source.getEndLocalDateTime().getMinute());
	}
	

	/** Returns a LocalDateTime object converted from TaskDate. */
	private LocalDateTime getConvertedTime(TaskDate t){
		return LocalDateTime.ofInstant(new Date(t.getDateInLong()).toInstant(), ZoneId.systemDefault());
    	
    }
	
	/** Returns the startTime of the agenda. */
	private LocalDateTime getAgendaStartDateTime(){
		LocalDateTime displayedDateTime = getDisplayedLocalDateTime().truncatedTo(ChronoUnit.DAYS);
		int dayOfWeek = displayedDateTime.getDayOfWeek().getValue() % 7;
		return displayedDateTime.minusDays(dayOfWeek);
	}
	
	/** Returns the endTime of the agenda. */
	private LocalDateTime getAgendaEndDateTime(){
		LocalDateTime displayedDateTime = getDisplayedLocalDateTime().truncatedTo(ChronoUnit.DAYS);
		int dayOfWeek = displayedDateTime.getDayOfWeek().getValue() % 7;
		return displayedDateTime.plusDays(6 - dayOfWeek);
	}
	
	
	/** Returns a new appointment with start and end time specified and contains same data with source. */
	private AppointmentImplLocal copyAppointment(AppointmentImplLocal src, LocalDateTime start, LocalDateTime end){
		AppointmentImplLocal newOne = new AppointmentImplLocal().withAppointmentGroup(src.getAppointmentGroup())
				.withDescription(src.getDescription()).withSummary(src.getSummary());
		
		newOne.setStartLocalDateTime(start);
		newOne.setEndLocalDateTime(end);
		if(start.isAfter(src.getStartLocalDateTime()))
			newOne.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group9"));
		return newOne;
	}
	
	/** Returns true if it is a future task that is not needed to add to agenda. */
	private boolean isOutsideAgenda(AppointmentImplLocal appointment){
		return appointment.getStartLocalDateTime().truncatedTo(ChronoUnit.DAYS).isAfter(agendaEndTime);
	}
	
	/** Adds a new appointment to agenda. */
	private void addToAgenda(AppointmentImplLocal appointment, LocalDateTime start, LocalDateTime end){
		AppointmentImplLocal newAppointment = copyAppointment(appointment, start, end);
		if(!appointments().contains(newAppointment))
			appointments().add(newAppointment);
	}

}
