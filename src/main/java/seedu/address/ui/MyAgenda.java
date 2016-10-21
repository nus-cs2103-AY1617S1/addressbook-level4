package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javafx.collections.ObservableList;
import javafx.util.Callback;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.AppointmentImplLocal;
import seedu.address.logic.commands.BlockCommand;
import seedu.address.model.task.TaskComponent;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskType;

public class MyAgenda extends Agenda{
	
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
			appointment.setStartLocalDateTime(getTime(taskComponent.getStartDate()));
			appointment.setEndLocalDateTime(getTime(taskComponent.getEndDate()));
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
    		addToAgenda(t);
    	}
    }
	
	public void highLightSelected(TaskComponent taskComponent){
		AppointmentImplLocal appointment;
		if(appointments().contains((appointment = getAppointment(taskComponent))))
			appointments().get(appointments().indexOf(appointment))
			.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group5"));
	}

	
	private LocalDateTime getTime(TaskDate t){
    	return LocalDateTime.ofInstant(new Date(t.getDateInLong()).toInstant(), ZoneId.systemDefault());
    }

}
