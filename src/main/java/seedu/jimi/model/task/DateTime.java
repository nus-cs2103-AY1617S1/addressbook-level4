package seedu.jimi.model.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateTime implements Comparable<DateTime>{
    private LocalDateTime dtInstance;
    
    public DateTime() {
        dtInstance = LocalDateTime.now();
    }
    
    public DateTime(String dateStr){        
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        dtInstance = LocalDateTime.parse(dateStr, dtFormatter);      
    }
    
    public LocalDate getDate() {
        return dtInstance.toLocalDate();
    }
    
    public LocalTime getTime() {
        return dtInstance.toLocalTime();
    }
    
    
    private LocalDateTime getLocalDateTime() {
        return dtInstance;
    }
    
    @Override
    public int compareTo(DateTime dt) {
        return dtInstance.compareTo(dt.getLocalDateTime());
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime// instanceof handles nulls
                && this.dtInstance.equals(((DateTime) other).getLocalDateTime()));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(dtInstance);
    }   
    
    @Override
    public String toString() {
        return dtInstance.toString();
    }
}
