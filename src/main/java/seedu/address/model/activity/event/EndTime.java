package seedu.address.model.activity.event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.DateValidation;
import seedu.address.model.activity.DateTime;
//@@author A0131813R
public class EndTime extends DateTime {

    public static final String MESSAGE_ENDTIME_CONSTRAINTS = "Event's start time should only contain valid date";
    public static final String MESSAGE_ENDTIME_INVALID = "Event has already ended";
    public static final String MESSAGE_ENDTIME_NOTVALID = "Event end time is before start time";
    public String RecurringMessage;
    public EndTime(Calendar date) {
        super(date);
    }

    /**
     * Validates given Start Time.
     *
     * @throws IllegalValueException
     *             if given Start time string is invalid.
     */
    public EndTime(String starttime, String date) throws IllegalValueException {
        super(Calendar.getInstance());
        Date startdate = convertStringtoDate(starttime);
        if (date.equals("")){
            this.value= DateUtil.EndDateTime(startdate);
        }
        else {
            String[] recur;
            if(date!=""){
            if (date.contains("every")) {
                this.recurring = true;
                RecurringMessage = date;
                recur = date.split(" ", 2);
                if(recur.length==1)
                    throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
                date = recur[1];
            }
            setDate(date);
        }}    
      if (this.value.before(startdate)&&!this.recurring)
            throw new IllegalValueException(MESSAGE_ENDTIME_NOTVALID);
       else
           while (recurring && this.value.before(startdate)) {
               if (date.contains("year"))
                   this.value.add(Calendar.YEAR, 1);
               if (date.contains("month"))
                   this.value.add(Calendar.MONTH, 1);
               else if( date.contains("mond")||date.contains("tues")||date.contains("wedn")||date.contains("thur")||date.contains("frid")||date.contains("satu")||date.contains("sund"))
               this.value.add(Calendar.DAY_OF_WEEK, 7);
               if (date.contains("day"))
                   this.value.add(Calendar.DAY_OF_MONTH, 1);
           }
           
    }
    
    public Date convertStringtoDate(String date) throws IllegalValueException{
        DateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a");
        try {
            Date date1 = format.parse(date);
            return date1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
    }
    
    public void setDate(String date) throws IllegalValueException {
        String[] recur = date.split(" ", 2);
        String recurfreq = recur[0];
        if(recur.length==1)
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        if (recurfreq.contains("day")){
            date = "today " + recur[1];
        }
        if (recurfreq.contains("month") ) {
            date = DateUtil.everyMonth(recur[1]);       
        }
        if(recurfreq.contains("year")){
            date = DateUtil.everyYear(recur[1]);   
        }
/*        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        }
  */      if (!date.equals("")) {
            Date taskDate = DateUtil.FixedDateConvert(date);


            if (taskDate == null) {
                assert false : "Date should not be null";
            } /*else if (DateUtil.hasPassed(taskDate)) {
                throw new IllegalValueException(MESSAGE_STARTTIME_INVALID);
            }*/

            if (!isValidDate(date)) {
                throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
            }
            this.value.setTime(taskDate);
            this.value.set(Calendar.MILLISECOND, 0);
            this.value.set(Calendar.SECOND, 0);
            while (recurring && this.value.before(Calendar.getInstance())) {
                if (recurfreq.contains("year"))
                    this.value.add(Calendar.YEAR, 1);
                if (recurfreq.contains("month"))
                    this.value.add(Calendar.MONTH, 1);
                else if( recurfreq.contains("mon")||recurfreq.contains("tue")||recurfreq.contains("wed")||recurfreq.contains("thu")||recurfreq.contains("fri")||recurfreq.contains("sat")||recurfreq.contains("sun"))
                this.value.add(Calendar.DAY_OF_WEEK, 7);
                if (recurfreq.contains("day"))
                    this.value.add(Calendar.DAY_OF_MONTH, 1);
            }


        }
    }

    public EndTime(String date) throws IllegalValueException {
        super(date);
        Date taskDate;

        if (!date.equals("")) {
            taskDate = DateUtil.FixedDateConvert(date);

            if (taskDate.equals(null)) {
                assert false : "Date should not be null";
            } /* else if (DateUtil.hasPassed(taskDate)) {
                throw new IllegalValueException(MESSAGE_ENDTIME_INVALID);
            }*/

            if (!isValidDate(date)) {
                throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
            }
            this.value.setTime(taskDate);
            this.value.set(Calendar.MILLISECOND, 0);
            this.value.set(Calendar.SECOND, 0);
        }
    }

	public String forDisplay() {
        if (this.value == null) {
            return "End:\t\t\t-";
        } else {
            return "End:\t\t\t".concat(this.toString());
        }
    }
}
