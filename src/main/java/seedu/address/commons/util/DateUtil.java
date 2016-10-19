package seedu.address.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
    private static List<SimpleDateFormat> DATE_FORMATS;
    private static List<SimpleDateFormat> DATE_FORMATS1;
    private static List<SimpleDateFormat> DATE_FORMATS2;
    
    public DateUtil() {
        DATE_FORMATS = new ArrayList<>();
        DATE_FORMATS1 = new ArrayList<>();  
        DATE_FORMATS2 = new ArrayList<>();
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy h:mm a"));
        DATE_FORMATS.add(new SimpleDateFormat("d/MM/yyyy h:mm a"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy h.mm a"));
        DATE_FORMATS.add(new SimpleDateFormat("d/MM/yyyy h.mm a"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy HHmm"));
        DATE_FORMATS.add(new SimpleDateFormat("d/MM/yyyy HHmm"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy HH:mm"));
        DATE_FORMATS.add(new SimpleDateFormat("d/MM/yyyy HH:mm"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy HH.mm"));
        DATE_FORMATS.add(new SimpleDateFormat("d/MM/yyyy HH.mm"));
        DATE_FORMATS1.add(new SimpleDateFormat("d-MM-yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d/MM/yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d-MM-yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d/MM/yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d-MM-yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d/MM/yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d-MM-yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d/MM/yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d-MM-yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d/MM/yyyy"));
        DATE_FORMATS2.add(new SimpleDateFormat("d-MM-yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d/MM/yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d-MM-yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d/MM/yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d-MM-yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d/MM/yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d-MM-yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d/MM/yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d-MM-yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d/MM/yyyy HH:mm"));
    }
    /**
     * Validate date format with regular expression
     * 
     * @param date
     * @return true valid date format, false invalid date format
     */
    public static boolean validate(String date) {
        Date validDate;
        
        for (SimpleDateFormat sdf : DATE_FORMATS) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {
                    return true;
                }
            } catch (ParseException e) {
                continue;
            }
        }
        for (SimpleDateFormat sdf : DATE_FORMATS1) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {                    
                    return true;
                }
            } catch (ParseException e) {
                continue;
            }
        }

        
        return false;
    }
    /**
     * Convert valid date input into date format
     * Optional to contain time of the day in hour and mins
     * @param date
     * @return the date in valid date format
     */
    public static Date parseDate(String date) {
        Date validDate;
        
        String dateform;
        for (SimpleDateFormat sdf : DATE_FORMATS1) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {
                    dateform = sdf.format(validDate);
                    dateform = dateform.concat(" 23:59");
                    validDate = DATE_FORMATS2.get(DATE_FORMATS1.indexOf(sdf)).parse(dateform);
                    return validDate;
                }
            } catch (ParseException e) {
                continue;
            }
        }

        for (SimpleDateFormat sdf : DATE_FORMATS) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {
                    return validDate;
                }
            } catch (ParseException e) {
                continue;
            }
        }        
        return null;
    }
    /**
     * Convert valid reminder date input into date format
     * Optional to contain time of the day in hour and mins
     * @param date
     * @return the date in valid date format
     */
    public static Date parseReminder(String date) {
        Date validDate;
        String dateform;
        for (SimpleDateFormat sdf : DATE_FORMATS1) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {
                    dateform = sdf.format(validDate);
                    dateform = dateform.concat(" 20:00");
                    validDate = DATE_FORMATS2.get(DATE_FORMATS1.indexOf(sdf)).parse(dateform);
                    return validDate;
                }
            } catch (ParseException e) {
                continue;
            }
        }
        
        for (SimpleDateFormat sdf : DATE_FORMATS) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {
                    return validDate;
                }
            } catch (ParseException e) {
                continue;
            }
        }        
     
        return null;
    }
    /**
     * Convert valid date input into date format for event
     * Must contain time of the day in hour and mins
     * @param date
     * @return the date in valid date format
     */
    public static Date parseEvent(String date) {
        Date validDate;
        
        for (SimpleDateFormat sdf : DATE_FORMATS) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {
                    return validDate;
                }
            } catch (ParseException e) {
                continue;
            }
        }        
        return null;
    }

    
    public static boolean hasPassed(Date date) {
        Date today = Calendar.getInstance().getTime();
        
        if (date.before(today)) {
            return true;
        } else {
            return false;
        }
    }
}
