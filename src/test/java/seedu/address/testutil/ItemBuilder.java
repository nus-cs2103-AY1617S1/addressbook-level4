package seedu.address.testutil;

import java.time.LocalDateTime;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.DateTimeParser;
import seedu.address.model.item.*;

public class ItemBuilder {

    private TestItem item;
    
    public ItemBuilder(){
        this.item = new TestItem();
    }
    
    public ItemBuilder withDescription(String description) throws IllegalValueException{
        this.item.setDescription(new Description(description));
        return this;
    }
    
    public ItemBuilder withTags(String... tags) throws IllegalValueException{
        //TODO: fill in
        return null;
    }
    
    public ItemBuilder withDates(String startdate) throws IllegalValueException{
    	DateTimeParser parser = new DateTimeParser(startdate);
		LocalDateTime startTimeObj = parser.extractStartDate();
		LocalDateTime endTimeObj = parser.extractStartDate();
		this.item.setStartDate(startTimeObj);
		this.item.setEndDate(endTimeObj);
		return this;
    }
    
    public TestItem build(){
        return this.item;
    }
    
}
