package harmony.mastermind.logic.commands;

import java.util.Date;
import java.util.Set;

import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.commons.exceptions.InvalidEventDateException;


//@@author A0138862W
/*
 * AddCommandBuilder provides a safe way to create an AddCommand
 * 
 */
public class AddCommandBuilder {
    public String name;
    public Date startDate;
    public Date endDate;
    public Set<String> tags;
    public String recur;

    public AddCommandBuilder(String name) {
        this.name = name;
    }

    public AddCommandBuilder asEvent(Date startDate, Date endDate) throws InvalidEventDateException{
        if (startDate.after(endDate)) {
            throw new InvalidEventDateException();
        }
        
        this.startDate = startDate;
        this.endDate = endDate;
        return this;
    }

    public AddCommandBuilder asDeadline(Date endDate) {
        this.startDate = null;
        this.endDate = endDate;
        return this;
    }

    public AddCommandBuilder withTags(Set<String> tags) throws IllegalValueException {
        this.tags = tags;
        return this;
    }

    public AddCommandBuilder asRecurring(String recur) {
        this.recur = recur;
        return this;
    }

    public AddCommand build() throws IllegalValueException, InvalidEventDateException {
        return new AddCommand(this);
    }

    public boolean isFloating() {
        return startDate == null
               && endDate == null;
    }

    public boolean isDeadline() {
        return startDate == null
               && endDate != null;
    }

    public boolean isEvent() {
        return startDate != null
               && endDate != null;
    }

    public boolean isRecurring() {
        return this.recur != null;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Set<String> getTags() {
        return tags;
    }

    public String getRecur() {
        return recur;
    }

}