package seedu.cmdo.ui;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import jfxtras.scene.control.CalendarPicker;

/**
 * Implement Calendar for UI
 * 
 * @@author A0141128R
 * 
 */
public class CalendarView extends UiPart {

	@FXML
	private AnchorPane calendarHolderPane;
	
	public AnchorPane getCalendarView() {
		AnchorPane calendarHolderPane = new AnchorPane(); 
		calendarHolderPane.setPrefSize(300, 180);
		CalendarPicker myCalendar = new CalendarPicker();
		setCalendar(myCalendar);
		calendarHolderPane.getChildren().add(myCalendar);		
		return calendarHolderPane;
	}
	
	private void setCalendar(CalendarPicker myCalendar) {
		myCalendar.setPrefSize(300, 180);
	}
	
	@Override
	public void setNode(Node node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFxmlPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
