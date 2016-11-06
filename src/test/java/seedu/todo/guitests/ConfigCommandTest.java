package seedu.todo.guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.todo.commons.core.ConfigCenter;
import seedu.todo.commons.core.ConfigDefinition;
import seedu.todo.controllers.ConfigController;

public class ConfigCommandTest extends GuiTest {
    
    @Test
    public void config_showAll_success() {
        console.runCommand("config");
        for (ConfigDefinition configDefinition : ConfigCenter.getInstance().getConfig().getDefinitions()) {
            assertNotNull(configView.getConfigItem(configDefinition));            
        }
    }

    @Test
    public void config_wrongNumArgs_disambig() {
        console.runCommand("config 1 2 3 4");
        assertEquals(ConfigController.TEMPLATE_SET_CONFIG, console.getConsoleInputText());
    }
    
}
