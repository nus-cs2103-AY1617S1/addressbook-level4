package seedu.dailyplanner.logic.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.*;

public class nattyParser {
    
    private com.joestelmach.natty.Parser nattyParserPackage;
    
    nattyParser() {
    nattyParserPackage = new com.joestelmach.natty.Parser();
    }
    
    String parseDate(String date) {
        List<DateGroup> groups = nattyParserPackage.parse(date);
        Date parsedDate = new Date();
        for(DateGroup group:groups) {
          parsedDate = group.getDates().get(0);
          break;
          
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(parsedDate);
    }
    
    String parseTime(String time) {
        List<DateGroup> groups = nattyParserPackage.parse(time);
        Date parsedTime = new Date();
        for(DateGroup group:groups) {
          parsedTime = group.getDates().get(0);
          break;
          
        }
        DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(parsedTime);
    }

}
