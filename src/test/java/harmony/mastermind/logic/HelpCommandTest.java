package harmony.mastermind.logic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import harmony.mastermind.logic.commands.HelpCommand;

//@@author A0139194X
public class HelpCommandTest {
    
    private final int NUM_ENTRIES = 17;
    
    @Test
    public void initInfo_success() {
        HelpCommand help = new HelpCommand();
        assertEquals(help.getEntries().size(), NUM_ENTRIES);
    }
    
    @Test
    public void getEntries_success() {
        HelpCommand help = new HelpCommand();
        assertTrue(help.getEntries() instanceof ArrayList<?>);
    }

}
