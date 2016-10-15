package teamfour.tasc.ui;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.control.agenda.Agenda.AppointmentGroup;
import teamfour.tasc.commons.core.LogsCenter;
import teamfour.tasc.commons.events.ui.TaskPanelListChangedEvent;
import teamfour.tasc.commons.util.FxViewUtil;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.ui.calendar.CalendarReadOnlyAppointment;

/**
 * Panel containing a visual overview of the calendar.
 */
public class CalendarPanel extends UiPart {

    private static Logger logger = LogsCenter.getLogger(CalendarPanel.class);
   
    private Agenda agendaView;

    /**
     * Constructor is kept private as {@link #load(AnchorPane)} is the only way to create a CalendarPanel.
     */
    private CalendarPanel() {
        
    }
    
    public static CalendarPanel load(AnchorPane placeholder, List<ReadOnlyTask> initialTaskList) {
        logger.info("Initializing calendar view");
        CalendarPanel calendarPanel = new CalendarPanel();
        calendarPanel.setupAgendaView();
        calendarPanel.refreshTasks(initialTaskList);
        
        FxViewUtil.applyAnchorBoundaryParameters(calendarPanel.agendaView, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(calendarPanel.agendaView);
        
        return calendarPanel;
    }

    private void setupAgendaView() {
        agendaView = new Agenda();
        
        // forbid any form of editing
        agendaView.setAllowDragging(false);
        agendaView.setAllowResize(false);        
        agendaView.setEditAppointmentCallback(new Callback<Appointment, Void>() {
            @Override
            public Void call(Appointment appointment) {
                // don't show any editing popups at all
                return null;
            }            
        });
    }

    @Override
    public void setNode(Node node) {
        // not applicable
    }

    @Override
    public String getFxmlPath() {
        // not applicable (not using fxml for this panel)
        return null;
    }

    /**
     * Free resources used by the calendar.
     */
    public void freeResources() {
        // TODO Auto-generated method stub
        agendaView = null;
    }

    /** 
     * Refresh the calendar using the new task list given.
     */
    @Subscribe
    public void refreshTasks(List<ReadOnlyTask> taskList) {
        agendaView.appointments().clear();
        
        for (ReadOnlyTask task : taskList) {
            if (isDisplayableInCalendar(task)) {
                agendaView.appointments().addAll(new CalendarReadOnlyAppointment(task));
            }
        }
    }

    /**
     * Select the particular task in the calendar.
     */
    public void selectTask(ReadOnlyTask taskToSelect) {
        logger.fine("Calendar will handle selectTask()");
        agendaView.selectedAppointments().clear();
        
        CalendarReadOnlyAppointment taskAppointment = new CalendarReadOnlyAppointment(taskToSelect);
        for (Appointment appointment : agendaView.appointments()) {
            if (taskAppointment.hasSameAssociatedTask(appointment)) {
                logger.fine("Calendar found the right task to select!");
                agendaView.setDisplayedLocalDateTime(taskAppointment.getStartLocalDateTime());
                agendaView.selectedAppointments().add(appointment);
                break;
            }
        }
    }
    
    /**
     * Determine whether the task can be displayed in the calendar. It cannot
     * be displayed if the task has no timings at all.
     */
    private boolean isDisplayableInCalendar(ReadOnlyTask task) {
        return task.getPeriod().hasPeriod() || task.getDeadline().hasDeadline();
    }
}
