package seedu.Tdoo.model.task.attributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.ocpsoft.prettytime.PrettyTime;

//@@author A0132157M
public class Countdown {
    private static Countdown instance = null;
    protected Countdown(){
    }
    public static Countdown getInstance() {
        if(instance == null) {
            instance = new Countdown();
        }
        return instance;
    }
    
    PrettyTime p = new PrettyTime();
    
    //@@author A0132157M
    public String convertDateToMilli(String s, String q) throws ParseException {
        //splits the input date and time for conversion to milliseconds  
        String string = s;
        String[] parts = string.split(" ");
        String part1 = parts[0]; 
        String part2 = parts[1]; 
        String part3 = parts[2];
        String part4 = part1.substring(0, 2);
        String bstring = q;
        String input = part3 + " " + part2 + " " + part4 + " " + bstring;
        String result = convertToSDF(input);
        return result;
    }
    
    //@@author A0132157M
    private String convertToSDF(String input) throws ParseException {
        //Converts the input date and time to milliseconds 
        Date date = new SimpleDateFormat("yyyy MMMM dd HH:mmaaa", Locale.ENGLISH).parse(input);
        long milliseconds = date.getTime();
        long dateToMilli = milliseconds - (new Date()).getTime();
        String result = setPrettyTime(dateToMilli);
        return result;
    }
    
    //@@author A0132157M
    private String setPrettyTime(long input) {
        //Converts milliseconds into readable text for user using PrettyTime library
        //by comparing input with current date and time
        String result = p.format(new Date(System.currentTimeMillis() + input));
        return result; 
    }

}
