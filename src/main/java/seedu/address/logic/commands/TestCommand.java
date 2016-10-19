package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.*;
import seedu.address.model.tag.UniqueTagList;

public class TestCommand {
    public static void main(String[] args) {
        try {
            ReadOnlyActivity newParams = new Activity(
                    new Name("Name"),
                    new Reminder(""),
                    new UniqueTagList()
            );
            
            System.out.println(newParams.getClass().getSimpleName());
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
