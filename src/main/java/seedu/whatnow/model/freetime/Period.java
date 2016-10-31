//@@author A0139772U
package seedu.whatnow.model.freetime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * A time period with start and end time
 */
public class Period implements Comparator {
    
    public String start;
    public String end;
    
    private static final int SMALLER = -1;
    private static final int EQUAL = 0;
    private static final int BIGGER = 1;
    private static final String TWELVE_HOUR_WITH_MINUTES_COLON_FORMAT = "h:mma";
    
    public Period() {
        
    }
    
    public Period(String start, String end) {
        this.start = start;
        this.end = end;
    }
    
    public void setStart(String start) {
        this.start = start;
    }
    
    public void setEnd(String end) {
        this.end = end;
    }
    
    public String getStart() {
        return this.start;
    }
    
    public String getEnd() {
        return this.end;
    }
    
    @Override
    public String toString() {
        return "[" + start + ", " + end + "]";
    }

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 instanceof Period && o2 instanceof Period) {
            Period period1 = (Period)o1;
            Period period2 = (Period)o2;
            DateFormat df = new SimpleDateFormat(TWELVE_HOUR_WITH_MINUTES_COLON_FORMAT);
            df.setLenient(false);
            try {
                Date p1start = df.parse(period1.getStart());
                Date p1end = df.parse(period1.getEnd());
                Date p2start = df.parse(period2.getStart());

                if (p1start.compareTo(p2start) < 0
                        && p1end.compareTo(p2start) < 0) {
                    return SMALLER;
                } else if (p1start.compareTo(p2start) == 0
                        && p1end.compareTo(p2start) == 0) {
                    return EQUAL;
                } else {
                    return BIGGER;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return 0;
    }
    
}