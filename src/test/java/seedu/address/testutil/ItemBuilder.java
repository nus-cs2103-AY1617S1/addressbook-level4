package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
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
        //TODO: fill in
        return null;
    }
    
    public ItemBuilder withEndDate(String enddate) throws IllegalValueException{
        //TODO: fill in
        return null;
    }
    
    public TestItem build(){
        return this.item;
    }
    
}
