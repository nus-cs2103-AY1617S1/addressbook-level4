package seedu.ggist.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.tag.Tag;
import seedu.ggist.model.tag.UniqueTagList;
import seedu.ggist.model.task.Task;
import seedu.ggist.model.task.TaskName;

public class CommandStub extends Command{
    
    public CommandStub(String...arguments) {
        System.out.println(arguments[0]);
        System.out.println(arguments[1]);
        System.out.println(arguments[2]);
        System.out.println(arguments[3]);
    }
    
    @Override
    public CommandResult execute() {
        return null;
    }

}
