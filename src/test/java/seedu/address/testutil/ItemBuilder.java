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
    
    public ItemBuilder withStartDate(String startdate) throws IllegalValueException{
    	DateTimeParser parser = new DateTimeParser(startdate);
		LocalDateTime startTimeObj = parser.extractStartDate();
		this.item.setStartDate(startTimeObj);
		return this;
    }
    
    public ItemBuilder withEndDate(String enddate) throws IllegalValueException{
    	DateTimeParser parser = new DateTimeParser(enddate);
		LocalDateTime endTimeObj = parser.extractStartDate();
		this.item.setEndDate(endTimeObj);
		return this;
    }
    
    public TestItem build(){
        return this.item;
    }
    
}
