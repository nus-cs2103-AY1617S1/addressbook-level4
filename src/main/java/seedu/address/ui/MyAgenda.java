package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javafx.collections.ObservableList;
import javafx.util.Callback;
import jfxtras.scene.control.agenda.Agenda;
import seedu.address.logic.commands.BlockCommand;
import seedu.address.model.task.TaskComponent;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskType;
import seedu.address.model.task.RecurringType;

public class MyAgenda extends Agenda{
	
	private LocalDateTime agendaStartTime;
	private LocalDateTime agendaEndTime;
		
	public MyAgenda(){
		
		super();
		
		//Set preferred size
		setPrefSize(550, 700);	
		
		//Disable dragging
		allowDraggingProperty().set(false);
		
		//Disable editing via agenda
		setEditAppointmentCallback((Callback<Appointment, Void>)(appointment)->{
			return null;			
		});
		
		agendaStartTime = getAgendaStartDateTime();
		agendaEndTime = getAgendaEndDateTime();
		
	}
	
	private void addToAgenda(TaskComponent taskComponent){
		AppointmentImplLocal appointment;
		if((appointment = getAppointment(taskComponent))!=null)
			appointments().add(appointment);
	}
	
	private AppointmentImplLocal getAppointment(TaskComponent taskComponent){
		if(taskComponent.getTaskReference().getTaskType()!=TaskType.FLOATING && !taskComponent.hasOnlyEndDate()){
			AppointmentImplLocal appointment = new AppointmentImplLocal();
			appointment.setSummary(taskComponent.getTaskReference().getName().fullName);
			appointment.setDescription(taskComponent.getTaskReference().tagsString());
			appointment.setStartLocalDateTime(getCovertedTime(taskComponent.getStartDate()));
			appointment.setEndLocalDateTime(getCovertedTime(taskComponent.getEndDate()));
			if(taskComponent.getTaskReference().getTaskType() == TaskType.COMPLETED){
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
	
	public void addAllToAgenda(ObservableList<TaskComponent> taskList){
    	appointments().clear();
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
		
		AppointmentImplLocal appointment = getAppointment(taskComponent);
		if(appointment!=null && !appointments().contains(appointment)){
			switch(taskComponent.getTaskReference().getRecurringType()){				
				case DAILY:
					addDailyOccurrences(appointment);
				case WEEKLY:
				case MONTHLY:
				case YEARLY:
				case NONE:
					appointments().add(appointment);
				break;
				default:
					break;
			
			}
		}

	}
	
	private void addDailyOccurrences(AppointmentImplLocal appointment) {
		if(appointment.getStartLocalDateTime().isAfter(agendaEndTime)) return;
		int dayOfWeek = appointment.getStartLocalDateTime().getDayOfWeek().getValue() % 7;
		for(int i = dayOfWeek; i <= 6; i++){
			LocalDateTime start = appointment.getStartLocalDateTime().plusDays(i - dayOfWeek);
			LocalDateTime end = appointment.getEndLocalDateTime().plusDays(i - dayOfWeek);
			appointments().add(copyAppointment(appointment, start, end));
		}
		
	}

	private void addMonthlyOccurrences(AppointmentImplLocal appointment) {
		// TODO Auto-generated method stub
		if(appointment.getStartLocalDateTime().isAfter(agendaEndTime)) return;

	}

	private void addWeeklyOccurrences(AppointmentImplLocal appointment) {
		if(appointment.getStartLocalDateTime().isAfter(agendaEndTime)) return;
		
	}

	private void addYearlyOccurrences(AppointmentImplLocal appointment) {
		if(appointment.getStartLocalDateTime().isAfter(agendaEndTime)) return;
		
		
	}

	public void highLightSelected(TaskComponent taskComponent){
		AppointmentImplLocal appointment;
		if(appointments().contains((appointment = getAppointment(taskComponent))))
			appointments().get(appointments().indexOf(appointment))
			.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group5"));
	}

	
	private LocalDateTime getCovertedTime(TaskDate t){
    	return LocalDateTime.ofInstant(new Date(t.getDateInLong()).toInstant(), ZoneId.systemDefault());
    }
	
	private LocalDateTime getAgendaStartDateTime(){
		LocalDateTime displayedDateTime = getDisplayedLocalDateTime();
		int dayOfWeek = displayedDateTime.getDayOfWeek().getValue() % 7;
		return displayedDateTime.minusDays(dayOfWeek);
	}
	
	private LocalDateTime getAgendaEndDateTime(){
		LocalDateTime displayedDateTime = getDisplayedLocalDateTime();
		int dayOfWeek = displayedDateTime.getDayOfWeek().getValue() % 7;
		return displayedDateTime.plusDays(6 - dayOfWeek);
	}
	
	private AppointmentImplLocal copyAppointment(AppointmentImplLocal src, LocalDateTime start, LocalDateTime end){
		AppointmentImplLocal newOne = new AppointmentImplLocal().withAppointmentGroup(src.getAppointmentGroup())
				.withDescription(src.getDescription()).withSummary(src.getSummary());
		
		newOne.setStartLocalDateTime(start);
		newOne.setEndLocalDateTime(end);
		return newOne;
	}

}
