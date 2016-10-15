package seedu.jimi.logic;

import java.lang.reflect.Field;

import org.junit.Test;

import seedu.jimi.logic.commands.AddCommand;
import seedu.jimi.logic.commands.Command;
import seedu.jimi.logic.parser.JimiParser;
import seedu.jimi.model.task.DeadlineTask;

public class JimiParserTest {

    @Test
    public void test() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        JimiParser parser = new JimiParser();
        String userInput = "add \"chore\" due 12-31-2017";
        Command c = parser.parseCommand(userInput);
        assert c instanceof AddCommand;
        Field field = AddCommand.class.getDeclaredField("toAdd");
        field.setAccessible(true);
        Object o = field.get(c);
        assert o instanceof DeadlineTask;
        String output = ((DeadlineTask)o).getDeadline().toString();
        System.out.println(output);
    }

}
