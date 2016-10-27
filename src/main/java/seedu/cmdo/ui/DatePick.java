package seedu.cmdo.ui;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.control.DatePicker;
import seedu.cmdo.commons.core.LogsCenter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Logger;

/**
 * The Browser Panel of the App.
 */
public class DatePick extends UiPart{

    private static Logger logger = LogsCenter.getLogger(DatePicker.class);
    private DatePicker datePicker;
    private VBox datePickerHolder;
    
    DatePicker getDatePicker() {
    	logger.info("Starting date picker . . .");    	
    	DatePicker datePicker = new DatePicker();
    	datePicker.setValue(LocalDate.now());
    	datePicker.hide();
		return datePicker;
    }

    @Override
    public void setNode(Node node) {
        //not applicable
    }

    @Override
    public String getFxmlPath() {
        return null; //not applicable
    }

}
