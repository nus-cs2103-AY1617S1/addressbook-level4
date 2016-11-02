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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
    private StringProperty nameString;
    private Date date;
    private StringProperty dateString;
    private boolean isDone;
    private BooleanProperty done; // Use Observable so that listeners can know
                                  // when the task's done status is updated
    private boolean isRecurring;
    private Recurring recurring;
    private StringProperty recurringString;
    private UniqueTagList tags;
    private Priority priorityLevel;
    private IntegerProperty priorityInteger;
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
    //@@author A0142325R
    public Task(Name name, Date date, UniqueTagList tags, Priority priorityLevel) {
        this(name, date, tags, false, false, priorityLevel);
        recurring = null;
    }

    public Task(Name name, Date date, UniqueTagList tags, Recurring recurring, Priority priorityLevel) {
        this(name, date, tags, false, true, priorityLevel);
        this.recurring = recurring;
        recurringString.set(recurring.recurringFrequency);
    }

    public Task(Name name, Date date, UniqueTagList tags, boolean isDone, boolean isRecurring, Priority priorityLevel) {
        assert !CollectionUtil.isAnyNull(name, date, tags);
        this.name = name;
        this.nameString = new SimpleStringProperty(name.taskName);
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
        recurringString = new SimpleStringProperty();
        this.priorityLevel = priorityLevel;
        this.priorityInteger = new SimpleIntegerProperty(priorityLevel.priorityLevel);
    }

    public Task(Name name, Date date, UniqueTagList tags, boolean isDone, Recurring Recurring, Priority priorityLevel) {
        assert !CollectionUtil.isAnyNull(name, date, tags);
        this.name = name;
        this.nameString = new SimpleStringProperty(name.taskName);
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
        recurringString = new SimpleStringProperty(recurring.recurringFrequency);
        this.priorityLevel = priorityLevel;
        this.priorityInteger = new SimpleIntegerProperty(priorityLevel.priorityLevel);
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDate(), source.getTags(), source.isDone(), source.isRecurring(), source.getPriorityLevel());
        if (source.isRecurring()) {
            this.recurring = source.getRecurring();
            recurringString.set(recurring.recurringFrequency);
        }
    }

    public Task(Name name, UniqueTagList tags, Priority priorityLevel) throws IllegalValueException {
        this(name, new Deadline(""), tags, false, false, priorityLevel);
        this.recurring = null;
    }

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
            long numWeek = (elapsedDays - 1) / 7 + 1;
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
    //@@author

    @Override
    public Name getName() {
        return name;
    }

    //@@author A0142325R
    @Override
    public Recurring getRecurring() {
        // assert recurring!=null;
        return this.recurring;
    }

    //@@author A0146123R
    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public boolean isEvent() {
        return isEvent;
    }
    //@@author

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    //@@author A0142325R
    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public boolean isRecurring() {
        return isRecurring;
    }
    //@@author

    //@@author A0138717X
    @Override
    public Priority getPriorityLevel() {
    	return priorityLevel;
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

    //@@author A0142325R
    @Override
    public void markAsDone() {
        isDone = true;
        done.set(true);
    }

    //@@author A0146123R
    public void setName(Name newName) {
        name = newName;
        nameString.set(name.taskName);
    }

    public void setDate(Date newDate) {
        date = newDate;
        dateString.setValue(date.getValue());
        // It does nothing but can magically fix a bug. It seems like the UI need some time to reflect.
        dateString.get(); 
    }

    public void setRecurring(Recurring newRecurring) {
        recurring = newRecurring;
        isRecurring = true;
        recurringString.set(recurring.recurringFrequency);
    }

    /**
     * Returns Observable wrappers of the task
     */
    public StringProperty getNameString(){
        return nameString;
    }

    public StringProperty getDateString(){
        return dateString;
    }

    public BooleanProperty getDone() {
        return done;
    }

    public StringProperty getRecurringString(){
        return recurringString;
    }

    /*
     * Makes Task observable by its status
     */
    public static Callback<Task, Observable[]> extractor() {
        return (Task task) -> new Observable[] { task.getNameString(), task.getDateString(), task.getDone(),
                task.getRecurringString(), task.getPriorityInteger()};
    }

    //@@author A0138717X
	public boolean editDetail(String type, String details) throws IllegalValueException {
		switch(type) {
    	case "name": setName(new Name(details)); break;
    	case "priority": setPriorityLevel(new Priority(Integer.parseInt(details))); break;
    	case "recurring": setRecurring(new Recurring(details)); break;
    	case "startDate": setDate(new EventDate(details,((EventDate) date).getEndDate())); break;
    	case "endDate": setDate(new EventDate(((EventDate) date).getStartDate(), details)); break;
    	case "deadline": setDate(new Deadline(details)); break;
    	default: return false;
		}
		return true;
	}

	public IntegerProperty getPriorityInteger() {
		return priorityInteger;
	}

	public void setPriorityLevel(Priority priorityLevel) {
		this.priorityLevel = priorityLevel;
		priorityInteger.set(priorityLevel.priorityLevel);
	}

//	public boolean isValidPriorityLevel(int priorityLevel) {
//		if(priorityLevel <= 3)
//			return true;
//		return false;
//	}

}
