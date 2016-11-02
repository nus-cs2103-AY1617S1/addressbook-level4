package seedu.address.model.activity.event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public EndTime(StartTime starttime, String date) throws IllegalValueException {
        super(Calendar.getInstance());
        SimpleDateFormat start = new SimpleDateFormat("d-MM-yyyy HH:mm");
        String startstring = start.format(starttime.value.getTime());
        Date startdate;
        try {
            startdate = start.parse(startstring);
        } catch (ParseException e) {
            throw new IllegalValueException("Start Time Invalid");
        }
        // String[] recurring = starttime.RecurringMessage.split(" ");
        if (starttime.recurring) {
            if(date.equals("")) {
                recurring = true;
                String recu[] = starttime.RecurringMessage.split(" ");
                Calendar startcal = starttime.value;
                startcal.add(Calendar.HOUR_OF_DAY, 1);
                SimpleDateFormat format1 = new SimpleDateFormat("EEE HHmm");
                RecurringMessage = recu[0] + " " + format1.format(startcal.getTime());
                this.value = DateUtil.EndDateTime(startdate);
            }
            else {
                recurringEndTime(starttime, startdate, date);
                setDate(date);
            }
        } else if (date.equals("")) {
            this.value = DateUtil.EndDateTime(startdate);
        } else if(date.split(" ").length==1){
            Calendar startcal = starttime.value;
            SimpleDateFormat format1 = new SimpleDateFormat("d-MM-yyyy");
            date = format1.format(startcal) + " " + date;
            setDate(date);
        } else {
            setDate(date);
        }

    }

    private void recurringEndTime(StartTime starttime, Date startdate, String date) throws IllegalValueException {
        this.recurring = true;
        String[] recur;
        recur = date.split(" ");
        String[] recurstart;
        recurstart = starttime.RecurringMessage.split(" ");
        String[] recurendtime;
        if (recur.length == 1) {
            RecurringMessage = recurstart[0] + " " + recurstart[1] + " " + recur[0];
            date = recurstart[1] + " " + recur[0];
        } else if (date.contains("every")) {
            RecurringMessage = date;
            recurendtime = date.split(" ", 2);
            if (recurendtime.length == 1)
                throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
            date = recur[1];
        } else if (recur.length == 2) {
            RecurringMessage = "every " + date;
        } else
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        setDate(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(startdate);
        if (this.value.before(startdate)) {
            while (this.value.before(startdate)) {
                if (date.contains("mon") || date.contains("tues") || date.contains("wedn") || date.contains("thur")
                        || date.contains("frid") || date.contains("satu") || date.contains("sund"))
                    this.value.add(Calendar.DAY_OF_WEEK, 7);
                if (date.contains("day"))
                    this.value.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
    }

    public Date convertStringtoDate(String date) throws IllegalValueException {
        ArrayList<SimpleDateFormat> DATE_FORMATS = new ArrayList<>();
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy h:mm a"));
        DATE_FORMATS.add(new SimpleDateFormat("EEE, MMM d, yyyy h:mm a"));
        for (SimpleDateFormat sdf : DATE_FORMATS) {
            try {
                Date date1 = sdf.parse(date);
                return date1;
            } catch (ParseException e) {
                continue;
            }
        }
        throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
    }

    void setDate(String date) throws IllegalValueException {
        String[] recur = date.split(" ", 2);
        String recurfreq = recur[0];
        if (recur.length == 1)
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        if (recurfreq.contains("day")) {
            date = "today " + recur[1];
        }
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        }
        if (!date.equals("")) {
            Date taskDate = DateUtil.FixedDateConvert(date);

            if (taskDate == null) {
                assert false : "Date should not be null";
            } /*
               * else if (DateUtil.hasPassed(taskDate)) { throw new
               * IllegalValueException(MESSAGE_STARTTIME_INVALID); }
               */

            if (!isValidDate(date)) {
                throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
            }
            this.value.setTime(taskDate);
            this.value.set(Calendar.MILLISECOND, 0);
            this.value.set(Calendar.SECOND, 0);
            while (recurring && this.value.before(Calendar.getInstance())) {
                if (recurfreq.contains("mon") || recurfreq.contains("tue") || recurfreq.contains("wed")
                        || recurfreq.contains("thu") || recurfreq.contains("fri") || recurfreq.contains("sat")
                        || recurfreq.contains("sun"))
                    this.value.add(Calendar.DAY_OF_WEEK, 7);
                if (recurfreq.contains("day"))
                    this.value.add(Calendar.DAY_OF_MONTH, 1);
            }

        }
    }

    public EndTime(String date) throws IllegalValueException {
        super(date);
        String[] recur;
        if (date != "") {
            if (date.contains("every")) {
                this.recurring = true;
                RecurringMessage = date;
                recur = date.split(" ", 2);
                if (recur.length == 1)
                    throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
                date = recur[1];
            }

            setDate(date);
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
