package seedu.Tdoo.model.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import seedu.Tdoo.commons.util.CollectionUtil;
import seedu.Tdoo.model.task.attributes.Countdown;
import seedu.Tdoo.model.task.attributes.EndDate;
import seedu.Tdoo.model.task.attributes.EndTime;
import seedu.Tdoo.model.task.attributes.Name;
import seedu.Tdoo.model.task.attributes.StartDate;
import seedu.Tdoo.model.task.attributes.StartTime;

/**
 * Represents a task in the TodoList. Guarantees: details are present and not
 * null, field values are validated.
 */
public class Event extends Task implements ReadOnlyTask, Comparable<Event> {

	private StartDate startDate;
	private EndDate endDate;
	private StartTime startTime;
	private EndTime endTime;

	/**
	 * Every field must be present and not null.
	 */
	public Event(Name name, StartDate startDate, EndDate endDate, StartTime startTime, EndTime endTime, String isDone) {
		assert !CollectionUtil.isAnyNull(name, startDate, endDate, startTime, endTime);
		super.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isDone = isDone;
	}

	/**
	 * Copy constructor.
	 */
	public Event(Event source) {
		this(source.getName(), source.getStartDate(), source.getEndDate(), source.getStartTime(), source.getEndTime(),
				source.getDone());
	}

	public Event(ReadOnlyTask source) {
		this((Event) source);
	};

	public StartDate getStartDate() {
		return startDate;
	}

	public EndDate getEndDate() {
		return endDate;
	}

	public StartTime getStartTime() {
		return startTime;
	}

	public EndTime getEndTime() {
		return endTime;
	}

	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof Event // instanceof handles nulls
						&& super.name.equals(((Event) other).getName())
						&& this.startDate.equals(((Event) other).getStartDate())
						&& this.endDate.equals(((Event) other).getEndDate())
						&& this.startTime.equals(((Event) other).getStartTime())
						&& this.endTime.equals(((Event) other).getEndTime()));

	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(getName()).append("\nStart Date: ").append(getStartDate()).append("\nEnd Date: ")
				.append(getEndDate()).append("\nStartTime: ").append(getStartTime()).append("\nEndTime: ")
				.append(getEndTime());
		return builder.toString();
	}

	public Event getEvent() {
		// EVENT Auto-generated method stub
		return null;
	}
	
	// @@author A0139923X
    public boolean checkEndDateTime() {
    	try {
    		String result = getCountdown();
    		if(result.contains("ago")) return false;
    	}
    	catch (Exception e) {}
        // CHECK END DATE -------------------------------
        String endTaskDate = getEndDate().endDate;
        int month;
        String day;

        // CHECK END TIME -------------------------------
        String endTaskTime = getEndTime().endTime;
        int getHour = 12, getMinute;
        // get hour
        String hour = (getEndTime().endTime).substring(0, 1);
        // get min
        String minute = (getEndTime().endTime).substring(3, 4);
        // get AM/PM
        String AM_PM = (getEndTime().endTime).substring(4, 6);

        // Date object-----------------------------------------
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date dateobj = new Date();
        String[] dateArr = endTaskDate.split(" ");
        String[] curDate = df.format(dateobj).split("-");
        day = dateArr[0].substring(0, 2);
        
        // Time object-----------------------------------------
        DateFormat tf = new SimpleDateFormat("h:m:a");
        Date timeobj = new Date();
        String[] curTime = tf.format(timeobj).split(":");

        // Convert month to int value
        switch (dateArr[1]) {
        case "January":
            month = 1;
            break;
        case "Febuary":
            month = 2;
            break;
        case "March":
            month = 3;
            break;
        case "April":
            month = 4;
            break;
        case "May":
            month = 5;
            break;
        case "June":
            month = 6;
            break;
        case "July":
            month = 7;
            break;
        case "August":
            month = 8;
            break;
        case "September":
            month = 9;
            break;
        case "October":
            month = 10;
            break;
        case "November":
            month = 11;
            break;
        default:
            month = 12;
        }

        // Convert string hour to int
        switch (hour) {
        case "01":
            getHour = 1;
            break;
        case "02":
            getHour = 2;
            break;
        case "03":
            getHour = 3;
            break;
        case "04":
            getHour = 4;
            break;
        case "05":
            getHour = 5;
            break;
        case "06":
            getHour = 6;
            break;
        case "07":
            getHour = 7;
            break;
        case "08":
            getHour = 8;
            break;
        case "09":
            getHour = 9;
            break;
        case "10":
            getHour = 10;
            break;
        case "11":
            getHour = 11;
            break;
        default:
            getHour = Integer.parseInt(hour);
        }

        switch (minute) {
        case "00":
            getMinute = 0;
            break;
        case "01":
            getMinute = 1;
            break;
        case "02":
            getMinute = 2;
            break;
        case "03":
            getMinute = 3;
            break;
        case "04":
            getMinute = 4;
            break;
        case "05":
            getMinute = 5;
            break;
        case "06":
            getMinute = 6;
            break;
        case "07":
            getMinute = 7;
            break;
        case "08":
            getMinute = 8;
            break;
        case "09":
            getMinute = 9;
            break;
        default:
            getMinute = Integer.parseInt(minute);
        }

        // Check year or if same year, check month or if same year, same month , check day
        if ((Integer.parseInt(dateArr[2]) < Integer.parseInt(curDate[2]))
                || (Integer.parseInt(dateArr[2]) == Integer.parseInt(curDate[2])
                        && month < Integer.parseInt(curDate[1]))
                || (Integer.parseInt(dateArr[2]) == Integer.parseInt(curDate[2])
                        && month == Integer.parseInt(curDate[1])
                        && Integer.parseInt(day) < Integer.parseInt(curDate[0]))) {
            return false;
            // Same day so check time
        } else if (Integer.parseInt(dateArr[2]) == Integer.parseInt(curDate[2]) && month == Integer.parseInt(curDate[1])
                && Integer.parseInt(day) == Integer.parseInt(curDate[0])) {
            if ((AM_PM.equals("am") && curTime[2].equals("AM")) || (AM_PM.equals("pm") && curTime[2].equals("PM"))) {
                // Check if same hour then check minute difference
                if (getHour == Integer.parseInt(curTime[0]) && getMinute < Integer.parseInt(curTime[1])) {
                    return false;
                    // Check if task end time is less than current time
                    // Check if task is in morning or noon and 12 is suppose to
                    // be lesser than current time (1am/pm is more than 12am/pm)
                } else if ((getHour < Integer.parseInt(curTime[0])) || ((AM_PM.equals("am") || AM_PM.equals("pm"))
                        && getHour == 12 && getHour > Integer.parseInt(curTime[0]))) {
                    return false;
                } else {
                    return true;
                }
            // Check if task end time is am(morning) while current time is pm(night)
            } else if ((AM_PM.equals("am") && curTime[2].equals("PM"))) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    //@@author A0132157M
    public String getCountdown() throws ParseException {
        //Get count down timer
        Countdown count = Countdown.getInstance();
        String result = count.convertDateToMilli(this.startDate.toString(), this.startTime.toString());
        return result;
    }
    
    //@@author A0144061U
  	@Override
  	public int compareTo(Event other) {
  		String thisDate = this.startDate.getSaveDate();
  		Integer thisDay = Integer.parseInt(thisDate.substring(0, 2));
  		Integer thisMonth = Integer.parseInt(thisDate.substring(3, 5));
  		Integer thisYear = Integer.parseInt(thisDate.substring(6, 10));
  		String otherDate = other.getStartDate().getSaveDate();
  		Integer otherDay = Integer.parseInt(otherDate.substring(0, 2));
  		Integer otherMonth = Integer.parseInt(otherDate.substring(3, 5));
  		Integer otherYear = Integer.parseInt(otherDate.substring(6, 10));
  		if(thisMonth.equals(otherMonth) && thisYear.equals(otherYear)) {
  			return thisDay.compareTo(otherDay);
  		}
  		else if(thisYear.equals(otherYear)) {
  			return thisMonth.compareTo(otherMonth);
  		}
  		else {
  			return thisYear.compareTo(otherYear);
  		}
  	}
}

class EventComparator implements Comparator<Task> {
	@Override
	public int compare(Task first, Task second) {
		Event f = (Event) first;
		Event s = (Event) second;
		return f.compareTo(s);
	}
}