package seedu.task.ui;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaysFromDisplayedSkin;

import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.control.agenda.Agenda.AppointmentGroup;
import jfxtras.scene.control.agenda.Agenda.LocalDateTimeRange;
import seedu.task.commons.exceptions.CalendarUnsyncException;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.ReadOnlyTask;
import seedu.taskcommons.core.LogsCenter;

/**
 * The Calendar window controller
 * 		Responsible for loading the calendar
 * 		Updating the calendar view 
 * @author xuchen
 *
 */
public class CalendarPanel extends UiPart {

	
	private static final int DAY_SKIN = 1;
	private static final int WEEK_SKIN = 0;
	private static final String CALENDAR_UNSYC_MESSAGE = "Calendar is unsync";
	private static final String CALENDAR_VIEW_ID = "calendar";
	private Agenda agenda;
	private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);
	private AnchorPane placeHolderPane;
	private final CalendarHelper helper;
	

	public CalendarPanel() {
		agenda = new Agenda();
		helper = CalendarHelper.getInstance();
	}

	public static CalendarPanel load(Stage primaryStage, AnchorPane calendarPlaceHolder,
			List<ReadOnlyEvent> eventList) {
		CalendarPanel calendarPanel = new CalendarPanel();
		calendarPanel.setupCalendar(primaryStage, calendarPlaceHolder);
		calendarPanel.configure(eventList);
		return calendarPanel;
	}

	private void setupCalendar(Stage primaryStage, AnchorPane calendarPlaceHolder) {
		logger.info("Setting up Calendar panel...");
		
		setStage(primaryStage);
		setPlaceholder(calendarPlaceHolder);
		setBoundary();
		agenda.setSkin(new AgendaDaysFromDisplayedSkin(agenda));
		this.agenda.setAllowDragging(false);
		this.agenda.setDisplayedLocalDateTime(LocalDateTime.now());
		resetCallBack();
		addToPlaceHodler();
	}
	
	/**
	 * Reset callbacks which modify the calendar so that the calendar depends solely on the event list
	 */
	private void resetCallBack() {
		agenda.setActionCallback( new Callback<Appointment, Void>() {
			@Override
			public Void call(Appointment param) {
				logger.info(param.getSummary() + " is selected. ");
				return null;
			}
		});
		
		agenda.setEditAppointmentCallback( new Callback<Appointment, Void>() {
			@Override
			public Void call(Appointment param) {
				// Do nothing
				return null;
			}
		});
		
		agenda.setNewAppointmentCallback( new Callback<LocalDateTimeRange, Appointment>() {
			@Override
			public Appointment call(LocalDateTimeRange param) {
				// Not allowing adding new events by clicking.
				return null;
			}
		});
		
	}

	private void addToPlaceHodler() {
		SplitPane.setResizableWithParent(placeHolderPane, true);
		agenda.setId(CALENDAR_VIEW_ID);
		placeHolderPane.getChildren().add(agenda);
	}

	private void configure(List<ReadOnlyEvent> eventList) {
		setConnectionEvent(eventList);
	}

	private void setConnectionEvent(List<ReadOnlyEvent> eventList) {
		agenda.appointments().clear();
		agenda.selectedAppointments().clear();
		eventList.forEach(event -> {
			agenda.appointments().add(CalendarHelper.convertFromEvent(event));
		});
	}
	
	//TODO: tasks are not yet in the calendar
	private void setConnectionTask(ObservableList<ReadOnlyTask> taskList) {

	}

	
	/**
	 * Focus the calendar to a certain time frame
	 * @param t
	 */
	public void updateCalendarShownPeriod(LocalDateTime t) {
		agenda.setDisplayedLocalDateTime(t);
	}
	

	private void setBoundary() {
		AnchorPane.setTopAnchor(agenda, 0.0);
		AnchorPane.setBottomAnchor(agenda, 0.0);
		AnchorPane.setLeftAnchor(agenda, 0.0);
		AnchorPane.setRightAnchor(agenda, 0.0);
	}

	@Override
	public void setPlaceholder(AnchorPane placeholder) {
		this.placeHolderPane = placeholder;
	}

	@Override
	public void setNode(Node node) {

	}

	/**
	 * Not use Fxml
	 * @return
	 */
	@Override
	public String getFxmlPath() {
		return "";

	}

	/** 
	 * Refresh data shown when eventlist in model modified
	 * @param eventList
	 */
	public void refresh(List<ReadOnlyEvent> eventList) {
		logger.info("Refreshing calendar...");
		setConnectionEvent(FXCollections.observableList(eventList));
	}
	
	/**
	 * Toggle the Calendar display mode
	 * @param calendarViewMode
	 */
	public void updateCalendarMode(int calendarViewMode) {
		switch(calendarViewMode) {
		case DAY_SKIN:
			agenda.setSkin(new AgendaDaySkin(agenda));
			break;
		case WEEK_SKIN:
			agenda.setSkin(new AgendaDaysFromDisplayedSkin(agenda));
			break;
		default:
			agenda.setSkin(new AgendaDaysFromDisplayedSkin(agenda));
		}
	}
	
	/**
	 * Select a event in the calendar and show its details. 
	 * @param targetEvent
	 * @throws exception if calendar is not sync with event list. Restart needed.
	 */
	public void select(ReadOnlyEvent targetEvent) throws CalendarUnsyncException {
		// focus on the event
		LocalDateTime displayedDateTime = targetEvent.getDuration().getStartTime();
		updateCalendarShownPeriod(displayedDateTime);
		
		//highlight the event 
		Appointment targetAppoint  = agenda.appointments()
				.stream()
				.filter((Predicate<? super Agenda.Appointment>) appointment -> 
			appointment.getSummary().equals(targetEvent.getEvent().fullName)
			&& appointment.getStartLocalDateTime().equals(targetEvent.getDuration().getStartTime())
			&& appointment.getEndLocalDateTime().equals(targetEvent.getDuration().getEndTime()))
				.findAny()
				.orElseThrow(()-> new CalendarUnsyncException(CALENDAR_UNSYC_MESSAGE));
		
		agenda.selectedAppointments().add(targetAppoint);
	}
	
	public static String getGroup(ReadOnlyTask task) {
		return null;
		
	}
	
}
