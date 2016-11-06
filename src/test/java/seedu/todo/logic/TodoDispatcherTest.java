package seedu.todo.logic;

import org.junit.Test;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.AddCommand;
import seedu.todo.logic.commands.BaseCommand;
import seedu.todo.logic.commands.ClearCommand;
import seedu.todo.logic.commands.CommandMap;
import seedu.todo.logic.commands.CompleteCommand;
import seedu.todo.logic.commands.EditCommand;
import seedu.todo.logic.commands.RedoCommand;
import seedu.todo.logic.commands.SaveCommand;
import seedu.todo.logic.commands.ShowCommand;
import seedu.todo.logic.commands.UndoCommand;
import seedu.todo.logic.commands.ViewCommand;

import java.util.Map;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

//@@author A0139021U
public class TodoDispatcherTest {
    private Dispatcher d = new TodoDispatcher();
    
    @Test
    public void fullCommand() throws Exception {
        for (Map.Entry<String, Class<? extends BaseCommand>> entry : CommandMap.getCommandMap().entrySet()) {
            assertThat(d.dispatch(entry.getKey()), instanceOf(entry.getValue()));
        }
    }

    @Test
    public void firstLetterOfCommand() throws Exception {
        for (Map.Entry<String, Class<? extends BaseCommand>> entry : CommandMap.getCommandMap().entrySet()) {
            String firstLetter = entry.getKey().substring(0, 1);
            boolean isBelongToMultipleCommands = firstLetter.equals("e")
                    || firstLetter.equals("s")
                    || firstLetter.equals("c");
            if (!isBelongToMultipleCommands) {
                assertThat(d.dispatch(firstLetter), instanceOf(entry.getValue()));
            }
        }
    }

    /**
     * Partial commands set a baseline for what partial commands should trigger commands.
     * If being these are too vague, tune the threshold as this is what determines the user experience of
     * using smart commands.
     */
    @Test
    public void partialCommand() throws Exception {
        assertThat(d.dispatch("ad"), instanceOf(AddCommand.class));
        assertThat(d.dispatch("co"), instanceOf(CompleteCommand.class));
        assertThat(d.dispatch("cl"), instanceOf(ClearCommand.class));
        assertThat(d.dispatch("dit"), instanceOf(EditCommand.class));
        assertThat(d.dispatch("ed"), instanceOf(EditCommand.class));
        assertThat(d.dispatch("un"), instanceOf(UndoCommand.class));
        assertThat(d.dispatch("udo"), instanceOf(UndoCommand.class));
        assertThat(d.dispatch("re"), instanceOf(RedoCommand.class));
        assertThat(d.dispatch("rdo"), instanceOf(RedoCommand.class));
        assertThat(d.dispatch("vi"), instanceOf(ViewCommand.class));
        assertThat(d.dispatch("sh"), instanceOf(ShowCommand.class));
        assertThat(d.dispatch("sa"), instanceOf(SaveCommand.class));
    }
    
    @Test(expected = IllegalValueException.class)
    public void ambiguousCommand_letterE() throws Exception {
        d.dispatch("e");
    }

    @Test(expected = IllegalValueException.class)
    public void ambiguousCommand_letterS() throws Exception {
        d.dispatch("s");
    }

    @Test(expected = IllegalValueException.class)
    public void ambiguousCommand_letterC() throws Exception {
        d.dispatch("c");
    }

    @Test(expected = IllegalValueException.class)
    public void ambiguousCommand_wordDo() throws Exception {
        d.dispatch("do");
    }

    @Test(expected = IllegalValueException.class)
    public void ambiguousCommand_wordEit() throws Exception {
        d.dispatch("eit");
    }

    @Test(expected = IllegalValueException.class)
    public void emptyStringCommand() throws Exception {
        d.dispatch("");
    }
    
    @Test(expected = IllegalValueException.class)
    public void nonExistentCommand() throws Exception {
        d.dispatch("applejack");
    }
}
