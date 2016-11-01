package seedu.todo.ui;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PrettifyDate {

    public static final int THRESHOLD = 3;
    
    public static String prettifyDate(LocalDate date) {
        LocalDate today = LocalDate.now();
        
        if (date.equals(today)) {
            return "Today";
        }
        if (date.plusDays(1).equals(today)) {
            return "Yesterday";
        }
        if (date.minusDays(1).equals(today)) {
            return "Tomorrow";
        }
        
        if(date.isBefore(today) && today.minusDays(THRESHOLD).isBefore(date)) {
            int diff = Math.abs((int) ChronoUnit.DAYS.between(today, date));
            return diff + " days ago";
        } else if (date.isBefore(today) && today.minusDays(THRESHOLD).isBefore(date)) {
            int diff = Math.abs((int) ChronoUnit.DAYS.between(today, date));
            return diff + " days later";
        }
        
        return date.toString();
        
    }
    
    
    
}
