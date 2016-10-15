package seedu.ggist.logic.parser;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeParser {
    
    private String date;
    private String time;
    
    public DateTimeParser(String raw) {
        Date dateTime = new PrettyTimeParser().parse(raw).get(0);
        date = parseDate(dateTime);
        time = parseTime(dateTime);
    }
    
    private String parseDate(Date dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yy");
        return sdf.format(dateTime).toString();
    }
    
    private String parseTime(Date dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        return sdf.format(dateTime).toString();
    }
    
    public String getDate() {
        return date;
    }
    
    public String getTime() {
        return time;
    }
}
