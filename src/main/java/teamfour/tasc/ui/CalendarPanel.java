//@@author A0140011L
package teamfour.tasc.ui;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import teamfour.tasc.commons.core.LogsCenter;
import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.commons.util.FxViewUtil;
import teamfour.tasc.logic.commands.CalendarCommand;
import teamfour.tasc.model.task.Deadline;
import teamfour.tasc.model.task.Period;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.Recurrence;
import teamfour.tasc.ui.calendar.CalendarReadOnlyAppointment;
import teamfour.tasc.ui.calendar.CalendarReadOnlyRecurredAppointment;

/**
 * Panel containing a visual overview of the calendar.
 */
public class CalendarPanel extends UiPart {

    private static Logger logger = LogsCenter.getLogger(CalendarPanel.class);
    private static String currentCalendarView = "";
   
    private Agenda agendaView;
    private ReadOnlyTask lastSelectedTask;

    /**
     * Constructor is kept private as {@link #load(AnchorPane)} is the only way to create a CalendarPanel.
     */
    private CalendarPanel() {
        lastSelectedTask = null;
    }
    
    /**
     * Get current calendar view type.
     */
    public static String getCalendarView() {
        return currentCalendarView;
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
        changeView(CalendarCommand.KEYWORD_CALENDAR_VIEW_WEEK);
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
    
    //@@author A0148096W
    /**
     * Precondition: argument is not null.
     * Change the view of the calendar.
     */
    public void changeView(String view) {
        assert view != null;
        
        switch(view) {
        case CalendarCommand.KEYWORD_CALENDAR_VIEW_DAY:
            agendaView.setSkin(new AgendaDaySkin(agendaView));
            currentCalendarView = CalendarCommand.KEYWORD_CALENDAR_VIEW_DAY;
            break;
        case CalendarCommand.KEYWORD_CALENDAR_VIEW_WEEK:
            agendaView.setSkin(new AgendaWeekSkin(agendaView));
            currentCalendarView = CalendarCommand.KEYWORD_CALENDAR_VIEW_WEEK;
            break;
        default:
            logger.warning("Calendar view type is not recognized: " + view);
            break;
        }
        selectLastSelectedTask();
    }
    
    //@@author A0140011L
    /** 
     * Refresh the calendar using the new task list given.
     */
    @Subscribe
    public void refreshTasks(List<ReadOnlyTask> taskList) {
        agendaView.appointments().clear();
        
        int index = 0;
        
        for (ReadOnlyTask task : taskList) {
            index++; 
            
            if (isDisplayableInCalendar(task)) {
                try {
                    agendaView.appointments().addAll(generateAppointmentsForTask(task, index));
                } catch (IllegalValueException ive) {
                    assert false: "Not possible";
                }
            }
        }
    }

    /**
     * Generate an appointment(s) given a task, taking into
     * consideration any possible recurring.
     * 
     * Pre-condition: The task must be displayable in calendar.
     * @param task
     * @throws IllegalValueException 
     */
    private List<Appointment> generateAppointmentsForTask(ReadOnlyTask task, int index)
            throws IllegalValueException {
        assert isDisplayableInCalendar(task);
        
        List<Appointment> allAppointments = new ArrayList<Appointment>();
        allAppointments.add(new CalendarReadOnlyAppointment(task, index));
        
        Recurrence taskRecurrence = task.getRecurrence();
        
        if (taskRecurrence.hasRecurrence()) {
            Recurrence remainingRecurrence = task.getRecurrence();
            Deadline currentDeadline = task.getDeadline();
            Period currentPeriod = task.getPeriod();
            
            while (remainingRecurrence.hasRecurrence()) {
                if (currentDeadline.hasDeadline()) {
                    currentDeadline = new Deadline(remainingRecurrence
                            .getNextDateAfterRecurrence(currentDeadline.getDeadline()));
                }

                if (currentPeriod.hasPeriod()) {
                    currentPeriod = new Period(
                            remainingRecurrence
                                    .getNextDateAfterRecurrence(currentPeriod.getStartTime()),
                            remainingRecurrence
                                    .getNextDateAfterRecurrence(currentPeriod.getEndTime()));
                }
                
                remainingRecurrence = remainingRecurrence.getRecurrenceWithOneFrequencyLess();
                
                allAppointments.add(new CalendarReadOnlyRecurredAppointment(task, index, currentDeadline, currentPeriod));
            }
        }
        
        return allAppointments;
    }

    /**
     * Select the particular task in the calendar.
     */
    public void selectTask(ReadOnlyTask taskToSelect) {
        logger.fine("Calendar will handle selectTask()");
        agendaView.selectedAppointments().clear();
        
        CalendarReadOnlyAppointment taskAppointment = new CalendarReadOnlyAppointment(taskToSelect, -1);
        for (Appointment appointment : agendaView.appointments()) {
            if (taskAppointment.hasSameAssociatedTask(appointment)) {
                logger.fine("Calendar found the right task to select!");
                agendaView.setDisplayedLocalDateTime(taskAppointment.getStartLocalDateTime());
                agendaView.selectedAppointments().add(appointment);
                lastSelectedTask = taskToSelect;
                break;
            }
        }
    }
    
    /**
     * Re-selects the last selected task.
     */
    public void selectLastSelectedTask() {
        LocalDateTime time = (new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        agendaView.setDisplayedLocalDateTime(time);
        if (lastSelectedTask != null) {
            selectTask(lastSelectedTask);
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
