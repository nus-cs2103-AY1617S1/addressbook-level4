package seedu.task.model.task;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import seedu.task.commons.exceptions.IllegalValueException;

//Represents a Task's(event) start date in the task manager.
public class StartDate {
  
  public static final String MESSAGE_DATE_CONSTRAINTS = "Task's start date should be entered as DD-MM-YYYY";
  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
  
  public final Date startDate;

  public StartDate(String dateToValidate) throws IllegalValueException, ParseException {
      if (!isValidDate(dateToValidate)) {
          throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
      }
      this.startDate = DATE_FORMAT.parse(dateToValidate);
  }
  
  public static boolean isValidDate(String inDate) {
      DATE_FORMAT.setLenient(false);
      try {
          DATE_FORMAT.parse(inDate.trim());
      } catch (ParseException pe) {
        return false;
      }
      return true;
    }
  
  @Override
  public String toString() {
      return DATE_FORMAT.format(startDate);
  }

  @Override
  public boolean equals(Object other) {
      return other == this; // short circuit if same object 
  }

  @Override
  public int hashCode() {
      return startDate.hashCode();
  }
}
