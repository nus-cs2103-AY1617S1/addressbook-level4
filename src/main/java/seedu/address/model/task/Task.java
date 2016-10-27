package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.time.temporal.ChronoUnit;

/**
 * Represents an event or a task (with or without deadline) in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private boolean isEvent;
    private Name name;
    private Date date;
    private SimpleStringProperty dateString;
    private boolean isDone;
    private BooleanProperty done; // Use Observable so that listeners can know
                                  // when the task's done status is updated
    private boolean isRecurring;
    private Recurring recurring;
    private UniqueTagList tags;
    /*
     * public static void main(String[] args) throws IllegalValueException{ Task
     * t=new Task(new Name("xiaowei"),new Deadline("22.09.2016-14"),new
     * UniqueTagList(),new Recurring("weekly")); t.updateRecurringTask(); Task
     * k=new Task(new Name("Donghae"),new
     * EventDate("22.11.2016-14","25.11.2016-15"),new UniqueTagList(),new
     * Recurring("daily")); k.updateRecurringTask();
     * System.out.println(k.getAsText()); }
     */

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Date date, UniqueTagList tags) {
        this(name, date, tags, false, false);
        recurring = null;
    }

    public Task(Name name, Date date, UniqueTagList tags, Recurring recurring) {
        this(name, date, tags, false, true);
        this.recurring = recurring;
    }

    public Task(Name name, Date date, UniqueTagList tags, boolean isDone, boolean isRecurring) {
        assert !CollectionUtil.isAnyNull(name, date, tags);
        this.name = name;
        this.date = date;
        this.dateString=new SimpleStringProperty(date.getValue());
        if (date instanceof EventDate) {
            isEvent = true;
        } else {
            isEvent = false;
        }
        this.tags = new UniqueTagList(tags); // protect internal tags from
                                             // changes in the arg list
        this.isDone = isDone;
        this.done = new SimpleBooleanProperty(isDone);
        this.isRecurring = isRecurring;

    }
    
    public Task(Name name, Date date, UniqueTagList tags, boolean isDone, Recurring Recurring) {
        assert !CollectionUtil.isAnyNull(name, date, tags);
        this.name = name;
        this.date = date;
        this.dateString=new SimpleStringProperty(date.getValue());
        if (date instanceof EventDate) {
            isEvent = true;
        } else {
            isEvent = false;
        }
        this.tags = new UniqueTagList(tags); // protect internal tags from
                                             // changes in the arg list
        this.isDone = isDone;
        this.done = new SimpleBooleanProperty(isDone);
        this.isRecurring=true;
        this.recurring = Recurring;

    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDate(), source.getTags(), source.isDone(), source.isRecurring());
        if (source.isRecurring())
            this.recurring = source.getRecurring();
    }

    public Task(Name name, UniqueTagList tags) throws IllegalValueException {
        this(name, new Deadline(""), tags, false, false);
        this.recurring = null;
    }

    //@@LiXiaowei A0142325R
    public void updateRecurringTask() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar currentDateTime = Calendar.getInstance();
        DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                .withLocale(Locale.GERMAN);
        LocalDate currentDate = LocalDate.parse(dateFormat.format(currentDateTime.getTime()).toString(),
                germanFormatter);
        LocalDate startDate = LocalDate.parse(date.getValue().substring(0, 10), germanFormatter);
        long elapsedDays = ChronoUnit.DAYS.between(startDate, currentDate);
        if (elapsedDays < 1)
            return;
        switch (recurring.recurringFrequency) {
        case "daily":
            updateRecurringTask(elapsedDays);
            break;
        case "weekly":
            long numWeek = elapsedDays / 7 + 1;
            updateRecurringTask(numWeek * 7);
            break;
        case "monthly":
            long numMonth = elapsedDays / 28 + 1;
            updateRecurringTask(numMonth * 28);
        case "yearly":
            break;
        default:
           // System.out.println("default");
            break;
        }
    }

    private void updateRecurringTask(long daysToUpdate) {
        DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                .withLocale(Locale.GERMAN);
        if (date instanceof EventDate) {
            String startDate = ((EventDate) date).getStartDate().substring(0, 10);
            LocalDate startLocalDate = LocalDate.parse(startDate, germanFormatter);
            startDate = germanFormatter.format(startLocalDate.plusDays(daysToUpdate)).toString();
            String endDate = ((EventDate) date).getEndDate().substring(0, 10);
            LocalDate endLocalDate = LocalDate.parse(endDate, germanFormatter);
            endDate = germanFormatter.format(endLocalDate.plusDays(daysToUpdate)).toString();
            String startTime = "", endTime = "";
            if (((EventDate) date).getStartDate().length() > 10) {
                startTime = ((EventDate) date).getStartDate().substring(11);
                endTime = ((EventDate) date).getEndDate().substring(11);
            }
            if(!startTime.equals("")&&!endTime.equals("")){
            ((EventDate) date).updateDate(startDate + "-" + startTime, endDate + "-" + endTime);
            }else if(!startTime.equals("")){
                ((EventDate) date).updateDate(startDate+"-"+startTime, endDate);
            }else if(!endTime.equals("")){
                ((EventDate) date).updateDate(startDate, endDate+"-"+endTime);
            }else{
                ((EventDate) date).updateDate(startDate, endDate);
            }
        } else {
            if (date instanceof Deadline) {
                String deadlineDate = date.getValue().substring(0, 10);
                LocalDate deadlineLocalDate = LocalDate.parse(deadlineDate, germanFormatter);
                deadlineDate = germanFormatter.format(deadlineLocalDate.plusDays(daysToUpdate)).toString();
                String deadlineTime = "";
                if (date.toString().length() > 10) {
                    deadlineTime = date.toString().substring(11);
                }
                if(!deadlineTime.equals(""))
                ((Deadline) date).updateDate(deadlineDate + "-" + deadlineTime);
                else
                    ((Deadline) date).updateDate(deadlineDate);
            }

        }
        dateString.set(date.getValue());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Recurring getRecurring() {
        // assert recurring!=null;
        return this.recurring;
    }
//
    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public boolean isEvent() {
        return isEvent;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }
    
    
    public SimpleStringProperty getDateString(){
        return dateString;
    }

    /**
     * Returns the Observable wrapper of the done status
     */
    public BooleanProperty getDone() {
        return done;
    }
    

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    //@@LiXiaowei A0142325R
    @Override
    public boolean isRecurring() {
        return isRecurring;
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(name, date, tags, isDone);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public void markAsDone() {
        isDone = true;
        done.set(true);
    }

    /*
     * Makes Task observable by its done status
     */
    public static Callback<Task, Observable[]> extractor() {
        return (Task task) -> new Observable[] { task.getDone(),task.getDateString()};
    }

}
