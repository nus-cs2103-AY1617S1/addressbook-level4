package tars.logic;

import org.junit.Test;

import tars.logic.commands.ExitCommand;

public class ExitLogicCommandTest extends LogicCommandTest {
    @Test
    public void execute_exit() throws Exception {
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }
}
