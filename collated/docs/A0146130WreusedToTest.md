# A0146130WreusedToTest
###### /src/test/java/seedu/gtd/logic/LogicManagerTest.java
``` java
    @Test
    public void execute_help() throws Exception {
        assertCommandBehavior("help", HelpCommand.MESSAGE_USAGE+"/n"+HelpCommand.SHOWING_HELP_MESSAGE);
        assertTrue(helpShown);
        assertCommandBehavior("help add", AddCommand.MESSAGE_USAGE);
        assertCommandBehavior("help select", SelectCommand.MESSAGE_USAGE);
        assertCommandBehavior("help delete", DeleteCommand.MESSAGE_USAGE);
        assertCommandBehavior("help clear", ClearCommand.MESSAGE_USAGE);
        assertCommandBehavior("help find", FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("help list", ListCommand.MESSAGE_USAGE);
        assertCommandBehavior("help exit", ExitCommand.MESSAGE_USAGE);
    }

```
